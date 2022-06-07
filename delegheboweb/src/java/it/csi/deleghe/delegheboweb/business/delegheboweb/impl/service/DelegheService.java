/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.delegheboweb.business.delegheboweb.impl.service;

import it.csi.deleghe.deleghebe.ws.DelegheBackOfficeService;

import it.csi.deleghe.deleghebe.ws.DelegheCittadiniService;
import it.csi.deleghe.deleghebe.ws.model.*;
import it.csi.deleghe.deleghebe.ws.msg.*;
import it.csi.deleghe.delegheboweb.business.mapper.DelegatoMapper;
import it.csi.deleghe.delegheboweb.business.mapper.DelegheMapper;
import it.csi.deleghe.delegheboweb.dto.deleghebowebLEGACY.DelegheFilter;
import it.csi.deleghe.delegheboweb.dto.deleghebowebLEGACY.DichiarazioneFilter;
import it.csi.deleghe.delegheboweb.dto.deleghebowebLEGACY.DichiarazioneStato;
import it.csi.deleghe.delegheboweb.dto.deleghebowebLEGACY.DichiarazioneTipo;
import it.csi.deleghe.delegheboweb.util.CompilatoreMap;
import it.csi.deleghe.delegheboweb.util.SpringApplicationContextProvider;
import it.csi.deleghe.delegheboweb.util.filter.FilterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.*;
import java.util.stream.Collectors;

import static it.csi.deleghe.delegheboweb.util.CompilatoreMap.processedCF;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class DelegheService extends AbstractDelgheBEService {

   @Autowired
   private DelegheCittadiniService delegheCittadiniService;
   
   @Autowired
   private DelegheBackOfficeService delegheBackOfficeService;

   private CittadinoService cittadinoService = (CittadinoService) SpringApplicationContextProvider.getApplicationContext().getBean("it.csi.deleghe.delegheboweb.business.delegheboweb.impl.service.CittadinoService");

   public Response getDelegheList(String xRequestID, String xCodiceServizio, String filter, SecurityContext securityContext, HttpServletRequest request) {
      RicercaDeleghe ricercaDeleghe = new RicercaDeleghe();
      Richiedente richiedente = new Richiedente(xRequestID, xCodiceServizio, getCodiceFiscale(request));
      ricercaDeleghe.setRichiedente(richiedente);

      ricercaDeleghe.setDelega(new Delega());
      DelegheFilter f;
      try {
         f = FilterUtil.init(DelegheFilter.class, filter);
         populateFilters(ricercaDeleghe, f);
      } catch (IllegalArgumentException iae) {
         log.error("getDichiarazioniList", iae.getMessage(), iae);
         return Response.status(Response.Status.BAD_REQUEST).build();
      }

      RicercaDelegheResponse ricercaDelegheResponse = this.delegheCittadiniService.ricercaDelegheService(ricercaDeleghe);
      List<Delega> delegaList = ricercaDelegheResponse.getDeleghe();

      if (delegaList == null || delegaList.isEmpty()) {
         return getError(ricercaDelegheResponse);
      } else {
         delegaList = delegaList.stream().filter(delega -> {
            boolean result = false;
            final Cittadino delegato = delega.getDelegato();
            if (delegato != null) {
               final String codiceFiscale = delegato.getCodiceFiscale();
               if (codiceFiscale != null && (codiceFiscale.equals(f.getDelegatoCodiceFiscale()) || codiceFiscale.equals(f.getDeleganteCodiceFiscale()))) {
                  result = true;
               }
            }

            final Cittadino delegante = delega.getDelegante();
            if (delegante != null) {
               final String codiceFiscale = delegante.getCodiceFiscale();
               if (codiceFiscale != null && codiceFiscale.equals(f.getDeleganteCodiceFiscale()) || codiceFiscale.equals(f.getDelegatoCodiceFiscale())) {
                  result = true;
               }
            }

            if (f.getDelegatoCodiceFiscale() == null && f.getDeleganteCodiceFiscale() == null) {
               result = true;
            }

            return result;
         }).collect(Collectors.toList());
      }

      List<it.csi.deleghe.delegheboweb.dto.delegheboweb.Delega> delegas = new DelegheMapper().fromList(delegaList);


      for (it.csi.deleghe.delegheboweb.dto.delegheboweb.Delega delega : delegas) {
         String compilatoreCF = delega.getCompilatoreCF();
         String compilatore;
         if (!processedCF.containsKey(compilatoreCF)) {
            processedCF.put(compilatoreCF, cittadinoService.getCompilatoreInformation(compilatoreCF).getCompilatore());
         }

         compilatore = processedCF.get(compilatoreCF);
         delega.setFullCompilatoreCF(compilatore);
      }

      return Response.ok(delegas).build();
   }

   public Response updateDelega(String xRequestID, String xCodiceServizio, String cf, it.csi.deleghe.delegheboweb.dto.delegheboweb.Delega delega, SecurityContext securityContext, HttpServletRequest request) {
      AggiornaDelega aggiornaDelega = new AggiornaDelega();
      

      Richiedente richiedente = new Richiedente(xRequestID, xCodiceServizio, getCodiceFiscale(request));
      aggiornaDelega.setRichiedente(richiedente);

      final DelegheMapper delegheMapper = new DelegheMapper();   
      
      final Delega to = delegheMapper.to(delega);
      aggiornaDelega.setDelega(to);

      final AggiornaDelegaResponse aggiornaDelegaResponse = delegheCittadiniService.aggiornaDelegaService(aggiornaDelega);

      if (!RisultatoCodice.SUCCESSO.equals(aggiornaDelegaResponse.getEsito())) {
         List<Errore> errori = aggiornaDelegaResponse.getErrori();
         return Response.status(Response.Status.SEE_OTHER).entity(errori).build();
      }

      return Response.ok().build();
   }
   
 

   public Response create(String xRequestID, String xCodiceServizio, it.csi.deleghe.delegheboweb.dto.delegheboweb.Delega delega, HttpServletRequest request) {
      InserisciDelega inserisciDelega = new InserisciDelega();
      String codiceFiscale = getCodiceFiscale(request);
      inserisciDelega.setRichiedente(new Richiedente(codiceFiscale, xCodiceServizio, xRequestID));
      final DelegheMapper delegheMapper = new DelegheMapper();
      delega.setCompilatoreCF(codiceFiscale);

      inserisciDelega.setDelega(delegheMapper.to(delega));

      final InserisciDelegaResponse inserisciDelegaResponse = delegheCittadiniService.inserisciDelegaService(inserisciDelega);

      if (!RisultatoCodice.SUCCESSO.equals(inserisciDelegaResponse.getEsito())) {
         List<Errore> errori = inserisciDelegaResponse.getErrori();
         return Response.status(Response.Status.SEE_OTHER).entity(errori).build();
      }

      Delega responseDelega = inserisciDelegaResponse.getDelega();

      return Response.ok(delegheMapper.from(responseDelega)).build();
   }

   private void populateFilters(RicercaDeleghe req, DelegheFilter delegheFilter) {
      final Delega delega = req.getDelega();

      final String uuid = delegheFilter.getUuid();
      if (uuid != null) {
         delega.setUuid(UUID.fromString(uuid));
      }

      String deleganteCodiceFiscale = delegheFilter.getDeleganteCodiceFiscale();
      if (deleganteCodiceFiscale != null) {
         Cittadino delegante = new Cittadino();
         delegante.setCodiceFiscale(deleganteCodiceFiscale);
         delega.setDelegante(delegante);
      }

      String delegatoCodiceFiscale = delegheFilter.getDelegatoCodiceFiscale();
      if (delegatoCodiceFiscale != null) {
         Cittadino delegato = new Cittadino();
         delegato.setCodiceFiscale(delegatoCodiceFiscale);
         delega.setDelegato(delegato);
      }
      
      if(delegheFilter.getStato()!=null && !delegheFilter.getStato().isEmpty()) {
    	  List<String> statos = new ArrayList<String>();
    	  for (DichiarazioneStato stato : delegheFilter.getStato()) {
    		  statos.add(stato.getCodice());
    	  }
    	  req.setStatiDelega(statos);    	  
      }

      final Date dataInserimentoA = delegheFilter.getDataInserimentoA();
      if (dataInserimentoA != null) {
    	 
      }
   }
   
   
   /**
    * 
    * @param req
    * @param delegheFilter
    */
   private void populateFilters(RicercaDelega req, DelegheFilter delegheFilter) {
	      
	      if (delegheFilter.getDeleganteCodiceFiscale() != null) {
	         req.setCodiceFiscaleDelegante(delegheFilter.getDeleganteCodiceFiscale());
	      }

	      if (delegheFilter.getDelegatoCodiceFiscale() != null) {
	         req.setCodiceFiscaleDelegato(delegheFilter.getDelegatoCodiceFiscale());
	      }
	      
	      if(delegheFilter.getStato()!=null && !delegheFilter.getStato().isEmpty()) {
	    	  List<StatoDelega> statos = new ArrayList<StatoDelega>();
	    	  for (DichiarazioneStato statofiltro : delegheFilter.getStato()) {
	    		  StatoDelega stato = new StatoDelega();
	    		  stato.setCodice(statofiltro.getCodice());
	    		  statos.add(stato);
	    	  }
	    	  req.setStatiDelega(statos);  
	      }
	      
	      if(delegheFilter.getTipo()!=null && !delegheFilter.getTipo().isEmpty()) {
	    	  List<TipoDelega> tipos = new ArrayList<TipoDelega>();
	    	  for (DichiarazioneTipo tipofiltro : delegheFilter.getTipo()) {
	    		  TipoDelega tipo = new TipoDelega();
	    		  tipo.setCodice(tipofiltro.getCodice());
	    		  tipos.add(tipo);
	    	  }
	    	  req.setTipiDelega(tipos);
	      }

	      if (delegheFilter.getDataInserimentoA() != null) {
	    	 req.setDataInserimentoA(delegheFilter.getDataInserimentoA());
	      }
	      
	      if (delegheFilter.getDataInserimentoDa() != null) {
	    	 req.setDataInserimentoDa(delegheFilter.getDataInserimentoDa());
	      }
	      
	      if (delegheFilter.getDataValiditaInizio() != null) {
	    	 req.setDataValiditaDa(delegheFilter.getDataValiditaInizio());
	      }
	      
	      if (delegheFilter.getDataValiditaFine() != null) {
	    	 req.setDataValiditaA(delegheFilter.getDataValiditaFine());
	      }
	      
	      if (delegheFilter.getCiIdDelegato() != null) {	    	  
		     req.setCiIdDelegato(delegheFilter.getCiIdDelegato());
		  }
	      
	      if (delegheFilter.getCitIdDelegante() != null) {	    	  
		     req.setCitIdDelegante(delegheFilter.getCitIdDelegante());
		  }
	   }
   
   
   /**
    * 
    * @param xRequestID
    * @param xCodiceServizio
    * @param filter
    * @param securityContext
    * @param request
    * @return
    */
   public Response getDelegheBoList(String xRequestID, String xCodiceServizio, String filter, SecurityContext securityContext, HttpServletRequest request) {
	      RicercaDelega ricercaDelega = new RicercaDelega();
	      Richiedente richiedente = new Richiedente(xRequestID, xCodiceServizio, getCodiceFiscale(request));
	      ricercaDelega.setRichiedente(richiedente);

	      
	      DelegheFilter f;
	      try {
	         f = FilterUtil.init(DelegheFilter.class, filter);
	         populateFilters(ricercaDelega, f);
	         
	      } catch (IllegalArgumentException iae) {
	         log.error("getDelegheBoList", iae.getMessage(), iae);
	         return Response.status(Response.Status.BAD_REQUEST).build();
	      }

	      RicercaDelegaResponse ricercaDelegaResponse = this.delegheBackOfficeService.ricercaDelegaService(ricercaDelega);
	      List<Delega> delegaList = ricercaDelegaResponse.getDeleghe();

	      if (delegaList == null || delegaList.isEmpty()) {
	         return getError(ricercaDelegaResponse);
	      } else {
	         delegaList = delegaList.stream().filter(delega -> {
	            boolean result = false;
	            final Cittadino delegato = delega.getDelegato();
	            if (delegato != null) {
	               final String codiceFiscale = delegato.getCodiceFiscale();
	               if (codiceFiscale != null && (codiceFiscale.equals(f.getDelegatoCodiceFiscale()) || codiceFiscale.equals(f.getDeleganteCodiceFiscale()))) {
	                  result = true;
	               }
	            }

	            final Cittadino delegante = delega.getDelegante();
	            if (delegante != null) {
	               final String codiceFiscale = delegante.getCodiceFiscale();
	               if (codiceFiscale != null && codiceFiscale.equals(f.getDeleganteCodiceFiscale()) || codiceFiscale.equals(f.getDelegatoCodiceFiscale())) {
	                  result = true;
	               }
	            }

	            if (f.getDelegatoCodiceFiscale() == null && f.getDeleganteCodiceFiscale() == null) {
	               result = true;
	            }

	            return result;
	         }).collect(Collectors.toList());
	      }

	      List<it.csi.deleghe.delegheboweb.dto.delegheboweb.Delega> delegas = new DelegheMapper().fromList(delegaList);


	      for (it.csi.deleghe.delegheboweb.dto.delegheboweb.Delega delega : delegas) {
	         String compilatoreCF = delega.getCompilatoreCF();
	         String compilatore;
	         if (!processedCF.containsKey(compilatoreCF)) {
	            processedCF.put(compilatoreCF, cittadinoService.getCompilatoreInformation(compilatoreCF).getCompilatore());
	         }

	         compilatore = processedCF.get(compilatoreCF);
	         delega.setFullCompilatoreCF(compilatore);
	      }

	      return Response.ok(delegas).build();
   	}
   
   /**
    * 
    * @param xRequestID
    * @param xCodiceServizio
    * @param filter
    * @param securityContext
    * @param request
    * @return
    */
   public Response getLegameDelegatoDelegante(String xRequestID, String xCodiceServizio, String filter, SecurityContext securityContext, HttpServletRequest request) {
	      RicercaDelega ricercaDelega = new RicercaDelega();
	      Richiedente richiedente = new Richiedente(xRequestID, xCodiceServizio, getCodiceFiscale(request));
	      ricercaDelega.setRichiedente(richiedente);
	      
	      DelegheFilter f;
	      try {
	         f = FilterUtil.init(DelegheFilter.class, filter);
	         populateFilters(ricercaDelega, f);
	         
	      } catch (IllegalArgumentException iae) {
	         log.error("getDelegheBoList", iae.getMessage(), iae);
	         return Response.status(Response.Status.BAD_REQUEST).build();
	      }

	      RicercaDelegaResponse ricercaDelegaResponse = this.delegheBackOfficeService.ricercaDelegaService(ricercaDelega);
	      List<Delega> delegaList = ricercaDelegaResponse.getDeleghe();
	      

	      if (delegaList == null || delegaList.isEmpty()) {
	         return getError(ricercaDelegaResponse);
	      } 

	      List<it.csi.deleghe.delegheboweb.dto.delegheboweb.Delega> delegas = new DelegheMapper().fromList(delegaList);

	      return Response.ok(delegas).build();
   	}
   
   
   /**
    * 
    * @param xRequestID
    * @param xCodiceServizio
    * @param filter
    * @param securityContext
    * @param request
    * @return
    */
   public Response getDettaglioDelegaBo(String xRequestID, String xCodiceServizio, String filter, SecurityContext securityContext, HttpServletRequest request) {
	   	  GetDettaglioDelega req = new GetDettaglioDelega();
	      Richiedente richiedente = new Richiedente(xRequestID, xCodiceServizio, getCodiceFiscale(request));
	      req.setRichiedente(richiedente);

	      
	      DelegheFilter f;
	      try {
	         f = FilterUtil.init(DelegheFilter.class, filter);
	         Delega delega = new Delega();

	         String uuid = f.getUuid();
	         if (uuid != null) {
	            delega.setUuid(UUID.fromString(uuid));
	            req.setDelega(delega);
	         }
	         
	      } catch (IllegalArgumentException iae) {
	         log.error("getDettaglioDelegaBo", iae.getMessage(), iae);
	         return Response.status(Response.Status.BAD_REQUEST).build();
	      }

	      GetDettaglioDelegaResponse res = this.delegheBackOfficeService.getDettaglioDelegaService(req);
	      Delega delega = res.getDelega();

	      if (delega == null) {
	         return getError(res);
	      }

	      it.csi.deleghe.delegheboweb.dto.delegheboweb.Delega delegaweb = new DelegheMapper().from(delega);

	      
	      String compilatoreCF = delegaweb.getCompilatoreCF();
	      String compilatore;
	      if (!processedCF.containsKey(compilatoreCF)) {
	            processedCF.put(compilatoreCF, cittadinoService.getCompilatoreInformation(compilatoreCF).getCompilatore());
	      }

	      compilatore = processedCF.get(compilatoreCF);
	      delegaweb.setFullCompilatoreCF(compilatore);
	      

	      return Response.ok(delegaweb).build();
   	}
}
