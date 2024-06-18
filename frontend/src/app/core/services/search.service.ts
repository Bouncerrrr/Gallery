import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ApiResponse} from "../models/api-response";
import {Observable, tap} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class SearchService {

  constructor(private http: HttpClient) { }
  searchByKeywordAndMood(keyword: string, mood: string, page: number): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(`http://localhost:8080/api/search/page?keyword=${keyword}&mood=${mood}&page=${page}`).pipe(
      tap(response => console.log('API Response:', response)));
  }
}
