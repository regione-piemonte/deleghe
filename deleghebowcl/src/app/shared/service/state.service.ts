import { Injectable } from '@angular/core';
/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
@Injectable()
export class StateService {
  get data() {
    return this._data;
  }

  set data(value) {
    this._data = value;
  }

  constructor() {  }

  private _data: any = {};

}
