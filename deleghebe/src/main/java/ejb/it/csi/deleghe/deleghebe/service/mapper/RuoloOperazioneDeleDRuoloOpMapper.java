/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service.mapper;

import javax.inject.Inject;

import it.csi.deleghe.deleghebe.model.DeleDRuoloOp;
import it.csi.deleghe.deleghebe.service.DeleDRuoloOpRepository;
import it.csi.deleghe.deleghebe.ws.model.RuoloOperazione;

public class RuoloOperazioneDeleDRuoloOpMapper implements Mapper<RuoloOperazione, DeleDRuoloOp> {



	@Inject
	private DeleDRuoloOpRepository deleDRuoloOpRepository;
	
	@Override
	public DeleDRuoloOp to(RuoloOperazione src) {
		if(src==null  || src.getCodice()==null || src.getCodice().isEmpty()) {
			return null;
		}
		
		DeleDRuoloOp deleDRuoloOp=deleDRuoloOpRepository.ricercaServiziByRuoloOpCod(src.getCodice());
		
		DeleDRuoloOp res = new DeleDRuoloOp();
		if(deleDRuoloOp !=null) {
		  res=deleDRuoloOp;
		}
		
		return res;
	}

	@Override
	public RuoloOperazione from(DeleDRuoloOp dest) {
		if(dest==null) {
			return null;
		}
		RuoloOperazione res = new RuoloOperazione();
		
		res.setCodice(dest.getRuoloopCod());
		res.setDescrizione(dest.getRuoloopDesc());
		
		return res;
	}

}
