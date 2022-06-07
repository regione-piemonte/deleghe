/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service;

import static it.csi.deleghe.deleghebe.util.Check.checkCf;
import static it.csi.deleghe.deleghebe.util.Check.checkCondition;
import static it.csi.deleghe.deleghebe.util.Check.checkListNotNull;
import static it.csi.deleghe.deleghebe.util.Check.checkNotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import it.csi.deleghe.deleghebe.service.DeleDDichiarazioneRuoloRepository.DicRuoloCodEnum;
import it.csi.deleghe.deleghebe.service.base.BaseService;
import it.csi.deleghe.deleghebe.util.DateUtil;
import it.csi.deleghe.deleghebe.util.NotificatoreUtil;
import it.csi.deleghe.deleghebe.ws.model.Cittadino;
import it.csi.deleghe.deleghebe.ws.model.Dichiarazione;
import it.csi.deleghe.deleghebe.ws.model.DichiarazioneDettaglio;
import it.csi.deleghe.deleghebe.ws.model.RisultatoCodice;
import it.csi.deleghe.deleghebe.ws.msg.InserisciDichiarazione;
import it.csi.deleghe.deleghebe.ws.msg.InserisciDichiarazioneResponse;

public class InserisciDichiarazioneService extends BaseService<InserisciDichiarazione, InserisciDichiarazioneResponse> {

	@Inject
	private DichiarazioneBean dichiarazioneBean;
	@Inject
	private NotificatoreUtil notificatoreUtil;

	@Override
	protected void checkServiceParams(InserisciDichiarazione req) {
		checkNotNull(req.getDichiarazione(), "DA.V02", "Dichiarazione non valorizzata.");
		checkNotNull(req.getDichiarazione().getRuoloOperazione(), "DA.V02", "Ruolo operazione non valorizzato");
		checkNotNull(req.getDichiarazione().getRuoloOperazione().getCodice(), "DA.V01", "Codice ruolo operazione non valorizzato");
		checkNotNull(req.getDichiarazione().getModo(), "DA.V02", "Modo dichiarazione non valorizzato");
		checkNotNull(req.getDichiarazione().getModo().getCodice(), "DA.V01", "Codice modo dichiarazione non valorizzato");
		checkNotNull(req.getDichiarazione().getTipo(), "DA.V02", "Tipo dichiarazione non valorizzato");
		checkNotNull(req.getDichiarazione().getTipo().getCodice(), "DA.V01", "Codice tipo dichiarazione non valorizzato");
		checkNotNull(req.getDichiarazione().getStato(), "DA.V02", "Stato dichiarazione non valorizzato");
		checkNotNull(req.getDichiarazione().getStato().getCodice(), "DA.V01", "Codice modo dichiarazione non valorizzato");
		checkDettagli(req.getDichiarazione().getDettagli());
	}

	protected void checkDettagli(List<DichiarazioneDettaglio> dettagli) {
		checkListNotNull(dettagli, "DA.V02", "Dettagli non valorizzati.");
		for (DichiarazioneDettaglio dichiarazioneDettaglio : dettagli) {
			checkNotNull(dichiarazioneDettaglio.getCittadino1(), "DA.V02", "Dettaglio del cittadino non valorizzato.");
			checkNotNull(dichiarazioneDettaglio.getRuoloCittadino1(),"DA.V02", "Ruolo cittadino non valorizzato.");
			checkNotNull(dichiarazioneDettaglio.getRuoloCittadino1().getCodice(),"DA.V01", "Codice ruolo cittadino non valorizzato.");
			checkNotNull(dichiarazioneDettaglio.getCittadino1().getCodiceFiscale(), "DA.V01", "Codice fiscale " + dichiarazioneDettaglio.getRuoloCittadino1().getCodice() + " non valorizzato.");
			checkCondition(checkCf(dichiarazioneDettaglio.getCittadino1().getCodiceFiscale()),"DA.V01","Codice fiscale " + dichiarazioneDettaglio.getRuoloCittadino1().getCodice() + " non corretto: " + dichiarazioneDettaglio.getCittadino1().getCodiceFiscale());
			checkNotNull(dichiarazioneDettaglio.getCittadino1().getCognome(), "DA.V01", "Cognome " + dichiarazioneDettaglio.getRuoloCittadino1().getCodice() + " non valorizzato.");
			checkNotNull(dichiarazioneDettaglio.getCittadino1().getNome(), "DA.V01", "Nome " + dichiarazioneDettaglio.getRuoloCittadino1().getCodice() + " non valorizzato.");
			checkNotNull(dichiarazioneDettaglio.getCittadino1().getSesso(), "DA.V01", "Sesso " + dichiarazioneDettaglio.getRuoloCittadino1().getCodice() + " non valorizzato");
			checkNotNull(dichiarazioneDettaglio.getCittadino1().getDataNascita(), "DA.V01", "Data di nascita " + dichiarazioneDettaglio.getRuoloCittadino1().getCodice() + " non valorizzato.");
			checkNotNull(dichiarazioneDettaglio.getCittadino1().getComuneNascita(), "DA.V01", "Luogo di nascita " + dichiarazioneDettaglio.getRuoloCittadino1().getCodice() + " non valorizzato.");

			checkNotNull(dichiarazioneDettaglio.getRuoloCittadino2(),"DA.V02", "Ruolo cittadino non valorizzato.");
			checkNotNull(dichiarazioneDettaglio.getRuoloCittadino2().getCodice(),"DA.V01", "Codice ruolo cittadino non valorizzato.");
			checkNotNull(dichiarazioneDettaglio.getCittadino2(), "DA.V02", "Dettaglio del cittadino non valorizzato.");
			checkNotNull(dichiarazioneDettaglio.getCittadino2().getCodiceFiscale(), "DA.V01", "Codice fiscale " + dichiarazioneDettaglio.getRuoloCittadino2().getCodice() + " non valorizzato.");
			checkCondition(checkCf(dichiarazioneDettaglio.getCittadino2().getCodiceFiscale()),"DA.V01","Codice fiscale " + dichiarazioneDettaglio.getRuoloCittadino2().getCodice() + " non corretto: " + dichiarazioneDettaglio.getCittadino2().getCodiceFiscale());
			checkNotNull(dichiarazioneDettaglio.getCittadino2().getCognome(), "DA.V01", "Cognome " + dichiarazioneDettaglio.getRuoloCittadino2().getCodice() + " non valorizzato.");
			checkNotNull(dichiarazioneDettaglio.getCittadino2().getNome(), "DA.V01", "Nome " + dichiarazioneDettaglio.getRuoloCittadino2().getCodice() + " non valorizzato.");
			checkNotNull(dichiarazioneDettaglio.getCittadino2().getSesso(), "DA.V01", "Sesso " + dichiarazioneDettaglio.getRuoloCittadino2().getCodice() + " non valorizzato");
			checkNotNull(dichiarazioneDettaglio.getCittadino2().getDataNascita(), "DA.V01", "Data di nascita " + dichiarazioneDettaglio.getRuoloCittadino2().getCodice() + " non valorizzato.");
			checkNotNull(dichiarazioneDettaglio.getCittadino2().getComuneNascita(), "DA.V01", "Luogo di nascita " + dichiarazioneDettaglio.getRuoloCittadino2().getCodice() + " non valorizzato.");
			
			checkNotNull(dichiarazioneDettaglio.getStato(), "DA.V01", "Stato del dettaglio dichiarazione non valorizzato.");
		}
	}
	
	@Override
	protected InserisciDichiarazioneResponse execute(InserisciDichiarazione req) {


		Dichiarazione dichiarazioneInserita = dichiarazioneBean.inserisciDichiarazione(req.getDichiarazione(), req.getRichiedente().getCodiceFiscale());

		InserisciDichiarazioneResponse res = new InserisciDichiarazioneResponse();
		try {
			callNotificatore(dichiarazioneInserita);
		} catch (Exception e) {
			res.addErrore("DA.N1", "WARN: Errore Notificatore. " + e.getMessage());
		}

		res.setDichiarazione(dichiarazioneInserita);
		res.setEsito(RisultatoCodice.SUCCESSO);
		return res;
	}

	private void callNotificatore(Dichiarazione dichiarazioneInserita) {
		Map<Cittadino, Map<String, Cittadino>> mapValue = loadParentsBySon(dichiarazioneInserita.getDettagli());


		for (Map.Entry<Cittadino, Map<String, Cittadino>> entry : mapValue.entrySet()) {
			Map<String, String> replacements = new HashMap<>();

			Cittadino figlio = entry.getKey();
			replacements.put("@FIGLIO@", figlio.getNome() + " " + figlio.getCognome());
			replacements.put("@DATA_DICHIARAZIONE@", DateUtil.toStringSimpleDate(dichiarazioneInserita.getDataCreazione()));

			String cfGenitore1 = null;
			String cfGenitore2 = null;
			Map<String, Cittadino> parentsValue = entry.getValue();
			for (Map.Entry<String, Cittadino> entryGenitore : parentsValue.entrySet()) {
				String ruolo = entryGenitore.getKey();
				Cittadino genitore = entryGenitore.getValue();
				if (ruolo.equalsIgnoreCase(DicRuoloCodEnum.GENITORE_1.name())) {
					replacements.put("@GENITORE1@", genitore.getNome() + " " + genitore.getCognome());
					cfGenitore1 = genitore.getCodiceFiscale();
				} else if (ruolo.equalsIgnoreCase(DicRuoloCodEnum.GENITORE_2.name())) {
					replacements.put("@GENITORE2@", genitore.getNome() + " " + genitore.getCognome());
					cfGenitore2 = genitore.getCodiceFiscale();
				}
			}


			notificatoreUtil.callNotificatore("dichiarazione", "nuova", "genitore2", cfGenitore2, replacements);
			notificatoreUtil.callNotificatore("dichiarazione", "nuova", "figlio", figlio.getCodiceFiscale(), replacements);
		}
	}

	private Map<Cittadino, Map<String, Cittadino>> loadParentsBySon(List<DichiarazioneDettaglio> dettagli) {
		Map<Cittadino, Map<String, Cittadino>> mapValue = new HashMap<>();
		Map<String, Cittadino> parents;
		for (DichiarazioneDettaglio dettaglio : dettagli) {

			if (!mapValue.containsKey(dettaglio.getCittadino2())) {
				parents = new HashMap<>();
			} else {
				parents = mapValue.get(dettaglio.getCittadino2());
			}
			parents.put(dettaglio.getRuoloCittadino1().getCodice(), dettaglio.getCittadino1());
			mapValue.put(dettaglio.getCittadino2(), parents);

		}

		return mapValue;
	}

}
