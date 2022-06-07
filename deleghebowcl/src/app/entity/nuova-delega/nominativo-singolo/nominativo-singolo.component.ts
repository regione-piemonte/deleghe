import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {DefaultService} from '../../../api/default.service';
import {ModalService} from '../../../shared/modal/modal.service';
import {StateService} from '../../../shared/service/state.service';
import { AuthService } from 'src/app/auth/auth.service';
import { ShowMessagesService } from 'src/app/shared/service/show-messages.service';
import {Message, MessageService} from 'primeng/api';
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
  selector: 'app-nominativo-singolo',
  templateUrl: './nominativo-singolo.component.html',
  styleUrls: ['./nominativo-singolo.component.css']
})
export class NominativoSingoloComponent implements OnInit {

  msgs: Message[] = this.showMessagesService.getMsgs();

  constructor(
    protected  route: ActivatedRoute,
    private showMessagesService: ShowMessagesService,
    protected  modalService: ModalService,
    protected  router: Router,
    protected  defaultService: DefaultService,
    protected  stateService: StateService,
    protected authService: AuthService,
    private messageService: MessageService
  ) {
  }

  tableData = [];
  cols = [];
  title;
  type;
  component;

  loading = true;
  loading2 = true;

  fiscalCode;
  citizen;
  delega;


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
  items= [];

  get workingCitizen() {
    return this.stateService.data.workingCitizen;
  }

  onCitizenFound() {
    if (this.component === 'delegante') {
      this.type = "Delegante";
    } else if (this.component === 'genitore') {
      this.type = "Delegante"
    } else if (this.component === 'tutore') {
      this.type = "Tutore"
    }

    this.fiscalCode = this.route.snapshot.paramMap.get('cf');

    let filterDelegante = this.getFilterDelegante(this.fiscalCode);
    this.findData(filterDelegante)
      .subscribe((res) => {
        res.forEach((delega) => {



        });
        this.parseDelegas(res);
        this.loading = false;
        this.checkNotFound()
      }, () => {
        this.loading = false;
        this.checkNotFound()
      });









    this.cols = [
      {header: 'N.Pratica', field: 'nPratica'},

      {header: 'Delegato', field: 'delegato'},
      {header: 'Compilatore', field: 'compilatore'},
      {header: 'Codice', field: 'codice'},
      {header: 'Stato pratica', field: 'statoPratica'},
      {header: 'Data richiesta', field: 'dataRichiesta'},
      {header: 'Inizio Validità', field: 'inizioValidita'},
      {header: 'Fine Validità', field: 'fineValidita'},
    ];
  }

  getFilterDelegante(cf) {
    let filter: any = {};
    filter.delegato_codice_fiscale = undefined;
    filter.delegante_codice_fiscale= cf;
    return filter;
  }

  getFilterDelegato(cf) {
    let filter: any = {};
    filter.delegante_codice_fiscale = undefined;
    filter.delegato_codice_fiscale = cf;
    return filter;
  }

  onCitizenWasNotFound() {
    this.router.navigate(['ricerca', this.component]);
  }

  ngOnInit() {
    this.formattaCalcolaData();
    this.authService.getOperatoreLoggato();
    this.component = this.route.snapshot.paramMap.get('component');
    if (this.workingCitizen) {
      this.onCitizenFound();
      this.citizen = this.workingCitizen;
    } else {
      this.onCitizenWasNotFound()
    }
  }

  getMinInizioAndMaxFine(deleghe) {

    let min = undefined;
    let max = undefined;
    deleghe.servizi.forEach(srv => {
      if (min === undefined) {
        min = srv.data_inizio_delega;
      } else {
        min = srv.data_inizio_delega < min ? srv.data_inizio_delega : min;
      }
      if (max === undefined) {
        max = srv.data_fine_delega;
      } else {
        max = srv.data_fine_delega > max ? srv.data_fine_delega : max;
      }

    });
    return {min: min, max: max};
  }

  showContent() {

    return !this.loading && this.citizen
  }

  parseDelegas(delegaList) {
    delegaList.forEach((delega) => {


      let minInizioAndMaxFine = this.getMinInizioAndMaxFine(delega);
      let delegato = delega.delegato;
      let dichiarazioneDettaglio = delega.dichiarazione_dettaglio;

      if (!this.citizen) {
        this.citizen = this.fiscalCode === delegato.codice_fiscale? delegato: delega.delegante;
      }

      this.tableData.push({
        data: {
          nPratica: dichiarazioneDettaglio? dichiarazioneDettaglio.dicdet_id: undefined,
          uuid: delega.uuid,

          delegato: {
            nome: delegato.nome + ' ' + delegato.cognome,
            fiscal: delegato.codice_fiscale
          },
          compilatore: delega.compilatore_cf,
          codice: '_',
          statoPratica: delega.stato_delega,
          dataRichiesta: delega.richiesta,
          inizioValidita: minInizioAndMaxFine.min,
          fineValidita: minInizioAndMaxFine.max,
        },
        children: []
      })
    })
  }

  checkNotFound() {
    if (!this.loading && this.tableData.length === 0) {
    }

  }

  findData(filter) {

    let codiceFiscaleUtenteLoggato = this.authService.getToken();

    return this.defaultService.delegheGet(codiceFiscaleUtenteLoggato, '1', 'SANSOL', JSON.stringify(filter), 'body');
  }



  print(uuid) {


    let arrayMancantiDacaricare= [];

    let filter = {
      uuid: uuid
    };


    let codiceFiscaleUtenteLoggato = this.authService.getToken();


    this.defaultService.delegaGet(codiceFiscaleUtenteLoggato, '1', 'SANSOL', JSON.stringify(filter), 'body')
      .subscribe((res) => {
        if (res) {
          this.delega = res;
          let delegatoDich = this.delega.delegato;
          let deleganteDich = this.delega.delegante;

          this.nomeDelegante = deleganteDich.nome;
          this.cognomeDelegante = deleganteDich.cognome;
          this.nomeDelegato = delegatoDich.nome;
          this.cognomeDelegato = delegatoDich.cognome;
          this.tipologiaDelega = this.delega.tipo_delega;
          this.numeroPraticaDelega = this.delega.numero_pratica;

          this.items = this.popolaArrayServizi(this.delega.servizi);



          let numeroPratica = this.numeroPraticaDelega;
          let delegato = this.nomeDelegato + " " + this.cognomeDelegato;
          let delegante = this.nomeDelegante + " " + this.cognomeDelegante;
          let tipoDelega = this.tipologiaDelega;
          let dataOggi = this.dateToday;
          let listSol = this.items;

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
                  body:listSol
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
      });
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
