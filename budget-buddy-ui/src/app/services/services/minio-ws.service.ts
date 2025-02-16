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

import { bucketExists } from '../fn/minio-ws/bucket-exists';
import { BucketExists$Params } from '../fn/minio-ws/bucket-exists';
import { downloadFile } from '../fn/minio-ws/download-file';
import { DownloadFile$Params } from '../fn/minio-ws/download-file';
import { downloadFile2 } from '../fn/minio-ws/download-file-2';
import { DownloadFile2$Params } from '../fn/minio-ws/download-file-2';
import { findAllDocuments } from '../fn/minio-ws/find-all-documents';
import { FindAllDocuments$Params } from '../fn/minio-ws/find-all-documents';
import { loadDocument } from '../fn/minio-ws/load-document';
import { LoadDocument$Params } from '../fn/minio-ws/load-document';
import { MinIoInfos } from '../models/min-io-infos';
import { saveBucket } from '../fn/minio-ws/save-bucket';
import { SaveBucket$Params } from '../fn/minio-ws/save-bucket';
import { setObjectTags } from '../fn/minio-ws/set-object-tags';
import { SetObjectTags$Params } from '../fn/minio-ws/set-object-tags';
import { uploadMultipleToMinio } from '../fn/minio-ws/upload-multiple-to-minio';
import { UploadMultipleToMinio$Params } from '../fn/minio-ws/upload-multiple-to-minio';
import { uploadToMinio } from '../fn/minio-ws/upload-to-minio';
import { UploadToMinio$Params } from '../fn/minio-ws/upload-to-minio';

@Injectable({ providedIn: 'root' })
export class MinioWsService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `uploadToMinio()` */
  static readonly UploadToMinioPath = '/api/cloud/upload/file/{bucket}';

  /**
   * Upload a file to the bucket.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `uploadToMinio()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  uploadToMinio$Response(params: UploadToMinio$Params, context?: HttpContext): Observable<StrictHttpResponse<MinIoInfos>> {
    return uploadToMinio(this.http, this.rootUrl, params, context);
  }

  /**
   * Upload a file to the bucket.
   *
   *
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `uploadToMinio$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  uploadToMinio(params: UploadToMinio$Params, context?: HttpContext): Observable<MinIoInfos> {
    return this.uploadToMinio$Response(params, context).pipe(
      map((r: StrictHttpResponse<MinIoInfos>): MinIoInfos => r.body)
    );
  }

  /** Path part for operation `uploadMultipleToMinio()` */
  static readonly UploadMultipleToMinioPath = '/api/cloud/upload/bucket/{bucket}';

  /**
   * Upload multiple files to the bucket.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `uploadMultipleToMinio()` instead.
   *
   * This method doesn't expect any request body.
   */
  uploadMultipleToMinio$Response(params: UploadMultipleToMinio$Params, context?: HttpContext): Observable<StrictHttpResponse<Array<MinIoInfos>>> {
    return uploadMultipleToMinio(this.http, this.rootUrl, params, context);
  }

  /**
   * Upload multiple files to the bucket.
   *
   *
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `uploadMultipleToMinio$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  uploadMultipleToMinio(params: UploadMultipleToMinio$Params, context?: HttpContext): Observable<Array<MinIoInfos>> {
    return this.uploadMultipleToMinio$Response(params, context).pipe(
      map((r: StrictHttpResponse<Array<MinIoInfos>>): Array<MinIoInfos> => r.body)
    );
  }

  /** Path part for operation `setObjectTags()` */
  static readonly SetObjectTagsPath = '/api/cloud/tags/bucket/{bucket}/document/{document}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `setObjectTags()` instead.
   *
   * This method doesn't expect any request body.
   */
  setObjectTags$Response(params: SetObjectTags$Params, context?: HttpContext): Observable<StrictHttpResponse<void>> {
    return setObjectTags(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `setObjectTags$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  setObjectTags(params: SetObjectTags$Params, context?: HttpContext): Observable<void> {
    return this.setObjectTags$Response(params, context).pipe(
      map((r: StrictHttpResponse<void>): void => r.body)
    );
  }

  /** Path part for operation `bucketExists()` */
  static readonly BucketExistsPath = '/api/cloud/bucket/{bucket}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `bucketExists()` instead.
   *
   * This method doesn't expect any request body.
   */
  bucketExists$Response(params: BucketExists$Params, context?: HttpContext): Observable<StrictHttpResponse<boolean>> {
    return bucketExists(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `bucketExists$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  bucketExists(params: BucketExists$Params, context?: HttpContext): Observable<boolean> {
    return this.bucketExists$Response(params, context).pipe(
      map((r: StrictHttpResponse<boolean>): boolean => r.body)
    );
  }

  /** Path part for operation `saveBucket()` */
  static readonly SaveBucketPath = '/api/cloud/bucket/{bucket}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `saveBucket()` instead.
   *
   * This method doesn't expect any request body.
   */
  saveBucket$Response(params: SaveBucket$Params, context?: HttpContext): Observable<StrictHttpResponse<number>> {
    return saveBucket(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `saveBucket$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  saveBucket(params: SaveBucket$Params, context?: HttpContext): Observable<number> {
    return this.saveBucket$Response(params, context).pipe(
      map((r: StrictHttpResponse<number>): number => r.body)
    );
  }

  /** Path part for operation `downloadFile()` */
  static readonly DownloadFilePath = '/api/cloud/download/bucket/{bucket}/document/{document}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `downloadFile()` instead.
   *
   * This method doesn't expect any request body.
   */
  downloadFile$Response(params: DownloadFile$Params, context?: HttpContext): Observable<StrictHttpResponse<void>> {
    return downloadFile(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `downloadFile$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  downloadFile(params: DownloadFile$Params, context?: HttpContext): Observable<void> {
    return this.downloadFile$Response(params, context).pipe(
      map((r: StrictHttpResponse<void>): void => r.body)
    );
  }

  /** Path part for operation `downloadFile2()` */
  static readonly DownloadFile2Path = '/api/cloud/download-v2/bucket/{bucket}/document/{document}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `downloadFile2()` instead.
   *
   * This method doesn't expect any request body.
   */
  downloadFile2$Response(params: DownloadFile2$Params, context?: HttpContext): Observable<StrictHttpResponse<string>> {
    return downloadFile2(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `downloadFile2$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  downloadFile2(params: DownloadFile2$Params, context?: HttpContext): Observable<string> {
    return this.downloadFile2$Response(params, context).pipe(
      map((r: StrictHttpResponse<string>): string => r.body)
    );
  }

  /** Path part for operation `findAllDocuments()` */
  static readonly FindAllDocumentsPath = '/api/cloud/bucket/{bucket}/documents';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `findAllDocuments()` instead.
   *
   * This method doesn't expect any request body.
   */
  findAllDocuments$Response(params: FindAllDocuments$Params, context?: HttpContext): Observable<StrictHttpResponse<Array<string>>> {
    return findAllDocuments(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `findAllDocuments$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  findAllDocuments(params: FindAllDocuments$Params, context?: HttpContext): Observable<Array<string>> {
    return this.findAllDocuments$Response(params, context).pipe(
      map((r: StrictHttpResponse<Array<string>>): Array<string> => r.body)
    );
  }

  /** Path part for operation `loadDocument()` */
  static readonly LoadDocumentPath = '/api/cloud/bucket/{bucket}/document/{document}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `loadDocument()` instead.
   *
   * This method doesn't expect any request body.
   */
  loadDocument$Response(params: LoadDocument$Params, context?: HttpContext): Observable<StrictHttpResponse<string>> {
    return loadDocument(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `loadDocument$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  loadDocument(params: LoadDocument$Params, context?: HttpContext): Observable<string> {
    return this.loadDocument$Response(params, context).pipe(
      map((r: StrictHttpResponse<string>): string => r.body)
    );
  }

}
