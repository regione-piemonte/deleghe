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

import it.csi.deleghe.deleghebe.model.DeleDGrado;
import it.csi.deleghe.deleghebe.util.LogUtil;

@Stateless
public class DeleDGradoRepository extends BaseRepository {

	
	 @Inject
	    private LogUtil log;

	    @Inject
	    private EntityManager em;
	    
	    
	    public DeleDGrado ricercaGradoServizioByID(Integer delGradoId)  {
	    	final String METHOD_NAME = "ricercaGradoServizioByID";
	    	
	    	if(delGradoId == null) {
	    		String msg = "delGradoId non valorizzato";
				log.error(METHOD_NAME, msg);
	    		throw new NoResultException(msg);
	    	}
	    	
	    	Map<String, Object> params = new HashMap<>();
	    	StringBuilder sb = new StringBuilder();
	    	
	    	sb.append("SELECT tab FROM DeleDGrado tab ");
	    	sb.append("WHERE tab.dataCancellazione IS NULL ");
	    	sb.append("AND tab.delGradoId = :delGradoId ");
	    	params.put("delGradoId", delGradoId);
	    	
	    	String jpql = sb.toString();
	    	TypedQuery<DeleDGrado> query = em.createQuery(jpql, DeleDGrado.class);
	    	params.entrySet().stream().forEach(e -> {
	    		String key = e.getKey();
	    		Object value = e.getValue();
	    		query.setParameter(key, value);
	    		log.debug(METHOD_NAME, "Param: key: %s, value: %s", key, value);
	    	});

	    	DeleDGrado result;
	    	try {
				result = query.getSingleResult();
			} catch (NonUniqueResultException nure) {
				String msg = "Trovato piu' di un risultato per il delGradoId :" + delGradoId;
				log.error(METHOD_NAME, msg, nure, new Object[] {});
				throw new IllegalArgumentException(msg);
			} catch (NoResultException nre) {
				String msg = "Nessun risultato trovato per il delGradoId :" + delGradoId;
				log.debug(METHOD_NAME, msg, nre, new Object[] {});
				return null;
			}
	    	
	    	return result;
	    }
	    

	    public DeleDGrado ricercaGradoServizioByCodice(String delGradoCod)  {
	    	final String METHOD_NAME = "ricercaGradoServizioByCodice";
	    	
	    	if(delGradoCod == null) {
	    		String msg = "delGradoCod non valorizzato";
				log.error(METHOD_NAME, msg);
	    		throw new NoResultException(msg);
	    	}
	    	
	    	Map<String, Object> params = new HashMap<>();
	    	StringBuilder sb = new StringBuilder();
	    	
	    	sb.append("SELECT tab FROM DeleDGrado tab ");
	    	sb.append("WHERE tab.dataCancellazione IS NULL ");
	    	sb.append("AND tab.delGradoCod = :delGradoCod ");
	    	params.put("delGradoCod", delGradoCod);
	    	
	    	String jpql = sb.toString();
	    	TypedQuery<DeleDGrado> query = em.createQuery(jpql, DeleDGrado.class);
	    	params.entrySet().stream().forEach(e -> {
	    		String key = e.getKey();
	    		Object value = e.getValue();
	    		query.setParameter(key, value);
	    		log.debug(METHOD_NAME, "Param: key: %s, value: %s", key, value);
	    	});

	    	DeleDGrado result;
	    	try {
				result = query.getSingleResult();
			} catch (NonUniqueResultException nure) {
				String msg = "Trovato piu' di un risultato per il delGradoCod :" + delGradoCod;
				log.error(METHOD_NAME, msg, nure, new Object[] {});
				throw new IllegalArgumentException(msg);
			} catch (NoResultException nre) {
				String msg = "Nessun risultato trovato per il delGradoCod :" + delGradoCod;
				log.debug(METHOD_NAME, msg, nre, new Object[] {});
				return null;
			}
	    	
	    	return result;
	    }

	    
	    
	    public List<DeleDGrado> ricercaListaGradiServizi(DeleDGrado deleDGrado)  {
	    	final String METHOD_NAME = "ricercaListaGradiServizi";
	    	
	    	
	    	Map<String, Object> params = new HashMap<>();
	    	StringBuilder sb = new StringBuilder();
	    	
	    	sb.append("SELECT tab FROM DeleDGrado tab ");
	    	sb.append("WHERE tab.dataCancellazione IS NULL ");
	    	
	    	populateFilter(sb, params, deleDGrado);
	    	
	    	String jpql = sb.toString();
	    	TypedQuery<DeleDGrado> query = em.createQuery(jpql, DeleDGrado.class);
	    	params.entrySet().stream().forEach(e -> {
	    		String key = e.getKey();
	    		Object value = e.getValue();
	    		query.setParameter(key, value);
	    		log.debug(METHOD_NAME, "Param: key: %s, value: %s", key, value);
	    	});

	    	List<DeleDGrado> result = query.getResultList();
	    	
	    	return result;
	    }
	    

	    private void populateFilter(StringBuilder sb, Map<String, Object> params, DeleDGrado deleDGrado) {
			
	    	if(deleDGrado!=null) {
				
				if(deleDGrado.getDelGradoId()!=null) {
					sb.append("AND tab.delGradoId = :delGradoId ");
			    	params.put("delGradoId", deleDGrado.getDelGradoId());
				}
				if(deleDGrado.getDelGradoCod()!=null) {
					sb.append("AND tab.delGradoCod = :delGradoCod "); 
			    	params.put("delGradoCod", deleDGrado.getDelGradoCod());
				}
				if(deleDGrado.getDelGradoDesc()!=null) {
					sb.append("AND tab.delGradoDesc = delGradoDesc ");
			    	params.put("delGradoDesc", deleDGrado.getDelGradoDesc());
				}
				if(deleDGrado.getDataCreazione()!=null) {
						Calendar cal = Calendar.getInstance();
						cal.setTime(deleDGrado.getDataCreazione());
						cal.set(Calendar.HOUR_OF_DAY, 0);
				        cal.set(Calendar.MINUTE, 0);
				        cal.set(Calendar.SECOND, 0);
				        cal.set(Calendar.MILLISECOND, 0);
					
						sb.append("AND tab.dataCreazione = :dob ");
				    	params.put("dob",  cal.getTime());
				}
			}	    				
		}
	    
	    
	    public DeleDGrado inserisciGradoServizi(DeleDGrado deleDGrado, String loginOperazione){
	    	final String METHOD_NAME = "inserisciGradoServizi";

	    	deleDGrado.setDelGradoId(null);
	    	setCreazione(deleDGrado, loginOperazione);
	    	
	    	em.persist(deleDGrado);
	    	return deleDGrado;
	    }
	    
	    
	    public DeleDGrado aggiornaGradoServizi(DeleDGrado deleDGrado, String loginOperazione){
	    	final String METHOD_NAME = "aggiornaGradoServizi";
	    	
	    	DeleDGrado gradoBydb=em.find(DeleDGrado.class,deleDGrado.getDelGradoId());
	    	
	    	if(gradoBydb == null) {
	    		String msg = "gradoBydb non valorizzato";
				log.error(METHOD_NAME, msg);
	    		throw new NoResultException(msg);
	    	}

	    	gradoBydb.setDelGradoId(deleDGrado.getDelGradoId());
	    	setModifica(gradoBydb, loginOperazione);
	    	
	    	em.merge(gradoBydb);
	    	
	    	return gradoBydb;
	    }
}
