import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Book } from '../../models/book.model';

@Injectable({
  providedIn: 'root'
})
export class BookService {

  private apiUrl = 'http://localhost:8080'; // As defined on swagger json

  constructor(private http: HttpClient) { }

  getAllBooks(): Observable<Book[]> {
    return this.http.get<Book[]>(`${this.apiUrl}/books`);
  }

  addBook(book: Book): Observable<Book> {
    return this.http.post<Book>(`${this.apiUrl}/books/add`, book);
  }

  removeBook(bookId: number): Observable<string> {  // Changed to Observable<string>
    return this.http.delete(`${this.apiUrl}/books/${bookId}/remove`, { responseType: 'text' });
  }
}
