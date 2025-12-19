import { Injectable } from '@angular/core';
import { RestConstants } from '../../shared/rest-api-const';
import { HttpClient } from '@angular/common/http';
import { GrupoFamiliar, InvitacionGrupo } from '../../models/usuario/grupo-familiar';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class GrupoFamiliarService {
  
  restConstants = new RestConstants();

  constructor(private httpCliente : HttpClient) {}

   listarGrupos(): Observable<GrupoFamiliar[]> {
    return this.httpCliente.get<GrupoFamiliar[]>(`${this.restConstants.getApiURL()}grupo-familiar/listar`);
  }

  crearGrupo(nombreGrupo: string, idUsuarioDueño: number): Observable<GrupoFamiliar> {
    return this.httpCliente.post<GrupoFamiliar>(`${this.restConstants.getApiURL()}grupo-familiar/crear`, {
      nombreGrupo,
      idUsuarioDueño
    });
  }

  actualizarGrupo(idGrupo: number, nombreNuevo: string): Observable<any> {
  return this.httpCliente.put<any>(
    `${this.restConstants.getApiURL()}grupo-familiar/actualizar/${idGrupo}`,
    { nombreNuevo }
  );
}

  eliminarGrupo(idGrupo: number): Observable<any> {
    return this.httpCliente.delete(`${this.restConstants.getApiURL()}grupo-familiar/eliminar/${idGrupo}`);
  }

  agregarUsuario(idGrupo: number, idUsuario: number): Observable<any> {
    return this.httpCliente.post(`${this.restConstants.getApiURL()}grupo-familiar/agregar-usuario?idGrupo=${idGrupo}&idUsuario=${idUsuario}`, {});
  }

  eliminarUsuario(idGrupo: number, idUsuario: number): Observable<any> {
    return this.httpCliente.delete(`${this.restConstants.getApiURL()}grupo-familiar/eliminar-usuario?idGrupo=${idGrupo}&idUsuario=${idUsuario}`);
  }

  enviarInvitacion(idGrupo: number, idUsuario: number): Observable<any> {
    return this.httpCliente.post(`${this.restConstants.getApiURL()}grupo-familiar/invitar-usuario`, { idGrupo, idUsuario });
  }

  responderInvitacion(idGrupo: number, idUsuario: number, respuesta: 'ACEPTADA' | 'RECHAZADA'): Observable<any> {
    return this.httpCliente.post(`${this.restConstants.getApiURL()}grupo-familiar/responder-invitacion`, { idGrupo, idUsuario, respuesta });
  }

  obtenerInvitacionesPendientes(idUsuario: number): Observable<InvitacionGrupo[]> {
    return this.httpCliente.get<InvitacionGrupo[]>(`${this.restConstants.getApiURL()}grupo-familiar/invitaciones-pendientes/${idUsuario}`);
  }
}
