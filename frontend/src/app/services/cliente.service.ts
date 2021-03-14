import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { StorageService } from './storage.service';
import { ClienteModel } from '../models/cliente.model';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { ClienteCadastroModel } from '../models/cliente-cadastro.model';

@Injectable({
  providedIn: 'root'
})
export class ClienteService {

  constructor(
    private http: HttpClient,
    private storage: StorageService,
  ) {}

  findByEmail(email: string) {
    const path = `${environment.baseURL}/clientes/email?value=${email}`;
    return this.http.get(path);
  }

  getClientImage(image: string): Observable<any> {
    const path = `${environment.baseImageURL}/clientes/picture/${image}`;
    return this.http.get(path, {
      responseType: 'blob',
    });
  }

  insert(cliente: ClienteCadastroModel): Observable<void> {
    const path = `${environment.baseURL}/clientes`;
    return this.http.post<void>(path, cliente);
  }

}
