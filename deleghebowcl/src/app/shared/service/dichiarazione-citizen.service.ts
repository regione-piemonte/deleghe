import { Injectable } from '@angular/core';
import {Cittadino, CittadinoOrdered} from '../../model/cittadino';
/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
@Injectable({
  providedIn: 'root'
})
export class DichiarazioneCitizenService {

  constructor() { }

  getCitizens(dichiarazione) {
    let citizens: CittadinoOrdered[] = [];

    citizens.push({order: 0, citizen: dichiarazione.cittadino, nPratica: dichiarazione.npratica}); // 0 - main, 1 - adult, 2 - child

    dichiarazione.dettagli.forEach(dett => {
      let genitore_tutore_curatore = dett.genitore_tutore_curatore;
      let figlio_tutelato_curato = dett.figlio_tutelato_curato;
      if (!this.contains(genitore_tutore_curatore, citizens)) {
        citizens.push({order: 1, citizen: genitore_tutore_curatore, nPratica: dett.dicdet_id});
      }
      if (!this.contains(figlio_tutelato_curato, citizens)) {
        citizens.push({order: 2, citizen: figlio_tutelato_curato, nPratica: dett.dicdet_id});
      }
    })

    citizens = citizens.sort((a, b) => {
      return a.order > b.order? 1: -1
    });

    return citizens;
  }

  contains(citizen, citizens) {
    let result = false;
    citizens.forEach((citizenInArray) => {
      if (citizenInArray.citizen.codice_fiscale === citizen.codice_fiscale) {
        result = true;
      }
    });
    return result;
  }
}
