import { Injectable } from '@angular/core';
import { RestConstants } from '../../shared/rest-api-const';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class CompraService {
  restConstants = new RestConstants();

  constructor(private httpCliente: HttpClient) { }

  comprar(idUsuario: number, idVideojuego: number, fechaCompra: string) {
    return this.httpCliente.post(`${this.restConstants.getApiURL()}compra/realizar-compra`, {
      idUsuario,
      idVideojuego,
      fechaCompra,
    });
  }
}
