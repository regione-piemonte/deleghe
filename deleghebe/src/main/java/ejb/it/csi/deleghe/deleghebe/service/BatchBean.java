/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ejb.Stateless;
import javax.inject.Inject;

import it.csi.deleghe.deleghebe.model.DeleDDelegaServizioStato;
import it.csi.deleghe.deleghebe.model.DeleDDichiarazioneDetStato;
import it.csi.deleghe.deleghebe.model.DeleTDelega;
import it.csi.deleghe.deleghebe.model.DeleTDelegaServizio;
import it.csi.deleghe.deleghebe.model.DeleTDichiarazioneDet;
import it.csi.deleghe.deleghebe.model.DeleTLogBatch;
import it.csi.deleghe.deleghebe.service.DeleDDelegaServizioStatoRepository.DelstatoCodEnum;
import it.csi.deleghe.deleghebe.service.DeleDDichiarazioneDetStatoRepository.DicDetStatoCodEnum;
import it.csi.deleghe.deleghebe.service.mapper.DelegaServDeleTServizioMapper;
import it.csi.deleghe.deleghebe.util.DateUtil;
import it.csi.deleghe.deleghebe.util.LogUtil;
import it.csi.deleghe.deleghebe.util.NotificatoreUtil;
import it.csi.deleghe.deleghebe.ws.model.DatiAggiormentoDichiarazioniDB;
import it.csi.deleghe.deleghebe.ws.model.DatiPerNotificatoreDB;
import it.csi.deleghe.deleghebe.ws.model.DelegaServ;

@Stateless
public class BatchBean extends BaseRepository {

	private final SimpleDateFormat dtFormatter = new SimpleDateFormat("dd/MM/yyyy");
	
	private final Map<String, List<DeleTDelegaServizio>> mapNotificaDeleghe = new HashMap<>();
	
	@Inject
    private NotificatoreUtil  notificatoreUtil;
	@Inject
	private BatchDelegaRepository batchRepository;
	@Inject
	private DelegaServDeleTServizioMapper delegaServizioMapper;
	@Inject
	private DeleTServizioParametroRepository servizioParametroRepository;
	@Inject
	private DeleDDelegaServizioStatoRepository delegaServizioStatoRepository;
	@Inject
	private DeleTDichiarazioneDetRepository dichiarazioneDetRepository;
	@Inject
	private DeleDDichiarazioneDetStatoRepository dichiarazioneDetStatoRepository;
	@Inject
	private LogUtil log;


	public int[] scadenzaDeleghe () {
		int[] counters = new int[2];
		List<DeleTDelegaServizio> delegheServizio = null;
		
		int counter = 0;
		DeleDDelegaServizioStato statoInScadenza = delegaServizioStatoRepository.ricercaServiziStatoByDelstatoCod(DelstatoCodEnum.IN_SCADENZA);
		delegheServizio = ricercaDelegheServiziInScadenza();
		for (DeleTDelegaServizio tDelegaServ : delegheServizio) {
			tDelegaServ.setDeleDDelegaServizioStato(statoInScadenza);
			aggiornaStatoDelegaServizio(tDelegaServ);
			counter++;
		}
		counters[0] = counter;

		final DeleDDelegaServizioStato statoScadutaDLG = delegaServizioStatoRepository.ricercaServiziStatoByDelstatoCod(DelstatoCodEnum.SCADUTA);
		final DeleDDichiarazioneDetStato statoScadutaDIC = dichiarazioneDetStatoRepository.ricercaDetStatoByDicDetStatoCod(DicDetStatoCodEnum.SCADUTA);

		counter = 0;
		delegheServizio = ricercaDelegheServiziScaduti();
		for (DeleTDelegaServizio tDelegaServ : delegheServizio) {
			tDelegaServ.setDeleDDelegaServizioStato(statoScadutaDLG);
			aggiornaStatoDelegaServizio(tDelegaServ);

			DeleTDichiarazioneDet tDichiarazioneDet = tDelegaServ.getDeleTDelega().getDeleTDichiarazioneDet();
			if (tDichiarazioneDet != null) {
				tDichiarazioneDet.setDeleDDichiarazioneDetStato(statoScadutaDIC);
				aggiornaStatoDichiarazioneDet(tDichiarazioneDet);
			}

			counter++;
		}
		counters[1] = counter;

		finalizzaNotifiche();
		
		return counters;
	}
	
	public int scadenzaDelegheBatch (Calendar gCal) {
		DeleTLogBatch batchLog = batchRepository.prepareLog("BATCH_DELEGHE_1", gCal);
		if (batchLog == null) return 0;

		long time = GregorianCalendar.getInstance().getTimeInMillis();
		int[] counters = scadenzaDeleghe();
		time = GregorianCalendar.getInstance().getTimeInMillis() - time;
		
		StringBuilder sb = new StringBuilder("{");
			sb.append("deleghe: {");
				sb.append("cntInScadenza: ").append(counters[0]).append(", ");
				sb.append("cntScadute: ").append(counters[1]);
			sb.append("}, ");
			sb.append("msecDurata: ").append(time);
		sb.append("}");

	
		batchLog.setResult(sb.toString());
		batchRepository.updateLog(batchLog);

		return 1;
	}
	
	public int inScadenzaDelegheBatch (Calendar gCal) {
		final String METHOD_NAME = "inScadenzaDelegheBatch";
		
		DeleTLogBatch batchLog = batchRepository.prepareLog("DELEGHE_IN_SCADENZA", gCal);
		if (batchLog == null) return 0;
		
		log.info(METHOD_NAME, "INIZIO Esecuzione");
		
		long time = GregorianCalendar.getInstance().getTimeInMillis();
		time = GregorianCalendar.getInstance().getTimeInMillis() - time;
		
		int[] datiAggiornamento = delegheInScadenza();
		int recordInScadenza = 0;	
		int recordAggiornati = 0;
		
		StringBuilder sb = new StringBuilder("{");
			sb.append("deleghe: {");
				sb.append("cntInScadenza: ").append(datiAggiornamento[0]).append(", ");
				sb.append("cntAggiornate: ").append(datiAggiornamento[1]);
			sb.append("}, ");
			sb.append("msecDurata: ").append(time);
		sb.append("}");

		batchLog.setResult(sb.toString());
		batchRepository.updateLog(batchLog);

		
		recordInScadenza = datiAggiornamento[0];
		recordAggiornati = datiAggiornamento[1];

		if(recordInScadenza==0 && recordAggiornati==0) {
			log.info(METHOD_NAME, "Nessun record aggiornato");
			return 0;
		}else {
			log.info(METHOD_NAME, "Aggiornati record");
			return 1;
		}
		
	}
	
	public int[] delegheInScadenza () {
		final String METHOD_NAME = "delegheInScadenza";
		log.info(METHOD_NAME,"Inizio interrogazione e aggiornamenti db");
		
		int[] counters = new int[2];
		int recordInScadenza = 0;	
		int recordAggiornati = 0;
		boolean recordAggiornato = false;
		
		counters[0] = 0; 
		counters[1] = 0; 
			
		int giorniPreavviso = batchRepository.ricercaGiorniPreavviso();


		List<Integer> delIdserviziInScadenza = delegaServizioStatoRepository.ricercaServiziInScadenza(giorniPreavviso);
		
		if(delIdserviziInScadenza!=null && !delIdserviziInScadenza.isEmpty()) {
			recordInScadenza = delIdserviziInScadenza.size();
			log.info(METHOD_NAME,"recordInScadenza trovati: "+recordInScadenza);
			counters[0] = recordInScadenza;	
		}else {
			log.info(METHOD_NAME,"NON ci sono servizi in scadenza da aggiornare!");
		}

		for (Integer delIdservizioInscadenza : delIdserviziInScadenza) {			
			recordAggiornato =aggiornaStatoDelegaServizioInScadenza(delIdservizioInscadenza);
			if(recordAggiornato) {
				recordAggiornati++;
			}			
		}
		counters[1] = recordAggiornati;	
		
		if(counters[0] > 0 && counters[1] > 0) {
			List<Integer> listaDlgaId =batchRepository.cercaDlgaidAggiornati(giorniPreavviso);
			
			for (Integer singoloDlgaId : listaDlgaId) {				
				List<DatiPerNotificatoreDB> datiPerNotificatoreDB = batchRepository.cercaDatiAnagraficiPerNotificatore(singoloDlgaId);
				for (DatiPerNotificatoreDB datoPerNotificatoreDB : datiPerNotificatoreDB) {
					notificaDelegheInscadenza(datoPerNotificatoreDB);
				}		
			}				
		}				
		log.info(METHOD_NAME,"FINE interrogazione e aggiornamenti db");
		return counters;
	}	
	
	private boolean aggiornaStatoDelegaServizioInScadenza(Integer delId) {		
		boolean result = batchRepository.aggiornaStatoDelegaServizioInScadenza(delId);		
		
		return result;
	}
	

	public int scaduteAdultoDelegheBatch (Calendar gCal) {
		final String METHOD_NAME = "scaduteAdultoDelegheBatch";
		log.info(METHOD_NAME,"Inizio esecuzione");
		
		DeleTLogBatch batchLog = batchRepository.prepareLog("DELEGHE_ADU_SCADUTE", gCal);
		if (batchLog == null) return 0;
		
		long time = GregorianCalendar.getInstance().getTimeInMillis();
		time = GregorianCalendar.getInstance().getTimeInMillis() - time;
		
		int[] datiAggiornamento = delegheAdultoScadute();
		int recordscaduti = 0;	
		int recordAggiornati = 0;
		
		StringBuilder sb = new StringBuilder("{");
			sb.append("deleghe: {");
				sb.append("cntAduScadute: ").append(datiAggiornamento[0]).append(", ");
				sb.append("cntAggiornate: ").append(datiAggiornamento[1]);
			sb.append("}, ");
			sb.append("msecDurata: ").append(time);
		sb.append("}");

		batchLog.setResult(sb.toString());
		batchRepository.updateLog(batchLog);
		
		
		recordscaduti = datiAggiornamento[0];
		recordAggiornati = datiAggiornamento[1];

		if(recordscaduti==0 && recordAggiornati==0) {
			log.info(METHOD_NAME, "Nessun record aggiornato");
			return 0;
		}else {
			log.info(METHOD_NAME, "Aggiornati record");
			return 1;
		}
		
	}
	
	public int[] delegheAdultoScadute () {
		final String METHOD_NAME = "delegheAdultoScadute";
		log.info(METHOD_NAME,"Inizio interrogazione e aggiornamenti db");
		
		int[] counters = new int[2];
		int recordScadute = 0;	
		int recordAggiornati = 0;
		boolean recordAggiornato = false;
		
		counters[0] = 0; 
		counters[1] = 0; 
			
		List<Integer> delIdserviziScaduti = delegaServizioStatoRepository.ricercaServiziScaduti();
		
		if(delIdserviziScaduti!=null && !delIdserviziScaduti.isEmpty()) {
			recordScadute = delIdserviziScaduti.size();
			log.info(METHOD_NAME,"recordScadute trovati: "+recordScadute);
			counters[0] = recordScadute;	
		}else {
			log.info(METHOD_NAME,"NON ci sono servizi in scadenza o attivi da aggiornare per gli Adulti!");
		}

		for (Integer delIdservizioScaduto : delIdserviziScaduti) {			
			recordAggiornato =aggiornaStatoDelegaServizioScaduto(delIdservizioScaduto);
			if(recordAggiornato) {
				recordAggiornati++;
			}			
		}
		counters[1] = recordAggiornati;	
		
		
		if(counters[0] > 0 && counters[1] > 0) {
			List<Integer> listaDlgaId =batchRepository.cercaDlgaidAggiornatiScaduti();
			
			for (Integer singoloDlgaId : listaDlgaId) {				
				List<DatiPerNotificatoreDB> datiPerNotificatoreDB = batchRepository.cercaDatiAnagraficiPerNotificatoreScadute(singoloDlgaId);
				for (DatiPerNotificatoreDB datoPerNotificatoreDB : datiPerNotificatoreDB) {
					notificaDelegheScadute(datoPerNotificatoreDB);
				}		
			}				
		}				
		log.info(METHOD_NAME,"FINE interrogazione e aggiornamenti db");
		return counters;
	}	
	
	private boolean aggiornaStatoDelegaServizioScaduto(Integer delId) {		
		boolean result = batchRepository.aggiornaStatoDelegaServizioScaduto(delId);		
		
		return result;
	}
	

	public int scaduteMinoriDelegheBatch (Calendar gCal) {
		final String METHOD_NAME = "scaduteMinoriDelegheBatch";
		log.info(METHOD_NAME,"Inizio esecuzione");
		
		DeleTLogBatch batchLog = batchRepository.prepareLog("DELEGHE_MINORI", gCal);
		if (batchLog == null) return 0;
		

		long time = GregorianCalendar.getInstance().getTimeInMillis();
		time = GregorianCalendar.getInstance().getTimeInMillis() - time;
		

		int[] datiAggiornamento = delegheMinoriScadute();
		int recordscaduti = 0;	
		int recordAggiornatiDelegaServ = 0;
		int recordAggiornatiDelega = 0;
		int recordAggiornatiDichDett = 0;
		int recordAggiornatiDich = 0;

		

		StringBuilder sb = new StringBuilder("{");
			sb.append("deleghe: {");
				sb.append("cntMinScadute: ").append(datiAggiornamento[0]).append(", ");
				sb.append("cntAggiornateServiziDeleghe: ").append(datiAggiornamento[1]).append(", ");
				sb.append("cntAggiornateDeleghe: ").append(datiAggiornamento[2]).append(", ");
				sb.append("cntAggiornateDichDet: ").append(datiAggiornamento[3]).append(", ");
				sb.append("cntAggiornateDich: ").append(datiAggiornamento[4]);
			sb.append("}, ");
			sb.append("msecDurata: ").append(time);
		sb.append("}");


		batchLog.setResult(sb.toString());
		batchRepository.updateLog(batchLog);
		

		

		recordscaduti = datiAggiornamento[0];
		recordAggiornatiDelegaServ = datiAggiornamento[1];
		recordAggiornatiDelega = datiAggiornamento[2];
		recordAggiornatiDichDett = datiAggiornamento[3];
		recordAggiornatiDich = datiAggiornamento[4];

		if(recordscaduti==0 && recordAggiornatiDelegaServ==0 && recordAggiornatiDelega==0 && recordAggiornatiDichDett==0 && recordAggiornatiDich==0) {
			log.info(METHOD_NAME, "Nessun record aggiornato");
			return 0;
		}else {
			log.info(METHOD_NAME, "Aggiornati record");
			return 1;
		}
		
	}
	
	public int[] delegheMinoriScadute () {
		final String METHOD_NAME = "delegheMinoriScadute";
		log.info(METHOD_NAME,"Inizio interrogazione e aggiornamenti db");
				

		int recordScadute = 0;	
		int recordDelegaServAggiornate = 0;
		int recordDelegaAggiornate = 0;
		int recordDichDettAggiornate = 0;
		int recordDichAggiornate = 0;
		boolean recordAggiornatoDelegaServ = false;
		boolean recordAggiornatoDelega = false;
		boolean recordAggiornatoDichDett = false;
		boolean recordAggiornatoDich = false;
		
		int[] counters = new int[5];
		counters[0] = 0; 
		counters[1] = 0; 
		counters[2] = 0; 
		counters[3] = 0; 
		counters[4] = 0; 
			

		List<Integer> delIdserviziScadutiMinori = delegaServizioStatoRepository.ricercaServiziScadutiMinori();
		
		
		List<Integer> dlgaIdDelegheScaduteMinori = delegaServizioStatoRepository.ricercadlgaidDelegaScadutaMinori();
		

		if(delIdserviziScadutiMinori!=null && !delIdserviziScadutiMinori.isEmpty()) {
			recordScadute = delIdserviziScadutiMinori.size();
			log.info(METHOD_NAME,"recordScadute trovati: "+recordScadute);
			counters[0] = recordScadute;	
			

			List<DatiAggiormentoDichiarazioniDB> listaChiavi = delegaServizioStatoRepository.ricercaChiaviPerAggiornareDichiarazioni();
			

			if(listaChiavi!=null && !listaChiavi.isEmpty()) {
				

				for (Integer delIdservizioScadutoMinori : delIdserviziScadutiMinori) {			
					recordAggiornatoDelegaServ =aggiornaStatoDelegaServizioScadutoMinori(delIdservizioScadutoMinori);
					if(recordAggiornatoDelegaServ) {
						recordDelegaServAggiornate++;
					}			
				}
				counters[1] = recordDelegaServAggiornate;
				

				for (Integer dlgaIdDelegaScadutaMinori : dlgaIdDelegheScaduteMinori) {			
					recordAggiornatoDelega =aggiornaStatoDelegaScadutaMinori(dlgaIdDelegaScadutaMinori);
					if(recordAggiornatoDelega) {
						recordDelegaAggiornate++;
					}			
				}
				counters[2] = recordDelegaAggiornate;
				

				for (DatiAggiormentoDichiarazioniDB singoloChiavi : listaChiavi) {			
					recordAggiornatoDichDett =aggiornaStatoDichiarazioneDettaglioMinori(singoloChiavi.getDicdetId());
					if(recordAggiornatoDichDett) {
						recordDichDettAggiornate++;
					}			
				}
				counters[3] = recordDichDettAggiornate;
				

				for (DatiAggiormentoDichiarazioniDB singoloChiavi : listaChiavi) {			
					recordAggiornatoDich =aggiornaStatoDichiarazioneMinori(singoloChiavi.getDicId());
					if(recordAggiornatoDich) {
						recordDichAggiornate++;
					}			
				}
				counters[4] = recordDichAggiornate;
			}else {
				log.info(METHOD_NAME,"NON ci sono chiavi per aggiornare le dichiarazioni! Fermato aggiornamento Minori!");					
			}		
		}else {
			log.info(METHOD_NAME,"NON ci sono servizi in scadenza o attivi da aggiornare per i Minori!");
		}		
		
	

		if(counters[0] > 0 && counters[1] > 0 && counters[2] > 0 && counters[3] > 0 && counters[4] > 0) {

			List<Integer> listaDlgaId =batchRepository.cercaDlgaidAggiornatiScadutiMinori();
			
			for (Integer singoloDlgaId : listaDlgaId) {				

				List<DatiPerNotificatoreDB> datiPerNotificatoreDB = batchRepository.cercaDatiAnagraficiPerNotificatoreScaduteMinori(singoloDlgaId);
				for (DatiPerNotificatoreDB datoPerNotificatoreDB : datiPerNotificatoreDB) {

					notificaDelegheScaduteMinori(datoPerNotificatoreDB);
				}		
			}				
		}				
		log.info(METHOD_NAME,"FINE interrogazione e aggiornamenti db");
		return counters;
	}	
	
	private boolean aggiornaStatoDelegaServizioScadutoMinori(Integer delId) {		
		boolean result = batchRepository.aggiornaStatoDelegaServizioScadutoMinori(delId);		
		
		return result;
	}
	
	private boolean aggiornaStatoDelegaScadutaMinori(Integer dlgaId) {		
		boolean result = batchRepository.aggiornaStatoDelegaScadutaMinori(dlgaId);		
		
		return result;
	}
	
	private boolean aggiornaStatoDichiarazioneDettaglioMinori(Integer dicdetId) {		
		boolean result = batchRepository.aggiornaStatoDichiarazioneDettaglioMinori(dicdetId);		
		
		return result;
	}
	
	private boolean aggiornaStatoDichiarazioneMinori(Integer dicId) {		
		boolean result = batchRepository.aggiornaStatoDichiarazioneMinori(dicId);		
		
		return result;
	}

	
	public int scadenzaDichiarazioni () {
		int counter = 0;

		final DeleDDichiarazioneDetStato statoScadutaDIC = dichiarazioneDetStatoRepository.ricercaDetStatoByDicDetStatoCod(DicDetStatoCodEnum.SCADUTA);
		List<DeleTDichiarazioneDet> dichiarazioniDet = dichiarazioneDetRepository.ricercaDichiarazioneDetByStato(DicDetStatoCodEnum.DA_APPROVARE.name());
		for (DeleTDichiarazioneDet tDichiarazioneDet : dichiarazioniDet) {
			if (DateUtil.isMaggiorenne(tDichiarazioneDet.getDeleTCittadino2().getCitNascitaData())) {
				tDichiarazioneDet.setDeleDDichiarazioneDetStato(statoScadutaDIC);
				aggiornaStatoDichiarazioneDet(tDichiarazioneDet);
				counter++;
			}
		}
	
		return counter;
	}	

	public int scadenzaDichiarazioniBatch (Calendar gCal) {
		DeleTLogBatch batchLog = batchRepository.prepareLog("BATCH_DELEGHE_2", gCal);
		if (batchLog == null) return 0;
		
		long time = GregorianCalendar.getInstance().getTimeInMillis();
		int counter = scadenzaDichiarazioni();
		time = GregorianCalendar.getInstance().getTimeInMillis() - time;

		StringBuilder sb = new StringBuilder("{");
			sb.append("dichiarazioni: {");
				sb.append("cntScadute: ").append(counter);
			sb.append("}, ");
			sb.append("msecDurata: ").append(time);
		sb.append("}");
		
		batchLog.setResult(sb.toString());
		batchRepository.updateLog(batchLog);

		return 1;
	}	

	public int aggiornaDelegheServiziPerMinori () {
		return batchRepository.aggiornaDelegheServiziPerMinori();
	}
	
	public int aggiornaDelegheServiziPerMinoriBatch (Calendar gCal) {
		DeleTLogBatch batchLog = batchRepository.prepareLog("BATCH_DELEGHE_3", gCal);
		if (batchLog == null) return 0;

		long time = GregorianCalendar.getInstance().getTimeInMillis();
		int counter = aggiornaDelegheServiziPerMinori();
		time = GregorianCalendar.getInstance().getTimeInMillis() - time;
		StringBuilder sb = new StringBuilder("{");
			sb.append("servizi: {");
				sb.append("cntNuovi: ").append(counter);
			sb.append("}, ");
			sb.append("msecDurata: ").append(time);
		sb.append("}");
		
		batchLog.setResult(sb.toString());
		batchRepository.updateLog(batchLog);

		return 1;
	}
	
	private DelegaServ aggiornaStatoDelegaServizio(DeleTDelegaServizio tDelegaServ) {
		
		DeleTDelegaServizio deleTDelegaServizio = batchRepository.aggiornaStatoDelegaServizio(tDelegaServ);
		DeleTDelega deleTDelega = deleTDelegaServizio.getDeleTDelega();
		
		String key = deleTDelega.getDeleTCittadino1().getCitId() + "_" + deleTDelega.getDeleTCittadino1().getCitId() + "_" + deleTDelegaServizio.getDeleDDelegaServizioStato().getDelstatoCod();
		
		mapNotificaDeleghe.computeIfAbsent(key, k -> new ArrayList<>()).add(deleTDelegaServizio);
		
		return delegaServizioMapper.from(deleTDelegaServizio);
	}
	
	private void finalizzaNotifiche () {
		for (Entry<String, List<DeleTDelegaServizio>> entry : mapNotificaDeleghe.entrySet()) {
			DeleTDelegaServizio tDelegaServ = null;
			StringBuilder setServizi = new StringBuilder();
			for (DeleTDelegaServizio delegaServ : entry.getValue()) {
				tDelegaServ = delegaServ;
				setServizi.append((setServizi.length() > 0)? ", '" : "'").append(delegaServ.getDeleDServizio().getSerDesc()).append("'");
			}
			notifica(tDelegaServ, setServizi.toString());
		}
		mapNotificaDeleghe.clear();
	}

	private List<DeleTDelegaServizio> ricercaDelegheServiziInScadenza () {
		return ricercaDelegheServiziScadenza(false);
	}

	private List<DeleTDelegaServizio> ricercaDelegheServiziScaduti () {
		return ricercaDelegheServiziScadenza(true);
	}

	private List<DeleTDelegaServizio> ricercaDelegheServiziScadenza (boolean isScaduta) {
		int ggAnticipoScadenza = isScaduta? 0 : Integer.parseInt(servizioParametroRepository.getPreavvisoScadenzaServizio());
		
		return batchRepository.ricercaDelegheServiziScadenza(ggAnticipoScadenza);
	}

	
	private DeleTDichiarazioneDet aggiornaStatoDichiarazioneDet (DeleTDichiarazioneDet tDichiarazioneDet) {
		return batchRepository.aggiornaStatoDichiarazioneDet(tDichiarazioneDet);
	}
	
	/**
	 * Composizione chiamata al notificatore
	 * @param delegaServ
	 */
	private void notifica (DeleTDelegaServizio tDelegaServ, String servizi) {
		String operation = tDelegaServ.getDeleDDelegaServizioStato().getDelstatoCod().toLowerCase();
		String scope = (tDelegaServ.getDeleTDelega().getDeleTDichiarazioneDet() != null)? "delega_min" : "delega";
		
		Map<String,String> replacements = new HashMap<>();
		replacements.put("@DELEGANTE@", tDelegaServ.getDeleTDelega().getDeleTCittadino1().getCitNome() + " " +  tDelegaServ.getDeleTDelega().getDeleTCittadino1().getCitCognome());
		replacements.put("@DELEGATO@", tDelegaServ.getDeleTDelega().getDeleTCittadino2().getCitNome() + " " +  tDelegaServ.getDeleTDelega().getDeleTCittadino2().getCitCognome());
		replacements.put("@DTSCADENZA@", dtFormatter.format(tDelegaServ.getDelDatascadenza()));
		replacements.put("@SERVIZIO@", servizi);

		notificatoreUtil.callNotificatore(scope ,operation, "delegante", tDelegaServ.getDeleTDelega().getDeleTCittadino1().getCitCf(), replacements);
		notificatoreUtil.callNotificatore(scope, operation, "delegato", tDelegaServ.getDeleTDelega().getDeleTCittadino2().getCitCf(), replacements);
	}
	
	
	
	
	private void notificaDelegheInscadenza (DatiPerNotificatoreDB datiPerNotificatoreDB) {
		final String METHOD_NAME = "notificaDelegheInscadenza";
		String operation = "in_scadenza";
		String scope =  "delega";
		
		log.info(METHOD_NAME,"INIZIO NOTIFICA");
		

		Map<String,String> replacements = new HashMap<>();
		replacements.put("@DELEGANTE@", datiPerNotificatoreDB.getNomeDelegante() + " " +  datiPerNotificatoreDB.getCognomeDelegante());
		replacements.put("@DELEGATO@", datiPerNotificatoreDB.getNomeDelegato() + " " +  datiPerNotificatoreDB.getCognomeDelegato());
		replacements.put("@DTSCADENZA@", dtFormatter.format(datiPerNotificatoreDB.getDataDiScadenza()));
		replacements.put("@SERVIZIO@", datiPerNotificatoreDB.getCodiceServizio());

		notificatoreUtil.callNotificatore(scope ,operation, "delegante", datiPerNotificatoreDB.getCfDelegante(), replacements);
		notificatoreUtil.callNotificatore(scope, operation, "delegato", datiPerNotificatoreDB.getCfDelegato(), replacements);
		
		log.info(METHOD_NAME,"FINE NOTIFICA");
	}
	


	

	private void notificaDelegheScadute (DatiPerNotificatoreDB datiPerNotificatoreDB) {
		final String METHOD_NAME = "notificaDelegheScadute";
		String operation = "scaduta";
		String scope =  "delega";
		
		log.info(METHOD_NAME,"INIZIO NOTIFICA");
		
		Map<String,String> replacements = new HashMap<>();
		replacements.put("@DELEGANTE@", datiPerNotificatoreDB.getNomeDelegante() + " " +  datiPerNotificatoreDB.getCognomeDelegante());
		replacements.put("@DELEGATO@", datiPerNotificatoreDB.getNomeDelegato() + " " +  datiPerNotificatoreDB.getCognomeDelegato());
		replacements.put("@DTSCADENZA@", dtFormatter.format(datiPerNotificatoreDB.getDataDiScadenza()));
		replacements.put("@SERVIZIO@", datiPerNotificatoreDB.getCodiceServizio());

		notificatoreUtil.callNotificatore(scope ,operation, "delegante", datiPerNotificatoreDB.getCfDelegante(), replacements);
		notificatoreUtil.callNotificatore(scope, operation, "delegato", datiPerNotificatoreDB.getCfDelegato(), replacements);
		
		log.info(METHOD_NAME,"FINE NOTIFICA");
	}

	private void notificaDelegheScaduteMinori (DatiPerNotificatoreDB datiPerNotificatoreDB) {
		String operation = "scaduta";
		String scope =  "delega_min";
		
		
		Map<String,String> replacements = new HashMap<>();
		replacements.put("@DELEGANTE@", datiPerNotificatoreDB.getNomeDelegante() + " " +  datiPerNotificatoreDB.getCognomeDelegante());
		replacements.put("@DELEGATO@", datiPerNotificatoreDB.getNomeDelegato() + " " +  datiPerNotificatoreDB.getCognomeDelegato());
		replacements.put("@DTSCADENZA@", dtFormatter.format(datiPerNotificatoreDB.getDataDiScadenza()));
		replacements.put("@SERVIZIO@", datiPerNotificatoreDB.getCodiceServizio());

		notificatoreUtil.callNotificatore(scope ,operation, "delegante", datiPerNotificatoreDB.getCfDelegante(), replacements);
		notificatoreUtil.callNotificatore(scope, operation, "delegato", datiPerNotificatoreDB.getCfDelegato(), replacements);
	}

	
	
	
}
