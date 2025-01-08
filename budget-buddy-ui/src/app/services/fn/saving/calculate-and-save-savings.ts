/* tslint:disable */
/* eslint-disable */
/* Code generated by ng-openapi-gen DO NOT EDIT. */

import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { SavingResponse } from '../../models/saving-response';

export interface CalculateAndSaveSavings$Params {
}

export function calculateAndSaveSavings(http: HttpClient, rootUrl: string, params?: CalculateAndSaveSavings$Params, context?: HttpContext): Observable<StrictHttpResponse<SavingResponse>> {
  const rb = new RequestBuilder(rootUrl, calculateAndSaveSavings.PATH, 'post');
  if (params) {
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<SavingResponse>;
    })
  );
}

calculateAndSaveSavings.PATH = '/saving/calc-savings';