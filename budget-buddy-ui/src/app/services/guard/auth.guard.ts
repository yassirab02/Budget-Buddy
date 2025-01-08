import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { TokenService } from '../token/token.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private router: Router, private tokenService: TokenService) { }

  canActivate(): boolean {
    if (this.tokenService.isTokenNotValid()) {
      this.router.navigate(['login']);
      return false;
    }
    return true;
  }
}
