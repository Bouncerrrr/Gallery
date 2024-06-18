import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {catchError, Observable, throwError} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class DeleteService {

  constructor(private http: HttpClient) { }
  deleteImage(id: number): Observable<void> {
    return this.http.delete<void>(`http://localhost:8080/api/image/delete/${id}`).pipe(
      catchError(error => {
        let errorMessage = 'An error occurred while deleting the image.';
        if (error.error instanceof ErrorEvent) {
          errorMessage = `Client-side error: ${error.error.message}`;
        } else {
          errorMessage = `Server-side error: ${error.status} - ${error.error}`;
        }
        console.error(errorMessage);
        return throwError(errorMessage);
      })
    );
  }
}
