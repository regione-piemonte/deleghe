/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service.mapper;

import javax.inject.Inject;

import it.csi.deleghe.deleghebe.model.DeleDDichiarazioneTipo;
import it.csi.deleghe.deleghebe.service.DeleDDichiarazioneTipoRepository;
import it.csi.deleghe.deleghebe.ws.model.TipoDichiarazione;

public class TipoDichiarazioneDeleDDichiarazioneTipoMapper implements Mapper<TipoDichiarazione, DeleDDichiarazioneTipo> {



	@Inject
	private RuoloOperazioneDeleDRuoloOpMapper ruoloOperazioneDeleDRuoloOpMapper;
	@Inject
	private DeleDDichiarazioneTipoRepository deleDDichiarazioneTipoRepository;
	
	@Override
	public DeleDDichiarazioneTipo to(TipoDichiarazione src) {
		if(src==null  || src.getCodice()==null || src.getCodice().isEmpty()) {
			return null;
		}
		
		DeleDDichiarazioneTipo deleDDichiarazioneTipo=deleDDichiarazioneTipoRepository.ricercaServiziByDicTipoCod(src.getCodice());
		
		DeleDDichiarazioneTipo res = new DeleDDichiarazioneTipo();
		
		if(deleDDichiarazioneTipo != null) {
			res = deleDDichiarazioneTipo;
		}
		
		return res;
	}

	@Override
	public TipoDichiarazione from(DeleDDichiarazioneTipo dest) {
		if(dest==null) {
			return null;
		}
		TipoDichiarazione res = new TipoDichiarazione();
		
		res.setCodice(dest.getDicTipoCod());
		res.setDescrizione(dest.getDicTipoDesc());
		res.setRuoloOperazione(ruoloOperazioneDeleDRuoloOpMapper.from(dest.getDeleDRuoloOp()));
		
		return res;
	}

}
