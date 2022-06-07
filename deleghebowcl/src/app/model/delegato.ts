/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
export interface IndirizzoInterface {
  via: string;
  citta: string;
}

export class Indirizzo implements IndirizzoInterface {
  via: string;
  citta: string;

  constructor(via: string, indirizzo: string) {
    this.via = via;
    this.citta = indirizzo;
  }
}

  export interface DelegatoInterface {
  id: number;
  nome: string;
  cognome: string;
  indirizzo: Indirizzo;
}

export class Delegato implements DelegatoInterface {
  id: number;
  nome: string;
  cognome: string;
  indirizzo: Indirizzo;

  constructor(id: number, nome: string, cognome: string, indirizzo: Indirizzo) {
    this.id = id;
    this.nome = nome;
    this.cognome = cognome;
    this.indirizzo = indirizzo;
  }

}
