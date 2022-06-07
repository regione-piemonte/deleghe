/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.delegheboweb.business.delegheboweb.impl;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import it.csi.deleghe.delegheboweb.business.delegheboweb.ComuniApi;
import it.csi.deleghe.delegheboweb.business.delegheboweb.impl.service.CittadinoService;
import it.csi.deleghe.delegheboweb.business.delegheboweb.impl.service.ComuniService;
import it.csi.deleghe.delegheboweb.util.SpringApplicationContextProvider;

public class ComuniApiImpl implements ComuniApi{
																			
	private ComuniService comuniService = (ComuniService) SpringApplicationContextProvider.getApplicationContext().getBean("it.csi.deleghe.delegheboweb.business.delegheboweb.impl.service.ComuniService");
	
	public Response cercaComuniGet(String xRequestID, String xCodiceServizio, String filter, SecurityContext securityContext, HttpServletRequest request) {

	      return comuniService.cercaComuniGet(xRequestID, xCodiceServizio, filter, request);
	}

}
