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
import it.csi.deleghe.deleghebe.ws.msg.InserisciCittadino;
import it.csi.deleghe.deleghebe.ws.msg.InserisciCittadinoResponse;

public class InserisciCittadinoService extends BaseService<InserisciCittadino, InserisciCittadinoResponse> {

	@Inject
	private CittadinoBean cittadinoBean;
	
	@Override
	protected void checkServiceParams(InserisciCittadino req) {
		checkNotNull(req.getCittadino(), "DA.E48", "Cittadino non valorizzato.");
		checkNotNull(req.getCittadino().getCodiceFiscale(), "DA.E17", "Codice fiscale non valorizzato.");
		checkNotNull(req.getCittadino().getCognome(), "DA.E43", "Cognome non valorizzato.");
		checkNotNull(req.getCittadino().getNome(), "DA.E44", "Nome cittadino non valorizzato.");
		checkNotNull(req.getCittadino().getSesso(), "DA.E47", "Sesso cittadino non valorizzato");
		checkNotNull(req.getCittadino().getDataNascita(), "DA.E45", "Data di nascita cittadino non valorizzato.");
		checkNotNull(req.getCittadino().getComuneNascita(), "DA.E46", "Luogo di nascita cittadino non valorizzato.");
	}
	
	@Override
	protected InserisciCittadinoResponse execute(InserisciCittadino req) {
		Cittadino cittadino= cittadinoBean.inserisciCittadino(req.getCittadino(), req.getRichiedente().getCodiceFiscale());
		
		InserisciCittadinoResponse res = new InserisciCittadinoResponse();

		res.setCittadino(cittadino);
		res.setEsito(RisultatoCodice.SUCCESSO);
		return res;
	}

	

}
