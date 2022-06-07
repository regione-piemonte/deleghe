/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.delegheboweb.business.mapper;


import it.csi.deleghe.delegheboweb.dto.deleghebowebLEGACY.Delegato;

public class DelegatoMapper extends BaseMapper<Delegato, it.csi.deleghe.deleghebe.ws.model.Delegato> {

	@Override
	public it.csi.deleghe.deleghebe.ws.model.Delegato to(Delegato source) {
		if(source == null) {
			return null;
		}
		it.csi.deleghe.deleghebe.ws.model.Delegato result = new it.csi.deleghe.deleghebe.ws.model.Delegato();
		
		result.setCodiceFiscale(source.getCodiceFiscale());
		result.setCognome(source.getCognome());
		result.setNome(source.getNome());
		result.setDataNascita(source.getDataNascita());
		result.setComuneNascita(source.getComuneNascita());
		result.setSesso(source.getSesso());
		
		
		return result;
	}

	@Override
	public Delegato from(it.csi.deleghe.deleghebe.ws.model.Delegato dest) {
		if(dest == null) {
			return null;
		}
		Delegato result = new Delegato();
		
		result.setCodiceFiscale(dest.getCodiceFiscale());
		result.setCognome(dest.getCognome());
		result.setNome(dest.getNome());
		result.setDataNascita(dest.getDataNascita());
		result.setComuneNascita(dest.getComuneNascita());
		result.setSesso(dest.getSesso());
		
		return result;
	}

}
