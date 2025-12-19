export interface Biblioteca {
    idVideojuego: number;
    tituloVideojuego: string;
    precio: number;
    descripcion: string;
    fechaAdquisicion: string;
    estadoInstalacion: 'INSTALADO' | 'NO_INSTALADO';
    imagenUrl?: string;
}