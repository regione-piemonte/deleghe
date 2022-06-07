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

import it.csi.deleghe.deleghebe.model.DeleDRuoloOp;
import it.csi.deleghe.deleghebe.service.exception.BeServiceException;
import it.csi.deleghe.deleghebe.util.LogUtil;

@Stateless
public class DeleDRuoloOpRepository {

    @Inject
    private LogUtil log;

    @Inject
    private EntityManager em;
    
    public enum RuoloOpCodEnum {
    	CITTADINO,
    	OPERATORE_ASL
    }

    public DeleDRuoloOp ricercaServiziByRuoloOpCod(String ruoloOpCod)  {
    	RuoloOpCodEnum e;
    	try {
    		e = RuoloOpCodEnum.valueOf(ruoloOpCod);
    	} catch (IllegalArgumentException iae) {
    		throw new BeServiceException("DA.R03", "Codice ruolo operativo ["+ ruoloOpCod + "] non previsto");
    	}
    	
    	return ricercaServiziByRuoloOpCod(e);
    }
    
    public DeleDRuoloOp ricercaServiziByRuoloOpCod(RuoloOpCodEnum ruoloOpCod)  {
    	final String METHOD_NAME = "ricercaServiziByRuoloOpCod";
		log.debug(METHOD_NAME, "get service for: ruoloOpCod=%s", ruoloOpCod);

    	StringBuilder sb = new StringBuilder();
    	sb.append("SELECT d FROM DeleDRuoloOp d ");
    	sb.append("WHERE d.dataCancellazione IS NULL ");
    			
    	Map<String, Object> params = new HashMap<>();
    	populateStatoFilter(sb, params, ruoloOpCod.toString());
    	
    	String jpql = sb.toString();
    	log.debug(METHOD_NAME, "jpql: %s", jpql);
    	TypedQuery<DeleDRuoloOp> query = em.createQuery(jpql, DeleDRuoloOp.class);
    	params.entrySet().stream().forEach(e -> {
    		String key = e.getKey();
    		Object value = e.getValue();
    		query.setParameter(key, value);
    		log.debug(METHOD_NAME, "Param: key: %s, value: %s", key, value);
    	});
    	DeleDRuoloOp result;
    	try {
    		result = query.getSingleResult();
    	} catch(NonUniqueResultException nure) {
    		String msg = "Trovato piu' di un risultato per il codice "+ ruoloOpCod;
    		log.error(METHOD_NAME, msg, nure);
    		throw new BeServiceException("DA.R01", msg);
    	} catch(NoResultException nre) {
    		String msg = "Nessun risultato trovato per il codice "+ ruoloOpCod;
    		log.error(METHOD_NAME, msg, nre);
    		throw new BeServiceException("DA.R02", msg);
    	}
    	log.debug(METHOD_NAME, "result ruoloopId: %s", result.getRuoloopId());
    	return result;
    	
    }
	
	private void populateStatoFilter(StringBuilder sb, Map<String, Object> params, String ruoloOpCod) {
		if(StringUtils.isNotBlank(ruoloOpCod)) {
			sb.append("AND d.ruoloopCod = :ruoloOpCod ");
			params.put("ruoloOpCod", ruoloOpCod);
		}
	}
}
