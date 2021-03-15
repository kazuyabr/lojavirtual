import { Injectable } from '@angular/core';
import { StorageService } from './storage.service';
import { CartModel } from '../models/cart.model';
import { ProductModel } from '../models/product.model';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  constructor(
    private storage: StorageService,
  ) { }

  createOrClearCart(): CartModel {
    const cart: CartModel = { items: [] };
    this.storage.setCart(cart);
    return cart;
  }

  getCart(): CartModel {
    const cart: CartModel = this.storage.getCart();
    return (cart === null) ? this.createOrClearCart() : cart;
  }

  addProduto(produto: ProductModel): CartModel {
    const cart = this.getCart();
    const index = cart.items.findIndex(prod => prod.produto.id === produto.id); // retorna o index no array ou -1 caso não exista

    if (index === -1) {
      cart.items.push({ produto, quatidade: 1 });
    }

    this.storage.setCart(cart);
    return cart;
  }

  removeProduto(produto: ProductModel): CartModel {
    const cart = this.getCart();
    const index = cart.items.findIndex(item => item.produto.id === produto.id);

    if (index !== -1) {
      cart.items.splice(index, 1); // caso tenha o produto na lista, ele será cortado, ou seja, removido
    }
    
    this.storage.setCart(cart);
    return cart;
  }

  increaseQuatity(produto: ProductModel): CartModel {
    const cart = this.getCart();
    const index = cart.items.findIndex(item => item.produto.id === produto.id);

    if (index !== -1) {
      cart.items[index].quatidade++;
    }

    this.storage.setCart(cart);
    return cart;
  }

  decreaseQuantity(produto: ProductModel): CartModel {
    let cart = this.getCart();
    const index = cart.items.findIndex(item => item.produto.id === produto.id);

    if (index !== -1) {
      cart.items[index].quatidade--;

      if (cart.items[index].quatidade < 1) {
        cart = this.removeProduto(produto);
      }
    }

    this.storage.setCart(cart);
    return cart;
  }

  total(): number {
    const cart = this.getCart();
    let total = 0;

    for (let i = 0; i < cart.items.length; i++) {
      total += (cart.items[i].produto.preco * cart.items[i].quatidade);
    }

    return total;
  }

}
