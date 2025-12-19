import { Component, OnInit } from '@angular/core';
import { UsuarioService } from '../../../services/usuario/usuario-service';
import { UsuarioResponse } from '../../../models/usuario/usuario-response';
import { FormsModule, ReactiveFormsModule, FormBuilder, FormGroup, Validators} from '@angular/forms';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import Swal from 'sweetalert2';
@Component({
  selector: 'app-listar-usuarios',
  imports: [FormsModule, CommonModule, RouterModule, ReactiveFormsModule],
  templateUrl: './listar-usuarios.component.html',
  styleUrl: './listar-usuarios.component.css',
})
export class ListarUsuariosComponent implements OnInit{

  jugadores: UsuarioResponse[] = [];
  usuarioForm!: FormGroup;
  usuarioEditando: UsuarioResponse | null = null;

  constructor(private usuarioService: UsuarioService, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.cargarJugadores();

    this.usuarioForm = this.fb.group({
      nombre: ['', Validators.required],
      correo: ['', [Validators.required, Validators.email]],
      nickname: ['', Validators.required]
     
    });
  }

  cargarJugadores(): void {
    this.usuarioService.listarJugadores().subscribe(data => this.jugadores = data);
  }

  eliminarJugador(id: number): void {
    if (confirm('¿Desea eliminar este jugador?')) {
      this.usuarioService.eliminarJugador(id).subscribe(() => this.cargarJugadores());
    }
  }

  editarJugador(usuario: UsuarioResponse): void {
    this.usuarioEditando = usuario;
    this.usuarioForm.patchValue({
      nombre: usuario.nombre,
      correo: usuario.correo,
      nickname: usuario.nickname,
      dineroCartera: usuario.dineroCartera
    });
  }

  actualizarJugador(): void {
    if (this.usuarioForm.valid && this.usuarioEditando) {
      const datosActualizados = this.usuarioForm.value;
      this.usuarioService.actualizarJugador(this.usuarioEditando.idUsuario, datosActualizados)
        .subscribe({
          next: () => {
            Swal.fire('EXITO', 'Se actualizo el usuario')
            this.cargarJugadores();
            this.usuarioEditando = null;
            this.usuarioForm.reset();
          },
          error: err => console.error(err)
        });
    }
  }

  cancelarEdicion(): void {
    this.usuarioEditando = null;
    this.usuarioForm.reset();
  }
}
