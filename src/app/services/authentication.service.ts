import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse, HttpEvent } from '@angular/common/http';
import { LoginModel } from '../models/login.model';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { LocalUser } from '../models/local-user.model';
import { StorageService } from './storage.service';

import { JwtHelperService } from '@auth0/angular-jwt';
import { CartService } from './cart.service';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private jwtHelper: JwtHelperService;

  constructor(
    private http: HttpClient,
    private storage: StorageService,
    private cartService: CartService,
  ) {
    this.jwtHelper = new JwtHelperService();
  }

  authenticate(loginCreds: LoginModel): Observable<HttpResponse<void>> {
    const path = `${environment.baseURL}/login`;
    return this.http.post<void>(path, loginCreds, {
      observe: 'response',
    });
  }

  refreshToken() {
    const path = `${environment.baseURL}/auth/refresh_token`;
    return this.http.post(path, {}, { 
      observe: 'response', 
      responseType: 'text' 
    });
  }

  successfullLogin(authorization: string): void {
    const token = authorization.substring(7); // Remove o 'Bearer '
    const user: LocalUser = {
      token,
      email: this.jwtHelper.decodeToken(token).sub,
    };
    
    this.storage.setLocalUser(user);
    this.cartService.createOrClearCart();
  }

  logout(): void {
    this.storage.setLocalUser(null);
  }
}
