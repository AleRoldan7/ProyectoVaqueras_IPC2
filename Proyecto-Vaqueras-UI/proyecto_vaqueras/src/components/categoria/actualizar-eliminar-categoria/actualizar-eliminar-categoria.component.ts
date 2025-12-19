import { Component } from '@angular/core';
import { CategoriaService } from '../../../services/categoria-service/categoria-service';
import Swal from 'sweetalert2';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ListaService } from '../../../services/lista-service/lista-service';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-actualizar-eliminar-categoria',
  imports: [FormsModule, CommonModule, RouterModule],
  templateUrl: './actualizar-eliminar-categoria.component.html',
  styleUrl: './actualizar-eliminar-categoria.component.css',
})
export class ActualizarEliminarCategoriaComponent {

  categorias: any[] = [];

  mostrarFormularioEdicion = false;

  categoriaEditada = {
    idCategoria: 0,
    nombreCategoria: '',
    descripcionCategoria: ''
  };

  constructor(private categoriaService: CategoriaService, private listaCategorias: ListaService) { }

  ngOnInit(): void {
    this.cargarCategorias();
  }

  cargarCategorias() {
    this.listaCategorias.obtenerCategorias().subscribe({
      next: (cats) => {
        this.categorias = Array.isArray(cats) ? cats : [cats];
      },
      error: (err) => {
        Swal.fire('Error', 'No se pudieron cargar las categorías.', 'error');
        console.error(err);
      }
    });
  }

  editarCategoria(cat: any) {
    this.categoriaEditada = {
      idCategoria: cat.idCategoria,
      nombreCategoria: cat.nombreCategoria,
      descripcionCategoria: cat.descripcionCategoria
    };

    this.mostrarFormularioEdicion = true;
  }

  guardarEdicion() {

    if (!this.categoriaEditada.nombreCategoria.trim()) {
      Swal.fire('Error', 'El nombre de la categoría es obligatorio.', 'error');
      return;
    }

    this.categoriaService.actualizarCategoria(this.categoriaEditada).subscribe({
      next: () => {
        Swal.fire('Actualizada', 'Categoría modificada correctamente.', 'success');
        this.cancelarEdicion();
        this.cargarCategorias();
      },
      error: (err) => {
        Swal.fire(
          'Error',
          err.error?.error || 'No se pudo actualizar la categoría.',
          'error'
        );
      }
    });
  }

  cancelarEdicion() {
    this.mostrarFormularioEdicion = false;
    this.categoriaEditada = {
      idCategoria: 0,
      nombreCategoria: '',
      descripcionCategoria: ''
    };
  }

  eliminarCategoria(idCategoria: number) {
    Swal.fire({
      title: '¿Eliminar categoría?',
      text: 'Esta acción es permanente.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Sí, eliminar',
      cancelButtonText: 'Cancelar'
    }).then(result => {

      if (result.isConfirmed) {
        this.categoriaService.eliminarCategoria(idCategoria).subscribe({
          next: () => {
            Swal.fire('Eliminada', 'Categoría eliminada correctamente.', 'success');
            this.cargarCategorias();
          },
          error: () => {
            Swal.fire('Error', 'No se pudo eliminar la categoría.', 'error');
          }
        });
      }

    });
  }

}