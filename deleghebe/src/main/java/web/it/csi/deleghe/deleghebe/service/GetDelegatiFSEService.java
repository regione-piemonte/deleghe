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
import it.csi.deleghe.deleghebe.ws.model.DelegaCittadino;
import it.csi.deleghe.deleghebe.ws.model.Errore;
import it.csi.deleghe.deleghebe.ws.model.RisultatoCodice;
import it.csi.deleghe.deleghebe.ws.msg.GetDelegati;
import it.csi.deleghe.deleghebe.ws.msg.GetDelegatiFSE;
import it.csi.deleghe.deleghebe.ws.msg.GetDelegatiFSEResponse;
import it.csi.deleghe.deleghebe.ws.msg.GetDelegatiResponse;
import it.csi.deleghe.deleghebe.ws.msg.RicercaDeleghe;
import it.csi.deleghe.deleghebe.ws.msg.RicercaDelegheFSEResponse;
import it.csi.deleghe.deleghebe.ws.msg.RicercaDelegheResponse;

public class GetDelegatiFSEService extends BaseService<GetDelegatiFSE,GetDelegatiFSEResponse>{
	
	@Inject
	private RicercaDelegheServiceFSE   ricercaDelegheServiceFSE;
	@Inject
	private DelegaDelegaCittadinoMapper delegaDelegaCittadinoMapper;
	
	@Override
	protected void checkServiceParams(GetDelegatiFSE req) {
		checkNotNull(req.getCittadinoDelegante(), "DA.V02", "Delegante non valorizzato.");
		checkNotNull(req.getCittadinoDelegante().getCodiceFiscale(), "DA.V01", "Codice fiscale delegante non valorizzato.");
	} 
	
	@Override
	protected GetDelegatiFSEResponse execute(GetDelegatiFSE req) {		

		GetDelegatiFSEResponse res = new GetDelegatiFSEResponse();
		
		RicercaDeleghe ricercaDeleghe=new RicercaDeleghe();
		ricercaDeleghe.setRichiedente(req.getRichiedente());
		Delega delega=new Delega();
		delega.setDelegante(req.getCittadinoDelegante());
		delega.setDelegato(req.getCittadinoDelegato()); //questo lo imposto quando viene settato un filtro di ricerca
		
		ricercaDeleghe.setDelega(delega);
		ricercaDeleghe.setStatiDelega(req.getStatiDelega());
		ricercaDeleghe.setCodiciServizio(req.getCodiciServizio());
		RicercaDelegheFSEResponse  resultRicercaDeleghe=ricercaDelegheServiceFSE.executeService(ricercaDeleghe);
		checkEsitoSuccesso(resultRicercaDeleghe, "DA.R03", "Errore nella ricerca deleghe");

		List<String> delegati = resultRicercaDeleghe.getDeleghe();

		res.setEsito(RisultatoCodice.SUCCESSO);		
		

			if(!delegati.isEmpty()) {

				res.setDelegatiFSE(delegati);			
			}
			else {
				List<Errore> errori=new ArrayList<>();
				errori.add(new Errore("DA.R02","Non esistono occorrenze rispetto alla ricerca effettuata"));
				res.setErrori(errori);
			}		
		
		return res;
	}


}
