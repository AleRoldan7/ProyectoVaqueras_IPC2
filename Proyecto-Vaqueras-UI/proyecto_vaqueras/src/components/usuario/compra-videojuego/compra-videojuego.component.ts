import { Component } from '@angular/core';
import { Videojuego } from '../../../models/empresa/videojuego';
import { VideojuegoService } from '../../../services/videojuego-service/videojuego-service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-compra-videojuego',
  imports: [FormsModule, CommonModule, RouterModule],
  templateUrl: './compra-videojuego.component.html',
  styleUrl: './compra-videojuego.component.css',
})
export class CompraVideojuegoComponent {

   resultados: any[] = [];
  titulo: string = '';
  categoria: string = '';
  precioMin?: number;
  precioMax?: number;
  empresa: string = '';

  constructor(private videojuegoService: VideojuegoService,
              private sanitizer: DomSanitizer) { }

  ngOnInit(): void {
    this.buscar();
  }

  buscar(): void {
    this.videojuegoService.buscarVideojuegos(
      this.titulo, this.categoria, this.precioMin, this.precioMax, this.empresa
    ).subscribe(juegos => {
      this.resultados = juegos.map(juego => ({
        ...juego,
        imagenes: juego.imagenes?.map((img: any) => this.sanitizer.bypassSecurityTrustUrl(img)) || []
      }));
    });
  }
}

