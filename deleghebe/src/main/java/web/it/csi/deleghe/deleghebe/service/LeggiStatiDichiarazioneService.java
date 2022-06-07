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
import it.csi.deleghe.deleghebe.ws.model.StatoDichiarazione;
import it.csi.deleghe.deleghebe.ws.msg.LeggiStatiDichiarazione;
import it.csi.deleghe.deleghebe.ws.msg.LeggiStatiDichiarazioneResponse;

public class LeggiStatiDichiarazioneService extends BaseService<LeggiStatiDichiarazione, LeggiStatiDichiarazioneResponse> {
	
	@Inject
	private CodificheBean codificheBean;

	
	@Override
	protected void checkServiceParams(LeggiStatiDichiarazione req) {
	}
	
	@Override
	protected LeggiStatiDichiarazioneResponse execute(LeggiStatiDichiarazione req) {
		

		
		List<StatoDichiarazione> elencoStatoDichiarazione = codificheBean.ricercaStatiDichiarazioneDett();
		
		LeggiStatiDichiarazioneResponse res = new LeggiStatiDichiarazioneResponse();
		
		if(elencoStatoDichiarazione == null || elencoStatoDichiarazione.isEmpty()) {
			List<Errore> errori = new ArrayList<>();
			errori.add(new Errore("DA.R02","ATTENZIONE! Si e' verificato un errore interno del server"));
			res.setErrori(errori);
			res.setEsito(RisultatoCodice.FALLIMENTO);
			return res;
		}
		
		res.setEsito(RisultatoCodice.SUCCESSO);
		res.setElencoStatoDichiarazione(elencoStatoDichiarazione);
		return res;
	}

}
