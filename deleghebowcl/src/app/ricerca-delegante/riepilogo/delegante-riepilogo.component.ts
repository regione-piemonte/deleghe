import {Component, Input, OnInit} from '@angular/core';
import {faPen} from '@fortawesome/free-solid-svg-icons/faPen';
import {DefaultService} from '../../api/default.service';
import {ActivatedRoute, Router} from '@angular/router';
import {StateService} from '../../shared/service/state.service';
import {Delega} from '../../model/delega';
import {Cittadino} from '../../model/cittadino';
import {ServiziInfo} from '../../model/servizi';
import {DelegaServizio} from '../../model/delegaServizio';
import { formatDate } from '@angular/common';
import { AuthService } from 'src/app/auth/auth.service';
import {InformativaConsensi} from 'src/app/model/informativaConsensi';
import {GestioneCodificheService} from '../../api/gestioneCodifiche.service';
/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
@Component({
  selector: 'delegante-riepilogo',
  templateUrl: './delegante-riepilogo.component.html',
  styleUrls: ['./delegante-riepilogo.component.css']
})
export class DeleganteRiepilogoComponent implements OnInit {


  constructor(
    protected defaultService: DefaultService,
    protected router: Router,
    protected route: ActivatedRoute,
    protected stateService: StateService,
    protected authService: AuthService,
    protected codificheCitizenService: GestioneCodificheService,
  ) {
  }

  pen = faPen;

  informativaConsensi: InformativaConsensi[] = [];

  fiscalCode;
  citizen;

  dataFormattataInizio: string;
  dataFormattataFine: string;

  pdfInformativa;

  nomeOperatore;
  cognomeOperatore;

  get newDelega() {
    return this.stateService.data.newDelega;
  }

  set newDelega(newDelega) {
    this.stateService.data.newDelega = newDelega;
  }

  ngOnInit() {

    this.authService.getOperatoreLoggato();
    this.cognomeOperatore = localStorage.getItem('cognomeOperatore');
    this.nomeOperatore= localStorage.getItem('nomeOperatore');
    this.loadInformativaConsensi();

    if (this.workingCitizen) {

      this.onCitizenFound();
      this.citizen = this.workingCitizen;
    } else {

      this.onCitizenWasNotFound();
    }
  }

  onCitizenFound() {

    this.fiscalCode = this.workingCitizen.codice_fiscale;
    let newDelega = this.newDelega;


    this.newDelega.services.forEach(prova => {


    });



    if (!newDelega) {
      setTimeout(() => {
        this.router.navigate(['nuova-delega', 'delegante', this.fiscalCode]);
      }, 1000);
    } else {

    }
  }

  onCitizenWasNotFound() {
    this.router.navigate(['ricerca', 'delegante']);
  }

  get workingCitizen() {
    return this.stateService.data.workingCitizen;
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

        console.error('Errore nel caricamento della combobox tipi dichiarazione');
        console.error(error);

      }))
  }


  continua() {

    this.newDelega.citizen = this.citizen;
    let delega: any = {};

    delega.blocca_delega = false;


    delega.delegante = this.newDelega.citizen;
    delega.delegante.data_nascita=this.formattaDataPerAggiornamento(delega.delegante.data_nascita);



    let delegato: Cittadino = {};
    delegato.nome = this.newDelega.nome;
    delegato.cognome = this.newDelega.cognome;
    delegato.data_nascita = this.myDateParser(this.newDelega.dataNascita);
    delegato.sesso = this.newDelega.sesso;
    delegato.comune_nascita = this.newDelega.comuneDiNascita;
    delegato.codice_fiscale = this.newDelega.codiceFiscale.toUpperCase();

    delega.delegato = delegato;

    delega.tipo_delega = "ADULTO";


    delega.servizi = [];
    this.newDelega.services.forEach(service => {
      let serviceToAdd: DelegaServizio = {};
      this.dataFormattataInizio= this.myDateParser(service.data_inizio_delega);
      this.dataFormattataFine= this.myDateParser(service.data_fine_delega);

      serviceToAdd.data_inizio_delega = this.dataFormattataInizio;
      serviceToAdd.data_fine_delega = this.dataFormattataFine;
      serviceToAdd.codice_servizio = service.codice_servizio;
      serviceToAdd.grado_delega = service.grado_delega;


      delega.servizi.push(serviceToAdd);
    });


    let codiceFiscaleUtenteLoggato = this.authService.getToken();

    this.defaultService.delegaPost(codiceFiscaleUtenteLoggato, '1', 'SANSOL', delega, 'body')
      .subscribe(res => {
        this.stateService.data.feedback = {numeroPratica: res.numero_pratica};
        this.stateService.data.feedback.isSuccessfullCreated = true;
        this.stateService.data.feedback.provenienza = "ALTRO";
        this.stateService.data.feedback.nomeDelegante = res.delegante.nome;
        this.stateService.data.feedback.cognomeDelegante = res.delegante.cognome;
        this.stateService.data.feedback.nomeDelegato = res.delegato.nome;
        this.stateService.data.feedback.cognomeDelegato = res.delegato.cognome;
        this.stateService.data.feedback.tipologiaDelega = res.tipo_delega;
        this.stateService.data.feedback.numeroPraticaDelega = res.numero_pratica;
        this.stateService.data.feedback.DelegaServizio = res.servizi;
        this.router.navigate(['feedback']);
      }, (err) => {
        this.stateService.data.feedback = {isSuccessfullCreated: false};
        this.stateService.data.feedback.responseErrors = err.error;
        this.router.navigate(['feedback']);
      });
  }

  myDateParser(dateStr : string) : string {

    let giorno = dateStr.substring(0, 2);
    let mese = dateStr.substring(3, 5);
    let anno = dateStr.substring(6, 10);

    let validDate = anno + '-' + mese + '-' + giorno;
    return validDate;
  }

  getNormalizedForServerData(date: string) {
    let giorno = date.substring(0, 2);
    let mese = date.substring(3, 5);
    let anno = date.substring(6, 10);

    let validDate = anno + '-' + mese + '-' + giorno;
    let newdate = new Date(validDate);
    return newdate;
  }


  formattaDataPerAggiornamento(dataDaConvertire): string{
    const format = 'yyyy-MM-dd';
    const locale = 'en-US';

    const formattedDate = formatDate(dataDaConvertire, format, locale);
    return formattedDate;
  }

}
