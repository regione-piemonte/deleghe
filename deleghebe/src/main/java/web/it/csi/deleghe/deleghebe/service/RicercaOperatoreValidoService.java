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

public class RicercaOperatoreValidoService extends BaseService<RicercaCfOperatore, RicercaCfOperatoreResponse>{
	
	@Inject
	private OperatoreBean operatoreBean;
	
	@Override
	protected RicercaCfOperatoreResponse execute(RicercaCfOperatore req) {
		RicercaCfOperatoreResponse res = new RicercaCfOperatoreResponse();
		
		String asl=operatoreBean.ricercaOperatoreValidoConAsl(req.getCf());		
		if(asl!=null) {
			res.setAsl(asl);
			res.setExists(true);
			res.setEsito(RisultatoCodice.SUCCESSO);
		}else {
			res.setAsl(null);
			res.setExists(false);
			res.setEsito(RisultatoCodice.FALLIMENTO);
		}		
		
		return res;
	}

	@Override
	protected void checkServiceParams(RicercaCfOperatore req) {
		// TODO Auto-generated method stub
		
	}
	

}
