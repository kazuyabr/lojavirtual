import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { PedidoModel } from '../models/pedido.model';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PedidoService {

  constructor(
    private http: HttpClient,
  ) { }

  insert(pedido: PedidoModel) {
    const path = `${environment.baseURL}/pedidos`;
    return this.http.post(path, pedido, {
      observe: 'response',
      responseType: 'text',
    });
  }

}
