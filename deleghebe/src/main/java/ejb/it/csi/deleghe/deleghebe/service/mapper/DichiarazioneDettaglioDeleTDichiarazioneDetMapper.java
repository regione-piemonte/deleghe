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

public class DichiarazioneDettaglioDeleTDichiarazioneDetMapper implements Mapper<DichiarazioneDettaglio, DeleTDichiarazioneDet> {


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
	@Inject
	private DeleTCittadinoRepository deleTCittadinoRepository;
	@Inject
	private DeleDEnumerazioneRepository deleDEnumerazioneRepository;

	@Override
	public DeleTDichiarazioneDet to(DichiarazioneDettaglio src) {
		if(src==null) {
			return null;
		}
		
		DeleTDichiarazioneDet res = new DeleTDichiarazioneDet();
		res.setUuid(src.getUuid());
		res.setPresavisione(Boolean.FALSE); //necessaria in quanto colonna NOT NULL
		res.setNoteRevocaBlocco(src.getNotaRevocaBlocco());


		res.setDeleDDichiarazioneDetStato(dichiarazioneDettaglioStatoDeleDDichiarazioneDetStatoMapper.to(src.getStato()));

		res.setDeleTDocumento(documentoDeleTDocumentoMapper.to(src.getDocumento()));
		
		res.setDeleDRuoloOp(ruoloOperazioneDeleDRuoloOpMapper.to(src.getRuoloOperazione()));
		

		if(src.getCittadino1()!=null && !src.getCittadino1().getCodiceFiscale().isEmpty()) {
			res.setDeleTCittadino1(cittadinoMapper.to(src.getCittadino1()));
			DeleTCittadino searchCittadino1=deleTCittadinoRepository.ricercaCittadino(src.getCittadino1().getCodiceFiscale());
			if(searchCittadino1 == null) {
				res.setDeleTCittadino1(cittadinoMapper.to(src.getCittadino1()));
			}
			else {
				res.setDeleTCittadino1(searchCittadino1);
			}
		}
		if(src.getCittadino2()!=null && !src.getCittadino2().getCodiceFiscale().isEmpty()) {
			DeleTCittadino searchCittadino2=deleTCittadinoRepository.ricercaCittadino(src.getCittadino2().getCodiceFiscale());
			if(searchCittadino2 == null) {
				res.setDeleTCittadino2(cittadinoMapper.to(src.getCittadino1()));
			}
			else {
				res.setDeleTCittadino2(searchCittadino2);
			}
		}
		
		res.setDeleDDichiarazioneRuolo1(dichiarazioneRuoloDeleDDichiarazioneRuoloMapper.to(src.getRuoloCittadino1()));
		res.setDeleDDichiarazioneRuolo2(dichiarazioneRuoloDeleDDichiarazioneRuoloMapper.to(src.getRuoloCittadino2()));
		String motivazioneMenu = src.getMotivazioneMenu();
		if (motivazioneMenu != null) {
			res.setDeleDEnumerazione(deleDEnumerazioneRepository.ricercaDeleDEnumerazioneByEnumCod(motivazioneMenu));
		}
		res.setNotaMotivazione(src.getMotivazioneCasella());
		res.setDicdetId(src.getIdDettaglio());
		return res;
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
