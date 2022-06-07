/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service;

import javax.inject.Inject;

import it.csi.deleghe.deleghebe.service.base.BaseService;
import it.csi.deleghe.deleghebe.ws.model.RisultatoCodice;
import it.csi.deleghe.deleghebe.ws.msg.RicercaCfOperatore;
import it.csi.deleghe.deleghebe.ws.msg.RicercaCfOperatoreResponse;

public class RicercaCfOperatoreService extends BaseService<RicercaCfOperatore, RicercaCfOperatoreResponse> {

	@Inject
	private OperatoreBean operatoreBean;
	
	@Override
	protected RicercaCfOperatoreResponse execute(RicercaCfOperatore req) {
		
		boolean ret=operatoreBean.ricercaCFOperatore(req.getCf());
		
		RicercaCfOperatoreResponse res = new RicercaCfOperatoreResponse();
		
		res.setExists(ret);
		
		res.setEsito(RisultatoCodice.SUCCESSO);
		
		return res;
	}

	@Override
	protected void checkServiceParams(RicercaCfOperatore req) {
		// TODO Auto-generated method stub
		
	}

	

}
