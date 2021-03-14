import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ProductCartPageRoutingModule } from './product-cart-page-routing.module';
import { ProductCartPageComponent } from './product-cart-page.component';
import { MenuModule } from 'src/app/components/menu/menu.module';
import { RodapeModule } from 'src/app/components/rodape/rodape.module';
import { CartItemModule } from 'src/app/components/cart-item/cart-item.module';

import { MatListModule } from '@angular/material/list';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';

@NgModule({
  declarations: [ProductCartPageComponent],
  imports: [
    CommonModule,
    ProductCartPageRoutingModule,
    MenuModule,
    RodapeModule,
    CartItemModule,
    MatListModule,
    MatButtonModule,
    MatIconModule,
  ]
})
export class ProductCartPageModule { }
