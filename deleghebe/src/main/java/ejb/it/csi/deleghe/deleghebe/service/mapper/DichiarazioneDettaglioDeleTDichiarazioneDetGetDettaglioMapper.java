/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service.mapper;

import javax.inject.Inject;

import it.csi.deleghe.deleghebe.model.DeleDEnumerazione;
import it.csi.deleghe.deleghebe.model.DeleTCittadino;
import it.csi.deleghe.deleghebe.model.DeleTDichiarazioneDet;
import it.csi.deleghe.deleghebe.service.DeleDEnumerazioneRepository;
import it.csi.deleghe.deleghebe.service.DeleTCittadinoRepository;
import it.csi.deleghe.deleghebe.ws.model.DichiarazioneDettaglio;

public class DichiarazioneDettaglioDeleTDichiarazioneDetGetDettaglioMapper implements Mapper<DichiarazioneDettaglio, DeleTDichiarazioneDet> {


	

	@Inject
	private RuoloOperazioneDeleDRuoloOpMapper ruoloOperazioneDeleDRuoloOpMapper;

	@Inject
	private DichiarazioneDettaglioStatoDeleDDichiarazioneDetStatoMapper dichiarazioneDettaglioStatoDeleDDichiarazioneDetStatoMapper;
	@Inject
	private DocumentoDeleTDocumentoMapper documentoDeleTDocumentoMapper;
	@Inject
	private CittadinoDeleTCittadinoMapper cittadinoMapper;
	@Inject
	private DichiarazioneRuoloDeleDDichiarazioneRuoloMapper dichiarazioneRuoloDeleDDichiarazioneRuoloMapper;


	@Override
	public DeleTDichiarazioneDet to(DichiarazioneDettaglio src) {
		if(src==null) {
			return null;
		}
		
		return null;
	}

	@Override
	public DichiarazioneDettaglio from(DeleTDichiarazioneDet dest) {
		if(dest==null) {
			return null;
		}
		DichiarazioneDettaglio res = new DichiarazioneDettaglio();
		
		res.setUuid(dest.getUuid());
		res.setDocumento(documentoDeleTDocumentoMapper.from(dest.getDeleTDocumento()));
		res.setStato(dichiarazioneDettaglioStatoDeleDDichiarazioneDetStatoMapper.from(dest.getDeleDDichiarazioneDetStato()));
//		res.setDichiarazione(dichiarazioneDeleTDichiarazioneMapper.from(dest.getDeleTDichiarazione()));
		res.setRuoloOperazione(ruoloOperazioneDeleDRuoloOpMapper.from(dest.getDeleDRuoloOp()));
		res.setCittadino1(cittadinoMapper.from(dest.getDeleTCittadino1()));
		res.setCittadino2(cittadinoMapper.from(dest.getDeleTCittadino2()));
		res.setRuoloCittadino1(dichiarazioneRuoloDeleDDichiarazioneRuoloMapper.from(dest.getDeleDDichiarazioneRuolo1()));
		res.setRuoloCittadino2(dichiarazioneRuoloDeleDDichiarazioneRuoloMapper.from(dest.getDeleDDichiarazioneRuolo2()));
		res.setNotaRevocaBlocco(dest.getNoteRevocaBlocco());
		DeleDEnumerazione deleDEnumerazione = dest.getDeleDEnumerazione();
		if (deleDEnumerazione != null) {
			res.setMotivazioneMenu(deleDEnumerazione.getEnumCod());
		}
		res.setMotivazioneCasella(dest.getNotaMotivazione());
		res.setIdDettaglio(dest.getDicdetId());
		return res;
	}

}
