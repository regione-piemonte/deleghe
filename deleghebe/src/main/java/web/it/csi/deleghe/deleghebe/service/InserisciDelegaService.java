/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service;

import static it.csi.deleghe.deleghebe.util.Check.checkCf;
import static it.csi.deleghe.deleghebe.util.Check.checkCondition;
import static it.csi.deleghe.deleghebe.util.Check.checkNotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import it.csi.deleghe.deleghebe.service.base.BaseService;
import it.csi.deleghe.deleghebe.service.exception.BeServiceException;
import it.csi.deleghe.deleghebe.util.DateUtil;
import it.csi.deleghe.deleghebe.util.NotificatoreUtil;
import it.csi.deleghe.deleghebe.ws.model.Delega;
import it.csi.deleghe.deleghebe.ws.model.DelegaServ;
import it.csi.deleghe.deleghebe.ws.model.Dichiarazione;
import it.csi.deleghe.deleghebe.ws.model.RisultatoCodice;
import it.csi.deleghe.deleghebe.ws.msg.InserisciDelega;
import it.csi.deleghe.deleghebe.ws.msg.InserisciDelegaResponse;

public class InserisciDelegaService extends BaseService<InserisciDelega, InserisciDelegaResponse> {

	@Inject
	private DelegaBean delegaBean;
	@Inject
	private NotificatoreUtil notificatoreUtil;

	@Override
	protected void checkServiceParams(InserisciDelega req) {
		checkNotNull(req.getDelega(), "DA.V02", "Delega non valorizzata.");
		checkNotNull(req.getDelega().getDelegante(), "DA.V02", "Delegante non valorizzato.");
		checkNotNull(req.getDelega().getDelegante().getCodiceFiscale(), "DA.V01", "Codice fiscale delegante non valorizzato.");
		checkCondition(checkCf(req.getDelega().getDelegante().getCodiceFiscale()), "DA.V03", "Codice fiscale delegante non corretto: " + req.getDelega().getDelegante().getCodiceFiscale());
		checkNotNull(req.getDelega().getDelegante().getCognome(), "DA.V01", "Cognome delegante non valorizzato.");
		checkNotNull(req.getDelega().getDelegante().getNome(), "DA.V01", "Nome delegante non valorizzato.");
		checkNotNull(req.getDelega().getDelegante().getSesso(), "DA.V01", "Sesso delegante non valorizzato");
		checkNotNull(req.getDelega().getDelegante().getDataNascita(), "DA.V01", "Data di nascita delegante non valorizzato.");
		checkNotNull(req.getDelega().getDelegante().getComuneNascita(), "DA.V01", "Luogo di nascita delegante non valorizzato.");

		checkNotNull(req.getDelega().getDelegato(), "DA.V02", "Delegato non valorizzato.");
		checkNotNull(req.getDelega().getDelegato().getCodiceFiscale(), "DA.V01", "Codice fiscale delegato non valorizzato.");
		checkCondition(checkCf(req.getDelega().getDelegato().getCodiceFiscale()), "DA.V03", "Codice fiscale delegato non corretto: " + req.getDelega().getDelegato().getCodiceFiscale());
		checkNotNull(req.getDelega().getDelegato().getCognome(), "DA.V01", "Cognome delegato non valorizzato.");
		checkNotNull(req.getDelega().getDelegato().getNome(), "DA.V01", "Nome delegato non valorizzato.");
		checkNotNull(req.getDelega().getDelegato().getSesso(), "DA.V01", "Sesso delegato non valorizzato");
		checkNotNull(req.getDelega().getDelegato().getDataNascita(), "DA.V01", "Data di nascita delegato non valorizzato.");
		checkNotNull(req.getDelega().getDelegato().getComuneNascita(), "DA.V01", "Luogo di nascita delegato non valorizzato.");

		checkNotNull(req.getDelega().getServizi(), "DA.V02", "Servizi non valorizzati.");
		checkCondition(!req.getDelega().getServizi().isEmpty(), "DA.V02", "Servizi non valorizzati.");
	}

	@Override
	protected InserisciDelegaResponse execute(InserisciDelega req) {
		Delega delegaInserita = null;
		// CR 00XX_1 ADULTI
		if (checkCodiciFiscali(req.getDelega().getDelegante().getCodiceFiscale(), req.getDelega().getDelegato().getCodiceFiscale())) {
			throw new BeServiceException("DA.DLG07", "Il codice fiscale del delegante deve essere diverso da quello del delegato");
		}

		delegaInserita = delegaBean.inserisciDelega(req.getDelega(), req.getRichiedente().getCodiceFiscale());

		InserisciDelegaResponse res = new InserisciDelegaResponse();
		try {
			callNotificatore(delegaInserita);
		} catch (Exception e) {
			res.addErrore("DA.N1", "WARN: Errore Notificatore. " + e.getMessage());
		}
		


		res.setDelega(delegaInserita);
		res.setEsito(RisultatoCodice.SUCCESSO);
		workAroundCorrezionebaco(delegaInserita);
		return res;
	}
	
	private void workAroundCorrezionebaco(Delega delegaInserita ) {
	
		delegaBean.workAroundCorrezionebaco(delegaInserita.getnPratica());
		
	}

	private void callNotificatore(Delega delega) {
		if (DateUtil.isMaggiorenne(delega.getDelegante().getDataNascita())) {
			
			Map<String, String> replacements = new HashMap<String, String>();
			replacements.put("@DELEGATO@", delega.getDelegato().getNome() + " " + delega.getDelegato().getCognome());
			replacements.put("@DELEGANTE@", delega.getDelegante().getNome() + " " + delega.getDelegante().getCognome());
			replacements.put("@DATA_DELEGA@", DateUtil.toStringSimpleDate(delega.getDataCreazione()));
			replacements.put("@SERVIZI@", loadStringServices(delega.getServizi()));

			
			notificatoreUtil.callNotificatore("delega", "nuova", "delegato", delega.getDelegato().getCodiceFiscale(), replacements);
		}
	}

	private boolean checkCodiciFiscali(String cf_delegante, String cf_delegato) {
		return cf_delegante.equalsIgnoreCase(cf_delegato);
	}

	private String loadStringServices(List<DelegaServ> listserv) {

		StringBuilder strServ = new StringBuilder();
		for (DelegaServ servizio : listserv) {
			strServ.append("'" + servizio.getServizio().getDescrizione() + "',");
		}
		int ind = strServ.lastIndexOf(",");
		if (ind >= 0) strServ.replace(ind, ind + 1, "");

		return strServ.toString();
	}

}
