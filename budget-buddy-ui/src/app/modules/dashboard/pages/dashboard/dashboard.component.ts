import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {UserControllerService} from '../../../../services/services/user-controller.service';
import {User} from '../../../../services/models/user';
import {BudgetService} from '../../../../services/services/budget.service';
import {addBalance} from '../../../../services/fn/user-controller/add-balance';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements OnInit {
  add = false;
  isOpen = false;
  amountToAdd: number = 0; // Variable bound to the input field


  private _user: User | undefined;  // Defining private variable with undefined initially

  constructor(
    private router: Router,
    private userService: UserControllerService,
    private budgetService: BudgetService
  ) {
  }

  ngOnInit() {
    this.initializeDashboard(); // Call the reusable method during component initialization
  }

  initializeDashboard() {
    // Fetching the current user with type safety
    this.userService.getCurrentUser().subscribe(
      (user: User) => {
        this._user = user;  // Ensure user is set correctly
      },
      (error) => {
        console.error('Error fetching user data', error);  // Handle error gracefully
      }
    );
  }

  transactions = [
    {name: 'Groceries', amount: -50, date: '2023-06-15'},
    {name: 'Salary', amount: 3000, date: '2023-06-14'},
    {name: 'Electric Bill', amount: -80, date: '2023-06-13'}
  ];

  savingsGoals = [
    {name: 'Vacation Fund', current: 2000, target: 5000},
    {name: 'New Laptop', current: 800, target: 1500}
  ];


  // Getter and Setter for user
  get user(): User | undefined {
    return this._user;
  }

  set user(value: User | undefined) {
    this._user = value;
  }

  toggleDropdown() {
    this.isOpen = !this.isOpen;
  }

  logout() {
    localStorage.clear(); // or localStorage.removeItem('token');
    window.location.reload();
  }


  toggleAddInput() {
    this.add = !this.add;
  }


  addToBalance(amountToAdd: number) {
    if (amountToAdd <= 0 || amountToAdd === null) {
      return;
    }
    this.userService.addBalance({amount: amountToAdd}).subscribe(
      () => {
        this.amountToAdd = 0; // Reset the input field
        this.initializeDashboard(); // Refresh the dashboard data
      },
      (error) => {
        console.error('Error adding balance', error);
      }
    );
  }

  cancelAdd() {
    this.add = false; // Hide the input field
    this.amountToAdd = 0; // Reset the input value
  }

}
