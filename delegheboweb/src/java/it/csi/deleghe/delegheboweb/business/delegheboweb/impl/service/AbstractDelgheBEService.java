/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.delegheboweb.business.delegheboweb.impl.service;

import it.csi.deleghe.deleghebe.ws.model.Errore;

import it.csi.deleghe.deleghebe.ws.msg.ServiceResponse;
import it.csi.deleghe.delegheboweb.dto.delegheboweb.LoginOperatore;
import it.csi.deleghe.delegheboweb.exception.RESTException;
import it.csi.deleghe.delegheboweb.filter.IrideIdAdapterFilter;
import it.csi.deleghe.delegheboweb.util.DelegheBoWebStatus;
import it.csi.deleghe.delegheboweb.util.LogUtil;
import it.csi.iride2.policy.entity.Identita;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

public class AbstractDelgheBEService {
   protected LogUtil log = new LogUtil(this.getClass());

   public String getCodiceFiscale(HttpServletRequest request ) {
      Identita identita = request != null?
              (Identita) request.getSession().
                      getAttribute(IrideIdAdapterFilter.IRIDE_ID_SESSIONATTR)
              : null;

      if (identita != null) {
         return identita.getCodFiscale();
      } else {
         return "xyz";
      }
   }
   
   public Response getIdentitaOperatore(HttpServletRequest request ){
	   	  LoginOperatore loginOperatore = new LoginOperatore();
	      Identita identita = request != null?
	              (Identita) request.getSession().getAttribute(IrideIdAdapterFilter.IRIDE_ID_SESSIONATTR): null;

	      if (identita != null) {
	    	  
	    	  String ipAddress =getIpAddressClient(request);
	    	  String tokenLcce = request.getParameter("token");				
				if(tokenLcce == null || tokenLcce.isEmpty()){	
					Response.ok(loginOperatore).build();
				}
	    	  
	    	  String codiceFiscaleOperatore = identita.getCodFiscale();
	    	  String cognomeOperatore = identita.getCognome();
	    	  String nomeOperatore = identita.getNome();
	    	  
			
	    	  
	    	  loginOperatore.setCodiceFiscaleOperatore(codiceFiscaleOperatore);
	    	  loginOperatore.setCognomeOperatore(cognomeOperatore);
	    	  loginOperatore.setNomeOperatore(nomeOperatore);
	    	  loginOperatore.setTokenLcce(tokenLcce);
	    	  loginOperatore.setIpAddress(ipAddress);
	    	  
	    	  return Response.ok(loginOperatore).build();

	      } else {

	    	  loginOperatore.setCodiceFiscaleOperatore("xyz");
	    	  loginOperatore.setCognomeOperatore("FITTIZIO");
	    	  loginOperatore.setNomeOperatore("FINTO");
	    	  loginOperatore.setTokenLcce("TOKENFITTIZIO");
	    	  
	    	  String ipAddress =getIpAddressClient(request);
	    	  loginOperatore.setIpAddress(ipAddress);
	    	  
	    	  return Response.ok(loginOperatore).build();
	      }
	   }
   
   
   private String getIpAddressClient(HttpServletRequest request) {

		String ipAddress = request.getHeader("X-FORWARDED-FOR");
		String remoteAddress=request.getRemoteAddr();


		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		return ipAddress;
	}
   
   
   public Response removeFromSession(HttpServletRequest request ){


				request.getSession().removeAttribute(IrideIdAdapterFilter.IRIDE_ID_SESSIONATTR);	
				request.getSession().removeAttribute(IrideIdAdapterFilter.USERINFO_SESSIONATTR);	
				request.getSession().removeAttribute(IrideIdAdapterFilter.USERINFO_COD_FISCALE);	
	

			
			Identita identita = request != null?
			(Identita) request.getSession().getAttribute(IrideIdAdapterFilter.IRIDE_ID_SESSIONATTR): null;
			if (identita == null) {

			}
	   
	   return Response.ok().build();
	   
   }


   
   Response getError(ServiceResponse response) {
	      List<Errore> errori = response.getErrori();

	      return Response.status(Response.Status.NOT_FOUND).entity(errori).build();
   }

   protected boolean isCodiceFiscaleValido(String cf) {
      return cf != null && cf.matches("[a-zA-Z]{6}\\d\\d[a-zA-Z]\\d\\d[a-zA-Z]\\d\\d\\d[a-zA-Z]");
   }

   protected void checkCodiceFiscaleValido(String cf) {
      checkCondition(isCodiceFiscaleValido(cf), DelegheBoWebStatus.CODICE_FISCALE_NON_VALIDO.getRestException(cf));
   }

   /**
    * Controlla che il parametro str sia valorizzato;
    * diversamente solleva l'eccezione passata come parametro.
    *
    * @param re
    */
   protected void checkNotBlank(String str, RESTException re) {
      checkCondition(StringUtils.isNotBlank(str), re);
   }

   /**
    * Controlla che la condizione isOk sia uguale a <code>true</code>;
    * diversamente solleva l'eccezione passata come parametro.
    *
    * @param isOk
    * @param re
    */
   protected void checkCondition(boolean isOk, RESTException re) {
      if(!isOk) {
         throw re;
      }
   }

}
