/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.delegheboweb.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.csi.deleghe.delegheboweb.util.Constants;

/**
 * CORSFilter
 */
public class CORSFilter implements Filter {

	final static Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	
	private boolean isDisabled = false;
	private String accessControlAllowOrigin;
	private String accessControlAllowHeaders;
	private String accessControlAllowMethods;
	private String accessControlMaxAge;

	
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		boolean isHttpReqRes = req instanceof HttpServletRequest && resp instanceof HttpServletResponse;
		if(isDisabled || !isHttpReqRes ) {

			chain.doFilter(req, resp);
			return;
		}
		
		HttpServletRequest hreq = (HttpServletRequest) req;
		HttpServletResponse hresp = (HttpServletResponse) resp;
	
		setAccessControl(hresp);
		

		if (hreq.getMethod().equals("OPTIONS")) { 

			hresp.setStatus(HttpServletResponse.SC_ACCEPTED);
			return;
		}
		chain.doFilter(req, resp);
	}

	private void setAccessControl(HttpServletResponse hresp) {

		hresp.addHeader("Access-Control-Allow-Origin", this.accessControlAllowOrigin);
        hresp.addHeader("Access-Control-Allow-Headers", this.accessControlAllowHeaders);
        hresp.addHeader("Access-Control-Allow-Methods", this.accessControlAllowMethods);
        hresp.addHeader("Access-Control-Max-Age", this.accessControlMaxAge);
	}
	


	private enum Param{
		DISABLED("true"), 
		ACCESS_CONTROL_ALLOW_ORIGIN("*"),
		ACCESS_CONTROL_ALLOW_HEADERS("Authorization, Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, Shib-Identita-CodiceFiscale, shib-iride-identitadigitale, X-Codice-Servizio, X-Request-ID"), //, Shib-Identita-CodiceFiscale
		ACCESS_CONTROL_ALLOW_METHODS("POST, GET, PUT, PATCH, DELETE, HEAD, OPTIONS"),
		ACCESS_CONTROL_MAX_AGE("1209600");
		
		private String defaultValue;

		private Param(String defaultValue){
			this.defaultValue = defaultValue;
		}
	}

	@Override
	public void init(FilterConfig cfg) throws ServletException {
		
		String disabled = initParameter(cfg, Param.DISABLED);
		this.isDisabled = "true".equals(disabled);
		this.accessControlAllowOrigin = initParameter(cfg, Param.ACCESS_CONTROL_ALLOW_ORIGIN);
		this.accessControlAllowHeaders = initParameter(cfg, Param.ACCESS_CONTROL_ALLOW_HEADERS);
		this.accessControlAllowMethods = initParameter(cfg, Param.ACCESS_CONTROL_ALLOW_METHODS);
		this.accessControlMaxAge = initParameter(cfg, Param.ACCESS_CONTROL_MAX_AGE);
		
	}
	
	private String initParameter(FilterConfig cfg, Param param) {
		String value = cfg.getInitParameter(param.name());
		if(StringUtils.isBlank(value)) {
			return param.defaultValue;
		}
		return value;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
