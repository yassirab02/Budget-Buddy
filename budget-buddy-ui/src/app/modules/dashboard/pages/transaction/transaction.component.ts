import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {TransactionResponse} from '../../../../services/models/transaction-response';
import {animate, style, transition, trigger} from '@angular/animations';
import {FormControl} from '@angular/forms';
import {WalletResponse} from '../../../../services/models/wallet-response';
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
  searchTerm: string = '';
  typeFilter: string = 'ALL';
  statusFilter: string = 'ALL';
  sortConfig = { key: 'date', direction: 'desc' };
  showFilters = false;
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
      this.showSuccess = false;
    }, 4000);
  }

  toggleFilters(): void {
    this.showFilters = !this.showFilters;
  }

  requestSort(key: keyof TransactionResponse): void {
    let direction: 'asc' | 'desc' = 'asc';
    if (this.sortConfig.key === key && this.sortConfig.direction === 'asc') {
      direction = 'desc';
    }
    this.sortConfig = { key, direction };
  }

  getIconClass(transactionType: string): string {
    return transactionType === 'DEPOSIT' ? 'text-green-600' : 'text-red-600';
  }

  sort(key: keyof TransactionResponse) {
    const direction = this.sortConfig.key === key && this.sortConfig.direction === 'asc' ? 'desc' : 'asc';
    this.sortConfig = { key, direction };
  }

  getBadgeClasses(transaction: TransactionResponse): string {
    return `px-2 py-1 text-xs font-semibold rounded-full ${
      transaction.transactionType === 'DEPOSIT'
        ? 'bg-blue-100 text-blue-800'
        : 'bg-gray-100 text-gray-800'
    }`;
  }

  getStatusBadgeClasses(transaction: TransactionResponse): string {
    return `px-2 py-1 text-xs font-semibold rounded-full ${
      transaction.status === 'COMPLETED'
        ? 'bg-green-100 text-green-800'
        : 'bg-yellow-100 text-yellow-800'
    }`;
  }
}
