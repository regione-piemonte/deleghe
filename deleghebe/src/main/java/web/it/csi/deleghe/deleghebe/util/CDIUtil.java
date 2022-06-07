/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.util;

import java.lang.annotation.Annotation;
import java.util.Set;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.AmbiguousResolutionException;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class CDIUtil {
	
	private static BeanManager beanManager; 
	
	
	@SuppressWarnings("unchecked")
	public static <T> T resolve(Class<T> beanInterface, Annotation... qualifiers) {
		BeanManager beanManager = getBeanManager();
		
		Set<Bean<?>> beans = beanManager.getBeans(beanInterface, qualifiers);
		
		Bean<T> bean;
		try {
			bean = (Bean<T>) beanManager.resolve(beans);
		} catch (AmbiguousResolutionException  are) {
			throw new IllegalStateException("Impossibile risolvere bean: "+ beanInterface.getSimpleName(), are);
		}
		
		if(bean == null) {
			throw new IllegalStateException("Impossibile risolvere bean: "+ beanInterface.getSimpleName());
		}
		CreationalContext<T> creationalContext = beanManager.createCreationalContext(bean);
		if (creationalContext == null) {
			throw new IllegalStateException("Impossibile ottenere  Creational Context per il bean: "+ beanInterface.getSimpleName());
		}
		
		T instance = (T) beanManager.getReference(bean, beanInterface, creationalContext);
		return instance;
	}

	private static BeanManager getBeanManager() {
		if(beanManager != null) {
			return beanManager;
		}
		try {
			beanManager = (BeanManager) InitialContext.doLookup("java:comp/BeanManager");
		} catch (NamingException e) {
			throw new IllegalStateException("Impossibile trovare BeanManager in JNDI.", e);
		} catch (Exception e) {
			throw new IllegalStateException("Impossibile ottenere BeanManager. ", e);
		}
		return beanManager;
	}
}
