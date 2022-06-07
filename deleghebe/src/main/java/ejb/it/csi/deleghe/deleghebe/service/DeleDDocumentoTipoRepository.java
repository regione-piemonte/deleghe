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

import it.csi.deleghe.deleghebe.model.DeleDDichiarazioneDetStato;
import it.csi.deleghe.deleghebe.model.DeleDDocumentoTipo;
import it.csi.deleghe.deleghebe.service.exception.BeServiceException;
import it.csi.deleghe.deleghebe.util.LogUtil;

@Stateless
public class DeleDDocumentoTipoRepository {

    @Inject
    private LogUtil log;

    @Inject
    private EntityManager em;
    
    public enum DocumentoTipoCodEnum {
    	TIPO_1,
    	TIPO_2
    }

    public DeleDDocumentoTipo ricercaServiziByDocumentoTipoCod(String docTipoCod)  {
    	DocumentoTipoCodEnum e;
    	try {
    		e = DocumentoTipoCodEnum.valueOf(docTipoCod);
    	} catch (IllegalArgumentException iae) {
    		throw new BeServiceException("DA.R03", "Codice tipo documento ["+ docTipoCod + "] non previsto");
    	}
    	
    	return ricercaServiziByDocumentoTipoCod(e);
    }
    
    public DeleDDocumentoTipo ricercaServiziByDocumentoTipoCod(DocumentoTipoCodEnum docTipoCod)  {
    	final String METHOD_NAME = "ricercaServiziByDocumentoTipoCod";
		log.debug(METHOD_NAME, "get service for: documentoTipoCod=%s", docTipoCod);

    	StringBuilder sb = new StringBuilder();
    	sb.append("SELECT d FROM DeleDDocumentoTipo d ");
    	sb.append("WHERE d.dataCancellazione IS NULL ");
    			
    	Map<String, Object> params = new HashMap<>();
    	populateStatoFilter(sb, params, docTipoCod.toString());
    	
    	String jpql = sb.toString();
    	log.debug(METHOD_NAME, "jpql: %s", jpql);
    	TypedQuery<DeleDDocumentoTipo> query = em.createQuery(jpql, DeleDDocumentoTipo.class);
    	params.entrySet().stream().forEach(e -> {
    		String key = e.getKey();
    		Object value = e.getValue();
    		query.setParameter(key, value);
    		log.debug(METHOD_NAME, "Param: key: %s, value: %s", key, value);
    	});
    	DeleDDocumentoTipo result;
    	try {
    		result = query.getSingleResult();
    	} catch(NonUniqueResultException nure) {
    		String msg = "Trovato piu' di un risultato per il codice "+ docTipoCod;
    		log.error(METHOD_NAME, msg, nure);
    		throw new BeServiceException("DA.R01", msg);
    	} catch(NoResultException nre) {
    		String msg = "Nessun risultato trovato per il codice "+ docTipoCod;
    		log.error(METHOD_NAME, msg, nre);
    		throw new BeServiceException("DA.R02", msg);
    	}
    	log.debug(METHOD_NAME, "result docTipoId: %s", result.getDocTipoId());
    	return result;
    	
    }
    
    public List<DeleDDocumentoTipo> ricercaDescrizioneDocumenti() {
        final String METHOD_NAME = "ricercaDescrizioneDocumenti";
        
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT d FROM DeleDDocumentoTipo d ");
        sb.append("WHERE d.dataCancellazione IS NULL ");

        Map<String, Object> params = new HashMap<>();
        
        String jpql = sb.toString();
        log.debug(METHOD_NAME, "jpql: %s", jpql);
        TypedQuery<DeleDDocumentoTipo> query = em.createQuery(jpql, DeleDDocumentoTipo.class);
        
        List<DeleDDocumentoTipo> result = query.getResultList();
        
        
        return result;

     }
	
	private void populateStatoFilter(StringBuilder sb, Map<String, Object> params, String documentoTipoCod) {
		if(StringUtils.isNotBlank(documentoTipoCod)) {
			sb.append("AND d.docTipoCod = :documentoTipoCod ");
			params.put("documentoTipoCod", documentoTipoCod);
		}
	}
}
