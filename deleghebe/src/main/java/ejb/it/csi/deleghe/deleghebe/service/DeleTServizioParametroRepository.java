/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;

import it.csi.deleghe.deleghebe.model.DeleTServizioParametro;
import it.csi.deleghe.deleghebe.service.exception.BeServiceException;
import it.csi.deleghe.deleghebe.util.LogUtil;

@Stateless
public class DeleTServizioParametroRepository {

    @Inject
    private LogUtil log;

    @Inject
    private EntityManager em;
    
    public enum SerParCodEnum {
    	SCAD,
    	PRESCAD,
    	MAXDLGT,
    	MAXDLGN
    }

    public String getValiditaServizio(Integer serId) {
    	return ricercaServiziParametroBySerIdSerParCod(serId,SerParCodEnum.SCAD).getSerparValore();
    }

    public String getPreavvisoScadenzaServizio() {
    	return ricercaServiziParametroBySerParCod(SerParCodEnum.PRESCAD).getSerparValore();
    }

    public String getMaxNumeroDelegatiServizio() {
    	return ricercaServiziParametroBySerParCod(SerParCodEnum.MAXDLGT).getSerparValore();
    }

    public String getMaxNumeroDelegantiServizio() {
    	return ricercaServiziParametroBySerParCod(SerParCodEnum.MAXDLGN).getSerparValore();
    }
    
    public DeleTServizioParametro ricercaServiziParametroBySerParCod(String serParCod)  {
    	SerParCodEnum e;
    	try {
    		e = SerParCodEnum.valueOf(serParCod);
    	} catch (IllegalArgumentException iae) {
    		throw new IllegalArgumentException("Codice servizio parametro non previsto:  " + serParCod, iae);
    	}
    	
    	return ricercaServiziParametroBySerParCod(e);
    }
    
    public DeleTServizioParametro ricercaServiziParametroBySerParCod(SerParCodEnum serParCod)  {
    	final String METHOD_NAME = "ricercaServiziParametroBySerParCod";
		log.debug(METHOD_NAME, "get service for: serParCod=%s", serParCod);

    	StringBuilder sb = new StringBuilder();
    	sb.append("SELECT d FROM DeleTServizioParametro d ");
    	sb.append("WHERE d.dataCancellazione IS NULL ");
    			
    			
    	Map<String, Object> params = new HashMap<>();
    	populateFilter(sb, params, serParCod.name());
    	
    	String jpql = sb.toString();
    	log.debug(METHOD_NAME, "jpql: %s", jpql);
    	TypedQuery<DeleTServizioParametro> query = em.createQuery(jpql, DeleTServizioParametro.class);
    	params.entrySet().stream().forEach(e -> {
    		String key = e.getKey();
    		Object value = e.getValue();
    		query.setParameter(key, value);
    		log.debug(METHOD_NAME, "Param: key: %s, value: %s", key, value);
    	});
    	DeleTServizioParametro result;
    	try {
    		result = query.getSingleResult();
    	} catch(NonUniqueResultException nure) {
    		String msg = "Trovato piu' di un risultato per il codice "+ serParCod;
    		log.error(METHOD_NAME, msg, nure);
    		throw new BeServiceException("DA.R01", msg);
    	} catch(NoResultException nre) {
    		String msg = "Nessun risultato trovato per il codice "+ serParCod;
    		log.error(METHOD_NAME, msg, nre);
    		throw new BeServiceException("DA.R02", msg);
    	}
    	log.debug(METHOD_NAME, "result serparId: %s", result.getSerparId());
    	return result;
    	
    }
	
	private void populateFilter(StringBuilder sb, Map<String, Object> params, String serParCod) {
		if(StringUtils.isNotBlank(serParCod)) {
			sb.append("AND d.serparCod = :serParCod ");
			params.put("serParCod", serParCod);
		}
	}
	
	public DeleTServizioParametro ricercaServiziParametroBySerIdSerParCod(Integer serId,SerParCodEnum serParCod)  {
		final String METHOD_NAME = "ricercaServiziParametroBySerParCod";
		log.debug(METHOD_NAME, "get service for: serParCod=%s", serParCod);

    	StringBuilder sb = new StringBuilder();
    	sb.append("SELECT d FROM DeleTServizioParametro d ");
    	sb.append("WHERE d.dataCancellazione IS NULL ");
    			
    			
    	Map<String, Object> params = new HashMap<>();
    	populateFilter(sb, params, serId, serParCod.name());
    	
    	String jpql = sb.toString();
    	log.debug(METHOD_NAME, "jpql: %s", jpql);
    	TypedQuery<DeleTServizioParametro> query = em.createQuery(jpql, DeleTServizioParametro.class);
    	params.entrySet().stream().forEach(e -> {
    		String key = e.getKey();
    		Object value = e.getValue();
    		query.setParameter(key, value);
    		log.debug(METHOD_NAME, "Param: key: %s, value: %s", key, value);
    	});
    	DeleTServizioParametro result;
    	try {
    		result = query.getSingleResult();
    	} catch(NonUniqueResultException nure) {
    		String msg = "Trovato piu' di un risultato per il codice "+ serParCod;
    		log.error(METHOD_NAME, msg, nure, new Object[] {});
    		throw new IllegalArgumentException(msg);
    	} catch(NoResultException nre) {
    		String msg = "Nessun risultato trovato per il codice "+ serParCod;
    		log.error(METHOD_NAME, msg, nre, new Object[] {});
    		throw new IllegalArgumentException(msg);
    	}
    	log.debug(METHOD_NAME, "result serparId: %s", result.getSerparId());
    	return result;
	}
	
	private void populateFilter(StringBuilder sb, Map<String, Object> params, Integer serId, String serParCod) {
		if(StringUtils.isNotBlank(serParCod)) {
			sb.append("AND d.serparCod = :serParCod ");
			params.put("serParCod", serParCod);
		}
		if(serId!=null) {
			sb.append("AND d.deleDServizio.serId = :serId ");
			params.put("serId", serId);
		}
	}
}