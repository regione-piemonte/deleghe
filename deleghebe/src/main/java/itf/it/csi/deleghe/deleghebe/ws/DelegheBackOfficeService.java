/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.deleghe.deleghebe.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import it.csi.deleghe.deleghebe.ws.msg.AggiornaServizio;
import it.csi.deleghe.deleghebe.ws.msg.AggiornaServizioResponse;
import it.csi.deleghe.deleghebe.ws.msg.BatchDeleghe;
import it.csi.deleghe.deleghebe.ws.msg.BatchDelegheResponse;
import it.csi.deleghe.deleghebe.ws.msg.GetDettaglioDelega;
import it.csi.deleghe.deleghebe.ws.msg.GetDettaglioDelegaResponse;
import it.csi.deleghe.deleghebe.ws.msg.GetDettaglioDichiarazione;
import it.csi.deleghe.deleghebe.ws.msg.GetDettaglioDichiarazioneResponse;
import it.csi.deleghe.deleghebe.ws.msg.InserisciServizio;
import it.csi.deleghe.deleghebe.ws.msg.InserisciServizioResponse;
import it.csi.deleghe.deleghebe.ws.msg.IsAlive;
import it.csi.deleghe.deleghebe.ws.msg.IsAliveResponse;
import it.csi.deleghe.deleghebe.ws.msg.LeggiServizi;
import it.csi.deleghe.deleghebe.ws.msg.LeggiServiziResponse;
import it.csi.deleghe.deleghebe.ws.msg.RicercaDelega;
import it.csi.deleghe.deleghebe.ws.msg.RicercaDelegaResponse;
import it.csi.deleghe.deleghebe.ws.msg.RicercaServizi;
import it.csi.deleghe.deleghebe.ws.msg.RicercaServiziResponse;
import it.csi.deleghe.deleghebe.ws.msg.SaveFiltri;
import it.csi.deleghe.deleghebe.ws.msg.SaveFiltriResponse;


/**
 * JAX-WS RI 2.2.9-b130926.1035
 * 
 */
@WebService(name = "DelegheBackOfficeService", targetNamespace = "http://deleghebe.csi.it/")
public interface DelegheBackOfficeService {

    @WebMethod
    public @WebResult(name="isAliveResponse")  IsAliveResponse isAliveService(@WebParam(name = "isAlive")  IsAlive req);



    @WebMethod
    public @WebResult(name="ricercaServiziResponse") RicercaServiziResponse ricercaServiziService(@WebParam(name = "getServizi") RicercaServizi req);
    
    @WebMethod
    public @WebResult(name="inserisciServizioResponse") InserisciServizioResponse inserisciServizioService(@WebParam(name = "inserisciServizio") InserisciServizio req);
    
    @WebMethod
    public @WebResult(name="aggiornaServizioResponse") AggiornaServizioResponse aggiornaServizioService(@WebParam(name = "aggiornaServizio") AggiornaServizio req);



   
    @WebMethod
    public @WebResult(name="ricercaDelegaResponse") RicercaDelegaResponse ricercaDelegaService(@WebParam(name = "ricercaDelega") RicercaDelega req);
    
    @WebMethod
    public @WebResult(name="getDettaglioDelegaResponse") GetDettaglioDelegaResponse getDettaglioDelegaService(@WebParam(name = "getDettaglioDelega") GetDettaglioDelega req);
   
    @WebMethod
    public @WebResult(name="getDettaglioDichiarazioneResponse") GetDettaglioDichiarazioneResponse getDettaglioDichiarazioneService(@WebParam(name = "getDettaglioDichiarazione") GetDettaglioDichiarazione req);
   
    @WebMethod
    public @WebResult(name="saveFiltriResponse") SaveFiltriResponse saveFiltriService(@WebParam(name = "saveFiltri") SaveFiltri req);
   
    


    @WebMethod
    public @WebResult(name="batchDelegheResponse") BatchDelegheResponse batchDelegheService(@WebParam(name = "batchDeleghe") BatchDeleghe req);

    @WebMethod
    public @WebResult(name="batchDichiarazioniResponse") BatchDelegheResponse batchDichiarazioniService(@WebParam(name = "batchDichiarazioni") BatchDeleghe req);

    @WebMethod
    public @WebResult(name="batchDelegheResponse") BatchDelegheResponse batchDelegheMinoriService(@WebParam(name = "batchDeleghe") BatchDeleghe req);
    

    @WebMethod
    public @WebResult(name="leggiServiziResponse")  LeggiServiziResponse leggiServiziService(@WebParam(name = "leggiServizi")  LeggiServizi req);

}
