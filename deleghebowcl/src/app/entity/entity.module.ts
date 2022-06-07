import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {NuovaDelegaComponent} from './nuova-delega/nuova-delega.component';
import {AppRoutingModule} from '../app-routing.module';
import {MessagesModule, ProgressSpinnerModule, TreeTableModule} from 'primeng/primeng';
import {FormsModule} from '@angular/forms';
import {SharedModule} from '../shared/shared.module';
import {TuttePraticheComponent} from './tutte-pratiche/tutte-pratiche.component';
import {NominativoSingoloComponent} from "./nuova-delega/nominativo-singolo/nominativo-singolo.component";
import {BrowserModule} from "@angular/platform-browser";
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {FeedbackComponent} from './nuova-delega/feedback/feedback.component';
import { FeedbackMinoriComponent } from './nuova-delega/feeback-minori/feedback-minori.component';

/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

@NgModule({
  imports: [
    CommonModule,
    AppRoutingModule,
    ProgressSpinnerModule,
    FormsModule,
    SharedModule,
    TreeTableModule,
    BrowserModule,
    NgbModule,
    MessagesModule
  ],
  declarations: [
    NuovaDelegaComponent,
    NominativoSingoloComponent,
    TuttePraticheComponent,
    NominativoSingoloComponent,
    FeedbackComponent,
    FeedbackMinoriComponent,
  ],
  exports: [

  ]
})
export class EntityModule { }
