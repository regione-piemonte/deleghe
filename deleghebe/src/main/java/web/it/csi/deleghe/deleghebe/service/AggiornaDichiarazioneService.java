/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service;

import static it.csi.deleghe.deleghebe.util.Check.checkEsitoSuccesso;
import static it.csi.deleghe.deleghebe.util.Check.checkListNotNull;
import static it.csi.deleghe.deleghebe.util.Check.checkNotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;

import it.csi.deleghe.deleghebe.model.DeleTDelega;
import it.csi.deleghe.deleghebe.service.DeleDDelegaServizioStatoRepository.DelstatoCodEnum;
import it.csi.deleghe.deleghebe.service.DeleDDichiarazioneDetStatoRepository.DicDetStatoCodEnum;
import it.csi.deleghe.deleghebe.service.DeleDDichiarazioneRuoloRepository.DicRuoloCodEnum;
import it.csi.deleghe.deleghebe.service.base.BaseService;
import it.csi.deleghe.deleghebe.service.mapper.DelegaDelegaCittadinoMapper;
import it.csi.deleghe.deleghebe.service.mapper.DelegaServizioDeleTServizioMapper;
import it.csi.deleghe.deleghebe.util.DateUtil;
import it.csi.deleghe.deleghebe.util.NotificatoreUtil;
import it.csi.deleghe.deleghebe.ws.model.Cittadino;
import it.csi.deleghe.deleghebe.ws.model.DelegaCittadino;
import it.csi.deleghe.deleghebe.ws.model.DelegaServizio;
import it.csi.deleghe.deleghebe.ws.model.Dichiarazione;
import it.csi.deleghe.deleghebe.ws.model.DichiarazioneDettaglio;
import it.csi.deleghe.deleghebe.ws.model.RisultatoCodice;
import it.csi.deleghe.deleghebe.ws.model.TipoDichiarazione;
import it.csi.deleghe.deleghebe.ws.msg.AggiornaDichiarazione;
import it.csi.deleghe.deleghebe.ws.msg.AggiornaDichiarazioneResponse;
import it.csi.deleghe.deleghebe.ws.msg.SaveDelegati;
import it.csi.deleghe.deleghebe.ws.msg.SaveDelegatiResponse;

public class AggiornaDichiarazioneService extends BaseService<AggiornaDichiarazione, AggiornaDichiarazioneResponse> {

	@Inject
	private SaveDelegatiService saveDelegatiService;
	@Inject
	private DichiarazioneBean dichiarazioneBean;
	@Inject
	private DelegaDelegaCittadinoMapper delegaDelegaCittadinoMapper;
	@Inject
	private DeleTDelegaRepository deleTDelegaRepository;
	@Inject
	private DelegaServizioDeleTServizioMapper delegaServizioDeleTServizioMapper;
	@Inject
	private NotificatoreUtil notificatoreUtil;

	@Override
	protected void checkServiceParams(AggiornaDichiarazione req) {
		checkNotNull(req.getDichiarazione(), "DA.V02", "Dichiarazione non valorizzata.");
		checkNotNull(req.getDichiarazione().getUuid(), "DA.V01", "UUID dichiarazione non valorizzato.");
		checkNotNull(req.getDichiarazione().getStato(), "DA.V01", "Stato dichiarazione non valorizzato.");
		checkDettagli(req.getDichiarazione().getDettagli());
	}

	protected void checkDettagli(List<DichiarazioneDettaglio> dettagli) {
		checkListNotNull(dettagli, "DA.V02", "Dettagli non valorizzati.");
		for (DichiarazioneDettaglio dichiarazioneDettaglio : dettagli) {
			checkNotNull(dichiarazioneDettaglio.getUuid(), "DA.V01", "UUID dichiarazione dettaglio non valorizzato.");
			checkNotNull(dichiarazioneDettaglio.getStato(), "DA.V01", "Stato del dettaglio dichiarazione non valorizzato.");
			checkNotNull(dichiarazioneDettaglio.getRuoloCittadino1(), "DA.V01", "Ruolo cittadino non valorizzato.");
			checkNotNull(dichiarazioneDettaglio.getRuoloCittadino2(), "DA.V01", "Ruolo cittadino non valorizzato.");
			checkNotNull(dichiarazioneDettaglio.getRuoloCittadino1().getCodice(), "DA.V01", "Codice ruolo cittadino non valorizzato.");
			checkNotNull(dichiarazioneDettaglio.getRuoloCittadino2().getCodice(), "DA.V01", "Codice ruolo cittadino non valorizzato.");
		}
	}

	@Override
	protected AggiornaDichiarazioneResponse execute(AggiornaDichiarazione req) {

		AggiornaDichiarazioneResponse res = new AggiornaDichiarazioneResponse();
		
		Dichiarazione dichiarazioneAggiornata = dichiarazioneBean.aggiornaDichiarazione(req.getDichiarazione(), req.getRichiedente().getCodiceFiscale());
		
		TipoDichiarazione tipoDichiarazione =dichiarazioneAggiornata.getTipo();
		String codiceTipoDichiarazione = tipoDichiarazione.getCodice();		
		Date dataFineTutelaDich = dichiarazioneAggiornata.getDataFineTutela();
				
		if (dichiarazioneAggiornata != null) {

			Map<String, SaveDelegati> delegantiXSaveRequest = new HashMap<>();
			SaveDelegati saveDelegatiRequest = null;
			

			final List<DichiarazioneDettaglio> dettagli = dichiarazioneAggiornata.getDettagli();
			for (int index = 0; index< dettagli.size(); index++) {
				
				DichiarazioneDettaglio dd = dettagli.get(index);
				
				if (!delegantiXSaveRequest.containsKey(dd.getCittadino2().getCodiceFiscale())) {
					saveDelegatiRequest = new SaveDelegati();
					delegantiXSaveRequest.put(dd.getCittadino2().getCodiceFiscale(), saveDelegatiRequest);
				} else {
					saveDelegatiRequest = delegantiXSaveRequest.get(dd.getCittadino2().getCodiceFiscale());
				}

				saveDelegatiRequest.setRichiedente(req.getRichiedente());
				saveDelegatiRequest.setCittadinoDelegante(dd.getCittadino2());

				if (saveDelegatiRequest.getDelegati() == null) {
					List<DelegaCittadino> delegati = new ArrayList<>();
					saveDelegatiRequest.setDelegati(delegati);
				}

				DelegaCittadino delegato = delegaDelegaCittadinoMapper.estraiDelegatiByDettagli(dd);
				delegato.setTipoDelega(req.getDichiarazione().getTipo().getCodice());

				if (delegato != null) {
					String codStato = dd.getStato().getCodice();
					
					List<DeleTDelega> listDeleTDelega = deleTDelegaRepository.ricercaDelegheByCF(dd.getCittadino2().getCodiceFiscale(), dd.getCittadino1().getCodiceFiscale());
					
					if ((listDeleTDelega == null) || listDeleTDelega.isEmpty()) {
					
					
						if (codStato.equals(DicDetStatoCodEnum.VALIDA.name())) 
							saveDelegatiRequest.getDelegati().add(delegato);
					} 
					else {
					
					
						DeleTDelega deleTDelega = listDeleTDelega.get(0);
						delegato.setUuid(deleTDelega.getUuid().toString());
						delegato.setDeleghe(delegaServizioDeleTServizioMapper.fromList(deleTDelega.getDeleTDelegaServizios()));

						if (codStato.equals(DicDetStatoCodEnum.REVOCATA.name()) || codStato.equals(DicDetStatoCodEnum.REVOCATA_BLOCCO.name())) {
					
							for (DelegaServizio delegaServizio : delegato.getDeleghe()) {
								delegaServizio.setStatoDelega(DelstatoCodEnum.REVOCATA.name());
							}
							saveDelegatiRequest.getDelegati().add(delegato);
						}

						if (codStato.equals(DicDetStatoCodEnum.VALIDA.name()) || codStato.equals(DicDetStatoCodEnum.DA_APPROVARE.name())) {
							
					
							Date dataScadenzaDelega = deleTDelega.getDeleTCittadino1().getCitNascitaData();
							Calendar c = Calendar.getInstance();
							if(codiceTipoDichiarazione.equalsIgnoreCase("CONGIUNTA") || codiceTipoDichiarazione.equalsIgnoreCase("GENITOREMONO")) {
								c.setTime(dataScadenzaDelega);
								c.add(Calendar.YEAR, +18);
								dataScadenzaDelega = c.getTime();
							}else {
								c.setTime(dataFineTutelaDich);
								dataScadenzaDelega = c.getTime();
							}							
							
					
							for (DelegaServizio delegaServizio : delegato.getDeleghe()) {
								delegaServizio.setStatoDelega(DelstatoCodEnum.ATTIVA.name());
								delegaServizio.setDataFineDelega(dataScadenzaDelega);
							}
							saveDelegatiRequest.getDelegati().add(delegato);
						}
					}
				}
			}

			for (Entry<String, SaveDelegati> entrySaveDelegati : delegantiXSaveRequest.entrySet()) {
				SaveDelegatiResponse saveDelegatiResponse = saveDelegatiService.executeService(entrySaveDelegati.getValue());
				res.getErrori().addAll(saveDelegatiResponse.getErrori());
				checkEsitoSuccesso(saveDelegatiResponse, "DA.DCH07", "Errore nell'inserimento delle deleghe a partire dalla dichiarazione.");

				if (res.getErrori() != null && !res.getErrori().isEmpty()) {
					res.setEsito(RisultatoCodice.FALLIMENTO);
				} else {

					try {
						callNotificatore(dichiarazioneAggiornata, req.getRichiedente().getCodiceFiscale());
					} catch (Exception e) {
						res.addErrore("DA.N1", "WARN: Errore Notificatore. " + e.getMessage());
					}

					List<DichiarazioneDettaglio> dettagli2 = dichiarazioneAggiornata.getDettagli();
					List<DelegaCittadino> delegati = saveDelegatiResponse.getDelegati();
					for(int i = 0; i< dettagli2.size() && i < delegati.size(); i++){
						DichiarazioneDettaglio d = dettagli2.get(i);
						DelegaCittadino delegaCittadino = delegati.get(i);

						d.setDeleghe(delegaCittadino.getDeleghe());
						d.setTipoDelega(delegaCittadino.getTipoDelega());
					}

					res.setDichiarazione(dichiarazioneAggiornata);
					res.setEsito(RisultatoCodice.SUCCESSO);
				}
			}
		}

		return res;
	}

	private void callNotificatore(Dichiarazione dichiarazione, String cfGenitoreOperante) {
		
		Map<Cittadino, Map<String, Cittadino>> mapValue = loadParentsBySon(dichiarazione.getDettagli());

		
		for (Map.Entry<Cittadino, Map<String, Cittadino>> entry : mapValue.entrySet()) {
			Map<String, String> replacements = new HashMap<String, String>();

			Cittadino figlio = entry.getKey();
			replacements.put("@FIGLIO@", figlio.getNome() + " " + figlio.getCognome());
			replacements.put("@DATA_DICHIARAZIONE@", DateUtil.toStringSimpleDate(dichiarazione.getDataCreazione()));

			String cfGenitore1 = null;
			String cfGenitore2 = null;
			String stato = null;
			String ruolo = null;
			Map<String, Cittadino> parentsValue = entry.getValue();
			for (Map.Entry<String, Cittadino> entryGenitore : parentsValue.entrySet()) {
				String[] parts = entryGenitore.getKey().split("\\|");
				ruolo = parts[0];
				stato = parts[1];

				Cittadino genitore = entryGenitore.getValue();
				if (ruolo.equalsIgnoreCase(DicRuoloCodEnum.GENITORE_1.name())) {
					replacements.put("@GENITORE1@", genitore.getNome() + " " + genitore.getCognome());
					cfGenitore1 = genitore.getCodiceFiscale();
				} else if (ruolo.equalsIgnoreCase(DicRuoloCodEnum.GENITORE_2.name())) {
					replacements.put("@GENITORE2@", genitore.getNome() + " " + genitore.getCognome());
					cfGenitore2 = genitore.getCodiceFiscale();
				}
			}

			
			if (stato.equals(DicDetStatoCodEnum.REVOCATA.name())) {
				if (cfGenitoreOperante.equals(cfGenitore1)) {
					notificatoreUtil.callNotificatore("dichiarazione", "revocata", "genitore2", cfGenitore2, replacements);
				} else {
					notificatoreUtil.callNotificatore("dichiarazione", "revocata", "genitore1", cfGenitore1, replacements);
				}
				notificatoreUtil.callNotificatore("dichiarazione", "revocata", "figlio", figlio.getCodiceFiscale(), replacements);
			} else if (stato.equals(DicDetStatoCodEnum.REVOCATA_BLOCCO.name())) {
				notificatoreUtil.callNotificatore("dichiarazione", "revocataBlocco", "genitore1", cfGenitore1, replacements);
				notificatoreUtil.callNotificatore("dichiarazione", "revocataBlocco", "genitore2", cfGenitore2, replacements);
				notificatoreUtil.callNotificatore("dichiarazione", "revocataBlocco", "figlio", figlio.getCodiceFiscale(), replacements);
			} else if (stato.equals(DicDetStatoCodEnum.VALIDA.name())) {
				notificatoreUtil.callNotificatore("dichiarazione", "confermata", "genitore1", cfGenitore1, replacements);
				//notificatoreUtil.callNotificatore("dichiarazione", "confermata", "genitore2", cfGenitore2, replacements);
				notificatoreUtil.callNotificatore("dichiarazione", "confermata", "figlio", figlio.getCodiceFiscale(), replacements);
			}
		}
	}

	
	private Map<Cittadino, Map<String, Cittadino>> loadParentsBySon(List<DichiarazioneDettaglio> dettagli) {
		Map<Cittadino, Map<String, Cittadino>> mapValue = new HashMap<>();
		Map<String, Cittadino> parents;
		for (DichiarazioneDettaglio dettaglio : dettagli) {

			if (!mapValue.containsKey(dettaglio.getCittadino2())) {
				parents = new HashMap<>();
			} else {
				parents = mapValue.get(dettaglio.getCittadino2());
			}
			parents.put(dettaglio.getRuoloCittadino1().getCodice().concat("|").concat(dettaglio.getStato().getCodice()), dettaglio.getCittadino1());
			mapValue.put(dettaglio.getCittadino2(), parents);

		}

		return mapValue;
	}

}