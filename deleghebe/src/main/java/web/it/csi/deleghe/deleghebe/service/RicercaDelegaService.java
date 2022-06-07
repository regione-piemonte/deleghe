/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service;

import static it.csi.deleghe.deleghebe.util.Check.checkCondition;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import it.csi.deleghe.deleghebe.service.base.BaseService;
import it.csi.deleghe.deleghebe.ws.model.Delega;
import it.csi.deleghe.deleghebe.ws.model.Errore;
import it.csi.deleghe.deleghebe.ws.model.RisultatoCodice;
import it.csi.deleghe.deleghebe.ws.msg.RicercaDelega;
import it.csi.deleghe.deleghebe.ws.msg.RicercaDelegaResponse;


public class RicercaDelegaService extends BaseService<RicercaDelega, RicercaDelegaResponse> {

	@Inject
	private DelegaBean delegaBean;
	
	
	@Override
	protected void checkServiceParams(RicercaDelega req) {
		
		/**
		 * Si noti che, se indichiamo il codice fiscale del delegato/delegante allora 
		 * tutti gli altri campi non sono obbligatori; nel momento in cui questo codice 
		 * fiscale viene a mancare allora, per non appesantire la query, si rendera obbligatorio 
		 * almeno uno tra il periodo di validita e il periodo di inserimento con una durata massima 
		 * pari a 30 giorni.
		 */
		boolean filtroCitidDelegato =  req.getCiIdDelegato()!=null;
		boolean filtroCitidDelegante =  req.getCitIdDelegante()!=null;
		boolean filtroCFDelegato =  !StringUtils.isEmpty(req.getCodiceFiscaleDelegato());
		boolean filtroCFDelegante =  !StringUtils.isEmpty(req.getCodiceFiscaleDelegante());
		boolean filtroDataInserimento = req.getDataInserimentoDa()!=null && req.getDataInserimentoA()!=null;
		boolean filtroDataValidita = req.getDataValiditaDa()!=null && req.getDataValiditaA()!=null;
		
 

		checkCondition((filtroCFDelegato || filtroCFDelegante || filtroCitidDelegato || filtroCitidDelegante), "MSG006", "ATTENZIONE! Presenza di errori nei filtri di ricerca");
		
		if(filtroCFDelegato) checkCondition((req.getCodiceFiscaleDelegato().trim().length() == 16),"MSG035", "Codice fiscale delegato non valido" );
		if(filtroCFDelegante) checkCondition((req.getCodiceFiscaleDelegante().trim().length() == 16),"MSG035", "Codice fiscale delegante non valido");
		
		if(!filtroCFDelegato && !filtroCFDelegante) {
			
					 

			if(filtroDataInserimento) {
				checkCondition(verificaIntervalloDiTempo(req.getDataInserimentoDa(),req.getDataInserimentoA()),"MSGXXX", "Periodo di inserimento non valido. Impostare un intervallo di tempo non superiore ai 30 giorni.");
			}
			
			if(filtroDataValidita) {
				checkCondition(verificaIntervalloDiTempo(req.getDataValiditaDa(),req.getDataValiditaA()),"MSGXXX", "Periodo di validita' non valido. Impostare un intervallo di tempo non superiore ai 30 giorni.");
			}
			
		}

	}

	@Override
	protected RicercaDelegaResponse execute(RicercaDelega req) {
		
		String METHOD_NAME="execute";
		
		List<Delega> deleghe = null;
		
		String cfDelegato = req.getCodiceFiscaleDelegato();
		String cfDelegante = req.getCodiceFiscaleDelegante();
		Date dataInsDa = req.getDataInserimentoDa();
		Date dataInsA = req.getDataInserimentoA();
		Date dataValDa = req.getDataValiditaDa();
		Date dataValA = req.getDataValiditaA();
		Integer ciIdDelegato = req.getCiIdDelegato();
		Integer citIdDelegante = req.getCitIdDelegante();
		
		List<String> statiDelega = new ArrayList<>();
		if(req.getStatiDelega()!=null && !req.getStatiDelega().isEmpty()) {
			req.getStatiDelega().forEach(e -> {
				String codice = e.getCodice();
				statiDelega.add(codice);
				log.debug(METHOD_NAME, " Stato Codice: %s", codice);
			});
		}
		
		List<String> tipiDelega = new ArrayList<>();
		if(req.getTipiDelega()!=null && !req.getTipiDelega().isEmpty()) {
			req.getTipiDelega().forEach(e -> {
				String codice = e.getCodice();
				tipiDelega.add(codice);
				log.debug(METHOD_NAME, " Tipo Codice: %s", codice);
			});
		}
		
		
		deleghe = delegaBean.ricercaDeleghe(cfDelegato,cfDelegante, dataInsDa, dataInsA, 
											dataValDa, dataValA, 
											tipiDelega,
											statiDelega, ciIdDelegato, citIdDelegante);
		
		
		RicercaDelegaResponse res = new RicercaDelegaResponse();
		res.setEsito(RisultatoCodice.SUCCESSO);
		
		if(deleghe==null || deleghe.isEmpty()) {
			List<Errore> errori = new ArrayList<>();
			errori.add(new Errore("MSG005","ATTENZIONE! Nessun risultato corrispondente ai criteri di ricerca impostati"));
			res.setErrori(errori);
			res.setEsito(RisultatoCodice.FALLIMENTO);
			return res;
		}
		
		res.setDeleghe(deleghe);
		return res;
	}
	
	private boolean verificaIntervalloDiTempo(Date dataDa, Date dataA) {
		Calendar dataInsA = Calendar.getInstance();
		Calendar dataInsDa = Calendar.getInstance();
		
		dataInsDa.setTime(dataDa);
		dataInsA.setTime(dataA);
		
		dataInsA.add(Calendar.DATE, -30);
		return dataInsA.compareTo(dataInsDa) <0;
		
	}

}
