/* tslint:disable */
/* eslint-disable */
/* Code generated by ng-openapi-gen DO NOT EDIT. */

import { IncomeSource } from '../models/income-source';
import { Wallet } from '../models/wallet';
export interface Income {
  amount?: number;
  createdBy?: number;
  createdDate?: string;
  date?: string;
  description?: string;
  id?: number;
  incomeSource?: IncomeSource;
  lastModifiedBy?: number;
  lastModifiedDate?: string;
  name?: string;
  wallet?: Wallet;
}