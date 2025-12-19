import { Component } from '@angular/core';
import { SistemaService } from '../../../services/sistema-service/sistema-service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-solicitud-categorias',
  imports: [FormsModule, CommonModule, RouterModule],
  templateUrl: './solicitud-categorias.component.html',
  styleUrl: './solicitud-categorias.component.css',
})
export class SolicitudCategoriasComponent {

  solicitudes: any[] = [];

  constructor(private sistemaService: SistemaService) { }

  ngOnInit(): void {
    this.cargar();
  }

  cargar() {
    this.sistemaService.obtenerPendientes().subscribe({
      next: data => this.solicitudes = data,
      error: () => Swal.fire('Error', 'No se pudieron cargar solicitudes', 'error')
    });
  }

  aprobar(id: number) {
    this.sistemaService.aprobar(id).subscribe({
      next: (resp: any) => {
        Swal.fire('Éxito', resp.mensaje, 'success');
        this.cargar();
      },
      error: err => {
        Swal.fire('Error', err.error?.mensaje || 'Error inesperado', 'error');
      }
    });
  }


  rechazar(id: number) {
    Swal.fire({
      title: '¿Rechazar categoría?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Rechazar'
    }).then(res => {
      if (res.isConfirmed) {
        this.sistemaService.rechazar(id).subscribe(() => {
          Swal.fire('Rechazada', '', 'success');
          this.cargar();
        });
      }
    });
  }
}