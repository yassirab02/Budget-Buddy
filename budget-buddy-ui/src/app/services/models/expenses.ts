/* tslint:disable */
/* eslint-disable */
/* Code generated by ng-openapi-gen DO NOT EDIT. */

import { Budget } from '../models/budget';
import { ExpensesCategory } from '../models/expenses-category';
export interface Expenses {
  amount?: number;
  archived?: boolean;
  budget?: Budget;
  category?: ExpensesCategory;
  createdBy?: number;
  createdDate?: string;
  date?: string;
  description?: string;
  id?: number;
  lastModifiedBy?: number;
  lastModifiedDate?: string;
  name?: string;
  type?: 'FIXED' | 'VARIABLE' | 'ONE_TIME';
}
