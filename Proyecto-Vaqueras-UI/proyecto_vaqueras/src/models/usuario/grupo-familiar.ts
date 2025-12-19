import { RegistroUsuario } from "./registro-comun";

export interface GrupoFamiliar {
  idGrupo: number;
  nombreGrupo: string;
  idUsuarioDue: number;
  miembros?: RegistroUsuario[];
}

export interface InvitacionGrupo {
  idGrupo: number;
  nombreGrupo: string;
  idUsuario: number;
  estado: 'PENDIENTE' | 'ACEPTADA' | 'RECHAZADA';
}
