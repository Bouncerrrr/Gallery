import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ImageComponent } from './pages/image/image.component'
import {ShowcaseComponent} from "./pages/showcase/showcase.component";
import {UploadComponent} from "./pages/upload/upload.component";
import {SearchComponent} from "./pages/search/search.component";
import {EditComponent} from "./pages/edit/edit.component";
const routes: Routes = [
  {
    path: 'image/:id',
    component: ImageComponent
  },
  {
    path: 'showcase',
    component: ShowcaseComponent
  },
  {
    path: 'upload',
    component: UploadComponent
  },
  {
    path: 'searcher',
    component: SearchComponent
  },
  {
    path: 'edit/:id',
    component: EditComponent
  }

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ModulesRoutingModule { }
