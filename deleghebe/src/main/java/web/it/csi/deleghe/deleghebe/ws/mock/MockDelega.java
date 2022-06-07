/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.ws.mock;

import java.util.ArrayList;
import java.util.List;

import it.csi.deleghe.deleghebe.ws.model.Cittadino;
import it.csi.deleghe.deleghebe.ws.model.Delega;
import it.csi.deleghe.deleghebe.ws.model.DelegaServ;
import it.csi.deleghe.deleghebe.ws.model.DelegaServStato;
import it.csi.deleghe.deleghebe.ws.model.Servizio;

public class MockDelega {

	public static List<Delega> listaDeleghe() {
		List<Delega> result = new ArrayList<Delega>();
		result.add(newDelega(newCittadino("zyz", "zyz", "zyz"),
				             newCittadino("zyz", "zyz", "zyz"),
				             new String[]{"COD_SER1", "Attivo"},
				             new String[]{"COD_SER2", "Revocato"}
					));
		result.add(newDelega(newCittadino("zyz", "zyz", "zyz"),
				             newCittadino("zyz", "zyz", "zyz"),
				             new String[]{"COD_SER3", "Attivo"},
				             new String[]{"COD_SER4", "Revocato"}
					));
		result.add(newDelega(newCittadino("zyz", "zyz", "zyz"),
				             newCittadino("zyz", "zyz", "zyz"),
				             new String[]{"COD_SER5", "Attivo"},
				             new String[]{"COD_SER6", "Revocato"}
					));
		
		return result;
	}
	
	
	private static Delega newDelega(Cittadino delegato, Cittadino delegante, String[]...servizi) {
		Delega d = new Delega();
		d.setDelegato(delegato);
		d.setDelegante(delegato);
		List<DelegaServ> s = new ArrayList<>();
		for(String[] kv : servizi) {
			s.add(newDelegaServ(kv[0], kv[1]));
		}
		d.setServizi(s);
		return d;
	}

	private static DelegaServ newDelegaServ(String codiceServizio, String codiceStato) {
		DelegaServ ds = new DelegaServ();
		Servizio servizio = new Servizio();
		servizio.setCodice(codiceServizio);

		ds.setServizio(servizio);
		DelegaServStato stato = new DelegaServStato();
		stato.setCodice(codiceStato);
		ds.setStato(stato);
		return ds;
	}


	private static Cittadino newCittadino(String cf, String nome, String cognome) {
		Cittadino c = new Cittadino();
		c.setCodiceFiscale(cf);
		c.setNome(nome);
		c.setCognome(cognome);
		return c;
	}
	
}
