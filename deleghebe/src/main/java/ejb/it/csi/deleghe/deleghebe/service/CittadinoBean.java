/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import it.csi.deleghe.deleghebe.model.DeleTCittadino;
import it.csi.deleghe.deleghebe.service.mapper.CittadinoDeleTCittadinoMapper;
import it.csi.deleghe.deleghebe.ws.model.Cittadino;

@Stateless
public class CittadinoBean {
	
	@Inject
	private DeleTCittadinoRepository deleTCittadinoRepository;
	@Inject
	private CittadinoDeleTCittadinoMapper cittadinoMapper;
	
	
	public Cittadino inserisciCittadino(Cittadino cittadino, String loginOperazione) {
		
		DeleTCittadino deleTCittadinoSearch = deleTCittadinoRepository.ricercaCittadino(cittadino.getCodiceFiscale());
		
		if(deleTCittadinoSearch != null) {
			throw new IllegalArgumentException("Impossibile inserire.Cittadino già presente.");
		}
		
		DeleTCittadino deleTCittadino = cittadinoMapper.to(cittadino);
		
		DeleTCittadino deleTCittadinoInserito = deleTCittadinoRepository.inserisciCittadino(deleTCittadino, loginOperazione);

		return cittadinoMapper.from(deleTCittadinoInserito);
	}
	
	public Cittadino aggiornaCittadino(Cittadino cittadino, String loginOperazione) {
		
		

		DeleTCittadino deleTcittadinoCF = deleTCittadinoRepository.ricercaCittadino(cittadino.getCodiceFiscale());
				
		if(deleTcittadinoCF==null) {
			throw new IllegalArgumentException("Cittadino non trovato Cod. Fiscale:" + cittadino.getCodiceFiscale());
		}
		
		DeleTCittadino deleTcittadino = cittadinoMapper.to(cittadino);
		deleTcittadino.setCitId(deleTcittadinoCF.getCitId());
		deleTcittadino.setCitAuraid(cittadino.getIdAura().toString());
		
		DeleTCittadino deleTCittadinoInserito = deleTCittadinoRepository.aggiornaCittadino(deleTcittadino, loginOperazione);

		return cittadinoMapper.from(deleTCittadinoInserito);
	}
	

	public List<Cittadino> ricercaCittadino(Cittadino cittadino) {
		
		DeleTCittadino deleTCittadino = cittadinoMapper.to(cittadino);
		
		List<DeleTCittadino> listaCittadini = deleTCittadinoRepository.ricercaCittadino(deleTCittadino);

		return cittadinoMapper.fromList(listaCittadini);
	}
	
}
