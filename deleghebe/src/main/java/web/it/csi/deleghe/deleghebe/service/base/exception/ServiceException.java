/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service.base.exception;

import java.util.ArrayList;
import java.util.List;

import it.csi.deleghe.deleghebe.service.base.BaseService;
import it.csi.deleghe.deleghebe.ws.model.Errore;
/**
 * Eccezione nell'esecuzione dei {@link BaseService}.
 * 
 * Sollevando questa eccezione all'interno delle implementazioni di {@link BaseService} 
 * sarà possibile specificare la lista di errori da restituire nel servizio SOAP.
 * 
 * @author xyz xyz
 *
 */
public class ServiceException extends RuntimeException {

	
	private static final long serialVersionUID = 5560607219525404660L;
	
	private List<Errore> errori;
	
	public ServiceException() {
		super();
	}

	public ServiceException(List<Errore> errori) {
		super();
		this.errori = errori;
	}
	
	public ServiceException(Errore errore) {
		super(errore.getCodice() + " "+ errore.getDescrizione());
		this.getErrori().add(errore);
	}
	
	public ServiceException(String codice, String descrizione) {
		this(new Errore(codice, descrizione));
	}
	
	public ServiceException(String codice, String descrizione, Throwable cause) {
		this(new Errore(codice, descrizione), cause);
	}
	
	public ServiceException(List<Errore> errori, Throwable cause) {
		super(cause);
		this.errori = errori;
	}
	
	public ServiceException(Errore errore, Throwable cause) {
		super(errore.getCodice() + " "+ errore.getDescrizione(), cause);
		this.getErrori().add(errore);
	}
	

	public List<Errore> getErrori() {
		if (errori == null) {
			errori = new ArrayList<Errore>();
		}
		return errori;
	}

	public void setErrori(List<Errore> errori) {
		this.errori = errori;
	}

	
	
	
	
	
	

}
