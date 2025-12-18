import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { EmpresaService } from '../../services/empresa-service/empresa-service';

@Component({
  selector: 'app-pagina-principal-empresa',
  imports: [RouterLink],
  templateUrl: './pagina-principal-empresa.component.html',
  styleUrl: './pagina-principal-empresa.component.css',
})
export class PaginaPrincipalEmpresaComponent implements OnInit {

  nombreEmpresa: string = '';

  constructor(private empresaService: EmpresaService) { }

  ngOnInit() {
    const usuario = localStorage.getItem('usuario');

    if (!usuario) return;

    const user = JSON.parse(usuario);

    if (!user.idEmpresa) return;

    this.empresaService.getNombreEmpresa(user.idEmpresa).subscribe({
      next: (res) => this.nombreEmpresa = res.nombreEmpresa,
      error: () => this.nombreEmpresa = 'Empresa'
    });
  }


}
