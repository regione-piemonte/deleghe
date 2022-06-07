/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.delegheboweb.business.delegheboweb;

import io.swagger.annotations.ApiParam;
import it.csi.deleghe.delegheboweb.dto.delegheboweb.Cittadino;
import it.csi.deleghe.delegheboweb.dto.delegheboweb.CreateDichiarazione;
import it.csi.deleghe.delegheboweb.dto.delegheboweb.Delega;
import it.csi.deleghe.delegheboweb.dto.delegheboweb.Dichiarazione;
import it.csi.deleghe.delegheboweb.dto.delegheboweb.Errore;
import it.csi.deleghe.delegheboweb.dto.delegheboweb.TokenResponse;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("/cittadini")
@io.swagger.annotations.Api(description = "the cittadini API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyEapServerCodegen", date = "2019-12-21T13:27:09.569Z")
public interface CittadiniApi {

   @GET
   @Path("/find")
   @Produces({"application/json"})
   @io.swagger.annotations.ApiOperation(value = "", notes = "Ricerca di dichiarazioni di un cittadino", response = Dichiarazione.class, responseContainer = "List", tags = {"Gestione Dichiarazioni",})
   @io.swagger.annotations.ApiResponses(value = {
         @io.swagger.annotations.ApiResponse(code = 200, message = "Operazione eseguita con successo ", response = Dichiarazione.class, responseContainer = "List"),

         @io.swagger.annotations.ApiResponse(code = 400, message = "bad request", response = Errore.class),

         @io.swagger.annotations.ApiResponse(code = 401, message = "Utente non autorizzato a compiere l'operazione", response = Errore.class),

         @io.swagger.annotations.ApiResponse(code = 404, message = "Risorsa non trovata", response = Errore.class),

         @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = Errore.class)})
   public Response findCittadino( @ApiParam(value = "", required = true) @HeaderParam("X-Request-ID") String xRequestID, @ApiParam(value = "", required = true) @HeaderParam("X-Codice-Servizio") String xCodiceServizio, @QueryParam("filter") String filter, @Context SecurityContext securityContext, @Context HttpServletRequest request);


   @GET
   @Path("/{cf}/dichiarazioni")
   @Produces({"application/json"})
   @io.swagger.annotations.ApiOperation(value = "", notes = "Ricerca di dichiarazioni di un cittadino", response = Dichiarazione.class, responseContainer = "List", tags = {"Gestione Dichiarazioni",})
   @io.swagger.annotations.ApiResponses(value = {
         @io.swagger.annotations.ApiResponse(code = 200, message = "Operazione eseguita con successo ", response = Dichiarazione.class, responseContainer = "List"),

         @io.swagger.annotations.ApiResponse(code = 400, message = "bad request", response = Errore.class),

         @io.swagger.annotations.ApiResponse(code = 401, message = "Utente non autorizzato a compiere l'operazione", response = Errore.class),

         @io.swagger.annotations.ApiResponse(code = 404, message = "Risorsa non trovata", response = Errore.class),

         @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = Errore.class)})
   public Response cittadiniCfDichiarazioniGet( @ApiParam(value = "", required = true) @HeaderParam("X-Request-ID") String xRequestID, @ApiParam(value = "", required = true) @HeaderParam("X-Codice-Servizio") String xCodiceServizio, @PathParam("cf") String cf, @QueryParam("filter") String filter, @Context SecurityContext securityContext, @Context HttpServletRequest request);

   @GET
   @Path("/deleghe")
   @Produces({"application/json"})
   @io.swagger.annotations.ApiOperation(value = "", notes = "Ricerca di dichiarazioni di un cittadino", response = Dichiarazione.class, responseContainer = "List", tags = {"Gestione Dichiarazioni",})
   @io.swagger.annotations.ApiResponses(value = {
         @io.swagger.annotations.ApiResponse(code = 200, message = "Operazione eseguita con successo ", response = Dichiarazione.class, responseContainer = "List"),

         @io.swagger.annotations.ApiResponse(code = 400, message = "bad request", response = Errore.class),

         @io.swagger.annotations.ApiResponse(code = 401, message = "Utente non autorizzato a compiere l'operazione", response = Errore.class),

         @io.swagger.annotations.ApiResponse(code = 404, message = "Risorsa non trovata", response = Errore.class),

         @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = Errore.class)})
   public Response delegheGet( @ApiParam(value = "", required = true) @HeaderParam("X-Request-ID") String xRequestID, @ApiParam(value = "", required = true) @HeaderParam("X-Codice-Servizio") String xCodiceServizio, @QueryParam("filter") String filter, @Context SecurityContext securityContext, @Context HttpServletRequest request);

   @GET
   @Path("/dichiarazioni")
   @Produces({"application/json"})
   @io.swagger.annotations.ApiOperation(value = "", notes = "Ricerca di dichiarazioni di un cittadino", response = Dichiarazione.class, responseContainer = "List", tags = {"Gestione Dichiarazioni",})
   @io.swagger.annotations.ApiResponses(value = {
         @io.swagger.annotations.ApiResponse(code = 200, message = "Operazione eseguita con successo ", response = Dichiarazione.class, responseContainer = "List"),

         @io.swagger.annotations.ApiResponse(code = 400, message = "bad request", response = Errore.class),

         @io.swagger.annotations.ApiResponse(code = 401, message = "Utente non autorizzato a compiere l'operazione", response = Errore.class),

         @io.swagger.annotations.ApiResponse(code = 404, message = "Risorsa non trovata", response = Errore.class),

         @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = Errore.class)})
   public Response cittadiniCfDichiarazioniGet( @ApiParam(value = "", required = true) @HeaderParam("X-Request-ID") String xRequestID, @ApiParam(value = "", required = true) @HeaderParam("X-Codice-Servizio") String xCodiceServizio, @QueryParam("filter") String filter, @Context SecurityContext securityContext, @Context HttpServletRequest request);


   @PUT
   @Path("/{cf}/deleghe")
   @Produces({"application/json"})
   @io.swagger.annotations.ApiOperation(value = "", notes = "Aggiorna una dichiarazione", response = Delega.class, tags = {"Gestione Dichiarazioni",})
   @io.swagger.annotations.ApiResponses(value = {
         @io.swagger.annotations.ApiResponse(code = 200, message = "Operazione eseguita con successo ", response = Delega.class),

         @io.swagger.annotations.ApiResponse(code = 400, message = "bad request", response = Errore.class),

         @io.swagger.annotations.ApiResponse(code = 401, message = "Utente non autorizzato a compiere l'operazione", response = Errore.class),

         @io.swagger.annotations.ApiResponse(code = 404, message = "Risorsa non trovata", response = Errore.class),

         @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = Errore.class)})
   public Response delegaPut( @ApiParam(value = "", required = true) @HeaderParam("X-Request-ID") String xRequestID, @ApiParam(value = "", required = true) @HeaderParam("X-Codice-Servizio") String xCodiceServizio, @PathParam("cf") String cf, @ApiParam(value = "I campi obbligatori sono  * tipo    * codice -> deve essere CONGIUNTA  * stato    * codice -> deve essere DA_COMPLETARE, ATTIVA, REVOCATA, SCADUTA  * modo    * codice -> deve essere valorizzato ON_LINE  * dettagli    * stato      * codice -> deve essere DA_APPROVARE, VALIDA, DA_APPROVARE, REVOCATA_BLOCCO, REVOCATA, SCADUTA  * genitore_tutore_curatore    * codice_fiscale  * ruolo_genitore_curatore    * codice -> deve essere  GENITORE_1 o GENITORE_2  * figlio_tutelato_curato    * codice_fiscale  * ruolo_figlio_tutelato_curato    * codice deve essere passato FIGLIO ") it.csi.deleghe.delegheboweb.dto.delegheboweb.Delega delega, @Context SecurityContext securityContext, @Context HttpServletRequest request);

   @POST
   @Path("/deleghe")
   @Produces({"application/json"})
   @io.swagger.annotations.ApiOperation(value = "", notes = "Aggiorna una dichiarazione", response = Dichiarazione.class, tags = {"Gestione Dichiarazioni",})
   @io.swagger.annotations.ApiResponses(value = {
         @io.swagger.annotations.ApiResponse(code = 200, message = "Operazione eseguita con successo ", response = Dichiarazione.class),

         @io.swagger.annotations.ApiResponse(code = 400, message = "bad request", response = Errore.class),

         @io.swagger.annotations.ApiResponse(code = 401, message = "Utente non autorizzato a compiere l'operazione", response = Errore.class),

         @io.swagger.annotations.ApiResponse(code = 404, message = "Risorsa non trovata", response = Errore.class),

         @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = Errore.class)})
   public Response delegaPost( @ApiParam(value = "", required = true) @HeaderParam("X-Request-ID") String xRequestID, @ApiParam(value = "", required = true) @HeaderParam("X-Codice-Servizio") String xCodiceServizio,@ApiParam(value = "I campi obbligatori sono  * tipo    * codice -> deve essere CONGIUNTA  * stato    * codice -> deve essere DA_COMPLETARE, ATTIVA, REVOCATA, SCADUTA  * modo    * codice -> deve essere valorizzato ON_LINE  * dettagli    * stato      * codice -> deve essere DA_APPROVARE, VALIDA, DA_APPROVARE, REVOCATA_BLOCCO, REVOCATA, SCADUTA  * genitore_tutore_curatore    * codice_fiscale  * ruolo_genitore_curatore    * codice -> deve essere  GENITORE_1 o GENITORE_2  * figlio_tutelato_curato    * codice_fiscale  * ruolo_figlio_tutelato_curato    * codice deve essere passato FIGLIO ") it.csi.deleghe.delegheboweb.dto.delegheboweb.Delega delega, @Context SecurityContext securityContext, @Context HttpServletRequest request);


   @POST
   @Path("/dichiarazione")
   @Produces({"application/json"})
   @io.swagger.annotations.ApiOperation(value = "", notes = "Aggiorna una dichiarazione", response = Dichiarazione.class, tags = {"Gestione Dichiarazioni",})
   @io.swagger.annotations.ApiResponses(value = {
         @io.swagger.annotations.ApiResponse(code = 200, message = "Operazione eseguita con successo ", response = Dichiarazione.class),

         @io.swagger.annotations.ApiResponse(code = 400, message = "bad request", response = Errore.class),

         @io.swagger.annotations.ApiResponse(code = 401, message = "Utente non autorizzato a compiere l'operazione", response = Errore.class),

         @io.swagger.annotations.ApiResponse(code = 404, message = "Risorsa non trovata", response = Errore.class),

         @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = Errore.class)})
   public Response delegaDichiarazione( @ApiParam(value = "", required = true) @HeaderParam("X-Request-ID") String xRequestID, @ApiParam(value = "", required = true) @HeaderParam("X-Codice-Servizio") String xCodiceServizio, @ApiParam(value = "I campi obbligatori sono  * tipo    * codice -> deve essere CONGIUNTA  * stato    * codice -> deve essere DA_COMPLETARE, ATTIVA, REVOCATA, SCADUTA  * modo    * codice -> deve essere valorizzato ON_LINE  * dettagli    * stato      * codice -> deve essere DA_APPROVARE, VALIDA, DA_APPROVARE, REVOCATA_BLOCCO, REVOCATA, SCADUTA  * genitore_tutore_curatore    * codice_fiscale  * ruolo_genitore_curatore    * codice -> deve essere  GENITORE_1 o GENITORE_2  * figlio_tutelato_curato    * codice_fiscale  * ruolo_figlio_tutelato_curato    * codice deve essere passato FIGLIO ") CreateDichiarazione createDichiarazione, @Context SecurityContext securityContext, @Context HttpServletRequest request);


   @PUT
   @Path("/{cf}/dichiarazioni/{uuid_dichiarazione}")
   @Produces({"application/json"})
   @io.swagger.annotations.ApiOperation(value = "", notes = "Aggiorna una dichiarazione", response = Dichiarazione.class, tags = {"Gestione Dichiarazioni",})
   @io.swagger.annotations.ApiResponses(value = {
         @io.swagger.annotations.ApiResponse(code = 200, message = "Operazione eseguita con successo ", response = Dichiarazione.class),

         @io.swagger.annotations.ApiResponse(code = 400, message = "bad request", response = Errore.class),

         @io.swagger.annotations.ApiResponse(code = 401, message = "Utente non autorizzato a compiere l'operazione", response = Errore.class),

         @io.swagger.annotations.ApiResponse(code = 404, message = "Risorsa non trovata", response = Errore.class),

         @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = Errore.class)})
   public Response cittadiniCfDichiarazioniUuidDichiarazionePut( @ApiParam(value = "", required = true) @HeaderParam("X-Request-ID") String xRequestID, @ApiParam(value = "", required = true) @HeaderParam("X-Codice-Servizio") String xCodiceServizio, @PathParam("cf") String cf, @PathParam("uuid_dichiarazione") String uuidDichiarazione, @ApiParam(value = "I campi obbligatori sono  * tipo    * codice -> deve essere CONGIUNTA  * stato    * codice -> deve essere DA_COMPLETARE, ATTIVA, REVOCATA, SCADUTA  * modo    * codice -> deve essere valorizzato ON_LINE  * dettagli    * stato      * codice -> deve essere DA_APPROVARE, VALIDA, DA_APPROVARE, REVOCATA_BLOCCO, REVOCATA, SCADUTA  * genitore_tutore_curatore    * codice_fiscale  * ruolo_genitore_curatore    * codice -> deve essere  GENITORE_1 o GENITORE_2  * figlio_tutelato_curato    * codice_fiscale  * ruolo_figlio_tutelato_curato    * codice deve essere passato FIGLIO ") Dichiarazione dichiarazione, @Context SecurityContext securityContext, @Context HttpServletRequest request);

   @GET
   @Path("/tokeninfo")
   @Produces({"application/json"})
   @io.swagger.annotations.ApiOperation(value = "", notes = "Recupera informazioni utente loggato", response = TokenResponse.class, tags = {"Gestione Informazioni token",})
   @io.swagger.annotations.ApiResponses(value = {
   	 @io.swagger.annotations.ApiResponse(code = 200, message = "Operazione eseguita con successo ", response = TokenResponse.class),

   	 @io.swagger.annotations.ApiResponse(code = 400, message = "bad request", response = Errore.class),

   	 @io.swagger.annotations.ApiResponse(code = 401, message = "Utente non autorizzato a compiere l'operazione", response = Errore.class),

   	 @io.swagger.annotations.ApiResponse(code = 404, message = "Risorsa non trovata", response = Errore.class),

   	 @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = Errore.class)})
   public Response getTokenInformation2( @ApiParam(value = "", required = true) @HeaderParam("X-Request-ID") String xRequestID, @ApiParam(value = "", required = true) @HeaderParam("X-Codice-Servizio") String xCodiceServizio, @QueryParam("filter") String filter, @Context SecurityContext securityContext, @Context HttpServletRequest request);

   @GET
   @Path("/getIdAura")
   @Produces({"application/json"})
   @io.swagger.annotations.ApiOperation(value = "", notes = "Ricerca su aura del id aura", response = Cittadino.class, responseContainer = "List", tags = {"Ricerca su aura del id aura",})
   @io.swagger.annotations.ApiResponses(value = {
         @io.swagger.annotations.ApiResponse(code = 200, message = "Operazione eseguita con successo ", response = Cittadino.class, responseContainer = "List"),

         @io.swagger.annotations.ApiResponse(code = 400, message = "bad request", response = Errore.class),

         @io.swagger.annotations.ApiResponse(code = 401, message = "Utente non autorizzato a compiere l'operazione", response = Errore.class),

         @io.swagger.annotations.ApiResponse(code = 404, message = "Risorsa non trovata", response = Errore.class),

         @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = Errore.class)})
   public Response getIdAura( @ApiParam(value = "", required = true) @HeaderParam("X-Request-ID") String xRequestID, @ApiParam(value = "", required = true) @HeaderParam("X-Codice-Servizio") String xCodiceServizio, @QueryParam("filter") String filter, @Context SecurityContext securityContext, @Context HttpServletRequest request);

}
