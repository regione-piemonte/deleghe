/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.delegheboweb.business.mapper;

import it.csi.deleghe.delegheboweb.dto.deleghebowebLEGACY.DelegaServizio;

public class DelegaServizioMapper extends BaseMapper<DelegaServizio, it.csi.deleghe.deleghebe.ws.model.DelegaServizio> {

	@Override
	public it.csi.deleghe.deleghebe.ws.model.DelegaServizio to(DelegaServizio ds) {
		if(ds==null) {
			return null;
		}
		it.csi.deleghe.deleghebe.ws.model.DelegaServizio result = new it.csi.deleghe.deleghebe.ws.model.DelegaServizio();
		result.setCodiceServizio(ds.getCodiceServizio());
		result.setDataFineDelega(ds.getDataFineDelega());
		result.setDataInizioDelega(ds.getDataInizioDelega());
		result.setStatoDelega(ds.getStatoDelega());
		result.setUUID(ds.getUuid());
		
		return result;
	}

	@Override
	public DelegaServizio from(it.csi.deleghe.deleghebe.ws.model.DelegaServizio ds) {
		if(ds==null) {
			return null;
		}
		DelegaServizio result = new DelegaServizio();
		result.setCodiceServizio(ds.getCodiceServizio());
		result.setDataFineDelega(ds.getDataFineDelega());
		result.setDataInizioDelega(ds.getDataInizioDelega());
		result.setStatoDelega(ds.getStatoDelega());
		result.setUuid(ds.getUUID());
		
		return result;
	}

}
