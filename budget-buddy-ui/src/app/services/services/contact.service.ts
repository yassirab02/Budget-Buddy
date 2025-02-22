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

import { createContact } from '../fn/contact/create-contact';
import { CreateContact$Params } from '../fn/contact/create-contact';

@Injectable({ providedIn: 'root' })
export class ContactService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `createContact()` */
  static readonly CreateContactPath = '/contact/add-contact';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `createContact()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createContact$Response(params: CreateContact$Params, context?: HttpContext): Observable<StrictHttpResponse<number>> {
    return createContact(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `createContact$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createContact(params: CreateContact$Params, context?: HttpContext): Observable<number> {
    return this.createContact$Response(params, context).pipe(
      map((r: StrictHttpResponse<number>): number => r.body)
    );
  }

}
