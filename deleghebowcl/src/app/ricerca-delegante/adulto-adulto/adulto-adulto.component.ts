import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {DefaultService} from '../../api/default.service';
import {StateService} from '../../shared/service/state.service';
import { GestioneComuniService } from '../../api/gestioneComuni.service';
import { ElencoComuni } from 'src/app/model/elencoComuni';
import { AuthService } from 'src/app/auth/auth.service';
import { debounce, debounceTime, distinctUntilChanged } from 'rxjs/operators';
import { Subject } from 'rxjs';


/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


@Component({
  selector: 'app-adulto-adulto',
  templateUrl: './adulto-adulto.component.html',
  styleUrls: ['./adulto-adulto.component.css']
})
export class AdultoAdultoComponent implements OnInit, OnDestroy {

  constructor(
    protected route: ActivatedRoute,
    protected defaultService: DefaultService,
    protected router: Router,
    protected stateService: StateService,
    protected gestioneComuniService: GestioneComuniService,
    protected authService: AuthService,
  ) {

  }

  @ViewChild('nascita') nascita;
  fiscalCode;
  citizen;
  codiceFiscale="";
  nome="";
  cognome="";
  isValid = true;
  elencoComuniCaricati:  ElencoComuni[] = [];
  filterTextChanged: Subject<string> = new Subject<string>();

  codiceFiscaleFormatOk = true;

  formattaNome(code) {
    let nomeCompleto;
    let primoCarattere = code;
    let lunghezza = code.length;
    let altriCaratteri;

    primoCarattere = code.substr(0, 1);
    primoCarattere = primoCarattere.toUpperCase();

    altriCaratteri = code.substr(1, lunghezza);
    altriCaratteri = altriCaratteri.toLowerCase();

    nomeCompleto =primoCarattere+altriCaratteri;

    return nomeCompleto;
  }

  formattaCognome(code) {
    let cognomeCompleto;
    let primoCarattere = code;
    let lunghezza = code.length;
    let altriCaratteri;

    primoCarattere = code.substr(0, 1);
    primoCarattere = primoCarattere.toUpperCase();

    altriCaratteri = code.substr(1, lunghezza);
    altriCaratteri = altriCaratteri.toLowerCase();

    cognomeCompleto =primoCarattere+altriCaratteri;

    return cognomeCompleto;
  }

  formattaComune(comune) {
    let comuneCompleto;
    let primoCarattere = comune;
    let lunghezza = comune.length;
    let altriCaratteri;

    primoCarattere = comune.substr(0, 1);
    primoCarattere = primoCarattere.toUpperCase();

    altriCaratteri = comune.substr(1, lunghezza);
    altriCaratteri = altriCaratteri.toLowerCase();

    comuneCompleto =primoCarattere+altriCaratteri;

    return comuneCompleto;
  }

  annulla(){
    this.isValid = true;
    this.newDelega.comuneDiNascita = "";
  }

  checkCodiceFiscaleFormat():boolean {
    let cf = this.newDelega.codiceFiscale.toUpperCase();
    let cfReg = /^[A-Z]{6}\d{2}[A-Z]\d{2}[A-Z]\d{3}[A-Z]$/;
    if (!cfReg.test(cf) || cf.length !== 16) {
      this.codiceFiscaleFormatOk = false;
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
      this.codiceFiscaleFormatOk = s % 26 == cf.charCodeAt(15) - 'A'.charCodeAt(0);
    }
    return this.codiceFiscaleFormatOk;
  }




  public onSearchChange(searchValue: string) {

      if (this.filterTextChanged.observers.length === 0) {
        this.filterTextChanged
          .pipe(debounceTime(2000), distinctUntilChanged())
          .subscribe(filterQuery => {
            this.cercaComuni(filterQuery);
          });
      }
      this.filterTextChanged.next(searchValue);
  }


  cercaComuni(searchValue){
    let wordSearch = searchValue;
      if (wordSearch == searchValue) {
        if (searchValue) {

          this.elencoComuniCaricati = [];

          let filter = this.getFilter(searchValue);
          if(filter.length >=2 && filter.length <=6){

            let codiceFiscaleUtenteLoggato = this.authService.getToken();

            this.gestioneComuniService.cercaComuniGet(codiceFiscaleUtenteLoggato, '1', 'SANSOL', JSON.stringify(filter), 'body')
            .subscribe((res) => {
              if (res && res.length > 0) {

                this.elencoComuniCaricati = res;
              }else{

                this.gestioneComuniService.cercaNazioniGet(codiceFiscaleUtenteLoggato, '1', 'SANSOL', JSON.stringify(filter), 'body')
                .subscribe((res) => {

                  if (res && res.length > 0) {
                    this.elencoComuniCaricati = res;
                  }else{
                    this.elencoComuniCaricati = [];
                    this.elencoComuniCaricati.push({desc: "COMUNE NON TROVATO. VERIFICARE RICERCA.", codice_catasto: ""});
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

  getFilter(searchValue): string {
    let lunghezza = searchValue.length;
    let inputComune: string;
    let comune = searchValue;
    let comuneReg = /^[A-Za-z ']*$/;


        if(lunghezza >=2 && lunghezza <= 5) {

          if (comuneReg.test(comune)) {
            inputComune=comune.toUpperCase();
            this.isValid = false;
          }
          else{
            inputComune="";
            this.isValid = true;
          }
        }
        else{
          inputComune="";
          this.isValid = true;
        }
    return inputComune;
  }

  onCitizenWasNotFound() {
    this.router.navigate(['ricerca', 'delegante']);
  }

  get newDelega() {
    return this.stateService.data.newDelega;
  }

  set newDelega(newDelega) {
    this.stateService.data.newDelega = newDelega;
  }

  pulisciCampi(){
    this.stateService.data.newDelega.nome = "";
    this.stateService.data.newDelega.cognome = "";
    this.nascita.nativeElement.value = ""
    this.stateService.data.newDelega.sesso = "M";
    this.stateService.data.newDelega.comuneDiNascita = "";
    this.stateService.data.newDelega.codiceFiscale = "";

    this.newDelega.legameTestFailed = false;
    this.newDelega.delegaTestFailed = false;
  }

  canProceed = false;

  onCitizenFound() {
    this.fiscalCode = this.workingCitizen.codice_fiscale;
    if (!this.stateService.data.newDelega) {
      this.stateService.data.newDelega = {};
      this.stateService.data.newDelega.nome = "";
      this.stateService.data.newDelega.cognome = "";
      this.stateService.data.newDelega.dataNascita = "";
      this.stateService.data.newDelega.sesso = "M";
      this.stateService.data.newDelega.comuneDiNascita = "";
      this.stateService.data.newDelega.codiceFiscale = "";
      this.stateService.data.newDelega.noteRichidente = "";
      this.stateService.data.newDelega.noteServizio = "";
      this.stateService.data.newDelega.noteInterna = "";
      this.stateService.data.newDelega.agreementPushed = false;
      this.stateService.data.newDelega.services = [];
      setTimeout(() => {
        this.nascita.nativeElement.value = ""
      });
    } else {
      setTimeout(() => {
        this.nascita.nativeElement.value = this.stateService.data.newDelega.dataNascita;
      });
    }

    this.interval = setInterval(() => {
     this.updateValues();
    }, 300)
  }

  interval;

  updateValues() {
    this.newDelega.dataNascita = this.nascita.nativeElement.value;
    let newDelega = this.newDelega;

    this.canProceed = newDelega.nome !== "" && newDelega.cognome !== ""  && newDelega.dataNascita !== "" && newDelega.sesso !== "" && newDelega.comuneDiNascita !== "" && newDelega.codiceFiscale !== ""

  }

  get workingCitizen() {
    return this.stateService.data.workingCitizen;
  }

  ngOnInit() {
    this.authService.getOperatoreLoggato();

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

  goToServices() {
    let risultatoControlloCF = true;
    risultatoControlloCF = this.checkCodiceFiscaleFormat();
    if(risultatoControlloCF===true){
      setTimeout(() => {
        this.newDelega.dataNascita = this.nascita.nativeElement.value;
        this.newDelega.nome = this.formattaNome(this.newDelega.nome);
        this.newDelega.cognome = this.formattaCognome(this.newDelega.cognome);
        this.newDelega.comuneDiNascita = this.formattaComune(this.newDelega.comuneDiNascita);

        this.router.navigate(['nuova-delega','delegante', this.fiscalCode, "servizi"])
      }, 600)
    }

  }
}
