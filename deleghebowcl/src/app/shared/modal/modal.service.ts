import { Injectable } from '@angular/core';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {DialogComponent} from './dialog/dialog.component';
/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
@Injectable({
  providedIn: 'root'
})
export class ModalService {

  constructor(private modalService: NgbModal) {
  }

  public showDialog(
    title: string,
    message: string,
    isMotivationRequired: boolean,
    additionalMessage: string = '',
    btnOkText: string = 'SI',
    btnCancelText: string = 'NO',
    dialogSize: 'sm' | 'lg' = 'sm'): Promise<any> {
    const modalRef = this.modalService.open(DialogComponent, {size: dialogSize});
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.additionalMessage = additionalMessage;
    modalRef.componentInstance.btnOkText = btnOkText;
    modalRef.componentInstance.btnCancelText = btnCancelText;
    modalRef.componentInstance.isMotivationRequired = isMotivationRequired;

    return modalRef.result;
  }
}
