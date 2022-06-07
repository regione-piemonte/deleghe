/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service.mapper;

import java.util.List;

import javax.inject.Inject;

import it.csi.deleghe.deleghebe.model.DeleDServizio;
import it.csi.deleghe.deleghebe.model.DeleTServizioParametro;
import it.csi.deleghe.deleghebe.service.DeleDServizioRepository;
import it.csi.deleghe.deleghebe.service.DeleTServizioParametroRepository;
import it.csi.deleghe.deleghebe.ws.model.Servizio;

public class ServizioDeleDServizioMapper implements Mapper<Servizio, DeleDServizio> {



	 
	@Inject
	private DeleDServizioRepository deleDServizioRepository;
	
	@Override
	public DeleDServizio to(Servizio src) {
		if(src==null  || src.getCodice()==null || src.getCodice().isEmpty()) {
			return null;
		}
		
		DeleDServizio deleDDelegaServizio=deleDServizioRepository.ricercaServiziBySerCod(src.getCodice());
		
		DeleDServizio res = new DeleDServizio();
		
		if(deleDDelegaServizio !=null )
			res=deleDDelegaServizio;
		
		
		return res;
	}

	@Override
	public Servizio from(DeleDServizio dest) {
		if(dest==null) {
			return null;
		}
		Servizio res = new Servizio();
		res.setCodice(dest.getSerCod());
		res.setDescrizione(dest.getSerDesc());
		res.setDescrizioneEstesa(dest.getSerDescestesa());
		res.setMinore(dest.getSerMinore());
		res.setArruolabile(dest.getSerArruolabile());
		res.setDelegabile(dest.getSerDelegabile());
		res.setNotificabile(dest.getSerNotificabile());
		res.setDataCancellazione(dest.getDataCancellazione());

		res.setCodSerPadre(dest.getCod_ser_padre());

		res.setFraseDebole(dest.getFraseDebole());

		res.setFraseForte(dest.getFraseForte());
		
		List<DeleTServizioParametro> deleTServizioParametros = dest.getDeleTServizioParametros();
		deleTServizioParametros.forEach(par -> {
			String serparCod = par.getSerparCod();

			
			if(DeleTServizioParametroRepository.SerParCodEnum.SCAD.name().equals(serparCod)){
				res.setNumeroGiorniDelegabili(Integer.valueOf(par.getSerparValore()));
			} 
			
		});
		
		res.setDataFineValidita(dest.getValiditaFine());
		res.setDataInizioValidita(dest.getValiditaInizio());
		res.setObbligatorioArruolamento(dest.getSerObblarruolamento());
		return res;
	}

}
