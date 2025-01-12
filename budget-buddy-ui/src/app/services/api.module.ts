/* tslint:disable */
/* eslint-disable */
/* Code generated by ng-openapi-gen DO NOT EDIT. */

import { NgModule, ModuleWithProviders, SkipSelf, Optional } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ApiConfiguration, ApiConfigurationParams } from './api-configuration';

import { WalletService } from './services/wallet.service';
import { TransactionsService } from './services/transactions.service';
import { StoryService } from './services/story.service';
import { SavingService } from './services/saving.service';
import { IncomeService } from './services/income.service';
import { GoalService } from './services/goal.service';
import { ExpensesService } from './services/expenses.service';
import { DebtService } from './services/debt.service';
import { CommentService } from './services/comment.service';
import { BudgetService } from './services/budget.service';
import { AuthenticationService } from './services/authentication.service';
import { UserControllerService } from './services/user-controller.service';
import { AdminService } from './services/admin.service';
import { ReportService } from './services/report.service';
import { QuoteService } from './services/quote.service';
import { CategoryService } from './services/category.service';

/**
 * Module that provides all services and configuration.
 */
@NgModule({
  imports: [],
  exports: [],
  declarations: [],
  providers: [
    WalletService,
    TransactionsService,
    StoryService,
    SavingService,
    IncomeService,
    GoalService,
    ExpensesService,
    DebtService,
    CommentService,
    BudgetService,
    AuthenticationService,
    UserControllerService,
    AdminService,
    ReportService,
    QuoteService,
    CategoryService,
    ApiConfiguration
  ],
})
export class ApiModule {
  static forRoot(params: ApiConfigurationParams): ModuleWithProviders<ApiModule> {
    return {
      ngModule: ApiModule,
      providers: [
        {
          provide: ApiConfiguration,
          useValue: params
        }
      ]
    }
  }

  constructor( 
    @Optional() @SkipSelf() parentModule: ApiModule,
    @Optional() http: HttpClient
  ) {
    if (parentModule) {
      throw new Error('ApiModule is already loaded. Import in your base AppModule only.');
    }
    if (!http) {
      throw new Error('You need to import the HttpClientModule in your AppModule! \n' +
      'See also https://github.com/angular/angular/issues/20575');
    }
  }
}
