import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class EditService {

  constructor(private http: HttpClient) { }

  editImage(imageData: FormData): Observable<string> {
    return this.http.post(`http://localhost:8080/api/image/edit`, imageData, { responseType: 'text' });
  }
}
