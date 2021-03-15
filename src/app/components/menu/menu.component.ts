import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { LoginComponent } from '../login/login.component';
import { StorageService } from 'src/app/services/storage.service';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

  itensNoCarrinho: number = 0;

  constructor(
    private router: Router, 
    private loginComponent: LoginComponent,
    private storage: StorageService,
    private auth: AuthenticationService,
  ) {}

  ngOnInit(): void {
    this.itensNoCarrinho = this.storage.getCart().items.length;
  }

  handleCartPage() {
    this.router.navigateByUrl('/product-cart-page');
  }

  handleLogin() : void {
    this.loginComponent.openDialog();
  }

  handleProfilePage() {
    this.router.navigateByUrl('/profile-page');
  }

  sair(): void {
    this.auth.logout();
    this.router.navigateByUrl('/');
  }

  get isUserLogado(): boolean {
    return (this.storage.getLocalUser() === null);
  }

}
