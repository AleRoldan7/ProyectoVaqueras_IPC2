import { Injectable } from '@angular/core';
import { RestConstants } from '../../shared/rest-api-const';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class SistemaService {

  restConstants = new RestConstants();

  constructor(private httpCliente: HttpClient) { }

  obtenerPendientes() {
    return this.httpCliente.get<any[]>(`${this.restConstants.getApiURL()}sistema/categorias/pendientes`);
  }

  aprobar(id: number) {
    return this.httpCliente.put(`${this.restConstants.getApiURL()}sistema/categorias/${id}/aprobar`, {});
  }

  rechazar(id: number) {
    return this.httpCliente.put(`${this.restConstants.getApiURL()}sistema/categorias/${id}/rechazar`, {});
  }

  getGlobal(): Observable<{ porcentaje: number }> {
    return this.httpCliente.get<{ porcentaje: number }>(`${this.restConstants.getApiURL()}sistema/global`);
  }

  updateGlobal(porcentaje: number): Observable<any> {
    return this.httpCliente.put(`${this.restConstants.getApiURL()}sistema/comision-global`, { porcentaje });
  }

  getEmpresa(idEmpresa: number): Observable<{ porcentaje: number }> {
    return this.httpCliente.get<{ porcentaje: number }>(`${this.restConstants.getApiURL()}sistema/empresa-comision/${idEmpresa}`);
  }

  updateEmpresa(idEmpresa: number, porcentaje: number): Observable<any> {
    return this.httpCliente.put(`${this.restConstants.getApiURL()}sistema/empresa-actualizar-comision/${idEmpresa}`, { porcentaje });
  }

}
