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
import {DichiarazioneTutelatiComponent} from "../ricerca-tutore/dichiarazione-tutelati/dichiarazione-tutelati.component";
import {TutelatoComponent} from "./tutelato/tutelato.component";
import {SostegnoComponent} from './sostegno/sostegno.component';
import {TutoreRiepilogoComponent} from "./riepilogo/tutore-riepilogo.component";
import {SostegnoRiepilogoComponent} from "./sostegno-riepilogo/sostegno-riepilogo.component";
import {SharedModule} from "../shared/shared.module";
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
    DichiarazioneTutelatiComponent,
    TutelatoComponent,
    TutoreRiepilogoComponent,
    SostegnoComponent,
    SostegnoRiepilogoComponent
  ]
})
export class RicercaTutoreModule { }
