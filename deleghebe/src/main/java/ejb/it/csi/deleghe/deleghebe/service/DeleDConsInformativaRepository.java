/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import it.csi.deleghe.deleghebe.model.DeleDConsInformativa;
import it.csi.deleghe.deleghebe.util.LogUtil;

public class DeleDConsInformativaRepository {
	
	 @Inject
	   private LogUtil log;

	   @Inject
	   private EntityManager em;	   
	   
	   public List<DeleDConsInformativa> ricercaInformativaConsensi() {
	      final String METHOD_NAME = "ricercaInformativaConsensi";
	      
	      StringBuilder sb = new StringBuilder();
	      sb.append("SELECT d FROM DeleDConsInformativa d ");
	      sb.append("WHERE d.dataCancellazione IS NULL ");

	      Map<String, Object> params = new HashMap<>();
	      
	      String jpql = sb.toString();
	      log.debug(METHOD_NAME, "jpql: %s", jpql);
	      TypedQuery<DeleDConsInformativa> query = em.createQuery(jpql, DeleDConsInformativa.class);
	      
	      List<DeleDConsInformativa> result = query.getResultList();	      
	      
	      return result;

	   }
}
