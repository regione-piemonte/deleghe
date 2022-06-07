/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.delegheboweb.business.mapper;

import it.csi.deleghe.deleghebe.ws.model.StatoDichiarazione;
import it.csi.deleghe.delegheboweb.dto.delegheboweb.DichiarazioneStato;

public class DichiarazioneStatoMapper extends BaseMapper<DichiarazioneStato, StatoDichiarazione> {

	@Override
	public StatoDichiarazione to(DichiarazioneStato source) {
		if(source == null) {
			return null;
		}
		StatoDichiarazione result = new StatoDichiarazione();
		result.setCodice(source.getCodice());
		result.setDescrizione(source.getDescrizione());
		return result;
	}

	@Override
	public DichiarazioneStato from(StatoDichiarazione dest) {
		if(dest == null) {
			return null;
		}
		DichiarazioneStato result = new DichiarazioneStato();
		result.setCodice(dest.getCodice());
		result.setDescrizione(dest.getDescrizione());
		return result;
	}

}
