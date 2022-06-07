/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service.mapper;

import javax.inject.Inject;

import it.csi.deleghe.deleghebe.model.DeleDDichiarazioneDetStato;
import it.csi.deleghe.deleghebe.service.DeleDDichiarazioneDetStatoRepository;
import it.csi.deleghe.deleghebe.ws.model.DichiarazioneDettaglioStato;

public class DichiarazioneDettaglioStatoDeleDDichiarazioneDetStatoMapper implements Mapper<DichiarazioneDettaglioStato, DeleDDichiarazioneDetStato> {


	@Inject
	private RuoloOperazioneDeleDRuoloOpMapper ruoloOperazioneDeleDRuoloOpMapper;
	@Inject
	private DeleDDichiarazioneDetStatoRepository deleDDichiarazioneDetStatoRepository;
	
	@Override
	public DeleDDichiarazioneDetStato to(DichiarazioneDettaglioStato src) {
		if(src==null || src.getCodice()==null || src.getCodice().isEmpty()) {
			return null;
		}
		
		DeleDDichiarazioneDetStato dichiarazioneDetStato=deleDDichiarazioneDetStatoRepository.ricercaDetStatoByDicDetStatoCod(src.getCodice());
		
		DeleDDichiarazioneDetStato res = new DeleDDichiarazioneDetStato();
		
		if(dichiarazioneDetStato != null) {
			res=dichiarazioneDetStato;
		}
		
			
		return res;
	}

	@Override
	public DichiarazioneDettaglioStato from(DeleDDichiarazioneDetStato dest) {
		if(dest==null) {
			return null;
		}
		DichiarazioneDettaglioStato res = new DichiarazioneDettaglioStato();
		
		res.setCodice(dest.getDicdetStatoCod());
		res.setDescrizione(dest.getDicdetStatoDesc());
		res.setRuoloOperazione(ruoloOperazioneDeleDRuoloOpMapper.from(dest.getDeleDRuoloOp()));
		
		
		return res;
	}

}
