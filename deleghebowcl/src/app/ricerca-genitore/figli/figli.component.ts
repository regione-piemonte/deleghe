import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {DefaultService} from '../../api/default.service';
import {StateService} from '../../shared/service/state.service';
import {el} from '@angular/platform-browser/testing/src/browser_util';
import {Message} from 'primeng/api';
import {formatDate} from '@angular/common';
import {forEach} from '@angular/router/src/utils/collection';
import { AuthService } from 'src/app/auth/auth.service';
/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
@Component ({
  selector: 'app-figli',
  templateUrl: './figli.component.html',
  styleUrls: ['./figli.component.css']
})
export class FigliComponent implements OnInit, OnDestroy {
  constructor(protected  route: ActivatedRoute,
              protected  defaultService: DefaultService,
              protected  stateService: StateService,
              protected router: Router,
              protected authService: AuthService,
              ) {}

  msgs: Message[];

  onErrorDuringSearchingFigli(i) {
    this.newDichiarazione.tutelatoList[i] = this.getDefaultTutelato();
    this.newDichiarazione.tutelatoList[i].nonTrovato = undefined;

    this.msgs = [{severity: 'error', summary: '', detail: 'Il nominativo del cittadino che hai cercato non è presente nella Banca dati!'}];

    setTimeout(() => {
      this.msgs = []
    }, 10000)
  }

  onFigliNotFound(i) {
    this.newDichiarazione.tutelatoList[i] = this.getDefaultTutelato();
    this.newDichiarazione.tutelatoList[i].nonTrovato = true;
  }

  inFigliFound(i, figli) {

    let dataConvertita;

    dataConvertita = this.formattaData(figli.data_nascita);


    let giorno = dataConvertita.substring(0, 2);
    let mese = dataConvertita.substring(3, 5);
    let anno = dataConvertita.substring(6, 10);


    if (this.isUnder18Years(anno, mese, giorno)) {
      this.newDichiarazione.tutelatoList[i].info = figli;
      this.newDichiarazione.tutelatoList[i].nonTrovato = false;
    } else {
      this.onFigliNotFound(i);
    }
  }

  formattaData(dataDaConvertire): string{
    const format = 'dd/MM/yyyy';
    const locale = 'en-US';
    let dateAppoggioFrom: Date;

    dateAppoggioFrom = new Date(dataDaConvertire);
    const dateFrom = dateAppoggioFrom;
     const formattedDateFrom = formatDate(dateFrom, format, locale);
     return formattedDateFrom;
  }

  isUnder18Years(year, month, day) {
    return new Date(year + 18, month, day) >= new Date();
  }

  findFigli(i) {
    let risultatoControllo = true;
    risultatoControllo = this.checkFiscalCodeFormat(i);
    if(risultatoControllo===true){
      let tutelatoListElement = this.newDichiarazione.tutelatoList[i];

    let filter = JSON.stringify({
      codiceFiscale: tutelatoListElement.searchFiscalCode
    })


    let codiceFiscaleUtenteLoggato = this.authService.getToken();

    this.defaultService.findCitizen(codiceFiscaleUtenteLoggato, '1', 'SANSOL', filter, 'body')
      .subscribe(res => {
        if (res.length === 0) {
          this.onFigliNotFound(i);
        }
        this.inFigliFound(i, res[0]);

      }, () => this.onErrorDuringSearchingFigli(i));
    }
  }

  searchFiscalCodeFormatOk: boolean[] = [true, true, true, true];

  checkFiscalCodeFormat(i):boolean {
    let cf = this.newDichiarazione.tutelatoList[i].searchFiscalCode.toUpperCase();
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
      for( let i = 1; i <= 13; i += 2 )
        s += setpari.indexOf( set2.charAt( set1.indexOf( cf.charAt(i) )));
      for( let i = 0; i <= 14; i += 2 )
        s += setdisp.indexOf( set2.charAt( set1.indexOf( cf.charAt(i) )));
      this.searchFiscalCodeFormatOk[i] = s % 26 == cf.charCodeAt(15) - 'A'.charCodeAt(0);
    }
    return this.searchFiscalCodeFormatOk[i];
  }

  continueCreating() {
    this.router.navigate(['nuova-delega', 'genitore', this.fiscalCode, 'riepilogo']);
  }

  canProceed() {
    let result = true;
    this.newDichiarazione.tutelatoList.forEach(tut => {
      if (tut.nonTrovato || tut.nonTrovato === undefined) {
        result = false;
      }
    });

    return result;
  }

  getDefaultTutelato() {
    return {
      nonTrovato: undefined,
      searchFiscalCode: '',
      info: {
        nome: '',
        cognome: '',
        cumune_nascita: '',
        data_nascita: '',
        codice_fiscale: '',
        sesso: 'M',
      }
    };
  }

  fiscalCode;
  citizen;

  onCitizenWasNotFound() {
    this.router.navigate(['/ricerca','genitore'])
  }

  onCitizenFound() {
    this.citizen = this.workingCitizen;
    this.fiscalCode = this.workingCitizen.codice_fiscale;
    if (this.newDichiarazione) {
      if (!this.newDichiarazione.tutelatoList) {
        this.newDichiarazione.tutelatoList = [this.getDefaultTutelato()]
      }
    } else {
      this.router.navigate(['/ricerca','genitore'])
    }
  }

  get newDichiarazione() {
    return this.stateService.data.newDichiarazione;
  }

  set newDichiarazione(newDichiarazione) {
    this.stateService.data.newDichiarazione = newDichiarazione;
  }




  changeCardNumber($event: any) {
    let value = $event.target.value;

    while (this.newDichiarazione.tutelatoList.length < value) {
      this.newDichiarazione.tutelatoList.push(this.getDefaultTutelato());
    }
    while (this.newDichiarazione.tutelatoList.length > value) {
      this.newDichiarazione.tutelatoList = this.newDichiarazione.tutelatoList.splice(0, this.newDichiarazione.tutelatoList.length - 1);
    }
  }

  get workingCitizen() {
    return this.stateService.data.workingCitizen;
  }

  interval;

  ngOnDestroy(): void {
    clearInterval(this.interval)
  }

  ngOnInit() {
    this.authService.getOperatoreLoggato();


    if (this.workingCitizen) {
      this.onCitizenFound();
    } else {
      this.onCitizenWasNotFound()
    }
  }
}
