import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import Swal from 'sweetalert2';
import { CategoriaService } from '../../../services/categoria-service/categoria-service';
import { ListaService } from '../../../services/lista-service/lista-service';
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

  constructor(private categoriaService: CategoriaService, private lista: ListaService) {}

  ngOnInit(): void {
    this.lista.obtenerCategorias().subscribe({
      next: (data) => {
        this.categorias = data;
      }
    });
  }

  crearCategoria() {
    this.categoriaService.crearCategoria(this.nuevaCategoria).subscribe({
      next: () => {
        Swal.fire('Éxito', 'Categoría creada', 'success');
        this.nuevaCategoria = { nombreCategoria: '', descripcionCategoria: '' };
      },
      error: err => {
        if (err.status === 409) {
          Swal.fire('Error', 'Ya existe una categoría con este nombre', 'error');
        } else if (err.status === 400) {
          Swal.fire('Error', 'Datos inválidos', 'error');
        } else {
          Swal.fire('Error', err.error?.message || 'No se pudo crear la categoría', 'error');
        }
      }
    });
  }
}
