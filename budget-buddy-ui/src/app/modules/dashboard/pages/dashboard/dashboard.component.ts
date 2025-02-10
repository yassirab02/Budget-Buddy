import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {Router} from '@angular/router';
import {UserControllerService} from '../../../../services/services/user-controller.service';
import {BudgetService} from '../../../../services/services/budget.service';
import {BudgetResponse} from '../../../../services/models/budget-response';
import {GoalResponse} from '../../../../services/models/goal-response';
import {GoalService} from '../../../../services/services/goal.service';
import {ExpensesResponse} from '../../../../services/models/expenses-response';
import {IncomeResponse} from '../../../../services/models/income-response';
import {IncomeService} from '../../../../services/services/income.service';
import {ExpensesService} from '../../../../services/services/expenses.service';
import {UserResponse} from '../../../../services/models/user-response';
import {TransactionResponse} from '../../../../services/models/transaction-response';
import {TransactionsService} from '../../../../services/services/transactions.service';
import {DebtResponse} from '../../../../services/models/debt-response';
import {DebtService} from '../../../../services/services/debt.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements OnInit {
  @ViewChild('chart') chartElement!: ElementRef;

  message = '';
  level: 'success' | 'error' = 'success';
  errorMsg: Array<string> = [];
  add = false;
  isOpen = false;
  amountToAdd: number = 0; // Variable bound to the input field
  showSuccess: boolean = false; // Controls whether to display the success message
  budgets: BudgetResponse[] = []; // Array to store fetched budgets
  isLoadingUser: boolean = true;
  isLoadingBudgets = false; // To show a loading indicator while fetching budgets
  goalResponse: GoalResponse[] = []; // Array to store fetched goals
  expenseResponse: ExpensesResponse[] = []; // Array to store fetched expenses
  incomeResponse: IncomeResponse[] = []; // Array to store fetched incomes
  transactionResponse: TransactionResponse[] = []; // Array to store fetched transactions
  debtResponse: DebtResponse[] = []; // Array to store fetched transactions
  private _user: UserResponse | undefined;  // Defining private variable with undefined initially



  constructor(
    private router: Router,
    private userService: UserControllerService,
    private budgetService: BudgetService,
    private goalService: GoalService,
    private expenseService: ExpensesService,
    private incomeService: IncomeService,
    private transactionService: TransactionsService,
    private debtService: DebtService,
  ) {
  }

  ngOnInit() {
    this.initializeDashboard(); // Call the reusable method during component initialization
    this.fetchBudgetsByOwner(); // Fetch budgets by owner
    this.findAllGoals(); // Fetch goals
    this.findAllIncome(); // Fetch income
    this.findAllExpenses(); // Fetch expenses
    this.findAllTransactions(); // Fetch transactions
    this.findAllDebts(); // Fetch debts
  }


  initializeDashboard() {
    this.isLoadingUser = true;  // Set loading to true
    this.userService.getCurrentUser().subscribe(
      (user: UserResponse) => {
        this._user = user;
        this.isLoadingUser = false; // Hide the spinner once data is loaded
      },
      (error) => {
        console.error('Error fetching user data', error);
        this.isLoadingUser = false; // Hide the spinner even if there's an error
      }
    );
  }


  fetchBudgetsByOwner() {
    this.isLoadingBudgets = true; // Show a loading indicator
    this.budgetService.findAllBudgetsByOwner({page: 0, size: 10}).subscribe(
      (response) => {
        this.budgets = response.content || []; // Store the fetched budgets
        this.isLoadingBudgets = false; // Hide the loading indicator
      },
      (error) => {
        console.error('Error fetching budgets:', error);
        this.isLoadingBudgets = false; // Hide the loading indicator on error
      }
    );
  }

  findAllTransactions() {
    this.transactionService.findAllTransactions({})
      .subscribe({
        next: (transactions) => {
          this.transactionResponse = transactions.content || [];
        },
        error: (err) => {
          console.error('Error fetching transactions:', err);
          this.message = 'An error occurred while fetching the transactions.';
          this.level = 'error';
        }
      });
  }


  addToBalance(amountToAdd: number) {
    if (amountToAdd <= 0 || amountToAdd === null) {
      return;
    }
    this.userService.addBalance({amount: amountToAdd}).subscribe(
      () => {
        this.showSuccess = true;  // Show success message
        this.amountToAdd = 0;     // Reset input field
        this.add = false;
        this.initializeDashboard(); // Refresh the dashboard
        setTimeout(() => this.showSuccess = false, 3000); // Hide success message after 3 seconds
      },
      (error) => {
        console.error('Error adding balance', error);
      }
    );
  }



  findAllGoals(): void {
    this.goalService.findAllGoalsByUser().subscribe({
      next: (goals) => {
        this.goalResponse = goals.content || [];
      },
      error: (err) => {
        console.error('Error fetching goals:', err);
        this.message = 'An error occurred while fetching the goals.';
        this.level = 'error';
      }
    });
  }

  findAllIncome(): void {
    this.incomeService.findAllIncomes().subscribe({
      next: (income) => {
        this.incomeResponse = income.content || [];
      },
      error: (err) => {
        console.error('Error fetching income:', err);
        this.message = 'An error occurred while fetching the income.';
        this.level = 'error';
      }
    });
  }

  findAllExpenses(): void {
    this.expenseService.findAllExpenses().subscribe({
      next: (expenses) => {
        this.expenseResponse = expenses.content || [];
      },
      error: (err) => {
        console.error('Error fetching expenses:', err);
        this.message = 'An error occurred while fetching the expenses.';
        this.level = 'error';
      }
    });
  }

  findAllDebts() {
    this.debtService.findAllDebtsByOwner({
    })
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
  }

  getUserInitials(): string {
    if (this.user) {
      const firstName = this.user.firstName || '';
      const lastName = this.user.lastName || '';
      return (firstName.charAt(0).toUpperCase() + lastName.charAt(0).toUpperCase());
    }
    return '';
  }


  get totalIncomes(): number | undefined {
    return this.incomeResponse?.reduce((sum, income) => sum + (income.amount ?? 0), 0);
  }

  get totalExpenses(): number | undefined {
    return this.expenseResponse?.reduce((sum, expense) => sum + (expense.amount ?? 0), 0);
  }

  get totalDebt(): number | undefined {
    return this.debtResponse?.reduce((sum, debt) => sum + (debt.amount ?? 0), 0);
  }

  cancelAdd() {
    this.add = false; // Hide the input field
    this.amountToAdd = 0; // Reset the input value
  }

  redirectToBudget() {
    this.router.navigate(['/budget']);
  }

  logout() {
    localStorage.clear(); // or localStorage.removeItem('token');
    window.location.reload();
  }

  toggleDropdown() {
    this.isOpen = !this.isOpen;
  }

  toggleAddInput() {
    this.add = !this.add;
  }
  redirectToGoals() {
    this.router.navigate(['/goal']);
  }
  viewAllTransactions() {

  }


  // Getter and Setter for user
  get user(): UserResponse | undefined {
    return this._user;
  }

  set user(value: UserResponse | undefined) {
    this._user = value;
  }

  goToProfile() {
    this.router.navigate(['/profile'], { state: { user: this.user } });
  }
  goToContact() {
    this.router.navigate(['/help'], { state: { user: this.user } });
  }

  goToSettings() {
    this.router.navigate(['/setting'], { state: { user: this.user } });
  }
}
