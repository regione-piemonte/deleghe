/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;

import it.csi.deleghe.deleghebe.model.DeleDServizio;
import it.csi.deleghe.deleghebe.service.exception.BeServiceException;
import it.csi.deleghe.deleghebe.util.LogUtil;
import it.csi.deleghe.deleghebe.util.LogUtil.ToLog;

@Stateless
public class DeleDServizioRepository {

    @Inject
    private LogUtil log;

    @Inject
    private EntityManager em;
    
    
    public List<DeleDServizio> getAllServicesDelegabiliMinore() {
    	final String METHOD_NAME = "getAllServices";
    	log.debug(METHOD_NAME, "recupera tutti i servizi che sono delegabili e per minori");

    	StringBuilder sb = getBasicQueryStringBuilder();
    	sb.append("AND (d.validitaFine IS NULL OR d.validitaFine > CURRENT_DATE()) ");
    	sb.append("AND d.serDelegabile = TRUE ");
    	sb.append("AND d.serMinore = TRUE ");
    	
    	String jpql = sb.toString();
    	TypedQuery<DeleDServizio> query = em.createQuery(jpql, DeleDServizio.class);

    	List<DeleDServizio> result = query.getResultList();
    	
    	return result;
    }
    
    public DeleDServizio ricercaServiziBySerCod(String serCod)  {
    	final String METHOD_NAME = "ricercaServiziBySerCod";
		log.debug(METHOD_NAME, "get service for: serCod=%s", serCod);

    	StringBuilder sb = getBasicQueryStringBuilder();
    			
    	Map<String, Object> params = new HashMap<>();
    	populateStatoFilter(sb, params, serCod);
    	
    	String jpql = sb.toString();
    	log.debug(METHOD_NAME, "jpql: %s", jpql);
    	TypedQuery<DeleDServizio> query = em.createQuery(jpql, DeleDServizio.class);
    	params.entrySet().stream().forEach(e -> {
    		String key = e.getKey();
    		Object value = e.getValue();
    		query.setParameter(key, value);
    		log.debug(METHOD_NAME, "Param: key: %s, value: %s", key, value);
    	});
    	DeleDServizio result;
    	try {
    		result = query.getSingleResult();
    	} catch(NonUniqueResultException nure) {
    		String msg = "Trovato piu' di un risultato per il codice "+ serCod;
    		log.error(METHOD_NAME, msg, nure);
    		throw new BeServiceException("DA.R01", msg);
    	} catch(NoResultException nre) {
    		String msg = "Nessun risultato trovato per il codice "+ serCod;
    		log.error(METHOD_NAME, msg, nre);
    		throw new BeServiceException("DA.R02", msg);
    	}
    	log.debug(METHOD_NAME, "result delestatoId: %s", result.getSerCod());
    	return result;
    	
    }
	
	private void populateStatoFilter(StringBuilder sb, Map<String, Object> params, String serCod) {
		if(StringUtils.isNotBlank(serCod)) {
			sb.append("AND d.serCod = :serCod ");
			params.put("serCod", serCod);
		}
	}
	
	public List<DeleDServizio> ricercaServizi()  {
		return ricercaServizi(null);
	}
	
	public List<DeleDServizio> ricercaServizi(DeleDServizio dServizio)  {
    	final String METHOD_NAME = "getServizi";
		log.debug(METHOD_NAME, "get service for: serCod=%s", (ToLog) () -> {
			return dServizio != null ? dServizio.getSerCod() : "null";
		});

    	StringBuilder sb = getBasicQueryStringBuilder();
    	sb.append("AND (d.validitaFine IS NULL OR d.validitaFine > CURRENT_DATE()) ");
    			
    	Map<String, Object> params = new HashMap<>();
    	populateFilter(sb, params, dServizio);
    	
    	String jpql = sb.toString();
    	log.debug(METHOD_NAME, "jpql: %s", jpql);
    	TypedQuery<DeleDServizio> query = em.createQuery(jpql, DeleDServizio.class);
    	params.entrySet().stream().forEach(e -> {
    		String key = e.getKey();
    		Object value = e.getValue();
    		query.setParameter(key, value);
    		log.debug(METHOD_NAME, "Param: key: %s, value: %s", key, value);
    	});
    	
    	List<DeleDServizio> result = query.getResultList();
    	log.debug(METHOD_NAME, "result size: %s", result.size());
    	return result;
    	
    }
   
	private StringBuilder getBasicQueryStringBuilder () {
    	StringBuilder sb = new StringBuilder();
    	sb.append("SELECT d FROM DeleDServizio d ");
    	sb.append("WHERE d.dataCancellazione IS NULL ");
    	return sb;
	}

	private void populateFilter(StringBuilder sb, Map<String, Object> params, DeleDServizio dServizio) {
		if(dServizio!=null) {
    		if(StringUtils.isNotBlank(dServizio.getSerCod())) {
    			sb.append("AND d.serCod = :serCod ");
    			params.put("serCod", dServizio.getSerCod());
    		}
    	}
	}
	
}
