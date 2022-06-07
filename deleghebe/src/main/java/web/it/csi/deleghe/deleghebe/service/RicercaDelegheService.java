/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import it.csi.deleghe.deleghebe.service.base.BaseService;
import it.csi.deleghe.deleghebe.ws.model.Delega;
import it.csi.deleghe.deleghebe.ws.model.Errore;
import it.csi.deleghe.deleghebe.ws.model.RisultatoCodice;
import it.csi.deleghe.deleghebe.ws.msg.RicercaDeleghe;
import it.csi.deleghe.deleghebe.ws.msg.RicercaDelegheResponse;


public class RicercaDelegheService extends BaseService<RicercaDeleghe, RicercaDelegheResponse> {

	@Inject
	private DelegaBean delegaBean;
	
	
	@Override
	protected void checkServiceParams(RicercaDeleghe req) {
	}
	
	@Override
	protected RicercaDelegheResponse execute(RicercaDeleghe req) {
		
		boolean useStorico = (req.getDelega().getDelegante() != null) && (req.getDelega().getDelegato() != null);
		
		List<Delega> deleghe = null;
		if (useStorico) {
			deleghe = delegaBean.ricercaDelegheStorico(req.getDelega(), req.getCodiciServizio(),req.getStatiDelega());
		} else {
			deleghe = delegaBean.ricercaDeleghe(req.getDelega(), req.getCodiciServizio(),req.getStatiDelega());
		}
		
		RicercaDelegheResponse res = new RicercaDelegheResponse();
		res.setEsito(RisultatoCodice.SUCCESSO);
		
		if(deleghe.isEmpty()) {
			List<Errore> errori = new ArrayList<>();
			errori.add(new Errore("DA.R02","Non esistono occorrenze rispetto alla ricerca effettuata"));
			res.setErrori(errori);
			return res;
		}
		
		res.setDeleghe(deleghe);
		return res;
	}

}
