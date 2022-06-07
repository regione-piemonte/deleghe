/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service;

import static it.csi.deleghe.deleghebe.util.Check.checkCondition;
import static it.csi.deleghe.deleghebe.util.Check.checkEsitoSuccesso;

import javax.inject.Inject;

import it.csi.deleghe.deleghebe.service.base.BaseService;
import it.csi.deleghe.deleghebe.service.mapper.DelegaDelegaCittadinoMapper;
import it.csi.deleghe.deleghebe.ws.model.Delega;
import it.csi.deleghe.deleghebe.ws.model.DelegaCittadino;
import it.csi.deleghe.deleghebe.ws.model.RisultatoCodice;
import it.csi.deleghe.deleghebe.ws.msg.AggiornaDelega;
import it.csi.deleghe.deleghebe.ws.msg.AggiornaDelegaResponse;
import it.csi.deleghe.deleghebe.ws.msg.InserisciDelega;
import it.csi.deleghe.deleghebe.ws.msg.InserisciDelegaResponse;
import it.csi.deleghe.deleghebe.ws.msg.RicercaDeleghe;
import it.csi.deleghe.deleghebe.ws.msg.RicercaDelegheResponse;
import it.csi.deleghe.deleghebe.ws.msg.SaveDelegati;
import it.csi.deleghe.deleghebe.ws.msg.SaveDelegatiResponse;

public class SaveDelegatiService extends BaseService<SaveDelegati, SaveDelegatiResponse> {

	
	@Inject
	private InserisciDelegaService inserisciDelegaService;
	@Inject
	private AggiornaDelegaService  aggiornaDelegaService;
	@Inject
	private RicercaDelegheService   ricercaDelegheService;
	@Inject
	private DelegaDelegaCittadinoMapper delegaDelegaCittadinoMapper;
	
	@Override
	protected void checkServiceParams(SaveDelegati req) {
	}
	
	@Override
	protected SaveDelegatiResponse execute(SaveDelegati req) {
		SaveDelegatiResponse res = new SaveDelegatiResponse();
		
		
		for (DelegaCittadino delegaCittadino : req.getDelegati()) {
			
			Delega delega = delegaDelegaCittadinoMapper.estraiDelega(req.getCittadinoDelegante(), delegaCittadino);
			
			boolean isInserimento = true;
			
		
			if (delega.getUuid() != null) {
				RicercaDeleghe ricercaDeleghe = new RicercaDeleghe();
				ricercaDeleghe.setRichiedente(req.getRichiedente());
				ricercaDeleghe.setDelega(delega);
				RicercaDelegheResponse  resultRicercaDeleghe = ricercaDelegheService.executeService(ricercaDeleghe);
				
		
				isInserimento = (resultRicercaDeleghe.getDeleghe() != null) && resultRicercaDeleghe.getDeleghe().isEmpty();
				checkEsitoSuccesso(resultRicercaDeleghe, "DA.DLG06", "Ricerca deleghe fallita.");
				checkCondition(
						resultRicercaDeleghe.getDeleghe() == null || resultRicercaDeleghe.getDeleghe().size() <= 1,
						"DA.R05", "UUID non univoco per deleghe."
				);
			}
			
			if(isInserimento) {
		
				InserisciDelega ids = new InserisciDelega();
				delega.setStatoDelega("ATTIVA");
				ids.setDelega(delega);
				ids.setRichiedente(req.getRichiedente());
				InserisciDelegaResponse idr = inserisciDelegaService.executeService(ids);
				if(RisultatoCodice.FALLIMENTO == idr.getEsito() || idr.getDelega() == null){
					//JIRA 76
					res.getErrori().addAll(idr.getErrori());
					res.addErrore("DA.DLG04", "Inserimento delega fallito.");
				} else {
					DelegaCittadino d = delegaDelegaCittadinoMapper.estraiDelegatoCittadino(idr.getDelega(), idr.getDelega().getDelegato());
					if(d!=null) {
						res.getDelegati().add(d);
					}
				}
			} else {
		
				AggiornaDelega ads = new AggiornaDelega();
				ads.setDelega(delega);
				ads.setRichiedente(req.getRichiedente());
				AggiornaDelegaResponse adr = aggiornaDelegaService.executeService(ads);
				if(RisultatoCodice.FALLIMENTO == adr.getEsito() || adr.getDelega() == null){
		
					res.getErrori().addAll(adr.getErrori());
					res.addErrore("DA.DLG05", "Aggiornamento delega fallito");
				} else {
					DelegaCittadino d = delegaDelegaCittadinoMapper.estraiDeleganteCittadino(adr.getDelega(), adr.getDelega().getDelegato());
					res.getDelegati().add(d);
				}
			}
		}
		
		if(res.getErrori()!=null && !res.getErrori().isEmpty()) {
			res.setEsito(RisultatoCodice.FALLIMENTO);
		}
		else {
			res.setEsito(RisultatoCodice.SUCCESSO);
		}
		
		return res;
	}
	

}
