import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {faTimes} from '@fortawesome/free-solid-svg-icons';
import {faChevronDown} from '@fortawesome/free-solid-svg-icons/faChevronDown';
import {ActivatedRoute, Router} from '@angular/router';
import {Message, MessageService} from 'primeng/api';
import {ShowMessagesService} from '../shared/service/show-messages.service';
import {AuthService} from '../auth/auth.service';
import { Utente } from '../model/utente';
import {GestioneCodificheService} from '../api/gestioneCodifiche.service';
import {InformativaConsensi} from 'src/app/model/informativaConsensi';

/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit, OnDestroy {

  constructor(private route: ActivatedRoute,
              protected router: Router,
              protected showMessagesService: ShowMessagesService,
              private authService: AuthService,
              protected codificheCitizenService: GestioneCodificheService,
              protected messageService: MessageService) {
                //RECUPERO DEL PARAMETRO TOKEN PASSATO DALLA URL PRINCIPALE
                  this.route.queryParams.subscribe(params => {
                    this.token = params['token'];

                });
              }

  public token: string;
  pdfInformativa;
  informativaConsensi: InformativaConsensi[] = [];
  times = faTimes;
  arrowDown = faChevronDown;
  msgs: Message[] = this.showMessagesService.getMsgs();
  user: Utente;
  public loading = false;

  @Input() isFilterShown = true;

  private daFareComponent = "DA_FARE";
  private tutteLeDelegheComponent = "TUTTE_LE_DELEGHE";

  private shownComponent = this.tutteLeDelegheComponent;

  ngOnInit() {

    this.authService.getTokenOperatoreLoggato(this.token);
    this.authService.getOperatoreLoggato();
    this.loadInformativaConsensi();
  }

  ngOnDestroy(): void {
    this.messageService.clear();
  }

  isDaFareShown() {
    return this.shownComponent === this.daFareComponent;
  }

  showDaFare() {
    this.shownComponent = this.daFareComponent
  }

  isTutteLeDelegheShown() {
    return this.shownComponent === this.tutteLeDelegheComponent;
  }

  showTutteLeDeleghe() {
    this.shownComponent = this.tutteLeDelegheComponent
  }



 loadInformativaConsensi() {

  let codiceFiscaleUtenteLoggato = this.authService.getToken();

  this.codificheCitizenService.getInformativaConsensiList(
    codiceFiscaleUtenteLoggato, '1', 'SANSOL', 'body', false
  ).subscribe((res) => {
      this.informativaConsensi = res;
      this.informativaConsensi.forEach(inf => {
        this.pdfInformativa = inf.pdf_informativa;
      });


    }, (error => {



    }));
}



}
