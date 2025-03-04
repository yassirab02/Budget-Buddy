/* tslint:disable */
/* eslint-disable */
/* Code generated by ng-openapi-gen DO NOT EDIT. */

import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { UserTransferResponse } from '../../models/user-transfer-response';

export interface GetUsersTransfer$Params {
}

export function getUsersTransfer(http: HttpClient, rootUrl: string, params?: GetUsersTransfer$Params, context?: HttpContext): Observable<StrictHttpResponse<Array<UserTransferResponse>>> {
  const rb = new RequestBuilder(rootUrl, getUsersTransfer.PATH, 'get');
  if (params) {
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<Array<UserTransferResponse>>;
    })
  );
}

getUsersTransfer.PATH = '/api/v1/user/users-transfer';
