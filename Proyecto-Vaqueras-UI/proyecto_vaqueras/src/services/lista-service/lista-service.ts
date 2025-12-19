import { Injectable } from '@angular/core';
import { RestConstants } from '../../shared/rest-api-const';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ListaService {

  restConst = new RestConstants();

  constructor(private httpCliente: HttpClient) { }

  obtenerAdminEmpresa(): Observable<any[]> {
    return this.httpCliente.get<any[]>(`${this.restConst.getApiURL()}lista/admin-empresa`);
  }

  obtenerCategorias(): Observable<any[]> {
    return this.httpCliente.get<any[]>(`${this.restConst.getApiURL()}lista/categorias`);
  }
}
