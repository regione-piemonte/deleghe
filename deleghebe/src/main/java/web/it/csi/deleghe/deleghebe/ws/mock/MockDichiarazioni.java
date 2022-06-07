/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.ws.mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import it.csi.deleghe.deleghebe.ws.model.Cittadino;
import it.csi.deleghe.deleghebe.ws.model.Dichiarazione;
import it.csi.deleghe.deleghebe.ws.model.DichiarazioneDettaglio;
import it.csi.deleghe.deleghebe.ws.model.DichiarazioneDettaglioStato;
import it.csi.deleghe.deleghebe.ws.model.DichiarazioneRuolo;
import it.csi.deleghe.deleghebe.ws.model.Documento;
import it.csi.deleghe.deleghebe.ws.model.DocumentoFile;
import it.csi.deleghe.deleghebe.ws.model.DocumentoTipo;
import it.csi.deleghe.deleghebe.ws.model.Entita;
import it.csi.deleghe.deleghebe.ws.model.ModoDichiarazione;
import it.csi.deleghe.deleghebe.ws.model.StatoDichiarazione;
import it.csi.deleghe.deleghebe.ws.model.TipoDichiarazione;

public class MockDichiarazioni {

	public static List<Dichiarazione> listaDichiarazioni() {

		List<Dichiarazione> result = new ArrayList<>();

		result.add(newDichiarazione(UUID.randomUUID(), newCittadino("zyz", "zyz", "zyz"), 
				newModoDichiarazioneOnLine(), 
				newStatoDichiarazioneAttiva(), 
				newTipoDichiarazioneCongiunta(), 
				newDichiarazioneDettaglio(UUID.randomUUID(), newCittadino("zyz", "zyz", "zyz"), 
											newCittadino("zyz", "zyz", "zyz"), 
											newDichiarazioneRuoloGenitore1(), 
											newDichiarazioneRuoloFiglio(), 
											newDocumento(1, newDocumentoTipo1(), "Desc documento 1", 
														newDocumentoFile("documento1.pdf", new byte[] {123,127})), 
											newDichiarazioneDettaglioStatoValida())));
		
		
		result.add(newDichiarazione(UUID.randomUUID(), newCittadino("zyz", "zyz", "zyz"), 
				newModoDichiarazioneSportello(), 
				newStatoDichiarazioneAttiva(), 
				newTipoDichiarazioneCongiunta(), 
				newDichiarazioneDettaglio(UUID.randomUUID(), newCittadino("zyz", "zyz", "zyz"), 
											newCittadino("zyz", "zyz", "zyz"), 
											newDichiarazioneRuoloGenitore1(), 
											newDichiarazioneRuoloFiglio(), 
											newDocumento(1, newDocumentoTipo1(), "Desc documento 2", 
														newDocumentoFile("documento2.pdf", new byte[] {122,127})), 
											newDichiarazioneDettaglioStatoValida())));
		
		
		result.add(newDichiarazione(UUID.randomUUID(), newCittadino("zyz", "zyz", "zyz"), 
				newModoDichiarazioneSportello(), 
				newStatoDichiarazioneRevocata(), 
				newTipoDichiarazioneCongiunta(), 
				newDichiarazioneDettaglio(UUID.randomUUID(), newCittadino("zyz", "zyz", "zyz"), 
											newCittadino("zyz", "zyz", "zyz"), 
											newDichiarazioneRuoloGenitore1(), 
											newDichiarazioneRuoloFiglio(), 
											newDocumento(1, newDocumentoTipo1(), "Desc documento 2", 
														newDocumentoFile("documento2.pdf", new byte[] {122,127})), 
											newDichiarazioneDettaglioStatoRevocata())));
		

		return result;

	}

	private static Dichiarazione newDichiarazione(UUID uuid, Cittadino cittadino, ModoDichiarazione modo, StatoDichiarazione stato, TipoDichiarazione tipo, DichiarazioneDettaglio...dettagli) {
		Dichiarazione d = new Dichiarazione();
		d.setUuid(uuid);
		d.setCittadino(cittadino);
		d.setModo(modo);
		d.setStato(stato);
		d.setTipo(tipo);
		d.setDettagli(Arrays.asList(dettagli));
		
		setDataCreazioneELoginOperazione(d);
		return d;
	}

	private static DichiarazioneDettaglio newDichiarazioneDettaglio(UUID uuid, Cittadino cittadino1, Cittadino cittadino2, DichiarazioneRuolo ruoloCittadino1, DichiarazioneRuolo ruoloCittadino2, Documento documento, DichiarazioneDettaglioStato stato) {
		DichiarazioneDettaglio dd = new DichiarazioneDettaglio();
		dd.setUuid(uuid);
		dd.setCittadino1(cittadino1);
		dd.setCittadino2(cittadino2);
		dd.setRuoloCittadino1(ruoloCittadino1);
		dd.setRuoloCittadino2(ruoloCittadino2);
		dd.setDocumento(documento);
		dd.setStato(stato);
		setDataCreazioneELoginOperazione(dd);
		return dd;
	}
	
	/**
	 * 
	 * VALIDA Valida 
	 * DA_APPROVARE In attesa di approvazione 
	 * REVOCATA_BLOCCO Revocata con blocco 
	 * REVOCATA Revocata 
	 * SCADUTA Scaduta
	 * 
	 * @param codice
	 * @param descrizione
	 * @return
	 */
	private static DichiarazioneDettaglioStato newDichiarazioneDettaglioStato(String codice, String descrizione) {
		DichiarazioneDettaglioStato dds = new DichiarazioneDettaglioStato();
		dds.setCodice(codice);
		dds.setDescrizione(descrizione);
		setDataCreazioneELoginOperazione(dds);
		return dds;
	}
	
	private static DichiarazioneDettaglioStato newDichiarazioneDettaglioStatoValida() {
		return newDichiarazioneDettaglioStato("VALIDA", "Valida");
	}
		
	private static DichiarazioneDettaglioStato newDichiarazioneDettaglioStatoDaApprovare() {
		return newDichiarazioneDettaglioStato("DA_APPROVARE", "In attesa di approvazione");
	}
	
	private static DichiarazioneDettaglioStato newDichiarazioneDettaglioStatoRevocataBlocco() {
		return newDichiarazioneDettaglioStato("REVOCATA_BLOCCO", "Revocata con blocco");
	}
	
	private static DichiarazioneDettaglioStato newDichiarazioneDettaglioStatoRevocata() {
		return newDichiarazioneDettaglioStato("REVOCATA", "Revocata");
	}
	
	private static DichiarazioneDettaglioStato newDichiarazioneDettaglioStatoScaduta() {
		return newDichiarazioneDettaglioStato("SCADUTA", "Scaduta");
	}
		
	
	private static DichiarazioneRuolo newDichiarazioneRuolo(String codice, String descrizione) {
		DichiarazioneRuolo dr = new DichiarazioneRuolo();
		dr.setCodice(codice);
		dr.setDescrizione(descrizione);
		setDataCreazioneELoginOperazione(dr);
		return dr;
	}

	private static DichiarazioneRuolo newDichiarazioneRuoloGenitore1() {
		return newDichiarazioneRuolo("GENITORE_1", "Genitore 1");
	}
	
	private static DichiarazioneRuolo newDichiarazioneRuoloGenitore2() {
		return newDichiarazioneRuolo("GENITORE_2", "Genitore 2");
	}
	
	private static DichiarazioneRuolo newDichiarazioneRuoloFiglio() {
		return newDichiarazioneRuolo("FIGLIO", "Figlio");
	}
	
	private static Documento newDocumento(Integer id, DocumentoTipo documentoTipo, String desc, DocumentoFile file) {
		Documento d = new Documento();
		d.setId(id);
		d.setDocumentoTipo(documentoTipo);
		d.setDesc(desc);
		d.setFile(file);
		setDataCreazioneELoginOperazione(d);
		return d;
	}
	
	private static DocumentoFile newDocumentoFile(String nome, byte[] bytes) {
		DocumentoFile df = new DocumentoFile();
		df.setNome(nome);
		if(bytes!=null) {
			df.setDimensioneInBytes(bytes.length);
			df.setBytes(bytes);
			df.setBase64File(Base64.getEncoder().encodeToString(bytes));	
		}
		
		return df;
	}
	
	private static DocumentoTipo newDocumentoTipo(String codice, String descrizione) {
		DocumentoTipo dt = new DocumentoTipo();
		dt.setCodice(codice);
		dt.setDescrizione(descrizione);
		setDataCreazioneELoginOperazione(dt);
		return dt;
	}
	
	private static DocumentoTipo newDocumentoTipo1() {
		return newDocumentoTipo("1", "1");
	}

	
	private static ModoDichiarazione newModoDichiarazioneOnLine(String codice, String descrizione) {
		ModoDichiarazione modo = new ModoDichiarazione();
		modo.setCodice(codice);
		modo.setDescrizione(descrizione);
		setDataCreazioneELoginOperazione(modo);
		return modo;
	}
	
	private static ModoDichiarazione newModoDichiarazioneOnLine() {
		return newModoDichiarazioneOnLine("ON_LINE", "On-Line");
	}
	
	private static ModoDichiarazione newModoDichiarazioneSportello() {
		return newModoDichiarazioneOnLine("SPORTELLO", "Sportello");
	}
	
	private static TipoDichiarazione newTipoDichiarazione(String codice, String descrizione) {
		TipoDichiarazione modo = new TipoDichiarazione();
		modo.setCodice(codice);
		modo.setDescrizione(descrizione);
		setDataCreazioneELoginOperazione(modo);
		return modo;
	} 
	
	private static TipoDichiarazione newTipoDichiarazioneCongiunta() {
		return newTipoDichiarazione("CONGIUNTA", "Congiunta");
	}
	
	
	
	/*
	 * Codici
	 * DA_COMPLETARE In attesa di completamento 
	 * ATTIVA Attiva 
	 * REVOCATA Revocata
	 * SCADUTA Scaduta
	 */
	private static StatoDichiarazione newStatoDichiarazione(String codice, String descrizione) {
		StatoDichiarazione stato = new StatoDichiarazione();
		// Codice: on-line o sportello 1 -> sportello
		stato.setCodice(codice);
		stato.setDescrizione(descrizione);
		setDataCreazioneELoginOperazione(stato);
		return stato;
	}
	
	private static StatoDichiarazione newStatoDichiarazioneDaCompletare() {
		return newStatoDichiarazione("DA_COMPLETARE", "In attesa di completamento");
	}
	
	private static StatoDichiarazione newStatoDichiarazioneAttiva() {
		return newStatoDichiarazione("ATTIVA", "Attiva");
	}
	
	private static StatoDichiarazione newStatoDichiarazioneRevocata() {
		return newStatoDichiarazione("REVOCATA", "Revocata");
	}
	
	private static StatoDichiarazione newStatoDichiarazioneScaduta() {
		return newStatoDichiarazione("SCADUTA", "Scaduta");
	}

	private static void setDataCreazioneELoginOperazione(Entita e) {
		Date now = new Date();
		e.setDataCreazione(now);
		e.setDataModifica(now);
		e.setLoginOperazione("RSLNDD74C22L219W");
	}
	
	
	private static Cittadino newCittadino(String cf, String nome, String cognome) {
		Cittadino c = new Cittadino();
		c.setCodiceFiscale(cf);
		c.setNome(nome);
		c.setCognome(cognome);

		return c;
	}
}
