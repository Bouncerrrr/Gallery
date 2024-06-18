import { Injectable } from '@angular/core';
import {catchError, map, Observable, throwError} from "rxjs";
import {ImageModel} from "../models/image-model"
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class ImageService {

  constructor(private http: HttpClient) {
  }

  public getImage(id: number): Observable<ImageModel> {
    return this.http.get<ImageModel>(`http://localhost:8080/api/gallery/image/${id}`).pipe(
      map((response: ImageModel) => {
        return response;
      }),
      catchError((error: any) => {
        console.error('Error fetching image:', error);
        return throwError('Error fetching image. Please try again later.');
      })
    );
  }


}
