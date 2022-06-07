/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.delegheboweb.business.mapper;

import it.csi.deleghe.deleghebe.ws.model.TipoDichiarazione;
import it.csi.deleghe.delegheboweb.dto.delegheboweb.DichiarazioneTipo;

public class DichiarazioneTipoMapper extends BaseMapper<DichiarazioneTipo, TipoDichiarazione> {

	@Override
	public TipoDichiarazione to(DichiarazioneTipo source) {
		if(source == null) {
			return null;
		}
		TipoDichiarazione result = new TipoDichiarazione();
		result.setCodice(source.getCodice());
		result.setDescrizione(source.getDescrizione());
		return result;
	}

	@Override
	public DichiarazioneTipo from(TipoDichiarazione dest) {
		if(dest == null) {
			return null;
		}
		DichiarazioneTipo result = new DichiarazioneTipo();
		result.setCodice(dest.getCodice());
		result.setDescrizione(dest.getDescrizione());
		return result;
	}

}
