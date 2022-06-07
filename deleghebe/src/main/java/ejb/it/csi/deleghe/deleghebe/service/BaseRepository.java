/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;

public class BaseRepository {

	protected void setCreazione(Object o, String loginOperazione) {
		Date now = new Date();

		try {
			invokeSingleParamMethod(o, "setDataCreazione", Date.class, now);
			invokeSingleParamMethod(o, "setDataModifica", Date.class, now);
			invokeSingleParamMethod(o, "setDataCancellazione", Date.class, null);
			invokeSingleParamMethod(o, "setLoginOperazione", String.class, loginOperazione);
		} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
			throw new IllegalArgumentException("Impossibile impostare parametri data creazione", e);
		}
	}

	protected void setModifica(Object o, String loginOperazione) {
		Date now = new Date();

		try {
			invokeSingleParamMethod(o, "setDataModifica", Date.class, now);
			invokeSingleParamMethod(o, "setLoginOperazione", String.class, loginOperazione);
		} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
			throw new IllegalArgumentException("Impossibile impostare parametri data modifica", e);
		}
	}

	protected void setCancellazione(Object o, String loginOperazione) {
		Date now = new Date();

		try {
			invokeSingleParamMethod(o, "setDataCancellazione", Date.class, now);
			invokeSingleParamMethod(o, "setLoginOperazione", String.class, loginOperazione);
		} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
			throw new IllegalArgumentException("Impossibile impostare parametri data cancellazione", e);
		}
	}

	private void invokeSingleParamMethod(Object o, String setterMethodName, Class valueClass, Object value)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		Class<? extends Object> c = o.getClass();
		Method method = c.getMethod(setterMethodName, valueClass);
		method.invoke(o, value);
	}
	
}
