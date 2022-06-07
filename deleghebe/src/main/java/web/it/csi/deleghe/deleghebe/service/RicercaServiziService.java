/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service;

import java.util.List;

import javax.inject.Inject;

import it.csi.deleghe.deleghebe.service.base.BaseService;
import it.csi.deleghe.deleghebe.ws.model.RisultatoCodice;
import it.csi.deleghe.deleghebe.ws.model.Servizio;
import it.csi.deleghe.deleghebe.ws.msg.RicercaServizi;
import it.csi.deleghe.deleghebe.ws.msg.RicercaServiziResponse;

public class RicercaServiziService extends BaseService<RicercaServizi, RicercaServiziResponse> {
	
	@Inject
	private ServizioBean servizioBean;

	
	@Override
	protected void checkServiceParams(RicercaServizi req) {
	}
	
	@Override
	protected RicercaServiziResponse execute(RicercaServizi req) {
		
		List<Servizio> serviziRicercati = servizioBean.ricercaServizi(req.getServizio());
		
		RicercaServiziResponse res = new RicercaServiziResponse();
		
		res.setEsito(RisultatoCodice.SUCCESSO);
		res.setServizi(serviziRicercati);
		
		return res;
	}

}
