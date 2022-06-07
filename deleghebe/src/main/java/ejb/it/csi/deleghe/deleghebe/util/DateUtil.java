/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	private static final DateFormat dfmt = new SimpleDateFormat("dd/MM/yyyy");


	private DateUtil () {
		
	}
	
	public static boolean isMinorenne(Date datadinascita) {
		Calendar now = Calendar.getInstance();
		Calendar dob = Calendar.getInstance();

		dob.setTime(datadinascita);
		int age = now.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
		if (now.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) age--;

		return (age < 18);
	}

	public static boolean isMaggiorenne(Date datadinascita) {
		return !isMinorenne(datadinascita);
	}
	
	public static String toStringSimpleDate (Date date) {
		return dfmt.format(date);
	}
	
	public static Date advanceDate (Date date, String days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime((date != null)? date : new Date());
		
		int iDays = Integer.parseInt((days != null)? days : "365");

		cal.add(Calendar.DATE, iDays-1);
		cal.set(Calendar.HOUR, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		
		return cal.getTime();
	}
	
	public static Date resetTime (Date date) {
		if (date != null) {
			Calendar cal = Calendar.getInstance();

			cal.setTime(date);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			date = cal.getTime();
		}
		return date;
	}

}
