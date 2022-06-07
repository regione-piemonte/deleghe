import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {ModalService} from '../../shared/modal/modal.service';
import {GestioneDichiarazioniService} from '../../api/gestioneDichiarazioni.service';
import {DichiarazioneCitizenService} from '../../shared/service/dichiarazione-citizen.service';
import {ShowMessagesService} from '../../shared/service/show-messages.service';
import {Cittadino, CittadinoOrdered} from '../../model/cittadino';
import {Dichiarazione} from '../../model/dichiarazione';
import {DefaultService} from '../../api/default.service';
import {StateService} from '../../shared/service/state.service';
import {el} from '@angular/platform-browser/testing/src/browser_util';
import {DichiarazioneDettaglio} from '../../model/dichiarazioneDettaglio';
import {DichiarazioneRuolo} from '../../model/dichiarazioneRuolo';
import {Documento} from '../../model/documento';
import {DocumentoTipo} from '../../model/documentoTipo';
import {DocumentoFile} from '../../model/documentoFile';
import { AuthService } from 'src/app/auth/auth.service';
import { formatDate } from '@angular/common';
import {InformativaConsensi} from 'src/app/model/informativaConsensi';
import {GestioneCodificheService} from '../../api/gestioneCodifiche.service';
/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
@Component({
  selector: 'app-tutore-riepilogo',
  templateUrl: './tutore-riepilogo.component.html',
  styleUrls: ['./tutore-riepilogo.component.css']
})
export class TutoreRiepilogoComponent implements OnInit {

  constructor(
    protected  route: ActivatedRoute,
    protected  router: Router,
    protected  defaultService: DefaultService,
    protected  modalService: ModalService,
    protected gestioneDichiarazioniService: GestioneDichiarazioniService,
    protected codificheCitizenService: GestioneCodificheService,
    protected stateService: StateService,
    private showMessagesService: ShowMessagesService,
    protected authService: AuthService,
  ) {
  }

  informativaConsensi: InformativaConsensi[] = [];
  loading = true;
  citizens;
  dichiarazione: Dichiarazione;
  fiscalCode;
  agreementPushed = false;
  citizen;
  dateOfBirth: Date;
  dataDiOggi: Date;
  dateNascitaPiu18: Date;
  dateAppoggio: Date;
  pdfInformativa;
  nomeOperatore;
  cognomeOperatore;
  cfTutelatoDelegante;
  cittadino: Cittadino;

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

  onCitizenWasNotFound() {
    this.router.navigate(['ricerca', 'tutore']);
  }

  onCitizenFound() {
    this.fiscalCode = this.workingCitizen.codice_fiscale;
    if (this.newDichiarazione && this.newDichiarazione.tutelatoList) {
      this.citizens = [this.newDichiarazione.citizen];
      this.newDichiarazione.tutelatoList.forEach(tutelato => {
        this.citizens.push(tutelato);
        this.testDelegato(tutelato);
      });
    } else {
      this.router.navigate(['nuova-delega', 'tutore', this.fiscalCode]);
    }
  }

  get newDichiarazione() {
    return this.stateService.data.newDichiarazione;
  }

  set newDichiarazione(newDichiarazione) {
    this.stateService.data.newDichiarazione = newDichiarazione;
  }

  failDelegaTest() {
    this.newDichiarazione.dichiarazioneTestFailed = true;
    this.router.navigate(['nuova-delega', 'tutore', this.fiscalCode, 'tutelato']);
  }

  delegatoTested = false;
  testedTutelato = 0;

  testDelegato(tutelato) {
    let filter = JSON.stringify({
      codiceFiscale: tutelato.codice_fiscale,
      sesso: tutelato.sesso,
      dataNascita: this.myDateParser(tutelato.data_nascita),
      comuneNascita: tutelato.cumune_nascita,
      cognome: tutelato.cognome,
      nome: tutelato.nome,
    });


    let codiceFiscaleUtenteLoggato = this.authService.getToken();

    this.defaultService.findCitizen(codiceFiscaleUtenteLoggato, '1', 'SANSOL', filter, 'body')
      .subscribe(res => {
        if (res && res.length > 0) {
          this.testedTutelato++;
          if (this.testedTutelato === this.newDichiarazione.tutelatoList.length) {
            this.delegatoTested = true;
            this.newDichiarazione.dichiarazioneTestFailed = false;
          }
        } else {
          this.failDelegaTest();
        }
      }, () => {
        this.failDelegaTest();
      });
  }

  myDateParser(dateStr : string) : string {

    let giorno = dateStr.substring(0, 2);
    let mese = dateStr.substring(3, 5);
    let anno = dateStr.substring(6, 10);

    let validDate = anno + '-' + mese + '-' + giorno;
    return validDate;
  }

  get workingCitizen() {
    return this.stateService.data.workingCitizen;
  }

  navigateAndLeftPositiveAlert(status) {
    this.showMessagesService.setMsgs([{severity: 'success', summary: '', detail: 'Testo Feed-back positivo ' + status}]);
    this.router.navigate(['/']);
  }

  changeStatus(status) {
    this.loading = true;
    let dich = this.dichiarazione;
    dich.dettagli = [];
    dich.stato.codice = status;


    let codiceFiscaleUtenteLoggato = this.authService.getToken();


    this.gestioneDichiarazioniService.cittadiniCfDichiarazioniUuidDichiarazionePut(
      codiceFiscaleUtenteLoggato,
      '1',
      'SANSOL',
      codiceFiscaleUtenteLoggato,
      this.dichiarazione.uuid,
      this.dichiarazione,
      'body'
    ).subscribe(() => this.navigateAndLeftPositiveAlert(status), () => this.navigateAndLeftPositiveAlert(status));
  }


  getNormalizedForServerData(date: string) {
    let strings = date.replace('-', '/');
    return new Date(strings);
  }

  public getFeedBack() {
    let dich: Dichiarazione = {};
    dich.stato_servizio_delega = "ATTIVA";
    dich.cittadino = new Cittadino();
    dich.cittadino.codice_fiscale = this.newDichiarazione.citizen.codice_fiscale.toUpperCase();
    dich.cittadino.cognome = this.newDichiarazione.citizen.cognome;
    dich.cittadino.comune_nascita = this.newDichiarazione.citizen.comune_nascita;
    dich.cittadino.data_nascita = this.newDichiarazione.citizen.data_nascita;
    dich.cittadino.nome = this.newDichiarazione.citizen.nome;
    dich.cittadino.sesso = this.newDichiarazione.citizen.sesso;


    if(this.newDichiarazione.citizen.numeroDocumento !=null || this.newDichiarazione.citizen.numeroDocumento != undefined){

        let documentoOggetto : Documento = {};
        dich.cittadino.documento = documentoOggetto;
        dich.cittadino.documento.data_rilascio = this.newDichiarazione.citizen.dataRilascio;
        dich.cittadino.documento.data_scadenza_doc = this.newDichiarazione.citizen.dataScadenza;
        dich.cittadino.documento.autorita = this.newDichiarazione.citizen.rilasciatoDa;
        dich.cittadino.documento.numero_documento = this.newDichiarazione.citizen.numeroDocumento;

        let documentoTipo : DocumentoTipo = {};
        dich.cittadino.documento.tipo = documentoTipo;
        dich.cittadino.documento.doc_desc =  this.newDichiarazione.citizen.tipoDocumento;
        if(this.newDichiarazione.citizen.tipoDocumento == 'Carta di identita'){
          dich.cittadino.documento.tipo.codice =  'TIPO_1';
        }
        if(this.newDichiarazione.citizen.tipoDocumento == 'Patente'){
          dich.cittadino.documento.tipo.codice =  'TIPO_2';
        }


    }else{

    }

    dich.dettagli = [];

    this.newDichiarazione.tutelatoList.forEach(tutelato => {
      const dataDiNascita = tutelato.data_nascita;
      this.dateOfBirth = new Date(dataDiNascita);




      let dettaglio: DichiarazioneDettaglio = {};
      dettaglio.genitore_tutore_curatore = dich.cittadino;
      if (this.isUnder18Years(this.dateOfBirth.getFullYear(), this.dateOfBirth.getMonth(), this.dateOfBirth.getDay())) {

        dettaglio.ruolo_genitore_tutore_curatore = {codice: 'TUTORE'};
      } else {

        dettaglio.ruolo_genitore_tutore_curatore = {codice: 'TUTORE_DI_UN_MAGIORE_INTER'};
      }

      let figlio: Cittadino = {};
      let idAura;
      figlio.nome = tutelato.nome;
      figlio.cognome = tutelato.cognome;
      figlio.codice_fiscale = tutelato.codice_fiscale.toUpperCase();
      this.cfTutelatoDelegante = tutelato.codice_fiscale.toUpperCase();
      figlio.data_nascita = this.myDateParser(tutelato.data_nascita);
      figlio.sesso = tutelato.sesso;
      figlio.comune_nascita = tutelato.cumune_nascita;
      idAura = this.cercaIdSuAura(tutelato.codice_fiscale.toUpperCase());
      if(idAura!=0){
        figlio.id_aura = idAura;
      }
      dettaglio.figlio_tutelato_curato = figlio;
      dettaglio.ruolo_figlio_tutelato_curato = {codice: 'TUTELATO'};

      dettaglio.stato = {codice: 'VALIDA'};

      dich.dettagli.push(dettaglio);
    });

    dich.stato = {codice: 'ATTIVA'};
    dich.data_inserimento = this.getNormalizedForServerData(this.newDichiarazione.data);
    dich.modo = {codice: 'SPORTELLO'};
    if (this.isUnder18Years(this.dateOfBirth.getFullYear(), this.dateOfBirth.getMonth(), this.dateOfBirth.getDay())) {

      dich.tipo = {codice: 'TUTELA'};
    } else {

      dich.tipo = {codice: 'TUTELA_MAG18'};
    }
    dich.numero_documento  = this.newDichiarazione.numeroDocumento;
    dich.autorita  = this.newDichiarazione.autorita;
    dich.data_inizio_tutela  = this.myDateParser(this.newDichiarazione.dataInizio);
    dich.data_fine_tutela  = this.myDateParser(this.newDichiarazione.dataFine);
    dich.provenienza='TUTELA';



    let createDich: any = {};
    createDich.dichiarazione = dich;


    let codiceFiscaleUtenteLoggato = this.authService.getToken();


    this.defaultService.dichiarazionePost(codiceFiscaleUtenteLoggato,
      '1',
      'SANSOL',
      createDich, 'body')
      .subscribe(res => {


        let filter = this.getFilterDelegante(this.newDichiarazione.citizen.codice_fiscale,this.cfTutelatoDelegante);
        this.defaultService.delegheGet(codiceFiscaleUtenteLoggato, '1', 'SANSOL', JSON.stringify(filter), 'body')
        .subscribe(resDeleghe => {
          resDeleghe.forEach(deleghe => {

              let filterUuid = this.getFilterUuid(deleghe.uuid);
              this.defaultService.delegaGet(codiceFiscaleUtenteLoggato, '1', 'SANSOL', JSON.stringify(filterUuid), 'body')
              .subscribe((resDelegheUuid) => {

              });
          });
        });


        this.router.navigate(['feedback-minori',res.npratica,res.uuid,'true','noError']);
      }, (err) => {

        let responseErrors = [];
        let codiceErrore;
        responseErrors = err.error;
        responseErrors.forEach(errore => {

          codiceErrore = errore.codice;

        });
        this.router.navigate(['feedback-minori','fallito','fallito','false',codiceErrore]);
      });


  }

  getFilterDelegante(cfDelegato, cfDelegante) {
    let filter: any = {};
    filter.delegato_codice_fiscale= cfDelegato;
    filter.delegante_codice_fiscale= cfDelegante;
    return filter;
  }

  getFilterUuid(uuid) {
    let filter: any = {};
    filter.uuid= uuid;
    return filter;
  }



  isUnder18Years(year, month, day) {
    let risultato: boolean;
    this.dateNascitaPiu18 = new Date(year + 18, month - 1, day);

    this.dataDiOggi = new Date();

    if(this.dateNascitaPiu18 >= this.dataDiOggi){

      risultato = true;

    }else{

      risultato = false;
    }
    return risultato;

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

  cercaIdSuAura(fiscalCode): number{

    let codiceFiscaleUtenteLoggato = this.authService.getToken();

    let idAura;

    let filter = JSON.stringify({
      codiceFiscale: fiscalCode,
    });

    this.defaultService.getIdAura(codiceFiscaleUtenteLoggato, '1', 'SANSOL', fiscalCode, 'body')
    .subscribe((res) => {
      if(res){
        this.cittadino = res[0];

        idAura = this.cittadino.id_aura;
      }else{

        idAura = 0;
      }

    }, (err) => {

    });
    return idAura;
  }

}
