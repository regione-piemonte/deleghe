/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import it.csi.deleghe.deleghebe.model.DeleDServizio;
import it.csi.deleghe.deleghebe.util.LogUtil;
import it.csi.deleghe.deleghebe.ws.model.Servizio;

public class ServiziBean {
	
	@Inject
	protected LogUtil log;
	
	@Inject 
	private DeleDServizioRepository deleDServizioRepository;
	
	@Inject
	private DeleTServizioParametroRepository servizioParametroRepository;
	
	public List<Servizio> ricercaServiziBySerCod(String serCod) {
		List<Servizio> result = new ArrayList<Servizio>();
		Servizio servizio = new Servizio();
		DeleDServizio deleDServizio = deleDServizioRepository.ricercaServiziBySerCod(serCod);
		
		String validitaServizio = getValiditaServizio(deleDServizio.getSerId());
		
		servizio.setValiditaServizio(validitaServizio);
		servizio.setSerId(deleDServizio.getSerId());
		servizio.setCodice(serCod);
		
		result.add(servizio);
		
		return result;
	}
	
	public String getValiditaServizio(Integer serId) {
		String validitaServizio = servizioParametroRepository.getValiditaServizio(serId);

		return validitaServizio;
	}

}
