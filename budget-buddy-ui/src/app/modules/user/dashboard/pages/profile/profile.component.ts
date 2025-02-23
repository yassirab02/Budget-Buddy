import {Component, OnInit} from '@angular/core';
import {UserResponse} from '../../../../../services/models/user-response';
import {Router} from '@angular/router';
import {ExpensesService} from '../../../../../services/services/expenses.service';
import {ExpensesCategoryResponse} from '../../../../../services/models/expenses-category-response';
import {BudgetService} from '../../../../../services/services/budget.service';
import {BudgetResponse} from '../../../../../services/models/budget-response';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent implements OnInit {
  userResponse:UserResponse = {};
  categoriesResponse:Array<ExpensesCategoryResponse>= [];
  budgetResponse:BudgetResponse = {};
  isLoading = true;

  constructor(private router:Router,
              private expenseService:ExpensesService,
              private budgetService:BudgetService) {
    const navigation = window.history.state;
    this.userResponse = navigation.user || {};
    this.isLoading = false;
  }

  ngOnInit() {
    this.getMostSpendingCategories();
    this.getMonthlyBudget();
  }

  getUserInitials(): string {
    if (this.userResponse) {
      const firstName = this.userResponse.firstName || '';
      const lastName = this.userResponse.lastName || '';
      return (firstName.charAt(0).toUpperCase() + lastName.charAt(0).toUpperCase());
    }
    return '';
  }

  getMostSpendingCategories(){
    this.expenseService.getTopSpendingCategories().subscribe({
      next: (data) => {
        this.categoriesResponse = data;
      },
      error: (err) => {
        console.error('Error fetching top spending categories:', err);
      }
    });
  }

  getMonthlyBudget(){
    this.budgetService.calculateMonthlyBudget().subscribe({
      next: (data) => {
        this.budgetResponse = data;
      },
      error: (err) => {
        console.error('Error fetching monthly budget:', err);
      }
    });
  }
}
