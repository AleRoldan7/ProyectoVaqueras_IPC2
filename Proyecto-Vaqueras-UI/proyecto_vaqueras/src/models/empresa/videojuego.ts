export interface Videojuego {
  tituloVideojuego: string;
  descripcion: string;
  precio: number;
  recursosMinimos: string;
  clasificacionEdad: 'TODOS' | 'ADOLESCENTE' | 'ADULTO';
  idEmpresa: number;
}