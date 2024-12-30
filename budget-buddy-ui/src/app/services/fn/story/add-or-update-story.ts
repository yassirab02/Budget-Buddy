/* tslint:disable */
/* eslint-disable */
/* Code generated by ng-openapi-gen DO NOT EDIT. */

import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { StoryRequest } from '../../models/story-request';
import { StoryResponse } from '../../models/story-response';

export interface AddOrUpdateStory$Params {
      body: StoryRequest
}

export function addOrUpdateStory(http: HttpClient, rootUrl: string, params: AddOrUpdateStory$Params, context?: HttpContext): Observable<StrictHttpResponse<StoryResponse>> {
  const rb = new RequestBuilder(rootUrl, addOrUpdateStory.PATH, 'post');
  if (params) {
    rb.body(params.body, 'application/json');
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<StoryResponse>;
    })
  );
}

addOrUpdateStory.PATH = '/story/add-story';
