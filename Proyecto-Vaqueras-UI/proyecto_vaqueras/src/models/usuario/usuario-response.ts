export interface UsuarioResponse {
    idUsuario: number;
    nombre: string | null;
    correo: string;
    nickname?: string | null;
    tipoUsuario: 'ADMIN_SISTEMA' | 'ADMIN_EMPRESA' | 'USUARIO_COMUN';
    dineroCartera?: number;
    fechaNacimiento: string;  
    idEmpresa?: number;

}
