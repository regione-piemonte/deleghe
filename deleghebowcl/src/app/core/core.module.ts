import {NgModule} from '@angular/core';
import {HeaderBackofficeComponent} from './header/header.backoffice';
import {SharedModule} from '../shared/shared.module';
import {ErrorPageComponent} from './error/error-page.component';
import {AlertComponent} from './alert/alert.component';
import {CommonModule} from '@angular/common';
import {MessageComponent} from './messages/message.component';
import {FootbarComponent} from "./footbar/footbar.component";
import {AppRoutingModule} from '../app-routing.module';

/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

@NgModule({
  imports: [
    CommonModule,
    SharedModule,
    AppRoutingModule,
  ],
  declarations: [
    // HeaderComponent,
    HeaderBackofficeComponent,
    ErrorPageComponent,
    AlertComponent,
    MessageComponent,
    FootbarComponent
  ],
  exports: [
    // HeaderComponent,
    HeaderBackofficeComponent,
    ErrorPageComponent,
    AlertComponent,
    MessageComponent,
    FootbarComponent,
  ],
  providers: [
  ]
})

export class CoreModule {}

