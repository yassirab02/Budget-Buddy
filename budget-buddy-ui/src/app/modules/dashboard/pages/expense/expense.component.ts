import {Component, EventEmitter, Input, Output} from '@angular/core';
import {ExpensesResponse} from '../../../../services/models/expenses-response';


@Component({
  selector: 'app-expense',
  templateUrl: './expense.component.html',
  styleUrl: './expense.component.css'
})
export class ExpenseComponent {
  expenses:any = [
    {
      id: '1',
      name: 'Groceries',
      amount: 120.5,
      date: '2025-01-12',
      category: 'Food',
      expensesType: 'Essential',
      budget: 'Monthly Essentials',
      wallet: 'Main Wallet'
    },
    {
      id: '2',
      name: 'Gym Membership',
      amount: 45.0,
      date: '2025-01-10',
      category: 'Fitness',
      expensesType: 'Non-Essential',
      budget: 'Fitness Budget',
      wallet: 'Credit Card'
    },
    {
      id: '3',
      name: 'Electricity Bill',
      amount: 80.75,
      date: '2025-01-05',
      category: 'Utilities',
      expensesType: 'Essential',
      budget: 'Monthly Bills',
      wallet: 'Savings Wallet'
    },
    {
      id: '4',
      name: 'Movie Night',
      amount: 30.0,
      date: '2025-01-08',
      category: 'Entertainment',
      expensesType: 'Non-Essential',
      budget: 'Leisure',
      wallet: 'Main Wallet'
    }
  ];
}
