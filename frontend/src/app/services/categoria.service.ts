import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CategoryModel } from '../models/category.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CategoriaService {

  constructor(
    private http: HttpClient
  ) { }

  public findAll(): Observable<CategoryModel[]> {
    return this.http.get<CategoryModel[]>(`${environment.baseURL}/categorias`);
  }

}
