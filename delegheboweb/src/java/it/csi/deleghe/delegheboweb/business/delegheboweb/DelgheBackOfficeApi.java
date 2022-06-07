/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.delegheboweb.business.delegheboweb;

import io.swagger.annotations.ApiParam;

import it.csi.deleghe.delegheboweb.dto.delegheboweb.Servizio;
import it.csi.deleghe.delegheboweb.dto.delegheboweb.Delega;
import it.csi.deleghe.delegheboweb.dto.delegheboweb.Dichiarazione;
import it.csi.deleghe.delegheboweb.dto.delegheboweb.DocumentoFile;
import it.csi.deleghe.delegheboweb.dto.delegheboweb.Errore;
import it.csi.deleghe.delegheboweb.dto.delegheboweb.InfoServizio;
import it.csi.deleghe.delegheboweb.dto.delegheboweb.LoginOperatore;
import it.csi.deleghe.delegheboweb.dto.delegheboweb.Operatore;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("/back-office")
@io.swagger.annotations.Api(description = "the servizio-attivo API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyEapServerCodegen", date = "2019-12-21T13:27:09.569Z")
public interface DelgheBackOfficeApi {

   @GET
   @Path("/services")
   @Produces({"application/json"})
   @io.swagger.annotations.ApiOperation(value = "", notes = "Serve per verificare se il servizio risponde o meno ", response = InfoServizio.class, tags = {})
   @io.swagger.annotations.ApiResponses(value = {
         @io.swagger.annotations.ApiResponse(code = 200, message = "OK", response = InfoServizio.class)})
   public Response servizioGet(@ApiParam(value = "", required = true) @HeaderParam("X-Request-ID") String xRequestID, @ApiParam(value = "", required = true) @HeaderParam("X-Codice-Servizio") String xCodiceServizio, @Context SecurityContext securityContext, @Context HttpServletRequest request);

   @GET
   @Path("/test-token")
   @Produces({"application/json"})
   public Response testToken(@Context HttpServletRequest request);
   
   @GET
   @Path("/deleghebo")
   @Produces({"application/json"})
   @io.swagger.annotations.ApiOperation(value = "", notes = "Ricerca del backoffice di deleghe di un cittadino", response = Delega.class, responseContainer = "List", tags = {"Gestione Deleghe",})
   @io.swagger.annotations.ApiResponses(value = {
         @io.swagger.annotations.ApiResponse(code = 200, message = "Operazione eseguita con successo ", response = Delega.class, responseContainer = "List"),

         @io.swagger.annotations.ApiResponse(code = 400, message = "bad request", response = Errore.class),

         @io.swagger.annotations.ApiResponse(code = 401, message = "Utente non autorizzato a compiere l'operazione", response = Errore.class),

         @io.swagger.annotations.ApiResponse(code = 404, message = "Risorsa non trovata", response = Errore.class),

         @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = Errore.class)})
   public Response delegheBoGet( @ApiParam(value = "", required = true) @HeaderParam("X-Request-ID") String xRequestID, @ApiParam(value = "", required = true) @HeaderParam("X-Codice-Servizio") String xCodiceServizio, @QueryParam("filter") String filter, @Context SecurityContext securityContext, @Context HttpServletRequest request);

   @GET
   @Path("/legame")
   @Produces({"application/json"})
   @io.swagger.annotations.ApiOperation(value = "", notes = "Ricerca del legame delgante delegato", response = Delega.class, responseContainer = "List", tags = {"Gestione Deleghe",})
   @io.swagger.annotations.ApiResponses(value = {
         @io.swagger.annotations.ApiResponse(code = 200, message = "Operazione eseguita con successo ", response = Delega.class, responseContainer = "List"),

         @io.swagger.annotations.ApiResponse(code = 400, message = "bad request", response = Errore.class),

         @io.swagger.annotations.ApiResponse(code = 401, message = "Utente non autorizzato a compiere l'operazione", response = Errore.class),

         @io.swagger.annotations.ApiResponse(code = 404, message = "Risorsa non trovata", response = Errore.class),

         @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = Errore.class)})
   public Response getLegameDelegatoDelegante( @ApiParam(value = "", required = true) @HeaderParam("X-Request-ID") String xRequestID, @ApiParam(value = "", required = true) @HeaderParam("X-Codice-Servizio") String xCodiceServizio, @QueryParam("filter") String filter, @Context SecurityContext securityContext, @Context HttpServletRequest request);

   @GET
   @Path("/delegabo")
   @Produces({"application/json"})
   @io.swagger.annotations.ApiOperation(value = "", notes = "Get dettaglio delega", response = Delega.class, responseContainer = "List", tags = {"Gestione Deleghe",})
   @io.swagger.annotations.ApiResponses(value = {
         @io.swagger.annotations.ApiResponse(code = 200, message = "Operazione eseguita con successo ", response = Delega.class, responseContainer = "List"),

         @io.swagger.annotations.ApiResponse(code = 400, message = "bad request", response = Errore.class),

         @io.swagger.annotations.ApiResponse(code = 401, message = "Utente non autorizzato a compiere l'operazione", response = Errore.class),

         @io.swagger.annotations.ApiResponse(code = 404, message = "Risorsa non trovata", response = Errore.class),

         @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = Errore.class)})
   public Response dettaglioDelegaBoGet( @ApiParam(value = "", required = true) @HeaderParam("X-Request-ID") String xRequestID, @ApiParam(value = "", required = true) @HeaderParam("X-Codice-Servizio") String xCodiceServizio, @QueryParam("filter") String filter, @Context SecurityContext securityContext, @Context HttpServletRequest request); 

   @GET
   @Path("/validazione")
   @Produces({"application/json"})
   @io.swagger.annotations.ApiOperation(value = "", notes = "Ricerca della data validazione", response = Servizio.class, responseContainer = "List", tags = {"Gestione Data Validazione",})
   @io.swagger.annotations.ApiResponses(value = {
         @io.swagger.annotations.ApiResponse(code = 200, message = "Operazione eseguita con successo ", response = Servizio.class, responseContainer = "List"),

         @io.swagger.annotations.ApiResponse(code = 400, message = "bad request", response = Errore.class),

         @io.swagger.annotations.ApiResponse(code = 401, message = "Utente non autorizzato a compiere l'operazione", response = Errore.class),

         @io.swagger.annotations.ApiResponse(code = 404, message = "Risorsa non trovata", response = Errore.class),

         @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = Errore.class)})
   public Response getDatavalidazione( @ApiParam(value = "", required = true) @HeaderParam("X-Request-ID") String xRequestID, @ApiParam(value = "", required = true) @HeaderParam("X-Codice-Servizio") String xCodiceServizio, @QueryParam("filter") String filter, @Context SecurityContext securityContext, @Context HttpServletRequest request);
   
   @GET
   @Path("/identitaOperatore")
   @Produces({"application/json"})
   @io.swagger.annotations.ApiOperation(value = "", notes = "Ricerca dati login operatore", response = LoginOperatore.class, responseContainer = "List", tags = {"Ricerca dati login operatore",})
   @io.swagger.annotations.ApiResponses(value = {
         @io.swagger.annotations.ApiResponse(code = 200, message = "Operazione eseguita con successo ", response = LoginOperatore.class, responseContainer = "List"),

         @io.swagger.annotations.ApiResponse(code = 400, message = "bad request", response = Errore.class),

         @io.swagger.annotations.ApiResponse(code = 401, message = "Utente non autorizzato a compiere l'operazione", response = Errore.class),

         @io.swagger.annotations.ApiResponse(code = 404, message = "Risorsa non trovata", response = Errore.class),

         @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = Errore.class)})
   public Response getIdentitaOperatore( @ApiParam(value = "", required = true) @HeaderParam("X-Request-ID") String xRequestID, @ApiParam(value = "", required = true) @HeaderParam("X-Codice-Servizio") String xCodiceServizio, @QueryParam("filter") String filter, @Context SecurityContext securityContext, @Context HttpServletRequest request);
   
   @GET
   @Path("/rimuoviSessione")
   @Produces({"application/json"})
   @io.swagger.annotations.ApiOperation(value = "", notes = "Rimuove dati login operatore", response = LoginOperatore.class, responseContainer = "List", tags = {"Ricerca dati login operatore",})
   @io.swagger.annotations.ApiResponses(value = {
         @io.swagger.annotations.ApiResponse(code = 200, message = "Operazione eseguita con successo ", response = LoginOperatore.class, responseContainer = "List"),

         @io.swagger.annotations.ApiResponse(code = 400, message = "bad request", response = Errore.class),

         @io.swagger.annotations.ApiResponse(code = 401, message = "Utente non autorizzato a compiere l'operazione", response = Errore.class),

         @io.swagger.annotations.ApiResponse(code = 404, message = "Risorsa non trovata", response = Errore.class),

         @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = Errore.class)})
   public Response removeFromSession( @ApiParam(value = "", required = true) @HeaderParam("X-Request-ID") String xRequestID, @ApiParam(value = "", required = true) @HeaderParam("X-Codice-Servizio") String xCodiceServizio, @QueryParam("filter") String filter, @Context SecurityContext securityContext, @Context HttpServletRequest request);
   
   @GET
   @Path("/fileDocumento")
   @Produces({"application/json"})
   @io.swagger.annotations.ApiOperation(value = "", notes = "Ricerca file documento", response = DocumentoFile.class, responseContainer = "List", tags = {"Ricerca file documento",})
   @io.swagger.annotations.ApiResponses(value = {
         @io.swagger.annotations.ApiResponse(code = 200, message = "Operazione eseguita con successo ", response = DocumentoFile.class, responseContainer = "List"),

         @io.swagger.annotations.ApiResponse(code = 400, message = "bad request", response = Errore.class),

         @io.swagger.annotations.ApiResponse(code = 401, message = "Utente non autorizzato a compiere l'operazione", response = Errore.class),

         @io.swagger.annotations.ApiResponse(code = 404, message = "Risorsa non trovata", response = Errore.class),

         @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = Errore.class)})
   public Response ricercaFileDocumentoPerId( @ApiParam(value = "", required = true) @HeaderParam("X-Request-ID") String xRequestID, @ApiParam(value = "", required = true) @HeaderParam("X-Codice-Servizio") String xCodiceServizio, @QueryParam("fileId") Integer fileId, @Context SecurityContext securityContext, @Context HttpServletRequest request);
   
   @GET
   @Path("/operatoreValido")
   @Produces({"application/json"})
   @io.swagger.annotations.ApiOperation(value = "", notes = "Ricerca della asl dell'operatore valido", response = Operatore.class, responseContainer = "List", tags = {"Ricerca della asl dell'operatore valido",})
   @io.swagger.annotations.ApiResponses(value = {
         @io.swagger.annotations.ApiResponse(code = 200, message = "Operazione eseguita con successo ", response = Operatore.class, responseContainer = "List"),

         @io.swagger.annotations.ApiResponse(code = 400, message = "bad request", response = Errore.class),

         @io.swagger.annotations.ApiResponse(code = 401, message = "Utente non autorizzato a compiere l'operazione", response = Errore.class),

         @io.swagger.annotations.ApiResponse(code = 404, message = "Risorsa non trovata", response = Errore.class),

         @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = Errore.class)})
   public Response ricercaOperatoreValidoConAsl( @ApiParam(value = "", required = true) @HeaderParam("X-Request-ID") String xRequestID, @ApiParam(value = "", required = true) @HeaderParam("X-Codice-Servizio") String xCodiceServizio, @QueryParam("codiceFiscale") String codiceFiscale, @Context SecurityContext securityContext, @Context HttpServletRequest request);
}
