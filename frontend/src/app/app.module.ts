import { BrowserModule } from '@angular/platform-browser';
import { NgModule, LOCALE_ID } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { MenuComponent } from './components/menu/menu.component';
import { LoginComponent } from './components/login/login.component';
import { RodapeComponent } from './components/rodape/rodape.component';
import { CardProductComponent } from './components/card-product/card-product.component';

import { HttpClientModule } from '@angular/common/http';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { JwtModule } from '@auth0/angular-jwt';
import { AuthInterceptorProvider } from './interceptors/auth.interceptor';
import { ErrorInterceptorProvider } from './interceptors/error.interceptor';
import { registerLocaleData } from '@angular/common';
import LOCALE_BR from '@angular/common/locales/pt';
import { CartItemComponent } from './components/cart-item/cart-item.component';

registerLocaleData(LOCALE_BR);

@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    MatSnackBarModule,
    JwtModule,
  ],
  providers: [
    { provide: LOCALE_ID, useValue: 'pt-BR' },
    ErrorInterceptorProvider,
    AuthInterceptorProvider,
    MenuComponent,
    RodapeComponent,
    LoginComponent,
    CardProductComponent,
    CartItemComponent,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
