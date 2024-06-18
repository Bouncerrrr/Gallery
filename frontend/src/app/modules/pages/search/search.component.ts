import { Component } from '@angular/core';
import {SearchService} from "../../../core/services/search.service";
import {Observable} from "rxjs";
import {ApiResponse} from "../../../core/models/api-response";
import {PageEvent} from "@angular/material/paginator";
import {ShowcaseModel} from "../../../core/models/showcase-model";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrl: './search.component.css'
})
export class SearchComponent {
  keyword: string | undefined = '';
  selectedMood: string | undefined = '';
  moods: string[] = ['HAPPY', 'ANGRY', 'EXCITED', 'ADVENTUROUS', 'SAD', 'NERVOUS', 'LOVE', 'HUNGRY'];
  images: ShowcaseModel[] = [];
  totalPages: number | undefined;
  page: ApiResponse | undefined;
  currentPage = 0;

  constructor(private searchService: SearchService,
              private activatedRoute: ActivatedRoute,
              private router: Router) {}

  redirectToImage(id: number): void {
    this.router.navigate([`/image/${id}`]);
  }

  onPageChange(event: PageEvent) {
    this.currentPage = event.pageIndex;
    console.log('Selected page:', this.currentPage);
    this.searchImages();
  }

  // Method to perform search
  searchImages() {
    if (this.keyword && this.selectedMood) {
      this.searchService.searchByKeywordAndMood(this.keyword, this.selectedMood, this.currentPage)
        .subscribe(
          (data: ApiResponse) => {
            this.images = data.content;
            this.totalPages = data.totalPages;
          },
          error => {
            console.error('Error searching images: ', error);
          }
        );
    }
  }

}
