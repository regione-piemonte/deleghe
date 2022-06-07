/**
 * Deleghe
 * Api risorse per gestire le deleghe e le dichiarazioni (deleghe minori)
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
import { Codifica } from './codifica';


/**
 * possibili valori del codice {DA_COMPLETARE, ATTIVA, REVOCATA, SCADUTA}
 */
export interface DichiarazioneStato  {

  codice?: string;
  descrizione?: string;

}
