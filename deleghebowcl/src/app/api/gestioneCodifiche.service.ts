/**
 * Deleghe
 * Api risorse per leggere le codifiche tipo e stato
 *
 * OpenAPI spec version: 1.0.0
 *
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */
/* tslint:disable:no-unused-variable member-ordering */
/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

import { Inject, Injectable, Optional } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams,
         HttpResponse, HttpEvent } from '@angular/common/http';
import { CustomHttpUrlEncodingCodec } from './encoder';

import { Observable } from 'rxjs/Observable';

import { DichiarazioneStato } from '../model/dichiarazioneStato';
import { DichiarazioneTipo } from '../model/dichiarazioneTipo';
import { DelegaStato } from '../model/delegaStato';
import { DelegaTipo } from '../model/delegaTipo';
import { Errore } from '../model/errore';

import { BASE_PATH, COLLECTION_FORMATS } from './variables';
import { Configuration } from './configuration';
import {environment} from '../../environments/environment';
import { ArrayType } from '@angular/compiler';
import { map } from 'rxjs/operators';
import { DocumentoTipo } from '../model/documentoTipo';
import { InformativaConsensi } from '../model/informativaConsensi';


@Injectable()
export class GestioneCodificheService {

    protected basePath = environment.delegheboUrl;
    public defaultHeaders = new HttpHeaders();
    public configuration = new Configuration();

    constructor(protected httpClient: HttpClient, @Optional()@Inject(BASE_PATH) basePath: string, @Optional() configuration: Configuration) {
        if (basePath) {
            this.basePath = basePath;
        }
        if (configuration) {
            this.configuration = configuration;
            this.basePath = basePath || configuration.basePath || this.basePath;
        }
    }

    /**
     *
     * Leggi elenco stato dichiarazione
     * @param shibIdentitaCodiceFiscale
     * @param xRequestID
     * @param xCodiceServizio
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public statiDichiarazioneGet(shibIdentitaCodiceFiscale: string, xRequestID: string, xCodiceServizio: string, observe?: 'body', reportProgress?: boolean): Observable<DichiarazioneStato[]>;
    public statiDichiarazioneGet(shibIdentitaCodiceFiscale: string, xRequestID: string, xCodiceServizio: string, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<DichiarazioneStato[]>>;
    public statiDichiarazioneGet(shibIdentitaCodiceFiscale: string, xRequestID: string, xCodiceServizio: string, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<DichiarazioneStato[]>>;
    public statiDichiarazioneGet(shibIdentitaCodiceFiscale: string, xRequestID: string, xCodiceServizio: string, observe: any = 'body', reportProgress: boolean = true ): Observable<any> {

        if (shibIdentitaCodiceFiscale === null || shibIdentitaCodiceFiscale === undefined) {
            throw new Error('Required parameter shibIdentitaCodiceFiscale was null or undefined when calling statiDichiarazioneGet.');
        }

        if (xRequestID === null || xRequestID === undefined) {
            throw new Error('Required parameter xRequestID was null or undefined when calling statiDichiarazioneGet.');
        }

        if (xCodiceServizio === null || xCodiceServizio === undefined) {
            throw new Error('Required parameter xCodiceServizio was null or undefined when calling statiDichiarazioneGet.');
        }

        let headers = this.defaultHeaders;
        if (xRequestID !== undefined && xRequestID !== null) {
            headers = headers.set('X-Request-ID', String(xRequestID));
        }
        if (xCodiceServizio !== undefined && xCodiceServizio !== null) {
            headers = headers.set('X-Codice-Servizio', String(xCodiceServizio));
        }

        // to determine the Accept header
        let httpHeaderAccepts: string[] = [];
        const httpHeaderAcceptSelected: string | undefined = this.configuration.selectHeaderAccept(httpHeaderAccepts);
        if (httpHeaderAcceptSelected != undefined) {
            headers = headers.set('Accept', httpHeaderAcceptSelected);
        }

        // to determine the Content-Type header
        const consumes: string[] = [
        ];

        return this.httpClient.get<DichiarazioneStato[]>(`${this.basePath}/codifiche/statiDichiarazione`,
            {
                withCredentials: this.configuration.withCredentials,
                headers: headers,
                observe: observe,
                reportProgress: reportProgress
            }
        );
    }


    /**
     *
     * Leggi elenco documenti identita
     * @param shibIdentitaCodiceFiscale
     * @param xRequestID
     * @param xCodiceServizio
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public descrizioneDocumentoGet(shibIdentitaCodiceFiscale: string, xRequestID: string, xCodiceServizio: string, observe?: 'body', reportProgress?: boolean): Observable<DocumentoTipo[]>;
    public descrizioneDocumentoGet(shibIdentitaCodiceFiscale: string, xRequestID: string, xCodiceServizio: string, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<DocumentoTipo[]>>;
    public descrizioneDocumentoGet(shibIdentitaCodiceFiscale: string, xRequestID: string, xCodiceServizio: string, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<DocumentoTipo[]>>;
    public descrizioneDocumentoGet(shibIdentitaCodiceFiscale: string, xRequestID: string, xCodiceServizio: string, observe: any = 'body', reportProgress: boolean = true ): Observable<any> {

        if (shibIdentitaCodiceFiscale === null || shibIdentitaCodiceFiscale === undefined) {
            throw new Error('Required parameter shibIdentitaCodiceFiscale was null or undefined when calling descrizioneDocumentoGet.');
        }

        if (xRequestID === null || xRequestID === undefined) {
            throw new Error('Required parameter xRequestID was null or undefined when calling descrizioneDocumentoGet.');
        }

        if (xCodiceServizio === null || xCodiceServizio === undefined) {
            throw new Error('Required parameter xCodiceServizio was null or undefined when calling descrizioneDocumentoGet.');
        }

        let headers = this.defaultHeaders;
        if (xRequestID !== undefined && xRequestID !== null) {
            headers = headers.set('X-Request-ID', String(xRequestID));
        }
        if (xCodiceServizio !== undefined && xCodiceServizio !== null) {
            headers = headers.set('X-Codice-Servizio', String(xCodiceServizio));
        }

        // to determine the Accept header
        let httpHeaderAccepts: string[] = [];
        const httpHeaderAcceptSelected: string | undefined = this.configuration.selectHeaderAccept(httpHeaderAccepts);
        if (httpHeaderAcceptSelected != undefined) {
            headers = headers.set('Accept', httpHeaderAcceptSelected);
        }

        // to determine the Content-Type header
        const consumes: string[] = [
        ];

        return this.httpClient.get<DocumentoTipo[]>(`${this.basePath}/codifiche/descrioneDocumento`,
            {
                withCredentials: this.configuration.withCredentials,
                headers: headers,
                observe: observe,
                reportProgress: reportProgress
            }
        );
    }




        /**
     *
     * Leggi elenco tipi dichiarazione
     * @param shibIdentitaCodiceFiscale
     * @param xRequestID
     * @param xCodiceServizio
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public tipiDichiarazioneGet(shibIdentitaCodiceFiscale: string, xRequestID: string, xCodiceServizio: string, observe?: 'body', reportProgress?: boolean): Observable<DichiarazioneTipo[]>;
    public tipiDichiarazioneGet(shibIdentitaCodiceFiscale: string, xRequestID: string, xCodiceServizio: string, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<DichiarazioneTipo[]>>;
    public tipiDichiarazioneGet(shibIdentitaCodiceFiscale: string, xRequestID: string, xCodiceServizio: string, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<DichiarazioneTipo[]>>;
    public tipiDichiarazioneGet(shibIdentitaCodiceFiscale: string, xRequestID: string, xCodiceServizio: string, observe: any = 'body', reportProgress: boolean = false ): Observable<any> {

        if (shibIdentitaCodiceFiscale === null || shibIdentitaCodiceFiscale === undefined) {
            throw new Error('Required parameter shibIdentitaCodiceFiscale was null or undefined when calling tipiDichiarazioneGet.');
        }

        if (xRequestID === null || xRequestID === undefined) {
            throw new Error('Required parameter xRequestID was null or undefined when calling tipiDichiarazioneGet.');
        }

        if (xCodiceServizio === null || xCodiceServizio === undefined) {
            throw new Error('Required parameter xCodiceServizio was null or undefined when calling tipiDichiarazioneGet.');
        }

        let headers = this.defaultHeaders;
        if (xRequestID !== undefined && xRequestID !== null) {
            headers = headers.set('X-Request-ID', String(xRequestID));
        }
        if (xCodiceServizio !== undefined && xCodiceServizio !== null) {
            headers = headers.set('X-Codice-Servizio', String(xCodiceServizio));
        }

        // to determine the Accept header
        let httpHeaderAccepts: string[] = [];
        const httpHeaderAcceptSelected: string | undefined = this.configuration.selectHeaderAccept(httpHeaderAccepts);
        if (httpHeaderAcceptSelected != undefined) {
            headers = headers.set('Accept', httpHeaderAcceptSelected);
        }

        // to determine the Content-Type header
        const consumes: string[] = [
        ];

        return this.httpClient.get<DichiarazioneTipo[]>(`${this.basePath}/codifiche/tipoDichiarazione`,
            {
                withCredentials: this.configuration.withCredentials,
                headers: headers,
                observe: observe,
                reportProgress: reportProgress
            }
        );
    }



        /**
     *
     * Leggi elenco stato dichiarazione
     * @param shibIdentitaCodiceFiscale
     * @param xRequestID
     * @param xCodiceServizio
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public statiDelegaGet(shibIdentitaCodiceFiscale: string, xRequestID: string, xCodiceServizio: string, observe?: 'body', reportProgress?: boolean): Observable<DelegaStato[]>;
    public statiDelegaGet(shibIdentitaCodiceFiscale: string, xRequestID: string, xCodiceServizio: string, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<DelegaStato[]>>;
    public statiDelegaGet(shibIdentitaCodiceFiscale: string, xRequestID: string, xCodiceServizio: string, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<DelegaStato[]>>;
    public statiDelegaGet(shibIdentitaCodiceFiscale: string, xRequestID: string, xCodiceServizio: string, observe: any = 'body', reportProgress: boolean = true ): Observable<any> {

        if (shibIdentitaCodiceFiscale === null || shibIdentitaCodiceFiscale === undefined) {
            throw new Error('Required parameter shibIdentitaCodiceFiscale was null or undefined when calling statiDelegaGet.');
        }

        if (xRequestID === null || xRequestID === undefined) {
            throw new Error('Required parameter xRequestID was null or undefined when calling statiDelegaGet.');
        }

        if (xCodiceServizio === null || xCodiceServizio === undefined) {
            throw new Error('Required parameter xCodiceServizio was null or undefined when calling statiDelegaGet.');
        }

        let headers = this.defaultHeaders;
        if (xRequestID !== undefined && xRequestID !== null) {
            headers = headers.set('X-Request-ID', String(xRequestID));
        }
        if (xCodiceServizio !== undefined && xCodiceServizio !== null) {
            headers = headers.set('X-Codice-Servizio', String(xCodiceServizio));
        }

        // to determine the Accept header
        let httpHeaderAccepts: string[] = [];
        const httpHeaderAcceptSelected: string | undefined = this.configuration.selectHeaderAccept(httpHeaderAccepts);
        if (httpHeaderAcceptSelected != undefined) {
            headers = headers.set('Accept', httpHeaderAcceptSelected);
        }

        // to determine the Content-Type header
        const consumes: string[] = [
        ];

        return this.httpClient.get<DelegaStato[]>(`${this.basePath}/codifiche/statiDelega`,
            {
                withCredentials: this.configuration.withCredentials,
                headers: headers,
                observe: observe,
                reportProgress: reportProgress
            }
        );
    }


    /**
     *
     * Leggi elenco tipi Delega
     * @param shibIdentitaCodiceFiscale
     * @param xRequestID
     * @param xCodiceServizio
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public tipiDelegaGet(shibIdentitaCodiceFiscale: string, xRequestID: string, xCodiceServizio: string, observe?: 'body', reportProgress?: boolean): Observable<DelegaTipo[]>;
    public tipiDelegaGet(shibIdentitaCodiceFiscale: string, xRequestID: string, xCodiceServizio: string, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<DelegaTipo[]>>;
    public tipiDelegaGet(shibIdentitaCodiceFiscale: string, xRequestID: string, xCodiceServizio: string, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<DelegaTipo[]>>;
    public tipiDelegaGet(shibIdentitaCodiceFiscale: string, xRequestID: string, xCodiceServizio: string, observe: any = 'body', reportProgress: boolean = false ): Observable<any> {

        if (shibIdentitaCodiceFiscale === null || shibIdentitaCodiceFiscale === undefined) {
            throw new Error('Required parameter shibIdentitaCodiceFiscale was null or undefined when calling tipiDelegaGet.');
        }

        if (xRequestID === null || xRequestID === undefined) {
            throw new Error('Required parameter xRequestID was null or undefined when calling tipiDelegaGet.');
        }

        if (xCodiceServizio === null || xCodiceServizio === undefined) {
            throw new Error('Required parameter xCodiceServizio was null or undefined when calling tipiDelegaGet.');
        }

        let headers = this.defaultHeaders;
        if (xRequestID !== undefined && xRequestID !== null) {
            headers = headers.set('X-Request-ID', String(xRequestID));
        }
        if (xCodiceServizio !== undefined && xCodiceServizio !== null) {
            headers = headers.set('X-Codice-Servizio', String(xCodiceServizio));
        }

        // to determine the Accept header
        let httpHeaderAccepts: string[] = [];
        const httpHeaderAcceptSelected: string | undefined = this.configuration.selectHeaderAccept(httpHeaderAccepts);
        if (httpHeaderAcceptSelected != undefined) {
            headers = headers.set('Accept', httpHeaderAcceptSelected);
        }

        // to determine the Content-Type header
        const consumes: string[] = [
        ];

        return this.httpClient.get<DelegaTipo[]>(`${this.basePath}/codifiche/tipiDelega`,
            {
                withCredentials: this.configuration.withCredentials,
                headers: headers,
                observe: observe,
                reportProgress: reportProgress
            }
        );
    }

    /**
     *
     * Leggi informativa consensi
     * @param shibIdentitaCodiceFiscale
     * @param xRequestID
     * @param xCodiceServizio
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public getInformativaConsensiList(shibIdentitaCodiceFiscale: string, xRequestID: string, xCodiceServizio: string, observe?: 'body', reportProgress?: boolean): Observable<InformativaConsensi[]>;
    public getInformativaConsensiList(shibIdentitaCodiceFiscale: string, xRequestID: string, xCodiceServizio: string, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<InformativaConsensi[]>>;
    public getInformativaConsensiList(shibIdentitaCodiceFiscale: string, xRequestID: string, xCodiceServizio: string, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<InformativaConsensi[]>>;
    public getInformativaConsensiList(shibIdentitaCodiceFiscale: string, xRequestID: string, xCodiceServizio: string, observe: any = 'body', reportProgress: boolean = false ): Observable<any> {

        if (shibIdentitaCodiceFiscale === null || shibIdentitaCodiceFiscale === undefined) {
            throw new Error('Required parameter shibIdentitaCodiceFiscale was null or undefined when calling getInformativaConsensiList.');
        }

        if (xRequestID === null || xRequestID === undefined) {
            throw new Error('Required parameter xRequestID was null or undefined when calling getInformativaConsensiList.');
        }

        if (xCodiceServizio === null || xCodiceServizio === undefined) {
            throw new Error('Required parameter xCodiceServizio was null or undefined when calling getInformativaConsensiList.');
        }

        let headers = this.defaultHeaders;
        if (xRequestID !== undefined && xRequestID !== null) {
            headers = headers.set('X-Request-ID', String(xRequestID));
        }
        if (xCodiceServizio !== undefined && xCodiceServizio !== null) {
            headers = headers.set('X-Codice-Servizio', String(xCodiceServizio));
        }

        // to determine the Accept header
        let httpHeaderAccepts: string[] = [];
        const httpHeaderAcceptSelected: string | undefined = this.configuration.selectHeaderAccept(httpHeaderAccepts);
        if (httpHeaderAcceptSelected != undefined) {
            headers = headers.set('Accept', httpHeaderAcceptSelected);
        }

        // to determine the Content-Type header
        const consumes: string[] = [
        ];

        return this.httpClient.get<InformativaConsensi[]>(`${this.basePath}/codifiche/informativaConsensi`,
            {
                withCredentials: this.configuration.withCredentials,
                headers: headers,
                observe: observe,
                reportProgress: reportProgress
            }
        );
    }
}
