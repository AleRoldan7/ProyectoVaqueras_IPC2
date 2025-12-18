import { Routes } from '@angular/router';
import { LoginComponent } from '../components/login/login.component';
import { RegistroUsuarioComponent } from '../components/usuario/registro-usuario/registro-usuario.component';
import { RegistroEmpresaComponent } from '../components/empresa/registro-empresa/registro-empresa.component';
import { CrearVideojuegoComponent } from '../components/empresa/crear-videojuego/crear-videojuego.component';
import { PaginaPrincipalEmpresaComponent } from '../pages/admin-empresa/pagina-principal-empresa.component';
import { VideojuegosCreadosComponent } from '../components/empresa/videojuegos-creados/videojuegos-creados.component';
import { CrearCategoriaComponent } from '../components/categoria/crear-categoria/crear-categoria.component';
import { PaginaPrincipalSistemaComponent } from '../pages/admin-sistema/pagina-principal-sistema.component';

export const routes: Routes = [

    {
        path: '',
        redirectTo: 'login',
        pathMatch: 'full',
    },
    {   
        path: 'login',
        component: LoginComponent
    },
    {
        path: 'registro-empresa',
        component: RegistroEmpresaComponent
    },
    {
        path: 'registro-usuario-comun',
        component: RegistroUsuarioComponent
    },
    {
        path: 'crear-videojuego',
        component: CrearVideojuegoComponent
    },
    {
        path: "videojuegos-creados",
        component: VideojuegosCreadosComponent
    },
    {
        path: 'categoria',
        component: CrearCategoriaComponent
    },
    {
        path: 'admin-empresa',
        component: PaginaPrincipalEmpresaComponent
    },
    {
        path: 'admin-sistema',
        component: PaginaPrincipalSistemaComponent
    }

];
