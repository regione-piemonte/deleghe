/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service.mapper;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

import it.csi.deleghe.deleghebe.model.DeleTDelegato;
import it.csi.deleghe.deleghebe.util.DateUtil;
import it.csi.deleghe.deleghebe.ws.model.Delegato;

public class DelegatoDeleTDelegatoMapper implements Mapper<Delegato, DeleTDelegato> {

	@Inject
	private RuoloOperazioneDeleDRuoloOpMapper ruoloOperazioneDeleDRuoloOpMapper;
	
	@Override
	public DeleTDelegato to(Delegato src) {
		if(src==null) {
			return null;
		}
		DeleTDelegato res = new DeleTDelegato();
		
		res.setDlgoCf(StringUtils.upperCase(src.getCodiceFiscale()));
		res.setDlgoCognome(WordUtils.capitalizeFully(src.getCognome()));
		res.setDlgoNome(WordUtils.capitalizeFully(src.getNome()));
		res.setDlgoSesso(src.getSesso());
		res.setDlgoNascitaData(DateUtil.resetTime(src.getDataNascita()));
		res.setDlgoNascitaComune(WordUtils.capitalizeFully(src.getComuneNascita()));
		res.setDeleDRuoloOp(ruoloOperazioneDeleDRuoloOpMapper.to(src.getRuoloOperazione()));
		
		return res;
	}

	@Override
	public Delegato  from(DeleTDelegato dest) {
		if(dest==null) {
			return null;
		}
		Delegato res = new Delegato();

		res.setCodiceFiscale(StringUtils.upperCase(dest.getDlgoCf()));
		res.setCognome(WordUtils.capitalizeFully(dest.getDlgoCognome()));
		res.setNome(WordUtils.capitalizeFully(dest.getDlgoNome()));
		res.setDataNascita(DateUtil.resetTime(dest.getDlgoNascitaData()));
		res.setComuneNascita(WordUtils.capitalizeFully(dest.getDlgoNascitaComune()));
		res.setSesso(dest.getDlgoSesso());
		res.setRuoloOperazione(ruoloOperazioneDeleDRuoloOpMapper.from(dest.getDeleDRuoloOp()));

		return res;
	}

}
