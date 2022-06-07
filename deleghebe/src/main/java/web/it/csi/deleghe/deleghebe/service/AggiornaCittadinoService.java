/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service;

import static it.csi.deleghe.deleghebe.util.Check.checkNotNull;

import javax.inject.Inject;

import it.csi.deleghe.deleghebe.service.base.BaseService;
import it.csi.deleghe.deleghebe.ws.model.Cittadino;
import it.csi.deleghe.deleghebe.ws.model.RisultatoCodice;
import it.csi.deleghe.deleghebe.ws.msg.AggiornaCittadino;
import it.csi.deleghe.deleghebe.ws.msg.AggiornaCittadinoResponse;

public class AggiornaCittadinoService extends BaseService<AggiornaCittadino, AggiornaCittadinoResponse> {

	@Inject
	private CittadinoBean cittadinoBean;
	
	@Override
	protected void checkServiceParams(AggiornaCittadino req) {
		checkNotNull(req.getCittadino(), "DA.E48", "Cittadino non valorizzato.");
		checkNotNull(req.getCittadino().getCodiceFiscale(), "DA.E17", "Codice fiscale non valorizzato.");
	}
	
	
	@Override
	protected AggiornaCittadinoResponse execute(AggiornaCittadino req) {
		
		Cittadino cittadino=cittadinoBean.aggiornaCittadino(req.getCittadino(), req.getRichiedente().getCodiceFiscale());
		
		AggiornaCittadinoResponse res = new AggiornaCittadinoResponse();

		res.setCittadino(cittadino);
		res.setEsito(RisultatoCodice.SUCCESSO);
		return res;
	}

	

}
