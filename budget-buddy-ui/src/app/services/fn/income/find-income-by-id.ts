/* tslint:disable */
/* eslint-disable */
/* Code generated by ng-openapi-gen DO NOT EDIT. */

import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { IncomeResponse } from '../../models/income-response';

export interface FindIncomeById$Params {
  'income-id': number;
}

export function findIncomeById(http: HttpClient, rootUrl: string, params: FindIncomeById$Params, context?: HttpContext): Observable<StrictHttpResponse<IncomeResponse>> {
  const rb = new RequestBuilder(rootUrl, findIncomeById.PATH, 'get');
  if (params) {
    rb.path('income-id', params['income-id'], {});
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<IncomeResponse>;
    })
  );
}

findIncomeById.PATH = '/income/{income-id}';
