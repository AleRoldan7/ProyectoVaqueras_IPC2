export interface UsuarioSesion {
  idUsuario: number;
  nombre: string;
  correo: string;
  tipoUsuario: string;
  idEmpresa?: number;
}