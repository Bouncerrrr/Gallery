import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ModulesRoutingModule } from './modules-routing.module';
import { ShowcaseComponent } from './pages/showcase/showcase.component';
import {MatPaginator} from "@angular/material/paginator";
import { ImageComponent } from './pages/image/image.component';
import { UploadComponent } from './pages/upload/upload.component';
import {FormsModule} from "@angular/forms";
import { SuccessDialogComponent } from './dialog/success-dialog/success-dialog.component';
import {MatDialogActions, MatDialogClose, MatDialogContent} from "@angular/material/dialog";
import { DeleteDialogComponent } from './dialog/delete-dialog/delete-dialog.component';
import { SearchComponent } from './pages/search/search.component';
import {MatFormField} from "@angular/material/form-field";
import {MatOption, MatSelect} from "@angular/material/select";
import {MatInput} from "@angular/material/input";
import {MatButton} from "@angular/material/button";
import { EditComponent } from './pages/edit/edit.component';


@NgModule({
  declarations: [
    ShowcaseComponent,
    ImageComponent,
    UploadComponent,
    SuccessDialogComponent,
    DeleteDialogComponent,
    SearchComponent,
    EditComponent
  ],
  exports: [
    ShowcaseComponent,
    SearchComponent
  ],
  imports: [
    CommonModule,
    ModulesRoutingModule,
    MatPaginator,
    FormsModule,
    MatDialogContent,
    MatDialogActions,
    MatDialogClose,
    MatFormField,
    MatSelect,
    MatOption,
    MatInput,
    MatButton
  ]
})
export class ModulesModule { }
