/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import it.csi.deleghe.deleghebe.model.DeleDParametro;
import it.csi.deleghe.deleghebe.service.DeleDParametroRepository;

public class ParamsUtil {
	private static final Map<String,DeleDParametro> params = new HashMap<>();
	
	@Inject
	private DeleDParametroRepository dParametroRepository;
	
	public String getParametro (String key) {
		if (params.isEmpty()) {
			final List<DeleDParametro> paramList = dParametroRepository.ricercaParametri();
			for (DeleDParametro deleDParametro : paramList) {
				params.put(deleDParametro.getParCod(), deleDParametro);
			}
		}
		DeleDParametro dParametro = params.get(key);
		return (dParametro != null)? dParametro.getParValore() : null;
	}

	public void setParametro (String key, String value) {
		if (params.containsKey(key)) {
			DeleDParametro dParametro = params.get(key);
			dParametro.setParValore(value);
			dParametroRepository.aggiornaParametro(dParametro);
			params.put(key, dParametro);
		}
	}

}
