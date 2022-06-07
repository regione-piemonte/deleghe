import {Component, Input, OnInit} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {Router} from '@angular/router';
import {Message, MessageService} from 'primeng/api';
import {ShowMessagesService} from '../../../shared/service/show-messages.service';
/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
@Component({
  selector: 'dialog',
  templateUrl: './dialog.component.html',
  styleUrls: ['./dialog.component.css']
})
export class DialogComponent implements OnInit {

  @Input() title: string;
  @Input() message: string;
  @Input() additionalMessage: string;
  @Input() btnOkText: string;
  @Input() btnCancelText: string;
  @Input() isMotivationRequired: boolean;

  msgs: Message[] = [];
  motivatione: any = "";
  motivaText: any = "";

  constructor(private activeModal: NgbActiveModal,
              private router: Router,
              private messageService: MessageService,
              private showMessagesService: ShowMessagesService) {
  }

  ngOnInit() {
  }

  public decline() {
    this.activeModal.close(false);
  }

  public accept() {
    this.msgs = [];

    if (true) {
      this.msgs.push({severity: 'success', summary: '', detail: 'Operazione avvenuta con successo'});
    }


    let reuslt: any = {};
     if(this.motivatione === 'Altra descrizione') {
       reuslt.motivatione = this.motivatione;
       reuslt.motivaText = this.motivaText;

     } else {
       reuslt.motivatione = this.motivatione;
     }

    this.showMessagesService.setMsgs(this.msgs);
    this.activeModal.close(reuslt);

  }

  public dismiss() {
    this.activeModal.dismiss();
  }

}
