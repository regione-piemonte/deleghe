/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import it.csi.deleghe.deleghebe.service.base.BaseService;
import it.csi.deleghe.deleghebe.ws.model.Documento;
import it.csi.deleghe.deleghebe.ws.model.DocumentoFile;
import it.csi.deleghe.deleghebe.ws.model.Errore;
import it.csi.deleghe.deleghebe.ws.model.RisultatoCodice;
import it.csi.deleghe.deleghebe.ws.msg.RicercaDichiarazioni;
import it.csi.deleghe.deleghebe.ws.msg.RicercaDichiarazioniResponse;
import it.csi.deleghe.deleghebe.ws.msg.RicercaFile;
import it.csi.deleghe.deleghebe.ws.msg.RicercaFileResponse;

public class RicercaFileService extends BaseService<RicercaFile, RicercaFileResponse>{
	
	@Inject
	private DichiarazioneBean dichiarazioneBean;
	
	
	@Override
	protected void checkServiceParams(RicercaFile req) {
	}
	
	@Override
	protected RicercaFileResponse execute(RicercaFile req) {	
		RicercaFileResponse res = new RicercaFileResponse();
				

		DocumentoFile documentoFile =dichiarazioneBean.cercaFile(req.getFileId());
				
		
		res.setEsito(RisultatoCodice.SUCCESSO);
		
		if(documentoFile != null) {
			res.setDocumentoFile(documentoFile);
		}
		else {
			List<Errore> errori = new ArrayList<>();
			errori.add(new Errore("DA.R02","Non esistono occorrenze rispetto alla ricerca su deleTFile"));
			res.setErrori(errori);
		}
		
		return res;
	}

}
