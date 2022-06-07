/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.delegheboweb.business.delegheboweb;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import io.swagger.annotations.ApiParam;
import it.csi.deleghe.delegheboweb.dto.delegheboweb.Dichiarazione;
import it.csi.deleghe.delegheboweb.dto.delegheboweb.DocumentoTipo;
import it.csi.deleghe.delegheboweb.dto.delegheboweb.Errore;
import it.csi.deleghe.delegheboweb.dto.delegheboweb.InformativaConsensi;
import it.csi.deleghe.delegheboweb.dto.delegheboweb.StatoDelega;
import it.csi.deleghe.delegheboweb.dto.delegheboweb.StatoDichiarazione;
import it.csi.deleghe.delegheboweb.dto.delegheboweb.TipoDelega;
import it.csi.deleghe.delegheboweb.dto.delegheboweb.TipoDichiarazione;

@Path("/codifiche")
@io.swagger.annotations.Api(description = "the servizio-attivo API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyEapServerCodegen", date = "2020-03-11T13:27:09.569Z")
public interface DelegheCodificheApi {
  
   
   @GET
   @Path("/statiDichiarazione")
   @Produces({"application/json"})
   @io.swagger.annotations.ApiOperation(value = "", notes = "Legge gli stati possibili della dichiarazione", response = StatoDichiarazione.class, responseContainer = "List", tags = {"Legge stati della dichiarazione",})
   @io.swagger.annotations.ApiResponses(value = {
         @io.swagger.annotations.ApiResponse(code = 200, message = "OK", response = StatoDichiarazione.class, responseContainer = "List"),

         @io.swagger.annotations.ApiResponse(code = 400, message = "bad request", response = Errore.class),

         @io.swagger.annotations.ApiResponse(code = 404, message = "Risorsa non trovata", response = Errore.class),

         @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = Errore.class)})
   public Response statiDichiarazioneGet(@ApiParam(value = "", required = true) @HeaderParam("X-Request-ID") String xRequestID, @ApiParam(value = "", required = true) @HeaderParam("X-Codice-Servizio") String xCodiceServizio, @Context SecurityContext securityContext, @Context HttpServletRequest request);

   
   
   @GET
   @Path("/statiDelega")
   @Produces({"application/json"})
   @io.swagger.annotations.ApiOperation(value = "", notes = "Legge gli stati possibili della delega", response = StatoDelega.class, responseContainer = "List", tags = {"Legge stati della delega",})
   @io.swagger.annotations.ApiResponses(value = {
         @io.swagger.annotations.ApiResponse(code = 200, message = "OK", response = StatoDelega.class, responseContainer = "List"),

         @io.swagger.annotations.ApiResponse(code = 400, message = "bad request", response = Errore.class),

         @io.swagger.annotations.ApiResponse(code = 404, message = "Risorsa non trovata", response = Errore.class),

         @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = Errore.class)})
   public Response statiDelegaGet(@ApiParam(value = "", required = true) @HeaderParam("X-Request-ID") String xRequestID, @ApiParam(value = "", required = true) @HeaderParam("X-Codice-Servizio") String xCodiceServizio, @Context SecurityContext securityContext, @Context HttpServletRequest request);
   
   
   
   @GET
   @Path("/tipiDelega")
   @Produces({"application/json"})
   @io.swagger.annotations.ApiOperation(value = "", notes = "Legge i tipi possibili della delega", response = TipoDelega.class, responseContainer = "List", tags = {"Legge le tipologie di delega",})
   @io.swagger.annotations.ApiResponses(value = {
         @io.swagger.annotations.ApiResponse(code = 200, message = "OK", response = TipoDelega.class, responseContainer = "List"),

         @io.swagger.annotations.ApiResponse(code = 400, message = "bad request", response = Errore.class),

         @io.swagger.annotations.ApiResponse(code = 404, message = "Risorsa non trovata", response = Errore.class),

         @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = Errore.class)})
   public Response tipiDelegaGet(@ApiParam(value = "", required = true) @HeaderParam("X-Request-ID") String xRequestID, @ApiParam(value = "", required = true) @HeaderParam("X-Codice-Servizio") String xCodiceServizio, @Context SecurityContext securityContext, @Context HttpServletRequest request);
   

   @GET
   @Path("/tipoDichiarazione")
   @Produces({"application/json"})
   @io.swagger.annotations.ApiOperation(value = "", notes = "Legge i tipi possibili della dichiarazione", response = TipoDichiarazione.class, responseContainer = "List", tags = {"Legge le tipologie di dichiarazione",})
   @io.swagger.annotations.ApiResponses(value = {
         @io.swagger.annotations.ApiResponse(code = 200, message = "OK", response = TipoDichiarazione.class, responseContainer = "List"),

         @io.swagger.annotations.ApiResponse(code = 400, message = "bad request", response = Errore.class),

         @io.swagger.annotations.ApiResponse(code = 404, message = "Risorsa non trovata", response = Errore.class),

         @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = Errore.class)})
   public Response tipiDichiarazioneGet(@ApiParam(value = "", required = true) @HeaderParam("X-Request-ID") String xRequestID, @ApiParam(value = "", required = true) @HeaderParam("X-Codice-Servizio") String xCodiceServizio, @Context SecurityContext securityContext, @Context HttpServletRequest request);

   @GET
   @Path("/descrioneDocumento")
   @Produces({"application/json"})
   @io.swagger.annotations.ApiOperation(value = "", notes = "Legge i tipi possibili di documenti allegabili", response = DocumentoTipo.class, responseContainer = "List", tags = {"Legge i tipi possibili di documenti allegabili",})
   @io.swagger.annotations.ApiResponses(value = {
         @io.swagger.annotations.ApiResponse(code = 200, message = "OK", response = DocumentoTipo.class, responseContainer = "List"),

         @io.swagger.annotations.ApiResponse(code = 400, message = "bad request", response = Errore.class),

         @io.swagger.annotations.ApiResponse(code = 404, message = "Risorsa non trovata", response = Errore.class),

         @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = Errore.class)})
   public Response descrizioneDocumentoGet(@ApiParam(value = "", required = true) @HeaderParam("X-Request-ID") String xRequestID, @ApiParam(value = "", required = true) @HeaderParam("X-Codice-Servizio") String xCodiceServizio, @Context SecurityContext securityContext, @Context HttpServletRequest request);

   @GET
   @Path("/informativaConsensi")
   @Produces({"application/json"})
   @io.swagger.annotations.ApiOperation(value = "", notes = "Legge informativa consensi", response = InformativaConsensi.class, responseContainer = "List", tags = {"Legge informativa consensi",})
   @io.swagger.annotations.ApiResponses(value = {
         @io.swagger.annotations.ApiResponse(code = 200, message = "OK", response = InformativaConsensi.class, responseContainer = "List"),

         @io.swagger.annotations.ApiResponse(code = 400, message = "bad request", response = Errore.class),

         @io.swagger.annotations.ApiResponse(code = 404, message = "Risorsa non trovata", response = Errore.class),

         @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = Errore.class)})
   public Response getInformativaConsensiList(@ApiParam(value = "", required = true) @HeaderParam("X-Request-ID") String xRequestID, @ApiParam(value = "", required = true) @HeaderParam("X-Codice-Servizio") String xCodiceServizio, @Context SecurityContext securityContext, @Context HttpServletRequest request);

}
