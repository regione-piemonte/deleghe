/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.delegheboweb.business.delegheboweb.impl;

import it.csi.deleghe.delegheboweb.business.delegheboweb.CittadiniApi;
import it.csi.deleghe.delegheboweb.business.delegheboweb.impl.service.CittadinoService;
import it.csi.deleghe.delegheboweb.business.delegheboweb.impl.service.DelegheService;
import it.csi.deleghe.delegheboweb.business.delegheboweb.impl.service.DichiarazioniService;
import it.csi.deleghe.delegheboweb.dto.delegheboweb.CreateDichiarazione;
import it.csi.deleghe.delegheboweb.dto.delegheboweb.Delega;
import it.csi.deleghe.delegheboweb.dto.delegheboweb.Dichiarazione;
import it.csi.deleghe.delegheboweb.util.SpringApplicationContextProvider;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyEapServerCodegen", date = "2019-12-21T13:27:09.569Z")
public class CittadiniApiServiceImpl implements CittadiniApi {

   private DichiarazioniService dichiarazioniSerivce = (DichiarazioniService) SpringApplicationContextProvider.getApplicationContext().getBean("it.csi.deleghe.delegheboweb.business.delegheboweb.impl.service.DichiarazioniService");
   private DelegheService delegheSerivce = (DelegheService) SpringApplicationContextProvider.getApplicationContext().getBean("it.csi.deleghe.delegheboweb.business.delegheboweb.impl.service.DelegheService");
   private CittadinoService cittadinoService = (CittadinoService) SpringApplicationContextProvider.getApplicationContext().getBean("it.csi.deleghe.delegheboweb.business.delegheboweb.impl.service.CittadinoService");

   public Response findCittadino(String xRequestID, String xCodiceServizio, String filter, SecurityContext securityContext, HttpServletRequest request) {
      return cittadinoService.getCittadino(xRequestID, xCodiceServizio, filter, request);
   }

   public Response cittadiniCfDichiarazioniGet(String xRequestID, String xCodiceServizio, String cf, String filter, SecurityContext securityContext, HttpServletRequest request) {
      return dichiarazioniSerivce.getDichiarazioniList(xRequestID, xCodiceServizio, cf, filter, securityContext, request);
   }

   public Response delegheGet(String xRequestID, String xCodiceServizio, String filter, SecurityContext securityContext, HttpServletRequest request) {
      return delegheSerivce.getDelegheList( xRequestID, xCodiceServizio, filter, securityContext, request);
   }

   public Response cittadiniCfDichiarazioniGet(String xRequestID, String xCodiceServizio, String filter, SecurityContext securityContext, HttpServletRequest request) {
      return dichiarazioniSerivce.getDichiarazioniList(xRequestID, xCodiceServizio, "", filter, securityContext, request);
   }

   public Response delegaPut(String xRequestID, String xCodiceServizio, String cf, it.csi.deleghe.delegheboweb.dto.delegheboweb.Delega delega, SecurityContext securityContext, HttpServletRequest request) {
      return delegheSerivce.updateDelega(xRequestID, xCodiceServizio, cf, delega, securityContext, request);
   }

   @Override
   public Response delegaPost(String xRequestID, String xCodiceServizio, Delega delega, SecurityContext securityContext, HttpServletRequest request) {
      return delegheSerivce.create(xRequestID, xCodiceServizio, delega, request);
   }

   @Override
   public Response delegaDichiarazione(String xRequestID, String xCodiceServizio, CreateDichiarazione createDichiarazione, SecurityContext securityContext, HttpServletRequest request) {
      return dichiarazioniSerivce.create(xRequestID, xCodiceServizio, createDichiarazione, request);
   }
   
   public Response getIdAura(String xRequestID, String xCodiceServizio, String filter, SecurityContext securityContext, HttpServletRequest request) {
	      return dichiarazioniSerivce.getIdAura(xRequestID, xCodiceServizio, filter, request);
   }

   public Response cittadiniCfDichiarazioniUuidDichiarazionePut(String xRequestID, String xCodiceServizio, String cf, String uuidDichiarazione, Dichiarazione dichiarazione, SecurityContext securityContext, HttpServletRequest request) {
      return dichiarazioniSerivce.updateDichiarazioni(xRequestID, xCodiceServizio, cf, uuidDichiarazione, dichiarazione, securityContext, request);
   }
   
   public Response getTokenInformation2(String xRequestID, String xCodiceServizio, String filter, SecurityContext securityContext, HttpServletRequest request) {
	      return cittadinoService.getTokenInformation2(xRequestID, xCodiceServizio, filter, securityContext, request);
   }
}
