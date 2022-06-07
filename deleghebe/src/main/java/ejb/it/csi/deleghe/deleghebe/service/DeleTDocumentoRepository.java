/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;

import it.csi.deleghe.deleghebe.model.DeleTDocumento;
import it.csi.deleghe.deleghebe.util.LogUtil;

@Stateless
public class DeleTDocumentoRepository extends BaseRepository {
	 @Inject
	    private LogUtil log;

	    @Inject
	    private EntityManager em;
	    
	    
	    public DeleTDocumento ricercaDocumento(Integer docId)  {
	    	final String METHOD_NAME = "ricercaFileDocumento";
	    	
	    	if(docId == null) {
	    		String msg = "docId non valorizzato";
				log.error(METHOD_NAME, msg);
	    		throw new NoResultException(msg);
	    	}
	    	
	    	Map<String, Object> params = new HashMap<>();
	    	StringBuilder sb = new StringBuilder();
	    	
	    	sb.append("SELECT tab FROM DeleTDocumento tab ");
	    	sb.append("WHERE tab.dataCancellazione IS NULL ");
	    	sb.append("AND tab.docId = :docId ");
	    	params.put("docId", docId);
	    	
	    	String jpql = sb.toString();
	    	TypedQuery<DeleTDocumento> query = em.createQuery(jpql, DeleTDocumento.class);
	    	params.entrySet().stream().forEach(e -> {
	    		String key = e.getKey();
	    		Object value = e.getValue();
	    		query.setParameter(key, value);
	    		log.debug(METHOD_NAME, "Param: key: %s, value: %s", key, value);
	    	});

	    	DeleTDocumento result;
	    	try {
				result = query.getSingleResult();
			} catch (NonUniqueResultException nure) {
				String msg = "Trovato piu' di un risultato per il docId :" + docId;
				log.error(METHOD_NAME, msg, nure, new Object[] {});
				throw new IllegalArgumentException(msg);
			} catch (NoResultException nre) {
				String msg = "Nessun risultato trovato per il docId :" + docId;
				log.debug(METHOD_NAME, msg, nre, new Object[] {});
				return null;
			}
	    	
	    	return result;
	    }
	    
	    
	    public List<DeleTDocumento> ricercaListaDocumento(DeleTDocumento deleTDocumento)  {
	    	final String METHOD_NAME = "ricercaListaDocumento";
	    	
	    	
	    	Map<String, Object> params = new HashMap<>();
	    	StringBuilder sb = new StringBuilder();
	    	
	    	sb.append("SELECT tab FROM DeleTDocumento tab ");
	    	sb.append("WHERE tab.dataCancellazione IS NULL ");
	    	
	    	populateFilter(sb, params, deleTDocumento);
	    	
	    	String jpql = sb.toString();
	    	TypedQuery<DeleTDocumento> query = em.createQuery(jpql, DeleTDocumento.class);
	    	params.entrySet().stream().forEach(e -> {
	    		String key = e.getKey();
	    		Object value = e.getValue();
	    		query.setParameter(key, value);
	    		log.debug(METHOD_NAME, "Param: key: %s, value: %s", key, value);
	    	});

	    	List<DeleTDocumento> result = query.getResultList();
	    	
	    	return result;
	    }
	    

	    private void populateFilter(StringBuilder sb, Map<String, Object> params, DeleTDocumento deleTDocumento) {
			
	    	if(deleTDocumento!=null) {
				
				if(deleTDocumento.getDocId()!=null) {
					sb.append("AND tab.docId = :docId ");
			    	params.put("docId", deleTDocumento.getDocId());
				}
				if(deleTDocumento.getDocDesc()!=null) {
					sb.append("AND tab.docDesc = :docDesc "); 
			    	params.put("docDesc", deleTDocumento.getDocDesc());
				}
				if(deleTDocumento.getAutorita()!=null) {
					sb.append("AND tab.autorita = autorita ");
			    	params.put("autorita", deleTDocumento.getAutorita());
				}
				if(deleTDocumento.getDataCreazione()!=null) {
						Calendar cal = Calendar.getInstance();
						cal.setTime(deleTDocumento.getDataCreazione());
						cal.set(Calendar.HOUR_OF_DAY, 0);
				        cal.set(Calendar.MINUTE, 0);
				        cal.set(Calendar.SECOND, 0);
				        cal.set(Calendar.MILLISECOND, 0);
					
						sb.append("AND tab.dataCreazione = :dob ");
				    	params.put("dob",  cal.getTime());
				}
				if(deleTDocumento.getNumeroDocumento()!=null) {
					sb.append("AND tab.numeroDocumento = numeroDocumento ");
			    	params.put("numeroDocumento", deleTDocumento.getNumeroDocumento());
				}
			}	    				
		}
	    
	    
	    public DeleTDocumento inserisciDocumento(DeleTDocumento deleTDocumento, String loginOperazione){
	    	final String METHOD_NAME = "inserisciDocumento";

	    	deleTDocumento.setDocId(null);
	    	setCreazione(deleTDocumento, loginOperazione);
	    	
	    	em.persist(deleTDocumento);
	    	return deleTDocumento;
	    }
	    
	    
	    public DeleTDocumento aggiornaDocumento(DeleTDocumento deleTDocumento, String loginOperazione){
	    	final String METHOD_NAME = "aggiornaDocumento";
	    	
	    	DeleTDocumento docBydb=em.find(DeleTDocumento.class,deleTDocumento.getDocId());
	    	
	    	if(docBydb == null) {
	    		String msg = "DOC non valorizzato";
				log.error(METHOD_NAME, msg);
	    		throw new NoResultException(msg);
	    	}

	    	docBydb.setDocId(deleTDocumento.getDocId());
	    	setModifica(docBydb, loginOperazione);
	    	
	    	em.merge(docBydb);
	    	
	    	return docBydb;
	    }

}
