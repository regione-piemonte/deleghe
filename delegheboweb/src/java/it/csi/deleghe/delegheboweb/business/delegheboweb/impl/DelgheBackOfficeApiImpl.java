/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.delegheboweb.business.delegheboweb.impl;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import it.csi.deleghe.delegheboweb.business.delegheboweb.DelgheBackOfficeApi;
import it.csi.deleghe.delegheboweb.business.delegheboweb.impl.service.DelegheService;
import it.csi.deleghe.delegheboweb.business.delegheboweb.impl.service.ServiziService;
import it.csi.deleghe.delegheboweb.util.SpringApplicationContextProvider;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyEapServerCodegen", date = "2019-12-21T13:27:09.569Z")
public class DelgheBackOfficeApiImpl implements DelgheBackOfficeApi {

   private ServiziService serviziService = (ServiziService) SpringApplicationContextProvider.getApplicationContext().getBean("it.csi.deleghe.delegheboweb.business.delegheboweb.impl.service.ServiziService");
   private DelegheService delegheSerivce = (DelegheService) SpringApplicationContextProvider.getApplicationContext().getBean("it.csi.deleghe.delegheboweb.business.delegheboweb.impl.service.DelegheService");
 
   public Response servizioGet(String xRequestID, String xCodiceServizio, SecurityContext securityContext, HttpServletRequest request) {
      return serviziService.getServiseList(xRequestID, xCodiceServizio, request);
   }

   public Response testToken(HttpServletRequest request) {
      return Response.ok(serviziService.getCodiceFiscale(request)).build();
   }

	@Override
	public Response getLegameDelegatoDelegante(String xRequestID, String xCodiceServizio, String filter, SecurityContext securityContext,
			HttpServletRequest request) {
		return delegheSerivce.getLegameDelegatoDelegante( xRequestID, xCodiceServizio, filter, securityContext, request);
	}
	
	@Override
	public Response delegheBoGet(String xRequestID, String xCodiceServizio, String filter, SecurityContext securityContext,
			HttpServletRequest request) {
		return delegheSerivce.getDelegheBoList( xRequestID, xCodiceServizio, filter, securityContext, request);
	}

	@Override
	public Response dettaglioDelegaBoGet(String xRequestID, String xCodiceServizio, String filter,
			SecurityContext securityContext, HttpServletRequest request) {
		
		return delegheSerivce.getDettaglioDelegaBo( xRequestID, xCodiceServizio, filter, securityContext, request);
	}
	
	@Override
	public Response getDatavalidazione(String xRequestID, String xCodiceServizio, String filter, SecurityContext securityContext, HttpServletRequest request) {
		return serviziService.getDatavalidazione( xRequestID, xCodiceServizio, filter, securityContext, request);
	}
	
	@Override
	public Response getIdentitaOperatore(String xRequestID, String xCodiceServizio, String filter, SecurityContext securityContext, HttpServletRequest request) {
		return serviziService.getIdentitaOperatore(request);
	}
	
	@Override
	public Response removeFromSession(String xRequestID, String xCodiceServizio, String filter, SecurityContext securityContext, HttpServletRequest request) {
	      return serviziService.removeFromSession(request);
	   }
	
	@Override
	public Response ricercaFileDocumentoPerId(String xRequestID, String xCodiceServizio, Integer fileId, SecurityContext securityContext, HttpServletRequest request) {
		return serviziService.ricercaFileDocumentoPerId( xRequestID, xCodiceServizio, fileId, securityContext, request);
	}
	
	@Override
	public Response ricercaOperatoreValidoConAsl(String xRequestID, String xCodiceServizio, String codiceFiscale, SecurityContext securityContext, HttpServletRequest request) {
		return serviziService.ricercaOperatoreValidoConAsl( xRequestID, xCodiceServizio, codiceFiscale, securityContext, request);
	}
}
