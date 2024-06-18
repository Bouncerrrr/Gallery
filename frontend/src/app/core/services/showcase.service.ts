import { Injectable } from '@angular/core';
import {Observable, tap} from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';

import  {ApiResponse} from "../models/api-response";
@Injectable({
  providedIn: 'root'
})
export class ShowcaseService {

  constructor(private http: HttpClient) {
  }
  public Showcase(pageNumber: number): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(`http://localhost:8080/api/showcase/page?page=${pageNumber}`).pipe(
      tap(response => console.log('API Response:', response))
    );
  }

}
