/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.dmacc;

import java.util.HashMap;

import java.util.Map;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.ws.security.handler.WSHandlerConstants;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;


import it.csi.deleghe.delegheboweb.integration.facade.ClientPasswordCallback;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class TokenInfoServiceClient {

	String userToken;
	String passToken;
	String tokenInfoServiceUrl;
	
	public TokenInformationService getClient() {
		ClientPasswordCallback callback=new ClientPasswordCallback();
		callback.setUserToken(userToken);
		callback.setPassToken(passToken);
		


		Map<String, Object> outProps = new HashMap<String, Object>();
		outProps.put(WSHandlerConstants.ACTION, "UsernameToken");
		outProps.put(WSHandlerConstants.PASSWORD_TYPE, "PasswordText");
		outProps.put(WSHandlerConstants.USER, userToken);
		outProps.put(WSHandlerConstants.PW_CALLBACK_REF, callback);
		outProps.put(WSHandlerConstants.MUST_UNDERSTAND, "false");


		WSS4JOutInterceptor wssOut = new WSS4JOutInterceptor(outProps);

		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(TokenInformationService.class);
		factory.setAddress(tokenInfoServiceUrl);
		factory.getOutInterceptors().add(wssOut);

		return (TokenInformationService) factory.create();
	}
	
	public String getUserToken() {
		return userToken;
	}
	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}
	
	public String getPassToken() {
		return passToken;
	}
	public void setPassToken(String passToken) {
		this.passToken = passToken;
	}
	
	public String getTokenInfoServiceUrl() {
		return tokenInfoServiceUrl;
	}
	public void setTokenInfoServiceUrl(String tokenInfoServiceUrl) {
		this.tokenInfoServiceUrl = tokenInfoServiceUrl;
	}
	
	
}
