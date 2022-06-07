import { Pipe, PipeTransform } from '@angular/core';
import {Servizi} from '../../model/servizi';
/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
@Pipe({
  name: 'servizi'
})
export class ServiziPipe implements PipeTransform {
  transform(value: string) {
    let  descr = '';
    switch (value) {
      case 'PTW':
        descr = Servizi.PTW;
        break;
      case 'RE':
        descr = Servizi.RE;
        break;
      case 'FP':
        descr = Servizi.FP;
        break;
      case 'ER':
        descr = Servizi.ER;
        break;
      case 'EP':
        descr = Servizi.EP;
        break;
      case 'ROL':
        descr = Servizi.ROL;
        break;
    }
    return descr;
  }
}
