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

import it.csi.deleghe.deleghebe.model.DeleDCittadinoDatiStato;
import it.csi.deleghe.deleghebe.service.exception.BeServiceException;
import it.csi.deleghe.deleghebe.util.LogUtil;

@Stateless
public class DeleDCittadinoDatiStatoRepository {

    @Inject
    private LogUtil log;

    @Inject
    private EntityManager em;
    
    public enum CitDatiStatoCodEnum {
    	ATTIVO,
    	DISATTIVO
    	
    }

    public DeleDCittadinoDatiStato ricercaServiziByCitDatiStatoCod(String citDatiStatoCod)  {
    	CitDatiStatoCodEnum e;
    	try {
    		e = CitDatiStatoCodEnum.valueOf(citDatiStatoCod);
    	} catch (IllegalArgumentException iae) {
    		throw new BeServiceException("DA.R03", "Codice cittadino dati stato [" + citDatiStatoCod + "] non previsto");
    	}
    	
    	return ricercaServiziByCitDatiStatoCod(e);
    }
    
    public DeleDCittadinoDatiStato ricercaServiziByCitDatiStatoCod(CitDatiStatoCodEnum citDatiStatoCod)  {
    	final String METHOD_NAME = "ricercaServiziByCitDatiStatoCod";
		log.debug(METHOD_NAME, "get service for: citDatiStatoCod=%s", citDatiStatoCod);

    	StringBuilder sb = new StringBuilder();
    	sb.append("SELECT d FROM DeleDCittadinoDatiStato d ");
    	sb.append("WHERE d.dataCancellazione IS NULL ");
    			
    	Map<String, Object> params = new HashMap<>();
    	populateStatoFilter(sb, params, citDatiStatoCod.toString());
    	
    	String jpql = sb.toString();
    	log.debug(METHOD_NAME, "jpql: %s", jpql);
    	TypedQuery<DeleDCittadinoDatiStato> query = em.createQuery(jpql, DeleDCittadinoDatiStato.class);
    	params.entrySet().stream().forEach(e -> {
    		String key = e.getKey();
    		Object value = e.getValue();
    		query.setParameter(key, value);
    		log.debug(METHOD_NAME, "Param: key: %s, value: %s", key, value);
    	});
    	DeleDCittadinoDatiStato result;
    	try {
    		result = query.getSingleResult();
    	} catch(NonUniqueResultException nure) {
    		String msg = "Trovato piu' di un risultato per il codice "+ citDatiStatoCod;
    		log.error(METHOD_NAME, msg, nure);
    		throw new BeServiceException("DA.R01", msg);
    	} catch(NoResultException nre) {
    		String msg = "Nessun risultato trovato per il codice "+ citDatiStatoCod;
    		log.error(METHOD_NAME, msg, nre);
    		throw new BeServiceException("DA.R02", msg);
    	}
    	log.debug(METHOD_NAME, "result citdstatoId: %s", result.getCitdstatoId());
    	return result;
    	
    }
	
	private void populateStatoFilter(StringBuilder sb, Map<String, Object> params, String citDatiStatoCod) {
		if(StringUtils.isNotBlank(citDatiStatoCod)) {
			sb.append("AND d.citdstatoCod = :citDatiStatoCod ");
			params.put("citDatiStatoCod", citDatiStatoCod);
		}
	}
}
