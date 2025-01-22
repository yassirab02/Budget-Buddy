import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {WalletService} from '../../../../../services/services/wallet.service';
import {WalletResponse} from '../../../../../services/models/wallet-response';
import {IncomeSourceResponse} from '../../../../../services/models/income-source-response';

enum TransactionType {
  TRANSFER_TO_USER = 'Transfer to User',
  TRANSFER_TO_GOAL = 'Transfer to Goal',
  TRANSFER_TO_DEBT = 'Transfer to Debt',
  TRANSFER_TO_WALLET = 'Transfer to Wallet',
}

enum SourceWalletType {
  CHECKING = 'Checking',
  SAVINGS = 'Savings',
  CREDIT = 'Credit',
  INVESTMENT = 'Investment',
}
@Component({
  selector: 'app-transaction-create',
  templateUrl: './transaction-create.component.html',
  styleUrl: './transaction-create.component.css'
})
export class TransactionCreateComponent implements OnInit{
  @Input() isOpen = false;
  @Output() closeModal = new EventEmitter<void>();
  @Output() submit = new EventEmitter<any>();
  walletResponse: Array<WalletResponse> = [];
  errorMsg: Array<string> = [];
  message = '';
  level: 'success' | 'error' = 'success';


  transactionTypes = Object.keys(TransactionType) as (keyof typeof TransactionType)[];
  sourceWalletTypes = Object.keys(SourceWalletType) as (keyof typeof SourceWalletType)[];

  getTransactionLabel(key: keyof typeof TransactionType): string {
    return TransactionType[key];
  }

  getSourceWalletLabel(key: keyof typeof SourceWalletType): string {
    return SourceWalletType[key];
  }

  transferForm: FormGroup;
  selectedTransactionType: TransactionType = TransactionType.TRANSFER_TO_USER;

  constructor(private fb: FormBuilder,private walletService:WalletService) {
    this.transferForm = this.fb.group({
      transactionType: [TransactionType.TRANSFER_TO_USER, [Validators.required]],
      sourceWalletType: [SourceWalletType.CHECKING, [Validators.required]],
      message: ['', [Validators.required]],
      description: [''],
      amount: [null, [Validators.required, Validators.min(0.01)]],
      destinationId: [null, [Validators.required, Validators.min(1)]],
    });
  }

  ngOnInit(): void {
    this.transferForm.get('transactionType')?.valueChanges.subscribe((type: TransactionType) => {
      this.selectedTransactionType = type;
    });
    this.findAllWallets();
  }


  findAllWallets() {
    this.walletService.findAllWalletsByOwner({
    })
      .subscribe({
        next: (wallets) => {
          if (wallets.content) {
            this.walletResponse = wallets.content;
          } else {
            this.walletResponse = [];
          }
        },
        error: (err) => {
          console.error('Error fetching wallets:', err);
          this.message = 'An error occurred while fetching the wallets.';
          this.level = 'error';
        }
      });
  }



  getIcon(type: TransactionType): string {
    switch (type) {
      case TransactionType.TRANSFER_TO_USER:
        return 'person';
      case TransactionType.TRANSFER_TO_GOAL:
        return 'work';
      case TransactionType.TRANSFER_TO_DEBT:
        return 'credit_card';
      case TransactionType.TRANSFER_TO_WALLET:
        return 'wallet';
    }
  }

  getSourceWalletIcon(type: SourceWalletType): string {
    switch (type) {
      case SourceWalletType.CHECKING:
        return 'account_balance';
      case SourceWalletType.SAVINGS:
        return 'savings';
      case SourceWalletType.CREDIT:
        return 'credit_card';
      case SourceWalletType.INVESTMENT:
        return 'trending_up';
    }
  }

  onSubmit(): void {
    if (this.transferForm.valid) {
      this.submit.emit(this.transferForm.value);
      this.closeModal.emit();
      this.transferForm.reset({
        transactionType: TransactionType.TRANSFER_TO_USER,
        sourceWalletType: SourceWalletType.CHECKING,
      });
    }
  }

  getDestinationLabel() {
    return "";
  }
}
