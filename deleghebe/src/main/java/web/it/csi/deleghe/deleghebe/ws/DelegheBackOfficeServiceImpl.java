/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.ws;

import javax.inject.Inject
;
import javax.jws.WebService;

import it.csi.deleghe.deleghebe.service.AggiornaServizioService;
import it.csi.deleghe.deleghebe.service.BatchDelegheService;
import it.csi.deleghe.deleghebe.service.BatchDichiarazioniService;
import it.csi.deleghe.deleghebe.service.GetDettaglioDelegaService;
import it.csi.deleghe.deleghebe.service.GetDettaglioDichiarazioneService;
import it.csi.deleghe.deleghebe.service.InserisciServizioService;
import it.csi.deleghe.deleghebe.service.IsAliveService;
import it.csi.deleghe.deleghebe.service.LeggiServiziService;
import it.csi.deleghe.deleghebe.service.RicercaDelegaService;
import it.csi.deleghe.deleghebe.service.RicercaServiziService;
import it.csi.deleghe.deleghebe.ws.model.ApplicazioneRichiedente;
import it.csi.deleghe.deleghebe.ws.model.Richiedente;
import it.csi.deleghe.deleghebe.ws.msg.AggiornaServizio;
import it.csi.deleghe.deleghebe.ws.msg.AggiornaServizioResponse;
import it.csi.deleghe.deleghebe.ws.msg.BatchDeleghe;
import it.csi.deleghe.deleghebe.ws.msg.BatchDelegheResponse;
import it.csi.deleghe.deleghebe.ws.msg.GetDettaglioDelega;
import it.csi.deleghe.deleghebe.ws.msg.GetDettaglioDelegaResponse;
import it.csi.deleghe.deleghebe.ws.msg.GetDettaglioDichiarazione;
import it.csi.deleghe.deleghebe.ws.msg.GetDettaglioDichiarazioneResponse;
import it.csi.deleghe.deleghebe.ws.msg.InserisciServizio;
import it.csi.deleghe.deleghebe.ws.msg.InserisciServizioResponse;
import it.csi.deleghe.deleghebe.ws.msg.IsAlive;
import it.csi.deleghe.deleghebe.ws.msg.IsAliveResponse;
import it.csi.deleghe.deleghebe.ws.msg.LeggiServizi;
import it.csi.deleghe.deleghebe.ws.msg.LeggiServiziResponse;
import it.csi.deleghe.deleghebe.ws.msg.RicercaDelega;
import it.csi.deleghe.deleghebe.ws.msg.RicercaDelegaResponse;
import it.csi.deleghe.deleghebe.ws.msg.RicercaServizi;
import it.csi.deleghe.deleghebe.ws.msg.RicercaServiziResponse;
import it.csi.deleghe.deleghebe.ws.msg.SaveFiltri;
import it.csi.deleghe.deleghebe.ws.msg.SaveFiltriResponse;

@WebService(serviceName = "DelegheBackOfficeService", targetNamespace = "http://deleghebe.csi.it/")
public class DelegheBackOfficeServiceImpl implements DelegheBackOfficeService {

	@Inject
	private IsAliveService isAliveService;
	@Inject
	private RicercaServiziService ricercaServiziService;
	@Inject
	private InserisciServizioService inserisciServizioService;
	@Inject
	private AggiornaServizioService aggiornaServizioService;
	@Inject
	private BatchDelegheService batchDelegheService;
	@Inject
	private BatchDichiarazioniService batchDichiarazioniService;
	
	@Inject
	private RicercaDelegaService ricercaDelegaService;
	
	@Inject
	private GetDettaglioDelegaService getDettaglioDelegaService;
	
	@Inject
	private GetDettaglioDichiarazioneService getDettaglioDichiarazioneService;
	
	@Inject
	private LeggiServiziService leggiServiziService;
	
	
	@Override
	public IsAliveResponse isAliveService(IsAlive req) {
		return isAliveService.executeService(req);
	}


	@Override
	public RicercaServiziResponse ricercaServiziService(RicercaServizi req) {
		req.setRichiedente(getRichiedenteGenerico());
		return ricercaServiziService.executeService(req);
	}

	@Override
	public InserisciServizioResponse inserisciServizioService(InserisciServizio req) {
		return inserisciServizioService.executeService(req);
	}

	@Override
	public AggiornaServizioResponse aggiornaServizioService(AggiornaServizio req) {
		return aggiornaServizioService.executeService(req);
	}
	
	@Override
    public BatchDelegheResponse batchDelegheService(BatchDeleghe req) {
		return batchDelegheService.executeService(req);
	}

	@Override
    public BatchDelegheResponse batchDichiarazioniService(BatchDeleghe req) {
		return batchDichiarazioniService.executeService(req);
	}

	@Override
    public BatchDelegheResponse batchDelegheMinoriService(BatchDeleghe req) {
		return batchDichiarazioniService.executeService(req);
	}
	
	private Richiedente getRichiedenteGenerico () {
		ApplicazioneRichiedente servizioGenerico = new ApplicazioneRichiedente();
		servizioGenerico.setCodice("UNDEFINED");
		servizioGenerico.setIdRequest("000000000");
		
		Richiedente richiedente = new Richiedente();
		richiedente.setCodiceFiscale("xyz");
		richiedente.setServizio(servizioGenerico);
		
		return richiedente;
	}


	@Override
	public RicercaDelegaResponse ricercaDelegaService(RicercaDelega req) {
		return ricercaDelegaService.executeService(req);
	}


	@Override
	public GetDettaglioDelegaResponse getDettaglioDelegaService(GetDettaglioDelega req) {
		// TODO Auto-generated method stub
		return getDettaglioDelegaService.executeService(req);
	}


	@Override
	public GetDettaglioDichiarazioneResponse getDettaglioDichiarazioneService(GetDettaglioDichiarazione req) {
		// TODO Auto-generated method stub
		return getDettaglioDichiarazioneService.executeService(req);
	}


	@Override
	public SaveFiltriResponse saveFiltriService(SaveFiltri req) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public LeggiServiziResponse leggiServiziService(LeggiServizi req) {
		return leggiServiziService.executeService(req);
	}
	
}
