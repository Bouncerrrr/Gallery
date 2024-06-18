import { Component } from '@angular/core';
import {ImageModel} from "../../../core/models/image-model";
import {TagModel} from "../../../core/models/tag-model";
import {ActivatedRoute, Router} from '@angular/router';
import { ImageService} from "../../../core/services/image.service";
import {MatDialog} from "@angular/material/dialog";
import {DeleteDialogComponent} from "../../dialog/delete-dialog/delete-dialog.component";
import {DeleteService} from "../../../core/services/delete.service";
import { MatSnackBar } from "@angular/material/snack-bar";

@Component({
  selector: 'app-image',
  templateUrl: './image.component.html',
  styleUrl: './image.component.css'
})
export class ImageComponent {
  id: number | undefined;
  url: string = '';
  image: ImageModel | undefined;
  tags: TagModel[] | undefined;
  tagsStringArray: string[] = [];
  error: string | null = null;

  constructor(private activatedRoute: ActivatedRoute,
              private imageService: ImageService,
              private deleteService: DeleteService,
              private dialog: MatDialog,
              private snackBar: MatSnackBar,
              private router: Router) {
  }

  ngOnInit(): void {

    this.id = parseInt(<string>this.activatedRoute.snapshot.paramMap.get('id'));
    this.url = `http://localhost:8080/api/gallery/image/${this.id}`;
    console.log(this.url);
    this.loadImage(this.id);
  }
  loadImage(id: number) {
    this.imageService.getImage(id).subscribe(
      (result: ImageModel) => {
        this.image = result;
        this.tags = result.tags;
        this.parseTags();
      }
    );
  }

  parseTags() {
    if (!this.tags) {
      return;
    }
    this.tagsStringArray = this.tags.map(tag => tag.tagName);
  }

  deleteImage() {
    if (this.id === undefined) {
      return;
    }

    const dialogRef = this.dialog.open(DeleteDialogComponent, {
      width: '250px',
      data: { message: 'Are you sure you want to delete this image?' }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.deleteService.deleteImage(this.id!).subscribe(
          () => {
            this.snackBar.open('Image deleted successfully!', 'Close', { duration: 3000 });
            this.router.navigate(['/showcase']); // Redirect to image list page after deletion
          },
          error => {
            this.snackBar.open('Failed to delete image!', 'Close', { duration: 3000 });
          }
        );
      }
    });
  }
  redirectToEdit() {
    if (this.id !== undefined) {
      this.router.navigate(['/edit', this.id]); // Redirect to edit page for the current image
    }
  }

}
