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
import it.csi.deleghe.deleghebe.ws.model.InformativaConsensi;
import it.csi.deleghe.deleghebe.ws.msg.LeggiInformativaConsensi;
import it.csi.deleghe.deleghebe.ws.msg.LeggiInformativaConsensiResponse;
import it.csi.deleghe.deleghebe.ws.msg.LeggiStatiDichiarazione;
import it.csi.deleghe.deleghebe.ws.msg.LeggiStatiDichiarazioneResponse;

public class LeggiInformativaConsensoService extends BaseService<LeggiInformativaConsensi, LeggiInformativaConsensiResponse> {
	
	@Inject
	private CodificheBean codificheBean;

	
	@Override
	protected void checkServiceParams(LeggiInformativaConsensi req) {
	}
	
	@Override
	protected LeggiInformativaConsensiResponse execute(LeggiInformativaConsensi req) {
		
		List<InformativaConsensi> elencoInformativaConsensi = codificheBean.ricercaInformativaConsensi();
		
		LeggiInformativaConsensiResponse res = new LeggiInformativaConsensiResponse();
		
		if(elencoInformativaConsensi == null || elencoInformativaConsensi.isEmpty()) {
			List<Errore> errori = new ArrayList<>();
			errori.add(new Errore("DA.R02","ATTENZIONE! Si e' verificato un errore interno del server"));
			res.setErrori(errori);
			res.setEsito(RisultatoCodice.FALLIMENTO);
			return res;
		}
		
		res.setEsito(RisultatoCodice.SUCCESSO);
		res.setElencoInformativaConsensi(elencoInformativaConsensi);
		return res;
	}

}
