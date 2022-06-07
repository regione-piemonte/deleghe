import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';

/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

@Component({
  selector: 'app-footbar',
  templateUrl: './footbar.component.html',
  styleUrls: ['./footbar.component.css']
})
export class FootbarComponent implements OnInit {

  constructor(
    protected router: Router
  ) { }

  ngOnInit() {
  }

  showFooter() {
    return (this.router.url !== '/home' && this.router.url !== '/auth');
  }

}
