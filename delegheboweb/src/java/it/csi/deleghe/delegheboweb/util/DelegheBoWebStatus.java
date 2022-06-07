/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.delegheboweb.util;

import javax.ws.rs.core.MediaType;

import it.csi.deleghe.delegheboweb.exception.RESTException;

public enum DelegheBoWebStatus {

	UTENTE_NON_AUTORIZZATO(401, "Utente non Autorizzato"), 
	CODICE_FISCALE_NON_VALIDO(421, "Codice Fiscale %s non valido"),
	MULTIPLE_RESULTS(421, "Trovato più di un risultato"),
	REFERTO_NON_TROVATO(404, "Referto non trovato"),
	CITTADINO_NON_TROVATO(404,"Nessun cittadino trovato"),
	LISTA_DELEGANTI_NON_TROVATA(404,"Nessun delegante trovato"),
	AGGIORNAMENTO_CITTADINO(404,"Aggiornamento cittadino. Operazione non avvenuta con successo"),
	INSERIMENTO_CITTADINO(404,"Inserimento cittadino. Operazione non avvenuta con successo"),
	SELEZIONA_CITTADINO_NON_TROVATO(405,"Nessun cittadino trovato nella base dati AURA"),
	LISTA_DELEGATI_NON_TROVATA(404,"Nessun delegato trovato");

	private int statusCode;
	private String descTemplate;

	DelegheBoWebStatus(int statusCode, String desc) {
		this.statusCode = statusCode;
		this.descTemplate = desc;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public RESTException getRestException(Object... args) {
		String desc = String.format(descTemplate, args);
		return new RESTException(this.statusCode, MediaType.TEXT_PLAIN_TYPE, desc, desc);
	}

	public RESTException getRestException(MediaType mediaType, Object entity, String message) {
		return new RESTException(this.statusCode, mediaType, entity, message);
	}
}
