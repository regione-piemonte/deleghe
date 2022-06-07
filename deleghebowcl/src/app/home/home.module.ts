import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeComponent } from './home.component';
import { DaFareComponent } from './da-fare/da-fare.component';
import { TutteLeDelegheComponent } from './tutte-le-deleghe/tutte-le-deleghe.component';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {MessagesModule, ProgressBarModule, SpinnerModule, TreeTableModule} from 'primeng/primeng';
import {FormsModule} from '@angular/forms';
import {AppRoutingModule} from '../app-routing.module';
/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
@NgModule({
  imports: [
    CommonModule,
    FontAwesomeModule,
    TreeTableModule,
    FormsModule,
    MessagesModule,
    AppRoutingModule,
    SpinnerModule,
    ProgressBarModule
  ],
  declarations: [HomeComponent, DaFareComponent, TutteLeDelegheComponent]
})
export class HomeModule { }
