/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.delegheboweb.business.delegheboweb.impl.service;

import java.io.UnsupportedEncodingException;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import it.csi.deleghe.deleghebe.ws.model.Cittadino;
import it.csi.deleghe.deleghebe.ws.msg.RicercaCittadini;
import it.csi.deleghe.deleghebe.ws.msg.RicercaCittadiniResponse;
import it.csi.deleghe.delegheboweb.business.aura.AnagrafeFindWSClientPool;
import it.csi.deleghe.delegheboweb.business.aura.find.model.AnagrafeFindSoap;
import it.csi.deleghe.delegheboweb.business.aura.find.model.DatiAnagraficiMsg;
import it.csi.deleghe.delegheboweb.business.aura.find.model.FindProfiliAnagraficiRequest;
import it.csi.deleghe.delegheboweb.business.mapper.CittadinoMapper;
import it.csi.deleghe.delegheboweb.business.mapper.ComuniMapper;
import it.csi.deleghe.delegheboweb.business.mapper.NazioniMapper;
import it.csi.deleghe.delegheboweb.dto.delegheboweb.Comuni;
import it.csi.deleghe.delegheboweb.dto.delegheboweb.Nazioni;
import it.csi.deleghe.delegheboweb.util.ComuniProperties;
import it.csi.deleghe.delegheboweb.util.Constants;
import it.csi.deleghe.delegheboweb.util.NazioniProperties;

public class ComuniService {
		
	String shibIdentitaCodiceFiscale;			
	String cf;									
	
	@Autowired
	private ComuniProperties comuniProperties;
	
	@Autowired
	private NazioniProperties nazioniProperties;
	
	final static Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	
	 public Response cercaComuniGet(String xRequestID, String xCodiceServizio, String comune, HttpServletRequest request) {
		 
		 List<Comuni> comuni = null;
		 RestTemplate rt = new RestTemplate();
		 URI uri;
         ResponseEntity<String> re2 = null;
		 

		 String filter = "{\"desc\":{\"si\":"+comune+"}}";	
		 

		 String filtroCodificato = encodeURIComponent(filter);
 
		 	
		 	StringBuilder base = new StringBuilder( comuniProperties.getUrl()  + "?filter=");
		 	base.append( filtroCodificato);
		 	base.append("&limit=100");
				
			
			HttpHeaders headers = new HttpHeaders();
			headers.add("X-Request-ID", xRequestID);
			headers.add("X-Codice-Servizio", xCodiceServizio);
			
			String auth = comuniProperties.getUsername() + ":" + comuniProperties.getPassword();
	        String encodedAuth = org.jboss.resteasy.util.Base64.encodeBytes(auth.getBytes( ));
	        String authHeader = "Basic " + new String( encodedAuth );
			
	        headers.add("Authorization", authHeader); 
	        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);
	        

	        
	        try {
	        uri = new URI(base.toString());
	        re2 = rt.exchange(uri, HttpMethod.GET, httpEntity, String.class);
	        } catch (URISyntaxException e) {
	        e.printStackTrace();
	        }
	        
	        String risultato =re2.getBody();
	        
	        try {
				comuni = ComuniMapper.createModelComuni(risultato);
			} catch (Exception e) {
				e.printStackTrace();
			}
	       	        
	        return Response.ok(comuni).build();	 
	 }
	 
	 public Response cercaNazioniGet(String xRequestID, String xCodiceServizio, String nazione, HttpServletRequest request) {
			
		 List<Nazioni> nazioni = null;
		 RestTemplate rt = new RestTemplate();
		 URI uri;
         ResponseEntity<String> re2 = null;
		 
		 LOG.info("nazione: "+nazione);
		 LOG.info("url: "+nazioniProperties.getUrlNazioni());

		 String filter = "{\"desc\":{\"si\":"+nazione+"}}";	
		 
		 LOG.info("filter PIRMA DELLE CODIFICA: "+filter);
		 String filtroCodificato = encodeURIComponent(filter);
		 	 
		 	
		 	StringBuilder base = new StringBuilder( nazioniProperties.getUrlNazioni()  + "?filter=");
		 	base.append( filtroCodificato);
		 	base.append("&limit=100");
					
			
			HttpHeaders headers = new HttpHeaders();
			headers.add("X-Request-ID", xRequestID);
			headers.add("X-Codice-Servizio", xCodiceServizio);
			
			String auth = nazioniProperties.getUsernameNazioni() + ":" + nazioniProperties.getPasswordNazioni();
	        String encodedAuth = org.jboss.resteasy.util.Base64.encodeBytes(auth.getBytes( ));
	        String authHeader = "Basic " + new String( encodedAuth );
			
	        headers.add("Authorization", authHeader); 
	        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);
	        
	        try {
	        uri = new URI(base.toString());
	        re2 = rt.exchange(uri, HttpMethod.GET, httpEntity, String.class);
	        } catch (URISyntaxException e) {
	        e.printStackTrace();
	        }
	        
	        String risultato =re2.getBody();
	        
	        try {
	        	nazioni = NazioniMapper.createModelNazioni(risultato);
			} catch (Exception e) {
				e.printStackTrace();
			}
	       	        
	        return Response.ok(nazioni).build();	 
	 }
	 
	 public static String decodeURIComponent(String s)
	  {
	    if (s == null)
	    {
	      return null;
	    }

	    String result = null;

	    try
	    {
	      result = URLDecoder.decode(s, "UTF-8");
	    }


	    catch (UnsupportedEncodingException e)
	    {
	      result = s;  
	    }

	    return result;
	  }

	  public static String encodeURIComponent(String s)
	  {
	    String result = null;

	    try
	    {
	      result = URLEncoder.encode(s, "UTF-8")
	                         .replaceAll("\\+", "%20")
	                         .replaceAll("\\%21", "!")
	                         .replaceAll("\\%27", "'")
	                         .replaceAll("\\%28", "(")
	                         .replaceAll("\\%29", ")")
	                         .replaceAll("\\%7E", "~");
	    }


	    catch (UnsupportedEncodingException e)
	    {
	      result = s;
	    }

	    return result;
	  }  



}
