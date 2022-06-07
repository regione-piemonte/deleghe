/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service.mapper;

import javax.inject.Inject;

import it.csi.deleghe.deleghebe.model.DeleDDelegaServizioStato;
import it.csi.deleghe.deleghebe.service.DeleDDelegaServizioStatoRepository;
import it.csi.deleghe.deleghebe.ws.model.DelegaServStato;

public class DelegaServStatoDeleDDelegaServizioStatoMapper implements Mapper<DelegaServStato, DeleDDelegaServizioStato > {

	@Inject
	private DeleDDelegaServizioStatoRepository deleDDelegaServizioStatoRepository;
	
	@Override
	public DeleDDelegaServizioStato to(DelegaServStato src) {
		if(src==null || src.getCodice()==null  || src.getCodice().isEmpty()) {
			return null;
		}
		
		DeleDDelegaServizioStato deleDDelegaServizioStato=deleDDelegaServizioStatoRepository.ricercaServiziStatoByDelstatoCod(src.getCodice());
		
		DeleDDelegaServizioStato res = new DeleDDelegaServizioStato();
		if(deleDDelegaServizioStato !=null )
			res=deleDDelegaServizioStato;

		
		return res;
	}

	@Override
	public DelegaServStato from(DeleDDelegaServizioStato dest) {
		if(dest==null) {
			return null;
		}
		DelegaServStato res = new DelegaServStato();
		
		res.setCodice(dest.getDelstatoCod());
		res.setDescrizione(dest.getDelstatoDesc());
		res.setDataCancellazione(dest.getDataCancellazione());
		res.setDataModifica(dest.getDataModifica());
		res.setLoginOperazione(dest.getLoginOperazione());
		
		
		return res;
	}

}
