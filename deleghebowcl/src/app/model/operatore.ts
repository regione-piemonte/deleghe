/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
export interface OperatoreInterface {
  nome: string;
  cognome: string;
  codfiscale: string;
}

export class Operatore implements OperatoreInterface {
  nome: string;
  cognome: string;
  codfiscale: string;
  constructor(nome: string, cognome: string, codfiscale: string) {
    this.nome = nome;
    this.cognome = cognome;
    this.codfiscale = codfiscale;
  }
}
