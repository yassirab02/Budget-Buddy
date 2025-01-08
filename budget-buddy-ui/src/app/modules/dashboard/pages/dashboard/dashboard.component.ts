import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements OnInit{
  constructor(
    private router:Router,
  ) {
  }
  ngOnInit() {

  }
  transactions = [
    { name: 'Groceries', amount: -50, date: '2023-06-15' },
    { name: 'Salary', amount: 3000, date: '2023-06-14' },
    { name: 'Electric Bill', amount: -80, date: '2023-06-13' }
  ];

  savingsGoals = [
    { name: 'Vacation Fund', current: 2000, target: 5000 },
    { name: 'New Laptop', current: 800, target: 1500 }
  ];

}
