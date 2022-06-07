import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {AppRoutingModule} from '../app-routing.module';
import {
  MessagesModule,
  ProgressBarModule,
  ProgressSpinnerModule,
  SpinnerModule,
  TreeTableModule,
} from 'primeng/primeng';
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";
import {FormsModule} from "@angular/forms";
import {SharedModule} from '../shared/shared.module';
import {DichiarazioneGenitoreComponent} from './dichiarazione-genitore/dichiarazione-genitore.component';
import {FigliComponent} from './figli/figli.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {RiepilogoGenitoreComponent} from './riepilogo/riepilogo-genitore.component';
/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
@NgModule({
  imports: [
    CommonModule,
    AppRoutingModule,
    ProgressSpinnerModule,
    CommonModule,
    FontAwesomeModule,
    TreeTableModule,
    FormsModule,
    MessagesModule,
    AppRoutingModule,
    SpinnerModule,
    ProgressBarModule,
    SharedModule,
    NgbModule
  ],
  declarations: [
    DichiarazioneGenitoreComponent,
    FigliComponent,
    RiepilogoGenitoreComponent,
  ]
})
export class RicercaGenitoreModule { }
