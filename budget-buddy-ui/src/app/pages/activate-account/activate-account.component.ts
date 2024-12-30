import { Component } from '@angular/core';
import {Router} from '@angular/router';
import {AuthenticationService} from '../../services/services/authentication.service';

@Component({
  selector: 'app-activate-account',
  templateUrl: './activate-account.component.html',
  styleUrl: './activate-account.component.scss'
})
export class ActivateAccountComponent {
  message = ''; // to hold all the messages ( activation, errors)
  isOkay = true;
  submitted = false; // if the code is already submitted or not

  constructor(
    private router: Router,
    private authService: AuthenticationService,
  ) {}

  onCodeCompleted(token:string) {
    this.confirmAccount(token);
  }

  redirectToLogin() {
    this.router.navigate(['login']);
  }

  private confirmAccount(token: string) {
    this.authService.confirm({
      token
    }).subscribe({
      next:() =>{
        this.message = 'Your account has been activated!\n Now you can proceed to login.'
        this.submitted = true;
        this.isOkay = true;
      },
      error:() =>{
        this.message = 'Token has been expired or invalid!'
        this.submitted = true;
        this.isOkay = false;
      }
    });
  }
}
