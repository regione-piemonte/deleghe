/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service.mapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import it.csi.deleghe.deleghebe.model.DeleDDelegaStato;
import it.csi.deleghe.deleghebe.model.DeleDDelegaTipo;
import it.csi.deleghe.deleghebe.model.DeleTDelega;
import it.csi.deleghe.deleghebe.model.DeleTDelegaServizio;
import it.csi.deleghe.deleghebe.ws.model.Delega;
import it.csi.deleghe.deleghebe.ws.model.DelegaServ;

public class DelegaDeleTDelegaBoMapper implements Mapper<Delega, DeleTDelega> {

	@Inject
	private CittadinoDeleTCittadinoMapper cittadinoMapper;

   @Inject
	private DichiarazioneDettaglioDeleTDichiarazioneDetMapper dichiarazioneDettaglioDeleTDichiarazioneDetMapper;

   @Override
	public DeleTDelega to(Delega src) {

		return null;
   }

	

    @Override
	public Delega from(DeleTDelega dest) {
		if(dest==null) {
			return null;
		}
		Delega res = new Delega();
		res.setnPratica(dest.getDlgaId());
		res.setUuid(dest.getUuid());
		
		res.setDelegante(cittadinoMapper.from(dest.getDeleTCittadino1()));
		res.setDelegato(cittadinoMapper.from(dest.getDeleTCittadino2()));
		res.setDataCreazione(dest.getDataCreazione());
		DeleDDelegaStato deleDDelegaStato = dest.getDeleDDelegaStato();
		if(deleDDelegaStato != null) {
			res.setStatoDelega(deleDDelegaStato.getDelStatoCod());
		}

		

		DeleTDelegaServizio deleTDelegaServizio = dest.getDeleTDelegaServizios().get(0);
		List<DelegaServ> servizi = new ArrayList<>();
		DelegaServ serv = new DelegaServ();
		serv.setDataDecorrenza(deleTDelegaServizio.getDelDatadecorrenza());
		serv.setDataScadenza(deleTDelegaServizio.getDelDatascadenza());
		servizi.add(serv);
		res.setServizi(servizi);
		

		res.setDichiarazioneDettaglio(dichiarazioneDettaglioDeleTDichiarazioneDetMapper.from(dest.getDeleTDichiarazioneDet()));
		
		if(dest.getCompilatoreCf()!=null) {
			res.setCompilatoreCF(dest.getCompilatoreCf());
		}else {

			
		}
		
		DeleDDelegaTipo deleDDelegaTipo = dest.getDeleDDelegaTipo();
		if(deleDDelegaTipo != null) {
			res.setTipoDelega(deleDDelegaTipo.getDelTipCod());
		}
		

		return res;
	}
	

}
