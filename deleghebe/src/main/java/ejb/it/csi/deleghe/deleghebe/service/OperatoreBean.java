/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import it.csi.deleghe.deleghebe.model.DeleTOperatore;

@Stateless
public class OperatoreBean {

	@Inject
	private DeleTOperatoreRepository deleTOperatoreRepository;

	public OperatoreBean() {
		// TODO Auto-generated constructor stub
	}


	public boolean ricercaCFOperatore(String cf) {

		DeleTOperatore deleTOperatore = deleTOperatoreRepository.ricercaCFOperatore(cf);

		if(deleTOperatore!=null)
			return true;

		return false;
	}
	
	public String ricercaOperatoreValidoConAsl(String codiceFiscale) {

		String aslOperatore = deleTOperatoreRepository.ricercaOperatoreValidoConAsl(codiceFiscale);

		return aslOperatore;
	}

}
