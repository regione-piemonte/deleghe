/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;

import it.csi.deleghe.deleghebe.model.DeleDDelegaServizioStato;
import it.csi.deleghe.deleghebe.model.DeleTDelegaServizio;
import it.csi.deleghe.deleghebe.service.DeleDDelegaServizioStatoRepository.DelstatoCodEnum;
import it.csi.deleghe.deleghebe.service.exception.BeServiceException;
import it.csi.deleghe.deleghebe.util.LogUtil;

@Stateless
public class DeleTDelegaServizioRepository extends BaseRepository{

    @Inject
    private LogUtil log;

    @Inject
    private EntityManager em;
    
    @Inject
    private DeleDDelegaServizioStatoRepository delegaServizioStato;

    
    public DeleTDelegaServizio aggiornaStatoRinunciatoDelegaServizio(DeleTDelegaServizio delegaServizioInput, String loginOperazione)  {
    	final String METHOD_NAME = "aggiornaStatoDelegaServizio";
    	log.debug(METHOD_NAME, "aggiorna stato delega servizio for UUID:" + delegaServizioInput.getUuid());
    	
    	DeleTDelegaServizio delegaServizio = em.find(DeleTDelegaServizio.class, delegaServizioInput.getDelId());
    	setModifica(delegaServizio, loginOperazione);
    	
    	Date delDatarinuncia = new Date();
    	
    	DeleDDelegaServizioStato deleDDelegaServizioStato = delegaServizioStato.ricercaServiziStatoByDelstatoCod(DelstatoCodEnum.RIFIUTATA);
		delegaServizio.setDeleDDelegaServizioStato(deleDDelegaServizioStato);
    	delegaServizio.setDelDatarinuncia(delDatarinuncia);
    	delegaServizio.setDelDatascadenza(delDatarinuncia);
    	delegaServizio.setUuid(UUID.randomUUID());
    	
    	em.merge(delegaServizio);
    	
    	return delegaServizio;
    }
    
    public DeleTDelegaServizio ricercaDelegaServizioByUUID(UUID uuid)  {
    	final String METHOD_NAME = "ricercaDelegaServizioByUUID";
    	log.debug(METHOD_NAME, "UUID:" + uuid);
    	
    	Map<String, Object> params = new HashMap<>();
    	StringBuilder sb = new StringBuilder();
    	sb.append("SELECT tab FROM DeleTDelegaServizio tab ");
    	sb.append("WHERE tab.dataCancellazione IS NULL ");
    	sb.append("AND tab.uuid = :uuid ");
    	params.put("uuid", uuid);
    	String jpql = sb.toString();
    	TypedQuery<DeleTDelegaServizio> query = em.createQuery(jpql, DeleTDelegaServizio.class);
    	params.entrySet().stream().forEach(e -> {
    		String key = e.getKey();
    		Object value = e.getValue();
    		query.setParameter(key, value);
    		log.debug(METHOD_NAME, "Param: key: %s, value: %s", key, value);
    	});
    	
    	DeleTDelegaServizio result;
		try {
			result = query.getSingleResult();
		} catch (NonUniqueResultException nure) {
    		String msg = "Trovato piu' di un risultato per l'UUID " + uuid;
    		log.error(METHOD_NAME, msg, nure);
    		throw new BeServiceException("DA.R01", msg);
    	} catch(NoResultException nre) {
    		String msg = "Nessun risultato trovato per l'UUID " + uuid;
    		log.error(METHOD_NAME, msg, nre);
			return null;
		}

    	return result;
    }

}
