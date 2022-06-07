import {Component, OnInit} from '@angular/core';
import {ModalService} from '../../shared/modal/modal.service';
import {GestioneDichiarazioniService} from '../../api/gestioneDichiarazioni.service';
import {ActivatedRoute, Router} from '@angular/router';
import {DichiarazioneCitizenService} from '../../shared/service/dichiarazione-citizen.service';
import {Dichiarazione} from '../../model/dichiarazione';
import {CittadinoOrdered} from '../../model/cittadino';
import {ShowMessagesService} from '../../shared/service/show-messages.service';
import {Message, MessageService} from 'primeng/api';
import { AuthService } from 'src/app/auth/auth.service';
import {InformativaConsensi} from 'src/app/model/informativaConsensi';
import {GestioneCodificheService} from '../../api/gestioneCodifiche.service';
import {DefaultService} from '../../api/default.service';
import {DocumentoFile} from '../../model/documentoFile';

/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


@Component({
  selector: 'dettaglio-dichiarazioni',
  templateUrl: './dettaglio-dichiarazioni.component.html',
  styleUrls: ['./dettaglio-dichiarazioni.component.css']
})
export class DettaglioDichiarazioniComponent implements OnInit {

  constructor(
    protected  route: ActivatedRoute,
    protected  router: Router,
    protected  modalService: ModalService,
    protected gestioneDichiarazioniService: GestioneDichiarazioniService,
    protected dichiarazioneCitizenService: DichiarazioneCitizenService,
    private showMessagesService: ShowMessagesService,
    private messageService: MessageService,
    protected authService: AuthService,
    protected  defaultService: DefaultService,
    protected codificheCitizenService: GestioneCodificheService,

  ) {
  }

  informativaConsensi: InformativaConsensi[] = [];
  loading = true;
  citizens: CittadinoOrdered[] = [];
  dichiarazione: Dichiarazione;
  pdfInformativa;
  agreementPushed = false;
  documentoFile:DocumentoFile;
  fileInBytes: string;
  documentoPresente =  false;
  nomeOperatore;
  cognomeOperatore;
  numeroDocumento;
  autorita;
  dataInizioTutela;
  dataFineTutela;
  attoFormale = true;


  parseDettaglio(dett, dich, citizen, ruolo) {

    return {
      uuid: dich.uuid,
      nPratica: dett.dicdet_id,
      asl: citizen.asl,
      ruolo: ruolo,
      citizen: citizen,
      compilatore: dich.cittadino.nome + ' ' + dich.cittadino.cognome,
      statoPratica: dett.stato.descrizione,
      rataRichiesta: new Date(dich.data_inserimento),
      tipoDelega: dich.tipo.descrizione,
      Azioni: '',
    };
  }

  parseDichiarazione(dich, dett, numeroPratica) {
    let allDettCitizens = [];



      let nroPraticaDett =dett.dicdet_id;


        allDettCitizens.push(this.parseDettaglio(dett, dich, dett.genitore_tutore_curatore, dett.ruolo_genitore_tutore_curatore.codice));
        allDettCitizens.push(this.parseDettaglio(dett, dich, dett.figlio_tutelato_curato, dett.ruolo_figlio_tutelato_curato.codice));



    let mainGenitoreDett;
    let filteredDettCitizens = [];

    allDettCitizens.forEach(dett => {
      let willAdd = true;
      filteredDettCitizens.forEach(filtered => {
        if (dett.citizen.codice_fiscale === filtered.data.citizen.codice_fiscale) {
          willAdd = false;
        }
      });
      if (willAdd) {
        if (dett.ruolo === 'GENITORE_1' || dett.ruolo === 'GENITORE_2' ||
            dett.ruolo === 'TUTORE' || dett.ruolo === 'TUTORE_DI_UN_MAGIORE_INTER') {
          mainGenitoreDett = dett;
        } else {
          filteredDettCitizens.push({
            data: dett,
            children: []
          });
        }
      }
    });

    return {
      data: mainGenitoreDett,
      children: filteredDettCitizens
    };
  }

  ngOnInit() {
    let filter = {
      uuid: {
        eq: this.route.snapshot.paramMap.get('uuid')
      }
    };


    let numeroPratica= this.route.snapshot.paramMap.get('nPratica');

    this.authService.getOperatoreLoggato();
    this.cognomeOperatore = localStorage.getItem('cognomeOperatore');
    this.nomeOperatore= localStorage.getItem('nomeOperatore');
    this.loadInformativaConsensi();


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

          this.dichiarazione.dettagli.forEach(dichia => {
            if(dichia.documento != null){
              if(dichia.documento.file !=null){
                this.documentoPresente=true;
                this.cercaFileDocumento(dichia.documento.file.file_id);
              }
            }
          });



          if(this.dichiarazione.numero_documento!= null){
            this.attoFormale=true;
            this.numeroDocumento = this.dichiarazione.numero_documento;
            this.autorita = this.dichiarazione.autorita;
            this.dataInizioTutela = this.dichiarazione.data_inizio_tutela;
            this.dataFineTutela = this.dichiarazione.data_fine_tutela;
          }else{
            this.attoFormale=false;

          }


          this.citizens = this.getCitizens(this.dichiarazione, numeroPratica);
          this.loading = false;
        }
      });
  }


  cercaFileDocumento(fileId){

    let codiceFiscaleUtenteLoggato = this.authService.getToken();

    this.defaultService.ricercaFileDocumentoPerId(
      codiceFiscaleUtenteLoggato,
      '1',
      'SANSOL',
      fileId,
      'body').subscribe((res) => {
        if (res) {
          this.documentoFile = res;


          this.fileInBytes=this.documentoFile.bytes;
        }
      });
  }


  showPdf(){
    var pdfInBase64 = atob(this.fileInBytes);


    const linkSource =  pdfInBase64;

    const downloadLink = document.createElement("a");
    const fileName = "statoDiFamiglia.pdf";

    downloadLink.href = linkSource;

    downloadLink.target = '_blank';
    downloadLink.download  = fileName;

    document.body.appendChild(downloadLink);
    downloadLink.click();

    document.body.removeChild(downloadLink);
}



  getCitizens(dich, numeroPratica) {
    let citizens: CittadinoOrdered[] = [];

    dich.dettagli.forEach(dettDich => {
      let parseDichiarazione1 = this.parseDichiarazione(dich, dettDich, numeroPratica);
      let ruoloGenitore =dettDich.ruolo_genitore_tutore_curatore.codice;

      if(ruoloGenitore==='GENITORE_1'){

        citizens.push({
          citizen: parseDichiarazione1.data.citizen,
          order: 0,
          nPratica: parseDichiarazione1.data.nPratica
        })

        parseDichiarazione1.children.forEach(dett => {

          let order = 2
          if (dett.data.ruolo === 'FIGLIO'  ||  dett.data.ruolo === 'TUTELATO' ) {
            order = 2
          }
          citizens.push({
            citizen: dett.data.citizen,
            order: order,
            nPratica: dett.data.nPratica
          })
        })
      }else{

        citizens.push({
          citizen: parseDichiarazione1.data.citizen,
          order: 1,
          nPratica: parseDichiarazione1.data.nPratica
        })
      }
    });

    return citizens;
  }

  navigateAndLeftPositiveAlert(status) {
    this.showMessagesService.setMsgs([{severity: 'success', summary: '', detail: 'La dichiarazione è stata correttamente aggiornata con il nuovo stato (' + status+")"}]);
    this.router.navigate(['/']);
  }

  msgs: Message[] = this.showMessagesService.getMsgs();

  negativeAlert() {
    this.loading = false;
    this.messageService.add({severity: 'error', summary: '', detail: "ATTENZIONE! Si è verificato un errore"});
  }

  changeStatus(status, dettStatus, motivazioneCasella) {
    this.loading = true;
    let dich = this.dichiarazione;
    dich.dettagli.forEach((dett) => {
      dett.stato = {codice: dettStatus};
      dett.motivazione_casella = motivazioneCasella
    });

    dich.stato.codice = status;


    let codiceFiscaleUtenteLoggato = this.authService.getToken();


    this.gestioneDichiarazioniService.cittadiniCfDichiarazioniUuidDichiarazionePut(
      codiceFiscaleUtenteLoggato,
      '1',
      'SANSOL',
      codiceFiscaleUtenteLoggato,
      this.dichiarazione.uuid,
      this.dichiarazione,
      'body'
    ).subscribe(() => this.navigateAndLeftPositiveAlert(status),
      () => this.negativeAlert());
  }

  public openRifiutaDialog() {

    let citizen = this.citizens[0].citizen;
    this.modalService.showDialog('RIFIUTA', 'Sei sicuro di voler rifiutare la delega ?',
      true, 'Se rifiuti una delega devi indicare una motivazione')
      .then((confirmed) => {
        this.changeStatus('RIFIUTA', 'RIFIUTA', confirmed.motivatione);
      });
  }

  public openBloccaDialog() {
    let citizen = this.citizens[0].citizen;
    this.modalService.showDialog('BLOCCA', 'Sei sicuro di voler bloccare la delega ?',
      true, 'Se blocchi una delega devi indicare una motivazione')
      .then((confirmed) => {
        this.changeStatus('BLOCCA', 'REVOCATA_BLOCCO', confirmed.motivatione);
      });
  }

  public openConfermaDialog() {
    let citizen = this.citizens[0].citizen;
    this.modalService.showDialog('CONFERMA', 'Sei sicuro di voler confermare la delega ?', false)
      .then((confirmed) => {
        this.changeStatus('ATTIVA', 'VALIDA', "");
      });
  }

  public openRevocaDialog() {
    let citizen = this.citizens[0].citizen;
    this.modalService.showDialog('REVOCA', 'Sei sicuro di voler revocare la dichiarazione e le deleghe allegate ?', false)
      .then((confirmed) => {
        this.changeStatus('REVOCATA', 'REVOCATA', "");
      });
  }

  loadInformativaConsensi() {

    let codiceFiscaleUtenteLoggato = this.authService.getToken();

    this.codificheCitizenService.getInformativaConsensiList(
      codiceFiscaleUtenteLoggato, '1', 'SANSOL', 'body', false
    ).subscribe((res) => {
        this.informativaConsensi = res;
        this.informativaConsensi.forEach(inf => {
          this.pdfInformativa = inf.pdf_informativa;
        });


      }, (error => {



      }))
  }


}
