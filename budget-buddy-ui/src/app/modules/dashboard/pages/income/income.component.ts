import {Component, OnInit} from '@angular/core';
import {IncomeResponse} from '../../../../services/models/income-response';
import {IncomeService} from '../../../../services/services/income.service';
import {PageResponseIncomeResponse} from '../../../../services/models/page-response-income-response';
import {PageResponseExpensesResponse} from '../../../../services/models/page-response-expenses-response';
import {findAllExpenses} from '../../../../services/fn/expenses/find-all-expenses';

@Component({
  selector: 'app-income',
  templateUrl: './income.component.html',
  styleUrl: './income.component.css'
})
export class IncomeComponent implements OnInit {
  isLoading: boolean = true;
  page = 0;
  size = 5;
  pages: any = [];
  message = '';
  level: 'success' | 'error' = 'success';
  errorMsg: Array<string> = [];
  createIncome=false;
  incomeResponse: PageResponseIncomeResponse = {};  // Store the actual wallet

  constructor(
    private incomeService:IncomeService,
  ) {
  }
  ngOnInit() {
    this.findAllIncomes();
  }

  findAllIncomes(resetPage: boolean = false) {
    if (resetPage) {
      this.page = 0; // Reset to the first page
    }
    this.incomeService.findAllIncomes({
      page: this.page,
      size: this.size
    })
      .subscribe({
        next: (incomes) => {
          this.incomeResponse = incomes;
          this.pages = Array(this.incomeResponse.totalPages)
            .fill(0)
            .map((x, i) => i);
          this.isLoading = false;
        },
        error: (err) => {
          console.error('Error fetching incomes:', err);
          this.message = 'An error occurred while fetching the incomes.';
          this.level = 'error';
          this.isLoading = false;
        }
      });
  }

  get totalIncome(): number | undefined {
    return this.incomeResponse.content?.reduce((sum, income) => sum + (income.amount ?? 0), 0);
  }

}
