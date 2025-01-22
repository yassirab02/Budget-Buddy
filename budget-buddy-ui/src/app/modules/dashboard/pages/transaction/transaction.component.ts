import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {TransactionResponse} from '../../../../services/models/transaction-response';
import {animate, style, transition, trigger} from '@angular/animations';
import {FormControl} from '@angular/forms';
import {WalletResponse} from '../../../../services/models/wallet-response';

@Component({
  selector: 'app-transaction',
  templateUrl: './transaction.component.html',
  styleUrl: './transaction.component.css',
  animations: [
    trigger('fadeIn', [
      transition(':enter', [
        style({ opacity: 0, transform: 'translateY(-20px)' }),
        animate('0.5s', style({ opacity: 1, transform: 'translateY(0)' })),
      ]),
      transition(':leave', [
        style({ opacity: 1, transform: 'translateY(0)' }),
        animate('0.3s', style({ opacity: 0, transform: 'translateY(-20px)' })),
      ]),
    ]),
  ],
})
export class TransactionComponent implements OnInit{
  searchTerm: string = '';
  typeFilter: string = 'ALL';
  statusFilter: string = 'ALL';
  sortConfig = { key: 'date', direction: 'desc' };
  showFilters = false;
  currentView = 'Grid';
  views = ['Grid', 'List'];
  createTransfer=false;
  mockTransactions: TransactionResponse[] = [
    {
      id: 1,
      message: "Salary deposit",
      description: "Monthly salary",
      amount: 5000,
      date: "2023-05-01",
      transactionType: "DEPOSIT",
      status: "COMPLETED",
      sourceWallet: "Employer",
      destinationWallet: "Main Account",
      sender: "ABC Company",
      receiver: "John Doe",
      goalName: ""
    },
    {
      id: 2,
      message: "Rent payment",
      description: "Monthly rent",
      amount: 1200,
      date: "2023-05-05",
      transactionType: "WITHDRAWAL",
      status: "FAILED",
      sourceWallet: "Main Account",
      destinationWallet: "Landlord",
      sender: "John Doe",
      receiver: "Property Management LLC",
      goalName: ""
    },
    {
      id: 3,
      message: "Savings transfer",
      description: "Transfer to savings account",
      amount: 500,
      date: "2023-05-10",
      transactionType: "TRANSFER",
      status: "COMPLETED",
      sourceWallet: "Main Account",
      destinationWallet: "Savings Account",
      sender: "John Doe",
      receiver: "John Doe",
      goalName: "Emergency Fund"
    }
    // Add other mock transactions
  ];
  filteredAndSortedTransactions: TransactionResponse[] = [];

  searchControl: FormControl = new FormControl('');

  ngOnInit(): void {
    this.filteredAndSortedTransactions = [...this.mockTransactions];
    this.searchControl.valueChanges.subscribe((value: string) => {
      this.searchTerm = value;
      this.applyFiltersAndSorting();
    });
  }

  applyFiltersAndSorting(): void {
    let result = this.mockTransactions.filter(transaction =>
      (transaction.message?.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
        transaction.description?.toLowerCase().includes(this.searchTerm.toLowerCase())) &&
      (this.typeFilter === 'ALL' || transaction.transactionType === this.typeFilter) &&
      (this.statusFilter === 'ALL' || transaction.status === this.statusFilter)
    );

    this.filteredAndSortedTransactions = result;
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
    this.applyFiltersAndSorting();
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
