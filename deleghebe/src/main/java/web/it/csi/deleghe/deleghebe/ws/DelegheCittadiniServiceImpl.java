/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.ws;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jws.WebService;

import it.csi.deleghe.deleghebe.service.AggiornaCittadinoService;
import it.csi.deleghe.deleghebe.service.AggiornaDelegaService;
import it.csi.deleghe.deleghebe.service.AggiornaDichiarazioneService;
import it.csi.deleghe.deleghebe.service.ElencoGenitoriService;
import it.csi.deleghe.deleghebe.service.GetDelegantiService;
import it.csi.deleghe.deleghebe.service.GetDelegatiFSEService;
import it.csi.deleghe.deleghebe.service.GetDelegatiService;
import it.csi.deleghe.deleghebe.service.InserisciCittadinoService;
import it.csi.deleghe.deleghebe.service.InserisciDelegaService;
import it.csi.deleghe.deleghebe.service.InserisciDichiarazioneService;
import it.csi.deleghe.deleghebe.service.IsAliveService;
import it.csi.deleghe.deleghebe.service.RicercaCfOperatoreService;
import it.csi.deleghe.deleghebe.service.RicercaCittadiniService;
import it.csi.deleghe.deleghebe.service.RicercaDelegheService;
import it.csi.deleghe.deleghebe.service.RicercaDichiarazioniService;
import it.csi.deleghe.deleghebe.service.RicercaFileService;
import it.csi.deleghe.deleghebe.service.RicercaOperatoreValidoService;
import it.csi.deleghe.deleghebe.service.RinunciaDelegatoService;
import it.csi.deleghe.deleghebe.service.SaveDelegatiService;
import it.csi.deleghe.deleghebe.ws.msg.AggiornaCittadino;
import it.csi.deleghe.deleghebe.ws.msg.AggiornaCittadinoResponse;
import it.csi.deleghe.deleghebe.ws.msg.AggiornaDelega;
import it.csi.deleghe.deleghebe.ws.msg.AggiornaDelegaResponse;
import it.csi.deleghe.deleghebe.ws.msg.AggiornaDichiarazione;
import it.csi.deleghe.deleghebe.ws.msg.AggiornaDichiarazioneResponse;
import it.csi.deleghe.deleghebe.ws.msg.ElencoGenitori;
import it.csi.deleghe.deleghebe.ws.msg.ElencoGenitoriResponse;
import it.csi.deleghe.deleghebe.ws.msg.GetDeleganti;
import it.csi.deleghe.deleghebe.ws.msg.GetDelegantiResponse;
import it.csi.deleghe.deleghebe.ws.msg.GetDelegati;
import it.csi.deleghe.deleghebe.ws.msg.GetDelegatiFSE;
import it.csi.deleghe.deleghebe.ws.msg.GetDelegatiFSEResponse;
import it.csi.deleghe.deleghebe.ws.msg.GetDelegatiResponse;
import it.csi.deleghe.deleghebe.ws.msg.InserisciCittadino;
import it.csi.deleghe.deleghebe.ws.msg.InserisciCittadinoResponse;
import it.csi.deleghe.deleghebe.ws.msg.InserisciDelega;
import it.csi.deleghe.deleghebe.ws.msg.InserisciDelegaResponse;
import it.csi.deleghe.deleghebe.ws.msg.InserisciDichiarazione;
import it.csi.deleghe.deleghebe.ws.msg.InserisciDichiarazioneResponse;
import it.csi.deleghe.deleghebe.ws.msg.IsAlive;
import it.csi.deleghe.deleghebe.ws.msg.IsAliveResponse;
import it.csi.deleghe.deleghebe.ws.msg.RicercaCfOperatore;
import it.csi.deleghe.deleghebe.ws.msg.RicercaCfOperatoreResponse;
import it.csi.deleghe.deleghebe.ws.msg.RicercaCittadini;
import it.csi.deleghe.deleghebe.ws.msg.RicercaCittadiniResponse;
import it.csi.deleghe.deleghebe.ws.msg.RicercaDeleghe;
import it.csi.deleghe.deleghebe.ws.msg.RicercaDelegheResponse;
import it.csi.deleghe.deleghebe.ws.msg.RicercaDichiarazioni;
import it.csi.deleghe.deleghebe.ws.msg.RicercaDichiarazioniResponse;
import it.csi.deleghe.deleghebe.ws.msg.RicercaFile;
import it.csi.deleghe.deleghebe.ws.msg.RicercaFileResponse;
import it.csi.deleghe.deleghebe.ws.msg.RinunciaDelegato;
import it.csi.deleghe.deleghebe.ws.msg.RinunciaDelegatoResponse;
import it.csi.deleghe.deleghebe.ws.msg.SaveDelegati;
import it.csi.deleghe.deleghebe.ws.msg.SaveDelegatiResponse;

@WebService(serviceName="DelegheCittadiniService", targetNamespace="http://deleghebe.csi.it/")
public class DelegheCittadiniServiceImpl implements DelegheCittadiniService {
	
	@Inject
	private IsAliveService isAliveService;
	@Inject
	private GetDelegatiService getDelegatiService;
	@Inject
	private GetDelegatiFSEService getDelegatiFSEService;
	@Inject
	private GetDelegantiService getDelegantiService;
	@Inject
	private SaveDelegatiService saveDelegatiService;
	@Inject
	private RinunciaDelegatoService rinunciaDelegatoService;
	@Inject
	private RicercaDelegheService ricercaDelegheService;
	@Inject
	private AggiornaDelegaService aggiornaDelegaService;
	@Inject
	private InserisciDelegaService inserisciDelegaService;
	@Inject
	private RicercaCittadiniService ricercaCittadiniService;
	@Inject
	private RicercaDichiarazioniService ricercaDichiarazioniService;
	@Inject
	private AggiornaCittadinoService aggiornaCittadinoService;
	@Inject
	private InserisciCittadinoService inserisciCittadinoService;
	@Inject
	private AggiornaDichiarazioneService aggiornaDichiarazioneService;
	@Inject
	private InserisciDichiarazioneService inserisciDichiarazioneService;
	@Inject
	private ElencoGenitoriService elencoGenitoriService;
	@Inject 
	private RicercaCfOperatoreService ricercaCfOperatoreService;
	@Inject 
	private RicercaFileService ricercaFileService;
	@Inject 
	private RicercaOperatoreValidoService ricercaOperatoreValidoService;
		
	
	@PostConstruct
	public void init() {

	}
	
	@Override
	public IsAliveResponse isAliveService(IsAlive req) {
		return isAliveService.executeService(req);
	}

	@Override
	public GetDelegantiResponse getDelegantiService(GetDeleganti req) {
		return getDelegantiService.executeService(req);
	}
	

	@Override
	public GetDelegatiFSEResponse getDelegatiFSEService(GetDelegatiFSE req) {
		return getDelegatiFSEService.executeService(req);
	}


	@Override
	public GetDelegatiResponse getDelegatiService(GetDelegati req) {
		return getDelegatiService.executeService(req);
	}
	
	

	@Override
	public SaveDelegatiResponse saveDelegatiService(SaveDelegati req) {
		return saveDelegatiService.executeService(req);
	}

	@Override
	public RinunciaDelegatoResponse rinunciaDelegatoService(RinunciaDelegato req) {
		return rinunciaDelegatoService.executeService(req);
	}

	@Override
	public InserisciDichiarazioneResponse inserisciDichiarazioneService(InserisciDichiarazione req) {
		return inserisciDichiarazioneService.executeService(req);
	}

	@Override
	public AggiornaDichiarazioneResponse aggiornaDichiarazioneService(AggiornaDichiarazione req) {
		return aggiornaDichiarazioneService.executeService(req);
	}

	@Override
	public InserisciCittadinoResponse inserisciCittadinoService(InserisciCittadino req) {
		return inserisciCittadinoService.executeService(req);
	}

	@Override
	public AggiornaCittadinoResponse aggiornaCittadinoService(AggiornaCittadino req) {
		return aggiornaCittadinoService.executeService(req);
	}

	@Override
	public RicercaDichiarazioniResponse ricercaDichiarazioniService(RicercaDichiarazioni req) {
		return ricercaDichiarazioniService.executeService(req);
	}

	@Override
	public RicercaCittadiniResponse ricercaCittadiniService(RicercaCittadini req) {
		return ricercaCittadiniService.executeService(req);
	}

	@Override
	public InserisciDelegaResponse inserisciDelegaService(InserisciDelega req) {
		return inserisciDelegaService.executeService(req);
	}

	@Override
	public AggiornaDelegaResponse aggiornaDelegaService(AggiornaDelega req) {
		return aggiornaDelegaService.executeService(req);
	}

	@Override
	public RicercaDelegheResponse ricercaDelegheService(RicercaDeleghe req) {
		return ricercaDelegheService.executeService(req);
	}

	@Override
	public ElencoGenitoriResponse elencoGenitoriService(ElencoGenitori req) {
		return elencoGenitoriService.executeService(req);
	}

	@Override
	public RicercaCfOperatoreResponse ricercaCfOperatoreService(RicercaCfOperatore req) {
		return ricercaCfOperatoreService.executeService(req);
	}
	
	@Override
	public RicercaFileResponse ricercaFileDocumentoPerId(RicercaFile req) {
		return ricercaFileService.executeService(req);
	}
	
	@Override
	public RicercaCfOperatoreResponse ricercaOperatoreValidoService(RicercaCfOperatore req) {
		return ricercaOperatoreValidoService.executeService(req);
	}
	
}
