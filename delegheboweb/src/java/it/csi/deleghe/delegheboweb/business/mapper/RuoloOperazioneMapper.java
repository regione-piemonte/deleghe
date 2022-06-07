/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.delegheboweb.business.mapper;

import it.csi.deleghe.delegheboweb.dto.delegheboweb.DocumentoTipo;
import it.csi.deleghe.delegheboweb.dto.delegheboweb.RuoloOperazione;

public class RuoloOperazioneMapper extends BaseMapper<RuoloOperazione,it.csi.deleghe.deleghebe.ws.model.RuoloOperazione> {
	
	@Override
	public it.csi.deleghe.deleghebe.ws.model.RuoloOperazione to(RuoloOperazione source) {
		if(source == null) {
			return null;
		}
		it.csi.deleghe.deleghebe.ws.model.RuoloOperazione result = new it.csi.deleghe.deleghebe.ws.model.RuoloOperazione();
		result.setCodice(source.getCodice());
		result.setDescrizione(source.getDescrizione());
		return result;
	}
	

	@Override
	public RuoloOperazione from(it.csi.deleghe.deleghebe.ws.model.RuoloOperazione dest) {
		if(dest == null) {
			return null;
		}
		RuoloOperazione result = new RuoloOperazione();
		result.setCodice(dest.getCodice());
		result.setDescrizione(dest.getDescrizione());
		return result;
	}

}
