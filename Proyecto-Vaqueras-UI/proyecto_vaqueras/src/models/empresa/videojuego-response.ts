export interface VideojuegoResponse {
  idVideojuego: number;
  tituloVideojuego: string;
  descripcion: string;
  precio: number;
  recursosMinimos: string;
  clasificacionEdad: 'TODOS' | 'ADOLESCENTE' | 'ADULTO';
}
