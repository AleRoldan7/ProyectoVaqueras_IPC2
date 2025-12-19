import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import Swal from 'sweetalert2';
import { Videojuego } from '../../models/empresa/videojuego';
import { CompraService } from '../../services/compra-service/compra-service';
import { VideojuegoService } from '../../services/videojuego-service/videojuego-service';
import { VideojuegoResponse } from '../../models/empresa/videojuego-response';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-tienda-videojuegos',
  imports: [CommonModule, RouterModule],
  templateUrl: './tienda-videojuegos.component.html',
  styleUrl: './tienda-videojuegos.component.css',
})
export class TiendaVideojuegosComponent {

  videojuegos: any[] = [];
  idUsuario!: number;

  constructor(
    private videojuegoService: VideojuegoService,
    private compraService: CompraService,
    private sanitizer: DomSanitizer
  ) { }

  ngOnInit(): void {
    const usuarioSesion = localStorage.getItem('usuario');

    if (usuarioSesion) {
      const usuario = JSON.parse(usuarioSesion);
      this.idUsuario = usuario.idUsuario;
      this.cargar();
    }
  }

  cargar() {
    this.videojuegoService.listarVideojuegoDisponibles()
      .subscribe(juegos => {
        this.videojuegos = juegos;
        this.cargarImagenes();
      });
  }

  cargarImagenes() {
    this.videojuegos.forEach(juego => {
      juego.imagenes = [];

      this.videojuegoService.getImagenVideojuego(juego.idVideojuego)
        .subscribe((imagenes: Blob) => {
          const url = URL.createObjectURL(imagenes);
          const safeUrl = this.sanitizer.bypassSecurityTrustUrl(url);

          juego.imagenes.push(safeUrl);
        });
    });
  }
  comprarVideojuego(idVideojuego: number) {

    Swal.fire({
      title: 'Fecha de compra',
      input: 'date',
      inputLabel: 'Seleccione la fecha',
      inputValue: new Date().toISOString().split('T')[0],
      showCancelButton: true,
      confirmButtonText: 'Comprar'
    }).then(result => {

      if (result.isConfirmed && result.value) {

        this.compraService
          .comprar(this.idUsuario, idVideojuego, result.value)
          .subscribe({
            next: () => {
              Swal.fire('Éxito', 'Compra realizada correctamente', 'success');
            },
            error: err => {
              Swal.fire(
                'Error',
                err.error?.mensaje || 'No se pudo realizar la compra',
                'error'
              );
            }
          });
      }
    });
  }
}

