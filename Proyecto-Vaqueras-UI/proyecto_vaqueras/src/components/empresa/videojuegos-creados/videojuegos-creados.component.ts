import { Component, OnInit } from '@angular/core';
import { VideojuegoService } from '../../../services/videojuego-service/videojuego-service';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import Swal from 'sweetalert2';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-videojuegos-creados',
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './videojuegos-creados.component.html',
  styleUrl: './videojuegos-creados.component.css',
})
export class VideojuegosCreadosComponent implements OnInit {

  videojuegos: any[] = [];
  idEmpresa!: number;
  videojuegoEditando: any = null;

  constructor(private videojuegoService: VideojuegoService, private sanitizer: DomSanitizer) { }

  ngOnInit(): void {
    const usuarioSesion = localStorage.getItem('usuario');

    if (usuarioSesion) {
      const usuario = JSON.parse(usuarioSesion);
      this.idEmpresa = usuario.idEmpresa;
      this.cargarVideojuegos();
    }
  }

  cargarVideojuegos(): void {

    this.videojuegoService.getVideojuegosEmpresa(this.idEmpresa)
      .subscribe(response => {

        this.videojuegos = response.videojuegos;

        this.videojuegos.forEach((juego: any) => {

          juego.imagenes = [];

          const idsImagenes = response.imagenes[juego.idVideojuego] || [];

          idsImagenes.forEach((idImg: number) => {

            this.videojuegoService.getImagenVideojuego(idImg)
              .subscribe(blob => {

                const url = URL.createObjectURL(blob);
                const safeUrl: SafeUrl =
                  this.sanitizer.bypassSecurityTrustUrl(url);

                juego.imagenes.push(safeUrl);
              });
          });
        });
      });
  }


  iniciarEdicion(juego: any): void {
    this.videojuegoEditando = { ...juego }; 
  }

  cancelarEdicion(): void {
    this.videojuegoEditando = null;
  }

  guardarEdicion(): void {
    if (!this.videojuegoEditando) return;

    this.videojuegoService.actualizarVideojuego(this.videojuegoEditando)
      .subscribe({
        next: () => {
          Swal.fire('Actualizado', 'Videojuego actualizado correctamente', 'success');
          this.videojuegoEditando = null;
          this.cargarVideojuegos();
        },
        error: err => {
          console.error(err);
          Swal.fire('Error', 'No se pudo actualizar el videojuego', 'error');
        }
      });
  }

}