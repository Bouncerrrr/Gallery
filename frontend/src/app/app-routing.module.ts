import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ShowcaseComponent} from "./modules/pages/showcase/showcase.component";
import {SearchComponent} from "./modules/pages/search/search.component";

const routes: Routes = [
  {
  path: 'module',
  loadChildren: () => import('./modules/modules.module').then(m => m.ModulesModule)
  },
  { path: '', redirectTo: '/showcase', pathMatch: 'full' },
  { path: 'showcase', component: ShowcaseComponent },
  { path: 'search', component: SearchComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
