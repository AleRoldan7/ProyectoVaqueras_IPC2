export interface LoginResponse {
    idUsuario: number;
    correo: string;
    tipoUsuario: string;
    nickname?: string;
    nombre?: string;
    dineroCartera: number;
    idEmpresa?: number | null;
}