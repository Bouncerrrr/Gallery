import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavbarComponent } from './components/navbar/navbar.component';
import { BackgroundComponent } from './components/background/background.component';
import { FooterComponent } from './components/footer/footer.component';
import {BrowserModule} from "@angular/platform-browser";
import {HttpClient, HttpClientModule} from "@angular/common/http";
import {RouterLink} from "@angular/router";


@NgModule({
    declarations: [
        NavbarComponent,
        BackgroundComponent,
        FooterComponent
    ],
    exports: [
        NavbarComponent
    ],
    imports: [
        CommonModule,
      BrowserModule,
      HttpClientModule,
      RouterLink
    ]
})
export class CoreModule { }
