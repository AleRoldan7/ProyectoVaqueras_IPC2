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
  
  listarEmpresas(): Observable<any[]> {
    return this.httpCliente.get<any[]>(`${this.restConstants.getApiURL()}empresa/listar`);
  }

  actualizarEmpresa(empresa: any): Observable<any> {
    return this.httpCliente.put(`${this.restConstants.getApiURL()}empresa/actualizar`, empresa);
  }


  deshabilitarComentario(idComentario: number): Observable<any> {
    return this.httpCliente.post(`${this.restConstants.getApiURL()}empresa/deshabilitar/${idComentario}`, {});
  }
}
