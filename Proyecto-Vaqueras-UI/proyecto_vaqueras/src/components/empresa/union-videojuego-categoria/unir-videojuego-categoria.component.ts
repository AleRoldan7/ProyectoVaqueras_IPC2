import { Component, OnInit } from '@angular/core';
import Swal from 'sweetalert2';
import { CategoriaService } from '../../../services/categoria-service/categoria-service';
import { ListaService } from '../../../services/lista-service/lista-service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule } from '@angular/router';

@Component({
  selector: 'app-unir-videojuego-categoria',
  imports: [FormsModule, CommonModule, RouterModule],
  templateUrl: './unir-videojuego-categoria.component.html',
  styleUrl: './unir-videojuego-categoria.component.css',
})
export class UnirVideojuegoCategoriaComponent implements OnInit {

  categorias: any[] = [];
  idVideojuego!: number;
  categoriaSeleccionada: number | null = null;

  constructor(private categoriaService: CategoriaService, private listaCategorias: ListaService, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');

      if (!id) {
        Swal.fire('Error', 'ID de videojuego inválido', 'error');
        return;
      }

      this.idVideojuego = Number(id);
      console.log('ID Videojuego:', this.idVideojuego);
    });
    this.cargarCategorias();
  }

  cargarCategorias() {
    this.listaCategorias.obtenerCategorias().subscribe({
      next: (data) => {
        this.categorias = data;
        console.log('Categorías:', this.categorias);
      },
      error: () => {
        Swal.fire('Error', 'No se pudieron cargar categorías', 'error');
      }
    });
  }

  solicitarCategoria() {

    if (!this.categoriaSeleccionada) {
      Swal.fire('Aviso', 'Seleccione una categoría', 'warning');
      return;
    }

    console.log('Enviando:', {
      idVideojuego: this.idVideojuego,
      idCategoria: this.categoriaSeleccionada
    });

    this.categoriaService
      .asignarCategoriaAVideojuego(
        this.idVideojuego,
        this.categoriaSeleccionada
      )
      .subscribe({
        next: () => {
          Swal.fire(
            'Enviado',
            'La categoría fue enviada a revisión',
            'success'
          );
        },
        error: () => {
          Swal.fire(
            'Error',
            'No se pudo solicitar la categoría',
            'error'
          );
        }
      });
  }
}