import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {TransactionResponse} from '../../../../services/models/transaction-response';
import {animate, style, transition, trigger} from '@angular/animations';
import {TransactionsService} from '../../../../services/services/transactions.service';
import {PageResponseTransactionResponse} from '../../../../services/models/page-response-transaction-response';

@Component({
  selector: 'app-transaction',
  templateUrl: './transaction.component.html',
  styleUrl: './transaction.component.css',
  animations: [
    trigger('fadeIn', [
      transition(':enter', [
        style({ opacity: 0 }),
        animate('300ms', style({ opacity: 1 }))
      ]),
      transition(':leave', [
        animate('300ms', style({ opacity: 0 }))
      ])
    ])
  ]
})
export class TransactionComponent implements OnInit{
  isLoading: boolean = true;
  page = 0;
  size = 6;
  pages: any = [];
  message = '';
  level: 'success' | 'error' = 'success';
  errorMsg: Array<string> = [];
  showSuccess=false ;
  currentView = 'Grid';
  views = ['Grid', 'List'];
  createTransfer=false;
  transactionResponse: PageResponseTransactionResponse = {};

  constructor(private transactionService:TransactionsService,private router:Router) {
  }

  ngOnInit(): void {
    this.findAllTransactions();
  }


  findAllTransactions(resetPage: boolean = false) {
    if (resetPage) {
      this.page = 0; // Reset to the first page
    }
      this.page = 0; // Reset to the first page
    this.transactionService.findAllTransactions({
      page: this.page,
      size: this.size
    })
      .subscribe({
        next: (transactions) => {
          this.transactionResponse = transactions;
          this.pages = Array(this.transactionResponse.totalPages)
            .fill(0)
            .map((x, i) => i);
          this.isLoading = false;
        },
        error: (err) => {
          console.error('Error fetching transactions:', err);
          this.message = 'An error occurred while fetching the transactions.';
          this.level = 'error';
          this.isLoading = false;
        }
      });
  }

  handleSuccess(): void {
    this.showSuccess = true;
    setTimeout(() => {
    this.findAllTransactions();
      this.showSuccess = false;
    }, 4000);
  }

  gotToPage(page: number) {
    this.page = page;
    this.findAllTransactions();
  }

  goToFirstPage() {
    this.page = 0;
    this.findAllTransactions();
  }

  goToPreviousPage() {
    this.page --;
    this.findAllTransactions();
  }

  goToLastPage() {
    this.page = this.transactionResponse.totalPages as number - 1;
    this.findAllTransactions();
  }

  goToNextPage() {
    this.page++;
    this.findAllTransactions();
  }

  get isLastPage() {
    return this.page === this.transactionResponse.totalPages as number - 1;
  }

}
