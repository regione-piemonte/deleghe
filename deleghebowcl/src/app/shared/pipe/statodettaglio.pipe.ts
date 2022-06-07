import { Pipe, PipeTransform } from '@angular/core';
import {StatiDettaglioDich} from '../../model/statidettagliodich';
/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
@Pipe({
  name: 'statodettaglio'
})
export class StatodettaglioPipe implements PipeTransform {
  transform(value: string) {
    let  src = '';
    switch (value) {
      case 'VALIDA':
        src = StatiDettaglioDich.VALIDA;
        break;
      case 'SCADUTA':
        src = StatiDettaglioDich.SCADUTA;
        break;
      case 'DA_APPROVARE':
        src = StatiDettaglioDich.DA_APPROVARE;
        break;
      case 'REVOCATA':
        src = StatiDettaglioDich.REVOCATA;
        break;
      case 'REVOCATA_BLOCCO':
        src = StatiDettaglioDich.REVOCATA_BLOCCO;
        break;
    }
    return src;
  }
}

