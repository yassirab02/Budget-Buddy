/* tslint:disable */
/* eslint-disable */
/* Code generated by ng-openapi-gen DO NOT EDIT. */

import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { CommentRequest } from '../../models/comment-request';
import { CommentResponse } from '../../models/comment-response';

export interface AddOrUpdateComment$Params {
      body: CommentRequest
}

export function addOrUpdateComment(http: HttpClient, rootUrl: string, params: AddOrUpdateComment$Params, context?: HttpContext): Observable<StrictHttpResponse<CommentResponse>> {
  const rb = new RequestBuilder(rootUrl, addOrUpdateComment.PATH, 'post');
  if (params) {
    rb.body(params.body, 'application/json');
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<CommentResponse>;
    })
  );
}

addOrUpdateComment.PATH = '/comment/add-comment';