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

import it.csi.deleghe.deleghebe.model.DeleDDichiarazioneModo;
import it.csi.deleghe.deleghebe.service.exception.BeServiceException;
import it.csi.deleghe.deleghebe.util.LogUtil;

@Stateless
public class DeleDDichiarazioneModoRepository {

    @Inject
    private LogUtil log;

    @Inject
    private EntityManager em;
    
    public enum DicModoCodEnum {
    	SPORTELLO,
    	ON_LINE
    	
    }

    public DeleDDichiarazioneModo ricercaServiziByDicModoCod(String dicModoCod)  {
    	DicModoCodEnum e;
    	try {
    		e = DicModoCodEnum.valueOf(dicModoCod);
    	} catch (IllegalArgumentException iae) {
    		throw new BeServiceException("DA.R03", "Codice modo dichiarazione ["+ dicModoCod + "] non previsto");
    	}
    	
    	return ricercaServiziByDicModoCod(e);
    }
    
    public DeleDDichiarazioneModo ricercaServiziByDicModoCod(DicModoCodEnum dicModoCod)  {
    	final String METHOD_NAME = "ricercaServiziByDicModoCod";
		log.debug(METHOD_NAME, "get service for: dicModoCod=%s", dicModoCod);

    	StringBuilder sb = new StringBuilder();
    	sb.append("SELECT d FROM DeleDDichiarazioneModo d ");
    	sb.append("WHERE d.dataCancellazione IS NULL ");
    			
    	Map<String, Object> params = new HashMap<>();
    	populateStatoFilter(sb, params, dicModoCod.toString());
    	
    	String jpql = sb.toString();
    	log.debug(METHOD_NAME, "jpql: %s", jpql);
    	TypedQuery<DeleDDichiarazioneModo> query = em.createQuery(jpql, DeleDDichiarazioneModo.class);
    	params.entrySet().stream().forEach(e -> {
    		String key = e.getKey();
    		Object value = e.getValue();
    		query.setParameter(key, value);
    		log.debug(METHOD_NAME, "Param: key: %s, value: %s", key, value);
    	});
    	DeleDDichiarazioneModo result;
    	try {
    		result = query.getSingleResult();
    	} catch(NonUniqueResultException nure) {
    		String msg = "Trovato piu' di un risultato per il codice "+ dicModoCod;
    		log.error(METHOD_NAME, msg, nure);
    		throw new BeServiceException("DA.R01", msg);
    	} catch(NoResultException nre) {
    		String msg = "Nessun risultato trovato per il codice "+ dicModoCod;
    		log.error(METHOD_NAME, msg, nre);
    		throw new BeServiceException("DA.R02", msg);
    	}
    	log.debug(METHOD_NAME, "result modoId: %s", result.getDicModoId());
    	return result;
    	
    }
	
	private void populateStatoFilter(StringBuilder sb, Map<String, Object> params, String dicModoCod) {
		if(StringUtils.isNotBlank(dicModoCod)) {
			sb.append("AND d.dicModoCod = :dicModoCod ");
			params.put("dicModoCod", dicModoCod);
		}
	}
}
