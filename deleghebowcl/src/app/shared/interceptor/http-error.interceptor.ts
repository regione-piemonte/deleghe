import {Injectable} from '@angular/core';
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/throw';
import 'rxjs/add/observable/of';
import {BroadcastService} from '../service/broadcast.service';
import {AlertType} from '../../model/alert';
import {AuthService} from '../../auth/auth.service';
import {LoggerService} from '../service/logger.service';
import {Errore} from '../../model/errore';
import {Router} from '@angular/router';
import { throwError } from 'rxjs';
import {forEach} from '@angular/router/src/utils/collection';

/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
@Injectable({providedIn: 'root'})
export class HttpErrorInterceptor implements HttpInterceptor {


  constructor(private broadCastService: BroadcastService,
              private logger: LoggerService,
              private auth: AuthService,
              private router: Router) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    this.logger.info('Intercepting Request ERROR:', request);
    return next.handle(request)
      .catch((err: HttpErrorResponse) => {
        if (err.error instanceof Error) {

          this.logger.info('An error occurred:', err.error.message);
          this.broadCastService.sendMessage(
            {type: AlertType.Error, message: 'Il servizio al momento non è disponibile.'});
        } else {

          this.logger.info(`Backend returned code ${err.status}`);
          switch (err.status) {
            case 0: {
              this.logger.info('Il servizio non è raggiungibile');
              this.broadCastService.sendMessage(
                {type: AlertType.Error, message: 'Il servizio al momento non è disponibile.'});
              break;
            }
            case 200: {
              this.logger.info('Operazione eseguita con successo');
              this.broadCastService.sendMessage(
                {type: AlertType.Success, message: 'Operazione eseguita con successo.'});
              break;
            }
            case 204: {
              this.logger.info('Empty queue');
              this.broadCastService.sendMessage(
                {type: AlertType.Error, message: 'Empty queue.'});
              break;
            }
            case 303: {
              this.logger.info('Dati non congruenti');
              this.broadCastService.sendMessage(
                {type: AlertType.Error, message: 'Dati non congruenti.'});
              break;
            }
            case 401: {
              this.logger.info('Utente non autorizzato a compiere l’operazione');
              this.broadCastService.sendMessage(
                {type: AlertType.Error, message: 'Utente non autorizzato a compiere l’operazione.'});
                this.router.navigate(['unauthorized']);
              break;
            }
            case 400: {
              this.logger.info('Bad Request');
              this.broadCastService.sendMessage(
                {type: AlertType.Error, message: 'Bad Request.'});
              break;
            }
            case 421: {
              this.logger.info('codice fiscale non valido (controllo su lunghezza)');
              this.broadCastService.sendMessage(
                {type: AlertType.Error, message: 'codice fiscale non valido (controllo su lunghezza).'});
              break;
            }
            case 404: {

              let errore: Array<Errore>;
              errore = <Errore[]>err.error;
              if (errore) {
                errore.forEach(value => {
                  if (value.code === 'NOT_FOUND') {
                    value.detail.forEach((detail) => {
                      this.broadCastService.sendMessage(
                        {type: AlertType.Info, message: detail.chiave+": "+detail.valore});
                    });
                  }
                });
              }
              this.logger.info(JSON.stringify(err.error));
              this.logger.info('ERRORE:' + JSON.stringify(errore));

              break;
            }

            case 405: {
              this.logger.info('Aura not allowed');
              this.auth.setAura('false');

              break;
            }
            case 500: {
              this.logger.info('Internal server error');
              this.broadCastService.sendMessage(
                {type: AlertType.Error, message: 'Internal server error.'});
              break;
            }
          }
        }


        return throwError(err);
      }) as any;
  }
}
