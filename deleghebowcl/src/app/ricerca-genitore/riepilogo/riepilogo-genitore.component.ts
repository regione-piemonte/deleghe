import { forEach } from '@angular/router/src/utils/collection';
import {Component, Input, OnInit} from '@angular/core';
import {faPen} from '@fortawesome/free-solid-svg-icons/faPen';
import {DefaultService} from '../../api/default.service';
import {ActivatedRoute, Router} from '@angular/router';
import {StateService} from '../../shared/service/state.service';
import {Dichiarazione} from '../../model/dichiarazione';
import {DichiarazioneDettaglio} from '../../model/dichiarazioneDettaglio';
import {Cittadino} from '../../model/cittadino';
import {Documento} from '../../model/documento';
import {DocumentoTipo} from '../../model/documentoTipo';
import {DocumentoFile} from '../../model/documentoFile';
import { formatDate } from '@angular/common';
import { IfStmt } from '@angular/compiler';
import { Entita } from 'src/app/model/entita';
import { AuthService } from 'src/app/auth/auth.service';
import {InformativaConsensi} from 'src/app/model/informativaConsensi';
import {GestioneCodificheService} from '../../api/gestioneCodifiche.service';
/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

@Component({
  selector: 'riepilogo-genitore',
  templateUrl: './riepilogo-genitore.component.html',
  styleUrls: ['./riepilogo-genitore.component.css']
})
export class RiepilogoGenitoreComponent  {

  constructor(
    protected defaultService: DefaultService,
    protected router: Router,
    protected route: ActivatedRoute,
    protected stateService: StateService,
    protected authService: AuthService,
    protected codificheCitizenService: GestioneCodificheService,
  ) {
  }

  informativaConsensi: InformativaConsensi[] = [];
  isMono = false;
  isAgree = false;
  fiscalCode;
  citizen;
  pen = faPen;
  dateAppoggioFrom: Date;
  numeroPratica = "";
  pdfInformativa;
  nomeOperatore;
  cognomeOperatore;
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
      this.onCitizenWasNotFound()
    }
  }

  onCitizenFound() {
    this.citizen = this.workingCitizen;
    this.fiscalCode = this.workingCitizen.codice_fiscale;
    this.newDichiarazione.citizen = this.citizen;

    let comNascita = this.newDichiarazione.altroGenitore.comune_nascita;
    let cf = this.newDichiarazione.altroGenitore.codice_fiscale;

    if (!this.newDichiarazione) {

      this.router.navigate(['/ricerca','genitore'])
    } else {

    }
  }

  onCitizenWasNotFound() {
    this.router.navigate(['/ricerca','genitore'])
  }

  get workingCitizen() {
    return this.stateService.data.workingCitizen;
  }


  getNormalizedForServerData(date: string) {
    let strings = date.replace('-', '/');
    return new Date(strings);
  }

  public continua(){


    let isUltimoDaLanciare:boolean = false;
    let contatore: number = 0;
    let contaMinori = this.newDichiarazione.tutelatoList.length;
    let contaGenitori = this.newDichiarazione.altroGenitore.length;
    if(contaGenitori === undefined){

      contaGenitori = 0;
    }



    if(contaGenitori ==0 ){



        this.newDichiarazione.tutelatoList.forEach(minore => {
          if(this.newDichiarazione.citizen.codice_fiscale!=null){


            contatore = contatore+1;


            let dichiarazione : Dichiarazione = {};

            dichiarazione.dettagli = [];



            dichiarazione.data_inserimento = new Date();
            dichiarazione.stato_servizio_delega = "ATTIVA";
            dichiarazione.stato = {codice: "ATTIVA"};
            dichiarazione.modo =  {codice: "SPORTELLO"};
            dichiarazione.tipo =  {codice: "GENITOREMONO"};
            dichiarazione.provenienza='GENITORE';


            dichiarazione.cittadino = new Cittadino();
            dichiarazione.cittadino.codice_fiscale = minore.info.codice_fiscale.toUpperCase();
            if(minore.info.cit_id!=null){
              dichiarazione.cittadino.cit_id = minore.info.cit_id;
            }
            dichiarazione.cittadino.cognome = minore.info.cognome;
            dichiarazione.cittadino.nome = minore.info.nome;
            dichiarazione.cittadino.sesso = minore.info.sesso;
            dichiarazione.cittadino.data_nascita = minore.info.data_nascita;
            dichiarazione.cittadino.comune_nascita = minore.info.comune_nascita;



            let dichiarazioneDettaglio : DichiarazioneDettaglio = {};
            let idAura;

            dichiarazioneDettaglio.figlio_tutelato_curato =  new Cittadino();
            dichiarazioneDettaglio.figlio_tutelato_curato.codice_fiscale = minore.info.codice_fiscale.toUpperCase();
            dichiarazioneDettaglio.figlio_tutelato_curato.cit_id = minore.info.cit_id;
            dichiarazioneDettaglio.figlio_tutelato_curato.cognome = minore.info.cognome;
            dichiarazioneDettaglio.figlio_tutelato_curato.nome = minore.info.nome;
            dichiarazioneDettaglio.figlio_tutelato_curato.sesso = minore.info.sesso;
            dichiarazioneDettaglio.figlio_tutelato_curato.data_nascita = minore.info.data_nascita;
            dichiarazioneDettaglio.figlio_tutelato_curato.comune_nascita = minore.info.comune_nascita;
            idAura = this.cercaIdSuAura(minore.info.codice_fiscale.toUpperCase());
            if(idAura!=0){
              dichiarazioneDettaglio.figlio_tutelato_curato.id_aura = idAura;
            }



            dichiarazioneDettaglio.genitore_tutore_curatore =  new Cittadino();
            dichiarazioneDettaglio.genitore_tutore_curatore.codice_fiscale = this.newDichiarazione.citizen.codice_fiscale.toUpperCase();
            if(this.newDichiarazione.citizen.cit_id!=null){
              dichiarazioneDettaglio.genitore_tutore_curatore.cit_id = this.newDichiarazione.citizen.cit_id;
            }
            dichiarazioneDettaglio.genitore_tutore_curatore.cognome = this.newDichiarazione.citizen.cognome;
            dichiarazioneDettaglio.genitore_tutore_curatore.nome = this.newDichiarazione.citizen.nome;
            dichiarazioneDettaglio.genitore_tutore_curatore.sesso = this.newDichiarazione.citizen.sesso;
            dichiarazioneDettaglio.genitore_tutore_curatore.data_nascita = this.newDichiarazione.citizen.data_nascita;
            dichiarazioneDettaglio.genitore_tutore_curatore.comune_nascita = this.newDichiarazione.citizen.comune_nascita;



            dichiarazioneDettaglio.ruolo_genitore_tutore_curatore = {codice: "GENITORE_1"};
            dichiarazioneDettaglio.ruolo_figlio_tutelato_curato = {codice: "FIGLIO"};



            if(this.newDichiarazione.citizen.numeroDocumento !=null || this.newDichiarazione.citizen.numeroDocumento != undefined){

                let documentoOggetto : Documento = {};
                dichiarazioneDettaglio.genitore_tutore_curatore.documento = documentoOggetto;
                dichiarazioneDettaglio.genitore_tutore_curatore.documento.data_rilascio = this.newDichiarazione.citizen.dataRilascio;
                dichiarazioneDettaglio.genitore_tutore_curatore.documento.data_scadenza_doc = this.newDichiarazione.citizen.dataScadenza;
                dichiarazioneDettaglio.genitore_tutore_curatore.documento.autorita = this.newDichiarazione.citizen.rilasciatoDa;
                dichiarazioneDettaglio.genitore_tutore_curatore.documento.numero_documento = this.newDichiarazione.citizen.numeroDocumento;

                let documentoTipo : DocumentoTipo = {};
                dichiarazioneDettaglio.genitore_tutore_curatore.documento.tipo = documentoTipo;
                dichiarazioneDettaglio.genitore_tutore_curatore.documento.doc_desc =  this.newDichiarazione.citizen.tipoDocumento;
                if(this.newDichiarazione.citizen.tipoDocumento == 'Carta di identita'){
                  dichiarazioneDettaglio.genitore_tutore_curatore.documento.tipo.codice =  'TIPO_1';
                }
                if(this.newDichiarazione.citizen.tipoDocumento == 'Patente'){
                  dichiarazioneDettaglio.genitore_tutore_curatore.documento.tipo.codice =  'TIPO_2';
                }


            }else{

            }

            dichiarazioneDettaglio.stato = {codice: "VALIDA"};

            dichiarazione.dettagli.push(dichiarazioneDettaglio);

            let createDich: any = {};
            createDich.dichiarazione = dichiarazione;

            if(contatore==contaMinori){

              isUltimoDaLanciare = true;
            }
            this.lanciaCreazioneDichiarazione(createDich,  isUltimoDaLanciare);
          }

        });
    }
    if(contaGenitori==1){

      this.newDichiarazione.tutelatoList.forEach(minore => {

          let dichiarazione : Dichiarazione = {};
          dichiarazione.dettagli = [];



          dichiarazione.data_inserimento = new Date();
          dichiarazione.stato_servizio_delega = "ATTIVA";
          dichiarazione.stato = {codice: "ATTIVA"};
          dichiarazione.modo =  {codice: "SPORTELLO"};
          dichiarazione.tipo =  {codice: "CONGIUNTA"};
          dichiarazione.provenienza='GENITORE';


          dichiarazione.cittadino = new Cittadino();
          dichiarazione.cittadino.codice_fiscale = minore.info.codice_fiscale.toUpperCase();
          if(minore.info.cit_id!=null){
            dichiarazione.cittadino.cit_id = minore.info.cit_id;
          }
          dichiarazione.cittadino.cognome = minore.info.cognome;
          dichiarazione.cittadino.nome = minore.info.nome;
          dichiarazione.cittadino.sesso = minore.info.sesso;
          dichiarazione.cittadino.data_nascita = minore.info.data_nascita;
          dichiarazione.cittadino.comune_nascita = minore.info.comune_nascita;




          let dichiarazioneDettaglio1 : DichiarazioneDettaglio = {};
          let idAura;

          dichiarazioneDettaglio1.figlio_tutelato_curato =  new Cittadino();
          dichiarazioneDettaglio1.figlio_tutelato_curato.codice_fiscale = minore.info.codice_fiscale.toUpperCase();
          dichiarazioneDettaglio1.figlio_tutelato_curato.cit_id = minore.info.cit_id;
          dichiarazioneDettaglio1.figlio_tutelato_curato.cognome = minore.info.cognome;
          dichiarazioneDettaglio1.figlio_tutelato_curato.nome = minore.info.nome;
          dichiarazioneDettaglio1.figlio_tutelato_curato.sesso = minore.info.sesso;
          dichiarazioneDettaglio1.figlio_tutelato_curato.data_nascita = minore.info.data_nascita;
          dichiarazioneDettaglio1.figlio_tutelato_curato.comune_nascita = minore.info.comune_nascita;
          idAura = this.cercaIdSuAura(minore.info.codice_fiscale.toUpperCase());
            if(idAura!=0){
              dichiarazioneDettaglio1.figlio_tutelato_curato.id_aura = idAura;
            }



          dichiarazioneDettaglio1.genitore_tutore_curatore =  new Cittadino();
          dichiarazioneDettaglio1.genitore_tutore_curatore.codice_fiscale = this.newDichiarazione.citizen.codice_fiscale.toUpperCase();
          if(this.newDichiarazione.citizen.cit_id!=null){
            dichiarazioneDettaglio1.genitore_tutore_curatore.cit_id = this.newDichiarazione.citizen.cit_id;
          }
          dichiarazioneDettaglio1.genitore_tutore_curatore.cognome = this.newDichiarazione.citizen.cognome;
          dichiarazioneDettaglio1.genitore_tutore_curatore.nome = this.newDichiarazione.citizen.nome;
          dichiarazioneDettaglio1.genitore_tutore_curatore.sesso = this.newDichiarazione.citizen.sesso;
          dichiarazioneDettaglio1.genitore_tutore_curatore.data_nascita = this.newDichiarazione.citizen.data_nascita;
          dichiarazioneDettaglio1.genitore_tutore_curatore.comune_nascita = this.newDichiarazione.citizen.comune_nascita;



          dichiarazioneDettaglio1.ruolo_genitore_tutore_curatore = {codice: "GENITORE_1"};
          dichiarazioneDettaglio1.ruolo_figlio_tutelato_curato = {codice: "FIGLIO"};

          dichiarazioneDettaglio1.stato = {codice: "VALIDA"};


          let dichiarazioneDettaglio2 : DichiarazioneDettaglio = {};

          dichiarazioneDettaglio2.figlio_tutelato_curato =  new Cittadino();
          dichiarazioneDettaglio2.figlio_tutelato_curato.codice_fiscale = minore.info.codice_fiscale.toUpperCase();
          dichiarazioneDettaglio2.figlio_tutelato_curato.cit_id = minore.info.cit_id;
          dichiarazioneDettaglio2.figlio_tutelato_curato.cognome = minore.info.cognome;
          dichiarazioneDettaglio2.figlio_tutelato_curato.nome = minore.info.nome;
          dichiarazioneDettaglio2.figlio_tutelato_curato.sesso = minore.info.sesso;
          dichiarazioneDettaglio2.figlio_tutelato_curato.data_nascita = minore.info.data_nascita;
          dichiarazioneDettaglio2.figlio_tutelato_curato.comune_nascita = minore.info.comune_nascita;



          dichiarazioneDettaglio2.genitore_tutore_curatore =  new Cittadino();
          this.newDichiarazione.altroGenitore.forEach(genitore2 => {
            dichiarazioneDettaglio2.genitore_tutore_curatore.codice_fiscale = genitore2.codice_fiscale.toUpperCase();
            dichiarazioneDettaglio2.genitore_tutore_curatore.cit_id = genitore2.cit_id;
            dichiarazioneDettaglio2.genitore_tutore_curatore.cognome = genitore2.cognome;
            dichiarazioneDettaglio2.genitore_tutore_curatore.nome = genitore2.nome;
            dichiarazioneDettaglio2.genitore_tutore_curatore.sesso = genitore2.sesso;
            dichiarazioneDettaglio2.genitore_tutore_curatore.data_nascita = genitore2.data_nascita;
            dichiarazioneDettaglio2.genitore_tutore_curatore.comune_nascita = genitore2.comune_nascita;


          });

          dichiarazioneDettaglio2.ruolo_genitore_tutore_curatore = {codice: "GENITORE_2"};
          dichiarazioneDettaglio2.ruolo_figlio_tutelato_curato = {codice: "FIGLIO"};

          dichiarazioneDettaglio2.stato = {codice: "VALIDA"};



          dichiarazione.dettagli.push(dichiarazioneDettaglio1);
          dichiarazione.dettagli.push(dichiarazioneDettaglio2);

          let createDich: any = {};
          createDich.dichiarazione = dichiarazione;

          isUltimoDaLanciare = true;
          this.lanciaCreazioneDichiarazione(createDich,  isUltimoDaLanciare);

          contatore = contatore+1;
          if(contatore==contaMinori){

          }
      });
    }
  }

  lanciaCreazioneDichiarazione(createDich, daLanciare){

    let codiceFiscaleUtenteLoggato = this.authService.getToken();

    this.defaultService.dichiarazionePost(codiceFiscaleUtenteLoggato, '1', 'SANSOL', createDich, "body").subscribe(res => {
      let numeroPraticaComposta = this.caricaFeedback(res.dettagli[0].dicdet_id) + " ";
      this.caricaFeedback(res.dettagli[0].dicdet_id);

      if(daLanciare){



        this.router.navigate(['feedback-minori',res.npratica,res.uuid,'true','noError']);
      }else{

      }
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

  caricaFeedback(nroPratica):string{

    this.numeroPratica= this.numeroPratica + " " +  nroPratica;
    return this.numeroPratica;
  }

  get newDichiarazione() {
    return this.stateService.data.newDichiarazione;
  }

  set newDichiarazione(newDelega) {
    this.stateService.data.newDichiarazione = newDelega;
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

}
