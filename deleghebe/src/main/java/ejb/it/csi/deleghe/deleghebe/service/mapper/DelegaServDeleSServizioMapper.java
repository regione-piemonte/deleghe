/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service.mapper;

import javax.inject.Inject;

import it.csi.deleghe.deleghebe.model.DeleSDelegaServizio;
import it.csi.deleghe.deleghebe.ws.model.DelegaServ;

public class DelegaServDeleSServizioMapper implements Mapper<DelegaServ, DeleSDelegaServizio> {



	
	@Inject
	private DelegaServStatoDeleDDelegaServizioStatoMapper delegaStatoMapper;
	
	@Inject
	private ServizioDeleDServizioMapper servizioMapper;

	
	@Override
	public DeleSDelegaServizio to(DelegaServ src) {
		throw new UnsupportedOperationException();		
	}

	@Override
	public DelegaServ from(DeleSDelegaServizio dest) {
		if(dest==null) {
			return null;
		}
		DelegaServ res = new DelegaServ();

		res.setDataScadenza(dest.getDelDatascadenza());
		res.setDataDecorrenza(dest.getDelDatadecorrenza());
		res.setDataRevoca(dest.getDelDatarevoca());
		res.setDataRinuncia(dest.getDelDatarinuncia());
		res.setStato(delegaStatoMapper.from(dest.getDeleDDelegaServizioStato()));
		res.setServizio(servizioMapper.from(dest.getDeleDServizio()));
		res.setDataCancellazione(dest.getDataCancellazione());
		return res;
	}

}
