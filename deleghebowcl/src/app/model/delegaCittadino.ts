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
import { DelegaServizio } from './delegaServizio';


export interface DelegaCittadino {
    uuid?: string;
    /**
     * codice fiscale
     */
    codiceFiscaleDelega?: string;
    /**
     * nome
     */
    nomeDelega?: string;
    /**
     * cognome
     */
    cognomeDelega?: string;
    /**
     * data di nascita questo campo non è obbligatorio
     */
    dataNascitaDelega?: string;
    /**
     * luogo di nascita questo campo non è obbligatorio
     */
    luogoNascitaDelega?: string;
    /**
     * sesso questo campo non è obbligatorio
     */
    sessoDelega?: string;
    /**
     * gestisce cancellazione o attivazione valori possibili attivi, eliminato
     */
    statoAnagrafica?: string;
    deleghe?: Array<DelegaServizio>;
}
