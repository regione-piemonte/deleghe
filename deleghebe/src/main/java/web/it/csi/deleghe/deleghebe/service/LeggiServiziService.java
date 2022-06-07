/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import it.csi.deleghe.deleghebe.service.base.BaseService;
import it.csi.deleghe.deleghebe.ws.model.Errore;
import it.csi.deleghe.deleghebe.ws.model.RisultatoCodice;
import it.csi.deleghe.deleghebe.ws.model.Servizio;
import it.csi.deleghe.deleghebe.ws.msg.LeggiServizi;
import it.csi.deleghe.deleghebe.ws.msg.LeggiServiziResponse;

public class LeggiServiziService  extends BaseService<LeggiServizi, LeggiServiziResponse> {
	
	@Inject
	private ServiziBean serviziBean;

	@Override
	protected LeggiServiziResponse execute(LeggiServizi req) {		
		List<Servizio> elencoServizio = serviziBean.ricercaServiziBySerCod(req.getSerCod());
		
		
		LeggiServiziResponse res = new LeggiServiziResponse();
		
		if(elencoServizio == null || elencoServizio.isEmpty()) {
			List<Errore> errori = new ArrayList<>();
			errori.add(new Errore("DA.R02","ATTENZIONE! Si e' verificato un errore interno del server"));
			res.setErrori(errori);
			res.setEsito(RisultatoCodice.FALLIMENTO);
			return res;
		}
		
		res.setEsito(RisultatoCodice.SUCCESSO);
		res.setElencoServizio(elencoServizio);

		return res;
	}
	
	

	@Override
	protected void checkServiceParams(LeggiServizi req) {
		// TODO Auto-generated method stub
		
	}

}
