import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {ExpensesResponse} from '../../../../../services/models/expenses-response';
import {PageResponseWalletResponse} from '../../../../../services/models/page-response-wallet-response';
import {PageResponseExpensesResponse} from '../../../../../services/models/page-response-expenses-response';
import {ExpensesService} from '../../../../../services/services/expenses.service';
import {DeleteBudget1$Params} from '../../../../../services/fn/budget/delete-budget-1';
import {DeleteExpense$Params} from '../../../../../services/fn/expenses/delete-expense';
import {ExpensesCategoryResponse} from '../../../../../services/models/expenses-category-response';
import {Router} from '@angular/router';


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
  categoriesResponse: Array<ExpensesCategoryResponse> = [];
  createExpense = false;
  showSuccess = false
  isArchive = false;
  isMonthlyArchive = false;
  isSubmenuOpen = false;
  isDelete = false;
  expenseToDelete: any;


  constructor(
    private expensesService: ExpensesService,
    private router: Router,
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
          this.expensesResponse = expenses;
          this.pages = Array(this.expensesResponse.totalPages)
            .fill(0)
            .map((x, i) => i);
          this.isLoading = false;
          this.getMostSpendingCategories();
        },
        error: (err) => {
          console.error('Error fetching expenses:', err);
          this.message = 'An error occurred while fetching the expenses.';
          this.level = 'error';
          this.isLoading = false;
        }
      });
  }

  handleSuccess(): void {
    this.showSuccess = true;
    setTimeout(() => {
      this.showSuccess = false;
    }, 4000);
  }

  toggleCreateExpense() {
    this.createExpense = !this.createExpense;
  }

  get nonArchivedExpenses() {
    return this.expensesResponse.content = (this.expensesResponse.content ?? []).filter(expense => !expense.archived);
  }

  resetMonthlyExpenses() {
    this.expensesService.resetMonthlyExpenses().subscribe({
      next: (res) => {
        this.isMonthlyArchive = false;
        this.findAllExpenses();
      },
      error: (err) => {
        console.log(err.error);
        this.errorMsg = err.error.validationErrors;
      }
    });
  }

  resetAllExpenses() {
    this.expensesService.resetExpenses().subscribe({
      next: (res) => {
        this.isArchive = false;
        this.findAllExpenses();
      },
      error: (err) => {
        console.log(err.error);
        this.errorMsg = err.error.validationErrors;
      }
    });
  }

  getMostSpendingCategories() {
    this.expensesService.getTopSpendingCategories().subscribe({
      next: (data) => {
        this.categoriesResponse = data;
      },
      error: (err) => {
        console.error('Error fetching top spending categories:', err);
      }
    });
  }

  get totalExpenses(): number | undefined {
    return this.expensesResponse.content?.reduce((sum, expense) => sum + (expense.amount ?? 0), 0);
  }

  gotToPage(page: number) {
    this.page = page;
    this.findAllExpenses();
  }

  goToFirstPage() {
    this.page = 0;
    this.findAllExpenses();
  }

  goToPreviousPage() {
    this.page--;
    this.findAllExpenses();
  }

  goToLastPage() {
    this.page = this.expensesResponse.totalPages as number - 1;
    this.findAllExpenses();
  }

  goToNextPage() {
    this.page++;
    this.findAllExpenses();
  }

  get isLastPage() {
    return this.page === this.expensesResponse.totalPages as number - 1;
  }


  deleteExpense(id: any) {
    const params: DeleteExpense$Params = {'expense-id': id};
    this.expensesService.deleteExpense(params).subscribe({
      next: (res) => {
        this.isDelete = false;
        this.findAllExpenses();
      },
      error: (err) => {
        console.log(err.error);
        this.errorMsg = err.error.validationErrors;
      }
    });
  }

  openDelete(id: any) {
    this.expenseToDelete = id;
    this.isDelete = true;
  }

  seeDetail(id: number | undefined) {
    this.router.navigate(['/expense/detail', id]);
  }

}
