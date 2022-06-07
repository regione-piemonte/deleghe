/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service.mapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import it.csi.deleghe.deleghebe.model.DeleTDelega;
import it.csi.deleghe.deleghebe.ws.model.Delega;
import it.csi.deleghe.deleghebe.ws.model.DelegaServ;

public class DelegaDeleSDelegaMapper implements Mapper<Delega, DeleTDelega> {

	@Inject
	private CittadinoDeleTCittadinoMapper cittadinoMapper;
	@Inject
	private DelegatoDeleTDelegatoMapper delegatoMapper;
	@Inject
	private DelegaServDeleTServizioMapper delegaTServizioMapper;
	@Inject
	private DelegaServDeleSServizioMapper delegaSServizioMapper;
	@Inject
	private RuoloOperazioneDeleDRuoloOpMapper ruoloOperazioneDeleDRuoloOpMapper;
	
	@Override
	public DeleTDelega to(Delega src) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Delega from(DeleTDelega dest) {
		if (dest == null) {
			return null;
		}
		List<DelegaServ> servizi = new ArrayList<>(); 
		servizi.addAll(delegaTServizioMapper.fromList(dest.getDeleTDelegaServizios()));
		servizi.addAll(delegaSServizioMapper.fromList(dest.getDeleSDelegaServizios()));
		
		Delega res = new Delega();
		
		res.setUuid(dest.getUuid());
		res.setDelegante(cittadinoMapper.from(dest.getDeleTCittadino1()));
		res.setDelegato(cittadinoMapper.from(dest.getDeleTCittadino2()));
		res.setDelegatoInput(delegatoMapper.from(dest.getDeleTDelegatos().get(0)));
		res.setServizi(servizi);
		res.setRuoloOperazione(ruoloOperazioneDeleDRuoloOpMapper.from(dest.getDeleDRuoloOp()));
		res.setDataCreazione(dest.getDataCreazione());
		
		return res;
	}

}
