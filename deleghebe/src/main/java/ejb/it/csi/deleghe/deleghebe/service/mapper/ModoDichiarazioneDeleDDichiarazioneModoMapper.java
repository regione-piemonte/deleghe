/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service.mapper;

import javax.inject.Inject;

import it.csi.deleghe.deleghebe.model.DeleDDichiarazioneModo;
import it.csi.deleghe.deleghebe.service.DeleDDichiarazioneModoRepository;
import it.csi.deleghe.deleghebe.ws.model.ModoDichiarazione;

public class ModoDichiarazioneDeleDDichiarazioneModoMapper implements Mapper<ModoDichiarazione, DeleDDichiarazioneModo> {


	@Inject
	private RuoloOperazioneDeleDRuoloOpMapper ruoloOperazioneDeleDRuoloOpMapper;
	@Inject
	private DeleDDichiarazioneModoRepository deleDDichiarazioneModoRepository;
	
	@Override
	public DeleDDichiarazioneModo to(ModoDichiarazione src) {
		if(src==null  || src.getCodice()==null || src.getCodice().isEmpty()) {
			return null;
		}
		
		DeleDDichiarazioneModo deleDDichiarazioneModo=deleDDichiarazioneModoRepository.ricercaServiziByDicModoCod(src.getCodice());
		
		DeleDDichiarazioneModo res = new DeleDDichiarazioneModo();
		
		if(deleDDichiarazioneModo != null) {
			res = deleDDichiarazioneModo;
		}
		
		return res;
	}

	@Override
	public ModoDichiarazione from(DeleDDichiarazioneModo dest) {
		if(dest==null) {
			return null;
		}
		ModoDichiarazione res = new ModoDichiarazione();
		
		res.setCodice(dest.getDicModoCod());
		res.setDescrizione(dest.getDicModoDesc());
		res.setRuoloOperazione(ruoloOperazioneDeleDRuoloOpMapper.from(dest.getDeleDRuoloOp()));
		
		return res;
	}

}
