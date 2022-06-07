/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.delegheboweb.business.mapper;

import it.csi.deleghe.deleghebe.ws.model.Sesso;
import it.csi.deleghe.delegheboweb.dto.deleghebowebLEGACY.DelegaCittadino;

public class DelegaCittadinoMapper extends BaseMapper<DelegaCittadino, it.csi.deleghe.deleghebe.ws.model.DelegaCittadino> {

	
	
	@Override
	public it.csi.deleghe.deleghebe.ws.model.DelegaCittadino to(DelegaCittadino source) {
		if(source == null) {
			return null;
		}
		it.csi.deleghe.deleghebe.ws.model.DelegaCittadino result = new it.csi.deleghe.deleghebe.ws.model.DelegaCittadino();
		
		result.setIdAura(source.getIdAura()!=null?new IntegerLongMapper().to(source.getIdAura()):null);
		result.setCodiceFiscale(source.getCodiceFiscale());
		result.setCognome(source.getCognome());
		result.setNome(source.getNome());
		result.setDataDiNascita(source.getDataNascita());
		result.setLuogoNascita(source.getComuneNascita());
		result.setSesso(Sesso.valueOf(source.getSesso().name()));
		result.setUuid(source.getUuid());
		
		result.setDeleghe(new DelegaServizioMapper().toList(source.getDelegheServizi()));
		
		return result;
	}

	@Override
	public DelegaCittadino from(it.csi.deleghe.deleghebe.ws.model.DelegaCittadino dest) {
		if(dest == null) {
			return null;
		}
		DelegaCittadino result = new DelegaCittadino();
		
		result.setCodiceFiscale(dest.getCodiceFiscale());
		result.setIdAura(dest.getIdAura()!=null? new IntegerLongMapper().from(dest.getIdAura()):null);
		result.setCognome(dest.getCognome());
		result.setNome(dest.getNome());
		result.setDataNascita(dest.getDataDiNascita());
		result.setComuneNascita(dest.getLuogoNascita());
		result.setUuid(dest.getUuid());
		result.setSesso(it.csi.deleghe.delegheboweb.dto.deleghebowebLEGACY.Sesso.valueOf(dest.getSesso().name()));
		result.setDelegheServizi(new DelegaServizioMapper().fromList(dest.getDeleghe()));
		
		return result;
	}

}
