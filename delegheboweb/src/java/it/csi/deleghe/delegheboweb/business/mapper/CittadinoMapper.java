/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.delegheboweb.business.mapper;

import it.csi.deleghe.delegheboweb.dto.delegheboweb.Cittadino;

public class CittadinoMapper extends BaseMapper<Cittadino, it.csi.deleghe.deleghebe.ws.model.Cittadino> {

	@Override
	public it.csi.deleghe.deleghebe.ws.model.Cittadino to(Cittadino source) {
		if(source == null) {
			return null;
		}
		it.csi.deleghe.deleghebe.ws.model.Cittadino result = new it.csi.deleghe.deleghebe.ws.model.Cittadino();
		
		result.setIdAura(source.getIdAura()!=null?source.getIdAura().longValue():null);
		result.setCodiceFiscale(source.getCodiceFiscale());
		result.setCognome(source.getCognome());
		result.setNome(source.getNome());
		result.setDataNascita(source.getDataNascita());
		result.setComuneNascita(source.getComuneNascita());
		result.setSesso(source.getSesso());
		result.setAsl(source.getAsl());
		result.setDocumento(new DocumentoMapper().to(source.getDocumento()));

		
		return result;
	}

	@Override
	public Cittadino from(it.csi.deleghe.deleghebe.ws.model.Cittadino dest) {
		if(dest == null) {
			return null;
		}
		Cittadino result = new Cittadino();
		
		result.setIdAura(dest.getIdAura()!=null?dest.getIdAura().intValue():null);
		result.setCodiceFiscale(dest.getCodiceFiscale());
		result.setCognome(dest.getCognome());
		result.setNome(dest.getNome());
		result.setDataNascita(dest.getDataNascita());
		result.setComuneNascita(dest.getComuneNascita());
		result.setSesso(dest.getSesso());
		result.setAsl(dest.getAsl());
		result.setCitId(dest.getCitId());
		result.setDocumento(new DocumentoMapper().from(dest.getDocumento()));   
		return result;
	}

}
