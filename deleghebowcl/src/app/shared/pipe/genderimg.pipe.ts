import { Pipe, PipeTransform } from '@angular/core';
/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
@Pipe({
  name: 'genderimg'
})
export class GenderImgPipe implements PipeTransform {
  transform(value: string) {
    let img_src = '';
    switch (value) {
      case 'F':
        img_src = './assets/img/user/iconadonna.png';
        break;
      case 'M':
        img_src = './assets/img/user/iconauomo.png';
        break;
    }
    return img_src;
  }
}

