import {Observable, Subject} from 'rxjs';
import {Injectable} from '@angular/core';
import {Alert, AlertType} from '../../model/alert';
import {ShowMessagesService} from './show-messages.service';
/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
// In modalità Singleton
@Injectable({providedIn: 'root'})
export class BroadcastService {
  // Observable di tipo Behavior
  message = new Subject<Alert>();

  constructor() {}

  getMessage(): Observable<Alert> {
    return this.message.asObservable();
  }
  sendMessage(alert: Alert) {
    this.message.next(alert);
  }
}
