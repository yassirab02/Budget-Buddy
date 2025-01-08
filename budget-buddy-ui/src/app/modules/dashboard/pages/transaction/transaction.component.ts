import { Component } from '@angular/core';
import {Router} from '@angular/router';

@Component({
  selector: 'app-transaction',
  templateUrl: './transaction.component.html',
  styleUrl: './transaction.component.css'
})
export class TransactionComponent {
  constructor(
    private router: Router,
  ) {
  }

  isOpen: boolean = true;

  redirectDash() {
    this.router.navigate(['dashboard']);
  }
}
