/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service;

import static it.csi.deleghe.deleghebe.util.Check.checkCondition;
import static it.csi.deleghe.deleghebe.util.Check.checkNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import it.csi.deleghe.deleghebe.service.base.BaseService;
import it.csi.deleghe.deleghebe.ws.model.Cittadino;
import it.csi.deleghe.deleghebe.ws.model.Dichiarazione;
import it.csi.deleghe.deleghebe.ws.model.DichiarazioneDettaglio;
import it.csi.deleghe.deleghebe.ws.model.Errore;
import it.csi.deleghe.deleghebe.ws.model.RisultatoCodice;
import it.csi.deleghe.deleghebe.ws.msg.ElencoGenitori;
import it.csi.deleghe.deleghebe.ws.msg.ElencoGenitoriResponse;

public class ElencoGenitoriService extends BaseService<ElencoGenitori, ElencoGenitoriResponse> {

	@Inject
	private DichiarazioneBean dichiarazioneBean;

	@Override
	protected void checkServiceParams(ElencoGenitori req) {
		checkNotNull(req.getCittadino(), "DA.V02", "Cittadino non valorizzato.");
		checkCondition((req.getCittadino().getCodiceFiscale() != null) || (req.getCittadino().getCognome() != null && req.getCittadino().getNome() != null && req.getCittadino().getDataNascita() != null), "DA.V01", "Codice fiscale o Dati anagrafici del cittadino non valorizzati.");
	}

	@Override
	protected ElencoGenitoriResponse execute(ElencoGenitori req) {

		

		Map<String, Cittadino> cittadini = new HashMap<>();

		String cf = req.getCittadino().getCodiceFiscale();

		List<Dichiarazione> dichiarazioni = new ArrayList<>();
		dichiarazioni.addAll(dichiarazioneBean.ricercaDichiarazioni(null, cf, DeleDDichiarazioneRuoloRepository.DicRuoloCodEnum.GENITORE_1.name(), null, null, null, null, null));
		dichiarazioni.addAll(dichiarazioneBean.ricercaDichiarazioni(null, cf, DeleDDichiarazioneRuoloRepository.DicRuoloCodEnum.GENITORE_2.name(), null, null, null, null, null));

		for (Dichiarazione dichiarazione : dichiarazioni) {
			for (DichiarazioneDettaglio dettaglio : dichiarazione.getDettagli()) {
				Cittadino cit = dettaglio.getCittadino1();
				if (!cit.getCodiceFiscale().equals(cf)) {
					cittadini.putIfAbsent(cit.getCodiceFiscale(), cit);
				}
			}
		}

		ElencoGenitoriResponse res = new ElencoGenitoriResponse();

		if (!cittadini.isEmpty()) {
			res.setCittadini(new ArrayList<Cittadino>(cittadini.values()));
		} else {
			List<Errore> errori = new ArrayList<>();
			errori.add(new Errore("DA.R02","Non esistono occorrenze rispetto alla ricerca effettuata"));
			res.setErrori(errori);
		}
		res.setEsito(RisultatoCodice.SUCCESSO);

		return res;
	}

}
