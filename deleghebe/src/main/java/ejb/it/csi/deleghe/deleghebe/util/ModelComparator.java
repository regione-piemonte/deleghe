/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

import it.csi.deleghe.deleghebe.model.DeleTDelegaServizio;

public class ModelComparator {
	
	public static LocalDate toLocalDate (Date date) {
	    return date != null ? LocalDate.from(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()) : null;
	}
	


	private static String getDeleDServizioCod(DeleTDelegaServizio delegaServ) {
		return ((delegaServ == null) || (delegaServ.getDeleDServizio() == null))? null : delegaServ.getDeleDServizio().getSerCod();
	}

	private static String getDeleDServizioStatoCod(DeleTDelegaServizio delegaServ) {
		return ((delegaServ == null) || (delegaServ.getDeleDDelegaServizioStato() == null))? null : delegaServ.getDeleDDelegaServizioStato().getDelstatoCod();
	}

	public static boolean equals (DeleTDelegaServizio delegaServA, DeleTDelegaServizio delegaServB) {
		if ((delegaServA == null) || (delegaServB == null)) return false;
		
		if (delegaServA.getDelDatadecorrenza() == null) {
			if (delegaServB.getDelDatadecorrenza() != null) return false;
		} else {
			if (!Objects.equals(toLocalDate(delegaServA.getDelDatadecorrenza()), toLocalDate(delegaServB.getDelDatadecorrenza()))) return false;
		}
		
		if (delegaServA.getDelDatascadenza() == null) {
			if (delegaServB.getDelDatascadenza() != null) return false;
		} else {
			if (!Objects.equals(toLocalDate(delegaServA.getDelDatascadenza()), toLocalDate(delegaServB.getDelDatascadenza()))) return false;
		}
		
		if (delegaServA.getDelDatarinuncia() == null) {
			if (delegaServB.getDelDatarinuncia() != null) return false;
		} else {
			if (!Objects.equals(toLocalDate(delegaServA.getDelDatarinuncia()), toLocalDate(delegaServB.getDelDatarinuncia()))) return false;
		}
		
		if (delegaServA.getDelDatarevoca() == null) {
			if (delegaServB.getDelDatarevoca() != null) return false;
		} else {
			if (!Objects.equals(toLocalDate(delegaServA.getDelDatarevoca()), toLocalDate(delegaServB.getDelDatarevoca()))) return false;
		}

		String dscA = getDeleDServizioCod(delegaServA);
		if (dscA == null) {
			if (getDeleDServizioCod(delegaServB) != null) return false;
		} else {
			if (!dscA.equals(getDeleDServizioCod(delegaServB))) return false;
		}

		String dsscA = getDeleDServizioStatoCod(delegaServA);
		if (dsscA == null) {
			if (getDeleDServizioStatoCod(delegaServB) != null) return false;
		} else {
			if (!dsscA.equals(getDeleDServizioStatoCod(delegaServB))) return false;
		}

		return true;
	}

}
