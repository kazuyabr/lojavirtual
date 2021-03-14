import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ProductCartPageComponent } from './product-cart-page.component';

const routes: Routes = [{ path: '', component: ProductCartPageComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProductCartPageRoutingModule { }
