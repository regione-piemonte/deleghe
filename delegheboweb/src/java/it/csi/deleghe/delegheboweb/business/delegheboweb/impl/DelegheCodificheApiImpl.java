/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.delegheboweb.business.delegheboweb.impl;

import it.csi.deleghe.delegheboweb.business.delegheboweb.*;
import it.csi.deleghe.delegheboweb.business.delegheboweb.impl.service.CodificheService;
import it.csi.deleghe.delegheboweb.business.delegheboweb.impl.service.ServiziService;
import it.csi.deleghe.delegheboweb.util.SpringApplicationContextProvider;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;


public class DelegheCodificheApiImpl implements DelegheCodificheApi {

   
   	
   	
   private CodificheService codificheService = (CodificheService) SpringApplicationContextProvider.
		   getApplicationContext().getBean("it.csi.deleghe.delegheboweb.business.delegheboweb.impl.service.CodificheService");
   											
   

	@Override
	public Response statiDichiarazioneGet(String xRequestID, String xCodiceServizio, SecurityContext securityContext,
			HttpServletRequest request) {
		
		
		
		return codificheService.getStatiDichiarazioneList(xRequestID, xCodiceServizio, securityContext, request);
	}
	
	@Override
	public Response tipiDichiarazioneGet(String xRequestID, String xCodiceServizio, SecurityContext securityContext,
			HttpServletRequest request) {
		return codificheService.getTipiDichiarazioneList(xRequestID, xCodiceServizio, securityContext, request);
	}
 
	@Override
	public Response statiDelegaGet(String xRequestID, String xCodiceServizio, SecurityContext securityContext,
			HttpServletRequest request) {
		return codificheService.getStatiDelegaList(xRequestID, xCodiceServizio, securityContext, request);
	}
	
	@Override
	public Response tipiDelegaGet(String xRequestID, String xCodiceServizio, SecurityContext securityContext,
			HttpServletRequest request) {
		return codificheService.getTipiDelegaList(xRequestID, xCodiceServizio, securityContext, request);
	}
	
	@Override
	public Response descrizioneDocumentoGet(String xRequestID, String xCodiceServizio, SecurityContext securityContext,
			HttpServletRequest request) {
		return codificheService.getDescrizioneDocumentoList(xRequestID, xCodiceServizio, securityContext, request);
	}
	
	@Override
	public Response getInformativaConsensiList(String xRequestID, String xCodiceServizio, SecurityContext securityContext,
			HttpServletRequest request) {
		return codificheService.getInformativaConsensiList(xRequestID, xCodiceServizio, securityContext, request);
	}
 
   
}
