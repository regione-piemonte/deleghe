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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import io.swagger.annotations.ApiParam;
import it.csi.deleghe.delegheboweb.dto.delegheboweb.Errore;
import it.csi.deleghe.delegheboweb.dto.delegheboweb.Nazioni;

@Path("/nazioni")
@io.swagger.annotations.Api(description = "the servizio-attivo API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyEapServerCodegen", date = "2020-03-11T13:27:09.569Z")
public interface NazioniApi {
	
	@GET
	   @Path("/cercaNazioni")
	   @Produces({"application/json"})
	   @io.swagger.annotations.ApiOperation(value = "", notes = "Legge le nazioni da apisancross", response = Nazioni.class, responseContainer = "List", tags = {"Legge le nazioni da apisancross",})
	   @io.swagger.annotations.ApiResponses(value = {
	         @io.swagger.annotations.ApiResponse(code = 200, message = "Operazione eseguita con successo ", response = Nazioni.class, responseContainer = "List"),

	         @io.swagger.annotations.ApiResponse(code = 400, message = "bad request", response = Errore.class),

	         @io.swagger.annotations.ApiResponse(code = 401, message = "Utente non autorizzato a compiere l'operazione", response = Errore.class),

	         @io.swagger.annotations.ApiResponse(code = 404, message = "Risorsa non trovata", response = Errore.class),

	         @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = Errore.class)})
	   public Response cercaNazioniGet( @ApiParam(value = "", required = true) @HeaderParam("X-Request-ID") String xRequestID, @ApiParam(value = "", required = true) @HeaderParam("X-Codice-Servizio") String xCodiceServizio, @QueryParam("filter") String filter, @Context SecurityContext securityContext, @Context HttpServletRequest request);

}
