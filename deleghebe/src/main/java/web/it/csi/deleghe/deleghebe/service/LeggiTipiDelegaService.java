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
import it.csi.deleghe.deleghebe.ws.model.TipoDelega;
import it.csi.deleghe.deleghebe.ws.msg.LeggiTipiDelega;
import it.csi.deleghe.deleghebe.ws.msg.LeggiTipiDelegaResponse;

public class LeggiTipiDelegaService extends BaseService<LeggiTipiDelega, LeggiTipiDelegaResponse> {
	
	@Inject
	private CodificheBean codificheBean;

	
	@Override
	protected void checkServiceParams(LeggiTipiDelega req) {
	}
	
	@Override
	protected LeggiTipiDelegaResponse execute(LeggiTipiDelega req) {
		
		List<TipoDelega> elencoTipoDelega = codificheBean.ricercaTipiDelega();
		
		LeggiTipiDelegaResponse res = new LeggiTipiDelegaResponse();
		
		if(elencoTipoDelega == null || elencoTipoDelega.isEmpty()) {
			List<Errore> errori = new ArrayList<>();
			errori.add(new Errore("DA.R02","ATTENZIONE! Si e' verificato un errore interno del server"));
			res.setErrori(errori);
			res.setEsito(RisultatoCodice.FALLIMENTO);
			return res;
		}
		
		res.setEsito(RisultatoCodice.SUCCESSO);
		res.setElencoTipoDelega(elencoTipoDelega);
		return res;
	}

}
