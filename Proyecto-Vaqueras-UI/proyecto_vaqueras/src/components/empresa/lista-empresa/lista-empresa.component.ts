import { Component, OnInit } from '@angular/core';
import { EmpresaService } from '../../../services/empresa-service/empresa-service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-lista-empresa',
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './lista-empresa.component.html',
  styleUrl: './lista-empresa.component.css',
})
export class ListaEmpresaComponent implements OnInit{

  empresas: any[] = [];

  constructor(private empresaService: EmpresaService) {}

  ngOnInit(): void {
    this.cargarEmpresas();
  }

  cargarEmpresas(): void {
    this.empresaService.listarEmpresas().subscribe({
      next: (data) => this.empresas = data,
      error: (err) => console.error(err)
    });
  }

}
