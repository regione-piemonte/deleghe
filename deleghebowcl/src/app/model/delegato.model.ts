/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
export interface IndirizzoInterface {
  via: string;
  citta: string;
}

  export interface DelegatoInterface {
  id: number;
  name: string;
  cognome: string;
  indirizzo: IndirizzoInterface;
}
