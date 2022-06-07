/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.delegheboweb.business.delegheboweb.impl.service;

import java.util.ArrayList;

import java.util.List;
import java.util.Base64;


import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import it.csi.deleghe.deleghebe.ws.DelegheBackOfficeService;
import it.csi.deleghe.deleghebe.ws.DelegheCittadiniService;
import it.csi.deleghe.deleghebe.ws.model.Delega;
import it.csi.deleghe.deleghebe.ws.model.DocumentoFile;
import it.csi.deleghe.deleghebe.ws.model.Operatore;
import it.csi.deleghe.deleghebe.ws.model.Richiedente;
import it.csi.deleghe.deleghebe.ws.model.Servizio;
import it.csi.deleghe.deleghebe.ws.model.StatoDichiarazione;
import it.csi.deleghe.deleghebe.ws.msg.RicercaServizi;
import it.csi.deleghe.deleghebe.ws.msg.LeggiServizi;
import it.csi.deleghe.deleghebe.ws.msg.LeggiServiziResponse;
import it.csi.deleghe.deleghebe.ws.msg.RicercaCfOperatore;
import it.csi.deleghe.deleghebe.ws.msg.RicercaCfOperatoreResponse;
import it.csi.deleghe.deleghebe.ws.msg.RicercaDeleghe;
import it.csi.deleghe.deleghebe.ws.msg.RicercaFile;
import it.csi.deleghe.deleghebe.ws.msg.RicercaFileResponse;
import it.csi.deleghe.deleghebe.ws.msg.RicercaServiziResponse;
import it.csi.deleghe.delegheboweb.business.mapper.ServizioMapper;
import it.csi.deleghe.delegheboweb.util.Constants;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ServiziService extends AbstractDelgheBEService {
	
	final static Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

   @Autowired
   private DelegheBackOfficeService delegheBackOfficeService; 
   
   @Autowired
   DelegheCittadiniService delegheCittadiniService;   


   public Response getServiseList(String xRequestID, String xCodiceServizio, HttpServletRequest request) {

      RicercaServizi ricercaServizi = new RicercaServizi();
      ricercaServizi.setRichiedente(new Richiedente(xRequestID, xCodiceServizio, getCodiceFiscale(request)));

      RicercaServiziResponse ricercaServiziResponse = delegheBackOfficeService.ricercaServiziService(ricercaServizi);

      final List<Servizio> serviziList = ricercaServiziResponse.getServizi();
      if (serviziList == null || serviziList.isEmpty()) {
         return getError(ricercaServiziResponse);
      }

      return Response.ok(new ServizioMapper().fromList(serviziList)).build();
   }
   
   public Response getDatavalidazione(String xRequestID, String xCodiceServizio, String filter, SecurityContext securityContext, HttpServletRequest request){
	   	  LeggiServizi leggiServizi = new LeggiServizi();
	   	  Richiedente richiedente = new Richiedente(xRequestID, xCodiceServizio, getCodiceFiscale(request));
	   	  leggiServizi.setRichiedente(richiedente);
	   	  
	   	  LOG.info("ServiziService getDatavalidazione filter: "+filter);
	   	  
	   	  leggiServizi.setServizio(new Servizio());
	   	  leggiServizi.setSerCod(filter);
	   	  
	   	  LeggiServiziResponse leggiServiziResponse = delegheBackOfficeService.leggiServiziService(leggiServizi);
	      List<Servizio> serviziListBe = leggiServiziResponse.getElencoServizio();
	      
	      if (serviziListBe == null || serviziListBe.isEmpty()) {
	          return getError(leggiServiziResponse);
	       } 
	      
	      List<it.csi.deleghe.delegheboweb.dto.delegheboweb.Servizio> result = new ArrayList<>();

	      for (Servizio servizioBe : serviziListBe) {
	    	  it.csi.deleghe.delegheboweb.dto.delegheboweb.Servizio servizio = new it.csi.deleghe.delegheboweb.dto.delegheboweb.Servizio();
			
	    	  servizio.setCodice(servizioBe.getCodice());
	    	  servizio.setSerId(servizioBe.getSerId());
	    	  servizio.setValiditaServizio(servizioBe.getValiditaServizio());
	    	  LOG.info("getDatavalidazione serId: "+servizio.getSerId());
	    	  LOG.info("getDatavalidazione validitaServizio: "+servizio.getValiditaServizio());
	    	  LOG.info("getDatavalidazione codice: "+servizio.getCodice());
	    	  
	    	  
	    	  result.add(servizio);
	      }
	      
	      return Response.ok(result).build();

	   }
   
   
   public Response ricercaFileDocumentoPerId(String xRequestID, String xCodiceServizio, Integer fileId, SecurityContext securityContext, HttpServletRequest request){
	   	  RicercaFile ricercaFile = new RicercaFile();
	   	  Richiedente richiedente = new Richiedente(xRequestID, xCodiceServizio, getCodiceFiscale(request));
	   	  ricercaFile.setRichiedente(richiedente);
	   	  ricercaFile.setFileId(fileId);
	      RicercaFileResponse ricercaFileResponse = delegheCittadiniService.ricercaFileDocumentoPerId(ricercaFile);
	      DocumentoFile documentoFileBe = ricercaFileResponse.getDocumentoFile();
	      
	      if (documentoFileBe == null) {
	          return getError(ricercaFileResponse);
	      } 

	    	  it.csi.deleghe.delegheboweb.dto.delegheboweb.DocumentoFile servizio = new it.csi.deleghe.delegheboweb.dto.delegheboweb.DocumentoFile();
			  servizio.setBase64File(documentoFileBe.getBase64File());
			  servizio.setNome(documentoFileBe.getNome());
			  servizio.setDimensioneInBytes(documentoFileBe.getDimensioneInBytes());
			  servizio.setFileId(documentoFileBe.getFileId());
	    	  

	    	  byte[] decoded = Base64.getDecoder().decode(servizio.getBase64File());
	    	  servizio.setBytes(decoded);    
	      
	      return Response.ok(servizio).build();

	   }
   
   public Response ricercaOperatoreValidoConAsl(String xRequestID, String xCodiceServizio, String codiceFiscale, SecurityContext securityContext, HttpServletRequest request){

	      RicercaCfOperatore ricercaCfOperatore = new RicercaCfOperatore();


	      ricercaCfOperatore.setCf(codiceFiscale);
	      
	      Richiedente richiedente = new Richiedente(xRequestID, xCodiceServizio, getCodiceFiscale(request));

	      ricercaCfOperatore.setRichiedente(richiedente);

	      RicercaCfOperatoreResponse ricercaCfOperatoreResponse = delegheCittadiniService.ricercaOperatoreValidoService(ricercaCfOperatore);
	      String aslOperatore = ricercaCfOperatoreResponse.getAsl();
	      boolean esitoRicerca = ricercaCfOperatoreResponse.isExists();

	      
	      it.csi.deleghe.delegheboweb.dto.delegheboweb.Operatore operatoreResponse = new it.csi.deleghe.delegheboweb.dto.delegheboweb.Operatore();	      
    	  
	      if(aslOperatore!= null) {
	    	  operatoreResponse.setAslOperatore(aslOperatore);
	    	  operatoreResponse.setEsitoRicerca(esitoRicerca);   
	      }else{
	    	  operatoreResponse.setEsitoRicerca(esitoRicerca); 
	      }
	      
	      
	      return Response.ok(operatoreResponse).build();

	   }
}
