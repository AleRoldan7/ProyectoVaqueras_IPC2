import { Component, OnInit } from '@angular/core';
import Swal from 'sweetalert2';
import { SistemaService } from '../../services/sistema-service/sistema-service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { EmpresaService } from '../../services/empresa-service/empresa-service';
@Component({
  selector: 'app-porcentaje-comision',
  imports: [FormsModule, CommonModule, RouterModule],
  templateUrl: './porcentaje-comision.component.html',
  styleUrl: './porcentaje-comision.component.css',
})
export class PorcentajeComisionComponent implements OnInit {

  porcentajeGlobal!: number;
  empresas: any[] = [];
  porcentajeEmpresas: { [id: number]: number } = {};

  constructor(private comisionService: SistemaService, private empresaService: EmpresaService) { }

  ngOnInit(): void {
    this.cargarGlobal();
    this.cargarEmpresas();
  }

  cargarGlobal() {
    this.comisionService.getGlobal().subscribe(res => this.porcentajeGlobal = res.porcentaje);
  }

  cargarEmpresas(): void {
    this.empresaService.listarEmpresas().subscribe({
      next: (data) => {
        this.empresas = data.map(emp => ({
          idEmpresa: emp.idEmpresa,
          nombreEmpresa: emp.nombreEmpresa
        }));
      },
      error: (err) => console.error(err)
    });
  }


  actualizarGlobal() {
    if (this.porcentajeGlobal < 0 || this.porcentajeGlobal > 100) {
      Swal.fire('Error', 'El porcentaje global debe estar entre 0 y 100', 'error');
      return;
    }

    this.comisionService.updateGlobal(this.porcentajeGlobal).subscribe({
      next: () => Swal.fire('Éxito', 'Comisión global actualizada', 'success'),
      error: err => Swal.fire('Error', err.error?.error, 'error')
    });
  }

  actualizarEmpresa(idEmpresa: number) {
    const porcentaje = this.porcentajeEmpresas[idEmpresa];

    if (porcentaje < 0 || porcentaje > 100) {
      Swal.fire('Error', 'El porcentaje de la empresa debe estar entre 0 y 100', 'error');
      return;
    }

    if (porcentaje > this.porcentajeGlobal) {
      Swal.fire('Error', 'El porcentaje específico no puede ser mayor al global', 'error');
      return;
    }

    this.comisionService.updateEmpresa(idEmpresa, porcentaje).subscribe({
      next: () => Swal.fire('Éxito', 'Comisión de la empresa actualizada', 'success'),
      error: err => Swal.fire('Error', err.error?.error, 'error')
    });
  }

}
