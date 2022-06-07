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
import it.csi.deleghe.deleghebe.ws.model.Dichiarazione;
import it.csi.deleghe.deleghebe.ws.model.DichiarazioneDettaglio;
import it.csi.deleghe.deleghebe.ws.model.Errore;
import it.csi.deleghe.deleghebe.ws.model.RisultatoCodice;
import it.csi.deleghe.deleghebe.ws.msg.GetDettaglioDichiarazione;
import it.csi.deleghe.deleghebe.ws.msg.GetDettaglioDichiarazioneResponse;

public class GetDettaglioDichiarazioneService extends BaseService<GetDettaglioDichiarazione, GetDettaglioDichiarazioneResponse> {

	@Inject
	private DichiarazioneBean dichiarazioneBean;
	
	
	@Override
	protected void checkServiceParams(GetDettaglioDichiarazione req) {
		checkNotNull(req.getDichiarazione(), "DA.V02", "Dichiarazione non valorizzata.");
		checkNotNull(req.getDichiarazione().getUuid(), "DA.V01", "UUID non valorizzato.");
	}
	
	@Override
	protected GetDettaglioDichiarazioneResponse execute(GetDettaglioDichiarazione req) {
		
		Dichiarazione dichiarazione = dichiarazioneBean.ricercaDichiarazioneByUUID(req.getDichiarazione().getUuid());
		
		GetDettaglioDichiarazioneResponse res = new GetDettaglioDichiarazioneResponse();
		res.setEsito(RisultatoCodice.SUCCESSO);
		
		if(dichiarazione != null) {
			res.setDichiarazione(dichiarazione);
		}
		else {
			List<Errore> errori = new ArrayList<>();
			errori.add(new Errore("DA.R02","ATTENZIONE! Si e' verificato un errore interno del server"));
			res.setErrori(errori);
			res.setEsito(RisultatoCodice.FALLIMENTO);
		}
		
		return res;
	}

}
