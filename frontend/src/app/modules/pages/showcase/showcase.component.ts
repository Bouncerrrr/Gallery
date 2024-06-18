import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ShowcaseService} from "../../../core/services/showcase.service";
import {ShowcaseModel} from "../../../core/models/showcase-model";
import {ApiResponse} from "../../../core/models/api-response";
import {Observable} from "rxjs";
import {PageEvent} from "@angular/material/paginator";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-showcase',
  templateUrl: './showcase.component.html',
  styleUrls: ['./showcase.component.css']
})

export class ShowcaseComponent implements OnInit{
  images: ShowcaseModel[] = [];
  totalPages: number | undefined;
  page: ApiResponse | undefined;
  currentPage = 0;

  constructor(private showcaseService: ShowcaseService,private activatedRoute: ActivatedRoute,
              private router: Router) { }

  ngOnInit(): void {
    this.loadImages(this.currentPage);
  }

  loadImages(pageNumber:number) {
    let serviceCall: Observable<ApiResponse>;
    serviceCall = this.showcaseService.Showcase(pageNumber);
    serviceCall.subscribe(
      (data: ApiResponse) =>{
        this.page = data;
        this.images = data.content;
        this.totalPages = data.totalPages;
      }, error => {
      console.error('Error fetching images: ', error);
    }
    )
  }

  redirectToImage(id: number): void {
    this.router.navigate([`/image/${id}`]);
  }


  onPageChange(event: PageEvent) {
    this.currentPage = event.pageIndex;
    console.log('Selected page:', this.currentPage);
    let serviceCall: Observable<ApiResponse>;
    serviceCall = this.showcaseService.Showcase(this.currentPage);
    serviceCall.subscribe(
      (data: ApiResponse) => {
        this.page = data;
        this.images = data.content;
        this.totalPages = data.totalPages;
      }, error => {
        console.error('Error fetching images: ', error);
      }
    )
  }
}
