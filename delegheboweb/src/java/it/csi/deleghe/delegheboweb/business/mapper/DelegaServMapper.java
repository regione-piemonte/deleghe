/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.delegheboweb.business.mapper;

import java.util.UUID;

import it.csi.deleghe.deleghebe.ws.model.DelegaServ;
import it.csi.deleghe.deleghebe.ws.model.DelegaServStato;
import it.csi.deleghe.deleghebe.ws.model.Servizio;
import it.csi.deleghe.delegheboweb.dto.delegheboweb.DelegaServizio;

public class DelegaServMapper extends BaseMapper<DelegaServizio, DelegaServ> {
	
	@Override
	public DelegaServ to(DelegaServizio ds) {
		if(ds==null) {
			return null;
		}
		DelegaServ result = new DelegaServ();
		Servizio servizio = new Servizio();
		servizio.setCodice(ds.getCodiceServizio());
		servizio.setDescrizione(ds.getDescriptionServizio());
		result.setServizio(servizio);
		result.setDataScadenza(ds.getDataFineDelega());
		result.setDataDecorrenza(ds.getDataInizioDelega());
		DelegaServStato stato = new DelegaServStato();
		stato.setCodice(ds.getStatoDelega());
		result.setStato(stato);
		final String uuid = ds.getUuid();
		if (uuid != null) result.setUuid(UUID.fromString(uuid));
		result.setDataRevoca(ds.getDataRevocaDelega());
		result.setDataRinuncia(ds.getDataRinunciaDelega());
		result.setDataCancellazione(ds.getDataCancellazione());
		result.setGradoDelega(ds.getGradoDelega());		

		return result;
	}
	
	@Override
	public DelegaServizio from(DelegaServ ds) {
		if(ds==null) {
			return null;
		}
		DelegaServizio result = new DelegaServizio();
		final Servizio servizio = ds.getServizio();
		result.setCodiceServizio(servizio !=null? servizio.getCodice():null);
		result.setDescriptionServizio(servizio !=null? servizio.getDescrizione():null);
		result.setDataFineDelega(ds.getDataScadenza());
		result.setDataInizioDelega(ds.getDataDecorrenza());
		result.setStatoDelega(ds.getStato()!=null?ds.getStato().getCodice():null);
		final UUID uuid = ds.getUuid();
		if (uuid != null) {
			result.setUuid(uuid.toString());
		}
		result.setDataRevocaDelega(ds.getDataRevoca());
		result.setDataRinunciaDelega(ds.getDataRinuncia());
		result.setDataCancellazione(ds.getDataCancellazione());
		result.setGradoDelega(ds.getGradoDelega());

		return result;
	}

	

}
