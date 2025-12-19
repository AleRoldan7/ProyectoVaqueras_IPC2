import { Routes } from '@angular/router';
import { LoginComponent } from '../components/login/login.component';
import { RegistroUsuarioComponent } from '../components/usuario/registro-usuario/registro-usuario.component';
import { RegistroEmpresaComponent } from '../components/empresa/registro-empresa/registro-empresa.component';
import { CrearVideojuegoComponent } from '../components/empresa/crear-videojuego/crear-videojuego.component';
import { PaginaPrincipalEmpresaComponent } from '../pages/admin-empresa/pagina-principal-empresa.component';
import { VideojuegosCreadosComponent } from '../components/empresa/videojuegos-creados/videojuegos-creados.component';
import { CrearCategoriaComponent } from '../components/categoria/crear-categoria/crear-categoria.component';
import { PaginaPrincipalSistemaComponent } from '../pages/admin-sistema/pagina-principal-sistema.component';
import { authGuard as autoGuard } from '../guards/auth-guard';
import { ActualizarEliminarCategoriaComponent } from '../components/categoria/actualizar-eliminar-categoria/actualizar-eliminar-categoria.component';
import { PaginaPrincipalUsuarioComponent } from '../pages/usuario-comun/pagina-principal-usuario.component';
import { UnirVideojuegoCategoriaComponent } from '../components/empresa/union-videojuego-categoria/unir-videojuego-categoria.component';
import { SolicitudCategoriasComponent } from '../components/sistema/solicitud-categorias/solicitud-categorias.component';
import { TiendaVideojuegosComponent } from '../components/tienda/tienda-videojuegos.component';
import { ActualizarEmpresaComponent } from '../components/empresa/actualizar-empresa/actualizar-empresa.component';
import { CrearGrupoComponent } from '../components/grupo-familiar/crear-grupo/crear-grupo.component';
import { ListaGruposComponent } from '../components/grupo-familiar/lista-grupos/lista-grupos.component';
import { InvitacionGrupoComponent } from '../components/grupo-familiar/invitaciones-grupo/invitacion-grupo.component';
import { ListarUsuariosComponent } from '../components/sistema/listar-usuario/listar-usuarios.component';
import { BibliotecaUsuarioComponent } from '../components/biblioteca/biblioteca-usuario.component';
import { CompraVideojuegoComponent } from '../components/usuario/compra-videojuego/compra-videojuego.component';
import { PorcentajeComisionComponent } from '../components/comisones/porcentaje-comision.component';

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
        component: RegistroEmpresaComponent,
        canActivate: [autoGuard],
        data: { roles: ['ADMIN_SISTEMA'] }
    },
    {
        path: 'registro-usuario-comun',
        component: RegistroUsuarioComponent

    },
    {
        path: 'crear-videojuego',
        component: CrearVideojuegoComponent,
        canActivate: [autoGuard],
        data: { roles: ['ADMIN_EMPRESA'] }
    },
    {
        path: "videojuegos-creados",
        component: VideojuegosCreadosComponent,

    },
    {
        path: 'categoria',
        component: CrearCategoriaComponent,

    },
    {
        path: 'admin-empresa',
        component: PaginaPrincipalEmpresaComponent,

    },
    {
        path: 'admin-sistema',
        component: PaginaPrincipalSistemaComponent,

    },
    {
        path: 'usuario-comun',
        component: PaginaPrincipalUsuarioComponent
    },
    {
        path: 'categoria-editar-eliminar',
        component: ActualizarEliminarCategoriaComponent,

    },
    {
        path: 'empresa/videojuego/:id/categorias',
        component: UnirVideojuegoCategoriaComponent,
        title: 'Agregar Categoria a Videojuego'
    },
    {
        path: 'solictudes-pendientes',
        component: SolicitudCategoriasComponent,
        title: 'Solicitudes de Categorías Pendientes'
    },
    {
        path: 'tienda-videojuegos',
        component: TiendaVideojuegosComponent,
        title: 'Tienda de Videojuegos'
    },
    {
        path: 'actualizar-empresa',
        component: ActualizarEmpresaComponent
    },
    {
        path: 'crear-grupo-familiar',
        component: CrearGrupoComponent
    },
    {
        path: 'listar-grupos-familiares',
        component: ListaGruposComponent
    },
    {
        path: 'invitaciones-grupo',
        component: InvitacionGrupoComponent
    },
    {
        path: 'lista-usuario',
        component: ListarUsuariosComponent
    },
    {
        path: 'biblioteca-usuario',
        component: BibliotecaUsuarioComponent
    },
    {
        path: 'buscar-videojuego',
        component: CompraVideojuegoComponent
    },
    {
        path: 'comision',
        component: PorcentajeComisionComponent
    }

];
