import { Component, Inject } from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {DialogData} from "../delete-dialog/delete-dialog.component";

@Component({
  selector: 'app-success-dialog',
  templateUrl: './success-dialog.component.html',
  styleUrl: './success-dialog.component.css'
})
export class SuccessDialogComponent {
  constructor(public dialogRef: MatDialogRef<SuccessDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: DialogData
  ) {}

}
