import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../../models/user.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = 'http://localhost:8080';  // As defined on swagger json

  constructor(private http: HttpClient) { }

  getAllActiveUsers(): Observable<User[]> {
    return this.http.get<User[]>(`${this.apiUrl}/users`);
  }

  inactivateUser(userId: number): Observable<string> {
    return this.http.post<string>(`${this.apiUrl}/users/${userId}/inactivate`, {});
  }
}
