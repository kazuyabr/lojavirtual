import { ProductModel } from './product.model';

export interface CartItemModel {
  quatidade: number;
  produto: ProductModel;
}