/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service.mapper;

import javax.inject.Inject;

import it.csi.deleghe.deleghebe.model.DeleDCittadinoDatiStato;
import it.csi.deleghe.deleghebe.model.DeleDCittadinoUtenzaStato;
import it.csi.deleghe.deleghebe.service.DeleDCittadinoDatiStatoRepository;
import it.csi.deleghe.deleghebe.service.DeleDCittadinoUtenzaStatoRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

import it.csi.deleghe.deleghebe.model.DeleTCittadino;
import it.csi.deleghe.deleghebe.util.DateUtil;
import it.csi.deleghe.deleghebe.ws.model.Cittadino;

public class CittadinoDeleTCittadinoMapper implements Mapper<Cittadino, DeleTCittadino> {

	@Inject
	private RuoloOperazioneDeleDRuoloOpMapper ruoloOperazioneDeleDRuoloOpMapper;
	@Inject
	private DeleDCittadinoDatiStatoRepository deleDCittadinoDatiStatoRepository;
	@Inject
	private DeleDCittadinoUtenzaStatoRepository deleDCittadinoUtenzaStatoRepository;
	@Inject
   DocumentoDeleTDocumentoMapper documentoDeleTDocumentoMapper;

	@Override
	public DeleTCittadino to(Cittadino src) {
		if (src == null) {
			return null;
		}

		DeleTCittadino res = new DeleTCittadino();

		res.setCitCf(StringUtils.upperCase(src.getCodiceFiscale()));
		res.setCitCognome(WordUtils.capitalizeFully(src.getCognome()));
		res.setCitNome(WordUtils.capitalizeFully(src.getNome()));
		res.setCitNascitaData(DateUtil.resetTime(src.getDataNascita()));
		res.setCitNascitaComune(WordUtils.capitalizeFully(src.getComuneNascita()));
		res.setCitSesso(src.getSesso());
		res.setCitAuraid((src.getIdAura() != null) ? src.getIdAura().toString() : null);
		res.setCitAsl(src.getAsl());
		res.setCitId(src.getCitId());

		res.setDeleDRuoloOp(ruoloOperazioneDeleDRuoloOpMapper.to(src.getRuoloOperazione()));

      String statoDati = src.getStatoDati();
      if (statoDati != null) {
         res.setDeleDCittadinoDatiStato(deleDCittadinoDatiStatoRepository.ricercaServiziByCitDatiStatoCod(statoDati));
      }
      String statoUtenza = src.getStatoUtenza();
      if (statoUtenza != null) {
         res.setDeleDCittadinoUtenzaStato(deleDCittadinoUtenzaStatoRepository.ricercaServiziByCitUtenzaStatoCod(statoUtenza));
      }
      res.setDeleTDocumento(documentoDeleTDocumentoMapper.to(src.getDocumento()));
      res.setVerificatoAsl(src.isVerificatoAsl());

      return res;
	}

	@Override
	public Cittadino from(DeleTCittadino dest) {
		if (dest == null) {
			return null;
		}
		Cittadino res = new Cittadino();

		res.setCodiceFiscale(StringUtils.upperCase(dest.getCitCf()));
		res.setCognome(WordUtils.capitalizeFully(dest.getCitCognome()));
		res.setNome(WordUtils.capitalizeFully(dest.getCitNome()));
		res.setSesso(dest.getCitSesso());
		res.setDataNascita(dest.getCitNascitaData());
		res.setComuneNascita(WordUtils.capitalizeFully(dest.getCitNascitaComune()));
		res.setRuoloOperazione(ruoloOperazioneDeleDRuoloOpMapper.from(dest.getDeleDRuoloOp()));
		res.setIdAura((dest.getCitAuraid() != null)? Long.parseLong(dest.getCitAuraid()) : null);
		res.setCitId(dest.getCitId());
		res.setAsl(dest.getCitAsl());

		DeleDCittadinoDatiStato deleDCittadinoDatiStato = dest.getDeleDCittadinoDatiStato();
		if (deleDCittadinoDatiStato != null) {
			res.setStatoDati(deleDCittadinoDatiStato.getCitdstatoCod());
		}
		DeleDCittadinoUtenzaStato deleDCittadinoUtenzaStato = dest.getDeleDCittadinoUtenzaStato();
		if (deleDCittadinoUtenzaStato != null) {
			res.setStatoUtenza(deleDCittadinoUtenzaStato.getCitustatoCod());
		}
		res.setDocumento(documentoDeleTDocumentoMapper.from(dest.getDeleTDocumento()));
		Boolean verificatoAsl = dest.getVerificatoAsl();
		if (verificatoAsl != null) {
			res.setVerificatoAsl(verificatoAsl);
		}
		return res;
	}

}
