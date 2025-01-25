import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {WalletResponse} from '../../../../../services/models/wallet-response';
import {CategoryService} from '../../../../../services/services/category.service';
import {WalletService} from '../../../../../services/services/wallet.service';
import {IncomeRequest} from '../../../../../services/models/income-request';
import {IncomeSourceResponse} from '../../../../../services/models/income-source-response';
import {IncomeService} from '../../../../../services/services/income.service';

@Component({
  selector: 'app-income-create',
  templateUrl: './income-create.component.html',
  styleUrl: './income-create.component.css'
})
export class IncomeCreateComponent implements OnInit{
  @Output() closeModal = new EventEmitter<void>();
  @Output() incomeCreated = new EventEmitter<void>(); // Emits an event when a new budget is created

  incomeForm: FormGroup;
  date: Date | null = null;
  errorMsg: Array<string> = [];
  incomeSource: Array<IncomeSourceResponse> = [];
  wallets: WalletResponse[] = [];
  incomeRequest: IncomeRequest = {
    name: '',
    amount: 0,
    description: '',
    date: '',
    incomeSourceId: 0,
    walletId: 0
  };
  showSuccess = false;

  constructor(
    private fb: FormBuilder,
    private categoryService: CategoryService,
    private incomeService: IncomeService,
    private walletService: WalletService,
  ) {
    this.incomeForm = this.fb.group({
      name: ['', Validators.required],
      amount: ['', [Validators.required, Validators.min(0.01)]],
      description: [''],
      date: [''],
      expenseType: ['Variable'],
      categoryId: ['', Validators.required],
      budgetId: ['', Validators.required],
      walletId: [''],
    });
  }

  ngOnInit() {
    this.fetchingUserInfo();
  }


  saveIncome() {
    this.incomeService.addOrUpdateIncome({
      body: this.incomeRequest
    }).subscribe({
      next: (expenseId) => {
        this.closeModal.emit();
        this.incomeForm.reset();
        this.showSuccess = true;
        this.incomeCreated.emit(); // Emit the event to notify parent component
        setTimeout(() => {
          this.showSuccess = false;
        }, 5000);
      },
      error: (err) => {
        console.log(err.error);
        this.errorMsg = err.error.validationErrors;
      }
    });
  }


  fetchingUserInfo() {
    this.categoryService.getIncomeSources().subscribe({
      next: (sources) => {
        this.incomeSource = sources;
      },
      error: (err) => {
        console.error('Error fetching sources:', err);
      }
    });

    this.walletService.findAllWalletsByOwner().subscribe({
      next: (wallets) => {
        if (wallets.content) {
          this.wallets = wallets.content;
        } else {
          this.wallets = [];
        }
      },
      error: (err) => {
        console.error('Error fetching wallets:', err);
      }
    });
  }

  close() {
    this.closeModal.emit();
  }

  closeError(): void {
    this.errorMsg = []; // Clear the error messages
  }
}
