import {Component, OnInit, ViewChild, ViewChildren} from '@angular/core';
import {DefaultService} from '../../api/default.service';
import {ActivatedRoute, Router} from '@angular/router';
import {StateService} from '../../shared/service/state.service';
import {DelegaServizio} from '../../model/delegaServizio';
import { DeletServizioParametro } from 'src/app/model/deletServizioParametro';
import {formatDate} from '@angular/common';
import { AuthService } from 'src/app/auth/auth.service';
/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
@Component({
  selector: 'app-servizi',
  templateUrl: './servizi.component.html',
  styleUrls: ['./servizi.component.css']
})
export class ServiziComponent implements OnInit {

  //***************INIZIO blocco PRINCIPALE********************/
  constructor(
    protected defaultService: DefaultService,
    protected router: Router,
    protected stateService: StateService,
    protected  route: ActivatedRoute,
    protected authService: AuthService,
  ) {}

  serviceList = [];           //LISTA SOL NON FSE
  serviceListFSE = [];        //LISTA SOL FSE
  serviceListSenzaGrado = []; //LISTA SOL SENZA GRADO
  elencoValidazione:  DeletServizioParametro[] = [];

  loading = true;
  delegatoTested = false;
  canProceed = false;
  interval;

  dateToday;                  //DATA ODIERNA CAMPO DAL
  dateOneYear;                //DATA A 365 GIORNI CAMPO AL
  dateOneYearFSE;             //DATA A 365 GIORNI CAMPO AL
  dateFromToday;
  dateToInterval;
  dateAppoggioFrom: Date;
  dateAppoggioTo: Date;
  numeroGiorni; number;

  citizen;
  fiscalCode;
  citId;
  nomeDelegato;
  cognomeDelegato;

  @ViewChildren('dal') dal;
  @ViewChildren('al') al;

  get workingCitizen() {
    return this.stateService.data.workingCitizen;
  }

  ngOnInit() {
    this.authService.getOperatoreLoggato();

    if (this.workingCitizen) {
      this.onCitizenFound();
      this.citizen = this.workingCitizen;

      //SERVICELIST POPOLATA DOPO AVER LANCIATO LA ricercaByUUID
      //A QUEL PUNTO CONTROLLA LE DATE OGNI 500 MILLISEC
      this.interval = setInterval(() => {
        if (this.serviceList && !this.loading) {
          this.checkEnteredDatesAreOK();
        }
      }, 500);

      //SERVICELISTFSE POPOLATA DOPO AVER LANCIATO LA ricercaByUUID
      //A QUEL PUNTO CONTROLLA LE DATE OGNI 500 MILLISEC
      this.interval = setInterval(() => {
        if (this.serviceListFSE && !this.loading) {
          this.checkEnteredDatesAreOKFSE();
        }
      }, 500);

      //SERVICELISTFSE POPOLATA DOPO AVER LANCIATO LA ricercaByUUID
      //A QUEL PUNTO CONTROLLA LE DATE OGNI 500 MILLISEC
      this.interval = setInterval(() => {
        if (this.serviceListSenzaGrado && !this.loading) {
          this.checkEnteredDatesAreOKSenzaGrado();
        }
      }, 500);

    } else {
      this.onCitizenWasNotFound();
    }
  }
  //***************fine blocco PRINCIPALE********************/

  //***************INIZIO blocco controllo date********************/
  //CHIAMATO DA NG ON INIT
  //DOPO AVER LANCIATO LA ricercaByUUID
  //COMINCIA A CONTROLLARE LE DATE OGNI 500 MILLISEC
  checkEnteredDatesAreOK() {
    this.serviceList.forEach(service => {
        let dates = this.getDates(service);
        if (!dates.al || !dates.dal) {
          service.isNullDate = true;
          service.isStartDateBigger = false;
        } else if (dates.al < dates.dal) {
          service.isNullDate = false;
          service.isStartDateBigger = true;
        } else {
          service.isNullDate = false;
          service.isStartDateBigger = false;
        }
    });
  }

  checkEnteredDatesAreOKFSE() {
    this.serviceListFSE.forEach(serviceFSE => {
        let dates = this.getDates(serviceFSE);
        if (!dates.al || !dates.dal) {
          serviceFSE.isNullDate = true;
          serviceFSE.isStartDateBigger = false;
        } else if (dates.al < dates.dal) {
          serviceFSE.isNullDate = false;
          serviceFSE.isStartDateBigger = true;
        } else {
          serviceFSE.isNullDate = false;
          serviceFSE.isStartDateBigger = false;
        }
    });
  }

  checkEnteredDatesAreOKSenzaGrado(){
    this.serviceListSenzaGrado.forEach(serviceSenzaGrado => {
      let dates = this.getDates(serviceSenzaGrado);
      if (!dates.al || !dates.dal) {
        serviceSenzaGrado.isNullDate = true;
        serviceSenzaGrado.isStartDateBigger = false;

      } else if (dates.al < dates.dal) {
        serviceSenzaGrado.isNullDate = false;
        serviceSenzaGrado.isStartDateBigger = true;
      } else {
        serviceSenzaGrado.isNullDate = false;
        serviceSenzaGrado.isStartDateBigger = false;
      }
    });
  }
  //***************fine blocco controllo date********************/

  //*********************************************************** */
  //INIZIO ON CITIZEN
  onCitizenWasNotFound() {
    this.router.navigate(['ricerca', 'delegante']);
  }

  onCitizenFound() {
    this.fiscalCode = this.workingCitizen.codice_fiscale;
    let newDelega = this.stateService.data.newDelega;

    if (!newDelega) {
      setTimeout(() => {
        this.router.navigate(['nuova-delega', 'delegante', this.fiscalCode]);
      }, 1000);
    } else {
      //testDelegato CONTROLLA CHE NON SI SIA GIA' UTILIZZATO IL DELEGATO SCELTO
      this.testDelegato();

      //PUNTO UNICO PER RECUPERARE IL CF DELL'OPERATORE LOGGATO
      let codiceFiscaleUtenteLoggato = this.authService.getToken();

      this.defaultService.sevicesGet(codiceFiscaleUtenteLoggato, '1', 'SANSOL', 'body')
      .subscribe(res => {
          res.forEach(service => {

            let delegaServizio: any = {};

              let nomeSolMancante =service.nome;
              let descrizioneSolMancante =service.descrizione;
              let fittizio = "fittizio-"+service.nome;
              let codiceServizioPadre = service.cod_serpadre;
              let fraseDebole =  service.frase_debole;
              let fraseForte = service.frase_forte;
              let dataMassima = this.formattaDataAdUnAnnoServer(service.numero_giorni_delegabili);
              let dataAlMassima = dataMassima;
              let delegabile = service.delegabile;

                if(codiceServizioPadre==='FSE'  && delegabile===true){
                      //I SOL-FSE MANCANTI PER IL CF CERCATO VENGONO CARICATI SULLA PAGINA HTML
                      this.creaElencoServiziMancantiFSE(nomeSolMancante, descrizioneSolMancante, fittizio, delegaServizio, fraseDebole, fraseForte, dataAlMassima, codiceServizioPadre);
                }
                if(codiceServizioPadre==='SENZAGRADO' && delegabile===true){
                      //I SOL-FSE FORTI MANCANTI PER IL CF CERCATO VENGONO CARICATI SULLA PAGINA HTML
                      this.creaElencoServiziMancantiSenzaGrado(nomeSolMancante, descrizioneSolMancante, fittizio, delegaServizio, fraseForte, dataAlMassima, codiceServizioPadre);
                }
                if(codiceServizioPadre==='NONFSE'  && delegabile===true){
                      //I SOL-NONFSE MANCANTI PER IL CF CERCATO VENGONO CARICATI SULLA PAGINA HTML
                      this.creaElencoServiziMancanti(nomeSolMancante, descrizioneSolMancante, fittizio, delegaServizio,dataAlMassima);
                }

                this.dateToday = this.formattaCalcolaDataOdierna();
          });

          this.loading = false;
        });
    }
  }
  //FINE ON CITIZEN
  //*********************************************************** */

  //*********************************************************** */
  //INIZIO CREAZIONE ENELNCHI SOL FSE; NONFSE E SENZAGRADO
  creaElencoServiziMancanti(nomeSolMancante: string, descrizioneSolMancante: string, fittizio: string, service, dataAlMassima: string) {
    this.servizioAssenteDaInserire(service, nomeSolMancante, descrizioneSolMancante, fittizio, dataAlMassima);
  }

  creaElencoServiziMancantiFSE(nomeSolMancante: string, descrizioneSolMancante: string, fittizio: string, service, fraseDebole: string, fraseForte: string, dataAlMassima: string, codiceServizioPadre: string) {
    this.servizioAssenteDaInserireFSE(service, nomeSolMancante, descrizioneSolMancante, fittizio, fraseDebole, fraseForte, dataAlMassima, codiceServizioPadre);
  }

  creaElencoServiziMancantiSenzaGrado(nomeSolMancante: string, descrizioneSolMancante: string, fittizio: string, service, fraseForte: string, dataAlMassima: string, codiceServizioPadre: string) {
    this.servizioAssenteDaInserireSenzaGrado(service, nomeSolMancante, descrizioneSolMancante, fittizio, fraseForte, dataAlMassima, codiceServizioPadre);
  }


  servizioAssenteDaInserire(service, codice,  descrizione, uuidFittizio, dataAlMassima){
    //ELENCO SOL NORMALI

    let serviceItem = {
      service: service,
      data_inizio_delega: '',
      data_fine_delega: '',
      uuid: uuidFittizio,
      description_servizio: descrizione,
      codice_servizio: codice,
      isNullDate: false,
      isStartDateBigger: false,
      dataAlMassima: dataAlMassima,
      disabilitaData: true
    };

    this.serviceList.push(serviceItem);
  }

  servizioAssenteDaInserireFSE(service, codice,  descrizione, uuidFittizio, fraseDebole, fraseForte, dataAlMassima, codiceServizioPadre){
    //ELENCO SOL FSE

    let serviceItem = {
      service: service,
      data_inizio_delega: '',
      data_fine_delega: '',
      uuid: uuidFittizio,
      description_servizio: descrizione,
      codice_servizio: codice,
      isNullDate: false,
      isStartDateBigger: false,
      fraseDebole: fraseDebole,
      fraseForte: fraseForte,
      dataAlMassima: dataAlMassima,
      disabilitaData: true
    };

    this.serviceListFSE.push(serviceItem);
  }

  servizioAssenteDaInserireSenzaGrado(service, codice,  descrizione, uuidFittizio, fraseForte, dataAlMassima, codiceServizioPadre){
    //ELENCO SOL SENZA GRADO (SCREEN E ESENPAT)

    let serviceItem = {
      service: service,
      data_inizio_delega: '',
      data_fine_delega: '',
      uuid: uuidFittizio,
      description_servizio: descrizione,
      codice_servizio: codice,
      isNullDate: false,
      isStartDateBigger: false,
      fraseForte: fraseForte,
      dataAlMassima: dataAlMassima
    };

    this.serviceListSenzaGrado.push(serviceItem);
  }
  //FINE CREAZIONE ENELNCHI SOL FSE; NONFSE E SENZAGRADO
  //*********************************************************** */

  //*********************************************************** */
  //INIZIO METODI RICHIAMATI DA HTML E FORMATTAZIONE DATE

  //formatta la data ricevuta da db
  formattaDataAdUnAnnoServer(numerogiorniDadb): string{
    const format = 'dd/MM/yyyy';
    const locale = 'en-US';
    let numeroGiorni= numerogiorniDadb -1;

     this.dateAppoggioFrom = new Date();
     this.dateAppoggioFrom.setDate(this.dateAppoggioFrom.getDate() + numeroGiorni);
     const dateTo = this.dateAppoggioFrom;
     const formattedDateTo = formatDate(dateTo, format, locale);
     return formattedDateTo;
  }

  //formatta la data odierna nel campo AL
  formattaCalcolaDataOdierna(): string{
    const format = 'dd/MM/yyyy';
    const locale = 'en-US';

     this.dateAppoggioFrom = new Date();
     const dateFrom = this.dateAppoggioFrom;
     const formattedDateFrom = formatDate(dateFrom, format, locale);
     return formattedDateFrom;
  }

  attivaDisattivaNeutro(service){

    if(service.checkAttiva ) {
      service.data_fine_delega_new_text = this.formattaDataAdUnAnno();
      service.disabilitaData=false;

    } else {
      service.data_fine_delega_new_text = '';
      service.disabilitaData=true;
    }
  }

  /*attivaDisattivaNeutro(uuid){
    this.serviceList.forEach(service => {
      if (service.checkAttiva && service.uuid===uuid) {
        this.azzeraDataFine(uuid);
      }
    });
  }*/

  //SE SI TOGLIE IL FLAG DAL CHEKBOX ATTIVA DATE AZZERA LA DATA AL
  azzeraDataFine(uuid){
    this.al.forEach(al => {
      let nativeElement = al.nativeElement;
      if (nativeElement.id === 'al-'+uuid ) {
        nativeElement.value='';
      }
    });
  }

  //formatta la data AL durante l'attivazione di un sol
  formattaDataAdUnAnno(): string{
    const format = 'dd/MM/yyyy';
    const locale = 'en-US';
    let numeroGiorni= 364;

     this.dateAppoggioFrom = new Date();
     this.dateAppoggioFrom.setDate(this.dateAppoggioFrom.getDate() + numeroGiorni);
     const dateTo = this.dateAppoggioFrom;
     const formattedDateTo = formatDate(dateTo, format, locale);
     return formattedDateTo;
  }

  //METODO CHE ATTIVA E DISATTIVA TUTTI I FLAG SULLE DELEGHE DEBOLI
  selezionaDelegheDeboli(){

    this.serviceListFSE.forEach(serviceFSE => {
      if(serviceFSE.debole ){
        serviceFSE.debole= false;
        this.dateOneYearFSE = null;
        serviceFSE.forte= false;
        serviceFSE.checkAttiva=false;
        serviceFSE.disabilitaData=true;
      }else{
        serviceFSE.debole= true;
        serviceFSE.forte= false;
        this.dateOneYearFSE = this.formattaDataAdUnAnno();
        serviceFSE.checkAttiva=true;
        serviceFSE.disabilitaData=false;
      }
    });
  }

  //METODO CHE ATTIVA E DISATTIVA TUTTI I FLAG SULLE DELEGHE FORTI
  selezionaDelegheForti(){

    this.serviceListFSE.forEach(serviceFSE => {
      if(serviceFSE.forte ){
        serviceFSE.forte= false;
        this.dateOneYearFSE = null;
        serviceFSE.checkAttiva=false;
        serviceFSE.debole= false;
        serviceFSE.disabilitaData=true;
      }else{
        serviceFSE.forte= true;
        serviceFSE.debole= false;
        this.dateOneYearFSE = this.formattaDataAdUnAnno();
        serviceFSE.checkAttiva=true;
        serviceFSE.disabilitaData=false;
      }
    });
  }

  attivaDisattivaNeutroSenzaGrado(uuid){
    this.serviceListSenzaGrado.forEach(serviceSenzaGrado => {
      if (serviceSenzaGrado.checkAttivaForti && serviceSenzaGrado.uuid===uuid) {
        this.azzeraDataFine(uuid);
      }
    });
  }
  //FINE METODI RICHIAMATI DA HTML E FORMATTAZIONE DATE
  //*********************************************************** */

  //*********************************************************** */
  //INIZIO METODO TEST DELEGATO

  get newDelega() {
    return this.stateService.data.newDelega;
  }

  set newDelega(newDelega) {
    this.stateService.data.newDelega = newDelega;
  }

  testDelegato() {
    let filter = JSON.stringify({
      codiceFiscale: this.newDelega.fiscalCode,
      sesso: this.newDelega.sesso,
      dataNascita: this.myDateParser(this.newDelega.dataNascita),
      comuneNascita: this.newDelega.comuneDiNascita,
      cognome: this.newDelega.cognome,
      nome: this.newDelega.nome,
    });

    //PUNTO UNICO PER RECUPERARE IL CF DELL'OPERATORE LOGGATO
    let codiceFiscaleUtenteLoggato = this.authService.getToken();

    this.defaultService.findCitizen(codiceFiscaleUtenteLoggato, '1', 'SANSOL', filter, 'body')
      .subscribe(res => {
        if (res && res.length > 0) {
            //RECUPERO DEL CIT_ID_DELEGATO
            res.forEach(service => {
              this.citId = service.cit_id;
              this.nomeDelegato = service.nome;
              this.cognomeDelegato = service.cognome;
            });

            //OTTENUTO IL CIT_ID DEL DELEGATO
            //SI CERCA SE IL DELEGANTE HA GIA' DELEGATO QUESTA PERSONA
            let filter = this.getFilter();

            //PUNTO UNICO PER RECUPERARE IL CF DELL'OPERATORE LOGGATO
            let codiceFiscaleUtenteLoggato = this.authService.getToken();

            this.defaultService.legameGet(codiceFiscaleUtenteLoggato, '1', 'SANSOL', JSON.stringify(filter), 'body')
              .subscribe(legame => {
              if(legame && legame.length > 0){
                //IL DELEGANTE HA GIA DELEGATO QUESTA PERSONA: FALLIMENTO
                this.delegatoDeleganteEsistenti();
              }
            }, () => {
              //IL DELEGANTE NON HA MAI DELEGATO QUESTA PERSONA: SUCCESSO
              //legameGet lancia una query che se non trova record restituisce una eccezione
              this.delegatoTested = true;
            });
        }else {
          //IN CASO DI DATI NON TROVATI RIMANDAVA AD ADULTO-ADULTO HTML (Cittadino non trovato): riga this.failDelegaTest();
          //ORA VA AVANTI
          //NESSUN CITTADINO TROVATO CON IL CF CERCATO
              this.delegatoTested = true;
              this.nomeDelegato = this.newDelega.nome;
              this.cognomeDelegato = this.newDelega.cognome;
        }

      }, () => {
        //IN CASO DI ERRORE RIMANDA AD ADULTO-ADULTO HTML (Cittadino non trovato) riga this.failDelegaTest();
        //ORA VA AVANTI
        this.delegatoTested = true;
        this.nomeDelegato = this.newDelega.nome;
        this.cognomeDelegato = this.newDelega.cognome;
      });
  }

  //CHIAMATO DA testDelegato
  getFilter() {
    let filter: any = {};
    filter.cit_id_delegato = this.citId;
    filter.cit_id_delegante = this.workingCitizen.cit_id;
    return filter;
  }

  //CHIAMATO DA testDelegato
  delegatoDeleganteEsistenti() {
    this.router.navigate(['nuova-delega','delegante',this.fiscalCode])
  }

  //CHIAMATO DA testDelegato
  myDateParser(dateStr : string) : string {
    let giorno = dateStr.substring(0, 2);
    let mese = dateStr.substring(3, 5);
    let anno = dateStr.substring(6, 10);

    let validDate = anno + '-' + mese + '-' + giorno;
    return validDate;
  }

  //REDIREZIONE NON PIU USATA: CODICE MANTENUTO
  failDelegaTest() {
    this.router.navigate(['nuova-delega','delegante',this.fiscalCode])
  }
  //FINE METODO TEST DELEGATO
  //*********************************************************** */

  //*********************************************************** */
  //INIZIO TASTO FINALE "Continua"
  roteToRiepilogo() {

      let services: DelegaServizio[] = [];

      //CARICAMENTO SOL NONFSE
      this.serviceList.forEach(service => {
        if (service.checkAttiva){
          let dates = this.getDatesPaginaRiepilogo(service);
          let delegaServizio: any = {};

          delegaServizio.data_inizio_delega = dates.dal;
          delegaServizio.data_fine_delega = dates.al;

          delegaServizio.codice_servizio = service.codice_servizio;
          delegaServizio.description_servizio = service.description_servizio;

          delegaServizio.grado_delega = null;
          services.push(delegaServizio);
        }
      });

      //CARICAMENTO SOL FSE
      this.serviceListFSE.forEach(serviceFSE => {
        if (serviceFSE.checkAttiva){
          let dates = this.getDatesPaginaRiepilogo(serviceFSE);
          let delegaServizioFSE: any = {};

          delegaServizioFSE.data_inizio_delega = dates.dal;
          delegaServizioFSE.data_fine_delega = dates.al;

          delegaServizioFSE.codice_servizio = serviceFSE.codice_servizio;
          delegaServizioFSE.description_servizio = serviceFSE.description_servizio;


          if(serviceFSE.forte && serviceFSE.debole){

            delegaServizioFSE.grado_delega_frase = 'Grado Delega Forte';
            delegaServizioFSE.grado_delega = 'FORTE';
          }
          else if(serviceFSE.forte){

            delegaServizioFSE.grado_delega_frase = 'Grado Delega Forte';
            delegaServizioFSE.grado_delega = 'FORTE';
          }
          else if(serviceFSE.debole){

            delegaServizioFSE.grado_delega_frase = 'Grado Delega Debole';
            delegaServizioFSE.grado_delega = 'DEBOLE';
          }
          else{

          }

          services.push(delegaServizioFSE);
        }
      });

      //CARICAMENTO SOL SENZAGRADO (SCREEN E ESENPAT)
      this.serviceListSenzaGrado.forEach(serviceSenzaGrado => {
        if (serviceSenzaGrado.checkAttivaForti){
          let dates = this.getDatesPaginaRiepilogo(serviceSenzaGrado);
          let delegaServizioSenzaGrado: any = {};

          delegaServizioSenzaGrado.data_inizio_delega = dates.dal;
          delegaServizioSenzaGrado.data_fine_delega = dates.al;

          delegaServizioSenzaGrado.codice_servizio = serviceSenzaGrado.codice_servizio;
          delegaServizioSenzaGrado.description_servizio = serviceSenzaGrado.description_servizio;

          delegaServizioSenzaGrado.grado_delega = null;
          services.push(delegaServizioSenzaGrado);
        }
      });

      this.stateService.data.newDelega.services = services;
      this.router.navigate(['nuova-delega', 'delegante', this.fiscalCode, 'riepilogo']);
  }

  //INIZIO NUOVI GET DATES
  getDates(service) {
    let result = {al: undefined, dal: undefined};

    this.dal.forEach((dal) => {
      let nativeElement = dal.nativeElement;
      if (nativeElement.id === 'dal-' + service.uuid) {
        let value = nativeElement.value;
        if (value && value !== '') {
          result.dal = this.getNormalizedForServerData(value);
          //result.dal = value;
        }
      }
    });
    this.al.forEach((al) => {
      let nativeElement = al.nativeElement;
      if (nativeElement.id === 'al-' + service.uuid) {
        let value = nativeElement.value;
        if (value && value !== '') {
          result.al = this.getNormalizedForServerData(value);
          //result.al = value;
        }
      }
    });

    return result;
  }

  getDatesPaginaRiepilogo(service) {
    let result = {al: undefined, dal: undefined};

    this.dal.forEach((dal) => {
      let nativeElement = dal.nativeElement;
      if (nativeElement.id === 'dal-' + service.uuid) {
        let value = nativeElement.value;
        if (value && value !== '') {
          result.dal = value;
        }
      }
    });
    this.al.forEach((al) => {
      let nativeElement = al.nativeElement;
      if (nativeElement.id === 'al-' + service.uuid) {
        let value = nativeElement.value;
        if (value && value !== '') {
          result.al = value;
        }
      }
    });

    return result;
  }

  //CHIAMATO NEL getDates
  getNormalizedForServerData(date: string) {
    let giorno = date.substring(0, 2);
    let mese = date.substring(3, 5);
    let anno = date.substring(6, 10);

    let validDate = anno + '-' + mese + '-' + giorno;
    let newdate = new Date(validDate);
    return newdate;
  }
  //FINE NUOVI GET DATES

  //FINE TASTO FINALE "Continua"
  //*********************************************************** */
}
