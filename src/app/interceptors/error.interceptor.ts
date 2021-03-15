import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HTTP_INTERCEPTORS } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { StorageService } from '../services/storage.service';
import { catchError } from 'rxjs/operators';
import { HandleMessageService } from '../services/handle-message.service';
import { FieldMessage } from '../models/field-message.model';

@Injectable({
  providedIn: 'root'
})
export class ErrorInterceptor implements HttpInterceptor {

  constructor(
    private storage: StorageService,
    private message: HandleMessageService,
  ) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req).pipe(
      catchError(data => this.handleError(data)),
    );
  }

  handleError(data) {
    let intercepError = data;

    if (intercepError.error) {
      intercepError = intercepError.error;
    }

    if (!intercepError.status) {
      intercepError = JSON.parse(intercepError);
    }

    switch (intercepError.status) {
      case 401: 
        this.handle401(data);
      break;

      case 403:
        this.handle403();
      break;

      case 422: 
        this.handle422(data);
      break;

      default: 
        this.handleDefaultError(data);
    }

    return Observable.throw(intercepError);
  }

  handle401(data): void {
    this.message.showMessage(data.error.message, true);
  }

  handle403(): void {
    this.message.showMessage('Sua sessão expirou! Faça login novamente.', true)
    this.storage.setLocalUser(null);
  }

  handle422(data): void {
    this.message.showMessage(this.handleListOfErrors(data.error.errors), true, 30000);
  }

  handleDefaultError(data): void {
    this.message.showMessage(data.error.message, true);
  }

  private handleListOfErrors(messages: FieldMessage[]): string {
    let message = '';

    for (let i = 0; i < messages.length; i++) {
      message += `${messages[i].fieldName}: ${messages[i].message} / `;
    }

    return message;
  }

}

export const ErrorInterceptorProvider = {
  provide: HTTP_INTERCEPTORS,
  useClass: ErrorInterceptor,
  multi: true,
};