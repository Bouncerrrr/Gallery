import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ImageModel} from "../models/image-model";
import {Observable} from "rxjs";
import {UploadModel} from "../models/upload-model";

@Injectable({
  providedIn: 'root'
})
export class UploadService {

  constructor(private http: HttpClient) { }

  uploadImage(imageData: FormData): Observable<string> {
    return this.http.post(`http://localhost:8080/api/gallery/upload`, imageData, { responseType: 'text' });
  }
}
