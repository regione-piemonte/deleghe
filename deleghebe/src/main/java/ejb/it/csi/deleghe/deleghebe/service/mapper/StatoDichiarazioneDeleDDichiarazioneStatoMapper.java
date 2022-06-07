/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service.mapper;

import javax.inject.Inject;

import it.csi.deleghe.deleghebe.model.DeleDDichiarazioneStato;
import it.csi.deleghe.deleghebe.service.DeleDDichiarazioneStatoRepository;
import it.csi.deleghe.deleghebe.ws.model.StatoDichiarazione;

public class StatoDichiarazioneDeleDDichiarazioneStatoMapper implements Mapper<StatoDichiarazione, DeleDDichiarazioneStato> {


	
	@Inject
	private RuoloOperazioneDeleDRuoloOpMapper ruoloOperazioneDeleDRuoloOpMapper;
	@Inject
	private DeleDDichiarazioneStatoRepository deleDDichiarazioneStatoRepository;
	
	
	@Override
	public DeleDDichiarazioneStato to(StatoDichiarazione src) {
		if(src==null || src.getCodice()==null || src.getCodice().isEmpty()) {
			return null;
		}
		
		DeleDDichiarazioneStato deleDDichiarazioneStato=deleDDichiarazioneStatoRepository.ricercaDichiarazioneStatoByDicStatoCod(src.getCodice());
		
		DeleDDichiarazioneStato res = new DeleDDichiarazioneStato();
		
		if(deleDDichiarazioneStato != null) {
			res = deleDDichiarazioneStato;
		}
		
		return res;
	}

	@Override
	public StatoDichiarazione from(DeleDDichiarazioneStato dest) {
		if(dest==null) {
			return null;
		}
		StatoDichiarazione res = new StatoDichiarazione();
		
		res.setCodice(dest.getDicStatoCod());
		res.setDescrizione(dest.getDicStatoDesc());
		res.setRuoloOperazione(ruoloOperazioneDeleDRuoloOpMapper.from(dest.getDeleDRuoloOp()));
		
		return res;
	}

}
