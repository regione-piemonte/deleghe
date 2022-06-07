/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service;

import it.csi.deleghe.deleghebe.service.base.BaseService;
import it.csi.deleghe.deleghebe.ws.model.RisultatoCodice;
import it.csi.deleghe.deleghebe.ws.msg.IsAlive;
import it.csi.deleghe.deleghebe.ws.msg.IsAliveResponse;

public class IsAliveService extends BaseService<IsAlive, IsAliveResponse> {

	@Override
	protected IsAliveResponse execute(IsAlive req) {

		IsAliveResponse res = new IsAliveResponse();

		res.setEsito(RisultatoCodice.SUCCESSO);

		res.setOut("Servizio vivo e vegetale!!!");
		return res;
	}

	@Override
	protected void checkServiceParams(IsAlive req) {
		
	}

}
