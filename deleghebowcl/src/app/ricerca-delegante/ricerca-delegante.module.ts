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
import {ServiziComponent} from './servizi/servizi.component';
import {AdultoAdultoComponent} from './adulto-adulto/adulto-adulto.component';
import {SharedModule} from '../shared/shared.module';
import {DeleganteRiepilogoComponent} from './riepilogo/delegante-riepilogo.component';
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
        SharedModule
    ],
  declarations: [
    ServiziComponent,
    AdultoAdultoComponent,
    DeleganteRiepilogoComponent,
  ]
})
export class RicercaDeleganteModule { }
