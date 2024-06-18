import { NgModule } from '@angular/core';
import { BrowserModule, provideClientHydration } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import  {CoreModule} from "./core/core.module";
import  {SharedModule} from "./shared/shared.module";
import {ModulesModule} from "./modules/modules.module";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import {MatFormFieldModule} from "@angular/material/form-field";
import {MAT_DIALOG_DEFAULT_OPTIONS, MatDialogModule} from "@angular/material/dialog";



@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    CoreModule,
    SharedModule,
    ModulesModule,
    MatInputModule,
    MatSelectModule,
    MatFormFieldModule,
    MatDialogModule
  ],
  providers: [
    provideClientHydration(),
    {provide: MAT_DIALOG_DEFAULT_OPTIONS, useValue: {hasBackdrop: true, direction: 'rtr'}},
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
