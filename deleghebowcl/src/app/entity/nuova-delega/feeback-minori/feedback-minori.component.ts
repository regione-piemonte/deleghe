import {Component, Input, OnInit} from '@angular/core';
import {faCheckCircle} from '@fortawesome/free-solid-svg-icons/faCheckCircle';
import {faTimesCircle} from '@fortawesome/free-solid-svg-icons/faTimesCircle';
import {StateService} from '../../../shared/service/state.service';
import {ActivatedRoute, Router} from '@angular/router';
import { DelegaServizio } from 'src/app/model/delegaServizio';
import { formatDate } from '@angular/common';
import { AuthService } from 'src/app/auth/auth.service';
import {GestioneDichiarazioniService} from '../../../api/gestioneDichiarazioni.service';
import { Dichiarazione } from 'src/app/model/dichiarazione';
import {DefaultService} from '../../../api/default.service';
import pdfFonts from 'pdfmake/build/vfs_fonts';
import pdfMake from 'pdfmake/build/pdfmake';
pdfMake.vfs = pdfFonts.pdfMake.vfs;

/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

@Component({
  selector: 'app-feedback-minori',
  templateUrl: './feedback-minori.component.html',
  styleUrls: ['./feedback-minori.component.css']
})
export class FeedbackMinoriComponent implements OnInit {

  numeroPratica: string = '123';
  passaggioUuid: string = '123';
  responseErrors = [];
  delDate = new Date();
  isSuccessfullCreated = true;
  codiceErrore;
  descrizioneErrore;

  servizi: Array<DelegaServizio>;
  dateToday;
  dateAppoggioFrom: Date;
  dateAppoggioConvertita: Date;
  bottoneAttivo = true;

  checkCircle = faCheckCircle;
  timesCircle = faTimesCircle;

  dichiarazione: Dichiarazione;
  items= [];

  constructor(
    protected  route: ActivatedRoute,
    protected stateService: StateService,
    protected authService: AuthService,
    protected gestioneDichiarazioniService: GestioneDichiarazioniService,
    protected defaultService: DefaultService,
    protected router: Router
  ) {}

  ngOnInit() {

    this.formattaCalcolaData();

      this.numeroPratica = this.route.snapshot.paramMap.get('nrPratica');
      this.passaggioUuid = this.route.snapshot.paramMap.get('uuid');
      let successo = this.route.snapshot.paramMap.get('success');
      if(successo == 'true'){
        this.isSuccessfullCreated = true;
        this.bottoneAttivo = true;
      }else{
        this.isSuccessfullCreated = false;
        this.bottoneAttivo = false;
        this.codiceErrore = this.route.snapshot.paramMap.get('errore');
        if(this.codiceErrore =='DA.DCH05'){
          this.descrizioneErrore = "Esiste gia' una dichiarazione associata al tutelato.";
        }

      }


  }

  printPrimaFase(uuid, nPratica){
    let contatorePrimaFase = 0;


    let filter = {
      uuid: {
        eq: uuid
      }
    };


    let codiceFiscaleUtenteLoggato = this.authService.getToken();


    this.gestioneDichiarazioniService.cittadiniCfDichiarazioniGet(
      codiceFiscaleUtenteLoggato,
      '1', 'SANSOL',
      codiceFiscaleUtenteLoggato,
      JSON.stringify(filter),
      'body')
      .subscribe((res) => {
        if (res && res[0]) {

          this.dichiarazione = res[0];
          let dettagliDichiarazione =this.dichiarazione.dettagli;


          dettagliDichiarazione.forEach(dettaglioDichiarazione => {
            let codiceRuoloGenitore = dettaglioDichiarazione.ruolo_genitore_tutore_curatore.codice;

            let figlioTutelato = dettaglioDichiarazione.figlio_tutelato_curato;
            if(codiceRuoloGenitore=='GENITORE_1' || codiceRuoloGenitore=='TUTORE_DI_UN_MAGIORE_INTER' || codiceRuoloGenitore=='TUTORE'){
              contatorePrimaFase = contatorePrimaFase +1;

              let codiceFiscaleDelegato = dettaglioDichiarazione.genitore_tutore_curatore.codice_fiscale;


              this.ricercaDelegaUuid(figlioTutelato.codice_fiscale, this.dichiarazione, nPratica, codiceFiscaleDelegato);
            }
          });
        }else{

        }
      });

  }

  ricercaDelegaUuid(codiceFiscaleDelegante, oggettoDichiarazione, nPratica, codiceFiscaleDelegato){
    let contatoreDelegaUuid = 0;


    let filter = this.getFilter(codiceFiscaleDelegante, codiceFiscaleDelegato);


    let codiceFiscaleUtenteLoggato = this.authService.getToken();

            this.defaultService.delegheGet(codiceFiscaleUtenteLoggato, '1', 'SANSOL', JSON.stringify(filter), 'body')
            .subscribe((res) => {
              if(res){

                res.forEach(deleghe => {
                  let ruoloGenitoreCuratore = deleghe.dichiarazione_dettaglio.ruolo_genitore_tutore_curatore.codice;

                  if(ruoloGenitoreCuratore=='GENITORE_1' || ruoloGenitoreCuratore=='TUTORE_DI_UN_MAGIORE_INTER' || ruoloGenitoreCuratore=='TUTORE'){
                    contatoreDelegaUuid = contatoreDelegaUuid +1;

                    this.ricercaServiziSol(deleghe.uuid, oggettoDichiarazione, nPratica);
                  }
                });
              }else{

              }
            }, (error => {

            }));
  }

  getFilter(codiceFiscaleDelegante, codiceFiscaleDelegato) {
    let filter: any = {};
    filter.delegante_codice_fiscale= codiceFiscaleDelegante.toUpperCase();
    filter.delegato_codice_fiscale= codiceFiscaleDelegato.toUpperCase();
    return filter;
  }

  ricercaServiziSol(uuid, oggettoDichiarazione, nPratica){
    let contatoreServiziSol = 0;

    let delega;

    let filter = {
      uuid: uuid
    };


    let codiceFiscaleUtenteLoggato = this.authService.getToken();


    this.defaultService.delegaGet(codiceFiscaleUtenteLoggato, '1', 'SANSOL', JSON.stringify(filter), 'body')
    .subscribe((res) => {
      if (res) {
        contatoreServiziSol = contatoreServiziSol +1;

        delega = res.servizi;
        this.printSecondaFase(nPratica, oggettoDichiarazione, delega);
      }else{

      }
    }, (error => {

    }));

  }

  printSecondaFase(nPratica, oggettoDichiarazione, delega){
    let contatoreSecondaFase = 0;



    let numeroPratica= nPratica;

        if (oggettoDichiarazione) {
          this.dichiarazione = oggettoDichiarazione;
          let delegato;
          let genitoreDichiarante;
          let altroGenitore;
          let dettagliDichiarazione =this.dichiarazione.dettagli;


          let dataOggi = this.dateToday;

          let stato =this.dichiarazione.stato.descrizione;


          let tipoDelega = this.dichiarazione.tipo.descrizione;



          dettagliDichiarazione.forEach(dettaglioDichiarazione => {
            contatoreSecondaFase = contatoreSecondaFase +1;

            let codiceRuoloGenitore = dettaglioDichiarazione.ruolo_genitore_tutore_curatore.codice;

            let genitoreCuratore = dettaglioDichiarazione.genitore_tutore_curatore;
            let figlioTutelato = dettaglioDichiarazione.figlio_tutelato_curato;


            if(codiceRuoloGenitore=='GENITORE_1' || codiceRuoloGenitore=='TUTORE_DI_UN_MAGIORE_INTER' || codiceRuoloGenitore=='TUTORE'){

              delegato = figlioTutelato.cognome + " " + figlioTutelato.nome;
              genitoreDichiarante = genitoreCuratore.cognome + " " + genitoreCuratore.nome;
            }else{

                altroGenitore = genitoreCuratore.cognome + " " + genitoreCuratore.nome;
            }
          });

          if(altroGenitore===undefined){
            altroGenitore="-";
          }


          this.items = this.popolaArrayServizi(delega);

          let listSol = this.items;


          const documentDefinition = {
            content: [
              { text: 'DELEGHE', style: 'header' },
              { text: 'Numero Pratica: '+numeroPratica, style: 'numeroPratica' },
              { text: 'GENITORE DICHIARANTE: '+genitoreDichiarante, style: 'testoGenerale' },
              { text: 'ALTRO GENITORE: '+altroGenitore, style: 'testoGenerale' },
              { text: 'MINORE/TUTELATO: '+delegato, style: 'testoGenerale' },
              { text: 'TIPOLOGIA DELEGA: '+tipoDelega, style: 'testoGenerale' },
              { text: 'STATO DELEGA: '+stato, style: 'testoGenerale' },
              { text: ' ', style: 'spaziaturaTre' },
              { text: 'SERVIZI ON LINE SU CUI E’ ATTIVA LA DELEGA', style: 'testoLista' },
              {
                style: 'testoGenerale',
                table:
                {
                  headerRows: 1,
                  body:this.items
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

    servizi.forEach(dettServizi => {


      let codiceServizio = dettServizi.codice_servizio;
      if(codiceServizio!="FSEESE" && codiceServizio!="FSEPREV" && codiceServizio!="FSE"){
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
      }

    });
    return arrayListaServizi;
  }

  formattaData(dataMillisec){
    const format = 'dd/MM/yyyy';
    const locale = 'en-US';

    this.dateAppoggioConvertita = new Date(dataMillisec);
    const dateToConvert = this.dateAppoggioConvertita;
    const formattedDate = formatDate(dateToConvert, format, locale);
    return formattedDate;
  }

  formattaCalcolaData(){
    const format = 'dd/MM/yyyy';
    const locale = 'en-US';

     this.dateAppoggioFrom = new Date();
     const dateFrom = this.dateAppoggioFrom;
     const formattedDateFrom = formatDate(dateFrom, format, locale);
     this.dateToday = formattedDateFrom;
  }




}
