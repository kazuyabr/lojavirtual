import { Component, OnInit, Input } from '@angular/core';
import { ProductModel } from 'src/app/models/product.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-card-product',
  templateUrl: './card-product.component.html',
  styleUrls: ['./card-product.component.css']
})
export class CardProductComponent implements OnInit {

  @Input() product: ProductModel;

  constructor(
    private router: Router,
  ) {}

  ngOnInit(): void {
  }

  productDetail(id: number) {
    this.router.navigate(['/product-detail', { 
      id,
    }]);
  }

}
