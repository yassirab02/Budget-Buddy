import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {BudgetService} from '../../../../services/services/budget.service';
import {PageResponseBudgetResponse} from '../../../../services/models/page-response-budget-response';
import {Router} from '@angular/router';

@Component({
  selector: 'app-budget',
  templateUrl: './budget.component.html',
  styleUrl: './budget.component.css'
})
export class BudgetComponent implements OnInit{
  isAdd = false;
  budgetResponse: PageResponseBudgetResponse = {};
  page = 0;
  size = 5;
  pages: any = [];
  message = '';
  level: 'success' |'error' = 'success';

  constructor(
    private budgetService: BudgetService,
    private router: Router,

  ) {
  }

  ngOnInit() {
    this.budgetService.findAllBudgetsByOwner();
  }

  private findAllBudgets() {
    this.budgetService.findAllBudgetsByOwner({
      page: this.page,
      size: this.size
    })
      .subscribe({
        next: (budgets) => {
          this.budgetResponse = budgets;
          this.pages = Array(this.budgetResponse.totalPages)
            .fill(0)
            .map((x, i) => i);
        }
      });
  }

  gotToPage(page: number) {
    this.page = page;
    this.findAllBudgets();
  }

  goToFirstPage() {
    this.page = 0;
    this.findAllBudgets();
  }

  goToPreviousPage() {
    this.page --;
    this.findAllBudgets();
  }


  goToLastPage() {
    this.page = this.budgetResponse.totalPages as number - 1;
    this.findAllBudgets();
  }

  goToNextPage() {
    this.page++;
    this.findAllBudgets();
  }

  get isLastPage() {
    return this.page === this.budgetResponse.totalPages as number - 1;
  }


}
