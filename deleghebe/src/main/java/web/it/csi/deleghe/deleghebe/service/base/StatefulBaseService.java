/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service.base;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.inject.Inject;

import it.csi.deleghe.deleghebe.service.base.exception.ServiceException;
import it.csi.deleghe.deleghebe.util.LogUtil;
import it.csi.deleghe.deleghebe.util.LogUtil.ToLog;
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
public abstract class StatefulBaseService<REQ extends ServiceRequest,RES extends ServiceResponse> {
	
	@Inject
	protected LogUtil log;
	
	/**
	 * Parametri di input del servizio
	 */
	protected REQ req;
	
	/**
	 * Parametri di output del servizio
	 */
	protected RES res;
	
	/**
	 * Esecuzione del servizo.
	 * 
	 * @param serviceRequest
	 * @return
	 */
	public RES executeService(REQ serviceRequest){
		final String METHOD_NAME = "executeService";
		long time = System.currentTimeMillis();
		log.debug(METHOD_NAME, "Start service: %s", this.getClass().getSimpleName());
		log.logXmlTypeObject(req, "Service Request");
		
		this.req = serviceRequest;
		this.res = instantiateNewRes();
		
		try {
			execute();
		} catch (Exception e) {
			handleException(e);
		} finally {
			log.logXmlTypeObject(res, "Service Response");
			log.debug(METHOD_NAME, "End service: %s. Elapsed: %s ms.", this.getClass().getSimpleName(),
					(ToLog) () -> (System.currentTimeMillis() - time));
		}
		return res;
	}
	
	protected void handleException(Exception e) {
		final String METHOD_NAME = "handleException";
		if (this.res == null) {
			this.res = instantiateNewRes();
		}
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
			log.error(METHOD_NAME, "Generic exception in service: %s", e, this.getClass().getSimpleName());
			String descrizione = e.getMessage() + (e.getCause()!=null?" [" + e.getCause().getMessage() + "]":"");
			res.addErrore("ERRORE", descrizione);
		}
	}
	

	/**
	 * Implementa l'esecuzione della bussiness logic del servizio.
	 * E' demandato all'implementazione di questo metodo 
	 * l'impostazione dell'esito del servizio
	 * Ad esempio in caso di successo bisognarà eseguire res.setEsito(Esito.SUCCESSO);
	 *  
	 */
	protected abstract void execute();
	

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

}
