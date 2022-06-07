import {Component, Input, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {HomeComponent} from '../home.component';
import {DefaultService} from '../../api/default.service';
import {GestioneCodificheService} from '../../api/gestioneCodifiche.service';
import {MessageService} from 'primeng/api';
import { DelegaStato } from 'src/app/model/delegaStato';
import { DelegaTipo } from 'src/app/model/delegaTipo';
import { formatDate } from '@angular/common';
import { IfStmt } from '@angular/compiler';
import { AuthService } from 'src/app/auth/auth.service';
import { DelegaServizio } from 'src/app/model/delegaServizio';
import pdfFonts from 'pdfmake/build/vfs_fonts';
import pdfMake from 'pdfmake/build/pdfmake';
pdfMake.vfs = pdfFonts.pdfMake.vfs;

/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

@Component({
  selector: 'app-tutte-le-deleghe',
  templateUrl: './tutte-le-deleghe.component.html',
  styleUrls: ['./tutte-le-deleghe.component.css']
})
export class TutteLeDelegheComponent implements OnInit, OnDestroy {
  @ViewChild('validitaInizio') validitaInizio;
  @ViewChild('validitaFine') validitaFine;
  @ViewChild('inserimentoA') inserimentoA;
  @ViewChild('inserimentoDa') inserimentoDa;
  tipo: any = 'Tutte';
  elencoStatiDelega: DelegaStato[] = [];
  elencoTipiDelega:  DelegaTipo[] = [];
  stato: any = 'Tutte';
  ruolo: any = 'GENITORE';
  codiceFiscale: any;
  cols = [];
  isTableLoading = false;
  tableData = undefined;
  canStartSearch = false;
  interval;
  dateToday;
  dateThirty;
  dateAppoggioFrom: Date;
  dateValidita = false;
  dateInserimento = false;
  erroreDataMaggiore =false;
  provenienza;
  fiscalCodeFormatOk = true;
  ch2 = true;
  ch1 = false;

  delega;
  nomeDelegante;
  cognomeDelegante;
  nomeDelegato;
  cognomeDelegato;
  tipologiaDelega;
  numeroPraticaDelega;
  servizi: Array<DelegaServizio>;
  dateAppoggioConvertita: Date;
  items= [];

  constructor(
    protected home: HomeComponent,
    protected defaultService: DefaultService,
    protected messageService: MessageService,
    protected codificheCitizenService: GestioneCodificheService,
    protected authService: AuthService,
  ) {
  }

  ngOnInit() {
    this.formattaCalcolaData();
    this.authService.getOperatoreLoggato();



    this.cols = [
      {header: 'N.Pratica', field: 'nPratica'},

      {header: 'Delegato', field: 'delegato'},
      {header: 'Compilatore', field: 'compilatore'},

      {header: 'Tipologia delega', field: 'tipoDelega'},

    ];


  }

  formattaCalcolaData(){
    const format = 'dd/MM/yyyy';
    const locale = 'en-US';

     this.dateAppoggioFrom = new Date();
     const dateFrom = this.dateAppoggioFrom;
     const formattedDateFrom = formatDate(dateFrom, format, locale);
     this.dateToday = formattedDateFrom;
  }

  ngOnDestroy(): void {
    clearInterval(this.interval);
  }

  hideTable() {
    this.tableData = undefined;
  }

  isFilterShown() {
    return this.home.isFilterShown;
  }


  checkFiscalCodeFormat(): boolean {
    let cf = this.codiceFiscale.toUpperCase();

    let cfReg = /^[A-Z]{6}\d{2}[A-Z]\d{2}[A-Z]\d{3}[A-Z]$/;
    if (!cfReg.test(cf) || cf.length !== 16) {
      this.fiscalCodeFormatOk = false;
    } else {
      let set1 = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ';
      let set2 = 'ABCDEFGHIJABCDEFGHIJKLMNOPQRSTUVWXYZ';
      let setpari = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';
      let setdisp = 'BAKPLCQDREVOSFTGUHMINJWZYX';
      let s = 0;
      for (let i = 1; i <= 13; i += 2) {
        s += setpari.indexOf(set2.charAt(set1.indexOf(cf.charAt(i))));
      }
      for (let i = 0; i <= 14; i += 2) {
        s += setdisp.indexOf(set2.charAt(set1.indexOf(cf.charAt(i))));
      }
      this.fiscalCodeFormatOk = s % 26 == cf.charCodeAt(15) - 'A'.charCodeAt(0);
    }
    return this.fiscalCodeFormatOk;
  }


  getStatoString(stato) {
    let result = "_";

    if (stato === "ANNULLATA") {
      result = 'Annullata'
    } else if (stato === "VALIDATA") {
      result = 'Validata'
    } else if (stato === "REVOCATA") {
      result = 'Revocata'
    } else if (stato === "ATTIVA") {
      result = 'Attiva'
    } else if (stato === "RINUNCIA") {
      result = 'Rinuncia'
    } else if (stato === "REVOCATA_D_UFFICIO") {
      result = 'Revocata d\'ufficio'
    }

    return result
  }

  getTipoText(tipo) {
    let result = "_";
    if (tipo === 'ADULTO') {
      this.provenienza = 'delegante';
      result = "Delega adulto/adulto"
    } else if (tipo === 'GENITOREMONO') {
      this.provenienza = 'tutore';
      result = "di un solo genitore (monogenitoriale)"
    } else if (tipo === 'CONGIUNTA') {
      this.provenienza = 'tutore';
      result = "Delega minore congiunta"
    }else if (tipo === 'AMMSOSTEGNO') {
      this.provenienza = 'tutore';
      result = "di un amministratore di sostegno di un maggiore di età (> di 18 anni)"
    } else if (tipo === 'TUTELA') {
      this.provenienza = 'tutore';
      result = "tutela di un minore emancipato (> di 16 anni e < di 18 anni)"
    } else if (tipo === 'TUTELA_MAG18') {
      this.provenienza = 'tutore';
      result = "tutela di un maggiorenne"
    }

    return result;
  }


 showTable() {


  if(this.codiceFiscale ===null || this.codiceFiscale === undefined){
    this.isTableLoading = false;
    this.messageService.clear();
    this.messageService.add({severity: 'error', summary: '', detail: 'Il codice fiscale è obbligatorio.'});
    this.hideTable();
  }else{

      let risultatoControlloCF = true;
      risultatoControlloCF = this.checkFiscalCodeFormat();
      if(risultatoControlloCF===true){
        this.isTableLoading = true;
        this.tableData = [];
        let filter = this.getFilter();


          let codiceFiscaleUtenteLoggato = this.authService.getToken();

          this.defaultService.delegheGet(codiceFiscaleUtenteLoggato, '1', 'SANSOL', JSON.stringify(filter), 'body')
            .subscribe((res) => {
              if(res){
                this.tableData = [];
                res.forEach(deleghe => {

                if(deleghe.tipo_delega === 'ADULTO'){


                  let isNew = true;
                  let minInizioAndMaxFine = this.getMinInizioAndMaxFine(deleghe);
                  this.tableData.forEach(item => {
                    if (item.data.citizen.codice_fiscale === deleghe.delegato.codice_fiscale) {
                      isNew = false;
                      item.children.push({
                        data: {


                          tipoDelega: deleghe.tipo_delega,

                          full_compilatore_cf: deleghe.full_compilatore_cf,
                          isChild: true,
                          blocca: deleghe.blocca_delega,
                          nPratica: deleghe.numero_pratica,
                          delegante: deleghe.delegante.codice_fiscale,

                          uuid: deleghe.uuid,
                          citizen: deleghe.delegante
                        }, children: []
                      });

                      if (item.data.tipoDelega !== deleghe.tipo_delega) {
                        item.data.tipoDelega = '_';
                      }


                    }
                  });


                  if (isNew) {

                    this.tableData.push({
                      data: {
                        isChild: false,
                        citizen: deleghe.delegato,
                        tipoDelega: deleghe.tipo_delega,
                        full_compilatore_cf: deleghe.full_compilatore_cf,
                        blocca: deleghe.blocca_delega,

                        compilatore: '_',

                      },
                      children: [{
                        data: {
                          tipoDelega: deleghe.tipo_delega,
                          full_compilatore_cf: deleghe.full_compilatore_cf,

                          isChild: true,
                          blocca: deleghe.blocca_delega,
                          nPratica: deleghe.numero_pratica,
                          delegante: deleghe.delegante.codice_fiscale,

                          uuid: deleghe.uuid,
                          citizen: deleghe.delegante
                        }, children: []
                      }]
                    });
                  } //
                }
                });



                this.isTableLoading = false;
                this.messageService.clear();
                if (this.tableData.length === 0) {

                  this.hideTable();
                  this.messageService.clear();
                  this.messageService.add({severity: 'error', summary: '', detail: 'Non esistono occorrenze rispetto alla ricerca effettuata'});
                }
              }
            }, (error) => {
              this.isTableLoading = false;

              this.messageService.clear();
              error.error.forEach(error2 => {
                this.messageService.clear();
                this.messageService.add({severity: 'error', summary: '', detail: error2.descrizione});
              });
              this.hideTable();
            });
      }

    }
  }


  getMinInizioAndMaxFine(deleghe) {
    let min = undefined;
    let max = undefined;
    deleghe.servizi.forEach(srv => {
      if (srv.data_inizio_delega) {
        srv.data_inizio_delega = new Date(srv.data_inizio_delega);
      }
      if (srv.data_fine_delega) {
        srv.data_fine_delega = new Date(srv.data_fine_delega);
      }

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

  getFilter() {
    let filter: any = {};
    if (this.isFilterShown()) {

      if (this.codiceFiscale) {

        if (this.ruolo === 'FIGLIO') {
          filter.delegante_codice_fiscale = this.codiceFiscale.toUpperCase();
        } else if (this.ruolo === 'GENITORE') {
          filter.delegato_codice_fiscale = this.codiceFiscale.toUpperCase();
        }


      }






      if (this.stato !== 'Tutte') {
        filter.stato = [{
          codice: this.stato
        }];
      }



      if (this.tipo !== 'Tutte') {
        filter.tipo = [
          {codice: this.tipo}
        ];
      }
    }

    return filter;
  }


  print(uuid, nPratica){


    let filter = {
      uuid: uuid
    };

    //PUNTO UNICO PER RECUPERARE IL CF DELL'OPERATORE LOGGATO
    let codiceFiscaleUtenteLoggato = this.authService.getToken();

    //DAL DB SI RECUPERANO I SOL ATTIVATI ASSOCIATI AL CF DELLA RICERCA
    this.defaultService.delegaGet(codiceFiscaleUtenteLoggato, '1', 'SANSOL', JSON.stringify(filter), 'body')
      .subscribe((res) => {
        if (res) {
          this.delega = res;
          let delegatoDich = this.delega.delegato;
          let deleganteDich = this.delega.delegante;
          /*res.servizi.forEach(dettServizi => {
            dettServizi.grado_delega;
          });*/

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
      }, (error => {

        this.messageService.clear();
        this.messageService.add({severity: 'error', summary: '', detail: 'La stampa non è possibile. Contattare Assistenza.'});
      }));
  }

  popolaArrayServizi(servizi){
    let arrayListaServizi = [];

    //INTESTAZIONE FISSA
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

}
