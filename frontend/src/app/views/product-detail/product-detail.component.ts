import { Component, OnInit } from '@angular/core';
import { ProductModel } from 'src/app/models/product.model';
import { Router, ActivatedRoute } from '@angular/router';
import { ProductService } from 'src/app/services/product.service';
import { environment } from 'src/environments/environment';
import { CartService } from 'src/app/services/cart.service';
import { HandleMessageService } from 'src/app/services/handle-message.service';

@Component({
  selector: 'app-product-detail',
  templateUrl: './product-detail.component.html',
  styleUrls: ['./product-detail.component.css']
})
export class ProductDetailComponent implements OnInit {

  product: ProductModel = {
    id: null,
    nome: '',
    preco: null,
    imgPath: null,
  }

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private productService: ProductService,
    private cartService: CartService,
    private message: HandleMessageService,
  ) { }

  ngOnInit(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.productService.findById(id).subscribe({
      next: (data) => {
        this.product = data;
        this.handleProductImg();
      }
    });
  }

  handleProductImg() {
    if (this.product.imgPath !== null) {
      this.product.imgPath = `${environment.baseImageURL}/produtos/picture/${this.product.imgPath}`;
    } else {
      this.product.imgPath = null;
    }
  }

  addToCart() {
    if (this.product.id > 0) {
      this.cartService.addProduto(this.product);
      this.message.showMessage(`Produto ${this.product.nome} foi adicionado ao carrinho`);
    }
    this.router.navigateByUrl('/products-page');
  }

  voltar(): void {
    this.router.navigateByUrl('/products-page');
  }

}
