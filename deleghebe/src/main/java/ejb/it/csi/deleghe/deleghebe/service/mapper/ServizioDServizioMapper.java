/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service.mapper;

import it.csi.deleghe.deleghebe.model.DServizio;
import it.csi.deleghe.deleghebe.ws.model.Servizio;

public class ServizioDServizioMapper implements Mapper<Servizio, DServizio> {



	
	@Override
	public DServizio to(Servizio src) {
		if(src==null) {
			return null;
		}
		DServizio res = new DServizio();
		
		res.setServCod(src.getCodice());
		res.setServDesc(src.getDescrizione());
		res.setServDescestesa(src.getDescrizioneEstesa());
		res.setServMinore(src.getMinore());
		res.setServArruolabile(src.getArruolabile());
		res.setServNotificabile(src.getNotificabile());
		res.setServDelegabile(src.getDelegabile());
		res.setServObblarruolamento(src.getObbligatorioArruolamento());
		res.setServMaxggDelega(src.getNumeroGiorniDelegabili());
		res.setValiditaInizio(src.getDataInizioValidita());
		res.setValiditaFine(src.getDataFineValidita());
		res.setDataCancellazione(src.getDataCancellazione());
		return res;
	}

	@Override
	public Servizio from(DServizio dest) {
		if(dest==null) {
			return null;
		}
		Servizio res = new Servizio();
		res.setCodice(dest.getServCod());
		res.setDescrizione(dest.getServDesc());
		res.setDescrizioneEstesa(dest.getServDescestesa());
		res.setMinore(dest.getServMinore());
		res.setDataCancellazione(dest.getDataCancellazione());
		res.setArruolabile(dest.getServArruolabile());
		res.setNotificabile(dest.getServNotificabile());
		res.setDelegabile(dest.getServDelegabile());
		res.setObbligatorioArruolamento(dest.getServObblarruolamento());
		res.setNumeroGiorniDelegabili(dest.getServMaxggDelega());
		res.setDataInizioValidita(dest.getValiditaInizio());
		res.setDataFineValidita(dest.getValiditaFine());
		
		return res;
	}

}
