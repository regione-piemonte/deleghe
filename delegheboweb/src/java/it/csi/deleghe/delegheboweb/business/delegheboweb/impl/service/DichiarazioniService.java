/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.delegheboweb.business.delegheboweb.impl.service;

import it.csi.deleghe.deleghebe.ws.DelegheBackOfficeService;


import it.csi.deleghe.deleghebe.ws.DelegheCittadiniService;
import it.csi.deleghe.deleghebe.ws.model.*;
import it.csi.deleghe.deleghebe.ws.model.Cittadino;
import it.csi.deleghe.deleghebe.ws.model.DichiarazioneDettaglio;
import it.csi.deleghe.deleghebe.ws.model.Errore;
import it.csi.deleghe.deleghebe.ws.msg.*;
import it.csi.deleghe.delegheboweb.business.aura.AnagrafeFindWSClientPool;
import it.csi.deleghe.delegheboweb.business.aura.find.model.AnagrafeFindSoap;
import it.csi.deleghe.delegheboweb.business.aura.find.model.ArrayOfdatianagraficiDatiAnagrafici;
import it.csi.deleghe.delegheboweb.business.aura.find.model.DatiAnagrafici;
import it.csi.deleghe.delegheboweb.business.aura.find.model.DatiAnagraficiMsg;
import it.csi.deleghe.delegheboweb.business.aura.find.model.FindProfiliAnagraficiRequest;
import it.csi.deleghe.delegheboweb.business.mapper.CittadinoMapper;
import it.csi.deleghe.delegheboweb.business.mapper.DelegheMapper;
import it.csi.deleghe.delegheboweb.business.mapper.DichiarazioneDettaglioMapper;
import it.csi.deleghe.delegheboweb.business.mapper.DichiarazioneMapper;
import it.csi.deleghe.delegheboweb.dto.delegheboweb.CreateDichiarazione;
import it.csi.deleghe.delegheboweb.dto.delegheboweb.Dichiarazione;
import it.csi.deleghe.delegheboweb.dto.deleghebowebLEGACY.*;
import it.csi.deleghe.delegheboweb.dto.deleghebowebLEGACY.DichiarazioneRuolo;
import it.csi.deleghe.delegheboweb.util.SpringApplicationContextProvider;
import it.csi.deleghe.delegheboweb.util.filter.FilterUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.*;

import static it.csi.deleghe.delegheboweb.util.CompilatoreMap.processedCF;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class DichiarazioniService extends AbstractDelgheBEService {

   @Autowired
   private DelegheCittadiniService delegheCittadiniService;

   @Autowired
   private DelegheBackOfficeService delegheBackOfficeService;
   
   @Autowired
   private AnagrafeFindWSClientPool anagrafeFindSoapService;

   private DelegheService delegheSerivce = (DelegheService) SpringApplicationContextProvider.getApplicationContext().getBean("it.csi.deleghe.delegheboweb.business.delegheboweb.impl.service.DelegheService");

   private CittadinoService cittadinoService = (CittadinoService) SpringApplicationContextProvider.getApplicationContext().getBean("it.csi.deleghe.delegheboweb.business.delegheboweb.impl.service.CittadinoService");

   public Response getDichiarazioniList(String xRequestID, String xCodiceServizio, String cf, String filter, SecurityContext securityContext, HttpServletRequest request) {

      RicercaDichiarazioni reqRd = new RicercaDichiarazioni();
      Richiedente richiedente = new Richiedente(xRequestID, xCodiceServizio, getCodiceFiscale(request));
      final it.csi.deleghe.deleghebe.ws.model.Dichiarazione dichiarazione = new it.csi.deleghe.deleghebe.ws.model.Dichiarazione();
      reqRd.setDichiarazione(dichiarazione);
      reqRd.setRichiedente(richiedente);

      DichiarazioneFilter f;
      try {
         f = FilterUtil.init(DichiarazioneFilter.class, filter);
         populateFilters(reqRd, f);
      } catch (IllegalArgumentException iae) {
         log.error("getDichiarazioniList", iae.getMessage(), iae);
         return Response.status(Response.Status.BAD_REQUEST).build();
      }

      List<it.csi.deleghe.deleghebe.ws.model.Dichiarazione> dichiarazioni = new ArrayList<>();
      List<Errore> errori = new ArrayList<>();
      final StringCrit codiceFiscaleCit1 = f.getCodiceFiscaleCit1();
      if (codiceFiscaleCit1 != null && f.getRuoloCit1() == null) {
         for (int i = 1; i <= 2; i++) {
            reqRd.getDichiarazione().setDettagli(new ArrayList<>());
            setDichCittadino(reqRd, codiceFiscaleCit1.getEq(), i);
            RicercaDichiarazioniResponse resRd = delegheCittadiniService.ricercaDichiarazioniService(reqRd);

            if (!RisultatoCodice.SUCCESSO.equals(resRd.getEsito())) {
               errori = resRd.getErrori();
               return Response.status(Response.Status.SEE_OTHER).entity(errori).build();
            } else {
               final List<it.csi.deleghe.deleghebe.ws.model.Dichiarazione> dichiarazioni1 = resRd.getDichiarazioni();
               errori = resRd.getErrori();
               if (dichiarazioni1 != null) dichiarazioni.addAll(dichiarazioni1);
            }
         }
      } else {
         RicercaDichiarazioniResponse resRd = delegheCittadiniService.ricercaDichiarazioniService(reqRd);

         if (!RisultatoCodice.SUCCESSO.equals(resRd.getEsito())) {
            errori = resRd.getErrori();
            return Response.status(Response.Status.SEE_OTHER).entity(errori).build();
         } else {
            final List<it.csi.deleghe.deleghebe.ws.model.Dichiarazione> dichiarazioni1 = resRd.getDichiarazioni();
            errori = resRd.getErrori();
            if (dichiarazioni1 != null) dichiarazioni.addAll(resRd.getDichiarazioni());
         }
      }

      if (dichiarazioni.isEmpty()) {
         return Response.status(Response.Status.NOT_FOUND).entity(errori).build();
      }

      List<Dichiarazione> entity = new DichiarazioneMapper().fromList(dichiarazioni);

      for (Dichiarazione dichiarazione1 : entity) {
         String compilatoreCF = dichiarazione1.getCompilatoreCF();
         String compilatore;
         if (!processedCF.containsKey(compilatoreCF)) {
            processedCF.put(compilatoreCF, cittadinoService.getCompilatoreInformation(compilatoreCF).getCompilatore());
         }

         compilatore = processedCF.get(compilatoreCF);
         dichiarazione1.setFullCompilatoreCF(compilatore);
      }

      return Response.ok(entity).build();
   }

   public Response updateDichiarazioni(String xRequestID, String xCodiceServizio, String cf, String uuidDichiarazione, Dichiarazione dichiarazione, SecurityContext securityContext, HttpServletRequest request) {

      AggiornaDichiarazione aggiornaDichiarazione = new AggiornaDichiarazione();

      Richiedente richiedente = new Richiedente(xRequestID, xCodiceServizio, getCodiceFiscale(request));
      aggiornaDichiarazione.setRichiedente(richiedente);

      final DichiarazioneMapper dichiarazioneMapper = new DichiarazioneMapper();
      final it.csi.deleghe.deleghebe.ws.model.Dichiarazione to = dichiarazioneMapper.to(dichiarazione);
      to.setDettagli(new DichiarazioneDettaglioMapper().toList(dichiarazione.getDettagli()));

      aggiornaDichiarazione.setDichiarazione(to);

      final AggiornaDichiarazioneResponse aggiornaDichiarazioneResponse = delegheCittadiniService.aggiornaDichiarazioneService(aggiornaDichiarazione);

      if (!RisultatoCodice.SUCCESSO.equals(aggiornaDichiarazioneResponse.getEsito())) {
         List<Errore> errori = aggiornaDichiarazioneResponse.getErrori();
         return Response.status(Response.Status.BAD_REQUEST).entity(errori).build();
      }
      return Response.ok().build();
   }

   void setDichCittadino(RicercaDichiarazioni req, String cf, int cittNumber) {
      ArrayList<DichiarazioneDettaglio> dettagli = new ArrayList<>();
      DichiarazioneDettaglio dichiarazioneDettaglio = new DichiarazioneDettaglio();
      Cittadino cittadino = new Cittadino();
      cittadino.setCodiceFiscale(cf);

      if (cittNumber == 1) {
         dichiarazioneDettaglio.setCittadino1(cittadino);
      } else if (cittNumber == 2) {
         dichiarazioneDettaglio.setCittadino2(cittadino);
      }

      dettagli.add(dichiarazioneDettaglio);
      req.getDichiarazione().setDettagli(dettagli);
   }

   private void populateFilters(RicercaDichiarazioni req, DichiarazioneFilter f) {

      if (f.getUuid() != null && StringUtils.isNotBlank(f.getUuid().getEq())) {
         req.getDichiarazione().setUuid(UUID.fromString(f.getUuid().getEq()));
      }

      final StringCrit codiceFiscaleCit1 = f.getCodiceFiscaleCit1();
      final DichiarazioneRuolo ruoloCit1 = f.getRuoloCit1();
      if (codiceFiscaleCit1 != null && ruoloCit1 != null) {
         final String codice = ruoloCit1.getCodice();
         if (codice != null) {
            if (codice.equals("GENITORE_1")) {
               setDichCittadino(req, codiceFiscaleCit1.getEq(), 1);
            } else {
               setDichCittadino(req, codiceFiscaleCit1.getEq(), 2);
            }
         }
      }

      final Date dataInserimentoA = f.getDataInserimentoA();
      if (dataInserimentoA != null) {
         req.setDataInserimentoA(dataInserimentoA);
      }

      final Date dataInserimentoDa = f.getDataInserimentoDa();
      if (dataInserimentoDa != null) {
         req.setDataInserimentoDa(dataInserimentoDa);
      }

      if (f.getTipo() != null && !f.getTipo().isEmpty()) {
         List<String> tipiDichiarazione = new ArrayList<>();
         for (DichiarazioneTipo tipo : f.getTipo()) {
            tipiDichiarazione.add(tipo.getCodice());
         }

         req.setTipiDichiarazione(tipiDichiarazione);
      }

      if (f.getModo() != null && !f.getModo().isEmpty()) {
         List<String> modiDichiarazione = new ArrayList<>();
         for (DichiarazioneModo tipo : f.getModo()) {
            modiDichiarazione.add(tipo.getCodice());
         }

         req.setModiDichiarazione(modiDichiarazione);
      }

      if (f.getStato() != null && !f.getStato().isEmpty()) {
         List<String> statiDichiarazione = new ArrayList<>();
         for (DichiarazioneStato tipo : f.getStato()) {
            statiDichiarazione.add(tipo.getCodice());
         }

         req.setStatiDichiarazione(statiDichiarazione);
      }

   }

   public Response create(String xRequestID, String xCodiceServizio, CreateDichiarazione createDichiarazione, HttpServletRequest request) {

	   
      InserisciDichiarazione inserisciDichiarazione = new InserisciDichiarazione();
      
      Richiedente richiedente = new Richiedente(xRequestID, xCodiceServizio, getCodiceFiscale(request));
      inserisciDichiarazione.setRichiedente(richiedente);
      
      final DichiarazioneMapper dichiarazioneMapper = new DichiarazioneMapper();
      Dichiarazione dichiarazione = createDichiarazione.getDichiarazione();
      
      String provenienza = dichiarazione.getProvenienza();
      
      String statoServizioDelega = dichiarazione.getStatoServizioDelega(); 
      
      dichiarazione.setStato(dichiarazione.getStato()); 
      dichiarazione.setCompilatoreCF(richiedente.getCodiceFiscale());
      

      inserisciDichiarazione.setDichiarazione(dichiarazioneMapper.to(dichiarazione));

      final InserisciDichiarazioneResponse inserisciDichiarazioneResponse = delegheCittadiniService.inserisciDichiarazioneService(inserisciDichiarazione);
      

      if (!RisultatoCodice.SUCCESSO.equals(inserisciDichiarazioneResponse.getEsito())) {
         List<Errore> errori = inserisciDichiarazioneResponse.getErrori();
         return Response.status(Response.Status.SEE_OTHER).entity(errori).build();
      }

      it.csi.deleghe.deleghebe.ws.model.Dichiarazione responseDichiarazione = inserisciDichiarazioneResponse.getDichiarazione();

      List<DichiarazioneDettaglio> dettagli = responseDichiarazione.getDettagli();
      for (DichiarazioneDettaglio dichiarazioneDettaglio : dettagli) {    	  
         Delega delega = new Delega();
         delega.setBloccaDelega(false);   
         
         final Cittadino delegante = dichiarazioneDettaglio.getCittadino2(); 
         delega.setDelegante(delegante);
         
         final Cittadino delegato = dichiarazioneDettaglio.getCittadino1(); 
         delega.setDelegato(delegato);
         
         delega.setPresavisione(false);
         delega.setDichiarazioneDettaglio(dichiarazioneDettaglio);
         
         final it.csi.deleghe.delegheboweb.dto.delegheboweb.DichiarazioneTipo tipo = dichiarazione.getTipo();
         if (tipo != null) {
            delega.setTipoDelega(tipo.getCodice()); 
         }
         
         
         delega.setStatoDelega(statoServizioDelega); 
         
         delega.setRuoloOperazione(responseDichiarazione.getRuoloOperazione());
         delega.setCompilatoreCF(richiedente.getCodiceFiscale());
         
         ArrayList<DelegaServ> servs = new ArrayList<>();

         final RicercaServiziResponse ricercaServiziResponse = delegheBackOfficeService.ricercaServiziService(new RicercaServizi());
         final List<Servizio> servizi = ricercaServiziResponse.getServizi();
         for (Servizio servizio : servizi) {
            final Boolean minore = servizio.getMinore();
            final Boolean delegabile = servizio.getDelegabile();
            if (minore != null && minore && delegabile != null && delegabile) {
               final DelegaServ delegaServ = new DelegaServ();
               delegaServ.setServizio(servizio);
               final DelegaServStato stato = new DelegaServStato();
               
               stato.setCodice(statoServizioDelega);
               if (tipo != null) {
                  delegaServ.setTipoDelega(tipo.getCodice());
               }
               delegaServ.setStato(stato);
               delegaServ.setDelega(delega);
               
               if(provenienza.equalsIgnoreCase("GENITORE")) {
            	  
            	   
                  
                   final Calendar calendar = Calendar.getInstance();               
                   calendar.setTime(Calendar.getInstance().getTime());
                   Date dataOdierna = calendar.getTime();
                   
                  
                  
                   final Calendar calendarDelegante = Calendar.getInstance();
                   calendarDelegante.setTime(delegante.getDataNascita());
                   calendarDelegante.add(calendarDelegante.YEAR, 18);
                   Date dataNascitaDelegantePiu18 = calendarDelegante.getTime();
                   
                   if(dataNascitaDelegantePiu18.after(dataOdierna)) {
                	   
                	   delegaServ.setDataDecorrenza(Calendar.getInstance().getTime());
                	   
                       delegaServ.setDataScadenza(dataNascitaDelegantePiu18);
                	   
                   }else {
                 	   
                	   calendar.add(Calendar.YEAR, 1);
                	   delegaServ.setDataDecorrenza(Calendar.getInstance().getTime());
                	   
                	   Date dataOdiernaPiuAnno = calendar.getTime();
                       delegaServ.setDataScadenza(dataOdiernaPiuAnno);
                   }             	   
               }else {
            	   
            	   delegaServ.setDataScadenza(dichiarazione.getDataFineTutela());
               }
               
               
                  
               
               servs.add(delegaServ);
            }
         }

         delega.setServizi(servs);
         final Response response = this.delegheSerivce.create(xRequestID, xCodiceServizio, new DelegheMapper().from(delega), request);
         

         if (response.getStatus() == Response.Status.SEE_OTHER.getStatusCode()) {
        	 List<Errore> errori = inserisciDichiarazioneResponse.getErrori();
             return Response.status(Response.Status.SEE_OTHER).entity(errori).build();
         }
      }

      return Response.ok(dichiarazioneMapper.from(responseDichiarazione)).build();
   }
   
   public Response getIdAura(String xRequestID, String xCodiceServizio, String codiceFiscaleInput, HttpServletRequest request) {
	      FindProfiliAnagraficiRequest findProfiliAnagraficiRequest = new FindProfiliAnagraficiRequest();	  


	      
	      List<Cittadino> cittadini = new ArrayList<>(); 
	      
	    	    
	    		
	    	  	findProfiliAnagraficiRequest.setFlagDecesso("0");
	    	  	findProfiliAnagraficiRequest.setIdEnte("SANSOL");

	    	  	findProfiliAnagraficiRequest.setCodiceFiscale(codiceFiscaleInput);
	    	  	
	    	  	AnagrafeFindSoap client = anagrafeFindSoapService.getClient();
	    	  	
	    	  	DatiAnagraficiMsg responseAura = client.findProfiliAnagrafici(findProfiliAnagraficiRequest);  
	    	  	if(responseAura!=null) {
	    	  	

	    	  		cittadini=extractCitizens(responseAura);
	    	  	}         
	      
	      return Response.ok(new CittadinoMapper().fromList(cittadini)).build();
   }
   
   
   private List<Cittadino> extractCitizens(DatiAnagraficiMsg responseAura) {
	      ArrayOfdatianagraficiDatiAnagrafici elencoProfili = responseAura.getBody().getElencoProfili();

	      
	      List<Cittadino> citizens = new ArrayList<>();

	      if (elencoProfili == null) {

	    	  return citizens;
	      }
	      

	      List<DatiAnagrafici> datianagrafici = elencoProfili.getDatianagrafici();

	      datianagrafici.forEach(foundCitizen -> {
	  
	         Cittadino citizen1 = new Cittadino();
	         
	         citizen1.setIdAura(foundCitizen.getIdProfiloAnagrafico().longValue()); 	         
	         citizen1.setNome(foundCitizen.getNome());
	         citizen1.setCodiceFiscale(foundCitizen.getCodiceFiscale());
	         citizen1.setCognome(foundCitizen.getCognome());
	         citizen1.setSesso(foundCitizen.getSesso());
	         citizen1.setComuneNascita(foundCitizen.getComuneNascita());
	         citizen1.setDataNascita(foundCitizen.getDataNascita().toGregorianCalendar().getTime());
	         

	         
	         citizens.add(citizen1);
	      });
	      return citizens;
	}
   
  
   
  
   
}
