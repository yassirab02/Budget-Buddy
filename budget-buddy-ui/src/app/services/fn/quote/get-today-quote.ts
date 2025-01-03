/* tslint:disable */
/* eslint-disable */
/* Code generated by ng-openapi-gen DO NOT EDIT. */

import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { QuotesResponse } from '../../models/quotes-response';

export interface GetTodayQuote$Params {
}

export function getTodayQuote(http: HttpClient, rootUrl: string, params?: GetTodayQuote$Params, context?: HttpContext): Observable<StrictHttpResponse<QuotesResponse>> {
  const rb = new RequestBuilder(rootUrl, getTodayQuote.PATH, 'get');
  if (params) {
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<QuotesResponse>;
    })
  );
}

getTodayQuote.PATH = '/quote/today-quote';
