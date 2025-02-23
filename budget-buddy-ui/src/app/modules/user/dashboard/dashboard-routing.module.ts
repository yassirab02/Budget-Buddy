import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {MainComponent} from './pages/main/main.component';
import {AuthGuard} from '../../../services/guard/auth.guard';
import {WalletComponent} from './pages/wallet/wallet.component';
import {IncomeComponent} from './pages/income/income.component';
import {ExpenseComponent} from './pages/expense/expense.component';
import {DebtComponent} from './pages/debt/debt.component';
import {GoalComponent} from './pages/goal/goal.component';
import {StoryComponent} from './pages/story/story.component';
import {ProfileComponent} from './pages/profile/profile.component';
import {SettingComponent} from './pages/setting/setting.component';
import {ContactComponent} from './pages/contact/contact.component';
import {ReportComponent} from './pages/report/report.component';
import {TransactionComponent} from './pages/transaction/transaction.component';
import {DashboardComponent} from './pages/dashboard/dashboard.component';
import {BudgetComponent} from './pages/budget/budget.component';
import {QuestionsComponent} from './pages/questions/questions.component';
import {ReportDetailComponent} from './pages/report/report-detail/report-month/report-detail.component';
import {ReportYearComponent} from './pages/report/report-detail/report-year/report-year.component';
import {StoryDetailComponent} from './components/story/story-detail/story-detail.component';
import {TipsComponent} from './pages/tips/tips.component';
import {ExpenseDetailComponent} from './components/expenses/expense-detail/expense-detail.component';

const routes: Routes = [
  {
    path: '',
    component: MainComponent,
    canActivate: [AuthGuard],
    children:[
      {
        path:'',
        component: DashboardComponent,
      },
      {
        path: 'budget',
        component: BudgetComponent,
        canActivate: [AuthGuard]
      },
      {
        path: 'wallet',
        component: WalletComponent,
        canActivate: [AuthGuard]
      },
      {
        path: 'income',
        component: IncomeComponent,
        canActivate: [AuthGuard]
      },
      {
        path: 'expense',
        component: ExpenseComponent,
        canActivate: [AuthGuard]
      },
      {
        path: 'expense/detail/:id',
        component: ExpenseDetailComponent,
        canActivate: [AuthGuard]
      },
      {
        path: 'debt',
        component: DebtComponent,
        canActivate: [AuthGuard]
      },
      {
        path: 'goal',
        component: GoalComponent,
        canActivate: [AuthGuard]
      },
      {
        path: 'stories',
        component: StoryComponent,
        canActivate: [AuthGuard]
      },
      {
        path: 'story/:id',
        component: StoryDetailComponent,
        canActivate: [AuthGuard]
      },
      {
        path: 'report',
        component: ReportComponent,
        canActivate: [AuthGuard]
      },
      {
        path: 'transactions',
        component: TransactionComponent,
        canActivate: [AuthGuard]
      },
      {
        path: 'tips',
        component: TipsComponent,
        canActivate: [AuthGuard]
      },
      {
        path: 'profile',
        component: ProfileComponent,
        canActivate: [AuthGuard]
      },
      {
        path: 'setting',
        component: SettingComponent,
        canActivate: [AuthGuard]
      },
      {
        path: 'help',
        component: ContactComponent,
        canActivate: [AuthGuard]
      },
      {
        path: 'report/monthly/:id',
        component: ReportDetailComponent,
        canActivate: [AuthGuard]
      },
      {
        path: 'report/yearly/:id',
        component: ReportYearComponent,
        canActivate: [AuthGuard]
      },
    ]
  },
  {
    path: 'questions',
    component: QuestionsComponent,
    canActivate: [AuthGuard]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DashboardRoutingModule { }
