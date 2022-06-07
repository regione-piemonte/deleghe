import {Injectable} from '@angular/core';
import {Utente} from '../model/utente';
import {LoggerService} from '../shared/service/logger.service';
import {UUID} from 'angular2-uuid';
import {environment} from '../../environments/environment';
import { Observable } from 'rxjs/Observable';
import { HttpClientModule, HttpClient } from '@angular/common/http';
import { map, filter, catchError, mergeMap } from 'rxjs/operators';
import { throwError } from 'rxjs';
import {Router} from '@angular/router';
import {DefaultService} from '../api/default.service';
import {LoginOperatore} from '../model/loginOperatore';
import { of } from 'rxjs';
import { TokenResponse } from '../model/tokenResponse';

/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/



@Injectable({providedIn: 'root'})
export class AuthService {
  token: string;
  aura: string;
  userInfo: Utente;

  loginOperatore: LoginOperatore;
  loginOperatoreAsl: LoginOperatore;
  codiceFiscaleLogin;
  tokenResponse : TokenResponse;
  tokenOperatoreLoggato: string;


  constructor(private loggerservice: LoggerService,
    protected defaultService: DefaultService,
    private http: HttpClient,
    protected router: Router,
    ) {
  }


  setAura(isAllowed: string) {

    this.loggerservice.info('Aura isAllowed:' + isAllowed);
    localStorage.setItem(environment.auraAllowed, isAllowed);
  }


  isAuthenticatedAura(): boolean {

    return this.aura != null;
  }


  setToken(token: any) {


    localStorage.setItem('token', token);
  }


  isAuthenticated(): boolean {

    this.loggerservice.info('isAuthenticated: token=' + this.getToken());
    this.loggerservice.info('isAuthenticated: userInfo=', this.userInfo);
    return this.getToken() != null && this.userInfo != null;
  }

  getToken(): string {
    this.token = localStorage.getItem('token');



    return this.token;
  }


  getOnAppExitURLOperatore(): string {

    try { if ( environment.delegheboLogout != null ) return environment.delegheboLogout; }
    catch ( e ) { return environment.delegheboLogout; }
  }

  getURLPua(): string {

    try { if ( environment.urlPua != null ) return environment.urlPua; }
    catch ( e ) { return environment.urlPua; }
  }


  getOperatoreLoggato(){



   let filter = JSON.stringify({
     codiceFiscale: "XYZ",
   });



   this.defaultService.getIdentitaOperatore("XYZ", '1', 'SANSOL', filter, "body").subscribe((res) => {


     this.loginOperatore = res;



     let tokenLcce = this.loginOperatore.token_lcce;
     let ip_address = this.loginOperatore.ip_address;



              this.setToken(this.loginOperatore.codice_fiscale_operatore);

              localStorage.setItem('codiceFiscaleOperatore',this.loginOperatore.codice_fiscale_operatore);
              localStorage.setItem('cognomeOperatore',this.loginOperatore.cognome_operatore);
              localStorage.setItem('nomeOperatore',this.loginOperatore.nome_operatore);
   }, (err) => {

      this.router.navigate(['unauthorized']);
   });
  }

  getOperatoreLoggatoAppComponent(){



   let filter = JSON.stringify({
     codiceFiscale: "XYZ",
   });



   this.defaultService.getIdentitaOperatore("XYZ", '1', 'SANSOL', filter, "body").subscribe((res) => {


     this.loginOperatore = res;




     let tokenLcce = this.tokenOperatoreLoggato;
     let ip_address = this.loginOperatore.ip_address;


     if(tokenLcce!=null){

     }else{

     }




              this.setToken(this.loginOperatore.codice_fiscale_operatore);

              localStorage.setItem('codiceFiscaleOperatore',this.loginOperatore.codice_fiscale_operatore);
              localStorage.setItem('cognomeOperatore',this.loginOperatore.cognome_operatore);
              localStorage.setItem('nomeOperatore',this.loginOperatore.nome_operatore);

   }, (err) => {


      this.router.navigate(['unauthorized']);
   });
  }

  getTokenLcceFittizio(tokenLcce, ip_address, asl){




    localStorage.setItem('aslOperatore',asl);

  }

  getTokenOperatoreLoggato(token){
    this.tokenOperatoreLoggato = token;

  }

  getTokenLcce(tokenLcce, ip_address): boolean {

    let risultato: boolean;


   let filter = this.getFilterToken(tokenLcce, 'DEL', ip_address);






   this.defaultService.getTokenInformation2("XYZ", '1', 'SANSOL', JSON.stringify(filter), "body").subscribe((resToken) => {

      this.tokenResponse = resToken;
      let esitoRicerca =this.tokenResponse.esito;
      let aslOperatore =this.tokenResponse.codice_azienda;

      if(esitoRicerca==='SUCCESSO'){

        localStorage.setItem('aslOperatore',aslOperatore);
        risultato = true;

      }else{

        risultato = false;

        this.router.navigate(['/']);
      }
    });
    return risultato;
  }

  getFilterToken(token, applicazione, ipbrowser ) {
    let filter: any = {};
    filter.token= token;
    filter.applicazione= applicazione;
    filter.ip_browser= ipbrowser;
    return filter;
  }



  getLogout(){


     let filter = JSON.stringify({
      codiceFiscale: "XYZ",
    });


     this.defaultService.removeFromSession(
      "XYZ",
      '1',
      'SANSOL',
      filter,
      "body"
      ).subscribe((res) => console.log("RESULT getLogout: "));


    this.removeCookies();

    this.setToken(null);
    localStorage.removeItem('codiceFiscaleOperatore');
    localStorage.removeItem('cognomeOperatore');
    localStorage.removeItem('nomeOperatore');
    localStorage.removeItem('aslOperatore');

    localStorage.clear();


    this.router.navigate(["/"]).then(result=>{window.location.href =  this.getOnAppExitURLOperatore()});
  }

  pulisciSessione(){

     let filter = JSON.stringify({
      codiceFiscale: "XYZ",
    });


     this.defaultService.removeFromSession(
      "XYZ",
      '1',
      'SANSOL',
      filter,
      "body"
      ).subscribe((res) => console.log("RESULT getLogout: "));

    this.removeCookies();

    this.setToken(null);
    localStorage.removeItem('codiceFiscaleOperatore');
    localStorage.removeItem('cognomeOperatore');
    localStorage.removeItem('nomeOperatore');
    localStorage.removeItem('aslOperatore');
    localStorage.clear();
  }

  esitoPositivo(status){
  }


  private removeCookies() {
    document.cookie = 'JSESSIONID=ou;expires=' + new Date().toUTCString() + '; Max-Age=1; path=/';

    document.cookie.split(';')
    .forEach( c => {
      document.cookie = c.replace(/^ +/, '')
                         .replace(/=.*/, '=;expires=' + new Date().toUTCString() + ';path=/');
    });
  }

}
