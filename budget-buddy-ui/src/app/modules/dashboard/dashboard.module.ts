import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DashboardRoutingModule } from './dashboard-routing.module';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import {SideBarComponent} from './components/side-bar/side-bar.component';
import {MatIconModule} from '@angular/material/icon';
import { BudgetComponent } from './components/budget/budget.component';
import { WalletComponent } from './components/wallet/wallet.component';
import { IncomeComponent } from './components/income/income.component';
import { ExpenseComponent } from './components/expense/expense.component';
import { GoalComponent } from './components/goal/goal.component';
import { DebtComponent } from './components/debt/debt.component';


@NgModule({
  declarations: [
    DashboardComponent,
    SideBarComponent,
    BudgetComponent,
    WalletComponent,
    IncomeComponent,
    ExpenseComponent,
    GoalComponent,
    DebtComponent,
  ],
  exports: [
    SideBarComponent,
  ],
  imports: [
    CommonModule,
    DashboardRoutingModule,
  ]
})
export class DashboardModule { }
