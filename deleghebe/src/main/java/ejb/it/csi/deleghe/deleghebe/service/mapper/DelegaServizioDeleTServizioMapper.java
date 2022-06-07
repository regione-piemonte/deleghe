/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service.mapper;

import java.util.UUID;

import javax.inject.Inject;

import it.csi.deleghe.deleghebe.model.DeleDDelegaServizioStato;
import it.csi.deleghe.deleghebe.model.DeleTDelegaServizio;
import it.csi.deleghe.deleghebe.service.DeleDDelegaServizioStatoRepository;
import it.csi.deleghe.deleghebe.ws.model.DelegaServizio;

public class DelegaServizioDeleTServizioMapper implements Mapper<DelegaServizio, DeleTDelegaServizio> {



	@Inject
	private DeleDDelegaServizioStatoRepository deleDDelegaServizioStatoRepository;
	
	@Override
	public DeleTDelegaServizio to(DelegaServizio src) {
		if(src==null) {
			return null;
		}
		
		DeleTDelegaServizio res = new DeleTDelegaServizio();
		res.setUuid(UUID.fromString(src.getUUID()));
		res.setDelDatadecorrenza(src.getDataInizioDelega());
		res.setDelDatascadenza(src.getDataFineDelega());
		res.setDelDatarevoca(src.getDataRevoca());
		res.setDelDatarinuncia(src.getDataRinuncia());
		DeleDDelegaServizioStato deleDDelegaServizioStato=deleDDelegaServizioStatoRepository.ricercaServiziStatoByDelstatoCod(src.getStatoDelega());
		res.setDeleDDelegaServizioStato(deleDDelegaServizioStato);
		
		return res;
	}

	@Override
	public DelegaServizio from(DeleTDelegaServizio dest) {
		if(dest==null) {
			return null;
		}
		DelegaServizio res = new DelegaServizio();

		res.setDataInizioDelega(dest.getDelDatadecorrenza());
		res.setDataFineDelega(dest.getDelDatascadenza());
		res.setUUID(dest.getUuid().toString());
		res.setDataRevoca(dest.getDelDatarevoca());
		res.setDataRinuncia(dest.getDelDatarinuncia());
		res.setStatoDelega(dest.getDeleDDelegaServizioStato().getDelstatoCod());

		return res;
	}

}
