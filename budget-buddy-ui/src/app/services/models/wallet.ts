/* tslint:disable */
/* eslint-disable */
/* Code generated by ng-openapi-gen DO NOT EDIT. */

import { Expenses } from '../models/expenses';
import { Income } from '../models/income';
import { Transaction } from '../models/transaction';
import { User } from '../models/user';
export interface Wallet {
  balance?: number;
  createdBy?: number;
  createdDate?: string;
  expenses?: Array<Expenses>;
  id?: number;
  incomes?: Array<Income>;
  lastModifiedBy?: number;
  lastModifiedDate?: string;
  name?: string;
  owner?: User;
  receivedTransfers?: Array<Transaction>;
  sentTransfers?: Array<Transaction>;
  totalExpenses?: number;
  totalIncome?: number;
  type?: 'SPENDING' | 'SAVINGS' | 'CASH';
}
