import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';


const routes: Routes = [
  {
    path: 'landing-page',
    loadChildren: () => import('./views/landing-page/landing-page.module').then(m => m.LandingPageModule)
  },
  {
    path: 'products-page',
    loadChildren: () => import('./views/products-page/products-page.module').then(m => m.ProductsPageModule)
  },
  {
    path: 'profile-page',
    loadChildren: () => import('./views/profile-page/profile-page.module').then(m => m.ProfilePageModule)
  },
  {
    path: 'signup-page',
    loadChildren: () => import('./views/signup-page/signup-page.module').then(m => m.SignupPageModule)
  },
  {
    path: 'product-detail',
    loadChildren: () => import('./views/product-detail/product-detail.module').then(m => m.ProductDetailModule)
  },
  {
    path: 'product-cart-page',
    loadChildren: () => import('./views/product-cart-page/product-cart-page.module').then(m => m.ProductCartPageModule)
  },
  {
    path: 'order-confirmation',
    loadChildren: () => import('./views/order-confirmation/order-confirmation.module').then(m => m.OrderConfirmationModule)
  },
  {
    path: '',
    redirectTo: 'landing-page',
    pathMatch: 'full'
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
