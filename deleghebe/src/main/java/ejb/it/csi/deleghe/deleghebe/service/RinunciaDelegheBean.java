/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service;

import java.util.List;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import it.csi.deleghe.deleghebe.model.DeleTDelega;
import it.csi.deleghe.deleghebe.model.DeleTDelegaServizio;
import it.csi.deleghe.deleghebe.service.exception.BeServiceException;

@Stateless
public class RinunciaDelegheBean extends BaseRepository {

	@Inject
	private DeleTDelegaServizioRepository delegaServizioRepository;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public String rinunciaServiziDeleghe(List<String> uuidDelegaServizii, String cfCitDelegante, String cfCitDelegato, String loginOperazione) {

		StringBuilder descrServices = new StringBuilder();
		for (String uuidDelegaServizio : uuidDelegaServizii) {
			UUID uuid = UUID.fromString(uuidDelegaServizio);
			DeleTDelegaServizio delegaServizio = delegaServizioRepository.ricercaDelegaServizioByUUID(uuid);
			if (delegaServizio == null) {
				String msg = "Non esiste una delega servizio per l'UUID: " + uuid;
				throw new BeServiceException("DA.R02", msg);
			}
			checkCoerenzaCittadini(delegaServizio, cfCitDelegante, cfCitDelegato);
			delegaServizioRepository.aggiornaStatoRinunciatoDelegaServizio(delegaServizio, loginOperazione);
			// costruisco la stringa che contiene le descrizioni dei servizi aggiornati
			descrServices.append(delegaServizio.getDeleDServizio().getSerDesc());
		}

		return descrServices.toString();

	}

	private void checkCoerenzaCittadini(DeleTDelegaServizio delegaServizio, String cfCitDelegante, String cfCitDelegato) {
		DeleTDelega deleTDelega = delegaServizio.getDeleTDelega();
		if (!deleTDelega.getDeleTCittadino1().getCitCf().equals(cfCitDelegante)) {
			throw new BeServiceException("DA.I02", "Codice fiscale delegante incoerente");
		}
		if (!deleTDelega.getDeleTCittadino2().getCitCf().equals(cfCitDelegato)) {
			throw new BeServiceException("DA.I02", "Codice fiscale delegato incoerente");
		}
	}

}
