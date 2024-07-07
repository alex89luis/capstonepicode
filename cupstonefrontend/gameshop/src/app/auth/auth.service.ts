
import { Injectable } from '@angular/core';
import { Utente } from '../models/utente';
import { JwtHelperService } from '@auth0/angular-jwt';
import { BehaviorSubject, Observable, map, tap } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { environment } from '../../environments/environment.development';
import { ILogin } from '../models/i-login';

type AccessData = {
  user: Utente,
  token: string
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  jwtHelper: JwtHelperService = new JwtHelperService();
  authSubject = new BehaviorSubject<Utente | null>(null);
  private apiUrl = environment.apiUrl;

  user$ = this.authSubject.asObservable();
  isLoggedIn$ = this.user$.pipe(
    map(user => !!user),
    tap(user => this.syncIsLoggedIn = user)
  );
  syncIsLoggedIn: boolean = false;

  constructor(
    private http: HttpClient,
    private router: Router
  ) {
    this.restoreUser();
  }

  registerUrl: string = `${this.apiUrl}/utenti`;
  loginUrl: string = `${this.apiUrl}/auth/login`;

  register(user: Partial<Utente>): Observable<any> {
    return this.http.post(`${this.registerUrl}`, user).pipe(
      tap({
        next: data => console.log('Registrazione avvenuta con successo', data),
        error: err => console.error('Errore durante la registrazione', err)
      })
    );
  }

  login(loginData: ILogin): Observable<AccessData> {
    return this.http.post(this.loginUrl, loginData, { responseType: 'text' }).pipe(
      map((token: string) => {
        const user = this.jwtHelper.decodeToken(token); // Decode the token to get user info
        const accessData: AccessData = { user, token };
        this.authSubject.next(user);
        localStorage.setItem('accessData', JSON.stringify(accessData));
        this.autoLogout(token);
        return accessData;
      }),
      tap({
        next: data => console.log('Login successful:', data),
        error: err => {
          console.error('Login error:', err);
        }
      })
    );
  }
  
  

  logout() {
    this.authSubject.next(null);
    localStorage.removeItem('accessData');
    this.router.navigate(['/auth/login']);
  }

  getAccessToken(): string {
    const userJson = localStorage.getItem('accessData');
    if (!userJson) return '';

    const accessData: AccessData = JSON.parse(userJson);
    if (this.jwtHelper.isTokenExpired(accessData.token)) return '';

    return accessData.token;
  }

  autoLogout(jwt: string) {
    console.log('JWT Token:', jwt);
    const decodedToken = this.jwtHelper.decodeToken(jwt);
    console.log('Decoded Token:', decodedToken);
    const expDate = this.jwtHelper.getTokenExpirationDate(jwt);
    if (!expDate) {
      console.error('Expiration date is null. Decoded Token:', decodedToken);
      return;
    }
    const expMs = expDate.getTime() - new Date().getTime();
    setTimeout(() => {
      this.logout();
    }, expMs);
  }

  restoreUser() {
    const userJson = localStorage.getItem('accessData');
    console.log('Restored access data:', userJson);

    if (!userJson) return;
    const accessData: AccessData = JSON.parse(userJson);
    console.log('Parsed access data:', accessData);
    if (this.jwtHelper.isTokenExpired(accessData.token)) {
      console.log('Token is expired');
      return;
    }
    this.authSubject.next(accessData.user);
    this.autoLogout(accessData.token);
  }

  errors(err: any) {
    switch (err.error) {
      case "Email and Password are required":
        return new Error('Email e password obbligatorie');
      case "Email already exists":
        return new Error('Utente esistente');
      case 'Email format is invalid':
        return new Error('Email scritta male');
      case 'Cannot find user':
        return new Error('Utente inesistente');
      default:
        return new Error('Errore');
    }
  }

  getUserProfile(): Utente | null {
    const userJson = localStorage.getItem('accessData');
    if (!userJson) return null;

    const accessData: AccessData = JSON.parse(userJson);
    return accessData.user;
  }

  updateLocalUser(user: Utente) {
    const accessData = JSON.parse(localStorage.getItem('accessData') || '{}');
    accessData.user = user;
    localStorage.setItem('accessData', JSON.stringify(accessData));
    this.authSubject.next(user);
  }
}

