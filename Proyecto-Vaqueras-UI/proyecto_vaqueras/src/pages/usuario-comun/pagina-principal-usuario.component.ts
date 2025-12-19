import { Component, OnInit } from '@angular/core';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-pagina-principal-usuario',
  imports: [RouterModule],
  templateUrl: './pagina-principal-usuario.component.html',
  styleUrl: './pagina-principal-usuario.component.css',
})
export class PaginaPrincipalUsuarioComponent implements OnInit{

  nombreUsuario: string = '';

   ngOnInit() {
    const usuario = localStorage.getItem('usuario');

    if (!usuario) return;

    const user = JSON.parse(usuario);
    this.nombreUsuario = user.nombreUsuario || 'Usuario Común';
  }
}
