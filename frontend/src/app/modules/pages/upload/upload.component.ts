import { Component } from '@angular/core';
import { UploadService} from "../../../core/services/upload.service";
import {ImageModel} from "../../../core/models/image-model";
import {TagModel} from "../../../core/models/tag-model";
import {ImageService} from "../../../core/services/image.service";
import {MatDialog} from "@angular/material/dialog";
import {Router} from "@angular/router";
import {SuccessDialogComponent} from "../../dialog/success-dialog/success-dialog.component";

@Component({
  selector: 'app-upload',
  templateUrl: './upload.component.html',
  styleUrl: './upload.component.css'
})
export class UploadComponent {
  selectedFile: File | undefined;
  previewImage: string | undefined;
  name: string = '';
  description: string = '';
  mood: string = '';
  tagsInput: string = '';

  moodOptions: string[] = ['HAPPY', 'ANGRY', 'EXCITED', 'ADVENTUROUS', 'SAD', 'NERVOUS', 'LOVE', 'HUNGRY'];

  constructor(private uploadService: UploadService, private dialog: MatDialog,
              private router: Router) { }

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
      this.previewImage = undefined;
    }
  }

  onUpload(): void {
    if (!this.selectedFile || !this.name || !this.description || !this.mood || !this.tagsInput) {
      console.error('Please fill out all fields');
      return;
    }

    const formData = new FormData();
    formData.append('content', this.selectedFile);
    formData.append('name', this.name);
    formData.append('description', this.description);
    formData.append('mood', this.mood);
    formData.append('tags', this.tagsInput);

    this.uploadService.uploadImage(formData).subscribe(
      response => {
        console.log('Image uploaded successfully:', response);
        this.openSuccessDialog();
      },
      error => {
        console.error('Error uploading image:', error);
      }
    );
  }

  openSuccessDialog(): void {
    const dialogRef = this.dialog.open(SuccessDialogComponent, {
      data: {message: 'Image uploaded successfully!'}
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      this.router.navigate(['/showcase']); // Redirect to index page
    });
  }
}

