import {Component, Input, OnDestroy, OnInit, QueryList, ViewChild, ViewChildren} from '@angular/core';
import {ModalService} from '../../shared/modal/modal.service';
import {DefaultService} from '../../api/default.service';
import {ActivatedRoute, Router} from '@angular/router';
import {ShowMessagesService} from '../../shared/service/show-messages.service';
import {Message, MessageService} from 'primeng/api';
import { formatDate } from '@angular/common';
import { AuthService } from 'src/app/auth/auth.service';
import { StateService } from 'src/app/shared/service/state.service';

/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

@Component({
  selector: 'app-tutte-pratiche',
  templateUrl: './tutte-pratiche.component.html',
  styleUrls: ['./tutte-pratiche.component.css']
})
export class TuttePraticheComponent implements OnInit, OnDestroy {


  constructor(
    protected  modalService: ModalService,
    private defaultService: DefaultService,
    protected  route: ActivatedRoute,
    protected  router: Router,
    private showMessagesService: ShowMessagesService,
    private messageService: MessageService,
    protected authService: AuthService,
    protected stateService: StateService,
  ) {
  }

  uuidreload;
  serviceList = [];
  serviceListFSE = [];
  serviceListSenzaGrado = [];
  serviceListNascosti = [];
  delega;
  interval;
  loading = false;
  dateToday;
  dateOneYear;
  dateAppoggioFrom: Date;
  dateAppoggioTo: Date;
  component;
  attivaAdulto;
  disabilitaData=true;


  @ViewChildren('dal') dal;
  @ViewChildren('al') al;


  ngOnDestroy(): void {
    clearInterval(this.interval);

  }

  ngOnInit() {

    this.authService.getOperatoreLoggato();
    this.component = this.route.snapshot.paramMap.get('component');




    this.interval = setInterval(() => {
      if (this.serviceList && !this.loading) {
        this.checkEnteredDatesAreOK();
      }
    }, 500);


    this.interval = setInterval(() => {
      if (this.serviceListFSE && !this.loading) {
        this.checkEnteredDatesAreOKFSE();
      }
    }, 500);


    this.interval = setInterval(() => {
      if (this.serviceListSenzaGrado && !this.loading) {
        this.checkEnteredDatesAreOKFSEForti();
      }
    }, 500);


    this.uuidreload = this.route.snapshot.paramMap.get('uuid');
    let filter = {
      uuid: this.route.snapshot.paramMap.get('uuid')
    };



    this.ricercaByUUID(filter);
  }

  private ricercaByUUID(filter) {

    let arrayMancantiDacaricare= [];


    let codiceFiscaleUtenteLoggato = this.authService.getToken();



     let provenienza = this.component;

     if(provenienza==='delegante'){

        this.attivaAdulto = true;
     }else{

        this.attivaAdulto = false;
     }



    this.defaultService.delegaGet(codiceFiscaleUtenteLoggato, '1', 'SANSOL', JSON.stringify(filter), 'body')
      .subscribe((res) => {
        if (res) {
          this.delega = res;
          let cfDelegante = this.delega.delegante.codice_fiscale;

          this.stateService.data.workingCitizen = this.delega.delegante;



          this.defaultService.sevicesGet(codiceFiscaleUtenteLoggato, '1', 'SANSOL', 'body')
          .subscribe(resSol => {
              this.delega.servizi.forEach(service => {
                let codiceServizioEsistente = service.codice_servizio;
                let gradoDelega = service.grado_delega;

                resSol.forEach(sol=> {

                    if(codiceServizioEsistente == sol.nome){

                      let codiceServizioPadre = sol.cod_serpadre;
                      let descrizioneServizioEsistente = sol.descrizione;
                      let dataMassima = this.formattaDataAdUnAnnoServer(sol.numero_giorni_delegabili);
                      let dataAlMassima = dataMassima;
                      let delegabile = sol.delegabile;
                      let fraseDebole =  sol.frase_debole;
                      let fraseForte = sol.frase_forte;

                      if(codiceServizioPadre==='FSE' && delegabile===true){

                            this.creaElencoServiziAttiviFSE(codiceServizioEsistente,descrizioneServizioEsistente,service, fraseDebole, fraseForte, gradoDelega, dataAlMassima, codiceServizioPadre);
                      }
                      if(codiceServizioPadre==='SENZAGRADO' && delegabile===true){

                            this.creaElencoServiziAttiviSenzaGrado(codiceServizioEsistente,descrizioneServizioEsistente,service, fraseForte, gradoDelega, dataAlMassima, codiceServizioPadre);
                      }
                      if(codiceServizioPadre==='NONFSE'  && delegabile===true){

                            this.creaElencoServiziAttivi(codiceServizioEsistente,descrizioneServizioEsistente,service, dataAlMassima);
                      }
                      if(codiceServizioPadre==='FSENONVISIBILE'  && delegabile===true){

                            this.trovaUuidSolNascosti(service);
                      }


                        const index= resSol.indexOf(sol);
                        resSol.splice(index,1);
                    }
                });
              });


              resSol.forEach(sol2=> {

                let delegaServizio: any = {};
                delegaServizio.uuid='fittizio-0000-0000-0000-fittizio0000';

                let nomeSolMancante =sol2.nome;
                let descrizioneSolMancante =sol2.descrizione;
                let fittizio = "fittizio-"+sol2.nome;
                let codiceServizioPadre = sol2.cod_serpadre;
                let fraseDebole =  sol2.frase_debole;
                let fraseForte = sol2.frase_forte;
                let dataMassima = this.formattaDataAdUnAnnoServer(sol2.numero_giorni_delegabili);
                let dataAlMassima = dataMassima;
                let delegabile = sol2.delegabile;

                if(codiceServizioPadre==='FSE'  && delegabile===true){

                      this.creaElencoServiziMancantiFSE(nomeSolMancante, descrizioneSolMancante, fittizio, delegaServizio, fraseDebole, fraseForte, dataAlMassima, codiceServizioPadre);
                }
                if(codiceServizioPadre==='SENZAGRADO' && delegabile===true){

                      this.creaElencoServiziMancantiSenzaGrado(nomeSolMancante, descrizioneSolMancante, fittizio, delegaServizio, fraseForte, dataAlMassima, codiceServizioPadre);
                }
                if(codiceServizioPadre==='NONFSE'  && delegabile===true){

                      this.creaElencoServiziMancanti(nomeSolMancante, descrizioneSolMancante, fittizio, delegaServizio,dataAlMassima);
                }
              });
          });
        }
        this.dateToday = this.formattaCalcolaDataOdierna();
      });
  }

  creaElencoServiziAttivi(nomeServizio: string, descrizioneServizio: string, service, dataAlMassima: string) {
    this.servizioEsistenteDaModificare(service, nomeServizio, descrizioneServizio, service.uuid, dataAlMassima);
  }

  creaElencoServiziAttiviFSE(nomeServizio: string, descrizioneServizio: string, service, fraseDebole: string, fraseForte: string, gradoDelega: string, dataAlMassima: string, codiceServizioPadre: string) {
    this.servizioEsistenteDaModificareFSE(service, nomeServizio, descrizioneServizio, service.uuid, fraseDebole, fraseForte, gradoDelega, dataAlMassima, codiceServizioPadre);
  }

  creaElencoServiziAttiviSenzaGrado(nomeServizio: string, descrizioneServizio: string, service, fraseForte: string, gradoDelega: string, dataAlMassima: string, codiceServizioPadre: string) {
    this.servizioEsistenteDaModificareSenzaGrado(service, nomeServizio, descrizioneServizio, service.uuid, fraseForte, gradoDelega, dataAlMassima, codiceServizioPadre);
  }

  trovaUuidSolNascosti(service) {
    this.impostaUuidSolNascosti(service);
  }

  creaElencoServiziMancanti(nomeSolMancante: string, descrizioneSolMancante: string, fittizio: string, service, dataAlMassima: string) {
    this.servizioAssenteDaInserire(service, nomeSolMancante, descrizioneSolMancante, fittizio, dataAlMassima);
  }

  creaElencoServiziMancantiFSE(nomeSolMancante: string, descrizioneSolMancante: string, fittizio: string, service, fraseDebole: string, fraseForte: string, dataAlMassima: string, codiceServizioPadre: string) {
    this.servizioAssenteDaInserireFSE(service, nomeSolMancante, descrizioneSolMancante, fittizio, fraseDebole, fraseForte, dataAlMassima, codiceServizioPadre);
  }

  creaElencoServiziMancantiSenzaGrado(nomeSolMancante: string, descrizioneSolMancante: string, fittizio: string, service, fraseForte: string, dataAlMassima: string, codiceServizioPadre: string) {
    this.servizioAssenteDaInserireSenzaGrado(service, nomeSolMancante, descrizioneSolMancante, fittizio, fraseForte, dataAlMassima, codiceServizioPadre);
  }

  servizioEsistenteDaModificare(service, codice, descrizione, uuidReale, dataAlMassima){
    let disabilitaRevocaInput = true;
    if(service.stato_delega=='ATTIVA' || service.stato_delega=='IN_SCADENZA'){
      disabilitaRevocaInput = false;
    }

    let serviceItem = {
      service: service,
      data_inizio_delega: service.data_inizio_delega,
      data_fine_delega: service.data_fine_delega,
      uuid: uuidReale,
      description_servizio: descrizione,
      codice_servizio: codice,
      prevStato: service.stato_delega,
      checkboxState: true,
      isNullDate: false,
      isStartDateBigger: false,
      dataAlMassima: dataAlMassima,
      disabilitaData: true,
      disabilitaRevoca: disabilitaRevocaInput
    };

    this.serviceList.push(serviceItem);
  }

  servizioEsistenteDaModificareFSE(service, codice, descrizione, uuidReale, fraseDebole, fraseForte, gradoDelega, dataAlMassima, codiceServizioPadre){
    let disabilitaRevocaInput = true;
    if(service.stato_delega=='ATTIVA' || service.stato_delega=='IN_SCADENZA'){
      disabilitaRevocaInput = false;
    }


    let gradoDelegaDebole;
    let gradoDelegaForte;

    if(gradoDelega!=null){
      if(gradoDelega=='FORTE'){
        gradoDelegaDebole=false;
        gradoDelegaForte=true;
      }else{
        gradoDelegaDebole=true;
        gradoDelegaForte=false;
      }
    }
    else{

      gradoDelegaDebole=false;
      gradoDelegaForte=false;
    }

    let serviceItem = {
      service: service,
      data_inizio_delega: service.data_inizio_delega,
      data_fine_delega: service.data_fine_delega,
      uuid: uuidReale,
      description_servizio: descrizione,
      codice_servizio: codice,
      prevStato: service.stato_delega,
      checkboxState: true,
      isNullDate: false,
      isStartDateBigger: false,
      fraseDebole: fraseDebole,
      fraseForte: fraseForte,
      deboleAttivo: gradoDelegaDebole,
      forteAttivo: gradoDelegaForte,
      dataAlMassima: dataAlMassima,
      disabilitaRevoca: disabilitaRevocaInput
    };

    this.serviceListFSE.push(serviceItem);
  }

  servizioEsistenteDaModificareSenzaGrado(service, codice, descrizione, uuidReale, fraseForte, gradoDelega, dataAlMassima, codiceServizioPadre){
    let disabilitaRevocaInput = true;
    if(service.stato_delega=='ATTIVA' || service.stato_delega=='IN_SCADENZA'){
      disabilitaRevocaInput = false;
    }


    let gradoDelegaForte;

    if(gradoDelega!=null){
      if(gradoDelega=='FORTE'){
        gradoDelegaForte=true;
      }else{
        gradoDelegaForte=false;
      }
    }
    else{

      gradoDelegaForte=false;
    }

    let serviceItem = {
      service: service,
      data_inizio_delega: service.data_inizio_delega,
      data_fine_delega: service.data_fine_delega,
      uuid: uuidReale,
      description_servizio: descrizione,
      codice_servizio: codice,
      prevStato: service.stato_delega,
      checkboxState: true,
      isNullDate: false,
      isStartDateBigger: false,
      fraseForte: fraseForte,
      forteAttivo: gradoDelegaForte,
      dataAlMassima: dataAlMassima,
      disabilitaRevoca: disabilitaRevocaInput
    };

    this.serviceListSenzaGrado.push(serviceItem);
  }

  impostaUuidSolNascosti(service){

    let serviceItem = {
      service: service,
      data_inizio_delega: service.data_inizio_delega,
      data_fine_delega: service.data_fine_delega,
      uuid: service.uuid,
      codice_servizio: service.codice_servizio
    };

    this.serviceListNascosti.push(serviceItem);
  }

  servizioAssenteDaInserire(service, codice,  descrizione, uuidFittizio, dataAlMassima){


    let serviceItem = {
      service: service,
      data_inizio_delega: '',
      data_fine_delega: '',
      uuid: uuidFittizio,
      description_servizio: descrizione,
      codice_servizio: codice,
      prevStato: 'DAATTIVARE',
      checkboxState: true,
      isNullDate: false,
      isStartDateBigger: false,
      dataAlMassima: dataAlMassima,
      disabilitaData: true,
      disabilitaRevoca: true
    };

    this.serviceList.push(serviceItem);
  }

  servizioAssenteDaInserireFSE(service, codice,  descrizione, uuidFittizio, fraseDebole, fraseForte, dataAlMassima, codiceServizioPadre){


    let serviceItem = {
      service: service,
      data_inizio_delega: '',
      data_fine_delega: '',
      uuid: uuidFittizio,
      description_servizio: descrizione,
      codice_servizio: codice,
      prevStato: 'DAATTIVARE',
      checkboxState: true,
      isNullDate: false,
      isStartDateBigger: false,
      fraseDebole: fraseDebole,
      fraseForte: fraseForte,
      dataAlMassima: dataAlMassima,
      disabilitaRevoca: true
    };

    this.serviceListFSE.push(serviceItem);
  }

  servizioAssenteDaInserireSenzaGrado(service, codice,  descrizione, uuidFittizio, fraseForte, dataAlMassima, codiceServizioPadre){


    let serviceItem = {
      service: service,
      data_inizio_delega: '',
      data_fine_delega: '',
      uuid: uuidFittizio,
      description_servizio: descrizione,
      codice_servizio: codice,
      prevStato: 'DAATTIVARE',
      checkboxState: true,
      isNullDate: false,
      isStartDateBigger: false,
      fraseForte: fraseForte,
      dataAlMassima: dataAlMassima,
      disabilitaRevoca: true
    };

    this.serviceListSenzaGrado.push(serviceItem);
  }

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

  checkEnteredDatesAreOKFSEForti(){
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


  getDates(service) {
    let result = {al: undefined, dal: undefined};

    this.al.forEach((al) => {
      let nativeElement = al.nativeElement;
      if (nativeElement.id === 'al-' + service.uuid) {
        let value = nativeElement.value;
        if (value && value !== '') {
          result.al = this.getNormalizedForServerData(value);
        }
      }
    });
    this.dal.forEach((dal) => {
      let nativeElement = dal.nativeElement;
      if (nativeElement.id === 'dal-' + service.uuid) {
        let value = nativeElement.value;
        if (value && value !== '') {
          result.dal = this.getNormalizedForServerData(value);
        }
      }
    });
    return result;
  }


  getNormalizedForServerData(date: string) {
    let giorno = date.substring(0, 2);
    let mese = date.substring(3, 5);
    let anno = date.substring(6, 10);

    let validDate = anno + '-' + mese + '-' + giorno;
    let newdate = new Date(validDate);
    return newdate;
  }


  formattaCalcolaDataOdierna(): string{
    const format = 'dd/MM/yyyy';
    const locale = 'en-US';

     this.dateAppoggioFrom = new Date();
     const dateFrom = this.dateAppoggioFrom;
     const formattedDateFrom = formatDate(dateFrom, format, locale);
     return formattedDateFrom;
  }


  formattaDataOdiernaServer(): string{
    const format = 'yyyy-MM-dd';
    const locale = 'en-US';

     this.dateAppoggioFrom = new Date();
     const dateFrom = this.dateAppoggioFrom;
     const formattedDateFrom = formatDate(dateFrom, format, locale);
     return formattedDateFrom;
  }


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

  formatDateDDMMYYYY(dateParam) {
    return formatDate(dateParam, 'dd/MM/yyyy', 'en-US');
  }


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

  public openSalvaDelegaDialog() {
    //VALIDO
    this.modalService.showDialog('SALVA', 'Sei sicuro di voler salvare la delega/le deleghe per ' + this.delega.delegato.nome + ' ' + this.delega.delegato.cognome + '?',
      false)
      .then((confirmed) => {
        this.gestioneSalvaDeleghe(false, 'ATTIVA',  this.delega);
      })
      .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
  }


  selezionaDelegheDeboli(){

    this.serviceListFSE.forEach(serviceFSE => {
      if(serviceFSE.debole ){
        serviceFSE.debole= false;
        this.dateOneYear = null;
        serviceFSE.forte= false;
        serviceFSE.checkAttiva=false;
      }else{
        serviceFSE.debole= true;
        this.dateOneYear = this.formattaDataAdUnAnno();
        serviceFSE.checkAttiva=true;
      }
    });
  }


  selezionaDelegheForti(){

    this.serviceListFSE.forEach(serviceFSE => {
      if(serviceFSE.forte ){
        serviceFSE.forte= false;
        this.dateOneYear = null;
        serviceFSE.checkAttiva=false;
        serviceFSE.debole= false;
      }else{
        serviceFSE.forte= true;
        this.dateOneYear = this.formattaDataAdUnAnno();
        serviceFSE.checkAttiva=true;
      }
    });
  }


  revocaTuttiFSE(){
    this.serviceListFSE.forEach(serviceFSE => {
      if(serviceFSE.checkRevoca ){
        serviceFSE.checkRevoca= false;
      }else{
        serviceFSE.checkRevoca= true;
      }
    });
  }

  attivaDisattivaNeutro(service){

    if(service.checkAttiva ) {
      service.data_fine_delega_new_text = this.formattaDataAdUnAnno(); //this.formatDateDDMMYYYY(service.data_fine_delega);
      service.disabilitaData=false;

    } else {
      service.data_fine_delega_new_text = '';
      service.disabilitaData=true;
    }




  }

  attivaDisattivaNeutroSenzaGrado(uuid){
    this.serviceListSenzaGrado.forEach(serviceSenzaGrado => {
      if (serviceSenzaGrado.checkAttivaForti && serviceSenzaGrado.uuid===uuid) {
        this.azzeraDataFine(uuid);
      }
    });
  }


  azzeraDataFine(uuid){
    this.al.forEach(al => {
      let nativeElement = al.nativeElement;
      if (nativeElement.id === 'al-'+uuid ) {
        nativeElement.value='';
      }
    });
  }

  gestioneSalvaDeleghe(isBlocked, stato, delegaCorrente) {
    this.loading = true;
    let serviceRes = [];
    let delega = delegaCorrente;


      this.serviceList.forEach(service => {
        let serviceToAdd = service.service;


        if(service.checkAttiva){


          if (service.prevStato === 'DAATTIVARE' ||
          service.prevStato === 'ATTIVA' ||
          service.prevStato === 'SCADUTA' ||
          service.prevStato === 'IN_SCADENZA' ||
          service.prevStato === 'RIFIUTATA' ||
          service.prevStato === 'REVOCATA') {

                let dates = this.getDates(service);

                if (dates.al && dates.dal) {

                  serviceToAdd.data_inizio_delega = dates.dal;
                  serviceToAdd.data_fine_delega = dates.al;

                  serviceToAdd.stato_delega = 'ATTIVA';


                  let stringaDaControllare =this.myStringSplit(service.uuid);
                  if(stringaDaControllare.includes('fittizio')){

                    serviceToAdd.uuid = null;
                  }
                  else{

                    serviceToAdd.uuid = service.uuid;
                  }

                  serviceToAdd.description_servizio=service.description_servizio;
                  serviceToAdd.codice_servizio = service.codice_servizio;



                  serviceRes.push(serviceToAdd);
                }
          }
        }

        if(service.checkRevoca){



          let dataodierna = this.formattaDataOdiernaServer();

          if (service.prevStato === 'ATTIVA' ||
          service.prevStato === 'IN_SCADENZA') {

                serviceToAdd.data_inizio_delega = service.data_inizio_delega;

                serviceToAdd.data_fine_delega = dataodierna;
                serviceToAdd.data_revoca_delega= dataodierna;

                serviceToAdd.stato_delega = 'REVOCATA';


                serviceToAdd.uuid = service.uuid;
                serviceToAdd.description_servizio=service.description_servizio;
                serviceToAdd.codice_servizio = service.codice_servizio;



                serviceRes.push(serviceToAdd);
            }
        }

      });

      this.serviceListFSE.forEach(serviceFSE => {
        let serviceToAdd = serviceFSE.service;


        if(serviceFSE.checkAttiva){


          if (serviceFSE.prevStato === 'DAATTIVARE') {

                let dates = this.getDates(serviceFSE);
                if (dates.al && dates.dal) {

                  serviceToAdd.data_inizio_delega = dates.dal;
                  serviceToAdd.data_fine_delega = dates.al;

                  serviceToAdd.stato_delega = 'ATTIVA';


                  let stringaDaControllare =this.myStringSplit(serviceFSE.uuid);
                  if(stringaDaControllare.includes('fittizio')){
                    serviceToAdd.uuid = null;
                  }
                  else{

                    serviceToAdd.uuid = serviceFSE.uuid;
                  }

                  if(serviceFSE.forte && serviceFSE.debole){

                            serviceToAdd.grado_delega = 'FORTE';
                  }
                  else if(serviceFSE.forte){

                            serviceToAdd.grado_delega = 'FORTE';
                  }
                  else if(serviceFSE.debole){

                            serviceToAdd.grado_delega = 'DEBOLE';
                  }
                  else{

                  }

                  serviceToAdd.description_servizio=serviceFSE.description_servizio;
                  serviceToAdd.codice_servizio = serviceFSE.codice_servizio;


                  serviceRes.push(serviceToAdd);
                }
          }
        }

        if(serviceFSE.checkAttiva){


          if (serviceFSE.prevStato === 'ATTIVA' ||
          serviceFSE.prevStato === 'SCADUTA' ||
          serviceFSE.prevStato === 'IN_SCADENZA' ||
          serviceFSE.prevStato === 'RIFIUTATA' ||
          serviceFSE.prevStato === 'REVOCATA') {


                let dates = this.getDates(serviceFSE);
                if (dates.al && dates.dal) {


                  serviceToAdd.data_inizio_delega = dates.dal;
                  serviceToAdd.data_fine_delega = dates.al;

                  serviceToAdd.stato_delega = 'ATTIVA';



                  let stringaDaControllare =this.myStringSplit(serviceFSE.uuid);
                  if(stringaDaControllare.includes('fittizio')){

                    serviceToAdd.uuid = null;
                  }
                  else{

                    serviceToAdd.uuid = serviceFSE.uuid;
                  }

                  if(serviceFSE.forte && serviceFSE.debole){

                            serviceToAdd.grado_delega = 'FORTE';
                  }
                  else if(serviceFSE.forte){

                            serviceToAdd.grado_delega = 'FORTE';
                  }
                  else if(serviceFSE.debole){

                            serviceToAdd.grado_delega = 'DEBOLE';
                  }
                  else{

                  }

                  serviceToAdd.description_servizio=serviceFSE.description_servizio;
                  serviceToAdd.codice_servizio = serviceFSE.codice_servizio;



                  serviceRes.push(serviceToAdd);
                }

          }
        }

        if(serviceFSE.checkRevoca){


          let dataodierna = this.formattaDataOdiernaServer();

          if (serviceFSE.prevStato === 'ATTIVA' ||
            serviceFSE.prevStato === 'IN_SCADENZA') {

                serviceToAdd.data_inizio_delega = serviceFSE.data_inizio_delega;

                serviceToAdd.data_fine_delega = dataodierna;
                serviceToAdd.data_revoca_delega= dataodierna;

                serviceToAdd.stato_delega = 'REVOCATA';


                serviceToAdd.uuid = serviceFSE.uuid;
                serviceToAdd.description_servizio=serviceFSE.description_servizio;
                serviceToAdd.codice_servizio = serviceFSE.codice_servizio;



                serviceRes.push(serviceToAdd);
            }
        }

      });

      this.serviceListSenzaGrado.forEach(serviceSenzaGrado => {
        let serviceToAdd = serviceSenzaGrado.service;


        if(serviceSenzaGrado.checkAttivaForti){


          if (serviceSenzaGrado.prevStato === 'DAATTIVARE') {



                let dates = this.getDates(serviceSenzaGrado);
                if (dates.al && dates.dal) {

                  serviceToAdd.data_inizio_delega = dates.dal;
                  serviceToAdd.data_fine_delega = dates.al;

                  serviceToAdd.stato_delega = 'ATTIVA';


                  let stringaDaControllare =this.myStringSplit(serviceSenzaGrado.uuid);
                  if(stringaDaControllare.includes('fittizio')){

                    serviceToAdd.uuid = null;
                  }
                  else{

                    serviceToAdd.uuid = serviceSenzaGrado.uuid;
                  }

                  serviceToAdd.description_servizio=serviceSenzaGrado.description_servizio;
                  serviceToAdd.codice_servizio = serviceSenzaGrado.codice_servizio;


                  serviceRes.push(serviceToAdd);
                }
          }
        }

        if(serviceSenzaGrado.checkAttivaForti){

          if (serviceSenzaGrado.prevStato === 'ATTIVA' ||
          serviceSenzaGrado.prevStato === 'SCADUTA' ||
          serviceSenzaGrado.prevStato === 'IN_SCADENZA' ||
          serviceSenzaGrado.prevStato === 'RIFIUTATA' ||
          serviceSenzaGrado.prevStato === 'REVOCATA') {

                let dates = this.getDates(serviceSenzaGrado);
                if (dates.al && dates.dal) {

                  serviceToAdd.data_inizio_delega = dates.dal;
                  serviceToAdd.data_fine_delega = dates.al;

                  serviceToAdd.stato_delega = 'ATTIVA';


                  let stringaDaControllare =this.myStringSplit(serviceSenzaGrado.uuid);
                  if(stringaDaControllare.includes('fittizio')){

                    serviceToAdd.uuid = null;
                  }
                  else{

                    serviceToAdd.uuid = serviceSenzaGrado.uuid;
                  }

                  serviceToAdd.description_servizio=serviceSenzaGrado.description_servizio;
                  serviceToAdd.codice_servizio = serviceSenzaGrado.codice_servizio;


                  serviceRes.push(serviceToAdd);
                }


                this.serviceListNascosti.forEach(serviceNascosti => {
                  let serviceToAddNascosti = serviceNascosti.service;




                      if(serviceSenzaGrado.codice_servizio ==='screen_tst' || serviceSenzaGrado.codice_servizio ==='screen_tst'){


                        if(serviceNascosti.codice_servizio === 'FSEPREV'){

                          serviceToAddNascosti.uuid = serviceNascosti.uuid;
                          serviceToAddNascosti.codice_servizio = 'FSEPREV';
                          serviceToAddNascosti.grado_delega = 'FORTE';
                          serviceRes.push(serviceToAddNascosti);
                        }
                      }
                      if(serviceSenzaGrado.codice_servizio ==='ESENPAT'){

                        if(serviceNascosti.codice_servizio === 'FSEESE'){

                          serviceToAddNascosti.uuid = serviceNascosti.uuid;
                          serviceToAddNascosti.codice_servizio = 'FSEESE';
                          serviceToAddNascosti.grado_delega = 'FORTE';
                          serviceRes.push(serviceToAddNascosti);
                        }
                      }
                });

          }
        }

        if(serviceSenzaGrado.checkRevoca){



          let dataodierna = this.formattaDataOdiernaServer();

          if (serviceSenzaGrado.prevStato === 'ATTIVA' || serviceSenzaGrado.prevStato === 'IN_SCADENZA') {

                serviceToAdd.data_inizio_delega = serviceSenzaGrado.data_inizio_delega;

                serviceToAdd.data_fine_delega = dataodierna;
                serviceToAdd.data_revoca_delega= dataodierna;

                serviceToAdd.stato_delega = 'REVOCATA';


                serviceToAdd.uuid = serviceSenzaGrado.uuid;

                serviceToAdd.description_servizio=serviceSenzaGrado.description_servizio;
                serviceToAdd.codice_servizio = serviceSenzaGrado.codice_servizio;



                serviceRes.push(serviceToAdd);
            }


            this.serviceListNascosti.forEach(serviceNascosti => {
            let serviceToAddNascosti = serviceNascosti.service;




                if(serviceSenzaGrado.codice_servizio ==='screen_tst' || serviceSenzaGrado.codice_servizio ==='screen_tst'){


                  if(serviceNascosti.codice_servizio === 'FSEPREV'){

                    serviceToAddNascosti.uuid = serviceNascosti.uuid;
                    serviceToAddNascosti.codice_servizio = 'FSEPREV';
                    serviceToAddNascosti.grado_delega = 'DEBOLE';
                    serviceRes.push(serviceToAddNascosti);
                  }
                }
                if(serviceSenzaGrado.codice_servizio ==='ESENPAT'){


                  if(serviceNascosti.codice_servizio === 'FSEESE'){

                    serviceToAddNascosti.uuid = serviceNascosti.uuid;
                    serviceToAddNascosti.codice_servizio = 'FSEESE';
                    serviceToAddNascosti.grado_delega = 'DEBOLE';
                    serviceRes.push(serviceToAddNascosti);
                  }
                }
             });

        }

      });


    delega.servizi = serviceRes;
    delega.stato_delega = stato;
    delega.blocca_delega = isBlocked;


    let codiceFiscaleUtenteLoggato = this.authService.getToken();


    this.defaultService.delegaPut(codiceFiscaleUtenteLoggato, '1', 'SANSOL', codiceFiscaleUtenteLoggato, delega)
      .subscribe(
        () => this.navigateAndLeftPositiveAlert(stato),
        () => this.navigateAndLeftNegativeAlert("Dati Non congruenti!")
      );
  }

  msgs: Message[] = this.showMessagesService.getMsgs();

  navigateAndLeftPositiveAlert(status) {

    let stato = '';
    if (this.delega.stato_delega === 'VALIDATA' && status === 'VALIDATA') {
      stato = 'con il nuovo stato (' + status + ')';
    }
    this.showMessagesService.setMsgs([{severity: 'success', summary: '', detail: 'La dichiarazione è stata correttamente aggiornata ' + stato}]);




    this.router.navigate(['ricerca','delegante','nominativo-singolo',this.delega.delegante.codice_fiscale]);

  }

  refreshPage() {
    window.location.reload();
  }

  navigateAndLeftNegativeAlert(messaggioErrore) {

    this.loading = false;
    this.messageService.add({severity: 'error', summary: '', detail: messaggioErrore});


    this.router.navigate(['/']);
  }
  myStringSplit(uuid : string) : string[] {
    let valore = uuid.split('-', 1);
    return valore;
  }




}




