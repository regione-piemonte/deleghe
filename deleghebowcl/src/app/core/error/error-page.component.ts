import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Data} from '@angular/router';
import {LoggerService} from '../../shared/service/logger.service';

/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

@Component({
  selector: 'app-error-page',
  templateUrl: './error-page.component.html',
  styleUrls: ['./error-page.component.css']
})
export class ErrorPageComponent implements OnInit {

  message = '';

  constructor(private route: ActivatedRoute, private logger: LoggerService) {}

  ngOnInit() {


    this.route.data.subscribe(
      (data: Data) => {
        if (data['message'] != null) {
        this.message = data['message'];
        }
      },
      (error) => this.logger.error('[ErrorPageComponent] errore:' + error)
    );
  }
}
