import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {AppComponent} from './app.component';
import {AppRoutingModule} from './app-routing.module';
import {AuthInterceptor} from './shared/interceptor/auth.interceptor';
import {HttpErrorInterceptor} from './shared/interceptor/http-error.interceptor';
import {SharedModule} from './shared/shared.module';
import {CoreModule} from './core/core.module';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {NgxSpinnerService} from 'ngx-spinner';
import {LoggerService} from './shared/service/logger.service';
import {ConsoleLoggerService} from './shared/service/console-logger.service';
import {UnauthorizedComponent} from './core/unauthorized/unauthorized.component';
import {AuthComponent} from './auth/auth.component';
import {AuthGuard} from './auth/auth-guard.service';
import {ToastModule} from 'primeng/toast';
import {MessageService} from 'primeng/api';
import {HomeModule} from './home/home.module';
import {ShowMessagesService} from './shared/service/show-messages.service';
import {EntityModule} from './entity/entity.module';
import {StateService} from './shared/service/state.service';
import {ModalService} from './shared/modal/modal.service';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {GestioneDichiarazioniService} from './api/gestioneDichiarazioni.service';
import {DichiarazioneCitizenService} from './shared/service/dichiarazione-citizen.service';
import { DefaultService } from './api/default.service';
import { RicercaComponent } from './entity/nuova-delega/ricerca/ricerca.component';
import {TreeTableModule} from 'primeng/treetable';
import {DialogComponent} from './shared/modal/dialog/dialog.component';
import { DettaglioDichiarazioniComponent } from './entity/dettaglio-dichiarazioni/dettaglio-dichiarazioni.component';
import {MessagesModule, ProgressSpinnerModule} from 'primeng/primeng';
import {RicercaDeleganteModule} from "./ricerca-delegante/ricerca-delegante.module";
import {RicercaTutoreModule} from "./ricerca-tutore/ricerca-tutore.module";
import {RicercaGenitoreModule} from './ricerca-genitore/ricerca-genitore.module';
import {GestioneCodificheService } from './api/gestioneCodifiche.service';
import { GestioneComuniService } from './api/gestioneComuni.service';

/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
@NgModule({
  declarations: [
    AppComponent,
    UnauthorizedComponent,
    AuthComponent,
    RicercaComponent,
    DialogComponent,
    DettaglioDichiarazioniComponent
  ],
  imports: [
    TreeTableModule,
    BrowserModule,
    NgbModule,
    BrowserAnimationsModule,
    ToastModule,
    HttpClientModule,
    AppRoutingModule,
    SharedModule, // forRoot() se ci fossero servizi in SINGLETON oppure Inject->provideIn->root
    CoreModule, HomeModule, EntityModule, ProgressSpinnerModule,   // forRoot() se ci fossero servizi in SINGLETON oppure Inject->provideIn->root
    RicercaDeleganteModule,
    RicercaTutoreModule,
    RicercaGenitoreModule, MessagesModule,
  ],
  providers: [
    NgxSpinnerService, // Servizio Spinner
    MessageService,
    DefaultService,
    DichiarazioneCitizenService,
    ShowMessagesService,
    GestioneDichiarazioniService,
    GestioneCodificheService,
    GestioneComuniService,
    ModalService,
    StateService,
    AuthGuard,

    {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true},          // Aggiungo il token nell'header

    {provide: HTTP_INTERCEPTORS, useClass: HttpErrorInterceptor, multi: true},        // Verifico gli errori Http
    {provide: LoggerService, useClass: ConsoleLoggerService },
  ],
  entryComponents: [DialogComponent],
  bootstrap: [AppComponent]
})

export class AppModule {
}
