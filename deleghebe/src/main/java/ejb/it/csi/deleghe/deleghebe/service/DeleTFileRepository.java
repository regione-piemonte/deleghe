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

import it.csi.deleghe.deleghebe.model.DeleTFile;
import it.csi.deleghe.deleghebe.util.LogUtil;
import it.csi.deleghe.deleghebe.util.LogUtil.ToLog;

@Stateless
public class DeleTFileRepository extends BaseRepository {
	
	 @Inject
	    private LogUtil log;

	    @Inject
	    private EntityManager em;
	    
	    
	    public DeleTFile ricercaFileDocumento(Integer fileId)  {
	    	final String METHOD_NAME = "ricercaFileDocumento";
	    	
	    	if(fileId == null) {
	    		String msg = "File id non valorizzato";
				log.error(METHOD_NAME, msg);
	    		throw new NoResultException(msg);
	    	}
	    	
	    	Map<String, Object> params = new HashMap<>();
	    	StringBuilder sb = new StringBuilder();
	    	
	    	sb.append("SELECT tab FROM DeleTFile tab ");
	    	sb.append("WHERE tab.dataCancellazione IS NULL ");
	    	sb.append("AND tab.fileId = :fileId ");
	    	params.put("fileId", fileId);
	    	
	    	String jpql = sb.toString();
	    	TypedQuery<DeleTFile> query = em.createQuery(jpql, DeleTFile.class);
	    	params.entrySet().stream().forEach(e -> {
	    		String key = e.getKey();
	    		Object value = e.getValue();
	    		query.setParameter(key, value);
	    		log.debug(METHOD_NAME, "Param: key: %s, value: %s", key, value);
	    	});

	    	DeleTFile result;
	    	try {
				result = query.getSingleResult();
			} catch (NonUniqueResultException nure) {
				String msg = "Trovato piu' di un risultato per il File id :" + fileId;
				log.error(METHOD_NAME, msg, nure, new Object[] {});
				throw new IllegalArgumentException(msg);
			} catch (NoResultException nre) {
				String msg = "Nessun risultato trovato per il File id :" + fileId;
				log.debug(METHOD_NAME, msg, nre, new Object[] {});
				return null;
			}
	    	
	    	return result;
	    }
	    
	    
	    public List<DeleTFile> ricercaListaFileDocumento(DeleTFile deleTFile)  {
	    	final String METHOD_NAME = "ricercaListaFileDocumento";
	    	
	    	
	    	Map<String, Object> params = new HashMap<>();
	    	StringBuilder sb = new StringBuilder();
	    	
	    	sb.append("SELECT tab FROM DeleTFile tab ");
	    	sb.append("WHERE tab.dataCancellazione IS NULL ");
	    	
	    	populateFilter(sb, params, deleTFile);
	    	
	    	String jpql = sb.toString();
	    	TypedQuery<DeleTFile> query = em.createQuery(jpql, DeleTFile.class);
	    	params.entrySet().stream().forEach(e -> {
	    		String key = e.getKey();
	    		Object value = e.getValue();
	    		query.setParameter(key, value);
	    		log.debug(METHOD_NAME, "Param: key: %s, value: %s", key, value);
	    	});

	    	List<DeleTFile> result = query.getResultList();
	    	
	    	return result;
	    }
	    

	    private void populateFilter(StringBuilder sb, Map<String, Object> params, DeleTFile deleTFile) {
			
	    	if(deleTFile!=null) {
				
				if(deleTFile.getFileId()!=null) {
					sb.append("AND tab.fileId = :fileId ");
			    	params.put("fileId", deleTFile.getFileId());
				}
				if(deleTFile.getFileName()!=null) {
					sb.append("AND tab.fileName = :fileName "); 
			    	params.put("fileName", deleTFile.getFileName());
				}
				if(deleTFile.getFileSize()!=null) {
					sb.append("AND tab.fileSize = fileSize ");
			    	params.put("fileSize", deleTFile.getFileSize());
				}
				if(deleTFile.getDataCreazione()!=null) {
						Calendar cal = Calendar.getInstance();
						cal.setTime(deleTFile.getDataCreazione());
						cal.set(Calendar.HOUR_OF_DAY, 0);
				        cal.set(Calendar.MINUTE, 0);
				        cal.set(Calendar.SECOND, 0);
				        cal.set(Calendar.MILLISECOND, 0);
					
						sb.append("AND tab.dataCreazione = :dob ");
				    	params.put("dob",  cal.getTime());
				}
				if(deleTFile.getFile()!=null) {
					sb.append("AND tab.file = file ");
			    	params.put("file", deleTFile.getFile());
				}
			}	    				
		}
	    
	    
	    public DeleTFile inserisciFileDocumento(DeleTFile deleTFile, String loginOperazione){
	    	final String METHOD_NAME = "inserisciFileDocumento";

	    	deleTFile.setFileId(null);
	    	setCreazione(deleTFile, loginOperazione);
	    	
	    	em.persist(deleTFile);
	    	return deleTFile;
	    }
	    
	    
	    public DeleTFile aggiornaFileDocumento(DeleTFile deleTFile, String loginOperazione){
	    	final String METHOD_NAME = "aggiornaFileDocumento";
	    	
	    	DeleTFile fileBydb=em.find(DeleTFile.class,deleTFile.getFileId());
	    	
	    	if(fileBydb == null) {
	    		String msg = "FILE non valorizzato";
				log.error(METHOD_NAME, msg);
	    		throw new NoResultException(msg);
	    	}

	    	fileBydb.setFileId(deleTFile.getFileId());
	    	setModifica(fileBydb, loginOperazione);
	    	
	    	em.merge(fileBydb);
	    	
	    	return fileBydb;
	    }
	   

}
