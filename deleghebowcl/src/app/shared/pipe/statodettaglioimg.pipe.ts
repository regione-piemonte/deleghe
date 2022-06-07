import { Pipe, PipeTransform } from '@angular/core';

/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
@Pipe({
  name: 'statodettaglioimg'
})
export class StatodettaglioimgPipe implements PipeTransform {
  transform(value: string) {
    let  img_src = '';
    switch (value) {
      case 'VALIDA':
        img_src = './assets/img/user/ok.png';
        break;
      case 'SCADUTA':
        img_src = './assets/img/user/ko.png';
        break;
      case 'DA_APPROVARE':
        img_src = './assets/img/user/kk.png'; // TODO: clessidra
        break;
      case 'REVOCATA':
        img_src = './assets/img/user/ko.png';
        break;
      case 'REVOCATA_BLOCCO':
        img_src = './assets/img/user/ko.png';
        break;
    }
    return img_src;
  }
}

