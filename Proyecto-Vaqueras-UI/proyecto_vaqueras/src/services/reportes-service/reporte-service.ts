import { Injectable } from '@angular/core';
import { RestConstants } from '../../shared/rest-api-const';
import { HttpClient } from '@angular/common/http';
import { GananciaGlobal } from '../../models/reportes/reporte-sistema/gananciaGlobal';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ReporteService {
  
  restConstants = new RestConstants();

  constructor(private httpClient: HttpClient) {}

  /*Url para reportes de sistema */
  reporteGananciaGlobal(data: any): Observable<any>{
    return this.httpClient.get<any>(`${this.restConstants.getApiURL()}/reportes/ganancias-globales`, data);
  }

}
