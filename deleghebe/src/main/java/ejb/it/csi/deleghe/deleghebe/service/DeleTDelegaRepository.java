/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service;

import java.util.ArrayList;


import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;
import javax.persistence.TypedQuery;

import it.csi.deleghe.deleghebe.model.*;
import it.csi.deleghe.deleghebe.service.DeleDDelegaServizioStatoRepository.DelstatoCodEnum;
import it.csi.deleghe.deleghebe.service.exception.BeServiceException;
import it.csi.deleghe.deleghebe.util.DateUtil;
import it.csi.deleghe.deleghebe.util.LogUtil;
import it.csi.deleghe.deleghebe.util.LogUtil.ToLog;
import it.csi.deleghe.deleghebe.util.ModelComparator;
import it.csi.deleghe.deleghebe.ws.model.Cittadino;
import it.csi.deleghe.deleghebe.ws.model.DatiDelegaServizioDB;
import it.csi.deleghe.deleghebe.ws.model.DatiDelegatoDB;
import it.csi.deleghe.deleghebe.ws.model.Delega;
import it.csi.deleghe.deleghebe.ws.model.DelegaCittadino;
import it.csi.deleghe.deleghebe.ws.model.StatoDelega;
import it.csi.deleghe.deleghebe.ws.model.TipoDelega;

@Stateless
public class DeleTDelegaRepository extends BaseRepository {

	@Inject
	private LogUtil log;

	@Inject
	private EntityManager em;

	@Inject
	private DeleDDelegaServizioStatoRepository delegaServizioStato;
	@Inject
	private DeleDServizioRepository servizioRepository;
	@Inject
	private DeleTDelegaServizioRepository deleTDelegaServizioRepository;
	@Inject
	private DeleTServizioParametroRepository servizioParametroRepository;
	@Inject
	private DeleTDichiarazioneDetRepository deleTDichiarazioneDetRepository;
	@Inject
	private DeleRServizioDelegaTipoGradoRepository deleRServizioDelegaTipoGradoRepository;

	public DeleTDelega inserisciDelega(DeleTDelega delega, String loginOperazione) {
		final String METHOD_NAME = "inserisciDelega";
		log.debug(METHOD_NAME, "inserisci delega for: citCf delegante=%s", (ToLog) () -> {
			return delega != null ? delega.getDeleTCittadino1().getCitCf() : "null";
		});
		delega.setDlgaId(null);
		delega.setUuid(UUID.randomUUID());
		setCreazione(delega, loginOperazione);
		

		
		if (delega.getDeleTCittadino1() != null) {
			setCreazione(delega.getDeleTCittadino1(), loginOperazione);
		}

		if (delega.getDeleTCittadino2() != null) {
			setCreazione(delega.getDeleTCittadino2(), loginOperazione);
		}


		DeleTDichiarazioneDet deleTDichiarazioneDet = deleTDichiarazioneDetRepository.ricercaDichiarazioneDetByCF(delega.getDeleTCittadino2().getCitCf(), delega.getDeleTCittadino1().getCitCf());
		if (deleTDichiarazioneDet != null) {
			delega.setDeleTDichiarazioneDet(deleTDichiarazioneDet);
		}

		if (delega.getDeleTDelegatos() != null && !delega.getDeleTDelegatos().isEmpty()) {
			DeleTDelegato deleTDelegato = delega.getDeleTDelegatos().get(0);
			deleTDelegato.setDlgoId(null);
			setCreazione(deleTDelegato, loginOperazione);
		}

		if (delega.getDeleTDelegaServizios() != null) {

			for (DeleTDelegaServizio ds : delega.getDeleTDelegaServizios()) {
				String validitaServizio = servizioParametroRepository.getValiditaServizio(ds.getDeleDServizio().getSerId());

				ds.setDeleDDelegaServizioStato(delegaServizioStato.ricercaServiziStatoByDelstatoCod(DeleDDelegaServizioStatoRepository.DelstatoCodEnum.ATTIVA));
				ds.setDelId(null);
				ds.setUuid(UUID.randomUUID());
				setCreazione(ds, loginOperazione);


				ds.setDelDatadecorrenza(new Date());

				if (ds.getDelDatascadenza() == null) {
	

					ds.setDelDatascadenza(DateUtil.advanceDate(ds.getDelDatadecorrenza(), validitaServizio));
				} else if (delega.getDeleTDichiarazioneDet() == null) {

					if (ds.getDelDatascadenza().after(ds.getDelDatadecorrenza())) {
						Date dataValidita = DateUtil.advanceDate(ds.getDelDatadecorrenza(), validitaServizio);
						if (ds.getDelDatascadenza().after(dataValidita)) {
							String msg = "La data di scadenza inserita e' successiva al periodo limite normativo di validita' della delega: " + DateUtil.toStringSimpleDate(dataValidita);
							log.error(METHOD_NAME, msg);
				    		throw new BeServiceException("DA.DLG01", msg);
						}
					} else {
						String msg = "La data di scadenza della delega deve essere successiva alla data di decorrenza";
						log.error(METHOD_NAME, msg);
			    		throw new BeServiceException("DA.DLG02", msg);
					}
				}
				

				if (ds.getDeleDGrado()!=null) {

				}else {

					if(delega.getDeleDDelegaTipo() != null) {
						if(ds.getDeleDServizio() != null) {
							DeleRServizioDelegaTipoGrado deleRServizioDelegaTipoGrado = deleRServizioDelegaTipoGradoRepository.ricercaGradoAssegnatoAlServizio(delega.getDeleDDelegaTipo().getDelTipId(), ds.getDeleDServizio().getSerId());
							if(deleRServizioDelegaTipoGrado!=null) {
								if(deleRServizioDelegaTipoGrado.getDeleDGrado()!=null) {
									Integer delGradoId = deleRServizioDelegaTipoGrado.getDeleDGrado().getDelGradoId();
									ds.setDeleDGrado(deleRServizioDelegaTipoGrado.getDeleDGrado());						
								}					
							}						
						}					
					}					
				}				
			}
		}

		
		em.persist(delega);
		return delega;
	}
	
	public String  ricercaDichTipoCodice (Integer nrpraticaDelega) {
		String dicTipoCod=null;
		final String METHOD_NAME = "ricercaChiaveDelega";


		StringBuilder sb = new StringBuilder();	
		
		sb.append("SELECT dicTipo.dic_tipo_cod ");
		sb.append("FROM dele_t_delega delega, "); 
		sb.append("dele_t_dichiarazione_det dicdet, "); 
		sb.append("dele_t_dichiarazione dichiaraz, "); 
		sb.append("dele_d_dichiarazione_tipo dicTipo "); 
		sb.append("WHERE delega.dlga_id="+nrpraticaDelega.intValue());		
		sb.append("and dicdet.dicdet_id = delega.dicdet_id "); 
		sb.append("and dichiaraz.dic_id = dicdet.dic_id ");
		sb.append("and dicTipo.dic_tipo_id = dichiaraz.dic_tipo_id ");
		
		String jpql = sb.toString();
    	Query query = em.createNativeQuery(jpql);
    	
		try {
			dicTipoCod = ((String)query.getSingleResult()).toString();
		}catch(NoResultException e){

			dicTipoCod=null;
		}
		

		return dicTipoCod;
	}
	
	public Integer  ricercaDelegaTipoCodice (String tipoCodice) {
		Integer delTipid=null;
		final String METHOD_NAME = "ricercaDelegaTipoCodice";
		
		Integer delTipIdInteger;
		int delTipIdPreavvisoInt=0;		

		StringBuilder sb = new StringBuilder();	
		
		sb.append("SELECT del_tip_id ");
		sb.append("FROM dele_d_delega_tipo "); 
		sb.append("WHERE del_tip_cod='"+tipoCodice+"'");		
		
		String jpql = sb.toString();
    	Query query = em.createNativeQuery(jpql);
    	
		try {
			delTipid = (Integer) query.getSingleResult();

		}catch(NoResultException e){

			delTipid=null;
		}


		return delTipid;
	}
	

	public boolean forzareDelTipId(Integer delTipid, Integer nrPratica) {

		final String METHOD_NAME = "aggiornaStatoDelegaServizioInScadenza";
		
		boolean result = false;	
		int resultQuery=0;
		
		StringBuilder sb = new StringBuilder();	
		
		sb.append("UPDATE ");
		sb.append("dele_t_delega  ");
		sb.append("SET ");	
		sb.append("del_tip_id = "+delTipid.intValue());			
		sb.append("WHERE ");	
		sb.append("dlga_id = "+nrPratica.intValue());	
		
		String jpql = sb.toString();

    	try {
    		Query query = em.createNativeQuery(jpql);
        	resultQuery = query.executeUpdate();
        	
		} catch (TransactionRequiredException tre) {
			result = false;
    	}    	
    	log.info(METHOD_NAME, "Aggiornamento eseguito per nrPratica: "+nrPratica.intValue());
    	
    	if(resultQuery==1){
    		result = true;
    	}
    	
		return result;
	}
	


	/**
	 * La modifica attualmente, come espresso in analisi, prevede esclusivamente l'aggionamento delle date
	 * 
	 * @param delega
	 * @param loginOperazione
	 * @return
	 */
	public DeleTDelega aggiornaDelega(DeleTDelega delega, String loginOperazione) {
		final String METHOD_NAME = "aggiornaDelega";
		log.debug(METHOD_NAME, "aggiorna delega for: citCf delegante=%s", (ToLog) () -> {
			return delega != null ? delega.getDeleTCittadino1().getCitCf() : "null";
		});


		DeleTDelega delegaBydb = em.find(DeleTDelega.class, delega.getDlgaId());

		if (delegaBydb == null) {
			String msg = "Nessun risultato trovato per l'id " + delega.getDlgaId();
			log.error(METHOD_NAME, msg);
    		throw new BeServiceException("DA.R02", msg);
		}

		setModifica(delegaBydb, delegaBydb.getLoginOperazione());
		delegaBydb.setUuid(UUID.randomUUID());

		if (delegaBydb.getDeleTCittadino1() != null) {
			setModifica(delegaBydb.getDeleTCittadino1(), delegaBydb.getDeleTCittadino1().getLoginOperazione());
		}

		if (delegaBydb.getDeleTCittadino2() != null) {
			setModifica(delegaBydb.getDeleTCittadino2(), delegaBydb.getDeleTCittadino2().getLoginOperazione());
		}


		DeleTDichiarazioneDet deleTDichiarazioneDet = deleTDichiarazioneDetRepository.ricercaDichiarazioneDetByCF(delega.getDeleTCittadino2().getCitCf(), delega.getDeleTCittadino1().getCitCf());
		if (deleTDichiarazioneDet != null) {
			delegaBydb.setDeleTDichiarazioneDet(deleTDichiarazioneDet);
		}

		if (delegaBydb.getDeleTDelegatos() != null && !delegaBydb.getDeleTDelegatos().isEmpty()) {
			DeleTDelegato deleTDelegato = delegaBydb.getDeleTDelegatos().get(0);
			setModifica(deleTDelegato, deleTDelegato.getLoginOperazione());
		}

		DeleDDelegaTipo deleDDelegaTipo = delega.getDeleDDelegaTipo();
		if(deleDDelegaTipo != null){
			delegaBydb.setDeleDDelegaTipo(deleDDelegaTipo);
		}

		DeleDDelegaStato deleDDelegaStato = delega.getDeleDDelegaStato();
		if(deleDDelegaStato != null){
			delegaBydb.setDeleDDelegaStato(deleDDelegaStato);
		}

		Boolean blocca = delega.getBlocca();
		if(blocca != null){
			delegaBydb.setBlocca(blocca);
		}


		if (delega.getDeleTDelegaServizios() != null) {
			for (DeleTDelegaServizio ds : delega.getDeleTDelegaServizios()) {
				if (ds.getUuid() != null) {
					DeleTDelegaServizio deleTDelegaServizioTrovato = deleTDelegaServizioRepository.ricercaDelegaServizioByUUID(ds.getUuid());
					
					
					if (deleTDelegaServizioTrovato != null) {
						DeleTDelegaServizio deleTDelegaServizio = em.find(DeleTDelegaServizio.class, deleTDelegaServizioTrovato.getDelId());
						

						if (ds.getDeleDGrado()!=null) {
							if(ds.getDeleDGrado().getDelGradoCod()!= null) {
								deleTDelegaServizio.setDeleDGrado(ds.getDeleDGrado());
							}
						}						

						if (!ModelComparator.equals(ds, deleTDelegaServizio)) {

							deleTDelegaServizio.setUuid(UUID.randomUUID());
							setModifica(deleTDelegaServizio, deleTDelegaServizio.getLoginOperazione());


							if (ds.getDeleDDelegaServizioStato().getDelstatoCod().equals(DelstatoCodEnum.REVOCATA.name()) ||
								ds.getDeleDDelegaServizioStato().getDelstatoCod().equals(DelstatoCodEnum.RIFIUTATA.name())	) {								

								if (!checkStatiRevoca(deleTDelegaServizio.getDeleDDelegaServizioStato().getDelstatoCod())) {
									String msg = "Non e' possibile revocare una delega in stato stato [" + deleTDelegaServizio.getDeleDDelegaServizioStato().getDelstatoCod() + "].";
									log.error(METHOD_NAME, msg);
									throw new BeServiceException("DA.DLG03", msg);
								}


								deleTDelegaServizio.setDelDatascadenza(ds.getDelDatascadenza());
								deleTDelegaServizio.setDelDatarevoca(ds.getDelDatarevoca());
								deleTDelegaServizio.setDelDatarinuncia(ds.getDelDatarinuncia());
								deleTDelegaServizio.setDeleDDelegaServizioStato(ds.getDeleDDelegaServizioStato());

							}
							else if (ds.getDelDatascadenza() != null) {


								storicizzazione(deleTDelegaServizio);

								ds.setDelDatadecorrenza(new Date());


								if (deleTDelegaServizio.getDeleDDelegaServizioStato().getDelstatoCod().equals((DeleDDelegaServizioStatoRepository.DelstatoCodEnum.IN_SCADENZA.name()))) {
									deleTDelegaServizio.setDeleDDelegaServizioStato(delegaServizioStato.ricercaServiziStatoByDelstatoCod(DeleDDelegaServizioStatoRepository.DelstatoCodEnum.ATTIVA));
								}

								if (delegaBydb.getDeleTDichiarazioneDet() == null) {
									
									if (ds.getDelDatascadenza().after(ds.getDelDatadecorrenza())) {
										String validitaServizio = servizioParametroRepository.getValiditaServizio(deleTDelegaServizioTrovato.getDeleDServizio().getSerId());
										Date dataValidita = DateUtil.advanceDate(ds.getDelDatadecorrenza(), validitaServizio);
		
										if (ds.getDelDatascadenza().after(dataValidita)) {
											String msg = "La data di scadenza inserita e' successiva al periodo limite normativo di validita' della delega: " + DateUtil.toStringSimpleDate(dataValidita);
											log.error(METHOD_NAME, msg);
								    		throw new BeServiceException("DA.DLG01", msg);
										}
									} else {
										String msg = "La data di scadenza della delega deve essere successiva alla data di decorrenza";
										log.error(METHOD_NAME, msg);
							    		throw new BeServiceException("DA.DLG02", msg);
									}
								}								

								deleTDelegaServizio.setDelDatadecorrenza(ds.getDelDatadecorrenza());
								deleTDelegaServizio.setDelDatascadenza(ds.getDelDatascadenza());
								
								deleTDelegaServizio.setDelDatarevoca(null);
								deleTDelegaServizio.setDelDatarinuncia(null);
								deleTDelegaServizio.setDeleDDelegaServizioStato(ds.getDeleDDelegaServizioStato());
							}
						}
					} else {
						String msg = "Nessun risultato trovato per delega su servizio con UUID " + ds.getUuid();
						log.error(METHOD_NAME, msg);
			    		throw new BeServiceException("DA.R02", msg);
					}
				} else {

					
					DeleDServizio dServizio = servizioRepository.ricercaServiziBySerCod(ds.getDeleDServizio().getSerCod());
					String validitaServizio = servizioParametroRepository.getValiditaServizio(dServizio.getSerId());

					ds.setDeleDDelegaServizioStato(delegaServizioStato.ricercaServiziStatoByDelstatoCod(DeleDDelegaServizioStatoRepository.DelstatoCodEnum.ATTIVA));
					ds.setDelId(null);
					ds.setUuid(UUID.randomUUID());
					setCreazione(ds, loginOperazione);

					ds.setDelDatadecorrenza(new Date());
					
					if (ds.getDelDatascadenza() == null) {


						ds.setDelDatascadenza(DateUtil.advanceDate(ds.getDelDatadecorrenza(), validitaServizio));
					} else if (delega.getDeleTDichiarazioneDet() == null) {
						
						if (ds.getDelDatascadenza().after(ds.getDelDatadecorrenza())) {
							Date dataValidita = DateUtil.advanceDate(ds.getDelDatadecorrenza(), validitaServizio);
							if (ds.getDelDatascadenza().after(dataValidita)) {
								String msg = "La data di scadenza inserita e' successiva al periodo limite normativo di validita' della delega:" + dataValidita;
								log.error(METHOD_NAME, msg);
					    		throw new BeServiceException("DA.DLG01", msg);
							}
						} else {
							String msg = "La data di scadenza della delega deve essere successiva alla data di decorrenza";
							log.error(METHOD_NAME, msg);
				    		throw new BeServiceException("DA.DLG02", msg);
						}
					}
					
					if (ds.getDeleDGrado()!=null) {
					
					}else{

						if(delega.getDeleDDelegaTipo() != null) {
							if(ds.getDeleDServizio() != null) {
								DeleRServizioDelegaTipoGrado deleRServizioDelegaTipoGrado = deleRServizioDelegaTipoGradoRepository.ricercaGradoAssegnatoAlServizio(delega.getDeleDDelegaTipo().getDelTipId(), ds.getDeleDServizio().getSerId());
								if(deleRServizioDelegaTipoGrado!=null) {
									if(deleRServizioDelegaTipoGrado.getDeleDGrado()!=null) {
										Integer delGradoId = deleRServizioDelegaTipoGrado.getDeleDGrado().getDelGradoId();
										ds.setDeleDGrado(deleRServizioDelegaTipoGrado.getDeleDGrado());						
									}					
								}						
							}					
						}
					}				
				
					delegaBydb.getDeleTDelegaServizios().add(ds);					
				}
			}
		}

		em.merge(delegaBydb);

		return delegaBydb;
	}	
	

	private boolean checkStatiRevoca(String codStato) {
		return codStato.equals(DeleDDelegaServizioStatoRepository.DelstatoCodEnum.ATTIVA.name())
			|| codStato.equals(DeleDDelegaServizioStatoRepository.DelstatoCodEnum.IN_SCADENZA.name());
	}


	public DeleDServizio ricercaServizio(String serCod) {
		final String METHOD_NAME = "ricercaServizio";

		Map<String, Object> params = new HashMap<>();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT tab FROM DeleDServizio tab ");
		sb.append("WHERE tab.dataCancellazione IS NULL ");
		sb.append("AND tab.serCod = :serCod ");
		params.put("serCod", serCod);
		String jpql = sb.toString();
		TypedQuery<DeleDServizio> query = em.createQuery(jpql, DeleDServizio.class);
		params.entrySet().stream().forEach(e -> {
			String key = e.getKey();
			Object value = e.getValue();
			query.setParameter(key, value);
			log.debug(METHOD_NAME, "Param: key: %s, value: %s", key, value);
		});

		DeleDServizio result = query.getSingleResult();
		return result;
	}

	public List<DeleTDelega> ricercaDeleghe(DeleTDelega delega, List<String> codiciServizio, List<String> statiDelega) {
		final String METHOD_NAME = "ricercaDeleghe";

		Map<String, Object> params = new HashMap<>();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT d FROM DeleTDelega d ");
		sb.append("WHERE d.dataCancellazione IS NULL ");
		populateFilter(sb, params, delega, codiciServizio, statiDelega);

		String jpql = sb.toString();
		TypedQuery<DeleTDelega> query = em.createQuery(jpql, DeleTDelega.class);
		params.entrySet().stream().forEach(e -> {
			String key = e.getKey();
			Object value = e.getValue();
			query.setParameter(key, value);
			log.debug(METHOD_NAME, "Param: key: %s, value: %s", key, value);
		});

		List<DeleTDelega> result = query.getResultList();
		
		return result;
	}
	

	public List<DatiDelegatoDB> ricercaDelegati(Delega delega, List<String> codiciServizio, List<String> statiDelega) {
		
		final String METHOD_NAME = "ricercaDelegati";
		
		List<DatiDelegatoDB> result = new ArrayList<DatiDelegatoDB>();	
		Map<String, Object> params = new HashMap<>();
		StringBuilder sb = new StringBuilder();
	
		sb.append("SELECT distinct ");
		sb.append("a.dlga_id, f.cit_cf, f.cit_cognome, f.cit_nome, f.cit_sesso, f.cit_nascita_comune, f.cit_nascita_data, f.cit_auraid, Cast(a.uuid as varchar) uuid ");
		sb.append("FROM "); 
		sb.append("dele_t_cittadino e, dele_t_delega a, dele_t_delega_servizio b, ");
		sb.append("dele_d_servizio c, dele_d_delega_servizio_stato d, dele_t_cittadino f ");
		sb.append("WHERE ");		
		if (delega.getDelegante() != null && delega.getDelegante().getCodiceFiscale() != null) {
			sb.append("e.cit_cf  = '" +delega.getDelegante().getCodiceFiscale()+"' and ");
		}
		if (delega.getDelegato() != null && delega.getDelegato().getCodiceFiscale() != null) {
			sb.append("f.cit_cf  = '" +delega.getDelegato().getCodiceFiscale()+"' and ");
		}		
		sb.append("a.cit_id_delegante = e.cit_id and   "); 
		sb.append("a.dlga_id = b.dlga_id and ");
		sb.append("b.ser_id = c.ser_id and ");
		if (codiciServizio != null && !codiciServizio.isEmpty()) {
			sb.append("c.ser_cod IN (:codiciServizio) and "); 
			params.put("codiciServizio", codiciServizio);
		}		

		sb.append("b.delstato_id = d.delstato_id and ");
		if (statiDelega != null && !statiDelega.isEmpty()) {
			sb.append("d.delstato_cod IN (:statiDelega) and ");
			params.put("statiDelega", statiDelega);
		}		
		sb.append("a.cit_id_delegato = f.cit_id ");  

		String jpql = sb.toString();
		
		Query query = em.createNativeQuery(jpql);

		params.entrySet().stream().forEach(e -> {
			String key = e.getKey();
			Object value = e.getValue();
			query.setParameter(key, value);
		});
		
		List<Object[]> resultQuery = query.getResultList();
			for(Object[] delegato : resultQuery) {
				DatiDelegatoDB datiDelegato = new DatiDelegatoDB();
				
				Integer dlgaId = (Integer) delegato[0];
				String codiceFiscale = (String) delegato[1];
			    String cognome = (String) delegato[2];
			    String nome = (String) delegato[3];	
			    String sesso = (String) delegato[4];
			    String luogoNascita = (String) delegato[5];
			    Date dataDiNascita = (Date) delegato[6];
			    String idAura = (String) delegato[7];
			    String uuid = (String) delegato[8];				
				
			    datiDelegato.setDlgaId(dlgaId);
			    datiDelegato.setCodiceFiscale(codiceFiscale);
			    datiDelegato.setCognome(cognome);
			    datiDelegato.setNome(nome);
			    datiDelegato.setSesso(sesso);
			    datiDelegato.setLuogoNascita(luogoNascita);
			    datiDelegato.setDataDiNascita(dataDiNascita);
			    datiDelegato.setIdAura(idAura);
			    datiDelegato.setUuid(uuid);
				
			    
				result.add(datiDelegato);						
			}			
		
		return result;
	}
	
	
	
	public List<DatiDelegatoDB> ricercaDelegatiFSE(Delega delega) {
		final String METHOD_NAME = "ricercaDelegatiFSE";

		
		List<DatiDelegatoDB> result = new ArrayList<DatiDelegatoDB>();	
		Map<String, Object> params = new HashMap<>();
		StringBuilder sb = new StringBuilder();
	
		sb.append("SELECT distinct ");
		sb.append("a.dlga_id, f.cit_cf, f.cit_cognome, f.cit_nome, f.cit_sesso, f.cit_nascita_comune, f.cit_nascita_data, f.cit_auraid, Cast(a.uuid as varchar) uuid ");
		sb.append("FROM "); 
		sb.append("dele_t_cittadino e, dele_t_delega a, dele_t_delega_servizio b, ");
		sb.append("dele_d_servizio c, dele_d_delega_servizio_stato d, dele_t_cittadino f ");
		sb.append("WHERE ");		
		if (delega.getDelegante() != null && delega.getDelegante().getCodiceFiscale() != null) {
			sb.append("e.cit_cf  = '" +delega.getDelegante().getCodiceFiscale()+"' and ");
		}		
		sb.append("a.cit_id_delegante = e.cit_id and   "); 
		sb.append("a.dlga_id = b.dlga_id and ");
		sb.append("b.ser_id = c.ser_id and ");

		sb.append("now() between b.del_datadecorrenza and b.del_datascadenza and ");
		sb.append("b.delstato_id = d.delstato_id and ");
		sb.append("d.delstato_cod IN ('ATTIVA', 'AGGIORNATA', 'IN_SCADENZA') and ");		
		sb.append("a.cit_id_delegato = f.cit_id ");  

		String jpql = sb.toString();
		
		Query query = em.createNativeQuery(jpql);
		
		List<Object[]> resultQuery = query.getResultList();
			for(Object[] delegato : resultQuery) {
				DatiDelegatoDB datiDelegato = new DatiDelegatoDB();
				
				Integer dlgaId = (Integer) delegato[0];
				String codiceFiscale = (String) delegato[1];
			    String cognome = (String) delegato[2];
			    String nome = (String) delegato[3];	
			    String sesso = (String) delegato[4];
			    String luogoNascita = (String) delegato[5];
			    Date dataDiNascita = (Date) delegato[6];
			    String idAura = (String) delegato[7];
			    String uuid = (String) delegato[8];				
				
			    datiDelegato.setDlgaId(dlgaId);
			    datiDelegato.setCodiceFiscale(codiceFiscale);
			    datiDelegato.setCognome(cognome);
			    datiDelegato.setNome(nome);
			    datiDelegato.setSesso(sesso);
			    datiDelegato.setLuogoNascita(luogoNascita);
			    datiDelegato.setDataDiNascita(dataDiNascita);
			    datiDelegato.setIdAura(idAura);
			    datiDelegato.setUuid(uuid);
			    
				
				result.add(datiDelegato);						
			}
					

		return result;
	}
	
	public List<DatiDelegaServizioDB> ricercaServiziAssociatiDelegato(String codiceFiscaleDelegante, List<String> codiciServizio, List<String> statiDelega, Integer dlgaId, String codiceFiscaleDelegato) {
		final String METHOD_NAME = "ricercaServiziAssociatiDelegato";
		
		List<DatiDelegaServizioDB> result = new ArrayList<DatiDelegaServizioDB>();
		Map<String, Object> params = new HashMap<>();
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT ");  
		sb.append("c.ser_cod, b.ser_id, b.del_datadecorrenza, b.del_datascadenza, b.del_datarevoca, b.del_datarinuncia, b.del_grado_id, d.delstato_cod, Cast(b.uuid as varchar) uuid, g.del_tip_cod "); 
		sb.append("FROM "); 
		sb.append("dele_t_cittadino e, dele_t_delega a, dele_t_delega_servizio b, "); 
		sb.append("dele_d_servizio c, dele_d_delega_servizio_stato d, dele_d_delega_tipo g, dele_t_cittadino f "); 
		sb.append("WHERE "); 
		if (codiceFiscaleDelegante != null) {
			sb.append("e.cit_cf  = '" +codiceFiscaleDelegante+"' and ");
		}	
		if (codiceFiscaleDelegato != null) {
			sb.append("f.cit_cf  = '" +codiceFiscaleDelegato+"' and ");
		}	
		sb.append("a.cit_id_delegante = e.cit_id and "); 
		sb.append("a.dlga_id = b.dlga_id and "); 
		sb.append("b.ser_id = c.ser_id and "); 
		if (codiciServizio != null && !codiciServizio.isEmpty()) {
			sb.append("c.ser_cod IN (:codiciServizio) and "); 
			params.put("codiciServizio", codiciServizio);
		}		 
		sb.append("a.del_tip_id = g.del_tip_id and ");

		sb.append("b.delstato_id = d.delstato_id and ");
		if (statiDelega != null && !statiDelega.isEmpty()) {
			sb.append("d.delstato_cod IN (:statiDelega) and ");
			params.put("statiDelega", statiDelega);
		}	
		sb.append("a.cit_id_delegato = f.cit_id and ");
		sb.append("b.dlga_id = " +dlgaId);		

		String jpql = sb.toString();
		Query query = em.createNativeQuery(jpql);
		
		params.entrySet().stream().forEach(e -> {
			String key = e.getKey();
			Object value = e.getValue();
			query.setParameter(key, value);
		});
		
		List<Object[]> resultQuery = query.getResultList();
			for(Object[] serviziAssociatiAlDelegato : resultQuery) {
				DatiDelegaServizioDB datiDelegaServizio = new DatiDelegaServizioDB();				
				
				String codiceServizio = (String) serviziAssociatiAlDelegato[0];
				Integer serId = (Integer) serviziAssociatiAlDelegato[1]; 
				Date dataInizioDelega = (Date) serviziAssociatiAlDelegato[2];
				Date dataFineDelega = (Date) serviziAssociatiAlDelegato[3];
				Date dataRevoca = (Date) serviziAssociatiAlDelegato[4];					
				Date dataRinuncia = (Date) serviziAssociatiAlDelegato[5];
				Integer idGradoDelega = (Integer) serviziAssociatiAlDelegato[6];
				String statoDelega = (String) serviziAssociatiAlDelegato[7];
			    String uuid = (String) serviziAssociatiAlDelegato[8];			    
			    String tipoDelega = (String) serviziAssociatiAlDelegato[9];	
			    
			    datiDelegaServizio.setCodiceServizio(codiceServizio);
			    datiDelegaServizio.setSerId(serId);
			    datiDelegaServizio.setDataInizioDelega(dataInizioDelega);
			    datiDelegaServizio.setDataFineDelega(dataFineDelega);
			    datiDelegaServizio.setDataRevoca(dataRevoca);
			    datiDelegaServizio.setDataRinuncia(dataRinuncia);
			    datiDelegaServizio.setIdGradoDelega(idGradoDelega);
			    datiDelegaServizio.setStatoDelega(statoDelega);
			    datiDelegaServizio.setUuid(uuid);			    
			    datiDelegaServizio.setTipoDelega(tipoDelega);			    
			    
				
				result.add(datiDelegaServizio);						
			}			
		
		
		return result;		
	}
	
	public List<DatiDelegaServizioDB> ricercaServiziAssociatiDelegatoFSE(String codiceFiscaleDelegante, Integer dlgaId) {
		final String METHOD_NAME = "ricercaServiziAssociatiDelegatoFSE";		
		
		List<DatiDelegaServizioDB> result = new ArrayList<DatiDelegaServizioDB>();
		Map<String, Object> params = new HashMap<>();
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT ");  
		sb.append("c.ser_cod, b.ser_id, b.del_datadecorrenza, b.del_datascadenza, b.del_datarevoca, b.del_datarinuncia, b.del_grado_id, d.delstato_cod, Cast(b.uuid as varchar) uuid, g.del_tip_cod "); 
		sb.append("FROM "); 
		sb.append("dele_t_cittadino e, dele_t_delega a, dele_t_delega_servizio b, "); 
		sb.append("dele_d_servizio c, dele_d_delega_servizio_stato d, dele_d_delega_tipo g, dele_t_cittadino f "); 
		sb.append("WHERE "); 
		if (codiceFiscaleDelegante != null) {
			sb.append("e.cit_cf  = '" +codiceFiscaleDelegante+"' and ");
		}	
		sb.append("a.cit_id_delegante = e.cit_id and "); 
		sb.append("a.dlga_id = b.dlga_id and "); 
		sb.append("b.ser_id = c.ser_id and "); 
		 
		sb.append("a.del_tip_id = g.del_tip_id and ");
		sb.append("now() between b.del_datadecorrenza and b.del_datascadenza and ");
		sb.append("b.delstato_id = d.delstato_id and ");
		sb.append("d.delstato_cod IN ('ATTIVA', 'AGGIORNATA', 'IN_SCADENZA') and ");		
		sb.append("a.cit_id_delegato = f.cit_id and ");
		sb.append("b.dlga_id = " +dlgaId);		

		String jpql = sb.toString();
		Query query = em.createNativeQuery(jpql);
		
		List<Object[]> resultQuery = query.getResultList();
			for(Object[] serviziAssociatiAlDelegato : resultQuery) {
				DatiDelegaServizioDB datiDelegaServizio = new DatiDelegaServizioDB();				
				
				String codiceServizio = (String) serviziAssociatiAlDelegato[0];
				Integer serId = (Integer) serviziAssociatiAlDelegato[1]; 
				Date dataInizioDelega = (Date) serviziAssociatiAlDelegato[2];
				Date dataFineDelega = (Date) serviziAssociatiAlDelegato[3];
				Date dataRevoca = (Date) serviziAssociatiAlDelegato[4];					
				Date dataRinuncia = (Date) serviziAssociatiAlDelegato[5];
				Integer idGradoDelega = (Integer) serviziAssociatiAlDelegato[6];
				String statoDelega = (String) serviziAssociatiAlDelegato[7];
			    String uuid = (String) serviziAssociatiAlDelegato[8];			    
			    String tipoDelega = (String) serviziAssociatiAlDelegato[9];	
			    
			    datiDelegaServizio.setCodiceServizio(codiceServizio);
			    datiDelegaServizio.setSerId(serId);
			    datiDelegaServizio.setDataInizioDelega(dataInizioDelega);
			    datiDelegaServizio.setDataFineDelega(dataFineDelega);
			    datiDelegaServizio.setDataRevoca(dataRevoca);
			    datiDelegaServizio.setDataRinuncia(dataRinuncia);
			    datiDelegaServizio.setIdGradoDelega(idGradoDelega);
			    datiDelegaServizio.setStatoDelega(statoDelega);
			    datiDelegaServizio.setUuid(uuid);			    
			    datiDelegaServizio.setTipoDelega(tipoDelega);			    
			    
				
				result.add(datiDelegaServizio);						
			}			
				
		
		return result;		
	}
	
	public String ricercaGrado(Integer idGradoDelega) {
		final String METHOD_NAME = "ricercaGrado";
		
		String result = new String();
		Map<String, Object> params = new HashMap<>();
		StringBuilder sb = new StringBuilder();
			
		sb.append("SELECT ");  
		sb.append("del_grado_cod "); 
		sb.append("FROM "); 
		sb.append("dele_d_grado "); 
		sb.append("WHERE "); 		
		sb.append("del_grado_id = " +idGradoDelega);	

		String jpql = sb.toString();
		Query query = em.createNativeQuery(jpql);			
		
		try {
			Object resultQuery = query.getSingleResult();
			result = (String) resultQuery;	
		} catch (NonUniqueResultException nure) {
    		String msg = "Trovato piu' di un risultato per idGradoDelega " + idGradoDelega;
    		log.error(METHOD_NAME, msg, nure);
    		throw new BeServiceException("DA.R01", msg);
    	} catch(NoResultException nre) {
    		String msg = "Nessun risultato trovato per idGradoDelega " + idGradoDelega;
    		log.error(METHOD_NAME, msg, nre);
			return null;
		}		
		
		return result;		
	}

	public List<String> ricercaDelegheFSE(Delega delega, List<String> codiciServizio, List<String> statiDelega) {
		final String METHOD_NAME = "ricercaDelegheFSE";
		
		List<String> result = new ArrayList<String>();
		

		Map<String, Object> params = new HashMap<>();
		StringBuilder sb = new StringBuilder();
	
		sb.append("SELECT f.cit_cf AS codiceFiscaledelegato ");
		sb.append("FROM dele_t_cittadino e, dele_t_delega a, dele_t_delega_servizio b, dele_d_servizio c, ");
		sb.append("dele_d_delega_servizio_stato d, dele_t_cittadino f where "); 
		if (delega.getDelegante() != null && delega.getDelegante().getCodiceFiscale() != null) {
			sb.append("e.cit_cf  = '" +delega.getDelegante().getCodiceFiscale()+"' ");
		}		
		sb.append("and a.cit_id_delegante = e.cit_id and a.dlga_id = b.dlga_id and b.ser_id = c.ser_id and "); 
		sb.append("c.ser_cod = 'ritiroreferti' and b.delstato_id = d.delstato_id and "); 
		sb.append("now() between b.del_datadecorrenza and b.del_datascadenza ");
		sb.append("and b.delstato_id = d.delstato_id and d.delstato_cod in ('ATTIVA', 'AGGIORNATA', 'IN_SCADENZA') ");
		sb.append("and a.cit_id_delegato = f.cit_id ");  


		String jpql = sb.toString();
		Query query = em.createNativeQuery(jpql);

		List resultQuery = query.getResultList();
		if(resultQuery.size() ==0) {
		}else {
			resultQuery.forEach(delegati -> {
				result.add(delegati.toString());			

		    });	
		}			

		
		return result;
	}

	
	private void populateFilter(StringBuilder sb, Map<String, Object> params, DeleTDelega delega, List<String> codiciServizio, List<String> statiDelega) {
		if (delega != null) {

			if (delega.getUuid() != null) {
				sb.append("AND d.uuid = :uuid ");
				params.put("uuid", delega.getUuid());
				return;
			}

			if (delega.getDeleTCittadino1() != null && delega.getDeleTCittadino1().getCitCf() != null) {
				sb.append("AND d.deleTCittadino1.citCf = :delegante ");
				params.put("delegante", delega.getDeleTCittadino1().getCitCf());
			}

			if (delega.getDeleTCittadino2() != null && delega.getDeleTCittadino2().getCitCf() != null) {
				sb.append("AND d.deleTCittadino2.citCf = :delegato ");
				params.put("delegato", delega.getDeleTCittadino2().getCitCf());
			}
		}

		
		if ((codiciServizio != null && !codiciServizio.isEmpty()) || (statiDelega != null && !statiDelega.isEmpty())) {

			sb.append("AND EXISTS (SELECT ds.deleDServizio.serCod FROM d.deleTDelegaServizios ds WHERE ds.dataCancellazione IS NULL");
			if (codiciServizio != null && !codiciServizio.isEmpty()) {
				sb.append(" AND ds.deleDServizio.serCod IN (:codiciServizio)");
				params.put("codiciServizio", codiciServizio);
			}
			if (statiDelega != null && !statiDelega.isEmpty()) {
				sb.append(" AND ds.deleDDelegaServizioStato.delstatoCod IN (:statiDelega)");
				params.put("statiDelega", statiDelega);
			}
			sb.append(")");
		}
	}
	
	
	/**
	 * NUOVO SERVIZIO
	 * Richiamato in ricercaDelega --> Back-office Tab Tutte le deleghe
	 * @param cfDelegato
	 * @param cfDelagante
	 * @param dataInsDa
	 * @param dateInsA
	 * @param dataValDa
	 * @param dataValA
	 * @param tipiDelega
	 * @param statiDelega
	 * @return
	 */
	public List<DeleTDelega> ricercaDeleghe(String cfDelegato, String cfDelagante, 
											Date dataInsDa, Date dataInsA, Date dataValDa , Date dataValA, 
											List<String> tipiDelega, List<String> statiDelega, Integer citIdDelegato, Integer citIdDelegante) {
		final String METHOD_NAME = "ricercaDeleghe";

		Map<String, Object> params = new HashMap<>();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT d FROM DeleTDelega d ");
		sb.append("WHERE d.dataCancellazione IS NULL ");
		populateFilter(sb, params, cfDelegato, cfDelagante, dataInsDa, dataInsA, dataValDa, dataValA, tipiDelega, statiDelega, citIdDelegato, citIdDelegante);

		String jpql = sb.toString();
		TypedQuery<DeleTDelega> query = em.createQuery(jpql, DeleTDelega.class);
		params.entrySet().stream().forEach(e -> {
			String key = e.getKey();
			Object value = e.getValue();
			query.setParameter(key, value);
			log.debug(METHOD_NAME, "Param: key: %s, value: %s", key, value);
		});

		
    	List<DeleTDelega> result = query.getResultList();
		
		return result;
	}
	
	
	private void populateFilter(StringBuilder sb, Map<String, Object> params, 
								String cfDelegato, String cfDelagante, Date dataInsDa, Date dataInsA, 
								Date dataValDa , Date dataValA, List<String> tipiDelega, List<String> statiDelega, Integer citIdDelegato, Integer citIdDelegante) {


		if (citIdDelegante != null) {
			sb.append("AND cit_id_delegante = :citIdDelegante ");
			params.put("citIdDelegante", citIdDelegante);
		}
		
		if (citIdDelegato != null) {
			sb.append("AND cit_id_delegato = :citIdDelegato ");
			params.put("citIdDelegato", citIdDelegato);
		}
		
		if (cfDelagante != null) {
			sb.append("AND d.deleTCittadino1.citCf = :delegante ");
			params.put("delegante", cfDelagante);
		}

		if (cfDelegato != null) {
			sb.append("AND d.deleTCittadino2.citCf = :delegato ");
			params.put("delegato", cfDelegato);
		}

	
		if (tipiDelega != null && !tipiDelega.isEmpty()) {
			if(!tipiDelega.get(0).equalsIgnoreCase("Tutte")) {
				sb.append(" AND d.deleDDelegaTipo.delTipCod IN (:tipiDelega)");
				params.put("tipiDelega", tipiDelega);
			}
		}
		
		if (dataInsDa != null && dataInsA !=null) {
			sb.append("AND to_date(to_char(d.dataCreazione, 'yyyy-MM-dd'), 'yyyy-MM-dd') "
					+ "BETWEEN to_date(:dataCreazioneDa, 'yyyy-MM-dd') AND to_date(:dataCreazioneA, 'yyyy-MM-dd') ");
		    params.put("dataCreazioneDa",  dataInsDa);
		    params.put("dataCreazioneA",  dataInsA);
		
		}
		

		if (statiDelega != null && !statiDelega.isEmpty()) {
			if(!statiDelega.get(0).equalsIgnoreCase("Tutte")) {
				sb.append(" AND d.deleDDelegaStato.delStatoCod IN (:statiDelega)");
				params.put("statiDelega", statiDelega);
			}
		}	

		
		
		if (dataValDa != null && dataValA !=null ) {
			


			sb.append(" AND EXISTS (SELECT ds FROM d.deleTDelegaServizios ds WHERE ds.dataCancellazione IS NULL");
			sb.append(" AND (:dataValDa BETWEEN ds.delDatadecorrenza AND ds.delDatascadenza) ");
			sb.append(" AND (:dataValA BETWEEN ds.delDatadecorrenza AND ds.delDatascadenza)");
			
					
			params.put("dataValDa", dataValDa);
			params.put("dataValA", dataValDa);
			
			
			sb.append(")");
			
		}
		
	}

	public DeleTDelega ricercaDelegheByUUID(UUID uuid) {
		final String METHOD_NAME = "ricercaDelegheByUUID";
		Map<String, Object> params = new HashMap<>();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT tab FROM DeleTDelega tab ");
		sb.append("WHERE tab.dataCancellazione IS NULL ");
		sb.append("AND tab.uuid = :uuid ");
		params.put("uuid", uuid);
		String jpql = sb.toString();
		TypedQuery<DeleTDelega> query = em.createQuery(jpql, DeleTDelega.class);
		params.entrySet().stream().forEach(e -> {
			String key = e.getKey();
			Object value = e.getValue();
			query.setParameter(key, value);
			log.debug(METHOD_NAME, "Param: key: %s, value: %s", key, value);
		});

		DeleTDelega result;
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

	public List<DeleTDelega> ricercaDelegheByCF(String cf1, String cf2) {
		final String METHOD_NAME = "ricercaDelegheByCF";
		Map<String, Object> params = new HashMap<>();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT tab FROM DeleTDelega tab ");
		sb.append("WHERE tab.dataCancellazione IS NULL ");
		populateFilter(sb, params, cf1, cf2);

		String jpql = sb.toString();
		TypedQuery<DeleTDelega> query = em.createQuery(jpql, DeleTDelega.class);
		params.entrySet().stream().forEach(e -> {
			String key = e.getKey();
			Object value = e.getValue();
			query.setParameter(key, value);
			log.debug(METHOD_NAME, "Param: key: %s, value: %s", key, value);
		});
		List<DeleTDelega> result = query.getResultList();

		return result;
	}

	private void populateFilter(StringBuilder sb, Map<String, Object> params, String codFiscale1, String codFiscale2) {

		if (codFiscale1 != null && !codFiscale1.isEmpty()) {
			sb.append("AND tab.deleTCittadino1.citCf = :delegante ");
			params.put("delegante", codFiscale1);
		}

		if (codFiscale2 != null && !codFiscale2.isEmpty()) {
			sb.append("AND tab.deleTCittadino2.citCf = :delegato ");
			params.put("delegato", codFiscale2);
		}
	}

	private void storicizzazione(DeleTDelegaServizio deleTDelegaServizio) {
		DeleDDelegaServizioStato stato = deleTDelegaServizio.getDeleDDelegaServizioStato();
		if (stato.getDelstatoCod().equals(DeleDDelegaServizioStatoRepository.DelstatoCodEnum.ATTIVA.name()) || stato.getDelstatoCod().equals(DeleDDelegaServizioStatoRepository.DelstatoCodEnum.IN_SCADENZA.name())) {
			stato = delegaServizioStato.ricercaServiziStatoByDelstatoCod(DelstatoCodEnum.AGGIORNATA);
		}
		
		DeleSDelegaServizio sServizio = new DeleSDelegaServizio();
		sServizio.setDelsId(null);
		sServizio.setDataCreazione(deleTDelegaServizio.getDataCreazione());
		sServizio.setDataModifica(deleTDelegaServizio.getDataModifica());
		sServizio.setDelDatadecorrenza(deleTDelegaServizio.getDelDatadecorrenza());
		sServizio.setDelDatarevoca(deleTDelegaServizio.getDelDatarevoca());
		sServizio.setDelDatascadenza(deleTDelegaServizio.getDelDatascadenza());
		sServizio.setDelDatarinuncia(deleTDelegaServizio.getDelDatarinuncia());
		sServizio.setDeleDDelegaServizioStato(deleTDelegaServizio.getDeleDDelegaServizioStato());
		sServizio.setDeleDRuoloOp(deleTDelegaServizio.getDeleDRuoloOp());
		sServizio.setDeleDServizio(deleTDelegaServizio.getDeleDServizio());
		sServizio.setDeleTDelega(deleTDelegaServizio.getDeleTDelega());
		sServizio.setLoginOperazione(deleTDelegaServizio.getLoginOperazione());
		sServizio.setDeleTDelegaServizio(deleTDelegaServizio);
		sServizio.setDeleDDelegaServizioStato(stato);
		sServizio.setValiditaInizio(deleTDelegaServizio.getDelDatadecorrenza());
		sServizio.setValiditaFine(new Date());
		
		em.persist(sServizio);
	}

}