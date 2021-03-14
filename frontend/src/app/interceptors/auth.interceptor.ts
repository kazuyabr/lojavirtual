import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HTTP_INTERCEPTORS } from '@angular/common/http';
import { Observable } from 'rxjs';
import { StorageService } from '../services/storage.service';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthInterceptor implements HttpInterceptor {

  constructor(
    private storage: StorageService,
  ) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const localUser = this.storage.getLocalUser();
    const baseUrlLength = environment.baseURL.length;
    const requestToApi = (req.url.substring(0, baseUrlLength) === environment.baseURL);

    if (localUser && requestToApi) {
      const authReq = req.clone({
        headers: req.headers.set('Authorization', 'Bearer ' + localUser.token),
      });

      return next.handle(authReq);
    } else {
      return next.handle(req);
    }
  }

}

export const AuthInterceptorProvider = {
  provide: HTTP_INTERCEPTORS,
  useClass: AuthInterceptor,
  multi: true,
}