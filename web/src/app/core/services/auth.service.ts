import { Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { User } from '../models/user.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly baseUrl = 'http://localhost:8080/auth';
  
  // Signals for state management
  currentUser = signal<User | null>(null);
  isAuthenticated = signal<boolean>(false);

  constructor(private http: HttpClient) {}

  login(loginData: any): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/login`, loginData).pipe(
      tap(response => {
        if (response.token) {
          localStorage.setItem('token', response.token);
          this.isAuthenticated.set(true);
          // In a real app, we'd decode the JWT or fetch the user info
        }
      })
    );
  }

  logout(): void {
    localStorage.removeItem('token');
    this.isAuthenticated.set(false);
    this.currentUser.set(null);
  }
}
