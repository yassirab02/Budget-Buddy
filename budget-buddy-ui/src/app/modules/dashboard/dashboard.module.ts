import {NgModule} from '@angular/core';
import {CommonModule, NgOptimizedImage} from '@angular/common';

import {DashboardRoutingModule} from './dashboard-routing.module';
import {MainComponent} from './pages/main/main.component';
import {SideBarComponent} from './pages/side-bar/side-bar.component';
import {WalletComponent} from './pages/wallet/wallet.component';
import {IncomeComponent} from './pages/income/income.component';
import {ExpenseComponent} from './pages/expense/expense.component';
import {GoalComponent} from './pages/goal/goal.component';
import {DebtComponent} from './pages/debt/debt.component';
import {StoryComponent} from './pages/story/story.component';
import {QuoteComponent} from './pages/quote/quote.component';
import {ProfileComponent} from './pages/profile/profile.component';
import {SettingComponent} from './pages/setting/setting.component';
import {ContactComponent} from './pages/contact/contact.component';
import {TransactionComponent} from './pages/transaction/transaction.component';
import {ReportComponent} from './pages/report/report.component';
import {DashboardComponent} from './pages/dashboard/dashboard.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {BudgetCreateComponent} from './components/budget/budget-create/budget-create.component';
import {BudgetComponent} from './pages/budget/budget.component';
import {BudgetCardComponent} from './components/budget/budget-card/budget-card.component';
import {SpinnerComponent} from './pages/spinner/spinner.component';
import {WalletDetailsComponent} from './components/wallet/wallet-details/wallet-details.component';
import {ExpenseCreateComponent} from './components/expenses/expense-create/expense-create.component';
import {MatInputModule} from '@angular/material/input';
import {MatMenuModule} from '@angular/material/menu';
import {MatIconModule} from '@angular/material/icon';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatSelectModule} from '@angular/material/select';
import {IncomeCreateComponent} from './components/income/income-create/income-create.component';
import {DebtCreateComponent} from './components/debt/debt-create/debt-create.component';
import {GoalCreateComponent} from './components/goal/goal-create/goal-create.component';
import {TransactionCreateComponent} from './components/transaction/transaction-create/transaction-create.component';
import {ChartComponent} from './components/chart/chart.component';
import {StoryCreateComponent} from './components/story/story-create/story-create.component';
import {StoryDetailComponent} from './components/story/story-detail/story-detail.component';
import {QuestionsComponent} from './pages/questions/questions.component';
import {ReportDetailComponent} from './pages/report/report-detail/report-month/report-detail.component';
import { ReportYearComponent } from './pages/report/report-detail/report-year/report-year.component';
import {MonthPipe} from '../../pipes/month.pipe';
import { TipsComponent } from './pages/tips/tips.component';
import { ExpenseDetailComponent } from './components/expenses/expense-detail/expense-detail.component';


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
    SpinnerComponent,
    WalletDetailsComponent,
    ExpenseCreateComponent,
    IncomeCreateComponent,
    DebtCreateComponent,
    GoalCreateComponent,
    TransactionCreateComponent,
    TransactionCreateComponent,
    ChartComponent,
    StoryCreateComponent,
    StoryDetailComponent,
    QuestionsComponent,
    ReportDetailComponent,
    ReportYearComponent,
    MonthPipe,
    TipsComponent,
    ExpenseDetailComponent,
  ],
  exports: [
    SpinnerComponent
  ],
  imports: [
    CommonModule,
    DashboardRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    MatInputModule,
    MatMenuModule,
    MatIconModule,
    MatDatepickerModule,
    MatSelectModule,
    NgOptimizedImage,

  ]
})
export class DashboardModule {
}
