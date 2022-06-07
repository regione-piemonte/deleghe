/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.delegheboweb.business.mapper;

import it.csi.deleghe.deleghebe.ws.model.Servizio;
import it.csi.deleghe.delegheboweb.dto.delegheboweb.InfoServizio;
import it.csi.deleghe.delegheboweb.dto.deleghebowebLEGACY.DelegaServizio;

public class ServizioMapper extends BaseMapper<InfoServizio, Servizio> {

	@Override
	public Servizio to(InfoServizio ds) {
		if(ds==null) {
			return null;
		}
		Servizio result = new Servizio();
		result.setCodice(ds.getNome());
		result.setDescrizione(ds.getDescrizione());
		result.setDescrizioneEstesa(ds.getServiceDescrizione());

		return result;
	}

	@Override
	public InfoServizio from(Servizio ds) {
		if(ds==null) {
			return null;
		}
		InfoServizio result = new InfoServizio();
		result.setDescrizione(ds.getDescrizione());
		result.setNome(ds.getCodice());
		result.setServiceDescrizione(ds.getDescrizioneEstesa());
		result.setCodSerpadre(ds.getCodSerPadre());
		result.setFraseDebole(ds.getFraseDebole());
		result.setFraseForte(ds.getFraseForte());	
		result.setNumeroGiorniDelegabili(ds.getNumeroGiorniDelegabili());
		result.setDelegabile(ds.getDelegabile());
		
		return result;
	}

}
