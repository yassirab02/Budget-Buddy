import {Component, OnInit} from '@angular/core';
import {PageResponseIncomeResponse} from '../../../../services/models/page-response-income-response';
import {IncomeService} from '../../../../services/services/income.service';
import {PageResponseDebtResponse} from '../../../../services/models/page-response-debt-response';
import {DebtService} from '../../../../services/services/debt.service';

@Component({
  selector: 'app-debt',
  templateUrl: './debt.component.html',
  styleUrl: './debt.component.css'
})
export class DebtComponent implements OnInit {

  isLoading: boolean = true;
  page = 0;
  size = 5;
  pages: any = [];
  message = '';
  level: 'success' | 'error' = 'success';
  errorMsg: Array<string> = [];
  createDebt = false;
  debtResponse: PageResponseDebtResponse = {};  // Store the actual wallet

  constructor(
    private debtService: DebtService,
  ) {
  }

  ngOnInit() {
    this.findAllDebts();
  }

  findAllDebts(resetPage: boolean = false) {
    if (resetPage) {
      this.page = 0; // Reset to the first page
    }
    this.debtService.findAllDebtsByOwner({
      page: this.page,
      size: this.size
    })
      .subscribe({
        next: (debts) => {
          this.debtResponse = debts;
          this.pages = Array(this.debtResponse.totalPages)
            .fill(0)
            .map((x, i) => i);
          this.isLoading = false;
        },
        error: (err) => {
          console.error('Error fetching debts:', err);
          this.message = 'An error occurred while fetching the debts.';
          this.level = 'error';
          this.isLoading = false;
        }
      });
  }

  get totalDebt(): number | undefined {
    return this.debtResponse.content?.reduce((sum, debt) => sum + (debt.amount ?? 0), 0);
  }

}
