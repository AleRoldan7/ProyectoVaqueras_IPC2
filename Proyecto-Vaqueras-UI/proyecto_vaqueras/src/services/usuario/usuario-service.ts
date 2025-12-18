import { Injectable } from '@angular/core';
import { RestConstants } from '../../shared/rest-api-const';
import { HttpClient } from '@angular/common/http';
import { RegistroUsuario } from '../../models/usuario/registro-comun';
import { Observable } from 'rxjs';
import { UsuarioResponse } from '../../models/usuario/usuario-response';
import { LoginUsuario } from '../../models/usuario/loginRequest';

@Injectable({
  providedIn: 'root',
})
export class UsuarioService {

  restConstants = new RestConstants();

  constructor(private httpClient: HttpClient) { }

  registrarUsuarioComun(data: RegistroUsuario): Observable<UsuarioResponse> {
    return this.httpClient.post<UsuarioResponse>(`${this.restConstants.getApiURL()}usuario/registroUsuarioComun`, data);
  }

  loginUsuario(data: LoginUsuario): Observable<UsuarioResponse> {
    return this.httpClient.post<UsuarioResponse>(`${this.restConstants.getApiURL()}usuario/login`, data);
  }


}
