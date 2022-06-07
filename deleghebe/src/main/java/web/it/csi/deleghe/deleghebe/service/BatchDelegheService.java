/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service;

import javax.inject.Inject;

import it.csi.deleghe.deleghebe.service.base.BaseService;
import it.csi.deleghe.deleghebe.ws.model.RisultatoCodice;
import it.csi.deleghe.deleghebe.ws.msg.BatchDeleghe;
import it.csi.deleghe.deleghebe.ws.msg.BatchDelegheResponse;

public class BatchDelegheService extends BaseService<BatchDeleghe, BatchDelegheResponse> {

	@Inject
	private BatchBean batchBean;

	@Override
	protected void checkServiceParams(BatchDeleghe req) {
		// Nessun parametro da controllare
	}
	
	@Override
	protected BatchDelegheResponse execute(BatchDeleghe req) {
		BatchDelegheResponse res = new BatchDelegheResponse();

		try {
			int[] counters = batchBean.scadenzaDeleghe();
			res.setCntInScadenza(counters[0]);
			res.setCntScadute(counters[1]);
		
			res.setEsito(RisultatoCodice.SUCCESSO);
		} catch (Exception e) {
			res.setEsito(RisultatoCodice.FALLIMENTO);
		}

		return res;
	}

}
