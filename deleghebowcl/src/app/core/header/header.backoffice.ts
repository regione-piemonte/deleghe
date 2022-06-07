import {AfterContentInit, Component, OnInit} from '@angular/core';
import {Utente} from '../../model/utente';
import {LoggerService} from '../../shared/service/logger.service';
import {AuthService} from '../../auth/auth.service';
import {AuthInterceptor} from '../../shared/interceptor/auth.interceptor';
import {environment} from '../../../environments/environment';
import {Router} from '@angular/router';
import { THIS_EXPR } from '@angular/compiler/src/output/output_ast';
import {DefaultService} from '../../api/default.service';
import {LoginOperatore} from '../../model/loginOperatore';

/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

@Component({
  selector: 'app-backoffice-header',
  templateUrl: './header.backoffice.html',
  styleUrls: ['./header.backoffice.css']
})
export class HeaderBackofficeComponent implements OnInit, AfterContentInit {
  operatore: Utente;

  constructor(private logger: LoggerService,
              private authService: AuthService,
              protected router: Router,
              protected defaultService: DefaultService,
              private authinterceptor: AuthInterceptor ) {
  }

  logoutUrl = environment.delegheboLogout;
  statusCode: number;
  nomeCognome;
  loginUserData = {};
  loginOperatore: LoginOperatore;
  loginOperatoreAsl: LoginOperatore;
  token: string;

  showHeader() {
    return (this.router.url !== '/home' && this.router.url !== '/auth');
  }



  async ngOnInit() {

    await this.getOperatoreLoggatoAdHoc();

    if(this.getOperatoreLoggatoAdHoc()){
      this.setAnagraficaOperatoreLoggato();
    }

  };


  ngAfterContentInit(): void {

  }

  logout() {

    this.authService.getLogout();
  }

  urlPua() {

    this.router.navigate(["/"]).then(result=>{window.location.href = this.authService.getURLPua()});
  }

  setAnagraficaOperatoreLoggato(){


    let cognome =localStorage.getItem('cognomeOperatore');
    let nome = localStorage.getItem('nomeOperatore');

    this.nomeCognome = nome + ' ' + cognome;

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

     this.setAnagraficaOperatoreLoggato();
   }, (err) => {


      this.router.navigate(['unauthorized']);
   });
  }


}
