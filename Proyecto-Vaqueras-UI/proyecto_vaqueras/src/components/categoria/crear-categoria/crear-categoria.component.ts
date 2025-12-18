import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import Swal from 'sweetalert2';
import { CategoriaService } from '../../../services/categoria-service/categoria-service';
@Component({
  selector: 'app-crear-categoria',
  imports: [FormsModule, CommonModule, RouterModule],
  templateUrl: './crear-categoria.component.html',
  styleUrl: './crear-categoria.component.css',
})
export class CrearCategoriaComponent implements OnInit{

  categorias: any[] = [];

  nuevaCategoria = {
    nombreCategoria: '',
    descripcionCategoria: ''
  };

  categoriaEditando: any = null;

  constructor(private categoriaService: CategoriaService) {}

  ngOnInit(): void {
    
  }

  crearCategoria() {
    this.categoriaService.crearCategoria(this.nuevaCategoria).subscribe({
      next: () => {
        Swal.fire('Éxito', 'Categoría creada', 'success');
        this.nuevaCategoria = { nombreCategoria: '', descripcionCategoria: '' };
      },
      error: err => {
        Swal.fire('Error', err.error || 'No se pudo crear', 'error');
      }
    });
  }

  editarCategoria(cat: any) {
    this.categoriaEditando = { ...cat };
  }

  guardarEdicion() {
    this.categoriaService.actualizarCategoria(this.categoriaEditando).subscribe({
      next: () => {
        Swal.fire('Actualizada', 'Categoría modificada', 'success');
        this.categoriaEditando = null;
      }
    });
  }

  eliminarCategoria(idCategoria: number) {
    Swal.fire({
      title: '¿Eliminar?',
      text: 'Esta acción es lógica',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Sí'
    }).then(res => {
      if (res.isConfirmed) {
        this.categoriaService.eliminarCategoria(idCategoria).subscribe(() => {
          Swal.fire('Eliminada', '', 'success');
        });
      }
    });
  }
}
