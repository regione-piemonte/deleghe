/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service.mapper;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

import it.csi.deleghe.deleghebe.model.DeleDCittadinoDatiStato;
import it.csi.deleghe.deleghebe.model.DeleDCittadinoUtenzaStato;
import it.csi.deleghe.deleghebe.model.DeleTCittadino;
import it.csi.deleghe.deleghebe.model.DeleTDichiarazione;
import it.csi.deleghe.deleghebe.service.DeleTCittadinoRepository;
import it.csi.deleghe.deleghebe.ws.model.Cittadino;
import it.csi.deleghe.deleghebe.ws.model.Dichiarazione;
import it.csi.deleghe.deleghebe.ws.model.ModoDichiarazione;
import it.csi.deleghe.deleghebe.ws.model.StatoDichiarazione;
import it.csi.deleghe.deleghebe.ws.model.TipoDichiarazione;

public class DichiarazioneDeleTDichiarazioneGetDettaglioMapper implements Mapper<Dichiarazione, DeleTDichiarazione> {


	@Inject
	private DichiarazioneDettaglioDeleTDichiarazioneDetGetDettaglioMapper dichiarazioneDettaglioDeleTDichiarazioneDetMapper;

	@Inject 
	private DocumentoDeleTDocumentoMapper documentoDeleTDocumentoMapper;
	
	@Override
	public DeleTDichiarazione to(Dichiarazione src) {
		return null;
	
	}

	@Override
	public Dichiarazione from(DeleTDichiarazione dest) {
		if(dest==null) {
			return null;
		}
		Dichiarazione res = new Dichiarazione();
		
		res.setUuid(dest.getUuid());
				
		Cittadino cittadino = new Cittadino();

		cittadino.setCodiceFiscale(StringUtils.upperCase(dest.getDeleTCittadino().getCitCf()));
		cittadino.setCognome(WordUtils.capitalizeFully(dest.getDeleTCittadino().getCitCognome()));
		cittadino.setNome(WordUtils.capitalizeFully(dest.getDeleTCittadino().getCitNome()));
		cittadino.setSesso(dest.getDeleTCittadino().getCitSesso());
		cittadino.setDataNascita(dest.getDeleTCittadino().getCitNascitaData());
		cittadino.setIdAura((dest.getDeleTCittadino().getCitAuraid() != null)? Long.parseLong(dest.getDeleTCittadino().getCitAuraid()) : null);
		cittadino.setAsl(dest.getDeleTCittadino().getCitAsl());

		cittadino.setDocumento(documentoDeleTDocumentoMapper.from(dest.getDeleTCittadino().getDeleTDocumento()));
		res.setCittadino(cittadino);

		ModoDichiarazione modoDichiarazione = new ModoDichiarazione();
		modoDichiarazione.setCodice(dest.getDeleDDichiarazioneModo().getDicModoCod());
		modoDichiarazione.setDescrizione(dest.getDeleDDichiarazioneModo().getDicModoDesc());
		res.setModo(modoDichiarazione);
		
		StatoDichiarazione statoDichiarazione = new StatoDichiarazione();
		statoDichiarazione.setCodice(dest.getDeleDDichiarazioneStato().getDicStatoCod());
		statoDichiarazione.setDescrizione(dest.getDeleDDichiarazioneStato().getDicStatoDesc());
		res.setStato(statoDichiarazione);

		TipoDichiarazione tipoDichiarazione = new TipoDichiarazione();
		tipoDichiarazione.setCodice(dest.getDeleDDichiarazioneTipo().getDicTipoCod());
		tipoDichiarazione.setDescrizione(dest.getDeleDDichiarazioneTipo().getDicTipoDesc());
		res.setTipo(tipoDichiarazione);
		
		res.setDettagli(dichiarazioneDettaglioDeleTDichiarazioneDetMapper.fromList(dest.getDeleTDichiarazioneDets()));
			
		
		res.setDataCreazione(dest.getDataCreazione());
		res.setCompilatoreCF(dest.getCompilatoreCf());
		res.setnPratica(dest.getDicId());
		return res;
	}

}
