import {NgModule} from '@angular/core';
import {PreloadAllModules, RouterModule, Routes} from '@angular/router';
import {ErrorPageComponent} from './core/error/error-page.component';
import {UnauthorizedComponent} from './core/unauthorized/unauthorized.component';
import {AuthComponent} from './auth/auth.component';
import {HomeComponent} from './home/home.component';
import {NuovaDelegaComponent} from './entity/nuova-delega/nuova-delega.component';
import {TuttePraticheComponent} from './entity/tutte-pratiche/tutte-pratiche.component';
import {RicercaComponent} from './entity/nuova-delega/ricerca/ricerca.component';
import {NominativoSingoloComponent} from './entity/nuova-delega/nominativo-singolo/nominativo-singolo.component';
import {ServiziComponent} from './ricerca-delegante/servizi/servizi.component';
import {AdultoAdultoComponent} from './ricerca-delegante/adulto-adulto/adulto-adulto.component';
import {DichiarazioneTutelatiComponent} from './ricerca-tutore/dichiarazione-tutelati/dichiarazione-tutelati.component';
import {TutelatoComponent} from './ricerca-tutore/tutelato/tutelato.component';
import {SostegnoComponent} from './ricerca-tutore/sostegno/sostegno.component';
import {TutoreRiepilogoComponent} from './ricerca-tutore/riepilogo/tutore-riepilogo.component';
import {SostegnoRiepilogoComponent} from './ricerca-tutore/sostegno-riepilogo/sostegno-riepilogo.component';
import {DeleganteRiepilogoComponent} from './ricerca-delegante/riepilogo/delegante-riepilogo.component';
import {DettaglioDichiarazioniComponent} from './entity/dettaglio-dichiarazioni/dettaglio-dichiarazioni.component';
import {DichiarazioneGenitoreComponent} from './ricerca-genitore/dichiarazione-genitore/dichiarazione-genitore.component';
import {FigliComponent} from './ricerca-genitore/figli/figli.component';
import {RiepilogoGenitoreComponent} from './ricerca-genitore/riepilogo/riepilogo-genitore.component';
import {FeedbackComponent} from './entity/nuova-delega/feedback/feedback.component';
import { FeedbackMinoriComponent } from './entity/nuova-delega/feeback-minori/feedback-minori.component';
/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
const appRoutes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'auth', component: AuthComponent},
  {path: 'nuova-delega', component: NuovaDelegaComponent},
  {path: 'dettaglio-dichiarazioni/:uuid/:nPratica', component: DettaglioDichiarazioniComponent},
  {path: 'tutte-pratiche/:uuid/:component', component: TuttePraticheComponent},

  {path: 'ricerca/:component', component: RicercaComponent},
  {path: 'ricerca/:component/nominativo-singolo/:cf', component: NominativoSingoloComponent},

  {path: 'nuova-delega/delegante/:cf', component: AdultoAdultoComponent},
  {path: 'nuova-delega/delegante/:cf/servizi', component: ServiziComponent},
  {path: 'nuova-delega/delegante/:cf/riepilogo', component: DeleganteRiepilogoComponent},

  {path: 'nuova-delega/genitore/:cf', component: DichiarazioneGenitoreComponent},
  {path: 'nuova-delega/genitore/:cf/figli', component: FigliComponent},
  {path: 'nuova-delega/genitore/:cf/riepilogo', component: RiepilogoGenitoreComponent},

  {path: 'nuova-delega/tutore/:cf', component: DichiarazioneTutelatiComponent},
  {path: 'nuova-delega/tutore/:cf/tutelato', component: TutelatoComponent},
  {path: 'nuova-delega/tutore/:cf/sostegno', component: SostegnoComponent},
  {path: 'nuova-delega/tutore/:cf/riepilogo', component: TutoreRiepilogoComponent},
  {path: 'nuova-delega/sostegno/:cf/riepilogo', component: SostegnoRiepilogoComponent},

  {path: 'feedback', component: FeedbackComponent},
  {path: 'feedback-minori/:nrPratica/:uuid/:success/:errore', component: FeedbackMinoriComponent},

  {path: 'unauthorized', component: UnauthorizedComponent, data: {message: 'Utente non abilitato'}},
  {path: '**', component: ErrorPageComponent, data: {message: 'Pagina non trovata'}},


];


@NgModule({
  imports: [
    RouterModule.forRoot(appRoutes, {preloadingStrategy: PreloadAllModules, enableTracing: false})
  ],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
