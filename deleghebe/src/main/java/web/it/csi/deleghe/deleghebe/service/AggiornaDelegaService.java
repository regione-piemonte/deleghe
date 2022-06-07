/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service;

import static it.csi.deleghe.deleghebe.util.Check.checkListNotNull;
import static it.csi.deleghe.deleghebe.util.Check.checkNotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import it.csi.deleghe.deleghebe.service.base.BaseService;
import it.csi.deleghe.deleghebe.util.DateUtil;
import it.csi.deleghe.deleghebe.util.NotificatoreUtil;
import it.csi.deleghe.deleghebe.ws.model.Delega;
import it.csi.deleghe.deleghebe.ws.model.DelegaServ;
import it.csi.deleghe.deleghebe.ws.model.RisultatoCodice;
import it.csi.deleghe.deleghebe.ws.msg.AggiornaDelega;
import it.csi.deleghe.deleghebe.ws.msg.AggiornaDelegaResponse;

public class AggiornaDelegaService extends BaseService<AggiornaDelega, AggiornaDelegaResponse> {

	@Inject
	private DelegaBean delegaBean;
	@Inject
	private NotificatoreUtil notificatoreUtil;

	@Override
	protected void checkServiceParams(AggiornaDelega req) {
		checkNotNull(req.getDelega(), "DA.V02", "Delega non valorizzata.");
		checkNotNull(req.getDelega().getUuid(), "DA.V01", "UUID non valorizzato.");
		checkListNotNull(req.getDelega().getServizi(), "DA.V02", "Servizi non valorizzati.");
	}

	@Override
	protected AggiornaDelegaResponse execute(AggiornaDelega req) {

		Delega delega = delegaBean.aggiornaDelega(req.getDelega(), req.getRichiedente().getCodiceFiscale());

		AggiornaDelegaResponse res = new AggiornaDelegaResponse();

		try {
			callNotificatore(delega);
		} catch (Exception e) {
			res.addErrore("DA.N1", "WARN: Errore Notificatore. " + e.getMessage());
		}

		res.setEsito(RisultatoCodice.SUCCESSO);
		res.setDelega(delega);
		return res;
	}

	private void callNotificatore(Delega delega) {
		if (DateUtil.isMaggiorenne(delega.getDelegante().getDataNascita())) {
			
			Map<String, String> replacements = new HashMap<>();
			replacements.put("@DELEGATO@", delega.getDelegato().getNome() + " " + delega.getDelegato().getCognome());
			replacements.put("@DELEGANTE@", delega.getDelegante().getNome() + " " + delega.getDelegante().getCognome());
			replacements.put("@DATA_DELEGA@", DateUtil.toStringSimpleDate(delega.getDataCreazione()));
			replacements.put("@SERVIZI@", loadStringServices(delega.getServizi()));
	
			
			notificatoreUtil.callNotificatore("delega", "modifica", "delegato", delega.getDelegato().getCodiceFiscale(), replacements);
		}
	}

	private String loadStringServices(List<DelegaServ> listserv) {

		StringBuilder strServ = new StringBuilder(listserv.isEmpty()? "[nessun servizio attivo]" : "");
		for (DelegaServ servizio : listserv) {
			strServ.append("'" + servizio.getServizio().getDescrizione() + "',");
		}
		int ind = strServ.lastIndexOf(",");
		if (ind >= 0) strServ.replace(ind, ind + 1, "");

		return strServ.toString();
	}

}
