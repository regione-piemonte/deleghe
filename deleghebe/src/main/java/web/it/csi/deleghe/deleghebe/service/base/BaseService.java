/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service.base;

import static it.csi.deleghe.deleghebe.util.Check.checkNotNull;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

import javax.inject.Inject;

import it.csi.deleghe.deleghebe.model.CsiLogAudit;
import it.csi.deleghe.deleghebe.service.CsiLogAuditRepository;
import it.csi.deleghe.deleghebe.service.base.exception.ServiceException;
import it.csi.deleghe.deleghebe.service.exception.BeServiceException;
import it.csi.deleghe.deleghebe.util.LogUtil;
import it.csi.deleghe.deleghebe.util.LogUtil.ToLog;
import it.csi.deleghe.deleghebe.ws.model.Richiedente;
import it.csi.deleghe.deleghebe.ws.model.RisultatoCodice;
import it.csi.deleghe.deleghebe.ws.msg.ServiceRequest;
import it.csi.deleghe.deleghebe.ws.msg.ServiceResponse;

/**
 * Classe base dell'implementazione della business logic di un generico servizio.
 * 
 * 
 * @author zyz zyz
 *
 * @param <REQ> Input del servizio che estende ServiceRequest
 * @param <RES> Output del servizio che estende ServiceResponse
 */
public abstract class BaseService<REQ extends ServiceRequest,RES extends ServiceResponse> {
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("[yyyy/MM/dd HH:mm:ss] ");
	
	@Inject
	protected LogUtil log;
	
	@Inject
	private CsiLogAuditRepository csiLogAuditRepository;	
	
	/**
	 * Esecuzione del servizo.
	 * 
	 * @param serviceRequest Input del servizio che estende ServiceRequest
	 * @return Output del servizio che estende ServiceResponse
	 */
	public RES executeService(REQ req){
		final String METHOD_NAME = "executeService";
		long time = System.currentTimeMillis();
		log.debug(METHOD_NAME, "Start service: %s", this.getClass().getSimpleName());
		log.logXmlTypeObject(req, "Service Request");
		
		RES res = null;
		try {

			checkServiceParams(req);
			res = execute(req);
		} catch (Exception e) {
			res = handleException(req, e);
		} finally {
			log.logXmlTypeObject(res, "Service Response");
			log.debug(METHOD_NAME, "End service: %s. Elapsed: %s ms.", this.getClass().getSimpleName(),
					(ToLog) () -> (System.currentTimeMillis() - time));
			saveLogAudit(req);
		}
		return res;
	}
	
	protected RES handleException(REQ req, Exception e) {
		final String METHOD_NAME = "handleException";
		RES res = instantiateNewRes();
		res.setEsito(RisultatoCodice.FALLIMENTO);
		
		if(e instanceof ServiceException) {
			ServiceException se = (ServiceException)e;
			log.error(METHOD_NAME, "Service exception in service: %s. Errori: %s "+ (se.getCause()!=null?"Cause":""), se.getCause(),
					this.getClass().getSimpleName(), (ToLog) () -> {

						StringBuilder sb = new StringBuilder();
						se.getErrori().forEach(err -> {
							sb.append("\n   ");
							sb.append(err.getCodice());
							sb.append(" - ");
							sb.append(err.getDescrizione());
						});
						sb.append("\n");
						return sb.toString();
					});
			res.getErrori().addAll(se.getErrori());
		} else {
			if ((e.getCause() != null) && (e instanceof Exception)) {
				e = (Exception)e.getCause();
				if (e instanceof BeServiceException) {
					BeServiceException se = (BeServiceException)e;
					log.error(METHOD_NAME, "Managed exception in service: %s", se, this.getClass().getSimpleName());
					res.addErrore(se.getCode(), sdf.format(GregorianCalendar.getInstance().getTime()) + se.getMessage());
				} else {
					log.error(METHOD_NAME, "Generic exception in service: %s", e, this.getClass().getSimpleName());
					res.addErrore("ERRORE", sdf.format(GregorianCalendar.getInstance().getTime()) + e.getMessage());
				}
			} else {
				log.error(METHOD_NAME, "Generic exception in service: %s", e, this.getClass().getSimpleName());
				res.addErrore("ERRORE", sdf.format(GregorianCalendar.getInstance().getTime()) + e.getMessage());
			}
		}
		
		return res;
	}

	/**
	 * Implementa l'esecuzione della bussiness logic del servizio.
	 * E' demandato all'implementazione di questo metodo 
	 * l'impostazione dell'esito del servizio
	 * Ad esempio in caso di successo bisognarà eseguire res.setEsito(Esito.SUCCESSO);
	 *  
	 */
	protected abstract RES execute(REQ req);
	
	/**
	 * Instanzia l'oggetto service response vuoto.
	 * Questo metodo prevede che l'oggetto serviceResponse abbia il costruttore vuoto.
	 * Per esigenze più complesse è necessario fare override.
	 * 
	 * @return
	 */
	protected RES instantiateNewRes() {
		final String METHOD_NAME = "instantiateNewRes";
		try {
			ParameterizedType t = (ParameterizedType) getClass().getGenericSuperclass();
			Type[] actualTypeArguments = t.getActualTypeArguments();
			Type type  = actualTypeArguments[1];
			@SuppressWarnings("unchecked")
			Class<RES> c = (Class<RES>)type;
			return c.newInstance();
		} catch (InstantiationException e) {
			String msg = "Errore instanziamento automatico serviceResponse. "
					+ "Deve esistere un costruttore vuoto. Per esigenze più complesse "
					+ "sovrascrivere il metodo instantiateNewRes a livello di servizio.";
			log.error(METHOD_NAME, msg, e);
			throw new IllegalArgumentException(msg, e);
		} catch (IllegalAccessException e) {
			String msg = "Errore instanziamento automatico serviceResponse. ";
			log.error(METHOD_NAME, msg, e);
			throw new IllegalArgumentException(msg, e);
		}
		
	}
	
	/**
	 * Check dei parametri di input del servzio.
	 * Check formali, di presenza e obbligatorietà, non check su db.
	 */
	protected abstract void checkServiceParams(REQ req);

	protected void checkRichiedente(Richiedente richiedente) {
		checkNotNull(richiedente, "DA.V02", "Richiedente non valorizzato.");
		checkNotNull(richiedente.getCodiceFiscale(), "DA.V01", "Codice fiscale richiedente non valorizzato.");
		checkNotNull(richiedente.getServizio(), "DA.V02", "Servizio richiedente non valorizzato.");
		checkNotNull(richiedente.getServizio().getIdRequest(), "DA.V01", "id request servizio richiedente non valorizzato.");
		checkNotNull(richiedente.getServizio().getCodice(), "DA.V01", "Codice servizio richiedente non valorizzato.");
	}
	
	protected void saveLogAudit(REQ req) {
		
		checkNotNull(req, "DA.V02", "ServiceRequest non valorizzata.");
		checkRichiedente(req.getRichiedente());
		
		CsiLogAudit cisLogAudit = new CsiLogAudit();
		
		cisLogAudit.setDataOra(new Date());
		cisLogAudit.setIdrequest(req.getRichiedente().getServizio().getIdRequest());
		cisLogAudit.setIdApp(req.getRichiedente().getServizio().getCodice());
		cisLogAudit.setIpAddress("");
		cisLogAudit.setOperazione("operazione");
		cisLogAudit.setOggOper("oggoper");
		cisLogAudit.setUtente(req.getRichiedente().getCodiceFiscale());
		cisLogAudit.setUuid(UUID.randomUUID());
		
		csiLogAuditRepository.inserisciLog(cisLogAudit);
	}

}
