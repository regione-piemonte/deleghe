/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service.mapper;

import javax.inject.Inject;

import it.csi.deleghe.deleghebe.model.DeleTCittadino;
import it.csi.deleghe.deleghebe.model.DeleTDichiarazione;
import it.csi.deleghe.deleghebe.service.DeleTCittadinoRepository;
import it.csi.deleghe.deleghebe.ws.model.Dichiarazione;

public class DichiarazioneDeleTDichiarazioneMapper implements Mapper<Dichiarazione, DeleTDichiarazione> {

	@Inject
	private CittadinoDeleTCittadinoMapper cittadinoMapper;
	@Inject
	private DichiarazioneDettaglioDeleTDichiarazioneDetMapper dichiarazioneDettaglioDeleTDichiarazioneDetMapper;
	@Inject
	private ModoDichiarazioneDeleDDichiarazioneModoMapper modoDichiarazioneDeleDDichiarazioneModoMapper;
	@Inject
	private StatoDichiarazioneDeleDDichiarazioneStatoMapper statoDichiarazioneDeleDDichiarazioneStatoMapper;
	@Inject
	private TipoDichiarazioneDeleDDichiarazioneTipoMapper tipoDichiarazioneDeleDDichiarazioneTipoMapper;
	@Inject
	private RuoloOperazioneDeleDRuoloOpMapper ruoloOperazioneDeleDRuoloOpMapper;
	@Inject
	private DeleTCittadinoRepository deleTCittadinoRepository;
	
	@Override
	public DeleTDichiarazione to(Dichiarazione src) {
		if(src==null) {
			return null;
		}
		DeleTDichiarazione res = new DeleTDichiarazione();
		res.setUuid(src.getUuid());
		
		res.setDeleDDichiarazioneModo(modoDichiarazioneDeleDDichiarazioneModoMapper.to(src.getModo()));
		res.setDeleDDichiarazioneStato(statoDichiarazioneDeleDDichiarazioneStatoMapper.to(src.getStato()));
		res.setDeleDDichiarazioneTipo(tipoDichiarazioneDeleDDichiarazioneTipoMapper.to(src.getTipo()));
		res.setDeleDRuoloOp(ruoloOperazioneDeleDRuoloOpMapper.to(src.getRuoloOperazione()));
		res.setDataCreazione(src.getDataCreazione());

		res.setNumeroDocumento(src.getNumeroDocumento());
		res.setAutorita(src.getAutorita());
		res.setDataInizioTutela(src.getDataInizioTutela());
		res.setDataFineTutela(src.getDataFineTutela());
		

		if(src.getCittadino()!=null && !src.getCittadino().getCodiceFiscale().isEmpty()) {
			DeleTCittadino searchCittadino=deleTCittadinoRepository.ricercaCittadino(src.getCittadino().getCodiceFiscale());
			if(searchCittadino == null) {
				res.setDeleTCittadino(cittadinoMapper.to(src.getCittadino()));
			}
			else {
				res.setDeleTCittadino(searchCittadino);
			}
		}
		
		res.setDeleTDichiarazioneDets(dichiarazioneDettaglioDeleTDichiarazioneDetMapper.toList(src.getDettagli()));

		if(res.getDeleTDichiarazioneDets()!=null) {
			res.getDeleTDichiarazioneDets().forEach(dd -> {
				dd.setDeleTDichiarazione(res);
			});
		}
		res.setCompilatoreCf(src.getCompilatoreCF());
		res.setDicId(src.getnPratica());
		return res;
	}

	@Override
	public Dichiarazione from(DeleTDichiarazione dest) {
		if(dest==null) {
			return null;
		}
		Dichiarazione res = new Dichiarazione();
		
		res.setUuid(dest.getUuid());
		res.setCittadino(cittadinoMapper.from(dest.getDeleTCittadino()));
		res.setDettagli(dichiarazioneDettaglioDeleTDichiarazioneDetMapper.fromList(dest.getDeleTDichiarazioneDets()));
		res.setModo(modoDichiarazioneDeleDDichiarazioneModoMapper.from(dest.getDeleDDichiarazioneModo()));
		res.setStato(statoDichiarazioneDeleDDichiarazioneStatoMapper.from(dest.getDeleDDichiarazioneStato()));
		res.setTipo(tipoDichiarazioneDeleDDichiarazioneTipoMapper.from(dest.getDeleDDichiarazioneTipo()));
		res.setRuoloOperazione(ruoloOperazioneDeleDRuoloOpMapper.from(dest.getDeleDRuoloOp()));
		res.setDataCreazione(dest.getDataCreazione());
		res.setCompilatoreCF(dest.getCompilatoreCf());
		res.setnPratica(dest.getDicId());
	
		res.setNumeroDocumento(dest.getNumeroDocumento());
		res.setAutorita(dest.getAutorita());
		res.setDataInizioTutela(dest.getDataInizioTutela());
		res.setDataFineTutela(dest.getDataFineTutela());
		return res;
	}

}
