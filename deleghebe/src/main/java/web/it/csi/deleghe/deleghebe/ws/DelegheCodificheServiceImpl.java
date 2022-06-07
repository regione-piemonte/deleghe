/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.ws;

import javax.annotation.PostConstruct;

import javax.inject.Inject;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import it.csi.deleghe.deleghebe.service.LeggiDescrizioneDocumentoService;
import it.csi.deleghe.deleghebe.service.LeggiStatiDelegaService;
import it.csi.deleghe.deleghebe.service.LeggiStatiDichiarazioneService;
import it.csi.deleghe.deleghebe.service.LeggiInformativaConsensoService;
import it.csi.deleghe.deleghebe.service.LeggiTipiDelegaService;
import it.csi.deleghe.deleghebe.service.LeggiTipiDichiarazioneService;
import it.csi.deleghe.deleghebe.ws.msg.LeggiDescrizioneDocumento;
import it.csi.deleghe.deleghebe.ws.msg.LeggiDescrizioneDocumentoResponse;
import it.csi.deleghe.deleghebe.ws.msg.LeggiInformativaConsensi;
import it.csi.deleghe.deleghebe.ws.msg.LeggiInformativaConsensiResponse;
import it.csi.deleghe.deleghebe.ws.msg.LeggiStatiDelega;
import it.csi.deleghe.deleghebe.ws.msg.LeggiStatiDelegaResponse;
import it.csi.deleghe.deleghebe.ws.msg.LeggiStatiDichiarazione;
import it.csi.deleghe.deleghebe.ws.msg.LeggiStatiDichiarazioneResponse;
import it.csi.deleghe.deleghebe.ws.msg.LeggiTipiDelega;
import it.csi.deleghe.deleghebe.ws.msg.LeggiTipiDelegaResponse;
import it.csi.deleghe.deleghebe.ws.msg.LeggiTipiDichiarazione;
import it.csi.deleghe.deleghebe.ws.msg.LeggiTipiDichiarazioneResponse;
import it.csi.deleghe.deleghebe.ws.msg.LeggiTipoDocumento;
import it.csi.deleghe.deleghebe.ws.msg.LeggiTipoDocumentoResponse;

@WebService(serviceName="DelegheCodificheService", targetNamespace="http://deleghebe.csi.it/")
public class DelegheCodificheServiceImpl implements DelegheCodificheService {

	@Inject
	private LeggiStatiDichiarazioneService leggiStatiDichiarazioneService;
	
	@Inject
	private LeggiTipiDichiarazioneService leggiTipiDichiarazioneService;
	
	@Inject
	private LeggiStatiDelegaService leggiStatiDelegaService;
	
	@Inject
	private LeggiTipiDelegaService leggiTipiDelegaService;
	
	@Inject
	private LeggiDescrizioneDocumentoService leggiDescrizioneDocumentoService;
	
	@Inject
	private LeggiInformativaConsensoService leggiInformativaConsensoService;
	
	@PostConstruct
	public void init() {

	}
	
	@Override
	public LeggiStatiDichiarazioneResponse leggiStatiDichiarazioneService(LeggiStatiDichiarazione req) {
		return leggiStatiDichiarazioneService.executeService(req);
	}

	@Override
	public LeggiTipiDichiarazioneResponse leggiTipiDichiarazioneService(LeggiTipiDichiarazione req) {
		return leggiTipiDichiarazioneService.executeService(req);
	}

	@Override
	public LeggiStatiDelegaResponse leggiStatiDelegaService(LeggiStatiDelega req) {
		// TODO Auto-generated method stub
		return leggiStatiDelegaService.executeService(req);
	}

	@Override
	public LeggiTipiDelegaResponse leggiTipiDelegaService(LeggiTipiDelega req) {
		// TODO Auto-generated method stub
		return leggiTipiDelegaService.executeService(req);
	}

	@Override
	public LeggiTipoDocumentoResponse leggiTipoDocumentoService(LeggiTipoDocumento req) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public LeggiDescrizioneDocumentoResponse leggiDescrizioneDocumentoService(LeggiDescrizioneDocumento req) {
		return leggiDescrizioneDocumentoService.executeService(req);
	}
	
	@Override
	public LeggiInformativaConsensiResponse leggiInformativaConsensoService(LeggiInformativaConsensi req) {
		return leggiInformativaConsensoService.executeService(req);
	}	
	
	


	
}
