import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {tap} from 'rxjs/operators';
import {Injectable} from '@angular/core';
import {LoggerService} from '../service/logger.service';
/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
@Injectable({providedIn: 'root'})
export class LoggingInterceptor implements HttpInterceptor {
  constructor(private logger: LoggerService) {}
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    this.logger.info('--> Logging Interceptor:' + JSON.stringify(req.headers.keys()));
    this.logger.info('--> Logging Interceptor:' + req.headers.get('Iride2_id')); // TODO: impostato fisso dall'AuthInter
    return next.handle(req)
      .pipe( tap(
          event => {
            this.logger.info('Logging interceptor', JSON.stringify(event));
          }
        ));
  }
}
