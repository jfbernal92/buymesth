import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs';
import {AuthService} from "../services/auth/auth.service";

@Injectable({
    providedIn: 'root'
})
export class AuthGuard implements CanActivate {

    constructor(private authService: AuthService, private router: Router) {
    }

    canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
        return new Promise(resolve => {
            this.authService.isAdmin()
                .then((data: string[]) => {
                    resolve(data.includes("ADMIN"));
                })
                .catch(() => {
                    resolve(false);
                    this.router.navigate(['/home'])
                })
        })
    }
}
