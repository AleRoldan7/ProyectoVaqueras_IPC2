import { Component } from '@angular/core';
import { UsuarioService } from '../../../services/usuario/usuario-service';
import { Router, RouterModule } from '@angular/router';
import Swal from 'sweetalert2';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-registro-usuario',
  imports: [FormsModule, CommonModule, RouterModule],
  templateUrl: './registro-usuario.component.html',
  styleUrl: './registro-usuario.component.css',
})
export class RegistroUsuarioComponent {

  nombre: string = '';
  correo: string = '';
  password: string = '';
  tipoUsuario = 'USUARIO_COMUN';
  fechaNacimiento: string = '';
  nickname: string = '';
  numeroTelefono: string = '';
  pais: string = '';
  cargando: boolean = false;

  constructor(private registroNormal: UsuarioService, private router: Router) { }

  registroUsuarioComun() {
    if (this.cargando) return;
    this.cargando = true;

    const usuarioData = {
      nombre: this.nombre,
      correo: this.correo,
      password: this.password,
      tipoUsuario: 'USUARIO_COMUN' as const,
      fechaNacimiento: this.fechaNacimiento,
      nickname: this.nickname,
      numeroTelefono: this.numeroTelefono,
      pais: this.pais,
    };

    this.registroNormal.registrarUsuarioComun(usuarioData).subscribe({
      next: () => {
        Swal.fire('Éxito', 'Usuario registrado correctamente.', 'success');
        this.router.navigate(['/login']);
      },
      error: (err) => {
        Swal.fire('Error', 'Hubo un problema al registrar el usuario.', 'error');
        console.error('Error al registrar usuario', err);
        alert('Hubo un problema al registrar el usuario.');
      },
    });
  }
}
