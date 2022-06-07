import {Component, OnInit} from '@angular/core';
import {environment} from '../../../environments/environment';

/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

@Component({
  selector: 'app-unauthorized',
  templateUrl: './unauthorized.component.html',
  styleUrls: ['./unauthorized.component.css']
})
export class UnauthorizedComponent implements OnInit {
  private _logoutLink: String;

  constructor() { }

  ngOnInit() {
    this._logoutLink = environment.delegheboLogout;
  }

  get logoutLink(): String {
    return this._logoutLink;
  }

}
