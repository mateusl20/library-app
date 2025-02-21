import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/auth';

  constructor(private http: HttpClient) {}

  login(username: string, password: string): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/login`, { username, password })
      .pipe(
        map(response => {
          if (response && response.token) {
            localStorage.setItem('currentUser', JSON.stringify(response));
          }
          return response;
        })
      );
  }

  logout() {
    localStorage.removeItem('currentUser');
  }

  getCurrentUser() {
    const user = localStorage.getItem('currentUser');
    console.log('Current user from localStorage:', user);
    return user ? JSON.parse(user) : null;
  }

  isAuthenticated(): boolean {
    const user = this.getCurrentUser();
    const isAuth = !!user && !!user.token;
    console.log('isAuthenticated:', isAuth);
    return isAuth;
  }

  hasRole(role: string): boolean {
    const user = this.getCurrentUser();
    const hasRole = user && user.user && user.user.roles && user.user.roles.includes(role);
    console.log('hasRole:', role, hasRole);
    return hasRole;
  }
}
