import { Component, OnInit } from '@angular/core';
import { BudgetService } from '../../../../../services/services/budget.service';
import { PageResponseBudgetResponse } from '../../../../../services/models/page-response-budget-response';
import { Router } from '@angular/router';
import {BudgetResponse} from '../../../../../services/models/budget-response';
import {Budget} from '@angular-devkit/build-angular';

@Component({
  selector: 'app-budget',
  templateUrl: './budget.component.html',
  styleUrls: ['./budget.component.css']
})
export class BudgetComponent implements OnInit {
  isLoading = true;
  isAdd = false;
  budgetResponse: PageResponseBudgetResponse = {};  // Store the actual budgets
  monthlyBudget: BudgetResponse = {};  // Store the actual budgets
  page = 0;
  size = 6;
  pages: any = [];
  message = '';
  level: 'success' | 'error' = 'success';
  showSuccess=false;
  isEditing = false;
  selectedBudgetId: number | undefined;

  constructor(
    private budgetService: BudgetService,
    private router: Router
  ) {}

  ngOnInit() {
    // Fetch the budgets from the backend when the component initializes
    this.findAllBudgets();
  }

   findAllBudgets(resetPage:boolean = false) {
    if (resetPage) {
      this.page = 0; // Reset to the first page
    }
    this.budgetService.findAllBudgetsByOwner({
      page: this.page,
      size: this.size
    })
      .subscribe({
        next: (budgets) => {
          // Store the backend response in budgetResponse
          this.budgetResponse = budgets;

          // Create an array of page numbers for pagination
          this.pages = Array(this.budgetResponse.totalPages)
            .fill(0)
            .map((x, i) => i);
          this.getMonthlyBudget();
          this.isLoading = false;
        },
        error: (err) => {
          console.error('Error fetching budgets:', err);
          this.message = 'An error occurred while fetching the budgets.';
          this.level = 'error';
          this.isLoading = false;
        }
      });
  }

  getMonthlyBudget(){
    this.budgetService.calculateMonthlyBudget().subscribe({
      next: (data) => {
        this.monthlyBudget = data;
      },
      error: (err) => {
        console.error('Error fetching monthly budget:', err);
      }
    });
  }


  gotToPage(page: number) {
    this.page = page;
    this.findAllBudgets();  // Fetch budgets for the selected page
  }

  goToFirstPage() {
    this.page = 0;
    this.findAllBudgets();  // Fetch budgets for the first page
  }

  goToPreviousPage() {
    this.page--;
    this.findAllBudgets();  // Fetch budgets for the previous page
  }

  goToLastPage() {
    this.page = this.budgetResponse.totalPages as number - 1;
    this.findAllBudgets();  // Fetch budgets for the last page
  }

  goToNextPage() {
    this.page++;
    this.findAllBudgets();  // Fetch budgets for the next page
  }

  get isLastPage() {
    return this.page === this.budgetResponse.totalPages as number - 1;
  }

  editBudget(budget: BudgetResponse) {
    this.router.navigate(['budget', 'create', budget.id]);
  }

  redirectCreate() {
    this.router.navigate(['budget', 'create']);
  }

  handleRefreshBudget(id: number) {
      this.budgetResponse.content = (this.budgetResponse.content ?? []).filter((budget: BudgetResponse) => budget.id !== id);
  }

  handleSuccess(): void {
    this.showSuccess = true;
    this.isEditing = false;
    setTimeout(() => {
      this.showSuccess = false;
    }, 4000);
  }

  handleCloseModal() {
    this.isAdd = false;
    this.selectedBudgetId = undefined;
  }

}
