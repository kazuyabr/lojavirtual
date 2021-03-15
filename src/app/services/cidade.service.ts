import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { CidadeModel } from '../models/cidade.model';
import { HttpClient } from '@angular/common/http';
import { StorageService } from './storage.service';

@Injectable({
  providedIn: 'root'
})
export class CidadeService {
  
  constructor(
    private http: HttpClient,
  ) { }

  findAll(estadoId: number): Observable<CidadeModel[]> {
    const path = `${environment.baseURL}/estados/${estadoId}/cidades`;
    return this.http.get<CidadeModel[]>(path);
  }

}
