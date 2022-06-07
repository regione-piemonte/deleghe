/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service;

import java.util.*;

import javax.ejb.Stateless;
import javax.inject.Inject;

import it.csi.deleghe.deleghebe.model.DeleTDichiarazione;
import it.csi.deleghe.deleghebe.model.DeleTDocumento;
import it.csi.deleghe.deleghebe.model.DeleTFile;
import it.csi.deleghe.deleghebe.service.DeleDDichiarazioneDetStatoRepository.DicDetStatoCodEnum;
import it.csi.deleghe.deleghebe.service.DeleDDichiarazioneRuoloRepository.DicRuoloCodEnum;
import it.csi.deleghe.deleghebe.service.exception.BeServiceException;
import it.csi.deleghe.deleghebe.service.mapper.CittadinoDeleTCittadinoMapper;
import it.csi.deleghe.deleghebe.service.mapper.DichiarazioneDeleTDichiarazioneMapper;
import it.csi.deleghe.deleghebe.service.mapper.DocumentoDeleTDocumentoMapper;
import it.csi.deleghe.deleghebe.service.mapper.DocumentoFileDeleTFileMapper;
import it.csi.deleghe.deleghebe.util.DateUtil;
import it.csi.deleghe.deleghebe.ws.model.Cittadino;
import it.csi.deleghe.deleghebe.ws.model.Dichiarazione;
import it.csi.deleghe.deleghebe.ws.model.DichiarazioneDettaglio;
import it.csi.deleghe.deleghebe.ws.model.Documento;
import it.csi.deleghe.deleghebe.ws.model.DocumentoFile;
import it.csi.deleghe.deleghebe.ws.model.DocumentoTipo;
import it.csi.deleghe.deleghebe.ws.model.TipoDichiarazione;

@Stateless
public class DichiarazioneBean extends BaseRepository {

	@Inject
	private DeleTDichiarazioneRepository dichiarazioneRepository;
	@Inject
	private DichiarazioneDeleTDichiarazioneMapper dichiarazioneMapper;
	@Inject
	private CittadinoBean cittadinoBean;
	@Inject
	private DeleTCittadinoRepository deleTCittadinoRepository;
	@Inject
	private CittadinoDeleTCittadinoMapper cittadinoMapper;	
	@Inject
	private DeleTDocumentoRepository dcoumentoRepository;	
	@Inject
	private DocumentoDeleTDocumentoMapper documentoMapper;
	@Inject
	private DeleTFileRepository fileRepository;
	@Inject
	private DocumentoFileDeleTFileMapper fileMapper;

	public Dichiarazione inserisciDichiarazione(Dichiarazione dichiarazione, String loginOperazione) {

		validaDichiarazioneByDettagli(dichiarazione.getDettagli(), dichiarazione.getTipo());

		
		Map<String,DichiarazioneDettaglio> cittadini = new HashMap<>(); 
		for (DichiarazioneDettaglio dd : dichiarazione.getDettagli()) {
			checkCittadini(dd, cittadini);

			ricercaInserimentoCittadino(dd.getCittadino1(), loginOperazione);
			ricercaInserimentoCittadino(dd.getCittadino2(), loginOperazione);
		}

		DeleTDichiarazione deleTDichiarazione = dichiarazioneMapper.to(dichiarazione);

		DeleTDichiarazione deleTDichiarazioneInserito = dichiarazioneRepository.inserisciDichiarazione(deleTDichiarazione, loginOperazione);

		return dichiarazioneMapper.from(deleTDichiarazioneInserito);
	}
	
	public Documento cercaDocumento(Integer docId) {
		DeleTDocumento deleTDocumentoTrovato = dcoumentoRepository.ricercaDocumento(docId);

		return documentoMapper.from(deleTDocumentoTrovato);
	}
	
	public DocumentoFile cercaFile(Integer fileId) {
		DeleTFile deleTFileTrovato = fileRepository.ricercaFileDocumento(fileId);

		return fileMapper.from(deleTFileTrovato);
	}
	
	

	public Dichiarazione aggiornaDichiarazione(Dichiarazione dichiarazione, String loginOperazione) {


		DeleTDichiarazione deleTdichiarazioneID = dichiarazioneRepository.ricercaDichiarazioniByUUID(dichiarazione.getUuid());
		if (deleTdichiarazioneID == null) {
    		String msg = "Dichiarazione non trovata per l'UUID:" + dichiarazione.getUuid();
    		throw new BeServiceException("DA.R02", msg);
		}

		Map<String,DichiarazioneDettaglio> cittadini = new HashMap<>(); 
		for (DichiarazioneDettaglio dd : dichiarazione.getDettagli()) {
			dd.setCittadino1(cittadinoMapper.from(deleTCittadinoRepository.ricercaCittadino(dd.getCittadino1().getCodiceFiscale())));
			dd.setCittadino2(cittadinoMapper.from(deleTCittadinoRepository.ricercaCittadino(dd.getCittadino2().getCodiceFiscale())));
			checkCittadini(dd, cittadini);
		}

		DeleTDichiarazione deleTDichiarazione = dichiarazioneMapper.to(dichiarazione);
		deleTDichiarazione.setDicId(deleTdichiarazioneID.getDicId());

		DeleTDichiarazione deleTDichiarazioneAggiornato = dichiarazioneRepository.aggiornaDichiarazione(deleTDichiarazione, loginOperazione);

		return dichiarazioneMapper.from(deleTDichiarazioneAggiornato);
	}


	public List<Dichiarazione> ricercaDichiarazioni(UUID uuid, String codFiscale1, String ruoloCit1, String codFiscale2, String ruoloCit2, List<String> tipoDichiarazione, List<String> modoDichiarazione, List<String> statoDichiarazione) {

		List<DeleTDichiarazione> listDeleTDichiarazione =
				dichiarazioneRepository.ricercaDichiarazioni(uuid, codFiscale1, ruoloCit1, codFiscale2, ruoloCit2,
						tipoDichiarazione, modoDichiarazione, statoDichiarazione,null,null,null,null);

		return dichiarazioneMapper.fromList(listDeleTDichiarazione);
	}


	public List<Dichiarazione> ricercaDichiarazioni(UUID uuid, String codFiscale1, String ruoloCit1, String codFiscale2,
																	String ruoloCit2, List<String> tipoDichiarazione,
																	List<String> modoDichiarazione, List<String> statoDichiarazione,
																	Date dataInserimentoDa, Date dataInserimentoA, String ruoloCittadino,
																	String cittadinoCF) {

		List<DeleTDichiarazione> listDeleTDichiarazione =
				dichiarazioneRepository.ricercaDichiarazioni(uuid, codFiscale1, ruoloCit1, codFiscale2, ruoloCit2,
						tipoDichiarazione, modoDichiarazione, statoDichiarazione, dataInserimentoDa,dataInserimentoA,ruoloCittadino,cittadinoCF);

		return dichiarazioneMapper.fromList(listDeleTDichiarazione);
	}
	
	/**
	 * 
	 * @param uuid
	 * @return
	 */
	public Dichiarazione ricercaDichiarazioneByUUID(UUID uuid) {

		DeleTDichiarazione dichiarazione =
				dichiarazioneRepository.ricercaDichiarazioniByUUID(uuid);

		return dichiarazioneMapper.from(dichiarazione);
	}

	private void validaDichiarazioneByDettagli(List<DichiarazioneDettaglio> dichiarazioneDettaglio, TipoDichiarazione tipo) {
		
		for (DichiarazioneDettaglio dd : dichiarazioneDettaglio) {

			String cf1 = dd.getCittadino1().getCodiceFiscale();
			String ruolo1 = dd.getRuoloCittadino1().getCodice();


			String cf2 = dd.getCittadino2().getCodiceFiscale();
			String ruolo2 = dd.getRuoloCittadino2().getCodice();



			List<Dichiarazione> searchDichiarazioneListMinore = ricercaDichiarazioni(null, null, null, cf2, ruolo2, null, null, null);
			

			List<Dichiarazione> searchDichiarazioneListEntrambi = ricercaDichiarazioni(null, cf1, ruolo1, cf2, ruolo2, null, null, null);
			
			String tipoCodice = tipo.getCodice();

			if(tipoCodice.equals("CONGIUNTA")){

				if (searchDichiarazioneListMinore != null) {
					for (Dichiarazione dichiarazioneMinore : searchDichiarazioneListMinore) {
						for (DichiarazioneDettaglio dettaglioMinore : dichiarazioneMinore.getDettagli()) {
							String msg = null;
							if (!dettaglioMinore.getStato().getCodice().equals(DicDetStatoCodEnum.REVOCATA.name())){								
								msg = "Esiste gia' una dichiarazione associata al minore";
								throw new BeServiceException("DA.DCH05", msg);
							}							
						}					
					}
				}
			}else {

				if (searchDichiarazioneListEntrambi != null) {
					for (Dichiarazione dichiarazioneEntrambi : searchDichiarazioneListEntrambi) {
						for (DichiarazioneDettaglio dettaglioEntrambi : dichiarazioneEntrambi.getDettagli()) {
							String msg = null;
							if (!dettaglioEntrambi.getStato().getCodice().equals(DicDetStatoCodEnum.REVOCATA.name())){								
								msg = "Esiste gia' una dichiarazione associata al tutelato";
								throw new BeServiceException("DA.DCH05", msg);
							}							
						}					
					}
				}				
			}			
			
			
		}
	}

	private void ricercaInserimentoCittadino(Cittadino cittadino, String loginOperazione) {
		if (deleTCittadinoRepository.ricercaCittadino(cittadino.getCodiceFiscale()) == null) {
			cittadinoBean.inserisciCittadino(cittadino, loginOperazione);
		}
	}

	/**
	 * Controlla che i partecipanti alla dichiarazione abbiano ruoli coerenti alla propria anagrafica
	 * @param dicDettaglio
	 * @param cittadini
	 */
	private void checkCittadini(DichiarazioneDettaglio dicDettaglio, Map<String,DichiarazioneDettaglio> cittadini) {
		String ruolo1 = dicDettaglio.getRuoloCittadino1().getCodice();
		String ruolo2 = dicDettaglio.getRuoloCittadino2().getCodice();
		if (ruolo1.equals(DicRuoloCodEnum.FIGLIO.name())) {
			throw new BeServiceException("DA.DCH01", "Il cittadino1 deve essere GENITORE.");
		} else if (ruolo1.equals(DicRuoloCodEnum.TUTORE.name()) || ruolo1.equals(DicRuoloCodEnum.TUTORE_DI_UN_MAGIORE_INTER.name())){

			if (ruolo2.equals(DicRuoloCodEnum.TUTELATO.name())) {
				return;
			}
		}
		if (!ruolo2.equals(DicRuoloCodEnum.FIGLIO.name())) {
			throw new BeServiceException("DA.DCH01", "Il cittadino2 deve essere FIGLIO.");
		} 

		Cittadino genitore = dicDettaglio.getCittadino1();
		Cittadino figlio = dicDettaglio.getCittadino2();
		if (DateUtil.isMaggiorenne(figlio.getDataNascita())) {
			throw new BeServiceException("DA.DCH02", "Nella dichiarazione il figlio deve essere minorenne.");
		}
		if (DateUtil.isMinorenne(genitore.getDataNascita())) {
			throw new BeServiceException("DA.DCH03", "Nella dichiarazione il genitore deve essere maggiorenne.");
		}
		

		DichiarazioneDettaglio dett = cittadini.get(genitore.getCodiceFiscale());
		if (dett != null) {
			Cittadino gen = dett.getCittadino1();
			if (!dett.getRuoloCittadino1().getCodice().equals(ruolo1))
				throw new BeServiceException("DA.DCH04", "Nella dichiarazione il cittadino " + gen.getCodiceFiscale() + " ha ruolo non coerente.");
		} else {
			cittadini.put(genitore.getCodiceFiscale(), dicDettaglio);
		}

	}

}