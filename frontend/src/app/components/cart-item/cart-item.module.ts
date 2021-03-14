import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CartItemComponent } from './cart-item.component';
import { MatButtonModule } from '@angular/material/button';
import { MatListModule } from '@angular/material/list';
import { MatIconModule } from '@angular/material/icon';



@NgModule({
  declarations: [
    CartItemComponent,
  ],
  imports: [
    CommonModule,
    MatButtonModule,
    MatListModule,
    MatIconModule,
  ],
  exports: [
    CartItemComponent,
  ]
})
export class CartItemModule { }
