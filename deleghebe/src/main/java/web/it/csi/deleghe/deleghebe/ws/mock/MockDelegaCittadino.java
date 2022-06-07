/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.ws.mock;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import it.csi.deleghe.deleghebe.ws.model.DelegaCittadino;
import it.csi.deleghe.deleghebe.ws.model.DelegaServizio;
import it.csi.deleghe.deleghebe.ws.model.Sesso;

public class MockDelegaCittadino {
	
	public static List<DelegaCittadino> listDelegaCittadino(){
		List<DelegaCittadino> deleghe = new ArrayList<>();
		deleghe.add(newDelegaCittadino());
		deleghe.add(newDelegaCittadino("zyz", "zyz", "zyz"));
		deleghe.add(newDelegaCittadino("zyz", "zyz", "zyz"));
		deleghe.add(newDelegaCittadino("zyz", "zyz", "zyz"));
		
		return deleghe;
	}
	
	public static DelegaCittadino newDelegaCittadino() {
		return newDelegaCittadino("zyz", "zyz", "zyz");
	}

	public static DelegaCittadino newDelegaCittadino(String cf, String nome, String cognome) {
		DelegaCittadino dc = new DelegaCittadino();
		dc.setCodiceFiscale(cf);
		dc.setNome(nome);
		dc.setCognome(cognome);
		dc.setDataDiNascita(new Date());
		dc.setLuogoNascita("Vieste");
		dc.setSesso(Sesso.M);
		dc.setIdAura(Long.valueOf(1232323));
		dc.setStato("ATTIVA");
		dc.setUuid(UUID.randomUUID().toString());
		
		List<DelegaServizio> deleghe = new ArrayList<>();
		deleghe.add(newDelegaServizio("CODSER1"));
		deleghe.add(newDelegaServizio("CODSER2"));
		dc.setDeleghe(deleghe);
		
		return dc;
	}

	public static DelegaServizio newDelegaServizio(String codiceServizio) {
		DelegaServizio ds = new DelegaServizio();
		ds.setCodiceServizio(codiceServizio);
		ds.setDataFineDelega(date(2020,1,1));
		ds.setDataInizioDelega(date(2018,1,1));
		ds.setStatoDelega("ATTIVA");
		ds.setUUID(UUID.randomUUID().toString());
		return ds;
	}
	
	private static Date date(int anno, int mese, int giorno) {
		LocalDate localDate = LocalDate.of(anno, mese, giorno);
		return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}
}
