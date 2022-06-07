import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {DefaultService} from '../../api/default.service';
import {StateService} from '../../shared/service/state.service';
import { GestioneComuniService } from '../../api/gestioneComuni.service';
import {GestioneCodificheService} from '../../api/gestioneCodifiche.service';
import { ElencoComuni } from 'src/app/model/elencoComuni';
import {Cittadino} from '../../model/cittadino';
import { IfStmt } from '@angular/compiler';
import { DocumentoTipo } from 'src/app/model/documentoTipo';
import { AuthService } from 'src/app/auth/auth.service';
import { debounce, debounceTime, distinctUntilChanged } from 'rxjs/operators';
import { Subject } from 'rxjs';
/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
@Component({
  selector: 'app-dichiarazione-genitore',
  templateUrl: './dichiarazione-genitore.component.html',
  styleUrls: ['./dichiarazione-genitore.component.css']
})
export class DichiarazioneGenitoreComponent implements OnInit, OnDestroy {

  constructor(protected route: ActivatedRoute,
              protected defaultService: DefaultService,
              protected stateService: StateService,
              protected router: Router,
              protected codificheCitizenService: GestioneCodificheService,
              protected gestioneComuniService: GestioneComuniService,
              protected authService: AuthService,
  ) {
  }

  @ViewChild('noInput') noInput;
  @ViewChild('siInput') siInput;
  @ViewChild('newCittDate') newCittDate;
  @ViewChild('data_nascita') data_nascita;
  @ViewChild('data_rilascio') data_rilascio;
  @ViewChild('dataScadenzaDoc') dataScadenzaDoc;

  isShowingGenitoriSummary = false;
  interval;
  newAltroGenitore = undefined;
  fiscalCode;
  citizen;
  searchCodiceFiscale;
  searchFiscalCodeFormatOk: boolean[] = [true, true, true];
  genitorePiemontese = true;
  proceedUno = true;

  fileToUpload: File = null;
  elencoDescrizioneDocumento: DocumentoTipo[] = [];
  filterTextChanged: Subject<string> = new Subject<string>();

  ngOnInit() {
    this.authService.getOperatoreLoggato();



    this.loadDescrizoneDcoumento();
    if (this.workingCitizen) {
      this.onCitizenFound();
      this.citizen = this.workingCitizen;
    } else {
      this.onCitizenWasNotFound()
    }
  }

  ngOnDestroy(): void {
    clearInterval(this.interval)
  }


  onCitizenFound() {
    this.fiscalCode = this.workingCitizen.codice_fiscale;

    if (this.newDichiarazione) {

      this.proceedUno = false;
      setTimeout(() => {
        if (this.newDichiarazione.isAltroGenitore) {
          this.noInput.nativeElement.checked = true;

          if (this.newDichiarazione.altroGenitore.length !== 0) {
            let searchCodiceFiscale1 = this.newDichiarazione.altroGenitore.codice_fiscale;
            if (searchCodiceFiscale1) {
              this.searchCodiceFiscale = searchCodiceFiscale1
            }

            setTimeout(() => {
              if (this.newCittDate) {
                this.newCittDate.nativeElement.value = this.newDichiarazione.altroGenitore.data_nascita;
              }
            })
          }

        } else  {
          this.noInput.nativeElement.checked = true;
        }
      })
    } else {

      this.proceedUno = false;
      this.newDichiarazione = {
        citizen: this.citizen,
        altroGenitore: this.getDefaultGenitore(),
        isAltroGenitore: false
      }
    }
  }

  onCitizenWasNotFound() {
    this.router.navigate(['/ricerca','genitore'])
  }

  get newDichiarazione() {
    return this.stateService.data.newDichiarazione;
  }

  set newDichiarazione(newDichiarazione) {
    this.stateService.data.newDichiarazione = newDichiarazione;
  }


  get workingCitizen() {
    return this.stateService.data.workingCitizen;
  }


  getDefaultGenitore() {
    return {
      nonTrovato: undefined,
      isNotFoundWarning: undefined,
      searchFiscalCode: '',
      nome: '',
      cognome: '',
      cumune_nascita: '',
      data_nascita: '',
      codice_fiscale: '',
      sesso: 'M',
      tipoDocumento: '',
      nrodocumento: '',
      rilasciato: '',
      data_rilascio: '',
      dataScadenzaDoc: '',
      codice: '',
      desc: '',
    };
  }


  chooseAddingGenitore(isAddingGenitore: boolean) {

    if (!isAddingGenitore) {

      this.newDichiarazione.isAltroGenitore = isAddingGenitore;
      this.isShowingGenitoriSummary = false;
      this.newDichiarazione.altroGenitore = [this.getDefaultGenitore()];
      this.genitorePiemontese=true;
      this.proceedUno = false;

    }else{

      this.newDichiarazione.isAltroGenitore = isAddingGenitore;
      this.isShowingGenitoriSummary = true;
      this.newDichiarazione.altroGenitore = [this.getDefaultGenitore()];
      this.genitorePiemontese=true;
      this.proceedUno = true;
    }
  }


  changeCardNumber($event: any) {
    let value = $event.target.value;

    while (this.newDichiarazione.altroGenitore.length < value) {
      this.newDichiarazione.altroGenitore.push(this.getDefaultGenitore());
    }
    while (this.newDichiarazione.altroGenitore.length > value) {
      this.newDichiarazione.altroGenitore = this.newDichiarazione.altroGenitore.splice(0, this.newDichiarazione.altroGenitore.length - 1);
    }
  }


  findNewGenitore(i) {
    let risultatoControllo = true;
    risultatoControllo = this.checkFiscalCodeFormat(i);
    if(risultatoControllo===true){
      let genitoreListElement = this.newDichiarazione.altroGenitore[i];

    let filter = JSON.stringify({
      codiceFiscale: genitoreListElement.searchFiscalCode
    });


    let codiceFiscaleUtenteLoggato = this.authService.getToken();

    this.defaultService.findCitizen(codiceFiscaleUtenteLoggato, '1', 'SANSOL', filter, 'body')
      .subscribe(res => {
        if (res.length === 0) {
          this.onNotFound(i);
        }
        this.newDichiarazione.altroGenitore[i] = res[0];
        this.newDichiarazione.altroGenitore[i].nonTrovato = false;
        this.newDichiarazione.isAltroGenitore = true;
        this.newDichiarazione.altroGenitore[i].isNotFoundWarning = false;
        this.proceedUno = false;

      }, () =>  this.onNotFound(i));
    }
  }


  onNotFound(i) {
    this.genitorePiemontese=false;
    this.newDichiarazione.altroGenitore[i].isNotFoundWarning = true;
    this.newDichiarazione.altroGenitore[i].nonTrovato = true;
    this.newDichiarazione.altroGenitore[i].nome= "";
    this.newDichiarazione.altroGenitore[i].cognome= "";
    this.newDichiarazione.altroGenitore[i].data_nascita= "";
    this.newDichiarazione.altroGenitore[i].codice_fiscale= "";
    this.newDichiarazione.altroGenitore[i].comune_nascita= "";
    this.newDichiarazione.altroGenitore[i].sesso= "M";
    this.newDichiarazione.altroGenitore[i].isValid= true;
    this.newDichiarazione.altroGenitore[i].tipoDocumento= "";
    this.newDichiarazione.altroGenitore[i].nrodocumento= "";
    this.newDichiarazione.altroGenitore[i].data_rilascio= "";
    this.newDichiarazione.altroGenitore[i].dataScadenzaDoc= "";
    this.newDichiarazione.altroGenitore[i].rilasciato= "";
    this.newDichiarazione.altroGenitore[i].codice= "";
    this.newDichiarazione.altroGenitore[i].desc= "";
  }



  public onSearchChange(searchValue: string, i) {

    if (this.filterTextChanged.observers.length === 0) {
      this.filterTextChanged
        .pipe(debounceTime(2000), distinctUntilChanged())
        .subscribe(filterQuery => {
          this.cercaComuni(filterQuery, i);
        });
    }
    this.filterTextChanged.next(searchValue);
  }


  cercaComuni (searchValue: string, i): void {
    let wordSearch = searchValue;
        if (wordSearch == searchValue) {
            if (searchValue) {

              this.newDichiarazione.altroGenitore[i].elencoComuniCaricati = [];

              let filter = this.getFilter(searchValue, i);
              if(filter.length >=2 && filter.length <=6){

                let codiceFiscaleUtenteLoggato = this.authService.getToken();

                this.gestioneComuniService.cercaComuniGet(codiceFiscaleUtenteLoggato, '1', 'SANSOL', JSON.stringify(filter), 'body')
                .subscribe((res) => {
                  if (res && res.length > 0) {

                    this.newDichiarazione.altroGenitore[i].elencoComuniCaricati = res;
                  }else{

                    this.gestioneComuniService.cercaNazioniGet(codiceFiscaleUtenteLoggato, '1', 'SANSOL', JSON.stringify(filter), 'body')
                    .subscribe((res) => {

                      if (res && res.length > 0) {
                        this.newDichiarazione.altroGenitore[i].elencoComuniCaricati = res;
                      }else{
                        this.newDichiarazione.altroGenitore[i].elencoComuniCaricati = [];
                        this.newDichiarazione.altroGenitore[i].elencoComuniCaricati.push({desc: "COMUNE NON TROVATO. VERIFICARE RICERCA.", codice_catasto: ""});
                      }

                    }, (error => {
                      console.error('Errore nel caricamento della combobox comuni di nascita');
                      console.error(error);
                    }));
                  }
                }, (error => {
                  console.error('Errore nel caricamento della combobox comuni di nascita');
                  console.error(error);
                }));
              }
            }
        }
  }


  getFilter(searchValue, i) {
    let lunghezza = searchValue.length;
    let inputComune;
    let comune = searchValue;
    let comuneReg = /^[A-Za-z ']*$/;



    if(lunghezza >=2 && lunghezza <= 5) {

      if (comuneReg.test(comune)) {
        inputComune=comune.toUpperCase();
        this.newDichiarazione.altroGenitore[i].isValid = false;
      }
      else{
        inputComune="";
        this.newDichiarazione.altroGenitore[i].isValid = true;
      }
    }
    else{
      inputComune="";
      this.newDichiarazione.altroGenitore[i].isValid = true;
    }
    return inputComune;
  }


  annulla(i){
    this.newDichiarazione.altroGenitore[i].isValid = true;
    this.newDichiarazione.altroGenitore[i].comune_nascita = "";
  }

  checkFiscalCodeFormat(i):boolean {
    let cf = this.newDichiarazione.altroGenitore[i].searchFiscalCode.toUpperCase();
    let cfReg = /^[A-Z]{6}\d{2}[A-Z]\d{2}[A-Z]\d{3}[A-Z]$/;
    if (!cfReg.test(cf) || cf.length !== 16)
    {
      this.searchFiscalCodeFormatOk[i] = false;
    } else {
      let set1 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
      let set2 = "ABCDEFGHIJABCDEFGHIJKLMNOPQRSTUVWXYZ";
      let setpari = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
      let setdisp = "BAKPLCQDREVOSFTGUHMINJWZYX";
      let s = 0;
      for( let i = 1; i <= 13; i += 2 ){
        s += setpari.indexOf( set2.charAt( set1.indexOf( cf.charAt(i) )));
      }
      for( let i = 0; i <= 14; i += 2 ){
        s += setdisp.indexOf( set2.charAt( set1.indexOf( cf.charAt(i) )));
      }
      this.searchFiscalCodeFormatOk[i] = s % 26 == cf.charCodeAt(15) - 'A'.charCodeAt(0);
    }
    return this.searchFiscalCodeFormatOk[i];
  }


  canProceedDue() {
    let result = true;

    if(this.newDichiarazione.altroGenitore[0].isNotFoundWarning == true){
      result = this.controllaDatiFuoriRegione();
    }
    return result;
  }


  controllaDatiFuoriRegione(){
    let result = true;
    if(this.newDichiarazione.altroGenitore[0].isNotFoundWarning == true){
      if(this.newDichiarazione.altroGenitore[0].nome ==""){
        result = false;
      }
      if(this.newDichiarazione.altroGenitore[0].cognome ==""){
        result = false;
      }
      if(this.newDichiarazione.altroGenitore[0].codice_fiscale ==""){
        result = false;
      }
      if(this.newDichiarazione.altroGenitore[0].comune_nascita ==""){
        result = false;
      }
      if(this.newDichiarazione.altroGenitore[0].rilasciato ==""){
        result = false;
      }

    }
    return result;
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


  loadDatiFuoriPiemonte() {
    if(this.newDichiarazione.altroGenitore[0].isNotFoundWarning != undefined){
      if(this.newDichiarazione.altroGenitore[0].isNotFoundWarning == true){
        this.controllaDatiFuoriRegione();
        if(this.data_nascita.nativeElement.value!=null){
          this.newDichiarazione.altroGenitore[0].data_nascita = this.myDateParser(this.data_nascita.nativeElement.value);
        }

        if(this.data_rilascio.nativeElement.value!=null){
          this.newDichiarazione.altroGenitore[0].data_rilascio = this.myDateParser(this.data_rilascio.nativeElement.value);
        }
        if(this.dataScadenzaDoc.nativeElement.value!=null){
          this.newDichiarazione.altroGenitore[0].dataScadenzaDoc = this.myDateParser(this.dataScadenzaDoc.nativeElement.value);
        }

        this.newDichiarazione.altroGenitore[0].nome = this.formattaStringa(this.newDichiarazione.altroGenitore[0].nome);
        this.newDichiarazione.altroGenitore[0].cognome = this.formattaStringa(this.newDichiarazione.altroGenitore[0].cognome);
        this.newDichiarazione.altroGenitore[0].comune_nascita = this.formattaStringa(this.newDichiarazione.altroGenitore[0].comune_nascita);
      }else{

      }
    }
  }


  myDateParser(dateStr : string) : string {

    let giorno = dateStr.substring(0, 2);
    let mese = dateStr.substring(3, 5);
    let anno = dateStr.substring(6, 10);

    let validDate = anno + '-' + mese + '-' + giorno;
    return validDate
  }


  continueCreatingUno() {
    this.router.navigate(['nuova-delega', 'genitore', this.fiscalCode, 'figli']);
  }


  continueCreatingDue() {
    this.loadDatiFuoriPiemonte();
    this.router.navigate(['nuova-delega', 'genitore', this.fiscalCode, 'figli']);
  }


  prendiValoreDocumento($event: any) {
    let valueDoc = $event.target.value;
    this.newDichiarazione.altroGenitore[0].codice = valueDoc;
    if(valueDoc==1){
      this.newDichiarazione.altroGenitore[0].tipoDocumento = 'Carta di identita';
    }
    if(valueDoc==2){
      this.newDichiarazione.altroGenitore[0].tipoDocumento = 'Patente';
    }
  }


  loadDescrizoneDcoumento() {

    let codiceFiscaleUtenteLoggato = this.authService.getToken();

    this.codificheCitizenService.descrizioneDocumentoGet(
      codiceFiscaleUtenteLoggato, '1', 'SANSOL', 'body', false
    ).subscribe((res) => {
        this.elencoDescrizioneDocumento = res;

      }, (error => {
        console.error('Errore nel caricamento della combobox descrizone documento');
        console.error(error);
      }))
  }

}
