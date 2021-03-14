import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { CartItemModel } from 'src/app/models/cart-item.model';
import { CartService } from 'src/app/services/cart.service';
import { ProductModel } from 'src/app/models/product.model';

@Component({
  selector: 'app-cart-item',
  templateUrl: './cart-item.component.html',
  styleUrls: ['./cart-item.component.css']
})
export class CartItemComponent implements OnInit {

  @Input()
  item: CartItemModel;

  @Output()
  itemsEvent = new EventEmitter<CartItemModel[]>();

  constructor(
    private cartService: CartService,
  ) { }

  ngOnInit(): void {
  }

  removeItem(produto: ProductModel): void {
    this.itemsEvent.emit(this.cartService.removeProduto(produto).items);
  }

  increaseQuatity(produto: ProductModel): void {
    this.itemsEvent.emit(this.cartService.increaseQuatity(produto).items);
  }

  decreaseQuantity(produto: ProductModel): void {
    this.itemsEvent.emit(this.cartService.decreaseQuantity(produto).items);
  }

}
