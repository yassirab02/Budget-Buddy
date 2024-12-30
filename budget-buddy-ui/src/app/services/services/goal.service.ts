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

import { addOrUpdateGoal } from '../fn/goal/add-or-update-goal';
import { AddOrUpdateGoal$Params } from '../fn/goal/add-or-update-goal';
import { deleteBudget } from '../fn/goal/delete-budget';
import { DeleteBudget$Params } from '../fn/goal/delete-budget';
import { findAllGoalsByUser } from '../fn/goal/find-all-goals-by-user';
import { FindAllGoalsByUser$Params } from '../fn/goal/find-all-goals-by-user';
import { findGoalById } from '../fn/goal/find-goal-by-id';
import { FindGoalById$Params } from '../fn/goal/find-goal-by-id';
import { findGoalsByUserAndReachedStatus } from '../fn/goal/find-goals-by-user-and-reached-status';
import { FindGoalsByUserAndReachedStatus$Params } from '../fn/goal/find-goals-by-user-and-reached-status';
import { GoalResponse } from '../models/goal-response';
import { PageResponseGoalResponse } from '../models/page-response-goal-response';

@Injectable({ providedIn: 'root' })
export class GoalService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `addOrUpdateGoal()` */
  static readonly AddOrUpdateGoalPath = '/goal/add-goal';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `addOrUpdateGoal()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  addOrUpdateGoal$Response(params: AddOrUpdateGoal$Params, context?: HttpContext): Observable<StrictHttpResponse<number>> {
    return addOrUpdateGoal(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `addOrUpdateGoal$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  addOrUpdateGoal(params: AddOrUpdateGoal$Params, context?: HttpContext): Observable<number> {
    return this.addOrUpdateGoal$Response(params, context).pipe(
      map((r: StrictHttpResponse<number>): number => r.body)
    );
  }

  /** Path part for operation `findGoalById()` */
  static readonly FindGoalByIdPath = '/goal/{goal-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `findGoalById()` instead.
   *
   * This method doesn't expect any request body.
   */
  findGoalById$Response(params: FindGoalById$Params, context?: HttpContext): Observable<StrictHttpResponse<GoalResponse>> {
    return findGoalById(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `findGoalById$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  findGoalById(params: FindGoalById$Params, context?: HttpContext): Observable<GoalResponse> {
    return this.findGoalById$Response(params, context).pipe(
      map((r: StrictHttpResponse<GoalResponse>): GoalResponse => r.body)
    );
  }

  /** Path part for operation `deleteBudget()` */
  static readonly DeleteBudgetPath = '/goal/{goal-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `deleteBudget()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteBudget$Response(params: DeleteBudget$Params, context?: HttpContext): Observable<StrictHttpResponse<{
}>> {
    return deleteBudget(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `deleteBudget$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteBudget(params: DeleteBudget$Params, context?: HttpContext): Observable<{
}> {
    return this.deleteBudget$Response(params, context).pipe(
      map((r: StrictHttpResponse<{
}>): {
} => r.body)
    );
  }

  /** Path part for operation `findGoalsByUserAndReachedStatus()` */
  static readonly FindGoalsByUserAndReachedStatusPath = '/goal/goals/reached';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `findGoalsByUserAndReachedStatus()` instead.
   *
   * This method doesn't expect any request body.
   */
  findGoalsByUserAndReachedStatus$Response(params: FindGoalsByUserAndReachedStatus$Params, context?: HttpContext): Observable<StrictHttpResponse<PageResponseGoalResponse>> {
    return findGoalsByUserAndReachedStatus(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `findGoalsByUserAndReachedStatus$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  findGoalsByUserAndReachedStatus(params: FindGoalsByUserAndReachedStatus$Params, context?: HttpContext): Observable<PageResponseGoalResponse> {
    return this.findGoalsByUserAndReachedStatus$Response(params, context).pipe(
      map((r: StrictHttpResponse<PageResponseGoalResponse>): PageResponseGoalResponse => r.body)
    );
  }

  /** Path part for operation `findAllGoalsByUser()` */
  static readonly FindAllGoalsByUserPath = '/goal/all-goals';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `findAllGoalsByUser()` instead.
   *
   * This method doesn't expect any request body.
   */
  findAllGoalsByUser$Response(params?: FindAllGoalsByUser$Params, context?: HttpContext): Observable<StrictHttpResponse<PageResponseGoalResponse>> {
    return findAllGoalsByUser(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `findAllGoalsByUser$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  findAllGoalsByUser(params?: FindAllGoalsByUser$Params, context?: HttpContext): Observable<PageResponseGoalResponse> {
    return this.findAllGoalsByUser$Response(params, context).pipe(
      map((r: StrictHttpResponse<PageResponseGoalResponse>): PageResponseGoalResponse => r.body)
    );
  }

}