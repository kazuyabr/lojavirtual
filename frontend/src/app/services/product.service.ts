import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ProductModel } from '../models/product.model';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(
    private http: HttpClient,
  ) { }

  findById(id: number): Observable<ProductModel> {
    const path = `${environment.baseURL}/produtos/${id}`;
    return this.http.get<ProductModel>(path);
  }

  findByCategoria(categoriaId: number, page: number = 0, linesPerPage: number = 10, ): Observable<ProductModel[]> {
    const path = `${environment.baseURL}/produtos/?categorias=${categoriaId}&page=${page}&linesPerPage=${linesPerPage}`;
    return this.http.get<ProductModel[]>(path);
  }

}
