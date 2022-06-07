/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.delegheboweb.business.delegheboweb.impl.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import it.csi.deleghe.deleghebe.ws.DelegheCodificheService;
import it.csi.deleghe.deleghebe.ws.model.DocumentoTipo;
import it.csi.deleghe.deleghebe.ws.model.InformativaConsensi;
import it.csi.deleghe.deleghebe.ws.model.Richiedente;
import it.csi.deleghe.deleghebe.ws.model.StatoDelega;
import it.csi.deleghe.deleghebe.ws.model.StatoDichiarazione;
import it.csi.deleghe.deleghebe.ws.model.TipoDelega;
import it.csi.deleghe.deleghebe.ws.model.TipoDichiarazione;
import it.csi.deleghe.deleghebe.ws.msg.LeggiDescrizioneDocumento;
import it.csi.deleghe.deleghebe.ws.msg.LeggiDescrizioneDocumentoResponse;
import it.csi.deleghe.deleghebe.ws.msg.LeggiInformativaConsensi;
import it.csi.deleghe.deleghebe.ws.msg.LeggiInformativaConsensiResponse;
import it.csi.deleghe.deleghebe.ws.msg.LeggiStatiDelega;
import it.csi.deleghe.deleghebe.ws.msg.LeggiStatiDelegaResponse;
import it.csi.deleghe.deleghebe.ws.msg.LeggiStatiDichiarazione;
import it.csi.deleghe.deleghebe.ws.msg.LeggiStatiDichiarazioneResponse;
import it.csi.deleghe.deleghebe.ws.msg.LeggiTipiDelega;
import it.csi.deleghe.deleghebe.ws.msg.LeggiTipiDelegaResponse;
import it.csi.deleghe.deleghebe.ws.msg.LeggiTipiDichiarazione;
import it.csi.deleghe.deleghebe.ws.msg.LeggiTipiDichiarazioneResponse;



@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CodificheService extends AbstractDelgheBEService {

   @Autowired
   private DelegheCodificheService delegheCodificheService;

   public Response getStatiDichiarazioneList(String xRequestID, String xCodiceServizio, SecurityContext securityContext, HttpServletRequest request) {
	  LeggiStatiDichiarazione req = new LeggiStatiDichiarazione();
      Richiedente richiedente = new Richiedente(xRequestID, xCodiceServizio, getCodiceFiscale(request));
      req.setRichiedente(richiedente);

      LeggiStatiDichiarazioneResponse res = this.delegheCodificheService.leggiStatiDichiarazioneService(req);
      List<StatoDichiarazione> elencoStatiDichiarazione = res.getElencoStatoDichiarazione();

      if (elencoStatiDichiarazione == null || elencoStatiDichiarazione.isEmpty()) {
         return getError(res);
      } 

      List<it.csi.deleghe.delegheboweb.dto.delegheboweb.StatoDichiarazione> result = new ArrayList<>();

      for (StatoDichiarazione statoDichiarazioneBe : elencoStatiDichiarazione) {
    	  it.csi.deleghe.delegheboweb.dto.delegheboweb.StatoDichiarazione statoDichiarazione = new it.csi.deleghe.delegheboweb.dto.delegheboweb.StatoDichiarazione();
		
    	  statoDichiarazione.setCodice(statoDichiarazioneBe.getCodice());
    	  statoDichiarazione.setDescrizione(statoDichiarazioneBe.getDescrizione());
    	  
    	  result.add(statoDichiarazione);
      }

      return Response.ok(result).build();
   }

   
   public Response getStatiDelegaList(String xRequestID, String xCodiceServizio, SecurityContext securityContext, HttpServletRequest request) {
	  LeggiStatiDelega req = new LeggiStatiDelega();
      Richiedente richiedente = new Richiedente(xRequestID, xCodiceServizio, getCodiceFiscale(request));
      req.setRichiedente(richiedente);

      LeggiStatiDelegaResponse res = this.delegheCodificheService.leggiStatiDelegaService(req);
      List<StatoDelega> elencoStatiDelega = res.getElencoStatoDelega();

      if (elencoStatiDelega == null || elencoStatiDelega.isEmpty()) {
         return getError(res);
      } 

      List<it.csi.deleghe.delegheboweb.dto.delegheboweb.StatoDelega> result = new ArrayList<>();

      for (StatoDelega statoDelegaBe : elencoStatiDelega) {
    	  it.csi.deleghe.delegheboweb.dto.delegheboweb.StatoDelega statoDelega = new it.csi.deleghe.delegheboweb.dto.delegheboweb.StatoDelega();
		
    	  statoDelega.setCodice(statoDelegaBe.getCodice());
    	  statoDelega.setDescrizione(statoDelegaBe.getDescrizione());
    	  
    	  result.add(statoDelega);
      }

      return Response.ok(result).build();
   }
   
   public Response getDescrizioneDocumentoList(String xRequestID, String xCodiceServizio, SecurityContext securityContext, HttpServletRequest request) {
		  LeggiDescrizioneDocumento req = new LeggiDescrizioneDocumento();
	      Richiedente richiedente = new Richiedente(xRequestID, xCodiceServizio, getCodiceFiscale(request));
	      req.setRichiedente(richiedente);

	      LeggiDescrizioneDocumentoResponse res = this.delegheCodificheService.leggiDescrizioneDocumentoService(req);
	      List<DocumentoTipo> elencoDocumentoTipo = res.getElencoDocumentoTipo();

	      if (elencoDocumentoTipo == null || elencoDocumentoTipo.isEmpty()) {
	         return getError(res);
	      } 

	      List<it.csi.deleghe.delegheboweb.dto.delegheboweb.DocumentoTipo> result = new ArrayList<>();

	      for (DocumentoTipo documentoTipoBe : elencoDocumentoTipo) {
	    	  it.csi.deleghe.delegheboweb.dto.delegheboweb.DocumentoTipo documentoTipo = new it.csi.deleghe.delegheboweb.dto.delegheboweb.DocumentoTipo();
			
	    	  documentoTipo.setCodice(documentoTipoBe.getCodice());
	    	  documentoTipo.setDescrizione(documentoTipoBe.getDescrizione());
	    	  
	    	  result.add(documentoTipo);
	      }

	      return Response.ok(result).build();
	   }


 
   public Response getTipiDichiarazioneList(String xRequestID, String xCodiceServizio, SecurityContext securityContext, HttpServletRequest request) {
	  LeggiTipiDichiarazione req = new LeggiTipiDichiarazione();
      Richiedente richiedente = new Richiedente(xRequestID, xCodiceServizio, getCodiceFiscale(request));
      req.setRichiedente(richiedente);

      LeggiTipiDichiarazioneResponse res = this.delegheCodificheService.leggiTipiDichiarazioneService(req);
      List<TipoDichiarazione> elencoTipiDichiarazione = res.getElencoTipoDichiarazione();

      if (elencoTipiDichiarazione == null || elencoTipiDichiarazione.isEmpty()) {
         return getError(res);
      } 

      List<it.csi.deleghe.delegheboweb.dto.delegheboweb.TipoDichiarazione> result = new ArrayList<>();

      for (TipoDichiarazione tipoDichiarazioneBe : elencoTipiDichiarazione) {
    	  it.csi.deleghe.delegheboweb.dto.delegheboweb.TipoDichiarazione tipoDichiarazione = new it.csi.deleghe.delegheboweb.dto.delegheboweb.TipoDichiarazione();
		
    	  tipoDichiarazione.setCodice(tipoDichiarazioneBe.getCodice());
    	  tipoDichiarazione.setDescrizione(tipoDichiarazioneBe.getDescrizione());
    	  
    	  result.add(tipoDichiarazione);
      }

      return Response.ok(result).build();
   }
   
   
   public Response getTipiDelegaList(String xRequestID, String xCodiceServizio, SecurityContext securityContext, HttpServletRequest request) {
	  LeggiTipiDelega req = new LeggiTipiDelega();
      Richiedente richiedente = new Richiedente(xRequestID, xCodiceServizio, getCodiceFiscale(request));
      req.setRichiedente(richiedente);

      LeggiTipiDelegaResponse res = this.delegheCodificheService.leggiTipiDelegaService(req);
      List<TipoDelega> elencoTipiDelega = res.getElencoTipoDelega();

      if (elencoTipiDelega == null || elencoTipiDelega.isEmpty()) {
         return getError(res);
      } 

      List<it.csi.deleghe.delegheboweb.dto.delegheboweb.TipoDelega> result = new ArrayList<>();

      for (TipoDelega tipiDelegaBe : elencoTipiDelega) {
    	  it.csi.deleghe.delegheboweb.dto.delegheboweb.TipoDelega tipoDelega = new it.csi.deleghe.delegheboweb.dto.delegheboweb.TipoDelega();
		
    	  tipoDelega.setCodice(tipiDelegaBe.getCodice());
    	  tipoDelega.setDescrizione(tipiDelegaBe.getDescrizione());
    	  
    	  result.add(tipoDelega);
      }

      return Response.ok(result).build();
   }
   
   public Response getInformativaConsensiList(String xRequestID, String xCodiceServizio, SecurityContext securityContext, HttpServletRequest request) {
	   	  LeggiInformativaConsensi req = new LeggiInformativaConsensi();
	      Richiedente richiedente = new Richiedente(xRequestID, xCodiceServizio, getCodiceFiscale(request));
	      req.setRichiedente(richiedente);

	      LeggiInformativaConsensiResponse res = this.delegheCodificheService.leggiInformativaConsensoService(req);
	      List<InformativaConsensi> elencoInformativaConsensi = res.getElencoInformativaConsensi();

	      if (elencoInformativaConsensi == null || elencoInformativaConsensi.isEmpty()) {
	         return getError(res);
	      } 

	      List<it.csi.deleghe.delegheboweb.dto.delegheboweb.InformativaConsensi> result = new ArrayList<>();

	      for (InformativaConsensi informativaConsensiBe : elencoInformativaConsensi) {
	    	  it.csi.deleghe.delegheboweb.dto.delegheboweb.InformativaConsensi informativaConsensi = new it.csi.deleghe.delegheboweb.dto.delegheboweb.InformativaConsensi();
			
	    	  informativaConsensi.setDescInformativa(informativaConsensiBe.getDescInformativa());
	    	  informativaConsensi.setHtmlInformativa(informativaConsensiBe.getHtmlInformativa());
	    	  informativaConsensi.setPdfInformativa(informativaConsensiBe.getPdfInformativa());
	    	  
	    	  result.add(informativaConsensi);
	      }

	      return Response.ok(result).build();
	   }
}
