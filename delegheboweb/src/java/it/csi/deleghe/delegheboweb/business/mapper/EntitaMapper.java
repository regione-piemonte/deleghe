/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.delegheboweb.business.mapper;

import it.csi.deleghe.delegheboweb.dto.delegheboweb.Entita;
import it.csi.deleghe.delegheboweb.dto.delegheboweb.RuoloOperazione;

public class EntitaMapper extends BaseMapper<Entita, it.csi.deleghe.deleghebe.ws.model.Entita>{
	
	@Override
	public it.csi.deleghe.deleghebe.ws.model.Entita to(Entita source) {
		if(source == null) {
			return null;
		}
		it.csi.deleghe.deleghebe.ws.model.Entita result =null;
		
		return result;
	}
	
	@Override
	public Entita from(it.csi.deleghe.deleghebe.ws.model.Entita dest) {
		if(dest == null) {
			return null;
		}
		Entita result = new Entita();
		
		return result;
	}

}
