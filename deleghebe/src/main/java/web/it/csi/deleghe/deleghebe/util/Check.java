/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.util;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import it.csi.deleghe.deleghebe.service.base.exception.ServiceException;
import it.csi.deleghe.deleghebe.ws.model.RisultatoCodice;
import it.csi.deleghe.deleghebe.ws.msg.ServiceResponse;

public class Check {

	public static void checkNotNull(Object obj, String codice, String descrizione) {
		check(() -> obj != null, codice, descrizione);
	}

	public static void checkListNotNull(List<? extends Object> obj, String codice, String descrizione) {
		obj.forEach(object -> {
			check(() -> object != null, codice, descrizione);
		});
	}

	public static void checkNotBlank(String str, String codice, String descrizione) {
		check(() -> StringUtils.isNotBlank(str), codice, descrizione);
	}

	public static boolean checkCf(String cf) {
		int c;

		int setdisp[] = { 1, 0, 5, 7, 9, 13, 15, 17, 19, 21, 2, 4, 18, 20, 11, 3, 6, 8, 12, 14, 16, 10, 22, 25, 24, 23 };
		if (cf.length() == 0)	return false;
		if (cf.length() != 16)	return false;
		String cf2 = cf.toUpperCase();
		for (int i = 0; i < 16; i++) {
			c = cf2.charAt(i);
			if (!(c >= '0' && c <= '9' || c >= 'A' && c <= 'Z')) return false;
		}
		int s = 0;
		for (int i = 1; i <= 13; i += 2) {
			c = cf2.charAt(i);
			if (c >= '0' && c <= '9')
				s = s + c - '0';
			else
				s = s + c - 'A';
		}
		for (int i = 0; i <= 14; i += 2) {
			c = cf2.charAt(i);
			if (c >= '0' && c <= '9')
				c = c - '0' + 'A';
			s = s + setdisp[c - 'A'];
		}
		
		return (s % 26 + 'A' == cf2.charAt(15));
	}

	public static void check(Condition condition, String codice, String descrizione) {
		if (!condition.isSatisfied()) {
			throw new ServiceException(codice, descrizione);
		}
	}

	public static void checkCondition(boolean mustBeTrue, String codice, String descrizione) {
		if (!mustBeTrue) {
			throw new ServiceException(codice, descrizione);
		}
	}

	public static void checkEsitoSuccesso(ServiceResponse serviceResponse, String codice, String descrizione) {
		checkNotNull(serviceResponse, codice, descrizione);
		checkCondition(RisultatoCodice.SUCCESSO == serviceResponse.getEsito(), codice, descrizione);
	}

	@FunctionalInterface
	public interface Condition {
		public boolean isSatisfied();
	}

}
