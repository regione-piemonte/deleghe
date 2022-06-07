import { Pipe, PipeTransform } from '@angular/core';
import {StatiDich} from '../../model/statidich';
/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
@Pipe({
  name: 'statodich'
})
export class StatodichPipe implements PipeTransform {
  transform(value: string) {
    let  src = '';
    switch (value) {
      case 'ATTIVA':
        src = StatiDich.ATTIVA;
        break;
      case 'SCADUTA':
        src = StatiDich.SCADUTA;
        break;
      case 'REVOCATA':
        src = StatiDich.REVOCATA;
        break;
      case 'RIFIUTATA':
        src = StatiDich.RIFIUTATA;
        break;
      case 'IN_SCADENZA':
        src = StatiDich.IN_SCADENZA;
        break;
    }
    return src;
  }
}

