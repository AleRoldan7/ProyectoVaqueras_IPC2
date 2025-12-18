import { Injectable } from '@angular/core';
import { RestConstants } from '../../shared/rest-api-const';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class CategoriaService {

  restConstants = new RestConstants();

  constructor(private httpCliente: HttpClient) {}

  crearCategoria(data: any): Observable<any> {
    return this.httpCliente.post(`${this.restConstants.getApiURL()}categoria/crear-categoria`,data);
  }

  actualizarCategoria(data: any): Observable<any> {
    return this.httpCliente.put(`${this.restConstants.getApiURL()}categoria/actualizar-categoria`,data);
  }

  eliminarCategoria(idCategoria: number): Observable<any> {
    return this.httpCliente.delete(`${this.restConstants.getApiURL()}categoria/eliminar-categoria/${idCategoria}`);
  }

  asignarCategoriaAVideojuego(idVideojuego: number, idCategoria: number) {
    return this.httpCliente.post(`${this.restConstants.getApiURL()}categoria/videojuegos/${idVideojuego}/categorias/${idCategoria}`,{});
  }
}
