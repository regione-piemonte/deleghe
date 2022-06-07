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
import it.csi.deleghe.deleghebe.service.exception.BeServiceException;
import it.csi.deleghe.deleghebe.util.LogUtil;

@Stateless
public class DeleDDichiarazioneDetStatoRepository {

   @Inject
   private LogUtil log;

   @Inject
   private EntityManager em;

   public enum DicDetStatoCodEnum {
      VALIDA,
      ATTIVA,
      DA_APPROVARE,
      IN_ATTESA_DI_CONFERMA,
      IN_ATTESA_DI_APPROVAZIONE,
      RICHIESTA_RETTIFICATA,
      IN_LAVORAZIONE,
      REVOCATA_BLOCCO,
      REVOCATA,
      SCADUTA,
      RIFIUTA
   }

   public DeleDDichiarazioneDetStato ricercaDetStatoByDicDetStatoCod(String dicDetStatoCod) {
      DicDetStatoCodEnum e;
      try {
         e = DicDetStatoCodEnum.valueOf(dicDetStatoCod);
      } catch (IllegalArgumentException iae) {
         throw new BeServiceException("DA.R03", "Codice stato dettaglio dichiarazione [" + dicDetStatoCod + "] non previsto");
      }

      return ricercaDetStatoByDicDetStatoCod(e);
   }

   public DeleDDichiarazioneDetStato ricercaDetStatoByDicDetStatoCod(DicDetStatoCodEnum dicDetStatoCod) {
      final String METHOD_NAME = "ricercaServiziByDicDetStatoCod";
      log.debug(METHOD_NAME, "get service for: dicDetStatoCod=%s", dicDetStatoCod);

      StringBuilder sb = new StringBuilder();
      sb.append("SELECT d FROM DeleDDichiarazioneDetStato d ");
      sb.append("WHERE d.dataCancellazione IS NULL ");

      Map<String, Object> params = new HashMap<>();
      populateStatoFilter(sb, params, dicDetStatoCod.toString());

      String jpql = sb.toString();
      log.debug(METHOD_NAME, "jpql: %s", jpql);
      TypedQuery<DeleDDichiarazioneDetStato> query = em.createQuery(jpql, DeleDDichiarazioneDetStato.class);
      params.entrySet().stream().forEach(e -> {
         String key = e.getKey();
         Object value = e.getValue();
         query.setParameter(key, value);
         log.debug(METHOD_NAME, "Param: key: %s, value: %s", key, value);
      });
      DeleDDichiarazioneDetStato result;
      try {
         result = query.getSingleResult();
      } catch (NonUniqueResultException nure) {
         String msg = "Trovato piu' di un risultato per il codice " + dicDetStatoCod;
         log.error(METHOD_NAME, msg, nure);
         throw new BeServiceException("DA.R01", msg);
      } catch (NoResultException nre) {
         String msg = "Nessun risultato trovato per il codice " + dicDetStatoCod;
         log.error(METHOD_NAME, msg, nre);
         throw new BeServiceException("DA.R02", msg);
      }
      log.debug(METHOD_NAME, "result detStatoId: %s", result.getDicdetStatoCod());
      return result;

   }
   
   
   public List<DeleDDichiarazioneDetStato> ricercaStatiDichiarazioneDett() {
      final String METHOD_NAME = "ricercaStatiDichiarazioneDett";
      
      StringBuilder sb = new StringBuilder();
      sb.append("SELECT d FROM DeleDDichiarazioneDetStato d ");
      sb.append("WHERE d.dataCancellazione IS NULL ");

      Map<String, Object> params = new HashMap<>();
      
      String jpql = sb.toString();
      log.debug(METHOD_NAME, "jpql: %s", jpql);
      TypedQuery<DeleDDichiarazioneDetStato> query = em.createQuery(jpql, DeleDDichiarazioneDetStato.class);
      
      List<DeleDDichiarazioneDetStato> result = query.getResultList();
      
      
      return result;

   }

   private void populateStatoFilter(StringBuilder sb, Map<String, Object> params, String dicDetStatoCod) {
      if (StringUtils.isNotBlank(dicDetStatoCod)) {
         sb.append("AND d.dicdetStatoCod = :dicDetStatoCod ");
         params.put("dicDetStatoCod", dicDetStatoCod);
      }
   }
}
