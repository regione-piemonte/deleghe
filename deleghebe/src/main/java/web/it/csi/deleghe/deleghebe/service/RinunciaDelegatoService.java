/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service;

import static it.csi.deleghe.deleghebe.util.Check.checkCf;
import static it.csi.deleghe.deleghebe.util.Check.checkCondition;
import static it.csi.deleghe.deleghebe.util.Check.checkNotNull;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import it.csi.deleghe.deleghebe.service.base.BaseService;
import it.csi.deleghe.deleghebe.util.NotificatoreUtil;
import it.csi.deleghe.deleghebe.ws.model.RisultatoCodice;
import it.csi.deleghe.deleghebe.ws.msg.RinunciaDelegato;
import it.csi.deleghe.deleghebe.ws.msg.RinunciaDelegatoResponse;

public class RinunciaDelegatoService extends BaseService<RinunciaDelegato, RinunciaDelegatoResponse> {

	@Inject
	private RinunciaDelegheBean rinunciaDelegheBean;
	@Inject
	private CittadinoBean cittadinoBean;

	@Inject
	private NotificatoreUtil notificatoreUtil;

	@Override
	protected void checkServiceParams(RinunciaDelegato req) {
		checkNotNull(req.getCittadinoDelegante(), "DA.V02", "Delegante non valorizzato.");
		checkNotNull(req.getCittadinoDelegante().getCodiceFiscale(), "DA.V01", "Codice fiscale delegante non valorizzato.");
		checkCondition(checkCf(req.getCittadinoDelegante().getCodiceFiscale()), "DA.V03", "Codice fiscale delegante non corretto: " + req.getCittadinoDelegante().getCodiceFiscale());
		checkNotNull(req.getCittadinoDelegato(), "DA.V02", "Delegato non valorizzato.");
		checkCondition(checkCf(req.getCittadinoDelegato().getCodiceFiscale()), "DA.V03", "Codice fiscale delegato non corretto: " + req.getCittadinoDelegato().getCodiceFiscale());
		checkNotNull(req.getCittadinoDelegato().getCodiceFiscale(), "DA.V01", "Codice fiscale delegato non valorizzato.");
	}

	@Override
	protected RinunciaDelegatoResponse execute(RinunciaDelegato req) {

		String stringServices = rinunciaDelegheBean.rinunciaServiziDeleghe(req.getUuidDelegaServizii(), req.getCittadinoDelegante().getCodiceFiscale(), req.getCittadinoDelegato().getCodiceFiscale(), req.getRichiedente().getCodiceFiscale());


		req.setCittadinoDelegante(cittadinoBean.ricercaCittadino(req.getCittadinoDelegante()).get(0));
		req.setCittadinoDelegato(cittadinoBean.ricercaCittadino(req.getCittadinoDelegato()).get(0));
		
		RinunciaDelegatoResponse res = new RinunciaDelegatoResponse();
		try {
			callNotificatore(req, stringServices);
		} catch (Exception e) {
			res.addErrore("DA.N1", "WARN: Errore Notificatore. " + e.getMessage());
		}

		res.setEsito(RisultatoCodice.SUCCESSO);
		return res;
	}

	private void callNotificatore(RinunciaDelegato req, String stringServices) {

		Map<String, String> replacements = new HashMap<>();
		replacements.put("@DELEGATO@", req.getCittadinoDelegato().getNome() + " " + req.getCittadinoDelegato().getCognome());
		replacements.put("@DELEGANTE@", req.getCittadinoDelegante().getNome() + " " + req.getCittadinoDelegante().getCognome());
		replacements.put("@SERVIZI@", stringServices);

		notificatoreUtil.callNotificatore("delega", "rifiuto", "delegante", req.getCittadinoDelegante().getCodiceFiscale(), replacements);
		
	}

}
