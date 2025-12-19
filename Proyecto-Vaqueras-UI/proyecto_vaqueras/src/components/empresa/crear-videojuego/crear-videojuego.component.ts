import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { Videojuego } from '../../../models/empresa/videojuego';
import { VideojuegoService } from '../../../services/videojuego-service/videojuego-service';
import Swal from 'sweetalert2';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-crear-videojuego',
  standalone: true,
  imports: [FormsModule, CommonModule, RouterModule],
  templateUrl: './crear-videojuego.component.html',
  styleUrls: ['./crear-videojuego.component.css'],
})
export class CrearVideojuegoComponent implements OnInit {

  videojuego: Videojuego = {
    tituloVideojuego: '',
    descripcion: '',
    precio: 0,
    recursosMinimos: '',
    clasificacionEdad: 'TODOS',
    idEmpresa: 0,
  };

  videojuegoCreado = false;
  idVideojuegoCreado!: number;
  imagenesSeleccionadas: File[] = [];
  imagenesPreview: string[] = [];


  constructor(private videojuegoService: VideojuegoService, private router: Router) { }


  ngOnInit() {
    const usuarioSesion = localStorage.getItem('usuario');

    if (!usuarioSesion) {
      Swal.fire('Error', 'No hay sesión activa', 'error');
      return;
    }

    const usuario = JSON.parse(usuarioSesion);

    if (!usuario.idEmpresa) {
      Swal.fire(
        'Error',
        'Este administrador no tiene empresa asignada',
        'error'
      );
      return;
    }

    this.videojuego.idEmpresa = usuario.idEmpresa;
  }

  crearVideojuego() {

    if (!this.videojuego.tituloVideojuego ||
      !this.videojuego.descripcion ||
      !this.videojuego.recursosMinimos ||
      this.videojuego.precio <= 0) {
      Swal.fire('Error', 'Complete todos los campos', 'error');
      return;
    }

    this.videojuegoService.crearVideojuego(this.videojuego).subscribe({
      next: (response: any) => {

        this.idVideojuegoCreado = response.idVideojuego;
        this.videojuegoCreado = true;

        Swal.fire('Éxito', 'Videojuego creado', 'success');
      },
      error: () => {
        Swal.fire('Error', 'No se pudo crear el videojuego ya existe o no cumple los requisitos', 'error');
      }
    });
  }


  onFilesSelected(event: any) {

    this.imagenesSeleccionadas = Array.from(event.target.files);
    this.imagenesPreview = [];

    this.imagenesSeleccionadas.forEach(file => {
      const reader = new FileReader();
      reader.onload = () => {
        this.imagenesPreview.push(reader.result as string);
      };
      reader.readAsDataURL(file);
    });
  }


  subirImagenes() {

    if (this.imagenesSeleccionadas.length === 0) {
      Swal.fire('Aviso', 'Seleccione imágenes', 'warning');
      return;
    }

    this.videojuegoService
      .agregarImagenesVideojuego(this.idVideojuegoCreado, this.imagenesSeleccionadas)
      .subscribe({
        next: () => {
          Swal.fire('Éxito', 'Imágenes subidas', 'success');
          this.imagenesSeleccionadas = [];
          this.router.navigate([
            '/empresa/videojuego',
            this.idVideojuegoCreado,
            'categorias'
          ]);
        },
        error: () => {
          Swal.fire('Error', 'No se pudieron subir', 'error');
        }
      });
  }



  limpiarFormulario() {
    this.videojuego = {
      tituloVideojuego: '',
      descripcion: '',
      precio: 0,
      recursosMinimos: '',
      clasificacionEdad: 'TODOS',
      idEmpresa: this.videojuego.idEmpresa
    };
  }
}