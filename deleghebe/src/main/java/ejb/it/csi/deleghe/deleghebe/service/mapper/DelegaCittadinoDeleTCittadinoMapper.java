/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service.mapper;

import java.util.LinkedList;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

import it.csi.deleghe.deleghebe.model.DeleTCittadino;
import it.csi.deleghe.deleghebe.util.DateUtil;
import it.csi.deleghe.deleghebe.ws.model.DelegaCittadino;
import it.csi.deleghe.deleghebe.ws.model.DelegaServizio;
import it.csi.deleghe.deleghebe.ws.model.Sesso;

public class DelegaCittadinoDeleTCittadinoMapper implements Mapper<DelegaCittadino, DeleTCittadino> {

	@Override
	public DeleTCittadino to(DelegaCittadino src) {
		if (src == null) {
			return null;
		}
		DeleTCittadino res = new DeleTCittadino();

		res.setCitCf(StringUtils.upperCase(src.getCodiceFiscale()));
		res.setCitCognome(WordUtils.capitalizeFully(src.getCognome()));
		res.setCitNome(WordUtils.capitalizeFully(src.getNome()));
		res.setCitNascitaComune(WordUtils.capitalizeFully(src.getLuogoNascita()));
		res.setCitNascitaData(DateUtil.resetTime(src.getDataDiNascita()));
		res.setCitSesso((src.getSesso() != null)? src.getSesso().value() : null);
		res.setCitAuraid((src.getIdAura() != null) ? src.getIdAura().toString() : null);

		return res;
	}

	@Override
	public DelegaCittadino from(DeleTCittadino dest) {
		if (dest == null) {
			return null;
		}
		DelegaCittadino res = new DelegaCittadino();

		res.setCodiceFiscale(StringUtils.upperCase(dest.getCitCf()));
		res.setCognome(WordUtils.capitalizeFully(dest.getCitCognome()));
		res.setNome(WordUtils.capitalizeFully(dest.getCitNome()));
		res.setDataDiNascita(DateUtil.resetTime(dest.getCitNascitaData()));
		res.setLuogoNascita(WordUtils.capitalizeFully(dest.getCitNascitaComune()));
		res.setSesso(Sesso.fromValue(dest.getCitSesso()));
		res.setIdAura((dest.getCitAuraid() != null) ? Long.parseLong(dest.getCitAuraid()) : null);
		
		res.setDeleghe(new LinkedList<DelegaServizio>());

		return res;
	}

}
