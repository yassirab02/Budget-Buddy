import { Component } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-wallet',
  templateUrl: './wallet.component.html',
  styleUrl: './wallet.component.css'
})
export class WalletComponent {
  createWallet=false;
  walletForm! : FormGroup;

  constructor(private fb: FormBuilder) { }

  ngOnInit(): void {
    this.walletForm = this.fb.group({
      name: ['', [Validators.required]],
      balance: ['', [Validators.required, Validators.min(0)]],
      cardNumber: ['', [Validators.required, Validators.pattern('^\\d{4}$')]],
    });
  }

  onSubmit(): void {

  }
}
