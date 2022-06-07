/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import it.csi.deleghe.deleghebe.model.DeleDConsInformativa;
import it.csi.deleghe.deleghebe.model.DeleDDelegaStato;
import it.csi.deleghe.deleghebe.model.DeleDDelegaTipo;
import it.csi.deleghe.deleghebe.model.DeleDDichiarazioneDetStato;
import it.csi.deleghe.deleghebe.model.DeleDDichiarazioneStato;
import it.csi.deleghe.deleghebe.model.DeleDDichiarazioneTipo;
import it.csi.deleghe.deleghebe.model.DeleDDocumentoTipo;
import it.csi.deleghe.deleghebe.service.mapper.StatoDelegaDeleDDelegaStatoMapper;
import it.csi.deleghe.deleghebe.service.mapper.StatoDichiarazioneDeleDDichiarazioneStatoMapper;
import it.csi.deleghe.deleghebe.service.mapper.TipoDelegaDeleDDelegaTipoMapper;
import it.csi.deleghe.deleghebe.service.mapper.TipoDichiarazioneDeleDDichiarazioneTipoMapper;
import it.csi.deleghe.deleghebe.ws.model.DocumentoTipo;
import it.csi.deleghe.deleghebe.ws.model.InformativaConsensi;
import it.csi.deleghe.deleghebe.ws.model.StatoDelega;
import it.csi.deleghe.deleghebe.ws.model.StatoDichiarazione;
import it.csi.deleghe.deleghebe.ws.model.TipoDelega;
import it.csi.deleghe.deleghebe.ws.model.TipoDichiarazione;

@Stateless
public class CodificheBean {

	@Inject
	private StatoDichiarazioneDeleDDichiarazioneStatoMapper dichiarazioneStatoMapper;
	
	@Inject
	private StatoDelegaDeleDDelegaStatoMapper delegaStatoMapper;
	
	@Inject
	private TipoDelegaDeleDDelegaTipoMapper delegaTipoMapper;
	
	@Inject
	private TipoDichiarazioneDeleDDichiarazioneTipoMapper dichiarazioneTipoMapper;
	
	@Inject 
	private DeleDDichiarazioneStatoRepository deleDDichiarazioneStatoRepository;
	
	
	@Inject 
	private DeleDDichiarazioneDetStatoRepository deleDDichiarazioneDetStatoRepository;
	
	@Inject 
	private DeleDDichiarazioneTipoRepository deleDDichiarazioneTipoRepository;
	
	@Inject 
	private DeleDDelegaStatoRepository deleDDelegaStatoRepository;
	
	@Inject 
	private DeleDDelegaTipoRepository deleDDelegaTipoRepository;
	
	@Inject 
	private DeleDDocumentoTipoRepository deleDDocumentoTipoRepository;
	
	@Inject 
	private DeleDConsInformativaRepository deleDConsInformativaRepository;

	public CodificheBean() {
		// TODO Auto-generated constructor stub
	}
	
	public List<DocumentoTipo> ricercaDescrizioneDocumenti() {
		List<DocumentoTipo> result = new ArrayList<DocumentoTipo>();
		for (DeleDDocumentoTipo deleDDocumentoTipo : deleDDocumentoTipoRepository.ricercaDescrizioneDocumenti()) {
			DocumentoTipo documentoTipo= new DocumentoTipo();
			documentoTipo.setCodice(deleDDocumentoTipo.getDocTipoCod());
			documentoTipo.setDescrizione(deleDDocumentoTipo.getDocTipoDesc());
			result.add(documentoTipo);
		}
		
		return result;
	}

	
	public List<StatoDichiarazione> ricercaStatiDichiarazione() {

		List<StatoDichiarazione> result = new ArrayList<StatoDichiarazione>();
		for (DeleDDichiarazioneStato deleDDichiarazioneStato : deleDDichiarazioneStatoRepository.ricercaStatiDichiarazione()) {
			StatoDichiarazione statoDichiarazione= new StatoDichiarazione();
			statoDichiarazione.setCodice(deleDDichiarazioneStato.getDicStatoCod());
			statoDichiarazione.setDescrizione(deleDDichiarazioneStato.getDicStatoDesc());
			result.add(statoDichiarazione);
		}
		
		return result;
	}
	
	
	public List<StatoDichiarazione> ricercaStatiDichiarazioneDett() {
	
		List<StatoDichiarazione> result = new ArrayList<StatoDichiarazione>();
		
		for (DeleDDichiarazioneStato deleDDichiarazioneStato : deleDDichiarazioneStatoRepository.ricercaStatiDichiarazione()) {
			StatoDichiarazione statoDichiarazione= new StatoDichiarazione();
			statoDichiarazione.setCodice(deleDDichiarazioneStato.getDicStatoCod());
			statoDichiarazione.setDescrizione(deleDDichiarazioneStato.getDicStatoDesc());
			result.add(statoDichiarazione);
		}
		
		return result;
	}
	
	public List<TipoDichiarazione> ricercaTipiDichiarazione() {

	
		List<TipoDichiarazione> result = new ArrayList<TipoDichiarazione>();
		for (DeleDDichiarazioneTipo deleDDichiarazioneTipo : deleDDichiarazioneTipoRepository.ricercaTipiDichiarazione()) {
			TipoDichiarazione tipoDichiarazione= new TipoDichiarazione();
			tipoDichiarazione.setCodice(deleDDichiarazioneTipo.getDicTipoCod());
			tipoDichiarazione.setDescrizione(deleDDichiarazioneTipo.getDicTipoDesc());
			result.add(tipoDichiarazione);
		}
		
		return result;
	}
	
	public List<StatoDelega> ricercaStatiDelega() {
		
		List<StatoDelega> result = new ArrayList<StatoDelega>();
		for (DeleDDelegaStato deleDDelegaStato : deleDDelegaStatoRepository.ricercaStatiDelega()) {
			StatoDelega statoDelega= new StatoDelega();
			statoDelega.setCodice(deleDDelegaStato.getDelStatoCod());
			statoDelega.setDescrizione(deleDDelegaStato.getDelStatoDesc());
			result.add(statoDelega);
		}
		
		return result;
	}
	
	public List<TipoDelega> ricercaTipiDelega() {
	
		
		List<TipoDelega> result = new ArrayList<TipoDelega>();
		for (DeleDDelegaTipo deleDDelegatipo : deleDDelegaTipoRepository.ricercaTipiDelega()) {
			TipoDelega tipoDelega= new TipoDelega();
			tipoDelega.setCodice(deleDDelegatipo.getDelTipCod());
			tipoDelega.setDescrizione(deleDDelegatipo.getDelTipDesc());
			result.add(tipoDelega);
		}
		
		return result;
	}
	
	public List<InformativaConsensi> ricercaInformativaConsensi() {
		List<InformativaConsensi> result = new ArrayList<InformativaConsensi>();
		
		for (DeleDConsInformativa deleDConsInformativa : deleDConsInformativaRepository.ricercaInformativaConsensi()) {
			InformativaConsensi informativaConsensi= new InformativaConsensi();
			informativaConsensi.setPdfInformativa(deleDConsInformativa.getPdfInformativa());
			informativaConsensi.setDescInformativa(deleDConsInformativa.getDescInformativa());
			informativaConsensi.setHtmlInformativa(deleDConsInformativa.getHtmlInformativa());
			result.add(informativaConsensi);
		}
		
		return result;
	}

}
