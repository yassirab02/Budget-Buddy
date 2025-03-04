/* tslint:disable */
/* eslint-disable */
/* Code generated by ng-openapi-gen DO NOT EDIT. */

import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';


export interface DownloadFile2$Params {
  bucket: string;
  document: string;
}

export function downloadFile2(http: HttpClient, rootUrl: string, params: DownloadFile2$Params, context?: HttpContext): Observable<StrictHttpResponse<string>> {
  const rb = new RequestBuilder(rootUrl, downloadFile2.PATH, 'get');
  if (params) {
    rb.path('bucket', params.bucket, {});
    rb.path('document', params.document, {});
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<string>;
    })
  );
}

downloadFile2.PATH = '/api/cloud/download-v2/bucket/{bucket}/document/{document}';
