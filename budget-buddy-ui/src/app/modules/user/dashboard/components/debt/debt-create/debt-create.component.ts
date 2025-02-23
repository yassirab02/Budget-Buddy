import {Component, EventEmitter, Output} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {IncomeSourceResponse} from '../../../../../../services/models/income-source-response';
import {WalletResponse} from '../../../../../../services/models/wallet-response';
import {IncomeRequest} from '../../../../../../services/models/income-request';
import {CategoryService} from '../../../../../../services/services/category.service';
import {IncomeService} from '../../../../../../services/services/income.service';
import {WalletService} from '../../../../../../services/services/wallet.service';
import {DebtRequest} from '../../../../../../services/models/debt-request';
import {DebtService} from '../../../../../../services/services/debt.service';

@Component({
  selector: 'app-debt-create',
  templateUrl: './debt-create.component.html',
  styleUrl: './debt-create.component.css'
})
export class DebtCreateComponent {
  @Output() closeModal = new EventEmitter<void>();
  @Output() debtCreated = new EventEmitter<void>(); // Emits an event when a new budget is created
  @Output() showSuccess = new EventEmitter<unknown>();

  debtForm: FormGroup;
  date: Date | null = null;
  errorMsg: Array<string> = [];
  wallets: WalletResponse[] = [];
  debtRequest: DebtRequest = {
    name: '',
    amount: 0,
    description: '',
    dueDate: '',
    issueDate: '',
    type: 'PERSONAL_LOAN', // or any other type like 'MORTGAGE', etc.
    isPaid: false,
  };

  constructor(
    private fb: FormBuilder,
    private debtService: DebtService,
  ) {
    this.debtForm = this.fb.group({
      name: ['', Validators.required],
      amount: ['', [Validators.required, Validators.min(0.01)]],
      description: [''],
      dueDate: [''],
      type: ['PERSONAL_LOAN'],
    });
  }

  ngOnInit() {
  }


  saveDebt() {
    this.debtService.addOrUpdateDebt({
      body: this.debtRequest
    }).subscribe({
      next: (expenseId) => {
        this.closeModal.emit();
        this.debtForm.reset();
        this.showSuccess.emit();
        this.debtCreated.emit(); // Emit the event to notify parent component
      },
      error: (err) => {
        console.log(err.error);
        this.errorMsg = err.error.validationErrors;
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
