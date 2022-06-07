import {Component, Input, OnInit} from '@angular/core';
import {faCheckCircle} from '@fortawesome/free-solid-svg-icons/faCheckCircle';
import {faTimesCircle} from '@fortawesome/free-solid-svg-icons/faTimesCircle';
import {StateService} from '../../../shared/service/state.service';
import {Router} from '@angular/router';
import { DelegaServizio } from 'src/app/model/delegaServizio';
import { formatDate } from '@angular/common';
import pdfFonts from 'pdfmake/build/vfs_fonts';
import pdfMake from 'pdfmake/build/pdfmake';
pdfMake.vfs = pdfFonts.pdfMake.vfs;

/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

@Component({
  selector: 'app-feedback',
  templateUrl: './feedback.component.html',
  styleUrls: ['./feedback.component.css']
})
export class FeedbackComponent implements OnInit {

  numeroPratica: string = '123';
  delDate = new Date();
  responseErrors = [];

  isSuccessfullCreated = true;

  nomeDelegante;
  cognomeDelegante;
  nomeDelegato;
  cognomeDelegato;
  tipologiaDelega;
  numeroPraticaDelega;
  servizi: Array<DelegaServizio>;
  dateToday;
  dateAppoggioFrom: Date;
  dateAppoggioConvertita: Date;
  bottoneAttivo = true;

  checkCircle = faCheckCircle;
  timesCircle = faTimesCircle;

  constructor(
    protected stateService: StateService,
    protected router: Router
  ) {
  }

  ngOnInit() {
    this.formattaCalcolaData();

    if (this.stateService.data.feedback) {
      this.numeroPratica = this.stateService.data.feedback.numeroPratica;
      this.responseErrors = this.stateService.data.feedback.responseErrors;
      this.isSuccessfullCreated = this.stateService.data.feedback.isSuccessfullCreated;

      if(this.stateService.data.feedback.provenienza === 'DICHRESPGENITORIALE'){
        this.bottoneAttivo = false;
      }
    } else {
      this.router.navigate(['/']);
    }

  }

  print() {
    this.nomeDelegante = this.stateService.data.feedback.nomeDelegante;
    this.cognomeDelegante = this.stateService.data.feedback.cognomeDelegante;
    this.nomeDelegato = this.stateService.data.feedback.nomeDelegato;
    this.cognomeDelegato = this.stateService.data.feedback.cognomeDelegato;
    this.tipologiaDelega = this.stateService.data.feedback.tipologiaDelega;
    this.numeroPraticaDelega = this.stateService.data.feedback.numeroPraticaDelega;
    this.servizi = this.stateService.data.feedback.DelegaServizio;



    let numeroPratica = this.numeroPraticaDelega;
    let delegato = this.nomeDelegato + " " + this.cognomeDelegato;
    let delegante = this.nomeDelegante + " " + this.cognomeDelegante;
    let tipoDelega = this.tipologiaDelega;
    let dataOggi = this.dateToday;

    let items = this.popolaArrayServizi(this.servizi);

    const documentDefinition = {
      content: [
        { text: 'DELEGHE', style: 'header' },
        { text: 'Numero Pratica: '+numeroPratica, style: 'numeroPratica' },
        { text: 'DELEGATO: '+delegato, style: 'testoGenerale' },
        { text: 'DELEGANTE: '+delegante, style: 'testoGenerale' },
        { text: 'TIPOLOGIA DELEGA: '+tipoDelega, style: 'testoGenerale' },
        { text: ' ', style: 'spaziaturaTre' },
        { text: 'SERVIZI ON LINE SU CUI E’ ATTIVA LA DELEGA', style: 'testoLista' },

        {
          style: 'testoGenerale',
          table:
          {
            headerRows: 1,
            body:items
          }

        },
        { text: ' ', style: 'spaziaturaUno' },
        { text: '* Grado Delega FORTE: Visualizza e scarica anche i documenti oscurati', style: 'testoGenerale' },
        { text: '* Grado Delega DEBOLE: Visualizza e scarica solo i documenti non oscurati', style: 'testoGenerale' },
        { text: ' ', style: 'spaziaturaUno' },
        { text: 'Data: '+dataOggi, style: 'testoGenerale' }
      ],
      styles: {
        header: {
          fontSize: 18,
          bold: true,
          alignment: 'center',
          lineHeight:2
        },
        numeroPratica: {
          fontSize: 12,
          bold: true,
          italics: true,
          alignment: 'right',
          lineHeight:2
        },
        testoGenerale: {
          italics: true,
          fontSize: 12,
          alignment: 'left',
          lineHeight:2
        },
        testoLista: {
          fontSize: 14,
          bold: true,
          alignment: 'center',
          lineHeight:2
        },
        spaziaturaTre: {
          lineHeight:3
        },
        spaziaturaUno: {
          lineHeight:1
        },
        tableHeader: {
          italics: true,
          fontSize: 12,
          alignment: 'justify',
          bold: true
        }
      }
    };
    pdfMake.createPdf(documentDefinition).download('deleghe.pdf');


  }

  popolaArrayServizi(servizi){
    let arrayListaServizi = [];


    var row0 = new Array();
    row0.push( 'Nome del servizio' );
    row0.push( 'Stato del servizio' );
    row0.push( 'Data Inizio' );
    row0.push( 'Data Fine' );
    row0.push( 'Grado Delega' );
    arrayListaServizi.push(row0);

    this.servizi.forEach(dettServizi => {


      var row = new Array();

      row.push( dettServizi.description_servizio );
      row.push( dettServizi.stato_delega );
      row.push( this.formattaData(dettServizi.data_inizio_delega)  );
      row.push( this.formattaData(dettServizi.data_fine_delega) );
      if(dettServizi.grado_delega!=null){
        row.push( dettServizi.grado_delega+" *" );
      }else{
        row.push( "" );
      }
      arrayListaServizi.push(row);

    });



    return arrayListaServizi;
  }

  formattaCalcolaData(){
    const format = 'dd/MM/yyyy';
    const locale = 'en-US';

     this.dateAppoggioFrom = new Date();
     const dateFrom = this.dateAppoggioFrom;
     const formattedDateFrom = formatDate(dateFrom, format, locale);
     this.dateToday = formattedDateFrom;
  }

  formattaData(dataMillisec){
    const format = 'dd/MM/yyyy';
    const locale = 'en-US';

    this.dateAppoggioConvertita = new Date(dataMillisec);
    const dateToConvert = this.dateAppoggioConvertita;
    const formattedDate = formatDate(dateToConvert, format, locale);
    return formattedDate;
  }



}
