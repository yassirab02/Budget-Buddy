import {Component} from '@angular/core';
import {AuthenticationRequest} from '../../services/models/authentication-request';
import {AuthenticationService} from '../../services/services/authentication.service';
import {Router} from '@angular/router';
import {TokenService} from '../../services/token/token.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  authRequest: AuthenticationRequest = {email: '', password: ''};
  errorMsg: Array<string> = [];
  passwordVisible = false;

  togglePasswordVisibility() {
    this.passwordVisible = !this.passwordVisible;
  }

  constructor(
    private router: Router,
    private authService: AuthenticationService,
    private tokenService: TokenService,
  ) {
  }

  login() {
    this.errorMsg = []; // to always clear the error that displays in the first time
    this.authService.authenticate({
      body: this.authRequest
    }).subscribe({
      next: (res) => {
        //save the token
        this.tokenService.token = res.token as string;
        this.router.navigate(['dashboard']);
      },
      error: (err) => {
        console.log(err);
        if (err.error.validationErrors) {
          this.errorMsg = err.error.validationErrors;
        } else {
          this.errorMsg.push(err.error.error);
        }
      }
    });

  }

  register() {
    this.router.navigate(['sign-up']);
  }

  closeError(): void {
    this.errorMsg = []; // Clear the error messages
  }
}
