import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { WalletService } from '../../../../../../services/services/wallet.service';
import { WalletResponse } from '../../../../../../services/models/wallet-response';
import { animate, style, transition, trigger } from '@angular/animations';
import { DebtResponse } from '../../../../../../services/models/debt-response';
import { GoalResponse } from '../../../../../../services/models/goal-response';
import { DebtService } from '../../../../../../services/services/debt.service';
import { GoalService } from '../../../../../../services/services/goal.service';
import { TransactionRequest } from '../../../../../../services/models/transaction-request';
import { TransactionsService } from '../../../../../../services/services/transactions.service';
import {UserControllerService} from '../../../../../../services/services/user-controller.service';
import {UserTransferResponse} from '../../../../../../services/models/user-transfer-response';

enum TransactionType {
  TRANSFER_TO_USER = 'Transfer to User',
  TRANSFER_TO_GOAL = 'Transfer to Goal',
  TRANSFER_TO_DEBT = 'Transfer to Debt',
  TRANSFER_TO_WALLET = 'Transfer to Wallet',
}

@Component({
  selector: 'app-transaction-create',
  templateUrl: './transaction-create.component.html',
  styleUrls: ['./transaction-create.component.css'],
  animations: [
    trigger('fadeIn', [
      transition(':enter', [
        style({ opacity: 0 }),
        animate('300ms', style({ opacity: 1 }))
      ]),
      transition(':leave', [
        animate('300ms', style({ opacity: 0 }))
      ])
    ])
  ]
})
export class TransactionCreateComponent implements OnInit {
  @Input() isOpen = false;
  @Output() closeModal = new EventEmitter<void>();
  @Output() showSuccess = new EventEmitter<void>();

  walletResponse: WalletResponse[] = [];
  debtResponse: DebtResponse[] = [];
  goalResponse: GoalResponse[] = [];
  userTransferResponse: UserTransferResponse[] = [];
  message = '';
  level: 'success' | 'error' = 'success';
  errorMsg: string[] = [];
  transferForm: FormGroup;
  walletTransfer=true;
  goalTransfer=false;
  debtTransfer=false;
  userTransfer=false;


// Original transaction types array
  transactionTypes = ['TRANSFER_TO_WALLET', 'TRANSFER_TO_GOAL', 'TRANSFER_TO_DEBT', 'TRANSFER_TO_USER'];

// Mapping object for display labels
  transactionTypeLabels: { [key: string]: string } = {
    TRANSFER_TO_WALLET: 'Transfer to Wallet',
    TRANSFER_TO_GOAL: 'Transfer to Goal',
    TRANSFER_TO_DEBT: 'Transfer to Debt',
    TRANSFER_TO_USER: 'Transfer to User'
  };
  transactionRequest: TransactionRequest = {
    amount: 0,
    description: '',
    message: '',
    transactionType: 'TRANSFER_TO_WALLET',
    goalId: 0,
    receiverId: 0,
    sourceWalletId: 0,
    destinationWalletId: 0
  };

  constructor(
    private fb: FormBuilder,
    private walletService: WalletService,
    private debtService: DebtService,
    private goalService: GoalService,
    private userService: UserControllerService,
    private transactionService: TransactionsService
  ) {
    this.transferForm = this.fb.group({
      message: ['', Validators.required],
      description: [''],
      amount: [0, [Validators.required, Validators.min(0.01)]],
      transactionType: [TransactionType.TRANSFER_TO_USER],
      destinationId: [null, Validators.required]
    });
  }

  ngOnInit() {
    this.findAllInfo();
  }

  findAllInfo() {
    this.walletService.findAllWalletsByOwner({})
      .subscribe({
        next: (wallets) => {
          this.walletResponse = wallets.content || [];
        },
        error: (err) => {
          console.error('Error fetching wallets:', err);
          this.message = 'An error occurred while fetching the wallets.';
          this.level = 'error';
        }
      });

    this.debtService.findAllNonPaidDebtsByOwner({ paidStatus: false })
      .subscribe({
        next: (debts) => {
          this.debtResponse = debts.content || [];
        },
        error: (err) => {
          console.error('Error fetching debts:', err);
          this.message = 'An error occurred while fetching the debts.';
          this.level = 'error';
        }
      });

    this.goalService.findGoalsByUserAndReachedStatus({ reached: false })
      .subscribe({
        next: (goals) => {
          this.goalResponse = goals.content || [];
        },
        error: (err) => {
          console.error('Error fetching goals:', err);
          this.message = 'An error occurred while fetching the goals.';
          this.level = 'error';
        }
      });

    this.userService.getUsersTransfer().subscribe({
      next: (users) => {
        this.userTransferResponse = users;
      },
      error: (err) => {
        console.error('Error fetching users:', err);
        this.message = 'An error occurred while fetching the users.';
        this.level = 'error';
      }
    })
  }

  saveTransaction() {
    this.transactionService.transferMoney({
      body: this.transactionRequest
    }).subscribe({
      next: () => {
        this.closeModal.emit();
        this.showSuccess.emit();
        this.transferForm.reset();
      },
      error: (err) => {
        console.error(err.error);
        this.errorMsg = err.error.validationErrors || [];
      }
    });
  }

  updateTransferTypeBooleans(event: any): void {
    const selectedType = event.target.value;
    this.transactionRequest.transactionType = selectedType;

    // You can add any logic here to toggle additional fields based on the selected type
    switch (selectedType) {
      case 'TRANSFER_TO_WALLET':
        this.walletTransfer = true;
        this.goalTransfer = false;
        this.debtTransfer = false;
        this.userTransfer = false;
        break;
      case 'TRANSFER_TO_GOAL':
        this.walletTransfer = false;
        this.goalTransfer = true;
        this.debtTransfer = false;
        this.userTransfer = false;
        break;
      case 'TRANSFER_TO_DEBT':
        this.walletTransfer = false;
        this.goalTransfer = false;
        this.debtTransfer = true;
        this.userTransfer = false;
        break;
      case 'TRANSFER_TO_USER':
        this.walletTransfer = false;
        this.goalTransfer = false;
        this.debtTransfer = false;
        this.userTransfer = true;
        break;
      default:
        this.walletTransfer = false;
        this.goalTransfer = false;
        this.debtTransfer = false;
        this.userTransfer = false;
        break;
    }
  }

  close(): void {
    this.closeModal.emit();
  }

  closeError() {
    this.errorMsg = [];
  }
}
