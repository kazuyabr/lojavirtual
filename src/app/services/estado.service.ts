import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { EstadoModel } from '../models/estado.model';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class EstadoService {

  constructor(
    private http: HttpClient,
  ) { }

  findAll(): Observable<EstadoModel[]> {
    const path = `${environment.baseURL}/estados`;
    return this.http.get<EstadoModel[]>(path);
  }

}
