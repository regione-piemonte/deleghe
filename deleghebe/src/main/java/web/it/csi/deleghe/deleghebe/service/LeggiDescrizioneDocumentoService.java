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
import it.csi.deleghe.deleghebe.ws.model.DocumentoTipo;
import it.csi.deleghe.deleghebe.ws.msg.LeggiDescrizioneDocumento;
import it.csi.deleghe.deleghebe.ws.msg.LeggiDescrizioneDocumentoResponse;

public class LeggiDescrizioneDocumentoService extends BaseService<LeggiDescrizioneDocumento, LeggiDescrizioneDocumentoResponse>{
	
	@Inject
	private CodificheBean codificheBean;

	
	@Override
	protected void checkServiceParams(LeggiDescrizioneDocumento req) {
	}
	
	@Override
	protected LeggiDescrizioneDocumentoResponse execute(LeggiDescrizioneDocumento req) {
		
		List<DocumentoTipo> elencoDocumentoTipo = codificheBean.ricercaDescrizioneDocumenti();
		
		LeggiDescrizioneDocumentoResponse res = new LeggiDescrizioneDocumentoResponse();
		
		if(elencoDocumentoTipo == null || elencoDocumentoTipo.isEmpty()) {
			List<Errore> errori = new ArrayList<>();
			errori.add(new Errore("DA.R02","ATTENZIONE! Si e' verificato un errore interno del server"));
			res.setErrori(errori);
			res.setEsito(RisultatoCodice.FALLIMENTO);
			return res;
		}
		
		res.setEsito(RisultatoCodice.SUCCESSO);
		res.setElencoDocumentoTipo(elencoDocumentoTipo);
		return res;
	}

}
