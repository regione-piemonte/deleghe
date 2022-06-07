/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.delegheboweb.business.mapper;

import it.csi.deleghe.deleghebe.ws.model.ModoDichiarazione;
import it.csi.deleghe.delegheboweb.dto.delegheboweb.DichiarazioneModo;

public class DichiarazioneModoMapper extends BaseMapper<DichiarazioneModo, ModoDichiarazione> {

	@Override
	public ModoDichiarazione to(DichiarazioneModo source) {
		if(source == null) {
			return null;
		}
		ModoDichiarazione result = new ModoDichiarazione();
		result.setCodice(source.getCodice());
		result.setDescrizione(source.getDescrizione());
		return result;
	}

	@Override
	public DichiarazioneModo from(ModoDichiarazione dest) {
		if(dest == null) {
			return null;
		}
		DichiarazioneModo result = new DichiarazioneModo();
		result.setCodice(dest.getCodice());
		result.setDescrizione(dest.getDescrizione());
		return result;
	}

}
