import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {ExpensesResponse} from '../../../../services/models/expenses-response';
import {PageResponseWalletResponse} from '../../../../services/models/page-response-wallet-response';
import {PageResponseExpensesResponse} from '../../../../services/models/page-response-expenses-response';
import {ExpensesService} from '../../../../services/services/expenses.service';


@Component({
  selector: 'app-expense',
  templateUrl: './expense.component.html',
  styleUrl: './expense.component.css'
})
export class ExpenseComponent implements OnInit {
  isLoading: boolean = true;
  page = 0;
  size = 5;
  pages: any = [];
  message = '';
  level: 'success' | 'error' = 'success';
  errorMsg: Array<string> = [];
  expensesResponse: PageResponseExpensesResponse = {};  // Store the actual wallet
  createExpense=false;


  constructor(
    private expensesService: ExpensesService,
  ) {
  }


  ngOnInit() {
    this.findAllExpenses();
  }

  findAllExpenses(resetPage: boolean = false) {
    if (resetPage) {
      this.page = 0; // Reset to the first page
    }
    this.expensesService.findAllExpenses({
      page: this.page,
      size: this.size
    })
      .subscribe({
        next: (expenses) => {
          // Store the backend response in budgetResponse
          this.expensesResponse = expenses;
          // Create an array of page numbers for pagination
          this.pages = Array(this.expensesResponse.totalPages)
            .fill(0)
            .map((x, i) => i);
          this.isLoading = false;
        },
        error: (err) => {
          console.error('Error fetching expenses:', err);
          this.message = 'An error occurred while fetching the expenses.';
          this.level = 'error';
          this.isLoading = false;
        }
      });
  }



}
