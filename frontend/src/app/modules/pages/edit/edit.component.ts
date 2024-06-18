import {Component, OnInit, Sanitizer} from '@angular/core';
import { UploadService } from "../../../core/services/upload.service";
import { MatDialog } from "@angular/material/dialog";
import { ActivatedRoute, Router } from "@angular/router";
import { SuccessDialogComponent } from "../../dialog/success-dialog/success-dialog.component";
import { ImageModel } from "../../../core/models/image-model";
import { ImageService } from "../../../core/services/image.service";
import { TagModel } from "../../../core/models/tag-model";
import {EditService} from "../../../core/services/edit.service";
import { DomSanitizer, SafeResourceUrl, SafeUrl} from '@angular/platform-browser';

@Component({
  selector: 'app-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.css']
})
export class EditComponent implements OnInit{
  id: number | undefined;
  url: string = '';
  image: ImageModel | undefined;
  tags: TagModel[] | undefined;
  tagsStringArray: string[] = [];
  error: string | null = null;

  selectedFile: File | undefined;
  previewImage: string | null = '';
  sanitizedPreviewImage: SafeResourceUrl | undefined;
  newName: string | null = '';
  newDescription: string | null = '';
  newMood: string | null = '';
  tagsInput: string = '';


  moodOptions: string[] = ['HAPPY', 'ANGRY', 'EXCITED', 'ADVENTUROUS', 'SAD', 'NERVOUS', 'LOVE', 'HUNGRY'];

  constructor(private editService: EditService,
              private imageService: ImageService,
              private route: ActivatedRoute,
              private dialog: MatDialog,
              private router: Router,
              private sanitizer: DomSanitizer) { }
  ngOnInit(): void {

    this.id = parseInt(<string>this.route.snapshot.paramMap.get('id'));
    this.url = `http://localhost:8080/api/gallery/image/${this.id}`;
    console.log(this.url);
    this.imageService.getImage(this.id).subscribe(
      (result: ImageModel) => {
        this.image = result;
        this.tags = result.tags;
        this.newName = result.name;
        this.newDescription = result.description;
        this.newMood = result.mood;
        this.tagsStringArray = this.tags.map(tag => tag.tagName);
        this.tagsInput = this.tagsStringArray.join(' ');

        if (result.content != null) {
          this.sanitizedPreviewImage = this.sanitizer.bypassSecurityTrustResourceUrl( 'data:image/jpeg;base64,' + result.content);
          // Convert the sanitized content to string
        }

      }
    );

  }
  sanitizeContent(content: string): SafeResourceUrl {
    // Use DomSanitizer to sanitize the content
    return this.sanitizer.bypassSecurityTrustResourceUrl(content);
  }

  parseTags() {
    if (!this.tags) {
      return;
    }
    this.tagsStringArray = this.tags.map(tag => tag.tagName);
  }

  onFileSelected(event: any): void {
    this.selectedFile = event.target.files[0];
    this.previewSelectedImage();
  }
  previewSelectedImage(): void {
    if (this.selectedFile) {
      const reader = new FileReader();
      reader.onload = () => {
        this.previewImage = reader.result as string;
      };
      reader.readAsDataURL(this.selectedFile);
    } else {
      this.previewImage = null;
    }
  }

  onUpload(): void {
    if (!this.newName || !this.newDescription || !this.newMood || !this.tagsInput) {
      console.error('Please fill out all fields');
      return;
    }

    const formData = new FormData();
    if (this.selectedFile) {
      formData.append('content', this.selectedFile);
    }else if (this.image?.content) {
      const content = this.image.content;
      if (content) { // Check if content is not null
        const blob = this.base64toBlob(content, 'image/jpeg');
        formData.append('content', blob);
      } else {
        console.error('Image content is null');
        return;
      }
    }
    formData.append('name', this.newName);
    formData.append('description', this.newDescription);
    formData.append('mood', this.newMood);
    formData.append('tags', this.tagsInput);
    this.id = parseInt(<string>this.route.snapshot.paramMap.get('id'));
    this.url = `http://localhost:8080/api/gallery/image/${this.id}`;
    formData.append('id', this.id.toString())


    this.editService.editImage(formData).subscribe(
      response => {
        console.log('Image uploaded successfully:', response);
        this.openSuccessDialog();
      },
      error => {
        console.error('Error uploading image:', error);
      }
    );
  }
  base64toBlob(base64Data: string, contentType: string) {
    contentType = contentType || '';
    var sliceSize = 1024;
    var byteCharacters = atob(base64Data);
    var bytesLength = byteCharacters.length;
    var slicesCount = Math.ceil(bytesLength / sliceSize);
    var byteArrays = new Array(slicesCount);

    for (var sliceIndex = 0; sliceIndex < slicesCount; ++sliceIndex) {
      var begin = sliceIndex * sliceSize;
      var end = Math.min(begin + sliceSize, bytesLength);

      var bytes = new Array(end - begin);
      for (var offset = begin, i = 0; offset < end; ++i, ++offset) {
        bytes[i] = byteCharacters[offset].charCodeAt(0);
      }
      byteArrays[sliceIndex] = new Uint8Array(bytes);
    }
    return new Blob(byteArrays, { type: contentType });
  }

  openSuccessDialog(): void {
    const dialogRef = this.dialog.open(SuccessDialogComponent, {
      data: {message: 'Image updated successfully!'}
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      this.router.navigate(['/showcase']); // Redirect to index page
    });
  }

}
