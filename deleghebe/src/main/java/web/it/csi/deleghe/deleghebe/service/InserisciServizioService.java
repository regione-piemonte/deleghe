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
import it.csi.deleghe.deleghebe.ws.msg.InserisciServizio;
import it.csi.deleghe.deleghebe.ws.msg.InserisciServizioResponse;

public class InserisciServizioService extends BaseService<InserisciServizio, InserisciServizioResponse> {
	
	@Inject
	private ServizioBean servizioBean;

	
	@Override
	protected void checkServiceParams(InserisciServizio req) {
		checkNotNull(req.getServizio(), "DA.E54", "Servizio non valorizzato.");
		checkNotNull(req.getServizio().getCodice(), "DA.E55", "Codice servizio non valorizzato.");
		checkNotNull(req.getServizio().getDataInizioValidita(), "DA.E56", "Data inzio validità non valorizzata.");
	}
	
	@Override
	protected InserisciServizioResponse execute(InserisciServizio req) {
		
		Servizio servizioInserito= servizioBean.inserisciServizio(req.getServizio(), req.getRichiedente().getCodiceFiscale());
		InserisciServizioResponse res = new InserisciServizioResponse();
		
		res.setServizio(servizioInserito);
		res.setEsito(RisultatoCodice.SUCCESSO);		
		
		return res;
	}

	



}
