/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service.mapper;

import javax.inject.Inject;

import it.csi.deleghe.deleghebe.model.DeleDDichiarazioneRuolo;
import it.csi.deleghe.deleghebe.service.DeleDDichiarazioneRuoloRepository;
import it.csi.deleghe.deleghebe.ws.model.DichiarazioneRuolo;

public class DichiarazioneRuoloDeleDDichiarazioneRuoloMapper implements Mapper<DichiarazioneRuolo, DeleDDichiarazioneRuolo> {


	@Inject
	private RuoloOperazioneDeleDRuoloOpMapper ruoloOperazioneDeleDRuoloOpMapper;
	@Inject
	private DeleDDichiarazioneRuoloRepository deleDDichiarazioneRuoloRepository;
	
	@Override
	public DeleDDichiarazioneRuolo to(DichiarazioneRuolo src) {
		if(src==null  || src.getCodice()==null || src.getCodice().isEmpty()) {
			return null;
		}
		
		DeleDDichiarazioneRuolo deleDDichiarazioneRuolo=deleDDichiarazioneRuoloRepository.ricercaServiziByDicRuoloCod(src.getCodice());
		
		DeleDDichiarazioneRuolo res = new DeleDDichiarazioneRuolo();
		
		if(deleDDichiarazioneRuolo != null) {
			res = deleDDichiarazioneRuolo;
		}
		
		return res;
	}

	@Override
	public DichiarazioneRuolo from(DeleDDichiarazioneRuolo dest) {
		if(dest==null) {
			return null;
		}
		DichiarazioneRuolo res = new DichiarazioneRuolo();
		
		res.setCodice(dest.getDicRuoloCod());
		res.setDescrizione(dest.getDicRuoloDesc());
		res.setRuoloOperazione(ruoloOperazioneDeleDRuoloOpMapper.from(dest.getDeleDRuoloOp()));
		
		
		return res;
	}

}
