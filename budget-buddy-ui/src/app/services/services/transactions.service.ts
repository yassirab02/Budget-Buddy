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

import { findAllTransactions } from '../fn/transactions/find-all-transactions';
import { FindAllTransactions$Params } from '../fn/transactions/find-all-transactions';
import { findAllTransactionsByReciever } from '../fn/transactions/find-all-transactions-by-reciever';
import { FindAllTransactionsByReciever$Params } from '../fn/transactions/find-all-transactions-by-reciever';
import { findAllTransactionsBySender } from '../fn/transactions/find-all-transactions-by-sender';
import { FindAllTransactionsBySender$Params } from '../fn/transactions/find-all-transactions-by-sender';
import { PageResponseTransactionResponse } from '../models/page-response-transaction-response';
import { transferMoney } from '../fn/transactions/transfer-money';
import { TransferMoney$Params } from '../fn/transactions/transfer-money';

@Injectable({ providedIn: 'root' })
export class TransactionsService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `transferMoney()` */
  static readonly TransferMoneyPath = '/transactions/transfer-money';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `transferMoney()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  transferMoney$Response(params: TransferMoney$Params, context?: HttpContext): Observable<StrictHttpResponse<number>> {
    return transferMoney(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `transferMoney$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  transferMoney(params: TransferMoney$Params, context?: HttpContext): Observable<number> {
    return this.transferMoney$Response(params, context).pipe(
      map((r: StrictHttpResponse<number>): number => r.body)
    );
  }

  /** Path part for operation `findAllTransactions()` */
  static readonly FindAllTransactionsPath = '/transactions/all-transactions';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `findAllTransactions()` instead.
   *
   * This method doesn't expect any request body.
   */
  findAllTransactions$Response(params?: FindAllTransactions$Params, context?: HttpContext): Observable<StrictHttpResponse<PageResponseTransactionResponse>> {
    return findAllTransactions(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `findAllTransactions$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  findAllTransactions(params?: FindAllTransactions$Params, context?: HttpContext): Observable<PageResponseTransactionResponse> {
    return this.findAllTransactions$Response(params, context).pipe(
      map((r: StrictHttpResponse<PageResponseTransactionResponse>): PageResponseTransactionResponse => r.body)
    );
  }

  /** Path part for operation `findAllTransactionsBySender()` */
  static readonly FindAllTransactionsBySenderPath = '/transactions/all-transactions-sent';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `findAllTransactionsBySender()` instead.
   *
   * This method doesn't expect any request body.
   */
  findAllTransactionsBySender$Response(params?: FindAllTransactionsBySender$Params, context?: HttpContext): Observable<StrictHttpResponse<PageResponseTransactionResponse>> {
    return findAllTransactionsBySender(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `findAllTransactionsBySender$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  findAllTransactionsBySender(params?: FindAllTransactionsBySender$Params, context?: HttpContext): Observable<PageResponseTransactionResponse> {
    return this.findAllTransactionsBySender$Response(params, context).pipe(
      map((r: StrictHttpResponse<PageResponseTransactionResponse>): PageResponseTransactionResponse => r.body)
    );
  }

  /** Path part for operation `findAllTransactionsByReciever()` */
  static readonly FindAllTransactionsByRecieverPath = '/transactions/all-transactions-received';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `findAllTransactionsByReciever()` instead.
   *
   * This method doesn't expect any request body.
   */
  findAllTransactionsByReciever$Response(params?: FindAllTransactionsByReciever$Params, context?: HttpContext): Observable<StrictHttpResponse<PageResponseTransactionResponse>> {
    return findAllTransactionsByReciever(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `findAllTransactionsByReciever$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  findAllTransactionsByReciever(params?: FindAllTransactionsByReciever$Params, context?: HttpContext): Observable<PageResponseTransactionResponse> {
    return this.findAllTransactionsByReciever$Response(params, context).pipe(
      map((r: StrictHttpResponse<PageResponseTransactionResponse>): PageResponseTransactionResponse => r.body)
    );
  }

}
