import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ExpensesRequest} from '../../../../../services/models/expenses-request';
import {CategoryService} from '../../../../../services/services/category.service';
import {ExpensesService} from '../../../../../services/services/expenses.service';
import {WalletService} from '../../../../../services/services/wallet.service';
import {BudgetService} from '../../../../../services/services/budget.service';
import {WalletResponse} from '../../../../../services/models/wallet-response';
import {BudgetResponse} from '../../../../../services/models/budget-response';
import {ExpensesCategoryResponse} from '../../../../../services/models/expenses-category-response';


@Component({
  selector: 'app-expense-create',
  templateUrl: './expense-create.component.html',
  styleUrl: './expense-create.component.css'
})
export class ExpenseCreateComponent implements OnInit {
  @Output() closeModal = new EventEmitter<void>();
  @Output() expenseCreated = new EventEmitter<void>(); // Emits an event when a new budget is created
  @Output() showSuccess = new EventEmitter<void>();


  expenseForm: FormGroup;
  date: Date | null = null;
  errorMsg: Array<string> = [];
  expensesCategory: Array<ExpensesCategoryResponse> = [];
  wallets: WalletResponse[] = [];
  budgets: BudgetResponse[] = [];
  expenseRequest: ExpensesRequest = {
    name: '',
    amount: 0,
    description: '',
    date: '',
    expensesType: 'FIXED',
    categoryId: 0,
    budgetId: 0,
    walletId: 0
  };
  constructor(
    private fb: FormBuilder,
    private categoryService: CategoryService,
    private expenseService: ExpensesService,
    private walletService: WalletService,
    private budgetService: BudgetService,
  ) {
    this.expenseForm = this.fb.group({
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


  saveExpense() {
    this.expenseService.addOrUpdateExpense({
      body: this.expenseRequest
    }).subscribe({
      next: (expenseId) => {
        // Set isAdd to false after successful save
        this.closeModal.emit();
        this.expenseForm.reset();
        this.showSuccess.emit();
        this.expenseCreated.emit(); // Emit the event to notify parent component
      },
      error: (err) => {
        console.log(err.error);
        this.errorMsg = err.error.validationErrors;
      }
    });
  }


  fetchingUserInfo() {
    this.categoryService.getExpensesCategory().subscribe({
      next: (categories) => {
        this.expensesCategory = categories;
      },
      error: (err) => {
        console.error('Error fetching categories:', err);
      }
    });

    this.budgetService.findAllBudgetsByOwner().subscribe({
      next: (budgets) => {
        if (budgets.content) {
          this.budgets = budgets.content;
        } else {
          this.budgets = [];
        }
      },
      error: (err) => {
        console.error('Error fetching budgets:', err);
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
