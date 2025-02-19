/* tslint:disable */
/* eslint-disable */
/* Code generated by ng-openapi-gen DO NOT EDIT. */

import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { ContactRequest } from '../../models/contact-request';
import { ContactResponse } from '../../models/contact-response';

export interface UpdateContactMessage$Params {
  'contact-id': ContactRequest;
}

export function updateContactMessage(http: HttpClient, rootUrl: string, params: UpdateContactMessage$Params, context?: HttpContext): Observable<StrictHttpResponse<ContactResponse>> {
  const rb = new RequestBuilder(rootUrl, updateContactMessage.PATH, 'post');
  if (params) {
    rb.path('contact-id', params['contact-id'], {});
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<ContactResponse>;
    })
  );
}

updateContactMessage.PATH = '/admin/contact/{contact-id}';
