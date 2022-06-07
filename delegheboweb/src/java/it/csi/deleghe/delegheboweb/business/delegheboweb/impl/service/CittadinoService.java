/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.delegheboweb.business.delegheboweb.impl.service;

import it.csi.deleghe.deleghebe.ws.DelegheCittadiniService;


import it.csi.deleghe.deleghebe.ws.model.Cittadino;
import it.csi.deleghe.deleghebe.ws.model.Errore;
import it.csi.deleghe.deleghebe.ws.model.Richiedente;
import it.csi.deleghe.deleghebe.ws.msg.RicercaCittadini;
import it.csi.deleghe.deleghebe.ws.msg.RicercaCittadiniResponse;
import it.csi.deleghe.delegheboweb.business.aura.AnagrafeFindWSClientPool;
import it.csi.deleghe.delegheboweb.business.aura.AnagrafeSanitariaWSClientPool;
import it.csi.deleghe.delegheboweb.business.aura.find.model.*;
import it.csi.deleghe.delegheboweb.business.aura.sanitaria.AnagrafeSanitariaSoap;
import it.csi.deleghe.delegheboweb.business.aura.sanitaria.GetProfiloSanitario;
import it.csi.deleghe.delegheboweb.business.aura.sanitaria.InfoSanitarie;
import it.csi.deleghe.delegheboweb.business.aura.sanitaria.SoggettoAuraMsg;
import it.csi.deleghe.delegheboweb.business.mapper.CittadinoMapper;
import it.csi.deleghe.delegheboweb.dto.CompilatoreInfo;
import it.csi.deleghe.delegheboweb.dto.UserInfo;
import it.csi.deleghe.delegheboweb.dto.delegheboweb.CittadinoFilter;
import it.csi.deleghe.delegheboweb.dto.delegheboweb.TokenRequest;
import it.csi.deleghe.delegheboweb.dto.delegheboweb.TokenResponse;
import it.csi.deleghe.delegheboweb.dto.deleghebowebLEGACY.DelegheFilter;
import it.csi.deleghe.delegheboweb.dto.deleghebowebLEGACY.DichiarazioneFilter;
import it.csi.deleghe.delegheboweb.util.filter.FilterUtil;
import it.csi.dmacc.GetTokenInformation2Request;
import it.csi.dmacc.GetTokenInformation2Response;
import it.csi.dmacc.GetTokenInformationResponse;
import it.csi.dmacc.TokenInfoServiceClient;
import it.csi.dmacc.TokenInformationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CittadinoService extends AbstractDelgheBEService {

   @Autowired
   private DelegheCittadiniService delegheCittadiniService;

   @Autowired
   private AnagrafeSanitariaWSClientPool anagrafeSanitariaWSClientPool;
   
   @Autowired
   private AnagrafeFindWSClientPool anagrafeFindSoapService;
   
   @Autowired
   private TokenInfoServiceClient tokenInfoServiceClient;
   

   public CompilatoreInfo getCompilatoreInformation(String cf) {
      CompilatoreInfo compilatoreInfo = new CompilatoreInfo();

      if (cf != null) {
         Cittadino entity = getDBCittadino(cf);

         if (entity != null) {
            compilatoreInfo.setAsl(entity.getAsl());
            compilatoreInfo.setCompilatore(entity.getNome() + " " + entity.getCognome());
         }
      }

      return compilatoreInfo;
   }

   private Cittadino getDBCittadino(String cf) {
      RicercaCittadini ricercaCittadini = prepareSoapRequest(getCodiceFiscale(null), UUID.randomUUID().toString(), "DELEGHEBO");
      ricercaCittadini.getCittadino().setCodiceFiscale(cf);
      RicercaCittadiniResponse ricercaCittadiniResponse = delegheCittadiniService.ricercaCittadiniService(ricercaCittadini);
      List<Cittadino> cittadini = ricercaCittadiniResponse.getCittadini();

      if (cittadini == null || cittadini.size() == 0) {
         return null;
      }

      return cittadini.get(0);
   }

   public Response getCittadino(String xRequestID, String xCodiceServizio, String filter, HttpServletRequest request) {
      RicercaCittadini ricercaCittadini = prepareSoapRequest(getCodiceFiscale(request), xRequestID, xCodiceServizio);
      FindProfiliAnagraficiRequest findProfiliAnagraficiRequest = new FindProfiliAnagraficiRequest();
      
      
      if (addFilter(filter, ricercaCittadini, findProfiliAnagraficiRequest)) {
         return Response.status(Response.Status.BAD_REQUEST).build();
      }

      List<Cittadino> cittadini = new ArrayList<>();
      RicercaCittadiniResponse ricercaCittadiniResponse = delegheCittadiniService.ricercaCittadiniService(ricercaCittadini);
      List <Errore> erroreRicerca = ricercaCittadiniResponse.getErrori();   
      
      if(!erroreRicerca.isEmpty() || erroreRicerca!=null) {
    	  if(erroreRicerca.size()!=0) {

    	  	findProfiliAnagraficiRequest.setFlagDecesso("0");
    	  	AnagrafeFindSoap client = anagrafeFindSoapService.getClient();
    	  	
    	  	DatiAnagraficiMsg responseAura = client.findProfiliAnagrafici(findProfiliAnagraficiRequest);  
    	  	if(responseAura!=null) {

    	  		cittadini=extractCitizens(responseAura);
    	  	}else {

    	  		return getError(ricercaCittadiniResponse);
    	  	}
    	  	
    	  }else {

        	cittadini = ricercaCittadiniResponse.getCittadini();   
        	
        	cittadini = extractCitizensAsl(cittadini);
          }         
      }   
      
      return Response.ok(new CittadinoMapper().fromList(cittadini)).build();
   }
   
   public Response getTokenInformation2(String xRequestID, String xCodiceServizio, String filter, SecurityContext securityContext, HttpServletRequest request)  { 

	   GetTokenInformation2Request getTokenInformation2Request = new GetTokenInformation2Request();    
	   TokenResponse tokenResponse = new TokenResponse();
	   
	   TokenRequest tokenRequest = new TokenRequest();
	      try {
	    	 tokenRequest = FilterUtil.init(TokenRequest.class, filter);
	         
	      } catch (IllegalArgumentException iae) {
	         log.error("getTokenInformation2", iae.getMessage(), iae);
	         return Response.status(Response.Status.BAD_REQUEST).build();
	      }
	   
	   getTokenInformation2Request.setApplicazione(tokenRequest.getApplicazione());
	   getTokenInformation2Request.setIpBrowser(tokenRequest.getIpBrowser());
	   getTokenInformation2Request.setToken(tokenRequest.getToken());
	   

	    

		TokenInformationService client = tokenInfoServiceClient.getClient();	

		GetTokenInformation2Response responseTokenInformation2 = client.getTokenInformation2(getTokenInformation2Request);

		
	  	if(responseTokenInformation2!=null) {	  		
	  		String esito = responseTokenInformation2.getEsito().toString();

	  		if(esito == "FALLIMENTO") {

	  			tokenResponse.setEsito("FALLIMENTO"); 			
	  		}else {

	  			tokenResponse= extractToken(responseTokenInformation2);
	  			
	  		}
	  	}
	  	return Response.ok(tokenResponse).build();
	}
 

   public RicercaCittadini prepareSoapRequest(String shibIdentitaCodiceFiscale, String xRequestID, String xCodiceServizio) {
      RicercaCittadini ricercaCittadini = new RicercaCittadini();
      ricercaCittadini.setRichiedente(new Richiedente(xRequestID, xCodiceServizio, shibIdentitaCodiceFiscale));
      Cittadino cittadino = new Cittadino();
      ricercaCittadini.setCittadino(cittadino);
      return ricercaCittadini;
   }

   public boolean addFilter(String filter, RicercaCittadini ricercaCittadini, FindProfiliAnagraficiRequest findProfiliAnagraficiRequest) {
      try {
         CittadinoFilter f = FilterUtil.init(CittadinoFilter.class, filter);
         populateFilters(ricercaCittadini, findProfiliAnagraficiRequest, f);
      } catch (IllegalArgumentException iae) {
         log.error("getDichiarazioniList", iae.getMessage(), iae);
         return true;
      }
      return false;
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

         String asl = extractAnagrafeSanitaria(citizen1.getIdAura());         
         
         citizen1.setNome(foundCitizen.getNome());
         citizen1.setCodiceFiscale(foundCitizen.getCodiceFiscale());
         citizen1.setCognome(foundCitizen.getCognome());
         citizen1.setSesso(foundCitizen.getSesso());
         citizen1.setComuneNascita(foundCitizen.getComuneNascita());
         citizen1.setDataNascita(foundCitizen.getDataNascita().toGregorianCalendar().getTime());
         citizen1.setAsl(asl);
         citizens.add(citizen1);
      });

      return citizens;
   }
   
   private TokenResponse extractToken(GetTokenInformation2Response responseTokenInformation2) {	     

	      TokenResponse tokenResponse = new TokenResponse();
	      tokenResponse.setCodiceAzienda(responseTokenInformation2.getRichiedente().getCodiceAzienda());
	      tokenResponse.setCodiceFiscale(responseTokenInformation2.getRichiedente().getCodiceFiscale());    
	      tokenResponse.setEsito(responseTokenInformation2.getEsito().toString());

	      return tokenResponse;
	   }
   
   private String extractAnagrafeSanitaria(Long idAura) {

	   String asl = null;  				
	   AnagrafeSanitariaSoap anagrafe = anagrafeSanitariaWSClientPool.getClient();


	   SoggettoAuraMsg anagrafeResponse= anagrafe.getProfiloSanitario(idAura.toString());
		
		if(anagrafeResponse!=null) {
			InfoSanitarie infoSanitarie = anagrafeResponse.getBody().getInfoSan();
		   	  
		      if(infoSanitarie.getAslAssistenza()!=null) {
		    	  asl = infoSanitarie.getAslAssistenza();
	
		      }else{
		    	  if(infoSanitarie.getAslDomicilio()!=null) {
		    		  asl = infoSanitarie.getAslDomicilio(); 
		    		
		    	  }	    	  
		      }				
		}   	  
	return asl;
   }
   
   private List<Cittadino> extractCitizensAsl(List<Cittadino> cittadini) {	      
	  	      
	      List<Cittadino> citizens = new ArrayList<>();

	      cittadini.forEach(foundCitizen -> {	         

	         String asl = extractAnagrafeSanitaria(foundCitizen.getIdAura());  
	         foundCitizen.setAsl(asl);

	         citizens.add(foundCitizen);
	      });

	      return citizens;
	}

   private void populateFilters(RicercaCittadini ricercaCittadini, FindProfiliAnagraficiRequest findProfiliAnagraficiRequest, CittadinoFilter f) {
      final String codiceFiscale = f.getCodiceFiscale();
      if (codiceFiscale != null) {
         ricercaCittadini.getCittadino().setCodiceFiscale(codiceFiscale);
         findProfiliAnagraficiRequest.setCodiceFiscale(codiceFiscale);
      }

      final String cognome = f.getCognome();
      if (cognome != null) {
         ricercaCittadini.getCittadino().setCognome(cognome);
         findProfiliAnagraficiRequest.setCognome(cognome);
      }

      final String comuneNascita = f.getComuneNascita();
      if (comuneNascita != null) {
         ricercaCittadini.getCittadino().setComuneNascita(comuneNascita);
      }

      final Date dataNascita = f.getDataNascita();
      if (dataNascita != null) {
         ricercaCittadini.getCittadino().setDataNascita(dataNascita);
         findProfiliAnagraficiRequest.setDataNascita(getData(dataNascita));
      }

      final String nome = f.getNome();
      if (nome != null) {
         ricercaCittadini.getCittadino().setNome(nome);
         findProfiliAnagraficiRequest.setNome(nome);
      }

      final String sesso = f.getSesso();
      if (sesso != null) {
         ricercaCittadini.getCittadino().setSesso(sesso);
      }

   }

   private String getDataToString(XMLGregorianCalendar calendar) {
      if(calendar == null) {
         return null;
      }
      Date d = calendar.toGregorianCalendar().getTime();
      return getData(d);
   }

   /**
    * Converte una data in formato Date in una data in formato string.
    * (copiato perch� nelle sanita il Convertitore non � visibile)
    *
    * @param data data
    * @return data nel formato String
    */
   private String getData(Date data) {
      if (data == null) return "";

      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
      String dataString = sdf.format(data);
      return dataString;
   }



}
