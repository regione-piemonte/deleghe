/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.delegheboweb.util;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class AppServletContextListener implements ServletContextListener {

	private static ServletContext sc;
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		AppServletContextListener.sc = sce.getServletContext();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

	public static ServletContext getServletContext() {
		return sc;
	}
}
