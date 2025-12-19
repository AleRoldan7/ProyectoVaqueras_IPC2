import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { EmpresaService } from '../../../services/empresa-service/empresa-service';
import { Empresa } from '../../../models/empresa/empresa';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import Swal from 'sweetalert2';
@Component({
  selector: 'app-actualizar-empresa',
  imports: [FormsModule, CommonModule, RouterModule, ReactiveFormsModule],
  templateUrl: './actualizar-empresa.component.html',
  styleUrl: './actualizar-empresa.component.css',
})
export class ActualizarEmpresaComponent implements OnInit{

  empresas: Empresa[] = [];
  empresaForm!: FormGroup;
  editandoEmpresa: Empresa | null = null;

  constructor(private empresaService: EmpresaService, private fb: FormBuilder) { }

  ngOnInit(): void {
    this.cargarEmpresas();
    this.empresaForm = this.fb.group({
      idEmpresa: [0],
      nombreEmpresa: ['', Validators.required],
      descripcionEmpresa: ['', Validators.required],
      paisEmpresa: ['', Validators.required],
      idUsuario: [0, Validators.required]
    });
  }

  cargarEmpresas(): void {
    this.empresaService.listarEmpresas().subscribe({
      next: (data) => this.empresas = data,
      error: (err) => console.error(err)
    });
  }

  editar(empresa: Empresa): void {
    this.editandoEmpresa = empresa;
    this.empresaForm.patchValue(empresa);
  }

  actualizarEmpresa(): void {
    if (this.empresaForm.valid) {
      const empresaActualizada: Empresa = this.empresaForm.value;
      this.empresaService.actualizarEmpresa(empresaActualizada).subscribe({
        next: () => {
          Swal.fire('Éxito', 'Empresa actualizada correctamente', 'success');
          this.editandoEmpresa = null;
          this.cargarEmpresas();
        },
        error: (err) => {
          Swal.fire('Error', 'No se pudo actualizar la empresa', 'error');
          console.error(err);
        }
      });
    }
  }

  cancelarEdicion(): void {
    this.editandoEmpresa = null;
  }
}
