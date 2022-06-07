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
import it.csi.deleghe.deleghebe.ws.model.StatoDelega;
import it.csi.deleghe.deleghebe.ws.msg.LeggiStatiDelega;
import it.csi.deleghe.deleghebe.ws.msg.LeggiStatiDelegaResponse;

public class LeggiStatiDelegaService extends BaseService<LeggiStatiDelega, LeggiStatiDelegaResponse> {
	
	@Inject
	private CodificheBean codificheBean;

	
	@Override
	protected void checkServiceParams(LeggiStatiDelega req) {
	}
	
	@Override
	protected LeggiStatiDelegaResponse execute(LeggiStatiDelega req) {
		
		List<StatoDelega> elencoStatoDelega = codificheBean.ricercaStatiDelega();
		
		LeggiStatiDelegaResponse res = new LeggiStatiDelegaResponse();
		
		if(elencoStatoDelega == null || elencoStatoDelega.isEmpty()) {
			List<Errore> errori = new ArrayList<>();
			errori.add(new Errore("DA.R02","ATTENZIONE! Si e' verificato un errore interno del server"));
			res.setErrori(errori);
			res.setEsito(RisultatoCodice.FALLIMENTO);
			return res;
		}
		
		res.setEsito(RisultatoCodice.SUCCESSO);
		res.setElencoStatoDelega(elencoStatoDelega);
		return res;
	}

}
