/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;

import it.csi.deleghe.deleghebe.model.DeleDDelegaServizioStato;
import it.csi.deleghe.deleghebe.service.exception.BeServiceException;
import it.csi.deleghe.deleghebe.util.LogUtil;
import it.csi.deleghe.deleghebe.ws.model.DatiAggiormentoDichiarazioniDB;
import it.csi.deleghe.deleghebe.ws.model.DatiPerNotificatoreDB;

@Stateless
public class DeleDDelegaServizioStatoRepository {

    @Inject
    private LogUtil log;

    @Inject
    private EntityManager em;
    
    public enum DelstatoCodEnum {
    	ATTIVA,
		VALIDATA,
    	IN_SCADENZA,
    	SCADUTA,
    	RIFIUTATA,	
    	REVOCATA,
    	AGGIORNATA
    }
    

    public DeleDDelegaServizioStato ricercaServiziStatoByDelstatoCod(String delstatoCod)  {
    	DelstatoCodEnum e;
    	try {
    		e = DelstatoCodEnum.valueOf(delstatoCod);
    	} catch (IllegalArgumentException iae) {
    		throw new BeServiceException("DA.R03", "Codice stato delega servizio ["+ delstatoCod + "] non previsto");
    	}
    	
    	return ricercaServiziStatoByDelstatoCod(e);
    }
    
    public DeleDDelegaServizioStato ricercaServiziStatoByDelstatoCod(DelstatoCodEnum delstatoCod)  {
    	final String METHOD_NAME = "ricercaServiziStatoByDelstatoCod";
		log.debug(METHOD_NAME, "get service for: delstatoCod=%s", delstatoCod);

    	StringBuilder sb = new StringBuilder();
    	sb.append("SELECT d FROM DeleDDelegaServizioStato d ");
    	sb.append("WHERE d.dataCancellazione IS NULL ");
    			
    	Map<String, Object> params = new HashMap<>();
    	populateStatoFilter(sb, params, delstatoCod.name());
    	
    	String jpql = sb.toString();
    	log.debug(METHOD_NAME, "jpql: %s", jpql);
    	TypedQuery<DeleDDelegaServizioStato> query = em.createQuery(jpql, DeleDDelegaServizioStato.class);
    	params.entrySet().stream().forEach(e -> {
    		String key = e.getKey();
    		Object value = e.getValue();
    		query.setParameter(key, value);
    		log.debug(METHOD_NAME, "Param: key: %s, value: %s", key, value);
    	});
    	DeleDDelegaServizioStato result;
    	try {
    		result = query.getSingleResult();
    	} catch(NonUniqueResultException nure) {
    		String msg = "Trovato piu' di un risultato per il codice "+ delstatoCod;
    		log.error(METHOD_NAME, msg, nure);
    		throw new BeServiceException("DA.R01", msg);
    	} catch(NoResultException nre) {
    		String msg = "Nessun risultato trovato per il codice "+ delstatoCod;
    		log.error(METHOD_NAME, msg, nre);
    		throw new BeServiceException("DA.R02", msg);
    	}
    	log.debug(METHOD_NAME, "result delestatoId: %s", result.getDelstatoId());
    	return result;
    	
    }
    

    public List<Integer> ricercaServiziInScadenza(int giorniPreavviso)  {
    	final String METHOD_NAME = "ricercaServiziInScadenza";
		log.debug(METHOD_NAME, "get service for: giorniPreavviso=%s", giorniPreavviso);		
		
		List<Integer> result = new ArrayList<Integer>();	
		Map<String, Object> params = new HashMap<>();
    	StringBuilder sb = new StringBuilder();
    	

    	sb.append("SELECT ");
    	sb.append("del_id ");
    	sb.append("FROM ");
    	sb.append("dele_t_delega_servizio ");
    	sb.append("WHERE ");
    	sb.append("delstato_id = 1 ");
    	sb.append("AND DATE_PART('day', del_datascadenza-current_date) < " + giorniPreavviso + " ");
    	sb.append("AND DATE_PART('day', del_datascadenza-current_date) > 0 ");
    	sb.append("AND data_cancellazione IS NULL ");
    	
    	String jpql = sb.toString();
    	log.debug(METHOD_NAME, "jpql: %s", jpql);
    	
    	Query query = em.createNativeQuery(jpql);
    	
    	List<Integer> resultQuery = query.getResultList();    	
		for(Integer servizioDelegaInscadenza : resultQuery) {
	
			Integer delId =servizioDelegaInscadenza.intValue();
		    
			result.add(delId);						
		}	
    	return result;    	
    }

	

    public List<Integer> ricercaServiziScaduti()  {
    	final String METHOD_NAME = "ricercaServiziScaduti";
		
		List<Integer> result = new ArrayList<Integer>();	
		Map<String, Object> params = new HashMap<>();
    	StringBuilder sb = new StringBuilder();    	
    	   	
    	sb.append("SELECT ");
    	sb.append("ds.del_id ");
    	sb.append("FROM ");
    	sb.append("dele_t_delega_servizio ds, dele_t_delega de ");
    	sb.append("WHERE ");
    	sb.append("ds.delstato_id IN (1,2) ");
    	sb.append("AND ds.del_datascadenza<=current_date ");
    	sb.append("AND ds.data_cancellazione IS NULL ");
    	sb.append("AND ds.dlga_id = de.dlga_id ");
    	sb.append("AND de.dicdet_id IS NULL ");
    	
    	String jpql = sb.toString();
    	log.debug(METHOD_NAME, "jpql: %s", jpql);
    	
    	Query query = em.createNativeQuery(jpql);
    	
    	List<Integer> resultQuery = query.getResultList();
    	
		for(Integer servizioDelegaScaduto : resultQuery) {
	
			Integer delId =servizioDelegaScaduto.intValue();
		    
			result.add(delId);						
		}	
    	return result;    	
    }

    public List<Integer> ricercaServiziScadutiMinori()  {
    	final String METHOD_NAME = "ricercaServiziScadutiMinori";
		
		List<Integer> result = new ArrayList<Integer>();	
		Map<String, Object> params = new HashMap<>();
    	StringBuilder sb = new StringBuilder();  
    	   	
    	sb.append("SELECT ");
    	sb.append("ds.del_id ");
    	sb.append("FROM ");
    	sb.append("dele_t_delega_servizio ds, dele_t_delega de ");
    	sb.append("WHERE ");
    	sb.append("ds.delstato_id IN (1,2) ");
    	sb.append("AND ds.del_datascadenza<=current_date ");
    	sb.append("AND ds.data_cancellazione IS NULL ");
    	sb.append("AND ds.dlga_id = de.dlga_id ");
    	sb.append("AND de.dicdet_id IS NOT NULL ");
    	
    	String jpql = sb.toString();
    	log.debug(METHOD_NAME, "jpql: %s", jpql);
    	
    	Query query = em.createNativeQuery(jpql);
    	
    	List<Integer> resultQuery = query.getResultList();
    	
		for(Integer servizioDelegaScaduto : resultQuery) {
	
			Integer delId =servizioDelegaScaduto.intValue();
		    
			result.add(delId);						
		}	
    	return result;    	
    }
    
    public List<Integer> ricercadlgaidDelegaScadutaMinori()  {
    	final String METHOD_NAME = "ricercadlgaidDelegaScadutaMinori";
		
		List<Integer> result = new ArrayList<Integer>();	
		Map<String, Object> params = new HashMap<>();
    	StringBuilder sb = new StringBuilder();      	
    	   	
    	sb.append("SELECT ");
    	sb.append("de.dlga_id ");
    	sb.append("FROM ");
    	sb.append("dele_t_delega_servizio ds, dele_t_delega de ");
    	sb.append("WHERE ");
    	sb.append("ds.delstato_id IN (1,2) ");
    	sb.append("AND ds.del_datascadenza<=current_date ");
    	sb.append("AND ds.data_cancellazione IS NULL ");
    	sb.append("AND ds.dlga_id = de.dlga_id ");
    	sb.append("AND de.dicdet_id IS NOT NULL ");
    	sb.append("GROUP BY de.dlga_id ");
    	
    	String jpql = sb.toString();
    	log.debug(METHOD_NAME, "jpql: %s", jpql);
    	
    	Query query = em.createNativeQuery(jpql);
    	
    	List<Integer> resultQuery = query.getResultList();
    	
		for(Integer servizioDelegaScaduto : resultQuery) {
	
			Integer dlgaId =servizioDelegaScaduto.intValue();
		    
			result.add(dlgaId);						
		}	
    	return result;    	
    }
    
    public List<DatiAggiormentoDichiarazioniDB> ricercaChiaviPerAggiornareDichiarazioni()  {
    	final String METHOD_NAME = "ricercaChiaviPerAggiornareDichiarazioni";
		
		List<DatiAggiormentoDichiarazioniDB> result = new ArrayList<DatiAggiormentoDichiarazioniDB>();	
		Map<String, Object> params = new HashMap<>();
    	StringBuilder sb = new StringBuilder();   	
    	   	
    	sb.append("SELECT ");
    	sb.append("de.dicdet_id, ");
    	sb.append("dt.dic_id ");
    	sb.append("FROM ");
    	sb.append("dele_t_delega_servizio ds, dele_t_delega de, dele_t_dichiarazione_det dt ");
    	sb.append("WHERE ");
    	sb.append("ds.delstato_id IN (1,2) ");
    	sb.append("AND ds.del_datascadenza<=current_date ");
    	sb.append("AND ds.data_cancellazione IS NULL ");
    	sb.append("AND ds.dlga_id = de.dlga_id ");
    	sb.append("AND de.dicdet_id IS NOT NULL ");
    	sb.append("AND de.dicdet_id = dt.dicdet_id ");
    	sb.append("GROUP BY ");
    	sb.append("de.dicdet_id, ");
    	sb.append("dt.dic_id ");
    	
    	String jpql = sb.toString();
    	log.debug(METHOD_NAME, "jpql: %s", jpql);
    	
    	Query query = em.createNativeQuery(jpql);
    	    	
		List<Object[]> resultQuery = query.getResultList();    	
    	
		for(Object[] listaChiavi : resultQuery) {
			DatiAggiormentoDichiarazioniDB datiAggiormentoDichiarazioniDB = new DatiAggiormentoDichiarazioniDB();
	
			Integer dicdetId = (Integer) listaChiavi[0];
			Integer dicId = (Integer) listaChiavi[1];
			
			datiAggiormentoDichiarazioniDB.setDicdetId(dicdetId);
			datiAggiormentoDichiarazioniDB.setDicId(dicId);
		
		    
			result.add(datiAggiormentoDichiarazioniDB);						
		}	
    	return result;    	
    }

    
	private void populateStatoFilter(StringBuilder sb, Map<String, Object> params, String delstatoCod) {
		if(StringUtils.isNotBlank(delstatoCod)) {
			sb.append("AND d.delstatoCod = :delstatoCod ");
			params.put("delstatoCod", delstatoCod);
		}
	}
}
