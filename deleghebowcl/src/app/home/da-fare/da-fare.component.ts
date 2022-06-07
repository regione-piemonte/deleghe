import {Component, OnDestroy, OnInit, ViewChild, ɵConsole} from '@angular/core';
import {Router} from '@angular/router';
import {faTimes} from '@fortawesome/free-solid-svg-icons';
import {faChevronDown} from '@fortawesome/free-solid-svg-icons/faChevronDown';
import {Message, MessageService} from 'primeng/api';
import {ShowMessagesService} from '../../shared/service/show-messages.service';
import {HomeComponent} from '../home.component';
import {GestioneDichiarazioniService} from '../../api/gestioneDichiarazioni.service';
import {DichiarazioneCitizenService} from '../../shared/service/dichiarazione-citizen.service';
import { DichiarazioneStato } from 'src/app/model/dichiarazioneStato';
import {GestioneCodificheService} from '../../api/gestioneCodifiche.service';
import {DichiarazioneTipo} from 'src/app/model/dichiarazioneTipo';
import { formatDate } from '@angular/common';
import { AuthService } from 'src/app/auth/auth.service';
import {DefaultService} from '../../api/default.service';
import {LoginOperatore} from '../../model/loginOperatore';
import { PdfMakeWrapper, Columns, Table, Ul, Ol, TextReference } from 'pdfmake-wrapper';
import pdfFonts from 'pdfmake/build/vfs_fonts';
import { Txt } from 'pdfmake-wrapper';
import pdfMake from 'pdfmake/build/pdfmake';
import { Observable } from 'rxjs/Observable';
import { Cittadino } from 'src/app/model/cittadino';
import { Dichiarazione } from 'src/app/model/dichiarazione';
pdfMake.vfs = pdfFonts.pdfMake.vfs;

/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

@Component({
  selector: 'app-da-fare',
  templateUrl: './da-fare.component.html',
  styleUrls: ['./da-fare.component.css']
})

export class DaFareComponent implements OnInit, OnDestroy {

  constructor(private router: Router,
    private showMessagesService: ShowMessagesService,
    protected home: HomeComponent,
    protected gestioneDichiarazioniService: GestioneDichiarazioniService,
    protected dichiarazioneCitizenService: DichiarazioneCitizenService,
    protected codificheCitizenService: GestioneCodificheService,
    protected authService: AuthService,
    protected defaultService: DefaultService,
    private messageService: MessageService) {}


  elencoStatiDichiarazione: DichiarazioneStato[] = [];
  notFilteredStatiDichiarazione: DichiarazioneStato[] = [];
  elencoTipiDichiarazione: DichiarazioneTipo[] = [];
  tipo: any = 'Tutte';
  stato: any = 'Tutte';
  ruolo: any = 'Tutti';


  @ViewChild('inserimentoDa') inserimentoDa;
  @ViewChild('inserimentoA') inserimentoA;
  tableData;
  codiceFiscale = '';
  isTableLoading = false;
  stampaCittadino: Cittadino;
  items= [];


  cols = [];


  fiscalCodeFormatOk = true;


  interval;
  dateToday;
  dateAppoggioFrom: Date;
  canStartSearch = false;
  dateThirty;
  loginOperatore: LoginOperatore;
  loginOperatoreAsl: LoginOperatore;
  aslOperatore;
  dateAppoggioConvertita: Date;


  dichiarazione: Dichiarazione;


  msgs: Message[] = this.showMessagesService.getMsgs();

  async ngOnInit() {
    this.interval = setInterval(() => {
      this.checkCanStartSearch();

    }, 300);

    this.codiceFiscale = '';



    await this.getOperatoreLoggatoAdHoc();

    if(this.getOperatoreLoggatoAdHoc()){
      this.formattaCalcolaData();
      this.formattaDataTrentaGiorniDaOggi();
      this.loadStatiDichiarazione();
      this.loadTipiDichiarazione();
    }

    this.aslOperatore= localStorage.getItem('aslOperatore');



    this.cols = [
      {header: 'N.Pratica', field: 'nPratica'},

      {header: 'Delegato', field: 'delegato'},
      {header: 'Compilatore', field: 'compilatore'},
      {header: 'Stato pratica', field: 'statoPratica'},
      {header: 'Data richiesta', field: 'rataRichiesta'},
      {header: 'Tipologia delega', field: 'tipoDelega'},

    ];

    this.clearPosParam();

  }

  clearPosParam() {

    this.router.navigate(['/'], { replaceUrl: true});
  }

  ngOnDestroy(): void {
    clearInterval(this.interval);
  }

  checkCanStartSearch() {
    let result = true;

    if (this.isFilterShown()) {
      if (this.isBlunk(this.codiceFiscale)) {
        let insertDa = this.inserimentoDa.nativeElement.value;
        let insertA = this.inserimentoA.nativeElement.value;

        if (this.isBlunk(insertDa) && this.isBlunk(insertA)) {
          result = false;
        }
      }
    }

    this.canStartSearch = result;
  }

  isBlunk(val) {
    return val === undefined || val === null || val === '';
  }


  checkFiscalCodeFormat(): boolean  {
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
      this.fiscalCodeFormatOk = s % 26 === cf.charCodeAt(15) - 'A'.charCodeAt(0);
    }
    return this.fiscalCodeFormatOk;
  }


  formattaCalcolaData(){
    const format = 'dd/MM/yyyy';
    const locale = 'en-US';

     this.dateAppoggioFrom = new Date();
     const dateFrom = this.dateAppoggioFrom;
     const formattedDateFrom = formatDate(dateFrom, format, locale);
     this.dateToday = formattedDateFrom;
  }


  formattaDataTrentaGiorniDaOggi(){
    const format = 'dd/MM/yyyy';
    const locale = 'en-US';
    let numeroGiorni= 29;

     this.dateAppoggioFrom = new Date();
     this.dateAppoggioFrom.setDate(this.dateAppoggioFrom.getDate() -numeroGiorni);
     const dateTo = this.dateAppoggioFrom;
     const formattedDateTo = formatDate(dateTo, format, locale);
    this.dateThirty = formattedDateTo;
  }


  loadStatiDichiarazione() {

    let codiceFiscaleUtenteLoggato = this.authService.getToken();

    this.codificheCitizenService.statiDichiarazioneGet(
      codiceFiscaleUtenteLoggato, '1', 'SANSOL', 'body', false
    ).subscribe((res) => {
        this.elencoStatiDichiarazione = res;
        this.notFilteredStatiDichiarazione = this.elencoStatiDichiarazione;

        this.filter();


      }, (error => {

        console.error('Errore nel caricamento della combobox stati dichiarazione');
        console.error(error);

      }))
  }


  filter() {

    let elencoStatiDichiarazione = [];
    this.notFilteredStatiDichiarazione.forEach(statoDichiarazione => {


      if(statoDichiarazione.codice === 'RICHIESTA_RETTIFICATA' ||
            statoDichiarazione.codice === 'IN_LAVORAZIONE' ||
              statoDichiarazione.codice === 'IN_ATTESA_DI_CONFERMA' ||
                statoDichiarazione.codice === 'DA_COMPLETARE' ||
                statoDichiarazione.codice === 'RIFIUTA' ||
                statoDichiarazione.codice === 'REVOCATA_BLOCCO'  ||
                statoDichiarazione.codice === 'VALIDA' ||
                statoDichiarazione.codice === 'REVOCATA' ||
                statoDichiarazione.codice === 'ATTIVA' ||
                statoDichiarazione.codice === 'DA_APPROVARE') {

        elencoStatiDichiarazione.push(statoDichiarazione);
      }


    });
    this.elencoStatiDichiarazione = elencoStatiDichiarazione;
  }


  loadTipiDichiarazione() {

    let codiceFiscaleUtenteLoggato = this.authService.getToken();

    this.codificheCitizenService.tipiDichiarazioneGet(
      codiceFiscaleUtenteLoggato, '1', 'SANSOL', 'body', false
    ).subscribe((res) => {
        this.elencoTipiDichiarazione = res;

      }, (error => {

        console.error('Errore nel caricamento della combobox tipi dichiarazione');
        console.error(error);

      }))
  }


  showTable() {

    if(this.codiceFiscale ===null || this.codiceFiscale === undefined || this.codiceFiscale === ''){
      this.messageService.clear();
      this.messageService.add({severity: 'error', summary: '', detail: 'Il codice fiscale è obbligatorio.'});
      this.hideTable();
    }
    if (this.dateAssenti()) {
      this.messageService.clear();
      this.messageService.add({severity: 'error', summary: '', detail: 'Inserire entrambe le date o nessuna.'});
      this.hideTable();
    }else {
      let risultatoControlloCF = true;
      risultatoControlloCF = this.checkFiscalCodeFormat();
      if(risultatoControlloCF===true){
        this.isTableLoading = true;
        this.tableData = [];
        let mainGenitoreDett;

        let filter = this.getFilter();


      let codiceFiscaleUtenteLoggato = this.authService.getToken();


      this.gestioneDichiarazioniService.cittadiniCfDichiarazioniGet(
        codiceFiscaleUtenteLoggato, '1', 'SANSOL', codiceFiscaleUtenteLoggato, JSON.stringify(filter), 'body')
        .subscribe((res) => {
          if (res) {
            this.tableData = [];
            res.forEach((dich) => {

              let dettagliGroped = {};
              for(let dettDich of dich.dettagli) {

                let group = dettagliGroped['' + dettDich.dicdet_id];
                if(!group || !group.lenght) {
                  group = [];
                }
                group.push(dettDich);
                dettagliGroped['' + dettDich.dicdet_id] = group;
              }

              for(let group of Object.keys(dettagliGroped)){

                let dettDich0 = dettagliGroped[group][0];

                mainGenitoreDett=this.parseDettaglio(dettDich0, dich, dettDich0.genitore_tutore_curatore, dettDich0.ruolo_genitore_tutore_curatore.codice);
                let items ={data: mainGenitoreDett};

                let figlioDett = [];
                for(let dettDich of dettagliGroped[group]){


                    figlioDett.push({
                      data: this.parseDettaglio(dettDich, dich, dettDich.figlio_tutelato_curato, dettDich.ruolo_figlio_tutelato_curato.codice),
                      children: []
                    });
                }
                items['children'] = figlioDett;


                let codiceStato = dettDich0.stato.codice;

                this.stampaCittadino =  dettDich0.figlio_tutelato_curato;
                if(codiceStato ==="DA_COMPLETARE" || codiceStato ==="IN_ATTESA_DI_CONFERMA"){
                  if(this.aslOperatore == this.stampaCittadino.asl ){
                    this.tableData.push(items);
                  }
                }else{
                  this.tableData.push(items);
                }

              };
            });
            this.messageService.clear();
            this.isTableLoading = false;
            if (this.tableData.length === 0) {
              this.hideTable();
              this.messageService.clear();
              this.messageService.add({severity: 'error', summary: '', detail: 'Non esistono occorrenze rispetto alla ricerca effettuata'});
            }
          }
        }, (error) => {
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

  dateAssenti() {
    let result = false;

    if (this.isFilterShown()) {
      let dataDa = this.inserimentoDa.nativeElement.value;
      let dataA = this.inserimentoA.nativeElement.value;


      if (!this.checkNotBlunk(dataA) && this.checkNotBlunk(dataDa)) {
        result = true;
      }


    }

    return result;
  }



  isFilterShown() {
    return this.home.isFilterShown;
  }

  checkNotBlunk(val) {
    return val !== undefined && val !== null && val !== '';
  }

  getTodayDate() {
    let date = new Date();
    let month = date.getMonth() + 1;
    let day = date.getDate();
    return date.getFullYear() + '-' + (month < 10 ? '0' + month : month) + '-' + (day < 10 ? '0' + day : day);
  }

  getFilter() {
    let filter: any = {};
    if (this.isFilterShown()) {

      if (this.codiceFiscale) {
        filter.codice_fiscale_cit1 = {
          eq: this.codiceFiscale.toUpperCase()
        };
      }

      let dateA = this.getDate(this.inserimentoA);
      if (dateA) {
        filter.data_inserimento_a = dateA;
      }

      let dateDa = this.getDate(this.inserimentoDa);
      if (dateA) {
        filter.data_inserimento_da = dateDa;
      }


      if (this.stato !== 'Tutte') {
        filter.stato = [{
          codice: this.stato
        }];
      }


      if (this.ruolo !== 'Tutti') {
        filter.ruolo_cit1 = {
          codice: this.ruolo
        };
      }

      if (this.tipo !== 'Tutte') {
        filter.tipo = [
          {codice: this.tipo}
        ];
      }
    }

    return filter;
  }

  getDate(element) {
    let pcFieldValue = element.nativeElement.value;
    if (pcFieldValue) {
      return this.getNormalizedForServerData(pcFieldValue);
    }
  }

  getNormalizedForServerData(dateStr: string) {
    let giorno = dateStr.substring(0, 2);
    let mese = dateStr.substring(3, 5);
    let anno = dateStr.substring(6, 10);

    let validDate = anno + '-' + mese + '-' + giorno;
    return new Date(validDate);
  }

  hideTable() {
    this.isTableLoading = false;
    this.tableData = undefined;
  }



  parseDettaglio(dett, dich, citizen, ruolo) {

    let uuidString = dich.uuid;



    return {
      uuid: uuidString,
      nPratica: dett.dicdet_id,

      ruolo: ruolo,
      full_compilatore_cf: dich.full_compilatore_cf,
      delegato: {
        sessoTipo: citizen.sesso,
        nome: citizen.nome + ' ' + citizen.cognome,
        fiscal: citizen.codice_fiscale
      },
      compilatore: dich.cittadino.nome + ' ' + dich.cittadino.cognome,
      statoPratica: dich.stato.descrizione,
      rataRichiesta: new Date(dich.data_inserimento),
      tipoDelega: dich.tipo.descrizione,

    };

  }


  postFilter(dich) {
    let result = true;

    if (this.stato === 'Tutte' || !this.isFilterShown()) {

      if (!(['IN_ATTESA_DI_CONFERMA', 'DA_COMPLETARE', 'RICHIESTA_RETTIFICATA', 'IN_LAVORAZIONE', 'RIFIUTA', 'REVOCATA_BLOCCO','ATTIVA', 'REVOCATA'].includes(dich.stato.codice))) {

        result = false;
      }else{

      }
    }

    return result;
  }

  nuovaDelega() {
    this.router.navigate(['./scelta-delega']);
  }

  popolaIntestazioneServizi(){
    let arrayListaServizi = [];

    var row0 = new Array();
    row0.push( 'Nome del servizio' );
    row0.push( 'Stato del servizio' );
    row0.push( 'Data Inizio' );
    row0.push( 'Data Fine' );
    arrayListaServizi.push(row0);

    return arrayListaServizi;
  }

  async getOperatoreLoggatoAdHoc(){

   let filter = JSON.stringify({
     codiceFiscale: "XYZ",
   });



   this.defaultService.getIdentitaOperatore("XYZ", '1', 'SANSOL', filter, "body").subscribe((res) => {


     this.loginOperatore = res;




     this.authService.setToken(this.loginOperatore.codice_fiscale_operatore);

     localStorage.setItem('codiceFiscaleOperatore',this.loginOperatore.codice_fiscale_operatore);
     localStorage.setItem('cognomeOperatore',this.loginOperatore.cognome_operatore);
     localStorage.setItem('nomeOperatore',this.loginOperatore.nome_operatore);
   }, (err) => {


      this.router.navigate(['unauthorized']);
   });
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

              let cfDelegato =dettaglioDichiarazione.genitore_tutore_curatore.codice_fiscale;


              this.ricercaDelegaUuid(figlioTutelato.codice_fiscale, this.dichiarazione, nPratica,cfDelegato);
            }
          });
        }else{

        }
      }, (err) => {

        this.messageService.clear();
        this.messageService.add({severity: 'error', summary: '', detail: 'La stampa non è possibile. Contattare Assistenza.'});
     });
  }



  ricercaDelegaUuid(codiceFiscaleDelegante, oggettoDichiarazione, nPratica,codiceFiscaleDelegato){
    let contatoreDelegaUuid = 0;

    let filter = this.getFilterStampa(codiceFiscaleDelegante, codiceFiscaleDelegato);


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

              this.messageService.clear();
              this.messageService.add({severity: 'error', summary: '', detail: 'La stampa non è possibile. Contattare Assistenza.'});
            }));
  }

  getFilterStampa(codiceFiscaleDelegante, codiceFiscaleDelegato) {
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

      this.messageService.clear();
      this.messageService.add({severity: 'error', summary: '', detail: 'La stampa non è possibile. Contattare Assistenza.'});
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
}
