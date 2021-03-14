import { Injectable } from '@angular/core';
import { LocalUser } from '../models/local-user.model';
import { STORAGE_KEYS } from '../config/storage-keys.config';
import { CartModel } from '../models/cart.model';

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  constructor() { }

  getLocalUser(): LocalUser {
    const userToken = localStorage.getItem(STORAGE_KEYS.localUser);
    return (userToken === null) ? null : JSON.parse(userToken);
  }

  setLocalUser(userToken: LocalUser): void {
    if (userToken === null) {
      localStorage.removeItem(STORAGE_KEYS.localUser);
    } else {
      localStorage.setItem(STORAGE_KEYS.localUser, JSON.stringify(userToken));
    }
  }

  getCart(): CartModel {
    const cart = localStorage.getItem(STORAGE_KEYS.cart);
    return (cart !== null) ? JSON.parse(cart) : null;
  }

  setCart(cart: CartModel) {
    if (cart !== null) {
      localStorage.setItem(STORAGE_KEYS.cart, JSON.stringify(cart));
    } else {
      localStorage.removeItem(STORAGE_KEYS.cart);
    }
  }
}
