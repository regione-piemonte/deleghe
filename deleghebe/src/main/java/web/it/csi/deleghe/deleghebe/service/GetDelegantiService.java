/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service;

import static it.csi.deleghe.deleghebe.util.Check.checkEsitoSuccesso;
import static it.csi.deleghe.deleghebe.util.Check.checkNotNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import it.csi.deleghe.deleghebe.service.base.BaseService;
import it.csi.deleghe.deleghebe.service.mapper.DelegaDelegaCittadinoMapper;
import it.csi.deleghe.deleghebe.ws.model.Delega;
//import it.csi.deleghe.deleghebe.ws.mock.MockDelegaCittadino;
import it.csi.deleghe.deleghebe.ws.model.DelegaCittadino;
import it.csi.deleghe.deleghebe.ws.model.Errore;
import it.csi.deleghe.deleghebe.ws.model.RisultatoCodice;
import it.csi.deleghe.deleghebe.ws.msg.GetDeleganti;
import it.csi.deleghe.deleghebe.ws.msg.GetDelegantiResponse;
import it.csi.deleghe.deleghebe.ws.msg.RicercaDeleghe;
import it.csi.deleghe.deleghebe.ws.msg.RicercaDelegheResponse;


public class GetDelegantiService extends BaseService<GetDeleganti, GetDelegantiResponse> {

	@Inject
	private RicercaDelegheService ricercaDelegheService;
	@Inject
	private DelegaDelegaCittadinoMapper delegaDelegaCittadinoMapper;
	
	@Override
	protected void checkServiceParams(GetDeleganti req) {
		checkNotNull(req.getCittadinoDelegato(), "DA.V02", "Delegato non valorizzato.");
		checkNotNull(req.getCittadinoDelegato().getCodiceFiscale(), "DA.V01", "Codice fiscale delegato non valorizzato.");
	}
	
	@Override
	protected GetDelegantiResponse execute(GetDeleganti req) {		

		RicercaDeleghe ricercaDeleghe=new RicercaDeleghe();
		ricercaDeleghe.setRichiedente(req.getRichiedente());
		Delega delega=new Delega();
		delega.setDelegante(req.getCittadinoDelegante()); //questo lo imposto quando viene settato un filtro di ricerca
		delega.setDelegato(req.getCittadinoDelegato());
		
		ricercaDeleghe.setDelega(delega);
		ricercaDeleghe.setStatiDelega(req.getStatiDelega());
		ricercaDeleghe.setCodiciServizio(req.getCodiciServizio());
		RicercaDelegheResponse  resultRicercaDeleghe=ricercaDelegheService.executeService(ricercaDeleghe);
		checkEsitoSuccesso(resultRicercaDeleghe, "DA.R03", "Errore nella ricerca deleghe");
		
		List<DelegaCittadino> deleganti = delegaDelegaCittadinoMapper.estraiDeleganti(resultRicercaDeleghe.getDeleghe());
		
		GetDelegantiResponse res = new GetDelegantiResponse();
		res.setEsito(RisultatoCodice.SUCCESSO);
		
		if(!deleganti.isEmpty()) {
			res.setDeleganti(deleganti);
		}
		else {
			List<Errore> errori=new ArrayList<>();
			errori.add(new Errore("DA.R02","Non esistono occorrenze rispetto alla ricerca effettuata"));
			res.setErrori(errori);
		}
		
		return res;
	}


}
