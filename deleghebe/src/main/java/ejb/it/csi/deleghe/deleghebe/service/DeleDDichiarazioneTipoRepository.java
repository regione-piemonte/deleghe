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

import it.csi.deleghe.deleghebe.model.DeleDDichiarazioneTipo;
import it.csi.deleghe.deleghebe.service.exception.BeServiceException;
import it.csi.deleghe.deleghebe.util.LogUtil;

@Stateless
public class DeleDDichiarazioneTipoRepository {

   @Inject
   private LogUtil log;

   @Inject
   private EntityManager em;

   public enum DicTipoCodEnum {
      CONGIUNTA,
      GENITOREMONO,
      TUTELA,
      AMMSOSTEGNO,
      TUTELA_MAG18
   }

   public DeleDDichiarazioneTipo ricercaServiziByDicTipoCod(String dicTipoCod) {
      DicTipoCodEnum e;
      try {
         e = DicTipoCodEnum.valueOf(dicTipoCod);
      } catch (IllegalArgumentException iae) {
         throw new BeServiceException("DA.R03", "Codice tipo dichiarazione [" + dicTipoCod + "] non previsto");
      }

      return ricercaServiziByDicTipoCod(e);
   }

   public DeleDDichiarazioneTipo ricercaServiziByDicTipoCod(DicTipoCodEnum dicTipoCod) {
      final String METHOD_NAME = "ricercaServiziByDicTipoCod";
      log.debug(METHOD_NAME, "get service for: dicTipoCod=%s", dicTipoCod);

      StringBuilder sb = new StringBuilder();
      sb.append("SELECT d FROM DeleDDichiarazioneTipo d ");
      sb.append("WHERE d.dataCancellazione IS NULL ");

      Map<String, Object> params = new HashMap<>();
      populateStatoFilter(sb, params, dicTipoCod.toString());

      String jpql = sb.toString();
      log.debug(METHOD_NAME, "jpql: %s", jpql);
      TypedQuery<DeleDDichiarazioneTipo> query = em.createQuery(jpql, DeleDDichiarazioneTipo.class);
      params.entrySet().stream().forEach(e -> {
         String key = e.getKey();
         Object value = e.getValue();
         query.setParameter(key, value);
         log.debug(METHOD_NAME, "Param: key: %s, value: %s", key, value);
      });
      DeleDDichiarazioneTipo result;
      try {
         result = query.getSingleResult();
      } catch (NonUniqueResultException nure) {
         String msg = "Trovato piu' di un risultato per il codice " + dicTipoCod;
         log.error(METHOD_NAME, msg, nure);
         throw new BeServiceException("DA.R01", msg);
      } catch (NoResultException nre) {
         String msg = "Nessun risultato trovato per il codice " + dicTipoCod;
         log.error(METHOD_NAME, msg, nre);
         throw new BeServiceException("DA.R02", msg);
      }
      log.debug(METHOD_NAME, "result tipoId: %s", result.getDicTipoId());
      return result;

   }
   
   
   public List<DeleDDichiarazioneTipo> ricercaTipiDichiarazione() {
	      final String METHOD_NAME = "ricercaTipiDichiarazione";
	      
	      StringBuilder sb = new StringBuilder();
	      sb.append("SELECT d FROM DeleDDichiarazioneTipo d ");
	      sb.append("WHERE d.dataCancellazione IS NULL ");

	      String jpql = sb.toString();
	      log.debug(METHOD_NAME, "jpql: %s", jpql);
	      TypedQuery<DeleDDichiarazioneTipo> query = em.createQuery(jpql, DeleDDichiarazioneTipo.class);
	      
	      List<DeleDDichiarazioneTipo> result = query.getResultList();
	      
	      log.debug(METHOD_NAME, "result size: %s", result.size());
	      return result;

   }

   private void populateStatoFilter(StringBuilder sb, Map<String, Object> params, String dicTipoCod) {
      if (StringUtils.isNotBlank(dicTipoCod)) {
         sb.append("AND d.dicTipoCod = :dicTipoCod ");
         params.put("dicTipoCod", dicTipoCod);
      }
   }
}
