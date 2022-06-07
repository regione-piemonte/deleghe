import { Pipe, PipeTransform } from '@angular/core';

/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
@Pipe({
  name: 'statodichimg'
})
export class StatodichimgPipe implements PipeTransform {
  transform(value: string) {
    let  img_src = '';
    switch (value) {
      case 'ATTIVA':
        img_src = './assets/img/user/ok.png';
        break;
      case 'SCADUTA':
        img_src = './assets/img/user/ko.png';
        break;
      case 'REVOCATA':
        img_src = './assets/img/user/ko.png';
        break;
      case 'RIFIUTATA':
        img_src = './assets/img/user/ko.png';
        break;
      case 'IN_SCADENZA':
        img_src = './assets/img/user/kk.png';
        break;
    }
    return img_src;
  }
}

