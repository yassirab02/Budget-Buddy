/* tslint:disable */
/* eslint-disable */
/* Code generated by ng-openapi-gen DO NOT EDIT. */

export interface ExpensesRequest {
  amount: number;
  budgetId: number;
  categoryId: number;
  date?: string;
  description?: string;
  expensesType: 'FIXED' | 'VARIABLE' | 'ONE_TIME';
  id?: number;
  name: string;
  walletId?: number;
}
