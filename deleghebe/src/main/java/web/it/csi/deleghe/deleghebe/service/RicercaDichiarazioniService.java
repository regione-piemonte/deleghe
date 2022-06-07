/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import it.csi.deleghe.deleghebe.service.base.BaseService;
import it.csi.deleghe.deleghebe.ws.model.Dichiarazione;
import it.csi.deleghe.deleghebe.ws.model.DichiarazioneDettaglio;
import it.csi.deleghe.deleghebe.ws.model.Errore;
import it.csi.deleghe.deleghebe.ws.model.RisultatoCodice;
import it.csi.deleghe.deleghebe.ws.msg.RicercaDichiarazioni;
import it.csi.deleghe.deleghebe.ws.msg.RicercaDichiarazioniResponse;

public class RicercaDichiarazioniService extends BaseService<RicercaDichiarazioni, RicercaDichiarazioniResponse> {

	@Inject
	private DichiarazioneBean dichiarazioneBean;
	
	
	@Override
	protected void checkServiceParams(RicercaDichiarazioni req) {
	}
	
	@Override
	protected RicercaDichiarazioniResponse execute(RicercaDichiarazioni req) {
		
		List<Dichiarazione> listDichiarazioni=new ArrayList<>();
		
		if(req.getDichiarazione() !=null && req.getDichiarazione().getDettagli() !=null  && !req.getDichiarazione().getDettagli().isEmpty()) {
			

			
			for (DichiarazioneDettaglio dichDettaglio : req.getDichiarazione().getDettagli()) {
				
				String cf1=null;
				if(dichDettaglio.getCittadino1() != null) {
					if(dichDettaglio.getCittadino1().getCodiceFiscale() != null) {
						cf1=dichDettaglio.getCittadino1().getCodiceFiscale();
					}
					
				}
				String ro1=null;
				if(dichDettaglio.getRuoloCittadino1()!=null) {
					if(dichDettaglio.getRuoloCittadino1().getCodice() != null) {
						ro1=dichDettaglio.getRuoloCittadino1().getCodice();
					}
				}
				String cf2=null;
				if(dichDettaglio.getCittadino2() != null) {
					if(dichDettaglio.getCittadino2().getCodiceFiscale() != null) {
						cf2=dichDettaglio.getCittadino2().getCodiceFiscale();
					}
					
				}
				String ro2=null;
				if(dichDettaglio.getRuoloCittadino2() != null) {
					if(dichDettaglio.getRuoloCittadino2().getCodice() != null) {
						ro2=dichDettaglio.getRuoloCittadino2().getCodice();
					}
				}
				
				
				List<Dichiarazione> dichiarazioniXDettaglio = dichiarazioneBean.ricercaDichiarazioni(req.getDichiarazione().getUuid(),
						cf1,
						ro1,
						cf2,
						ro2,
						req.getTipiDichiarazione(),
						req.getModiDichiarazione(),
						req.getStatiDichiarazione(),
						req.getDataInserimentoDa(),
						req.getDataInserimentoA(),
						req.getRuoloCittadino(),
						req.getCittadinoCF());
				
				listDichiarazioni.addAll(dichiarazioniXDettaglio);
			}
		}
		else {
			List<Dichiarazione> dichiarazioni = dichiarazioneBean.ricercaDichiarazioni(req.getDichiarazione().getUuid(),
					null,
					null,
					null,
					null,
					req.getTipiDichiarazione(),
					req.getModiDichiarazione(),
					req.getStatiDichiarazione(),
					req.getDataInserimentoDa(),
					req.getDataInserimentoA(),
					req.getRuoloCittadino(),
					req.getCittadinoCF());
			listDichiarazioni.addAll(dichiarazioni);
		}
		
		RicercaDichiarazioniResponse res = new RicercaDichiarazioniResponse();
		res.setEsito(RisultatoCodice.SUCCESSO);
		
		if(listDichiarazioni != null && !listDichiarazioni.isEmpty()) {
			res.setDichiarazioni(listDichiarazioni);
		}
		else {
			List<Errore> errori = new ArrayList<>();
			errori.add(new Errore("DA.R02","Non esistono occorrenze rispetto alla ricerca effettuata"));
			res.setErrori(errori);
		}
		
		return res;
	}

}
