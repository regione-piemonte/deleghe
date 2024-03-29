/**
 * Servizi trasversali
 * Api risorse per gestire i servizi trasversali
 *
 * OpenAPI spec version: 1.0.0
 *
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */
/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

export interface Nazione {
  /**
   * codice istat della nazione
   */
  codiceIstat?: string;
  /**
   * nome della nazione
   */
  desc?: string;
  /**
   * descrizione territorio
   */
  territorio?: string;
  /**
   * data inizio validita
   */
  dataInizioValidita?: string;
  /**
   * data fine validita
   */
  dataFineValidita?: string;
  /**
   * descrizione del continente
   */
  continente?: string;
}
