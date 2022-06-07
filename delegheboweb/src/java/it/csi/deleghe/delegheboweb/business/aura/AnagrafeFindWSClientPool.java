/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.delegheboweb.business.aura;

import it.csi.deleghe.delegheboweb.business.aura.find.model.AnagrafeFindSoap;
import it.csi.deleghe.delegheboweb.integration.facade.ClientPasswordCallback;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.ws.security.handler.WSHandlerConstants;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class AnagrafeFindWSClientPool {

	String auraUser;
	String auraPassword;
	String auraURL;

	public AnagrafeFindSoap getClient() {
		ClientPasswordCallback callback=new ClientPasswordCallback();
		callback.setUserAura(auraUser);
		callback.setPassAura(auraPassword);

		Map<String, Object> outProps = new HashMap<String, Object>();
		outProps.put(WSHandlerConstants.ACTION, "UsernameToken");
		outProps.put(WSHandlerConstants.PASSWORD_TYPE, "PasswordText");
		outProps.put(WSHandlerConstants.USER, auraUser);
		outProps.put(WSHandlerConstants.PW_CALLBACK_REF, callback);

		WSS4JOutInterceptor wssOut = new WSS4JOutInterceptor(outProps);

		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(AnagrafeFindSoap.class);
		factory.setAddress(auraURL);
		factory.getOutInterceptors().add(wssOut);

		return (AnagrafeFindSoap) factory.create();
	}

	public void setAuraUser(String auraUser) {
		this.auraUser = auraUser;
	}

	public void setAuraPassword(String auraPassword) {
		this.auraPassword = auraPassword;
	}

	public void setAuraURL(String auraURL) {
		this.auraURL = auraURL;
	}

	public String getAuraUser() {
		return auraUser;
	}

	public String getAuraPassword() {
		return auraPassword;
	}

	public String getAuraURL() {
		return auraURL;
	}
}