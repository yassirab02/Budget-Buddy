/* tslint:disable */
/* eslint-disable */
/* Code generated by ng-openapi-gen DO NOT EDIT. */

import { ExpensesResponse } from '../models/expenses-response';
import { IncomeResponse } from '../models/income-response';
import { TransactionResponse } from '../models/transaction-response';
export interface WalletResponse {
  balance?: number;
  expenses?: Array<ExpensesResponse>;
  id?: number;
  incomes?: Array<IncomeResponse>;
  name?: string;
  totalExpenses?: number;
  totalIncome?: number;
  walletType?: string;
}
