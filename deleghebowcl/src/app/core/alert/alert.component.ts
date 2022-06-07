import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {BroadcastService} from '../../shared/service/broadcast.service';
import {Alert, AlertType} from '../../model/alert';
import {ActivatedRoute, Data} from '@angular/router';
import {LoggerService} from '../../shared/service/logger.service';

/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

@Component({
  selector: 'app-alert',
  templateUrl: './alert.component.html',
  styleUrls: ['./alert.component.css']
})
export class AlertComponent implements OnInit, AfterViewInit {
  alerts: Alert[] = [];
  @ViewChild('lastElement') lastElement: HTMLElement;

  constructor(private route: ActivatedRoute, private logger: LoggerService, private broadCastServcie: BroadcastService) {
  }

  ngOnInit() {


    this.route.data.subscribe(
      (data: Data) => {
        if (data['message'] != null) {
          this.alerts.push(data['message']);
        }
      },
      (error1) => this.logger.error('[ErrorPageComponent] errore:' + error1)
    );


    this.broadCastServcie.getMessage()
      .subscribe((alert: Alert) => {
        if (!alert) {

          this.alerts = [];
          return;
        }

        this.alerts.push(alert);
        setTimeout(() => this.alerts = this.alerts.filter(x => x !== alert), 3000);

      });
  }

  removeAlert(alert: Alert) {
    this.alerts = this.alerts.filter(x => x !== alert);
  }

  cssClass(alert: Alert) {
    if (!alert) {
      return;
    }


    switch (alert.type) {
      case AlertType.Success:
        return 'alert alert-success';
      case AlertType.Error:
        return 'alert alert-danger';
      case AlertType.Info:
        return 'alert alert-info';
      case AlertType.Warning:
        return 'alert alert-warning';
    }
  }

  ngAfterViewInit() {
    if (this.lastElement) {
      const elem: any = this.lastElement.querySelector('[autofocus]');
      if (elem) {
        elem.focus();
      }
    }
  }
}
