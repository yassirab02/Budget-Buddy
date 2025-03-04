/* tslint:disable */
/* eslint-disable */
/* Code generated by ng-openapi-gen DO NOT EDIT. */

import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { addOrUpdateExpense } from '../fn/expenses/add-or-update-expense';
import { AddOrUpdateExpense$Params } from '../fn/expenses/add-or-update-expense';
import { deleteExpense } from '../fn/expenses/delete-expense';
import { DeleteExpense$Params } from '../fn/expenses/delete-expense';
import { ExpensesCategoryResponse } from '../models/expenses-category-response';
import { ExpensesResponse } from '../models/expenses-response';
import { findAllExpenses } from '../fn/expenses/find-all-expenses';
import { FindAllExpenses$Params } from '../fn/expenses/find-all-expenses';
import { findExpenseById } from '../fn/expenses/find-expense-by-id';
import { FindExpenseById$Params } from '../fn/expenses/find-expense-by-id';
import { getTopSpendingCategories } from '../fn/expenses/get-top-spending-categories';
import { GetTopSpendingCategories$Params } from '../fn/expenses/get-top-spending-categories';
import { PageResponseExpensesResponse } from '../models/page-response-expenses-response';
import { resetExpenses } from '../fn/expenses/reset-expenses';
import { ResetExpenses$Params } from '../fn/expenses/reset-expenses';
import { resetMonthlyExpenses } from '../fn/expenses/reset-monthly-expenses';
import { ResetMonthlyExpenses$Params } from '../fn/expenses/reset-monthly-expenses';

@Injectable({ providedIn: 'root' })
export class ExpensesService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `resetMonthlyExpenses()` */
  static readonly ResetMonthlyExpensesPath = '/expenses/reset-monthly-expenses';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `resetMonthlyExpenses()` instead.
   *
   * This method doesn't expect any request body.
   */
  resetMonthlyExpenses$Response(params?: ResetMonthlyExpenses$Params, context?: HttpContext): Observable<StrictHttpResponse<{
}>> {
    return resetMonthlyExpenses(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `resetMonthlyExpenses$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  resetMonthlyExpenses(params?: ResetMonthlyExpenses$Params, context?: HttpContext): Observable<{
}> {
    return this.resetMonthlyExpenses$Response(params, context).pipe(
      map((r: StrictHttpResponse<{
}>): {
} => r.body)
    );
  }

  /** Path part for operation `resetExpenses()` */
  static readonly ResetExpensesPath = '/expenses/reset-expenses';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `resetExpenses()` instead.
   *
   * This method doesn't expect any request body.
   */
  resetExpenses$Response(params?: ResetExpenses$Params, context?: HttpContext): Observable<StrictHttpResponse<{
}>> {
    return resetExpenses(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `resetExpenses$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  resetExpenses(params?: ResetExpenses$Params, context?: HttpContext): Observable<{
}> {
    return this.resetExpenses$Response(params, context).pipe(
      map((r: StrictHttpResponse<{
}>): {
} => r.body)
    );
  }

  /** Path part for operation `addOrUpdateExpense()` */
  static readonly AddOrUpdateExpensePath = '/expenses/add-expense';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `addOrUpdateExpense()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  addOrUpdateExpense$Response(params: AddOrUpdateExpense$Params, context?: HttpContext): Observable<StrictHttpResponse<number>> {
    return addOrUpdateExpense(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `addOrUpdateExpense$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  addOrUpdateExpense(params: AddOrUpdateExpense$Params, context?: HttpContext): Observable<number> {
    return this.addOrUpdateExpense$Response(params, context).pipe(
      map((r: StrictHttpResponse<number>): number => r.body)
    );
  }

  /** Path part for operation `findExpenseById()` */
  static readonly FindExpenseByIdPath = '/expenses/{expense-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `findExpenseById()` instead.
   *
   * This method doesn't expect any request body.
   */
  findExpenseById$Response(params: FindExpenseById$Params, context?: HttpContext): Observable<StrictHttpResponse<ExpensesResponse>> {
    return findExpenseById(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `findExpenseById$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  findExpenseById(params: FindExpenseById$Params, context?: HttpContext): Observable<ExpensesResponse> {
    return this.findExpenseById$Response(params, context).pipe(
      map((r: StrictHttpResponse<ExpensesResponse>): ExpensesResponse => r.body)
    );
  }

  /** Path part for operation `deleteExpense()` */
  static readonly DeleteExpensePath = '/expenses/{expense-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `deleteExpense()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteExpense$Response(params: DeleteExpense$Params, context?: HttpContext): Observable<StrictHttpResponse<{
}>> {
    return deleteExpense(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `deleteExpense$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteExpense(params: DeleteExpense$Params, context?: HttpContext): Observable<{
}> {
    return this.deleteExpense$Response(params, context).pipe(
      map((r: StrictHttpResponse<{
}>): {
} => r.body)
    );
  }

  /** Path part for operation `getTopSpendingCategories()` */
  static readonly GetTopSpendingCategoriesPath = '/expenses/top-spending-categories';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getTopSpendingCategories()` instead.
   *
   * This method doesn't expect any request body.
   */
  getTopSpendingCategories$Response(params?: GetTopSpendingCategories$Params, context?: HttpContext): Observable<StrictHttpResponse<Array<ExpensesCategoryResponse>>> {
    return getTopSpendingCategories(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getTopSpendingCategories$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getTopSpendingCategories(params?: GetTopSpendingCategories$Params, context?: HttpContext): Observable<Array<ExpensesCategoryResponse>> {
    return this.getTopSpendingCategories$Response(params, context).pipe(
      map((r: StrictHttpResponse<Array<ExpensesCategoryResponse>>): Array<ExpensesCategoryResponse> => r.body)
    );
  }

  /** Path part for operation `findAllExpenses()` */
  static readonly FindAllExpensesPath = '/expenses/all-expenses';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `findAllExpenses()` instead.
   *
   * This method doesn't expect any request body.
   */
  findAllExpenses$Response(params?: FindAllExpenses$Params, context?: HttpContext): Observable<StrictHttpResponse<PageResponseExpensesResponse>> {
    return findAllExpenses(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `findAllExpenses$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  findAllExpenses(params?: FindAllExpenses$Params, context?: HttpContext): Observable<PageResponseExpensesResponse> {
    return this.findAllExpenses$Response(params, context).pipe(
      map((r: StrictHttpResponse<PageResponseExpensesResponse>): PageResponseExpensesResponse => r.body)
    );
  }

}
