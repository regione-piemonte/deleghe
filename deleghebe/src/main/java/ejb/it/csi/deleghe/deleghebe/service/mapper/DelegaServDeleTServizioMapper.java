/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service.mapper;

import javax.inject.Inject;

import it.csi.deleghe.deleghebe.model.DeleDDelegaServizioStato;
import it.csi.deleghe.deleghebe.model.DeleDDelegaTipo;
import it.csi.deleghe.deleghebe.model.DeleDGrado;
import it.csi.deleghe.deleghebe.model.DeleTDelegaServizio;
import it.csi.deleghe.deleghebe.service.DeleDGradoRepository;
import it.csi.deleghe.deleghebe.ws.model.DelegaServ;

public class DelegaServDeleTServizioMapper implements Mapper<DelegaServ, DeleTDelegaServizio> {



	
	@Inject
	private DelegaServStatoDeleDDelegaServizioStatoMapper delegaStatoMapper;
	
	@Inject
	private ServizioDeleDServizioMapper servizioMapper;
	
	@Inject
	private DeleDGradoRepository deleDGradoRepository;

	
	@Override
	public DeleTDelegaServizio to(DelegaServ src) {
		if(src==null) {
			return null;
		}
		
		DeleTDelegaServizio res = new DeleTDelegaServizio();
		res.setUuid(src.getUuid());
		res.setDelDatarevoca(src.getDataRevoca());
		res.setDelDatarinuncia(src.getDataRinuncia());
		res.setDelDatadecorrenza(src.getDataDecorrenza());
		res.setDelDatascadenza(src.getDataScadenza());
		
		DeleDDelegaServizioStato deleDDelegaServizioStato = delegaStatoMapper.to(src.getStato());
		res.setDeleDDelegaServizioStato(deleDDelegaServizioStato);
		

		if(src.getGradoDelega()!=null) {
			DeleDGrado deleDGrado = deleDGradoRepository.ricercaGradoServizioByCodice(src.getGradoDelega());	
			if(deleDGrado !=null) {
				  res.setDeleDGrado(deleDGrado);
			}
		}

		
		
		res.setDeleDServizio(servizioMapper.to(src.getServizio()));
		
		return res;
	}

	@Override
	public DelegaServ from(DeleTDelegaServizio dest) {
		if(dest==null) {
			return null;
		}
		DelegaServ res = new DelegaServ();

		res.setUuid(dest.getUuid());
		res.setDataScadenza(dest.getDelDatascadenza());
		res.setDataDecorrenza(dest.getDelDatadecorrenza());
		res.setDataRevoca(dest.getDelDatarevoca());
		res.setDataRinuncia(dest.getDelDatarinuncia());
		res.setStato(delegaStatoMapper.from(dest.getDeleDDelegaServizioStato()));
		res.setServizio(servizioMapper.from(dest.getDeleDServizio()));
		res.setDataCancellazione(dest.getDataCancellazione());
		DeleDGrado deleDGrado = dest.getDeleDGrado();
		if(deleDGrado != null) {
			res.setGradoDelega(deleDGrado.getDelGradoCod());
		}

		DeleDDelegaTipo deleDDelegaTipo = dest.getDeleTDelega().getDeleDDelegaTipo();
		if(deleDDelegaTipo != null) {
			res.setTipoDelega(deleDDelegaTipo.getDelTipCod());
		}

		return res;
	}

}
