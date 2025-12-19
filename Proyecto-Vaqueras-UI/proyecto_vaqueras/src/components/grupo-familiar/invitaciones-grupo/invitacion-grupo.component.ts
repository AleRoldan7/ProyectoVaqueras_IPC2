import { Component, OnInit } from '@angular/core';
import { InvitacionGrupo } from '../../../models/usuario/grupo-familiar';
import { GrupoFamiliarService } from '../../../services/grupo-service/grupo-familiar-service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-invitacion-grupo',
  imports: [FormsModule, CommonModule, RouterModule],
  templateUrl: './invitacion-grupo.component.html',
  styleUrl: './invitacion-grupo.component.css',
})
export class InvitacionGrupoComponent implements OnInit{

  invitaciones: InvitacionGrupo[] = [];
  idUsuario = Number(localStorage.getItem('idUsuario'));

  constructor(private grupoService: GrupoFamiliarService) {}

  ngOnInit(): void {
    this.cargarInvitaciones();
  }

  cargarInvitaciones(): void {
    this.grupoService.obtenerInvitacionesPendientes(this.idUsuario)
      .subscribe(data => this.invitaciones = data);
  }

  responder(idGrupo: number, respuesta: 'ACEPTADA' | 'RECHAZADA'): void {
    this.grupoService.responderInvitacion(idGrupo, this.idUsuario, respuesta)
      .subscribe(() => this.cargarInvitaciones());
  }
}
