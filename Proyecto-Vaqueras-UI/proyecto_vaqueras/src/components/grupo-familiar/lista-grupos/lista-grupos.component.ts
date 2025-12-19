import { Component, OnInit } from '@angular/core';
import { GrupoFamiliar } from '../../../models/usuario/grupo-familiar';
import { GrupoFamiliarService } from '../../../services/grupo-service/grupo-familiar-service';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-lista-grupos',
  imports: [FormsModule, CommonModule, RouterModule, ReactiveFormsModule],
  templateUrl: './lista-grupos.component.html',
  styleUrl: './lista-grupos.component.css',
})
export class ListaGruposComponent implements OnInit {

 grupos: GrupoFamiliar[] = [];
  grupoEditando: GrupoFamiliar | null = null;
  grupoForm!: FormGroup;
  usuarioLogueado: any;

  constructor(private grupoService: GrupoFamiliarService, private fb: FormBuilder) { }

  ngOnInit(): void {
    this.usuarioLogueado = JSON.parse(localStorage.getItem('usuario')!);
    this.cargarGrupos();

    this.grupoForm = this.fb.group({
      nombreGrupo: ['', Validators.required]
    });
  }

  cargarGrupos(): void {
    this.grupoService.listarGrupos().subscribe({
      next: data => this.grupos = data,
      error: err => console.error(err)
    });
  }

  eliminarGrupo(idGrupo: number): void {
    if (confirm('¿Desea eliminar este grupo?')) {
      this.grupoService.eliminarGrupo(idGrupo).subscribe({
        next: () => this.cargarGrupos(),
        error: err => console.error(err)
      });
    }
  }

  editarGrupo(grupo: GrupoFamiliar): void {
    this.grupoEditando = grupo;
    this.grupoForm.patchValue({ nombreGrupo: grupo.nombreGrupo });
  }

  actualizarGrupo(): void {
    if (this.grupoEditando && this.grupoForm.valid) {
      const nombreNuevo = this.grupoForm.value.nombreGrupo;
      this.grupoService.actualizarGrupo(this.grupoEditando.idGrupo, nombreNuevo).subscribe({
        next: () => {
          alert('Grupo actualizado');
          this.grupoEditando = null;
          this.cargarGrupos();
        },
        error: err => console.error(err)
      });
    }
  }

  cancelarEdicion(): void {
    this.grupoEditando = null;
  }

  enviarInvitacion(idGrupo: number, idUsuario: number): void {
    this.grupoService.enviarInvitacion(idGrupo, idUsuario).subscribe({
      next: () => alert('Invitación enviada'),
      error: err => console.error(err)
    });
  }

}
