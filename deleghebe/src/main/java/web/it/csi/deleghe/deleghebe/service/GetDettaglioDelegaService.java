/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service;

import static it.csi.deleghe.deleghebe.util.Check.checkNotNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import it.csi.deleghe.deleghebe.service.base.BaseService;
import it.csi.deleghe.deleghebe.ws.model.Delega;
import it.csi.deleghe.deleghebe.ws.model.Errore;
import it.csi.deleghe.deleghebe.ws.model.RisultatoCodice;
import it.csi.deleghe.deleghebe.ws.msg.GetDettaglioDelega;
import it.csi.deleghe.deleghebe.ws.msg.GetDettaglioDelegaResponse;
import it.csi.deleghe.deleghebe.ws.msg.RicercaDeleghe;
import it.csi.deleghe.deleghebe.ws.msg.RicercaDelegheResponse;


public class GetDettaglioDelegaService extends BaseService<GetDettaglioDelega, GetDettaglioDelegaResponse> {

	@Inject
	private DelegaBean delegaBean;
	
	
	@Override
	protected void checkServiceParams(GetDettaglioDelega req) {
		checkNotNull(req.getDelega(), "DA.V02", "Delega non valorizzata.");
		checkNotNull(req.getDelega().getUuid(), "DA.V01", "UUID non valorizzato.");
	}
	
	@Override
	protected GetDettaglioDelegaResponse execute(GetDettaglioDelega req) {
		
		Delega delega = delegaBean.ricercaDettaglioDelegaByUUD(req.getDelega());
				
		GetDettaglioDelegaResponse res = new GetDettaglioDelegaResponse();
		res.setEsito(RisultatoCodice.SUCCESSO);
		
		if(delega == null) {
			List<Errore> errori = new ArrayList<>();
			errori.add(new Errore("DA.R02","ATTENZIONE! Si e' verificato un errore interno del server"));
			res.setErrori(errori);
			res.setEsito(RisultatoCodice.FALLIMENTO);
			return res;
		}
		
		res.setDelega(delega);
		return res;
	}

}
