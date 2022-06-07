/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.delegheboweb.business.delegheboweb.impl;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import it.csi.deleghe.delegheboweb.business.delegheboweb.NazioniApi;
import it.csi.deleghe.delegheboweb.business.delegheboweb.impl.service.ComuniService;
import it.csi.deleghe.delegheboweb.util.SpringApplicationContextProvider;

public class NazioniApiImpl implements  NazioniApi{
	
	private ComuniService comuniService = (ComuniService) SpringApplicationContextProvider.getApplicationContext().getBean("it.csi.deleghe.delegheboweb.business.delegheboweb.impl.service.ComuniService");
	
	public Response cercaNazioniGet(String xRequestID, String xCodiceServizio, String filter, SecurityContext securityContext, HttpServletRequest request) {
	      return comuniService.cercaNazioniGet(xRequestID, xCodiceServizio, filter, request);
	}

}
