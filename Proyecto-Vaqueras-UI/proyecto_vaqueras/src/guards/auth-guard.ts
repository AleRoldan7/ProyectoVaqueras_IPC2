import { inject, Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, GuardResult, MaybeAsync, Router, RouterStateSnapshot } from '@angular/router';

@Injectable({
  providedIn: 'root'
})

export class authGuard implements CanActivate {

  constructor(private router: Router) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    const usuarioData = localStorage.getItem('usuario') ? JSON.parse(localStorage.getItem('usuario')!) : null;
    if (!usuarioData) {
      this.router.navigate(['/login']);
      return false;
    }


    const rol = usuarioData.tipoUsuario;
    const allowedRoles = route.data['roles'] as string[];

    if (!allowedRoles.includes(rol)) {
      console.warn(`Acceso denegado: rol ${rol} no permitido`);
      this.router.navigate(['/login']);
      return false;
    }

    console.log(`Acceso permitido para el rol: ${rol}`);
    return true;
  }

}
