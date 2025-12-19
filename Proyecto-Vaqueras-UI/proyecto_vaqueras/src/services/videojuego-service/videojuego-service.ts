import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Videojuego } from '../../models/empresa/videojuego';
import { HttpClient } from '@angular/common/http';
import { RestConstants } from '../../shared/rest-api-const';
import { VideojuegoResponse } from '../../models/empresa/videojuego-response';

@Injectable({
  providedIn: 'root',
})
export class VideojuegoService {

  restConstants = new RestConstants();

  constructor(private httpCliente: HttpClient) { }

  crearVideojuego(videojuego: Videojuego): Observable<Videojuego> {
    return this.httpCliente.post<Videojuego>(`${this.restConstants.getApiURL()}videojuego/crear-videojuego`, videojuego);
  }

  agregarImagenesVideojuego(idVideojuego: number, imagenes: File[]) {

    const formData = new FormData();

    imagenes.forEach(archivo => {
      formData.append('imagenes', archivo);
    });

    formData.append('idVideojuego', idVideojuego.toString());

    return this.httpCliente.post(`${this.restConstants.getApiURL()}videojuego/agregar-imagen`, formData);
  }

  getVideojuegosEmpresa(idEmpresa: number) {
    return this.httpCliente.get<any>(`${this.restConstants.getApiURL()}videojuego/empresa/${idEmpresa}`);
  }

  getImagenVideojuego(idImagen: number) {
    return this.httpCliente.get(`${this.restConstants.getApiURL()}videojuego/imagen/${idImagen}`, { responseType: 'blob' });
  }

  listarVideojuegoDisponibles() {
    return this.httpCliente.get<VideojuegoResponse[]>(`${this.restConstants.getApiURL()}videojuego/disponibles`);
  }

  actualizarVideojuego(data: any): Observable<any[]> {
    return this.httpCliente.put<any[]>(`${this.restConstants.getApiURL()}videojuego/actualizar`, data);
  }

  buscarVideojuegos(titulo?: string, categoria?: string, precioMin?: number, precioMax?: number, empresa?: string): Observable<Videojuego[]> {
    let params: any = {};
    if (titulo) params.titulo = titulo;
    if (categoria) params.categoria = categoria;
    if (precioMin) params.precioMin = precioMin;
    if (precioMax) params.precioMax = precioMax;
    if (empresa) params.empresa = empresa;

    return this.httpCliente.get<Videojuego[]>(`${this.restConstants.getApiURL()}videojuego/buscar-videojuego`, { params });
  }

}
