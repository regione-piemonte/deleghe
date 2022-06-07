import {Component, OnInit} from '@angular/core';
import {MessageService} from 'primeng/api';
import {ActivatedRoute, Data} from '@angular/router';
import {Alert, AlertType} from '../../model/alert';
import {BroadcastService} from '../../shared/service/broadcast.service';
import {LoggerService} from '../../shared/service/logger.service';

/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

@Component({
  selector: 'app-message',
  templateUrl: './message.component.html',
  styles: [`
    :host ::ng-deep button {
      margin-right: .25em;
    }

    :host ::ng-deep .custom-toast .ui-toast-message {
      color: #ffffff;
      background: #FC466B;
      background: -webkit-linear-gradient(to right, #3F5EFB, #FC466B);
      background: linear-gradient(to right, #3F5EFB, #FC466B);
    }

    :host ::ng-deep .custom-toast .ui-toast-close-icon {
      color: #ffffff;
    }
  `],
  providers: [MessageService]
})
export class MessageComponent implements OnInit {

  constructor(private messageService: MessageService,
              private route: ActivatedRoute,
              private logger: LoggerService,
              private broadCastServcie: BroadcastService) {
  }

  onConfirm() {
    this.messageService.clear('c');
  }

  onReject() {
    this.messageService.clear('c');
  }

  clear() {
    this.messageService.clear();
  }

  ngOnInit() {

    this.route.data.subscribe(
      (data: Data) => {
        if (data['message'] != null) {
          this.messageService.add(
            {key: 'tc', severity: 'error', summary: 'Avviso', detail: data['message']});
        }
      },
      (error1) => this.logger.error('[ErrorPageComponent] errore:' + error1)
    );


    this.broadCastServcie.getMessage()
      .subscribe((alert: Alert) => {
        if (!alert) {

          this.messageService.clear('c');
          return;
        }


        this.messageService.add(
          {key: 'c', severity: this.cssClass(alert), summary: 'Avviso', detail: alert.message});
      });
  }

  cssClass(alert: Alert) {
    if (!alert) {
      return;
    }


    switch (alert.type) {
      case AlertType.Success:
        return 'success';
      case AlertType.Error:
        return 'error';
      case AlertType.Info:
        return 'info';
      case AlertType.Warning:
        return 'warn';
    }
  }
}
