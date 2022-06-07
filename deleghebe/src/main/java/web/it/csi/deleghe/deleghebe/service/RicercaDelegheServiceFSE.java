/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import it.csi.deleghe.deleghebe.service.base.BaseService;
import it.csi.deleghe.deleghebe.ws.model.Errore;
import it.csi.deleghe.deleghebe.ws.model.RisultatoCodice;
import it.csi.deleghe.deleghebe.ws.msg.RicercaDeleghe;
import it.csi.deleghe.deleghebe.ws.msg.RicercaDelegheFSEResponse;

public class RicercaDelegheServiceFSE extends BaseService<RicercaDeleghe, RicercaDelegheFSEResponse>{
	
	@Inject
	private DelegaBean delegaBean;
	
	
	@Override
	protected void checkServiceParams(RicercaDeleghe req) {
	}
	
	@Override
	protected RicercaDelegheFSEResponse execute(RicercaDeleghe req) {

		
		List<String> deleghe = delegaBean.ricercaDelegheFSE(req.getDelega(), req.getCodiciServizio(),req.getStatiDelega());

		
		RicercaDelegheFSEResponse res = new RicercaDelegheFSEResponse();		
		res.setEsito(RisultatoCodice.SUCCESSO);
		
		res.setDeleghe(deleghe);
		return res;
	}

}
