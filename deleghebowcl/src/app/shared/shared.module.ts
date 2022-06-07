import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {AccordionModule} from 'primeng/accordion';
import {TableModule} from 'primeng/table';
import {PanelModule} from 'primeng/panel';
import {GenderPipe} from './pipe/gender.pipe';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {library} from '@fortawesome/fontawesome-svg-core';
import {fas} from '@fortawesome/free-solid-svg-icons';
import {DropdowndDirective} from './directive/dropdownd.directive';
import {AutofocusDirective} from './directive/autofocus.directive';
import {ServiziPipe} from './pipe/servizi.pipe';
import {StatodichimgPipe} from './pipe/statodichimg.pipe';
import {StatodichPipe} from './pipe/statodich.pipe';
import {ToastModule} from 'primeng/toast';
import {NgxSpinnerModule} from 'ngx-spinner';
import {GenderImgPipe} from './pipe/genderimg.pipe';
import {GenderKidsPipe} from './pipe/genderkidsimg.pipe';
import {UpperDirective} from './directive/upper.directive';
import {StatodettaglioPipe} from './pipe/statodettaglio.pipe';
import {StatodettaglioimgPipe} from './pipe/statodettaglioimg.pipe';
import {CalendarModule} from 'primeng/primeng';
import {CommonModule} from '@angular/common';
import {ComuniComponent} from './components/comuni.component';
import {NuovaDelegaStageComponent} from './components/nuova-delega-stage/nuova-delega-stage.component';

/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
// Add an icon to the library for convenient access in other components
library.add(fas);

@NgModule({
  declarations: [
    GenderPipe,
    ServiziPipe,
    StatodichimgPipe,
    StatodichPipe,
    GenderImgPipe,
    StatodettaglioPipe,
    StatodettaglioimgPipe,
    GenderKidsPipe,
    DropdowndDirective,
    AutofocusDirective,
    UpperDirective,
    ComuniComponent,
    NuovaDelegaStageComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
  ],
  exports: [
    // Moduli Angular
    FontAwesomeModule,
    FormsModule,
    // Spinner
    NgxSpinnerModule,
    // Moduli NGPRime comuni a tutte le Features.
    ReactiveFormsModule,
    AccordionModule,
    TableModule,
    PanelModule,
    ToastModule,
    CalendarModule,
    // CUSTOM Pipe
    GenderPipe,
    ServiziPipe,
    StatodichimgPipe,
    StatodichPipe,
    GenderImgPipe,
    GenderKidsPipe,
    StatodettaglioPipe,
    StatodettaglioimgPipe,
    // CUSTOM Direttive
    DropdowndDirective,
    AutofocusDirective,
    UpperDirective,
    // CUSTOM Component
    ComuniComponent,
    NuovaDelegaStageComponent
  ],
  providers: [

  ]
})


export class SharedModule {}


