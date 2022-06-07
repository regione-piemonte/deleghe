/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.schedule;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.hibernate.engine.jdbc.batch.spi.Batch;

import it.csi.deleghe.deleghebe.service.BatchBean;
import it.csi.deleghe.deleghebe.util.LogUtil;

@Stateless
public class BatchMinoriTimer {
	
	//Singelton
	@Inject
	private LogUtil log;

	@Inject
	private BatchBean batchBean;
	

	@Schedule(second = "0", minute = "0", hour = "4", persistent = false)

	public void batchGlobal() {
		final String METHOD_NAME = "batchGlobal";
		
		String hostName = getHostName();
		log.info(METHOD_NAME, "hostName: "+hostName);
		if(isServer1(hostName)) {
		
			log.info(METHOD_NAME,"batch enabled on this host: "+hostName);
					
			int status = 1;
			final Calendar gCal = GregorianCalendar.getInstance();
			log.info(METHOD_NAME, "Esecuzione BatchMinoriTimer avviata");
			if (status > 0) {
				String msg = "Esecuzione scaduteMinoriDelegheBatch... ";
				status = batchBean.scaduteMinoriDelegheBatch(gCal);
				if (status > 0) msg += "completata!";
				log.info(METHOD_NAME, msg);
			}
	
			log.info(METHOD_NAME, "Esecuzione BatchMinoriTimer " + ((status > 0)? "completata" : "annullata"));
		} else {
			log.info(METHOD_NAME,"batch disabled on this host: "+hostName);
		}
	}
	
	
	private static boolean isServer1(String hostname) {
		return hostname.matches("xishan|Muroran|xyz|xyz");
	}
	
	private static String getHostName() {
		String hostname = "Unknown";

		try {
			InetAddress addr;
			addr = InetAddress.getLocalHost();
			hostname = addr.getHostName();
		} catch (UnknownHostException ex) {
			Logger log = Logger.getLogger(Batch.class);
			log.error("BaseBatch: hostName can not be resolved");
		}
		return hostname;
	}
	
	
	public static void main(String[] args) {		
	    boolean b = "xyz".matches("xishan|Muroran|xyz|xyz");
	}

}
