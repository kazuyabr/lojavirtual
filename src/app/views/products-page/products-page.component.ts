import { Component, OnInit, EventEmitter } from '@angular/core';
import { ProductModel } from 'src/app/models/product.model';
import { CategoriaService } from 'src/app/services/categoria.service';
import { CategoryModel } from 'src/app/models/category.model';
import { HandleMessageService } from 'src/app/services/handle-message.service';
import { ProductService } from 'src/app/services/product.service';
import { MatTabChangeEvent } from '@angular/material/tabs';
import { environment } from 'src/environments/environment';
import { PageEvent } from '@angular/material/paginator';

@Component({
  selector: 'app-products-page',
  templateUrl: './products-page.component.html',
  styleUrls: ['./products-page.component.css']
})
export class ProductsPageComponent implements OnInit {

  categories: CategoryModel[];

  products: ProductModel[] = [];

  page: number = 0;
  linesPerPage: number = 10;

  constructor(
    private categoriaService: CategoriaService,
    private handleMessage: HandleMessageService,
    private productService: ProductService,
  ) { }

  ngOnInit(): void {
    this.handleCategories();
  }

  handleCategories(): void {
    this.categoriaService.findAll().subscribe({
      next: (data) => {
        this.categories = data;
      },

      error: () => this.handleMessage.showMessage('Erro ao carregar as categorias', true)
    });
  }

  handleProductByCategory(event: EventEmitter<MatTabChangeEvent>): void {
    const categoriaId = (event['index'] + 1);
    this.handleProducts(categoriaId, this.page, this.linesPerPage);
  }

  handleProductImg() {
    this.products.forEach(product => {
      product.imgPath = (product.imgPath === null) ? null : `${environment.baseImageURL}/produtos/picture/${product.imgPath}`;
    });
  }

  handlePageChange(event: PageEvent) {
    this.page = event.pageIndex;
    this.linesPerPage = event.pageSize;

    this.handleProducts((event.pageIndex + 1), this.page, this.linesPerPage);
  }

  handleProducts(categoriaId: number, page: number, linesPerPage: number): void {
    this.productService.findByCategoria(categoriaId, page, linesPerPage).subscribe({
      next: (data) => {
        this.products = data['content'];
        this.handleProductImg();
      }
    });
  }

}
