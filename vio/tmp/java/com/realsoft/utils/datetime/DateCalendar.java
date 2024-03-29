/*
 * Created on 23.12.2005
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: DateCalendar.java,v 1.2 2016/04/15 10:37:40 dauren_home Exp $
 */
package com.realsoft.utils.datetime;

import java.util.Calendar;
import java.util.Date;

public class DateCalendar {

	private DateCalendar() {
	}

	public static Calendar getCalendar(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}

	public static Date getDate(Calendar calendar) {
		return calendar.getTime();
	}

}
