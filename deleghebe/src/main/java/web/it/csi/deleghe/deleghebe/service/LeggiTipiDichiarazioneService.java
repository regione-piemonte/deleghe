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
import it.csi.deleghe.deleghebe.ws.model.TipoDichiarazione;
import it.csi.deleghe.deleghebe.ws.msg.LeggiStatiDichiarazioneResponse;
import it.csi.deleghe.deleghebe.ws.msg.LeggiTipiDichiarazione;
import it.csi.deleghe.deleghebe.ws.msg.LeggiTipiDichiarazioneResponse;

public class LeggiTipiDichiarazioneService extends BaseService<LeggiTipiDichiarazione, LeggiTipiDichiarazioneResponse> {
	
	@Inject
	private CodificheBean codificheBean;

	
	@Override
	protected void checkServiceParams(LeggiTipiDichiarazione req) {
	}
	
	@Override
	protected LeggiTipiDichiarazioneResponse execute(LeggiTipiDichiarazione req) {
		
		List<TipoDichiarazione> elencoTipoDichiarazione = codificheBean.ricercaTipiDichiarazione();
		
		LeggiTipiDichiarazioneResponse res = new LeggiTipiDichiarazioneResponse();
		
		if(elencoTipoDichiarazione == null || elencoTipoDichiarazione.isEmpty()) {
			List<Errore> errori = new ArrayList<>();
			errori.add(new Errore("DA.R02","ATTENZIONE! Si e' verificato un errore interno del server"));
			res.setErrori(errori);
			res.setEsito(RisultatoCodice.FALLIMENTO);
			return res;
		}
		
		res.setEsito(RisultatoCodice.SUCCESSO);
		res.setElencoTipoDichiarazione(elencoTipoDichiarazione);
		return res;
	}

}
