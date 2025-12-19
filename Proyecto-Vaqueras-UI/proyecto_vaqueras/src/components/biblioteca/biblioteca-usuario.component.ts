import { Component, OnInit } from '@angular/core';
import { Videojuego } from '../../models/empresa/videojuego';
import { BibliotecaService } from '../../services/biblioteca-service/biblioteca-service';
import { Biblioteca } from '../../models/usuario/biblioteca';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-biblioteca-usuario',
  imports: [FormsModule, CommonModule, RouterModule],
  templateUrl: './biblioteca-usuario.component.html',
  styleUrl: './biblioteca-usuario.component.css',
})
export class BibliotecaUsuarioComponent implements OnInit {

  biblioteca: Biblioteca[] = [];
  usuarioId!: number;
  busquedaNickname: string = '';
  bibliotecaBusqueda: Biblioteca[] = [];

  constructor(private bibliotecaService: BibliotecaService, private sanitizer: DomSanitizer) { }

  ngOnInit(): void {
    const usuario = JSON.parse(localStorage.getItem('usuario')!);
    this.usuarioId = usuario.idUsuario;
    this.cargarBiblioteca();
  }

  cargarBiblioteca(): void {
    this.bibliotecaService.obtenerBibliotecaUsuario(this.usuarioId)
      .subscribe({
        next: juegos => this.biblioteca = juegos,
        error: err => console.error('Error al obtener biblioteca', err)
      });
  }

  buscarBiblioteca(): void {
    if (this.busquedaNickname.trim()) {
      this.bibliotecaService.obtenerBibliotecaPorNickname(this.busquedaNickname.trim())
        .subscribe(data => this.bibliotecaBusqueda = data);
    } else {
      this.bibliotecaBusqueda = [];
    }
  }

  toggleInstalacion(juego: Biblioteca): void {
    if (juego.estadoInstalacion === 'NO_INSTALADO') {
      this.instalarJuego(juego);
    } else {
      this.desinstalarJuego(juego);
    }
  }

  instalarJuego(juego: Biblioteca): void {
    this.bibliotecaService.cambiarEstadoInstalacion(this.usuarioId, juego.idVideojuego, 'INSTALADO')
      .subscribe({
        next: () => {
          juego.estadoInstalacion = 'INSTALADO';
          Swal.fire({
            icon: 'success',
            title: 'Instalado',
            text: `El juego "${juego.tituloVideojuego}" se ha instalado correctamente.`,
            timer: 2000,
            showConfirmButton: false
          });
        },
        error: () => {
          Swal.fire({
            icon: 'error',
            title: 'Error',
            text: `No se pudo instalar "${juego.tituloVideojuego}".`
          });
        }
      });
  }

  desinstalarJuego(juego: Biblioteca): void {
    this.bibliotecaService.cambiarEstadoInstalacion(this.usuarioId, juego.idVideojuego, 'NO_INSTALADO')
      .subscribe({
        next: () => {
          juego.estadoInstalacion = 'NO_INSTALADO';
          Swal.fire({
            icon: 'info',
            title: 'Desinstalado',
            text: `El juego "${juego.tituloVideojuego}" se ha desinstalado.`,
            timer: 2000,
            showConfirmButton: false
          });
        },
        error: () => {
          Swal.fire({
            icon: 'error',
            title: 'Error',
            text: `No se pudo desinstalar "${juego.tituloVideojuego}".`
          });
        }
      });
  }

}