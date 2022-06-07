/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service;


import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;
import javax.persistence.TypedQuery;

import org.hibernate.exception.ConstraintViolationException;

import it.csi.deleghe.deleghebe.model.DeleDDichiarazioneStato;
import it.csi.deleghe.deleghebe.model.DeleDServizio;
import it.csi.deleghe.deleghebe.model.DeleTDelega;
import it.csi.deleghe.deleghebe.model.DeleTDelegaServizio;
import it.csi.deleghe.deleghebe.model.DeleTDichiarazione;
import it.csi.deleghe.deleghebe.model.DeleTDichiarazioneDet;
import it.csi.deleghe.deleghebe.model.DeleTLogBatch;
import it.csi.deleghe.deleghebe.service.DeleDDelegaServizioStatoRepository.DelstatoCodEnum;
import it.csi.deleghe.deleghebe.service.DeleDDichiarazioneStatoRepository.DicStatoCodEnum;
import it.csi.deleghe.deleghebe.service.exception.BeServiceException;
import it.csi.deleghe.deleghebe.util.LogUtil;
import it.csi.deleghe.deleghebe.util.ParamsUtil;
import it.csi.deleghe.deleghebe.ws.model.DatiDelegaServizioDB;
import it.csi.deleghe.deleghebe.ws.model.DatiDelegatoDB;
import it.csi.deleghe.deleghebe.ws.model.DatiPerNotificatoreDB;

@Stateless
public class BatchDelegaRepository extends BaseRepository {

	private static final int RESULTSET_SIZE = 100;
	
	private final DateFormat dfmt = new SimpleDateFormat("yyyyMMddHHmm");

	@Inject
	private LogUtil log;
	@Inject
	private ParamsUtil paramsUtil;

	@Inject
	private EntityManager em;

	@Inject
	private DeleDDelegaServizioStatoRepository delegaServizioStatoRep;
	@Inject
	private DeleDDichiarazioneStatoRepository dichiarazioneStatoRep;
	@Inject
	private DeleDServizioRepository servizioRep;
	
	/**
	 * La modifica prevede esclusivamente l'aggionamento dello stato in SCADUTA o IN_SCADENZA
	 * @param delegaServizio
	 * @return
	 */
	public DeleTDelegaServizio aggiornaStatoDelegaServizio(DeleTDelegaServizio tDelegaServizio) {
		final String METHOD_NAME = "aggiornaStatoDelegaServizio";

		if (tDelegaServizio.getDelId() != null) {
			DeleTDelegaServizio deleTDelegaServizio = em.find(DeleTDelegaServizio.class, tDelegaServizio.getDelId());

			String statoCod = tDelegaServizio.getDeleDDelegaServizioStato().getDelstatoCod();
			if (statoCod.equals(DelstatoCodEnum.SCADUTA.name()) || statoCod.equals(DelstatoCodEnum.IN_SCADENZA.name())) {
				deleTDelegaServizio.setDeleDDelegaServizioStato(delegaServizioStatoRep.ricercaServiziStatoByDelstatoCod(statoCod));
				deleTDelegaServizio.setUuid(UUID.randomUUID());
				em.merge(deleTDelegaServizio);
			} 
			else {
				String msg = "La delega per il servizio [" + tDelegaServizio.getDeleDServizio().getSerCod()  + "] non puo cambiare stato.";
				log.error(METHOD_NAME, msg);
				throw new BeServiceException("DA.E01", msg);
			}
			
			tDelegaServizio = deleTDelegaServizio;
		}
		return tDelegaServizio;
	}

	/**
	 * Ricerca le deleghe che si trovano a ggAnticipoScadenza giorni dalla data di scadenza
	 * Il numero massimo di risultati restituito è definito nella costante RESULTSET_SIZE
	 * @param ggAnticipoScadenza
	 * @return
	 */
	public List<DeleTDelegaServizio> ricercaDelegheServiziScadenza (Integer ggAnticipoScadenza) {
		final String METHOD_NAME = "ricercaDelegheServiziScadenza";

		List<String> statiDelega = new ArrayList<>();
		statiDelega.add(DeleDDelegaServizioStatoRepository.DelstatoCodEnum.ATTIVA.name());
		if (ggAnticipoScadenza == 0) statiDelega.add(DeleDDelegaServizioStatoRepository.DelstatoCodEnum.IN_SCADENZA.name());
		
		String dateCheckOperator = (ggAnticipoScadenza == 0)? "<=" : "=";
		ggAnticipoScadenza--;
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ds FROM DeleTDelegaServizio ds ");
		sb.append("WHERE ds.dataCancellazione IS NULL ");
		sb.append("AND DATE_TRUNC('day', ds.delDatascadenza) " + dateCheckOperator + " (CURRENT_DATE() + CAST(:ggAnticipo AS int)) ");
		sb.append("AND ds.deleDDelegaServizioStato.delstatoCod IN (:statiDelega) ");

		Map<String, Object> params = new HashMap<>();
		params.put("ggAnticipo", ggAnticipoScadenza);
		params.put("statiDelega", statiDelega);

		String jpql = sb.toString();
		TypedQuery<DeleTDelegaServizio> query = em.createQuery(jpql, DeleTDelegaServizio.class).setMaxResults(RESULTSET_SIZE);
		params.entrySet().stream().forEach(e -> {
			String key = e.getKey();
			Object value = e.getValue();
			query.setParameter(key, value);
			log.debug(METHOD_NAME, "Param: key: %s, value: %s", key, value);
		});

		return query.getResultList();
	}

	// ***********************************************************************************************
	/**
	 * Aggiorna le deleghe per minori aggiungendo eventuali servizi aggiunti
	 * Il numero massimo di risultati restituito è definito nella costante RESULTSET_SIZE
	 * @param ggAnticipoScadenza
	 * @return
	 */
	public int aggiornaDelegheServiziPerMinori () {
		final String METHOD_NAME = "aggiornaDelegheServiziPerMinori";

		final String strCNT_SERVIZI = paramsUtil.getParametro("CNT_SERVIZI_MINORI");
		final int CNT_SERVIZI = (strCNT_SERVIZI != null)? Integer.parseInt(strCNT_SERVIZI) : 0;
		final int cnt_servizi = servizioRep.getAllServicesDelegabiliMinore().size();
		
		if (CNT_SERVIZI < cnt_servizi) {
		
			List<String> statiDelega = Arrays.asList(
				DeleDDelegaServizioStatoRepository.DelstatoCodEnum.ATTIVA.name(),
				DeleDDelegaServizioStatoRepository.DelstatoCodEnum.IN_SCADENZA.name()
			);
			
			StringBuilder sb;
			String jpql;
			
			sb = new StringBuilder();
			sb.append("SELECT DISTINCT ds.deleTDelega FROM DeleTDelegaServizio ds ");
			sb.append("WHERE ds.dataCancellazione IS NULL ");
			sb.append("AND ds.deleDDelegaServizioStato.delstatoCod IN (:statiDelega) ");
			sb.append("AND ds.deleTDelega.deleTDichiarazioneDet IS NOT NULL ");
	
			Map<String, Object> params = new HashMap<>();
			params.put("statiDelega", statiDelega);
	
			jpql = sb.toString();
			TypedQuery<DeleTDelega> query0 = em.createQuery(jpql,DeleTDelega.class).setMaxResults(RESULTSET_SIZE);
			params.entrySet().stream().forEach(e -> {
				String key = e.getKey();
				Object value = e.getValue();
				query0.setParameter(key, value);
				log.debug(METHOD_NAME, "Param: key: %s, value: %s", key, value);
			});
			
			boolean loop = true;
			while (loop) {
				List<DeleTDelega> deleghe = query0.getResultList();
				loop = !deleghe.isEmpty();
				if (loop) {

					sb = new StringBuilder();
					sb.append("SELECT s FROM DeleDServizio s ");
					sb.append("WHERE s.dataCancellazione IS NULL ");
					sb.append("AND s.serDelegabile = TRUE ");
					sb.append("AND s.serMinore = TRUE ");
					sb.append("AND NOT EXISTS (");
						sb.append("SELECT 1 FROM DeleTDelegaServizio ds WHERE ds.dataCancellazione IS NULL ");
						sb.append("AND ds.deleTDelega.dlgaId = :dlgaId ");
						sb.append("AND ds.deleDServizio.serId = s.serId");
					sb.append(")");
			
					jpql = sb.toString();
			
					for (DeleTDelega delega : deleghe) {
						DeleTDelegaServizio dsSource = delega.getDeleTDelegaServizios().get(0);
						TypedQuery<DeleDServizio> query1 = em.createQuery(jpql, DeleDServizio.class);
						query1.setParameter("dlgaId", delega.getDlgaId());
						List<DeleDServizio> servizi = query1.getResultList();
						for (DeleDServizio servizio : servizi) {
							DeleTDelegaServizio ds = clone(dsSource);
							ds.setUuid(UUID.randomUUID());
							ds.setDeleDServizio(servizio);
							delega.addDeleTDelegaServizio(ds);
						}
						em.merge(delega);
					}
					em.flush();
					em.clear();
				}
			}

			paramsUtil.setParametro("CNT_SERVIZI_MINORI", String.valueOf(cnt_servizi));
			
			return (cnt_servizi - CNT_SERVIZI) ;
		}
		return 0;
	}
	// ***********************************************************************************************

	
	static final List<String> statiDichiarazioneValidi = new ArrayList<>();
	static {
		statiDichiarazioneValidi.add(DeleDDichiarazioneDetStatoRepository.DicDetStatoCodEnum.DA_APPROVARE.name());
		statiDichiarazioneValidi.add(DeleDDichiarazioneDetStatoRepository.DicDetStatoCodEnum.VALIDA.name());
	}


	/**
	 * La modifica prevede esclusivamente l'aggionamento dello stato in SCADUTA
	 * @param tDichiarazioneDet
	 * @return
	 */
	public DeleTDichiarazioneDet aggiornaStatoDichiarazioneDet(DeleTDichiarazioneDet tDichiarazioneDet) {
		final String METHOD_NAME = "aggiornaStatoDichiarazione";

		if (tDichiarazioneDet.getDicdetId() != null) {
			DeleTDichiarazioneDet deleTDichiarazioneDet = em.find(DeleTDichiarazioneDet.class, tDichiarazioneDet.getDicdetId());

			if (tDichiarazioneDet.getDeleDDichiarazioneDetStato().getDicdetStatoCod().equals(DelstatoCodEnum.SCADUTA.name())) {
				deleTDichiarazioneDet.setDeleDDichiarazioneDetStato(tDichiarazioneDet.getDeleDDichiarazioneDetStato());
				deleTDichiarazioneDet.setUuid(UUID.randomUUID());
				
				if (isDichiarazioneScaduta(deleTDichiarazioneDet.getDeleTDichiarazione().getDicId())) {
					DeleDDichiarazioneStato dicStatoSCADUTA = dichiarazioneStatoRep.ricercaDichiarazioneStatoByDicStatoCod(DicStatoCodEnum.SCADUTA);
					
					// aggiornamento anche della dichiarazione
					DeleTDichiarazione deleTDichiarazione = deleTDichiarazioneDet.getDeleTDichiarazione();
					deleTDichiarazione.setDeleDDichiarazioneStato(dicStatoSCADUTA);
					deleTDichiarazione.setUuid(UUID.randomUUID());
				} 

				em.merge(deleTDichiarazioneDet);
			} 
			else {
				String msg = "La dichiarazione per il cittadino [" + tDichiarazioneDet.getDeleTCittadino1().getCitCf()  + "] non puo cambiare stato.";
				log.error(METHOD_NAME, msg);
				throw new BeServiceException("DA.E01", msg);
			}
			
			tDichiarazioneDet = deleTDichiarazioneDet;
		}
		return tDichiarazioneDet;
	}
	
	/**
	 * 
	 * @return
	 */
	public DeleTLogBatch prepareLog (String batchName, Calendar gCal) {
		final String METHOD_NAME = "prepareLog";
		
		if (gCal == null) gCal = GregorianCalendar.getInstance();
		DeleTLogBatch tLogBatch = new DeleTLogBatch();
		tLogBatch.setBatchId(Long.parseLong(dfmt.format(gCal.getTime())));
		tLogBatch.setBatchName(batchName);
		tLogBatch.setDataOra(new Timestamp(gCal.getTimeInMillis()));
		tLogBatch.setResult("*");
		try {
			em.persist(tLogBatch);
			em.flush();
		} catch (PersistenceException ex) {
			Throwable cause = ex.getCause();
			while (cause != null) {
				if (cause instanceof ConstraintViolationException) {
					log.error(METHOD_NAME, "Eccezione attesa, da non considerare.");
					break;
				}
				cause = cause.getCause();
			}
			return null;
		}
		
		return tLogBatch;
	}
	
	/**
	 * 
	 * @param deleTLogBatch
	 * @return
	 */
	public DeleTLogBatch updateLog (DeleTLogBatch deleTLogBatch) {
		DeleTLogBatch tLogBatch = em.merge(deleTLogBatch);
		em.flush();
		return tLogBatch;
	}
	
	//------------------------------------------------------------------

	private boolean isDichiarazioneScaduta (Integer dicId) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT COUNT(dd) FROM DeleTDichiarazioneDet dd ");
		sb.append("WHERE dd.dataCancellazione IS NULL ");
		sb.append("AND dd.deleTDichiarazione.dicId = :dicId ");
		sb.append("AND dd.deleDDichiarazioneDetStato.dicdetStatoCod IN (:statiDichiarazione) ");

		Query query = em.createQuery(sb.toString());
		query.setParameter("dicId", dicId);
		query.setParameter("statiDichiarazione", statiDichiarazioneValidi);
		
		int validCount = ((Number)query.getSingleResult()).intValue();

		return (validCount == 0);
	}
	
	private DeleTDelegaServizio clone (DeleTDelegaServizio tDelegaServizio) {
		DeleTDelegaServizio ds = new DeleTDelegaServizio();
		ds.setDelDatadecorrenza(new Date());
		ds.setDelDatascadenza(tDelegaServizio.getDelDatascadenza());
		ds.setDeleDDelegaServizioStato(tDelegaServizio.getDeleDDelegaServizioStato());
		setCreazione(ds, tDelegaServizio.getLoginOperazione());
		
		return ds;
	}
	

	public int ricercaGiorniPreavviso () {		
		StringBuilder sb = new StringBuilder();	
		Integer giorniPreavvisoInteger;
		int giorniPreavvisoInt=0;		
		
		sb.append("SELECT serpar_valore ");
		sb.append("FROM dele_t_servizio_parametro  ");
		sb.append("WHERE serpar_cod ='PRESCAD' ");		
		
		String jpql = sb.toString();
    	Query query = em.createNativeQuery(jpql);
		
		String giorniPreavviso = ((String)query.getSingleResult()).toString();
		giorniPreavvisoInteger = new Integer(giorniPreavviso);
		giorniPreavvisoInt = giorniPreavvisoInteger.intValue();

		return giorniPreavvisoInt;
	}
	
	public boolean aggiornaStatoDelegaServizioInScadenza(Integer delId) {
		final String METHOD_NAME = "aggiornaStatoDelegaServizioInScadenza";
		
		boolean result = false;	
		int resultQuery=0;
		
		StringBuilder sb = new StringBuilder();	
		
		sb.append("UPDATE ");
		sb.append("dele_t_delega_servizio  ");
		sb.append("SET ");	
		sb.append("delstato_id = 2, ");	
		sb.append("data_modifica = now() ");		
		sb.append("WHERE ");	
		sb.append("del_id = "+delId.intValue());	
		
		String jpql = sb.toString();

    	try {
    		Query query = em.createNativeQuery(jpql);
        	resultQuery = query.executeUpdate();
        	
		} catch (TransactionRequiredException tre) {
			result = false;
    	}    	
    	log.info(METHOD_NAME, "Aggiornamento eseguito per delId: "+delId.intValue());
    	
    	if(resultQuery==1){
    		result = true;
    	}
    	
		return result;
	}
	
	public List<DatiPerNotificatoreDB> cercaDatiAnagraficiPerNotificatore(Integer dlga_id) {
		final String METHOD_NAME = "cercaDatiAnagraficiPerNotificatore";	
		log.info(METHOD_NAME,"Anagrafiche per invio mail: INIZIO");
		
		List<DatiPerNotificatoreDB> result = new ArrayList<DatiPerNotificatoreDB>();
		StringBuilder sb = new StringBuilder();	 	
		
		sb.append("SELECT ");
		sb.append("string_agg(se.ser_desc, ', ') as descrizione, ");
		sb.append("ds.del_datascadenza, ");
		sb.append("cd.cit_nome as nomedelegante, "); //NOME DELEGANTE
		sb.append("cd.cit_cognome as cognomedelegante, "); //COGNOME DELEGANTE
		sb.append("cd.cit_cf as cfdelegante, "); //CF DELEGANTE
		sb.append("co.cit_nome as nomedelegato, "); //NOME DELEGATO
		sb.append("co.cit_cognome as cognomedelegato, "); //COGNOME DELEGATO	
		sb.append("co.cit_cf as cfdelegato "); //CF DELEGATO
		sb.append("FROM ");	
		sb.append("deleghe.dele_t_delega_servizio ds, deleghe.dele_d_servizio se, deleghe.dele_t_delega da, ");	
		sb.append("deleghe.dele_t_cittadino cd, deleghe.dele_t_cittadino co ");	
		sb.append("WHERE ");	
		sb.append("ds.dlga_id = "+dlga_id+" ");
		sb.append("and ds.delstato_id = 2 ");
		sb.append("and ds.ser_id = se.ser_id ");
		sb.append("and ds.dlga_id = da.dlga_id ");
		sb.append("and da.cit_id_delegante = cd.cit_id ");
		sb.append("and da.cit_id_delegato = co.cit_id ");
		sb.append("GROUP BY ");
		sb.append("ds.del_datascadenza, ");
		sb.append("nomedelegante, ");
		sb.append("cognomedelegante, ");
		sb.append("cfdelegante, ");
		sb.append("nomedelegato, ");
		sb.append("cognomedelegato, ");
		sb.append("cfdelegato ");
		
		String jpql = sb.toString();
    	Query query = em.createNativeQuery(jpql);

    	List<Object[]> resultQuery = query.getResultList(); 
    	
			for(Object[] datiPerNotificatore : resultQuery) {
				DatiPerNotificatoreDB datiPerMail = new DatiPerNotificatoreDB();
				
				String codiceServizio = (String) datiPerNotificatore[0];
				Date dataDiScadenza = (Date) datiPerNotificatore[1];
				String nomeDelegante = (String) datiPerNotificatore[2];
			    String cognomeDelegante = (String) datiPerNotificatore[3];
			    String cfDelegante = (String) datiPerNotificatore[4];	
			    String nomeDelegato = (String) datiPerNotificatore[5];	
			    String cognomeDelegato = (String) datiPerNotificatore[6];	
			    String cfDelegato = (String) datiPerNotificatore[7];	
				
			    datiPerMail.setCodiceServizio(codiceServizio);
			    datiPerMail.setDataDiScadenza(dataDiScadenza);
			    datiPerMail.setNomeDelegante(nomeDelegante);
			    datiPerMail.setCognomeDelegante(cognomeDelegante);
			    datiPerMail.setCfDelegante(cfDelegante);			    
			    datiPerMail.setNomeDelegato(nomeDelegato);
			    datiPerMail.setCognomeDelegato(cognomeDelegato);
			    datiPerMail.setCfDelegato(cfDelegato);	
				
	
			    
			    result.add(datiPerMail);
			}			
		log.info(METHOD_NAME,"Anagrafiche per invio mail: TROVATE");
		return result;
	}
	
	public List<Integer> cercaDlgaidAggiornati(int giorniPreavviso) {
		final String METHOD_NAME = "cercaDlgaidAggiornati";	
		log.debug(METHOD_NAME, "Inizio ricerca record aggiornati");
		
		List<Integer> result = new ArrayList<Integer>();
		StringBuilder sb = new StringBuilder();			
		
		sb.append("SELECT ");
		sb.append("dlga_id ");
		sb.append("FROM ");	
		sb.append("dele_t_delega_servizio ");	
		sb.append("WHERE ");	
		sb.append("delstato_id = 2 ");	
		sb.append("AND DATE_PART('day', del_datascadenza-current_date) < " + giorniPreavviso + " ");
    	sb.append("AND DATE_PART('day', del_datascadenza-current_date) > 0 ");
    	sb.append("AND data_cancellazione IS NULL ");
    	sb.append("GROUP BY ");
    	sb.append("dlga_id");
		
		String jpql = sb.toString();

    	Query query = em.createNativeQuery(jpql);

    	List<Integer> resultQuery = query.getResultList();
    	for(Integer listaDlgaId : resultQuery) {

    		Integer dlgaId = listaDlgaId.intValue();    	    

    	    result.add(dlgaId);						
    	}  
    	log.debug(METHOD_NAME, "FINE ricerca record aggiornati");
		return result;
	}	

	public boolean aggiornaStatoDelegaServizioScaduto(Integer delId) {
		final String METHOD_NAME = "aggiornaStatoDelegaServizioScaduto";
		
		boolean result = false;	
		int resultQuery=0;
		
		StringBuilder sb = new StringBuilder();	
		
		sb.append("UPDATE ");
		sb.append("dele_t_delega_servizio  ");
		sb.append("SET ");	
		sb.append("delstato_id = 5, ");	
		sb.append("data_modifica = now() ");		
		sb.append("WHERE ");	
		sb.append("del_id = "+delId.intValue());	
		
		String jpql = sb.toString();

    	try {
    		Query query = em.createNativeQuery(jpql);
        	resultQuery = query.executeUpdate();
        	
		} catch (TransactionRequiredException tre) {
			result = false;
    	}  
    	log.info(METHOD_NAME, "Aggiornamento eseguito per delId: "+delId.intValue());

    	
    	if(resultQuery==1){
    		result = true;
    	}
    	
		return result;
	}
	
	public List<Integer> cercaDlgaidAggiornatiScaduti() {
		final String METHOD_NAME = "cercaDlgaidAggiornatiScaduti";	
		log.debug(METHOD_NAME, "Inizio ricerca record aggiornati");	
		
		List<Integer> result = new ArrayList<Integer>();
		StringBuilder sb = new StringBuilder();				
		
		sb.append("SELECT ");
		sb.append("ds.dlga_id ");
		sb.append("FROM ");	
		sb.append("dele_t_delega_servizio ds, dele_t_delega de ");	
		sb.append("WHERE ");	
		sb.append("ds.delstato_id = 5 ");		 
		sb.append("AND (ds.del_datascadenza = current_date OR ds.del_datascadenza = current_date-1) ");
    	sb.append("AND ds.data_cancellazione IS NULL ");
    	sb.append("AND ds.dlga_id = de.dlga_id ");
    	sb.append("AND de.dicdet_id IS NULL ");
    	sb.append("GROUP BY ");
    	sb.append("ds.dlga_id ");
		
		String jpql = sb.toString();

    	Query query = em.createNativeQuery(jpql);

    	List<Integer> resultQuery = query.getResultList();
    	for(Integer listaDlgaId : resultQuery) {

    		Integer dlgaId = listaDlgaId.intValue();    	    

    	    result.add(dlgaId);						
    	}   
    	log.debug(METHOD_NAME, "FINE ricerca record aggiornati");
		return result;
	}	
	
	public List<DatiPerNotificatoreDB> cercaDatiAnagraficiPerNotificatoreScadute(Integer dlga_id) {
		final String METHOD_NAME = "cercaDatiAnagraficiPerNotificatoreScadute";	
		log.info(METHOD_NAME,"Anagrafiche per invio mail: INIZIO");
		
		List<DatiPerNotificatoreDB> result = new ArrayList<DatiPerNotificatoreDB>();
		StringBuilder sb = new StringBuilder();	 	
		
		sb.append("SELECT ");
		sb.append("string_agg(se.ser_desc, ', ') as descrizione, ");
		sb.append("ds.del_datascadenza, ");
		sb.append("cd.cit_nome as nomedelegante, "); //NOME DELEGANTE
		sb.append("cd.cit_cognome as cognomedelegante, "); //COGNOME DELEGANTE
		sb.append("cd.cit_cf as cfdelegante, "); //CF DELEGANTE
		sb.append("co.cit_nome as nomedelegato, "); //NOME DELEGATO
		sb.append("co.cit_cognome as cognomedelegato, "); //COGNOME DELEGATO	
		sb.append("co.cit_cf as cfdelegato "); //CF DELEGATO
		sb.append("FROM ");	
		sb.append("deleghe.dele_t_delega_servizio ds, deleghe.dele_d_servizio se, deleghe.dele_t_delega da, ");	
		sb.append("deleghe.dele_t_cittadino cd, deleghe.dele_t_cittadino co ");	
		sb.append("WHERE ");	
		sb.append("ds.dlga_id = "+dlga_id+" ");
		sb.append("and ds.delstato_id = 5 "); //SCADUTE
		sb.append("and ds.ser_id = se.ser_id ");
		sb.append("and ds.dlga_id = da.dlga_id ");
		sb.append("and da.cit_id_delegante = cd.cit_id ");
		sb.append("and da.cit_id_delegato = co.cit_id ");
		sb.append("and (ds.del_datascadenza = current_date OR ds.del_datascadenza = current_date-1) ");
		sb.append("GROUP BY ");
		sb.append("ds.del_datascadenza, ");
		sb.append("nomedelegante, ");
		sb.append("cognomedelegante, ");
		sb.append("cfdelegante, ");
		sb.append("nomedelegato, ");
		sb.append("cognomedelegato, ");
		sb.append("cfdelegato ");
		
		String jpql = sb.toString();
    	Query query = em.createNativeQuery(jpql);

    	List<Object[]> resultQuery = query.getResultList(); 	

			for(Object[] datiPerNotificatore : resultQuery) {
				DatiPerNotificatoreDB datiPerMail = new DatiPerNotificatoreDB();
				
				String codiceServizio = (String) datiPerNotificatore[0];
				Date dataDiScadenza = (Date) datiPerNotificatore[1];
				String nomeDelegante = (String) datiPerNotificatore[2];
			    String cognomeDelegante = (String) datiPerNotificatore[3];
			    String cfDelegante = (String) datiPerNotificatore[4];	
			    String nomeDelegato = (String) datiPerNotificatore[5];	
			    String cognomeDelegato = (String) datiPerNotificatore[6];	
			    String cfDelegato = (String) datiPerNotificatore[7];	
				
			    datiPerMail.setCodiceServizio(codiceServizio);
			    datiPerMail.setDataDiScadenza(dataDiScadenza);
			    datiPerMail.setNomeDelegante(nomeDelegante);
			    datiPerMail.setCognomeDelegante(cognomeDelegante);
			    datiPerMail.setCfDelegante(cfDelegante);			    
			    datiPerMail.setNomeDelegato(nomeDelegato);
			    datiPerMail.setCognomeDelegato(cognomeDelegato);
			    datiPerMail.setCfDelegato(cfDelegato);	
	
			    
			    result.add(datiPerMail);
			}			
			log.info(METHOD_NAME,"Anagrafiche per invio mail: TROVATE");
		return result;
	}

	public boolean aggiornaStatoDelegaServizioScadutoMinori(Integer delId) {
		final String METHOD_NAME = "aggiornaStatoDelegaServizioScadutoMinori";
		
		boolean result = false;	
		int resultQuery=0;
		
		StringBuilder sb = new StringBuilder();	
		
		sb.append("UPDATE ");
		sb.append("dele_t_delega_servizio  ");
		sb.append("SET ");	
		sb.append("delstato_id = 5, ");	
		sb.append("data_modifica = now() ");		
		sb.append("WHERE ");	
		sb.append("del_id = "+delId.intValue());	
		
		String jpql = sb.toString();

    	try {
    		Query query = em.createNativeQuery(jpql);
        	resultQuery = query.executeUpdate();
        	
		} catch (TransactionRequiredException tre) {
			result = false;
    	}  
    	log.info(METHOD_NAME, "Aggiornamento eseguito per delId: "+delId.intValue());

    	
    	if(resultQuery==1){
    		result = true;
    	}
    	
		return result;
	}
	
	public boolean aggiornaStatoDelegaScadutaMinori(Integer dlgaId) {
		final String METHOD_NAME = "aggiornaStatoDelegaScadutaMinori";
		
		boolean result = false;	
		int resultQuery=0;
		
		StringBuilder sb = new StringBuilder();	
		
		sb.append("UPDATE ");
		sb.append("dele_t_delega  ");
		sb.append("SET ");	
		sb.append("del_stato_id = 7, ");	
		sb.append("data_modifica = now() ");		
		sb.append("WHERE ");	
		sb.append("dlga_id = "+dlgaId.intValue());	
		
		String jpql = sb.toString();

    	try {
    		Query query = em.createNativeQuery(jpql);
        	resultQuery = query.executeUpdate();
        	
		} catch (TransactionRequiredException tre) {
			result = false;
    	}  
    	log.info(METHOD_NAME, "Aggiornamento eseguito per dlgaId: "+dlgaId.intValue());

    	
    	if(resultQuery==1){
    		result = true;
    	}
    	
		return result;
	}
	
	
	public boolean aggiornaStatoDichiarazioneDettaglioMinori(Integer dicdetId) {
		final String METHOD_NAME = "aggiornaStatoDichiarazioneDettaglioMinori";
		
		boolean result = false;	
		int resultQuery=0;
		
		StringBuilder sb = new StringBuilder();	
		
		sb.append("UPDATE ");
		sb.append("dele_t_dichiarazione_det  ");
		sb.append("SET ");	
		sb.append("dicdet_stato_id = 5, ");	
		sb.append("data_modifica = now() ");		
		sb.append("WHERE ");	
		sb.append("dicdet_id = "+dicdetId.intValue());	
		
		String jpql = sb.toString();

    	try {
    		Query query = em.createNativeQuery(jpql);
        	resultQuery = query.executeUpdate();
        	
		} catch (TransactionRequiredException tre) {
			result = false;
    	}  
    	log.info(METHOD_NAME, "Aggiornamento eseguito per dicdetId: "+dicdetId.intValue());

    	
    	if(resultQuery==1){
    		result = true;
    	}
    	
		return result;
	}
	
	public boolean aggiornaStatoDichiarazioneMinori(Integer dicId) {
		final String METHOD_NAME = "aggiornaStatoDichiarazioneMinori";
		
		boolean result = false;	
		int resultQuery=0;
		
		StringBuilder sb = new StringBuilder();	
		
		sb.append("UPDATE ");
		sb.append("dele_t_dichiarazione  ");
		sb.append("SET ");	
		sb.append("dic_stato_id = 4, ");	
		sb.append("data_modifica = now() ");		
		sb.append("WHERE ");	
		sb.append("dic_id = "+dicId.intValue());	
		
		String jpql = sb.toString();

    	try {
    		Query query = em.createNativeQuery(jpql);
        	resultQuery = query.executeUpdate();
        	
		} catch (TransactionRequiredException tre) {
			result = false;
    	}  
    	log.info(METHOD_NAME, "Aggiornamento eseguito per dicId: "+dicId.intValue());

    	
    	if(resultQuery==1){
    		result = true;
    	}
    	
		return result;
	}
	
	public List<Integer> cercaDlgaidAggiornatiScadutiMinori() {
		final String METHOD_NAME = "cercaDlgaidAggiornatiScadutiMinori";	
		log.debug(METHOD_NAME, "Inizio ricerca record aggiornati");	
		
		List<Integer> result = new ArrayList<Integer>();
		StringBuilder sb = new StringBuilder();		
		
		sb.append("SELECT ");
		sb.append("ds.dlga_id ");
		sb.append("FROM ");	
		sb.append("dele_t_delega_servizio ds, dele_t_delega de, dele_t_dichiarazione_det dt, dele_t_dichiarazione dc ");	
		sb.append("WHERE ");	
		sb.append("ds.delstato_id = 5 ");
		sb.append("AND dt.dicdet_stato_id = 5 ");
		sb.append("AND dc.dic_stato_id = 4 ");
		sb.append("AND ds.del_datascadenza = current_date ");
    	sb.append("AND ds.data_cancellazione IS NULL ");
    	sb.append("AND ds.dlga_id = de.dlga_id ");
    	sb.append("AND de.dicdet_id IS NOT NULL ");
    	sb.append("AND de.dicdet_id = dt.dicdet_id ");
    	sb.append("AND dt.dic_id = dc.dic_id ");
    	sb.append("GROUP BY ");
    	sb.append("ds.dlga_id ");
		
		String jpql = sb.toString();

    	Query query = em.createNativeQuery(jpql);

    	List<Integer> resultQuery = query.getResultList();
    	for(Integer listaDlgaId : resultQuery) {

    		Integer dlgaId = listaDlgaId.intValue();    	    

    	    result.add(dlgaId);						
    	}   
    	log.debug(METHOD_NAME, "FINE ricerca record aggiornati");
		return result;
	}	
	
	public List<DatiPerNotificatoreDB> cercaDatiAnagraficiPerNotificatoreScaduteMinori(Integer dlga_id) {
		final String METHOD_NAME = "cercaDatiAnagraficiPerNotificatoreScaduteMinori";	
		log.info(METHOD_NAME,"Anagrafiche per invio mail: INIZIO");
		
		List<DatiPerNotificatoreDB> result = new ArrayList<DatiPerNotificatoreDB>();
		StringBuilder sb = new StringBuilder();	 	
		
		sb.append("SELECT ");
		sb.append("string_agg(se.ser_desc, ', ') as descrizione, ");
		sb.append("ds.del_datascadenza, ");
		sb.append("cd.cit_nome as nomedelegante, "); //NOME DELEGANTE
		sb.append("cd.cit_cognome as cognomedelegante, "); //COGNOME DELEGANTE
		sb.append("cd.cit_cf as cfdelegante, "); //CF DELEGANTE
		sb.append("co.cit_nome as nomedelegato, "); //NOME DELEGATO
		sb.append("co.cit_cognome as cognomedelegato, "); //COGNOME DELEGATO	
		sb.append("co.cit_cf as cfdelegato "); //CF DELEGATO
		sb.append("FROM ");	
		sb.append("deleghe.dele_t_delega_servizio ds, deleghe.dele_d_servizio se, deleghe.dele_t_delega da, ");	
		sb.append("deleghe.dele_t_cittadino cd, deleghe.dele_t_cittadino co ");	
		sb.append("WHERE ");	
		sb.append("ds.dlga_id = "+dlga_id+" ");
		sb.append("and ds.delstato_id = 5 "); //SCADUTE
		sb.append("and ds.ser_id = se.ser_id ");
		sb.append("and ds.dlga_id = da.dlga_id ");
		sb.append("and da.cit_id_delegante = cd.cit_id ");
		sb.append("and da.cit_id_delegato = co.cit_id ");
		sb.append("GROUP BY ");
		sb.append("ds.del_datascadenza, ");
		sb.append("nomedelegante, ");
		sb.append("cognomedelegante, ");
		sb.append("cfdelegante, ");
		sb.append("nomedelegato, ");
		sb.append("cognomedelegato, ");
		sb.append("cfdelegato ");
		
		String jpql = sb.toString();
    	Query query = em.createNativeQuery(jpql);

    	List<Object[]> resultQuery = query.getResultList(); 	

			for(Object[] datiPerNotificatore : resultQuery) {
				DatiPerNotificatoreDB datiPerMail = new DatiPerNotificatoreDB();
				
				String codiceServizio = (String) datiPerNotificatore[0];
				Date dataDiScadenza = (Date) datiPerNotificatore[1];
				String nomeDelegante = (String) datiPerNotificatore[2];
			    String cognomeDelegante = (String) datiPerNotificatore[3];
			    String cfDelegante = (String) datiPerNotificatore[4];	
			    String nomeDelegato = (String) datiPerNotificatore[5];	
			    String cognomeDelegato = (String) datiPerNotificatore[6];	
			    String cfDelegato = (String) datiPerNotificatore[7];	
				
			    datiPerMail.setCodiceServizio(codiceServizio);
			    datiPerMail.setDataDiScadenza(dataDiScadenza);
			    datiPerMail.setNomeDelegante(nomeDelegante);
			    datiPerMail.setCognomeDelegante(cognomeDelegante);
			    datiPerMail.setCfDelegante(cfDelegante);			    
			    datiPerMail.setNomeDelegato(nomeDelegato);
			    datiPerMail.setCognomeDelegato(cognomeDelegato);
			    datiPerMail.setCfDelegato(cfDelegato);	
				

			    
			    result.add(datiPerMail);
			}			
			log.info(METHOD_NAME,"Anagrafiche per invio mail: TROVATE");
		return result;
	}

}
