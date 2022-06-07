import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {StateService} from "../../../shared/service/state.service";
import {DefaultService} from "../../../api/default.service";
import {Cittadino} from "../../../model/cittadino";
import { GestioneComuniService } from '../../../api/gestioneComuni.service';
import {GestioneCodificheService} from '../../../api/gestioneCodifiche.service';
import { DocumentoTipo } from 'src/app/model/documentoTipo';
import { AuthService } from 'src/app/auth/auth.service';
import { debounce, debounceTime, distinctUntilChanged } from 'rxjs/operators';
import { Subject } from 'rxjs';

/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

@Component({
  selector: 'ricerca',
  templateUrl: './ricerca.component.html',
  styleUrls: ['./ricerca.component.css']
})

export class RicercaComponent implements OnInit, OnDestroy {

  constructor(
    protected  route: ActivatedRoute,
    protected defaultService: DefaultService,
    protected router: Router,
    protected stateService: StateService,
    protected codificheCitizenService: GestioneCodificheService,
    protected gestioneComuniService: GestioneComuniService,
    protected authService: AuthService,
  ) {
  }

  @ViewChild('dataScadenza') dataScadenza;
  @ViewChild('dataRilascio') dataRilascio;
  @ViewChild('nascita') nascita;

  title;
  link;
  isTableShown = false;
  isUserInfoFilled = false;
  isUserNotFound = false;
  isDeleganteNotFound = false;
  component;
  tableData = [];
  cols = [];
  risultati = 0;
  fiscalCode = '';
  nome = '';
  cognome = '';
  citizen;
  isSalvaEnabled = false;
  interval;
  interval2;
  fiscalCodeFormatOk = true;
  fiscalCodeFormatNPOk = true;
  canProceed = false;
  selectedInd;
  elencoDescrizioneDocumento: DocumentoTipo[] = [];
  provenienza;
  filterTextChanged: Subject<string> = new Subject<string>();

  setDefaultWorkingCitizen() {
    this.workingCitizen = {};
    this.workingCitizen.id = "";
    this.workingCitizen.cognome = "";
    this.workingCitizen.nome = "";
    this.workingCitizen.codice_fiscale = "";
    this.workingCitizen.sesso = "M";
    this.workingCitizen.data_nascita = "";
    this.workingCitizen.comune_nascita = "";
    this.workingCitizen.isValid= true;
    this.workingCitizen.tipoDocumento = "";

    this.workingCitizen.numeroDocumento = "";
    this.workingCitizen.rilasciatoDa = "";
    this.workingCitizen.dataRilascio = "";
    this.workingCitizen.dataScadenza = "";
    this.workingCitizen.agreementPushed = false;
  }

  ngOnDestroy(): void {
    clearInterval(this.interval)
    clearInterval(this.interval2)
  }

  ngOnInit() {
    this.authService.getOperatoreLoggato();
    this.loadDescrizoneDocumento();
    this.interval = setInterval(() => {


      this.isSalvaEnabled = this.canSalva();
    }, 300);

    this.fiscalCode = '';
    this.stateService.data = {}
    this.component = this.route.snapshot.paramMap.get('component');
    if (this.component === 'delegante') {
      this.provenienza = this.component;
      this.title = "Ricerca delegante";
    } else if (this.component === 'genitore') {
      this.provenienza = this.component;
      this.title = "Genitore 1"
    } else if (this.component === 'tutore') {
      this.provenienza = this.component;
      this.title = "Ricerca tutore"
    }

    this.cols = [
      {header: 'ID', field: 'id'},
      {header: 'Cognome', field: 'cognome'},
      {header: 'Nome', field: 'nome'},
      {header: 'Codice Fiscale', field: 'codice_fiscale'},
      {header: 'Sesso', field: 'sesso'},
      {header: 'Data di nascita', field: 'data_nascita'},
      {header: 'Сomune di nascita', field: 'comuneNascita'},
    ];

  }

  onCitizenFound() {
    if (!this.stateService.data.workingCitizen) {
      this.setDefaultWorkingCitizen();
      setTimeout(() => {
        this.nascita.nativeElement.value = "";
        this.dataRilascio.nativeElement.value = "";
        this.dataScadenza.nativeElement.value = "";
      });
    } else {
      setTimeout(() => {
        this.nascita.nativeElement.value = this.stateService.data.workingCitizen.data_nascita;
        this.dataRilascio.nativeElement.value = this.stateService.data.workingCitizen.dataRilascio;
        this.dataScadenza.nativeElement.value = this.stateService.data.workingCitizen.dataScadenza;
      });
    }

    this.interval2 = setInterval(() => {
      let nascita1 = this.nascita;
      if (nascita1 && nascita1.nativeElement.value) this.workingCitizen.data_nascita = nascita1.nativeElement.value;
      let dataRilascio1 = this.dataRilascio;
      if (dataRilascio1 && dataRilascio1.nativeElement.value) this.workingCitizen.dataRilascio = dataRilascio1.nativeElement.value;
      let dataScadenza1 = this.dataScadenza;
      if (dataScadenza1 && dataScadenza1.nativeElement.value) this.workingCitizen.dataScadenza = dataScadenza1.nativeElement.value;
      let newCittadino = this.workingCitizen;
      this.canProceed = newCittadino.nome !== "" && newCittadino.cognome !== "" && newCittadino.data_nascita !== "" && newCittadino.sesso !== "" && newCittadino.comune_nascita !== "" && newCittadino.codice_fiscale !== ""

    }, 500);
  }

  onCitizenWasNotFound() {

    if (this.component === 'delegante') {
      this.isDeleganteNotFound = true;
    } else {
      this.workingCitizen = {};
      this.setDefaultWorkingCitizen()
      this.isUserNotFound = true;
      this.onCitizenFound();
    }
  }

  get workingCitizen() {
    return this.stateService.data.workingCitizen;
  }

  set workingCitizen(workingCitizen) {
    this.stateService.data.workingCitizen = workingCitizen;
  }

  checkFiscalCodeFormat(): boolean {
    let risultatoControllo = true;

    if(this.isUserNotFound===true){
      this.fiscalCodeFormatNPOk = this.isFiscalCodeValid(this.workingCitizen.codice_fiscale);
      risultatoControllo = this.fiscalCodeFormatNPOk;

    }else{

      this.fiscalCodeFormatOk = this.isFiscalCodeValid(this.fiscalCode);
      risultatoControllo = this.fiscalCodeFormatOk;

    }
    return risultatoControllo;
  }



  isFiscalCodeValid(code) {
    let result = true;
    let cf = code.toUpperCase();

    let cfReg = /^[A-Z]{6}\d{2}[A-Z]\d{2}[A-Z]\d{3}[A-Z]$/;
    if (!cfReg.test(cf) || cf.length !== 16)
    {
      result = false;
    } else {
      let set1 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
      let set2 = "ABCDEFGHIJABCDEFGHIJKLMNOPQRSTUVWXYZ";
      let setpari = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
      let setdisp = "BAKPLCQDREVOSFTGUHMINJWZYX";
      let s = 0;
      for( let i = 1; i <= 13; i += 2 )
        s += setpari.indexOf( set2.charAt( set1.indexOf( cf.charAt(i) )));
      for( let i = 0; i <= 14; i += 2 )
        s += setdisp.indexOf( set2.charAt( set1.indexOf( cf.charAt(i) )));
      result = s % 26 == cf.charCodeAt(15) - 'A'.charCodeAt(0);
    }
    return result;
  }

  canSalva() {
    return  this.workingCitizen &&
    this.workingCitizen.cognome && this.workingCitizen.cognome!== "" &&
    this.workingCitizen.nome && this.workingCitizen.nome!== "" &&
    this.workingCitizen.codice_fiscale && this.workingCitizen.codice_fiscale!== "" && this.isFiscalCodeValid(this.workingCitizen.codice_fiscale) &&
    this.workingCitizen.sesso && this.workingCitizen.sesso!== "" &&
    this.workingCitizen.data_nascita && this.workingCitizen.data_nascita!== "" &&
    this.workingCitizen.comune_nascita && this.workingCitizen.comune_nascita!== "" &&
    this.workingCitizen.tipoDocumento && this.workingCitizen.tipoDocumento!== "" &&
    this.workingCitizen.numeroDocumento && this.workingCitizen.numeroDocumento!== "" &&
    this.workingCitizen.rilasciatoDa && this.workingCitizen.rilasciatoDa!== "" &&
    this.workingCitizen.dataRilascio && this.workingCitizen.dataRilascio!== "" &&
    this.workingCitizen.dataScadenza && this.workingCitizen.dataScadenza!== ""
  }

  hideAlert() {
    this.isDeleganteNotFound = false;
  }

  hideForm() {
    this.isUserNotFound = false;
  }

  salva() {
    this.isUserNotFound = false;
    this.isUserInfoFilled = true;
  }

  annulla() {
    this.isUserInfoFilled = false;
    this.isUserNotFound = true;
  }

  hideRisultati() {
    this.isTableShown = false;
  }

  selectRow(index) {
    this.selectedInd = index;

    let citizen: Cittadino = {};
    citizen.cognome = this.tableData[index].data.cognome;
    citizen.nome = this.tableData[index].data.nome;
    citizen.codice_fiscale = this.tableData[index].data.codice_fiscale;
    citizen.sesso = this.tableData[index].data.sesso;
    citizen.data_nascita = this.tableData[index].data.data_nascita;
    citizen.comune_nascita = this.tableData[index].data.comuneNascita;

    this.stateService.data.workingCitizen = citizen;
  }

  ricerca(provenienza) {


    if (provenienza === 'delegante') {


      let risultatoControlloCF = true;
      risultatoControlloCF = this.checkFiscalCodeFormat();


      if(risultatoControlloCF===true){
        let filter = JSON.stringify({
          codiceFiscale: this.fiscalCode,
        });

        this.tableData = [];
        this.isTableShown = false;
        this.isUserNotFound = false;
        this.isUserInfoFilled = false;
        this.isDeleganteNotFound = false;
        this.workingCitizen = undefined;


        let codiceFiscaleUtenteLoggato = this.authService.getToken();

        this.defaultService.findCitizen(codiceFiscaleUtenteLoggato, '1', 'SANSOL', filter, 'body')
          .subscribe(res => {
            if (res.length === 0) {
              this.onCitizenWasNotFound();
            } else if (res.length === 1) {
              this.workingCitizen = res[0];
              this.router.navigate(['ricerca',this.component,'nominativo-singolo',this.workingCitizen.codice_fiscale]);
            } else {
              let ind = 0;
              res.forEach(citizen => {
                let data = {
                  id: citizen.asl,
                  index: ind++,
                  cognome: citizen.cognome,
                  nome: citizen.nome,
                  codice_fiscale: citizen.codice_fiscale,
                  sesso: citizen.sesso,
                  data_nascita: citizen.data_nascita,
                  comuneNascita: citizen.comune_nascita,
                };
                this.tableData.push({
                  data: data,
                  children: []
                });
              });

              this.isTableShown = true;
              this.risultati = this.tableData.length;
            }
          }, () => this.onCitizenWasNotFound());
      }
    } else if (provenienza === 'genitore') {


      let risultatoControlloCF = true;
      this.workingCitizen = undefined;

      risultatoControlloCF = this.checkFiscalCodeFormat();

      if(risultatoControlloCF===true){
        let filter = JSON.stringify({
          codiceFiscale: this.fiscalCode,
        });


         let codiceFiscaleUtenteLoggato = this.authService.getToken();


        this.defaultService.findCitizen(codiceFiscaleUtenteLoggato, '1', 'SANSOL', filter, 'body')
        .subscribe(res => {
          if (res.length === 0) {
            this.onCitizenWasNotFound();
          } else if (res.length === 1) {
            this.workingCitizen = res[0];
            this.router.navigate(['/nuova-delega','genitore',this.fiscalCode]);
          }
        }, () => this.onCitizenWasNotFound());
      }
    } else if (provenienza === 'tutore') {


      this.workingCitizen = undefined;
      let risultatoControlloCF = true;

      risultatoControlloCF = this.checkFiscalCodeFormat();

      if(risultatoControlloCF===true){
        let filter = JSON.stringify({
          codiceFiscale: this.fiscalCode,
        });


         let codiceFiscaleUtenteLoggato = this.authService.getToken();


        this.defaultService.findCitizen(codiceFiscaleUtenteLoggato, '1', 'SANSOL', filter, 'body')
        .subscribe(res => {
          if (res.length === 0) {
            this.onCitizenWasNotFound();
          } else if (res.length === 1) {
            this.workingCitizen = res[0];
            this.router.navigate(['/nuova-delega','tutore',this.fiscalCode]);
          }
        }, () => this.onCitizenWasNotFound());
      }
    }
  }

  chooseCitizen() {
    this.router.navigate(['ricerca',this.component,'nominativo-singolo',this.workingCitizen.codice_fiscale]);
  }

  createCitizen() {
    this.loadDatiFuoriPiemonte();

    this.router.navigate(['nuova-delega',this.component,this.workingCitizen.codice_fiscale]);
  }


  loadDatiFuoriPiemonte() {
        if(this.workingCitizen.data_nascita!=null){
          this.workingCitizen.data_nascita=this.myDateParser(this.workingCitizen.data_nascita);
        }
        if(this.workingCitizen.dataRilascio!=null){
          this.workingCitizen.dataRilascio=this.myDateParser(this.workingCitizen.dataRilascio);
        }
        if(this.workingCitizen.dataScadenza!=null){
          this.workingCitizen.dataScadenza=this.myDateParser(this.workingCitizen.dataScadenza);
        }

        this.workingCitizen.nome=this.formattaStringa(this.workingCitizen.nome);
        this.workingCitizen.cognome=this.formattaStringa(this.workingCitizen.cognome);
        this.workingCitizen.comune_nascita=this.formattaStringa(this.workingCitizen.comune_nascita);
  }


  formattaStringa(stringa) {
    let stringaCompleta;
    let primoCarattere = stringa;
    let lunghezza = stringa.length;
    let altriCaratteri;

    primoCarattere = stringa.substr(0, 1);
    primoCarattere = primoCarattere.toUpperCase();

    altriCaratteri = stringa.substr(1, lunghezza);
    altriCaratteri = altriCaratteri.toLowerCase();

    stringaCompleta =primoCarattere+altriCaratteri;

    return stringaCompleta;
  }


  myDateParser(dateStr : string) : string {

    let giorno = dateStr.substring(0, 2);
    let mese = dateStr.substring(3, 5);
    let anno = dateStr.substring(6, 10);

    let validDate = anno + '-' + mese + '-' + giorno;
    return validDate
  }


  public onSearchChange(searchValue: string) {

    if (this.filterTextChanged.observers.length === 0) {
      this.filterTextChanged
        .pipe(debounceTime(2000), distinctUntilChanged())
        .subscribe(filterQuery => {
          this.ricercaComuni(filterQuery);
        });
    }
    this.filterTextChanged.next(searchValue);
}


  ricercaComuni(searchValue: string): void {
    let wordSearch = searchValue;
        if (wordSearch == searchValue) {
            if (searchValue) {

              this.workingCitizen.elencoComuniCaricati = [];

              let filter = this.getFilter(searchValue);
              if(filter.length >=2 && filter.length <=6){

                let codiceFiscaleUtenteLoggato = this.authService.getToken();

                this.gestioneComuniService.cercaComuniGet(codiceFiscaleUtenteLoggato, '1', 'SANSOL', JSON.stringify(filter), 'body')
                .subscribe((res) => {
                  if (res && res.length > 0) {

                    this.workingCitizen.elencoComuniCaricati = res;
                  }else{

                    this.gestioneComuniService.cercaNazioniGet(codiceFiscaleUtenteLoggato, '1', 'SANSOL', JSON.stringify(filter), 'body')
                    .subscribe((res) => {
                      if (res && res.length > 0) {
                        this.workingCitizen.elencoComuniCaricati = res;
                      }else{
                        this.workingCitizen.elencoComuniCaricati = [];
                        this.workingCitizen.elencoComuniCaricati.push({desc: "COMUNE NON TROVATO. VERIFICARE RICERCA.", codice_catasto: ""});
                      }


                    }, (error => {

                    }));
                  }
                }, (error => {

                }));
              }
            }
        }
  }


  getFilter(searchValue) {
    let lunghezza = searchValue.length;
    let inputComune;
    let comune = searchValue;
    let comuneReg = /^[A-Za-z ']*$/;



    if(lunghezza >=2 && lunghezza <= 5) {

      if (comuneReg.test(comune)) {
        inputComune=comune.toUpperCase();
        this.workingCitizen.isValid = false;
      }
      else{
        inputComune="";
        this.workingCitizen.isValid = true;
      }
    }else{
      inputComune="";
      this.workingCitizen.isValid = true;
    }
    return inputComune;
  }


  annullaRicercaComuni(){
    this.workingCitizen.isValid = true;
    this.workingCitizen.comune_nascita = "";
  }


  loadDescrizoneDocumento() {

    let codiceFiscaleUtenteLoggato = this.authService.getToken();

    this.codificheCitizenService.descrizioneDocumentoGet(
      codiceFiscaleUtenteLoggato, '1', 'SANSOL', 'body', false
    ).subscribe((res) => {
        this.elencoDescrizioneDocumento = res;

      }, (error => {

      }))
  }

}
