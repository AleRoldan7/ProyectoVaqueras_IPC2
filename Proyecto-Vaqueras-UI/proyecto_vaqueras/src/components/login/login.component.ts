import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { UsuarioService } from '../../services/usuario/usuario-service';
import { LoginUsuario } from '../../models/usuario/loginRequest';
import Swal from 'sweetalert2';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-login',
  imports: [FormsModule, CommonModule, RouterModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent {
  correo: string = '';
  password: string = '';

  mensaje: string = '';
  loginExitoso: boolean = false;

  constructor(private usuarioService: UsuarioService, private router: Router) { }

  login() {
    const data: LoginUsuario = {
      correo: this.correo,
      password: this.password,
    };

    this.usuarioService.loginUsuario(data).subscribe({
      next: (resp) => {

        localStorage.setItem('usuario', JSON.stringify(resp));

        switch (resp.tipoUsuario) {
          case 'USUARIO_COMUN':
            this.router.navigate(['/usuario-comun']);
            Swal.fire({
              icon: 'success',
              title: 'Información',
              text: 'Funcionalidades de usuario común aún en desarrollo.',
            });
            break;
          case 'ADMIN_EMPRESA':
            this.router.navigate(['/admin-empresa']);
              Swal.fire({
              icon: 'info',
              title: 'Información',
              text: 'Funcionalidades de admin empresa  aún en desarrollo.',
            });
            break;
          case 'ADMIN_SISTEMA':
            this.router.navigate(['/admin-sistema']);
              Swal.fire({
              icon: 'info',
              title: 'Información',
              text: 'Funcionalidades de ADMIN SISTEMA aún en desarrollo.',
            });
            break;
          default:
            Swal.fire({
              icon: 'error',
              title: 'Error',
              text: 'Tipo de usuario desconocido.',
            });
        }
        this.mensaje =
              'Login exitoso - Bienvenido ' + (resp.nickname || resp.nombre || resp.correo);

            this.loginExitoso = true;

            console.log('Usuario logueado:', resp);
            console.log('ID Empresa:', resp.idEmpresa);

        },
        error: (err) => {
          Swal.fire({
            icon: 'error',
            title: 'Error de autenticación',
            text: 'Credenciales incorrectas. Por favor, inténtelo de nuevo.',
          });
          this.mensaje = 'Credenciales incorrectas';
          this.loginExitoso = false;
          console.error(err);
        },
    });
  }
}