import { Component, OnInit } from '@angular/core';
import { CartItemModel } from 'src/app/models/cart-item.model';
import { CartService } from 'src/app/services/cart.service';
import { Router } from '@angular/router';
import { ProductModel } from 'src/app/models/product.model';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-product-cart-page',
  templateUrl: './product-cart-page.component.html',
  styleUrls: ['./product-cart-page.component.css']
})
export class ProductCartPageComponent implements OnInit {

  items: CartItemModel[] = [];

  constructor(
    private cartService: CartService,
    private router: Router,
  ) { }

  ngOnInit(): void {
    const cartItems = this.cartService.getCart();
    this.items = cartItems.items;
  }

  total(): number {
    return this.cartService.total();
  }

  updateCartItens(items: CartItemModel[]): void {
    this.items = items;
  }

  handleProductPage(): void {
    this.router.navigateByUrl('/products-page');
  }

  isEmpty(): boolean {
    return (this.items.length === 0);
  }

  checkout(): void {
    this.router.navigateByUrl('/order-confirmation');
  }

}
