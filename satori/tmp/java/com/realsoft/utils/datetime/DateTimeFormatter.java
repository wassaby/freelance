/*
 * Created on 18.05.2005
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: DateTimeFormatter.java,v 1.1 2014/07/01 11:58:27 dauren_work Exp $
 */
package com.realsoft.utils.datetime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

/**
 * @author dimad
 */
public class DateTimeFormatter {

	private static Logger log = Logger.getLogger(DateTimeFormatter.class);

	private static final SimpleDateFormat sdfDate = new SimpleDateFormat(
			"yyyy-MM-dd");

	private DateTimeFormatter() {
	}

	public static Date getDateFirstSecond(Date date) throws ParseException {
		if (date != null)
			return sdfDate.parse(sdfDate.format(date));
		return null;
	}

	public static Date getDate(String format, String date)
			throws ParseException {
		if (date != null && date.length() != 0) {
			log.debug("date = " + date + "; format = " + format);
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.parse(date);
		}
		return null;
	}

	public static Date getDate(SimpleDateFormat format, String date)
			throws ParseException {
		if (date != null && date.length() != 0) {
			log.debug("date = " + date);
			return format.parse(date);
		}
		return null;
	}

	public static String getDateString(String format, Date date)
			throws ParseException {
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(date);
		}
		return "";
	}

	public static String getDateString(SimpleDateFormat format, Date date)
			throws ParseException {
		if (date != null) {
			return format.format(date);
		}
		return "";
	}

	public static String getDateString(String format, Calendar date)
			throws ParseException {
		return getDateString(format, date.getTime());
	}

	public static String getDateString(SimpleDateFormat format, Calendar date)
			throws ParseException {
		return getDateString(format, date.getTime());
	}

	public static String getDateString(Date date) throws ParseException {
		if (date != null)
			return sdfDate.format(date);
		return "";
	}

}
