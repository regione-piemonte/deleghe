import {Pipe, PipeTransform} from '@angular/core';
import {Gender} from '../../model/gender';
/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
@Pipe({
  name: 'gender'
})
export class GenderPipe implements PipeTransform {
  transform(value: string) {
    let descr = '';
    switch (value) {
          case 'M':
            descr = Gender.M;
            break;
          case 'F':
            descr = Gender.F;
            break;
        }
    return descr;
  }
}

