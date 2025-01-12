import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DashboardRoutingModule } from './dashboard-routing.module';
import { MainComponent } from './pages/main/main.component';
import {SideBarComponent} from './pages/side-bar/side-bar.component';
import { WalletComponent } from './pages/wallet/wallet.component';
import { IncomeComponent } from './pages/income/income.component';
import { ExpenseComponent } from './pages/expense/expense.component';
import { GoalComponent } from './pages/goal/goal.component';
import { DebtComponent } from './pages/debt/debt.component';
import { StoryComponent } from './pages/story/story.component';
import { QuoteComponent } from './pages/quote/quote.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { SettingComponent } from './pages/setting/setting.component';
import { ContactComponent } from './pages/contact/contact.component';
import { TransactionComponent } from './pages/transaction/transaction.component';
import { ReportComponent } from './pages/report/report.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { BudgetCreateComponent } from './components/budget/budget-create/budget-create.component';
import {BudgetComponent} from './pages/budget/budget.component';
import { BudgetCardComponent } from './components/budget/budget-card/budget-card.component';
import { WalletCreateComponent } from './components/wallet/wallet-create/wallet-create.component';


@NgModule({
  declarations: [
    MainComponent,
    SideBarComponent,
    WalletComponent,
    BudgetComponent,
    IncomeComponent,
    ExpenseComponent,
    GoalComponent,
    DebtComponent,
    StoryComponent,
    QuoteComponent,
    ProfileComponent,
    SettingComponent,
    ContactComponent,
    TransactionComponent,
    ReportComponent,
    DashboardComponent,
    BudgetCreateComponent,
    BudgetCardComponent,
    WalletCreateComponent,
  ],
  imports: [
    CommonModule,
    DashboardRoutingModule,
    FormsModule,
    ReactiveFormsModule,

  ]
})
export class DashboardModule { }
