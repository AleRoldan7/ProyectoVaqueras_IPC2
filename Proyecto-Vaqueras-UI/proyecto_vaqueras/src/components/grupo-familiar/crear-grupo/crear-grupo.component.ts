import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { RouterModule } from '@angular/router';
import Swal from 'sweetalert2';

import { GrupoFamiliarService } from '../../../services/grupo-service/grupo-familiar-service';
@Component({
  selector: 'app-crear-grupo',
  imports: [FormsModule, CommonModule, RouterModule, ReactiveFormsModule],
  templateUrl: './crear-grupo.component.html',
  styleUrl: './crear-grupo.component.css',
})
export class CrearGrupoComponent {

  grupoForm: FormGroup;

  constructor(private fb: FormBuilder, private grupoService: GrupoFamiliarService) {
    this.grupoForm = this.fb.group({
      nombreGrupo: ['', Validators.required]
    });
  }

  crearGrupo(): void {
    if (this.grupoForm.valid) {
      const usuario = JSON.parse(localStorage.getItem('usuario')!);

      const nombre = this.grupoForm.value.nombreGrupo;
      const idUsuarioDueño = usuario.idUsuario; 

      this.grupoService.crearGrupo(nombre, idUsuarioDueño).subscribe({
        next: () => 
          alert('Grupo creado'),
        error: err => console.error(err)
      });
    }
  }

}
