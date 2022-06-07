import { AuthService } from 'src/app/auth/auth.service';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Injectable, Injector} from '@angular/core';
import {Observable} from 'rxjs';
import {environment} from '../../../environments/environment';
/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


@Injectable({providedIn: 'root'})
export class AuthInterceptor implements HttpInterceptor {

  constructor(private injector: Injector) {

  }


  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let copiedReq;

    if (!environment.production || environment.fakeTokenProd) {

        let authService = this.injector.get(AuthService);
        copiedReq = req.clone({
          setHeaders:
          {

              Authorization: `${authService.getToken()}`
          }});

    } else {

      let authService = this.injector.get(AuthService);

      copiedReq = req.clone({
          setHeaders:{Authorization: `${authService.getToken()}`}
      });
    }


    return next.handle(copiedReq);
  }
}
