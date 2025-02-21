import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoanService {

  private apiUrl = 'http://localhost:8080'; // As defined on swagger json

  constructor(private http: HttpClient) { }

  rentBook(bookId: number, userId: number): Observable<string> {   // Changed to Observable<string>
    return this.http.post(`${this.apiUrl}/loans/${bookId}/rent/${userId}`, {}, { responseType: 'text' });
  }

  returnBook(bookId: number, userId: number): Observable<string> {  // Changed to Observable<string>
    return this.http.post(`${this.apiUrl}/loans/${bookId}/return/${userId}`, {}, { responseType: 'text' });
  }
}
