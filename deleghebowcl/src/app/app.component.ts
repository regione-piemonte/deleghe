import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ActivatedRoute, Router} from '@angular/router';
import {AuthService} from './auth/auth.service';
import {LoggerService} from './shared/service/logger.service';
/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'Deleghe BO';

  constructor(private route: ActivatedRoute,
              private router: Router,
              private http: HttpClient,
              private authService: AuthService,
              private logger: LoggerService) {
  }

  ngOnInit() {

    this.authService.getOperatoreLoggatoAppComponent();
    this.logger.info('AppComponent: logger.info()');
    this.logger.warn('AppComponent: logger.warn()');
    this.logger.error('AppComponent: logger.error()');
  }
}
