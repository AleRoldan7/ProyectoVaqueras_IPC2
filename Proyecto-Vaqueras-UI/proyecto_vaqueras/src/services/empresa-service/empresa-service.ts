import { Injectable } from '@angular/core';
import { RestConstants } from '../../shared/rest-api-const';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class EmpresaService {
  
  restConstants = new RestConstants();

  constructor(private httpCliente : HttpClient) {}

  crearEmpresa(data : any) : Observable<any> {
    return this.httpCliente.post<any>(`${this.restConstants.getApiURL()}empresa/crear-empresa`, data);
  }

  getNombreEmpresa(idEmpresa: number): Observable<{ nombreEmpresa: string }> {
    return this.httpCliente.get<{ nombreEmpresa: string }>(`${this.restConstants.getApiURL()}empresa/nombre/${idEmpresa}`);
  }
  
}
