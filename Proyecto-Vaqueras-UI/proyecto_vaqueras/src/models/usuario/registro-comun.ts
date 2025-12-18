export interface RegistroUsuario {
    correo: string;
    password: string;
    tipoUsuario: 'ADMIN_SISTEMA' | 'ADMIN_EMPRESA' | 'USUARIO_COMUN';
    nickname?: string;
    numeroTelefono?: string;
    pais?: string;
    nombre?: string;
    idEmpresa?: number;
    fechaNacimiento: string; 
}