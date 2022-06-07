/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service;

import static it.csi.deleghe.deleghebe.util.Check.checkNotNull;

import javax.inject.Inject;

import it.csi.deleghe.deleghebe.service.base.BaseService;
import it.csi.deleghe.deleghebe.ws.model.RisultatoCodice;
import it.csi.deleghe.deleghebe.ws.model.Servizio;
import it.csi.deleghe.deleghebe.ws.msg.AggiornaServizio;
import it.csi.deleghe.deleghebe.ws.msg.AggiornaServizioResponse;

public class AggiornaServizioService extends BaseService<AggiornaServizio, AggiornaServizioResponse> {
	
	@Inject
	private ServizioBean servizioBean;

	@Override
	protected void checkServiceParams(AggiornaServizio req) {
		checkNotNull(req.getServizio(), "DA.E54", "Servizio non valorizzato.");
		checkNotNull(req.getServizio().getCodice(), "DA.E55", "Codice servizio non valorizzato.");
	}
	
	@Override
	protected AggiornaServizioResponse execute(AggiornaServizio req) {
		
		Servizio servizioAggiornato= servizioBean.aggiornaServizio(req.getServizio(), req.getRichiedente().getCodiceFiscale());
		
		AggiornaServizioResponse res = new AggiornaServizioResponse();
		
		res.setServizio(servizioAggiornato);
		res.setEsito(RisultatoCodice.SUCCESSO);
		
		return res;
	}

	



}
