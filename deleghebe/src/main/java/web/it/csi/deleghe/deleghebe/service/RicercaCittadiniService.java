/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service;

import static it.csi.deleghe.deleghebe.util.Check.checkCondition;
import static it.csi.deleghe.deleghebe.util.Check.checkNotNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import it.csi.deleghe.deleghebe.service.base.BaseService;
import it.csi.deleghe.deleghebe.ws.model.Cittadino;
import it.csi.deleghe.deleghebe.ws.model.Errore;
import it.csi.deleghe.deleghebe.ws.model.RisultatoCodice;
import it.csi.deleghe.deleghebe.ws.msg.RicercaCittadini;
import it.csi.deleghe.deleghebe.ws.msg.RicercaCittadiniResponse;

public class RicercaCittadiniService extends BaseService<RicercaCittadini, RicercaCittadiniResponse> {

	@Inject
	private CittadinoBean cittadinoBean;
	
	@Override
	protected void checkServiceParams(RicercaCittadini req) {
		checkNotNull(req.getCittadino(), "DA.E48", "Cittadino non valorizzato.");
		checkCondition((req.getCittadino().getCodiceFiscale() !=null) || (req.getCittadino().getCognome()!=null && req.getCittadino().getNome()!=null && req.getCittadino().getDataNascita()!=null && req.getCittadino().getComuneNascita()!=null && req.getCittadino().getSesso() != null) ,"DA.E49","Inserire il codice fiscale oppure il cognome, nome, data di nascita, comune di nascita ed il sesso.");
	}
	
	@Override
	protected RicercaCittadiniResponse execute(RicercaCittadini req) {
		
		List<Cittadino> cittadini=cittadinoBean.ricercaCittadino(req.getCittadino());
		
		RicercaCittadiniResponse res = new RicercaCittadiniResponse();

		if(!cittadini.isEmpty()) {
			res.setCittadini(cittadini);
		}
		else {
			List<Errore> errori=new ArrayList<>();
			errori.add(new Errore("DMC03.E01","Non esistono occorrenze rispetto alla ricerca effettuata"));
			res.setErrori(errori);
		}
		res.setEsito(RisultatoCodice.SUCCESSO);
		
		return res;
	}

	

}
