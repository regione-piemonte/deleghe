import {Component, OnDestroy, OnInit, ViewChild, ViewChildren} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {StateService} from '../../shared/service/state.service';
import {DefaultService} from '../../api/default.service';
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
  selector: 'app-sostegno',
  templateUrl: './sostegno.component.html',
  styleUrls: ['./sostegno.component.css']
})
export class SostegnoComponent implements OnInit, OnDestroy {

  constructor(
    protected  route: ActivatedRoute,
    protected  router: Router,
    protected  stateService: StateService,
    protected  defaultService: DefaultService,
    protected  gestioneComuniService: GestioneComuniService,
    protected authService: AuthService,
  ) {
  }

  @ViewChild('dataFine') dataFine;
  @ViewChild('dataInizio') dataInizio;
  @ViewChild('data') data;

  @ViewChildren('dataNascita') dataNascita;
  @ViewChildren('comuneNascita') comuneNascita;

  fiscalCode;
  citizen;
  elencoComuniCaricati:  ElencoComuni[] = [];
  indice;
  interval;
  canProceed = false;
  filterTextChanged: Subject<string> = new Subject<string>();

  getDefaultCuratela() {
    return {
      nome: '',
      cognome: '',
      cumune_nascita: '',
      data_nascita: '',
      codice_fiscale: '',
      sesso: 'M',
      isValid: true,
      elencoComuni: this.elencoComuniCaricati,
    };
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

  onCitizenWasNotFound() {
    this.router.navigate(['ricerca', 'tutore']);
  }

  get newDichiarazione() {
    return this.stateService.data.newDichiarazione;
  }

  set newDichiarazione(newDichiarazione) {
    this.stateService.data.newDichiarazione = newDichiarazione;
  }

  onCitizenFound() {
    this.fiscalCode = this.workingCitizen.codice_fiscale;
    if (this.newDichiarazione) {
      this.newDichiarazione.citizen = this.workingCitizen;
      if (!this.newDichiarazione.tutelatoList) {
        this.newDichiarazione.tutelatoList = [this.getDefaultCuratela()];
        this.newDichiarazione.numeroDocumento = '';
        this.newDichiarazione.anno = '';
        this.newDichiarazione.autorita = '';
        this.newDichiarazione.data = '';
        this.newDichiarazione.dataInizio = '';
        this.newDichiarazione.dataFine = '';
        this.newDichiarazione.noteInterna = '';
        this.newDichiarazione.noteServizio = '';
        this.newDichiarazione.noteRichidente = '';
      } else {
        setTimeout(() => {
          this.data.nativeElement.value = this.newDichiarazione.data;
          this.dataInizio.nativeElement.value = this.newDichiarazione.dataInizio;
          this.dataFine.nativeElement.value = this.newDichiarazione.dataFine;

          let tutelatoList = this.newDichiarazione.tutelatoList;
          for (let i = 0; i < tutelatoList.length; i++) {
            this.dataNascita.forEach(datePicker => {
              if (datePicker.nativeElement.id === 'DataDiNascita-'+i) {
                datePicker.nativeElement.value = tutelatoList[i].data_nascita
              }
            })
          }
        });

      }
    } else {
      this.router.navigate(['nuova-delega', 'tutore', this.fiscalCode]);
    }

    this.interval = setInterval(() => {
      this.checkCanProceed();
    }, 500);
  }

  isEmptyDate(value) {
    return !value || value === '' || value === null
  }

  checkCanProceed() {
    let result = true;

    if (!this.dataInizio || !this.dataFine) {
      result = false
    }

    if (result) {
      this.newDichiarazione.dataInizio = this.dataInizio.nativeElement.value;
      this.newDichiarazione.dataFine = this.dataFine.nativeElement.value;

      if (this.isEmptyDate(this.newDichiarazione.dataInizio) ||
        this.isEmptyDate(this.newDichiarazione.dataFine)) {
        result = false;
      }
    }
    if (result) {
      for (let i = 0; i < this.newDichiarazione.tutelatoList.length; i++) {
        this.dataNascita.forEach(datePicker => {
          let nativeElement = datePicker.nativeElement;
          if (nativeElement.id === 'DataDiNascita-'+i) {
            this.newDichiarazione.tutelatoList[i].data_nascita = nativeElement.value;
          }
        })

        let tutelatoListElement = this.newDichiarazione.tutelatoList[i];
        if (this.isEmptyDate(tutelatoListElement.data_nascita)) {
          result = false;
        }

        if (tutelatoListElement.nome === '' ||  tutelatoListElement.cognome === '' ||
          tutelatoListElement.cumuneDiNascita === '' || tutelatoListElement.codiceFiscale === '' ||
          tutelatoListElement.sesso === '' || this.newDichiarazione.autorita === '' ||
          this.newDichiarazione.numeroDocumento === '') {
          result = false;
        }
      }
    }

    this.canProceed = result;
  }

  get workingCitizen() {
    return this.stateService.data.workingCitizen;
  }




  public onSearchChange(searchValue: string, indice) {

      if (this.filterTextChanged.observers.length === 0) {
        this.filterTextChanged
          .pipe(debounceTime(2000), distinctUntilChanged())
          .subscribe(filterQuery => {
            this.cercaComuni(filterQuery, indice);
          });
      }
      this.filterTextChanged.next(searchValue);
  }


  cercaComuni(searchValue: string, indice: number): void  {
    let wordSearch = searchValue;
        if (wordSearch == searchValue) {
            if (searchValue) {

               this.elencoComuniCaricati = [];

              let filter = this.getFilter(searchValue, indice);
              if(filter.length >=2 && filter.length <=6){

                let codiceFiscaleUtenteLoggato = this.authService.getToken();

                this.gestioneComuniService.cercaComuniGet(codiceFiscaleUtenteLoggato, '1', 'SANSOL', JSON.stringify(filter), 'body')
                .subscribe((res) => {

                  if (res && res.length > 0) {

                    this.newDichiarazione.tutelatoList[indice].elencoComuni = res;

                  }else{

                    this.gestioneComuniService.cercaNazioniGet(codiceFiscaleUtenteLoggato, '1', 'SANSOL', JSON.stringify(filter), 'body')
                    .subscribe((res) => {

                      if (res && res.length > 0) {
                        this.newDichiarazione.tutelatoList[indice].elencoComuni = res;
                      }else{
                        this.newDichiarazione.tutelatoList[indice].elencoComuni = [];
                        this.newDichiarazione.tutelatoList[indice].elencoComuni.push({desc: "COMUNE NON TROVATO. VERIFICARE RICERCA.", codice_catasto: ""});
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

  getFilter(searchValue, indice) {
    let lunghezza = searchValue.length;
    let inputComune;
    let comune = searchValue;
    let comuneReg = /^[A-Za-z ']*$/;
    let positivo = true;
    let negativo = false;

    if(lunghezza >=2 && lunghezza <= 5) {
      if (comuneReg.test(comune)) {
        inputComune=comune.toUpperCase();
        this.valorizzaIsValid(indice, negativo);
      }
      else{
        inputComune="";
        this.valorizzaIsValid(indice, positivo);
      }
    }
    else{
      inputComune="";
      this.valorizzaIsValid(indice, positivo);
    }
    return inputComune;
  }

  public toRiepilogo() {
    this.checkCanProceed();
    if (this.canProceed) {
      this.router.navigate(['nuova-delega', 'sostegno', this.fiscalCode, 'riepilogo']);
    }
  }

  valorizzaIsValid(indice, valore){
    if(indice===0){
      this.newDichiarazione.tutelatoList[0].isValid = valore;
    }
    if(indice===1){
      this.newDichiarazione.tutelatoList[1].isValid = valore;
    }
    if(indice===2){
      this.newDichiarazione.tutelatoList[2].isValid = valore;
    }
    if(indice===3){
      this.newDichiarazione.tutelatoList[3].isValid = valore;
    }
  }



  annulla(indice){

    if(indice===0){
      this.newDichiarazione.tutelatoList[0].isValid = true;
      this.newDichiarazione.tutelatoList[0].cumune_nascita="";
    }
    if(indice===1){
      this.newDichiarazione.tutelatoList[1].isValid = true;
      this.newDichiarazione.tutelatoList[1].cumune_nascita="";
    }
    if(indice===2){
      this.newDichiarazione.tutelatoList[2].isValid = true;
      this.newDichiarazione.tutelatoList[2].cumune_nascita="";
    }
    if(indice===3){
      this.newDichiarazione.tutelatoList[3].isValid = true;
      this.newDichiarazione.tutelatoList[3].cumune_nascita="";
    }
  }

  changeCardNumber($event: any) {
    let value = $event.target.value;

    while (this.newDichiarazione.tutelatoList.length < value) {
      this.newDichiarazione.tutelatoList.push(this.getDefaultCuratela());
    }
    while (this.newDichiarazione.tutelatoList.length > value) {
      this.newDichiarazione.tutelatoList = this.newDichiarazione.tutelatoList.splice(0, this.newDichiarazione.tutelatoList.length - 1);
    }
  }

}
