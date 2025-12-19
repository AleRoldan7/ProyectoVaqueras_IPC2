import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import Swal from 'sweetalert2';
import { ListaService } from '../../../services/lista-service/lista-service';
import { EmpresaService } from '../../../services/empresa-service/empresa-service';
import { RouterModule } from '@angular/router';


@Component({
  selector: 'app-registro-empresa',
  imports: [FormsModule, CommonModule, RouterModule],
  templateUrl: './registro-empresa.component.html',
  styleUrl: './registro-empresa.component.css',
})
export class RegistroEmpresaComponent implements OnInit {

  admins: any[] = [];
  nombreEmpresa: string = '';
  descripcionEmpresa: string = '';
  adminSeleccionado: any = null;
  paisEmpresa: string = '';

  constructor(private lista: ListaService, private empresaService: EmpresaService) { }

  ngOnInit(): void {
    this.cargarAdmins();
  }

  cargarAdmins() {
    this.lista.obtenerAdminEmpresa().subscribe({
      next: (admins) => {
        this.admins = Array.isArray(admins) ? admins : [admins];
      },
      error: (err) => {
        Swal.fire('Error', 'No se pudieron cargar los administradores.', 'error');
        console.error('Error al cargar administradores', err);
      },
    });
  }

  seleccionarAdmin(admin: any) {
    this.adminSeleccionado = admin;
  }

  asignarEmpresa() {
    if (!this.adminSeleccionado || !this.nombreEmpresa) {
      Swal.fire('Error', 'Por favor complete todos los campos.', 'error');
      return;
    }


    const empresaData = {
      nombreEmpresa: this.nombreEmpresa,
      descripcionEmpresa: this.descripcionEmpresa,
      idUsuario: this.adminSeleccionado.idUsuario,
      paisEmpresa: this.paisEmpresa
    };

    this.empresaService.crearEmpresa(empresaData).subscribe({
      next: () => {
        Swal.fire('Éxito', 'Empresa registrada correctamente.', 'success');
        this.nombreEmpresa = '';
        this.descripcionEmpresa = '';
        this.adminSeleccionado = null;
        this.paisEmpresa = '';
      },
      error: (err) => {
        console.error('Error al registrar la empresa', err);
        Swal.fire('Error', 'Hubo un problema al registrar la empresa.', 'error');
        console.log(empresaData);
      },
    });
  }
}
