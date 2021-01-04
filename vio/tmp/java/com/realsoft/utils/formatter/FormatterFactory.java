/*
 * Created on 10.04.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: FormatterFactory.java,v 1.2 2016/04/15 10:37:31 dauren_home Exp $
 */
package com.realsoft.utils.formatter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.IllegalFormatException;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;

import com.realsoft.utils.UtilsException;

public class FormatterFactory implements IFormatter {

	private static Logger log = Logger.getLogger(FormatterFactory.class);

	private Map<Class, IFormatter> formatters = new Hashtable<Class, IFormatter>();

	private Locale locale = null;

	public FormatterFactory(Locale locale) {
		super();
		this.locale = locale;
	}

	public FormatterFactory(String locale) {
		this(new Locale(locale));
	}

	public void initialize() {
		formatters.put(String.class, new FormatterImpl(locale, "%s"));
		formatters.put(Date.class, new FormatterImpl(locale, "%td-%<tm-%<tY"));
		formatters.put(GregorianCalendar.class, new FormatterImpl(locale,
				"%td-%<tm-%<tY"));
		formatters.put(BigDecimal.class, new FormatterImpl(locale, "%,.2f"));
		formatters.put(BigInteger.class, new FormatterImpl(locale, "%,d"));
		formatters.put(Byte.class, new FormatterImpl(locale, "%d"));
		formatters.put(Byte.TYPE, new FormatterImpl(locale, "%d"));
		formatters.put(byte.class, new FormatterImpl(locale, "%d"));
		formatters.put(Double.class, new FormatterImpl(locale, "%,.2f"));
		formatters.put(Double.TYPE, new FormatterImpl(locale, "%,.2f"));
		formatters.put(double.class, new FormatterImpl(locale, "%,.2f"));
		formatters.put(Float.class, new FormatterImpl(locale, "%,.5f"));
		formatters.put(Float.TYPE, new FormatterImpl(locale, "%,.5f"));
		formatters.put(float.class, new FormatterImpl(locale, "%,.5f"));
		formatters.put(Integer.class, new FormatterImpl(locale, "%,d"));
		formatters.put(Integer.TYPE, new FormatterImpl(locale, "%,d"));
		formatters.put(int.class, new FormatterImpl(locale, "%,d"));
		formatters.put(Long.class, new FormatterImpl(locale, "%,d"));
		formatters.put(Long.TYPE, new FormatterImpl(locale, "%,d"));
		formatters.put(long.class, new FormatterImpl(locale, "%,d"));
		formatters.put(Short.class, new FormatterImpl(locale, "%d"));
		formatters.put(Short.TYPE, new FormatterImpl(locale, "%d"));
		formatters.put(short.class, new FormatterImpl(locale, "%d"));

		formatters.put(Boolean.class, new FormatterImpl(locale, "%B"));

		formatters.put(Character.class, new FormatterImpl(locale, "%s"));
		formatters.put(Character.class, new FormatterImpl(locale, "%s"));
		formatters.put(Class.class, new FormatterImpl(locale, "%s"));
		formatters.put(java.sql.Date.class, new FormatterImpl(locale,
				"%td-%<tm-%<tY %<tH:%<tM:%<tS"));
		formatters.put(Time.class, new FormatterImpl(locale,
				"%tH:%<tM:%<tS.%<tL"));
		formatters.put(Timestamp.class, new FormatterImpl(locale,
				"%td-%<tm-%<tY %<tH:%<tM:%<tS.%<tL"));
	}

	public void register(Class clazz, IFormatter formatter)
			throws UtilsException {
		formatters.put(clazz, formatter);
	}

	public String format(Object object) throws UtilsException {
		IFormatter formatter = null;
		if (object == null)
			formatter = formatters.get(String.class);
		else
			formatter = formatters.get(object.getClass());
		if (formatter == null)
			formatter = formatters.get(String.class);
		return formatter.format(object);
	}

	public String format(String pattern, Object[] objects)
			throws UtilsException {
		Formatter formatter = new Formatter();
		try {
			return formatter.format(pattern, objects).toString();
		} catch (IllegalFormatException e) {
			log.error("Could not format array of objects", e);
			throw new UtilsException("Could not format array of objects", e);
		}

	}

	public String format(Object object, Object defaultValue)
			throws UtilsException {
		IFormatter formatter = null;
		if (object == null)
			formatter = formatters.get(String.class);
		else
			formatter = formatters.get(object.getClass());
		if (formatter == null)
			formatter = formatters.get(String.class);
		return formatter.format(object, defaultValue);
	}

	public String format(String pattern, Object object) throws UtilsException {
		IFormatter formatter = null;
		if (pattern == null)
			return format(object);
		if (object == null || pattern == null)
			formatter = formatters.get(String.class);
		else
			formatter = formatters.get(object.getClass());
		if (formatter == null)
			formatter = formatters.get(String.class);
		return formatter.format(pattern, object);
	}

	public String format(String pattern, Object object, Object defaultValue)
			throws UtilsException {
		IFormatter formatter = null;
		if (pattern == null)
			return format(object);
		if (object == null || pattern == null)
			formatter = formatters.get(String.class);
		else
			formatter = formatters.get(object.getClass());
		if (formatter == null)
			formatter = formatters.get(String.class);
		return formatter.format(pattern, object, defaultValue);
	}

	public String format(byte object) throws UtilsException {
		return format(Byte.valueOf(object));
	}

	public String format(byte object, byte defaultValue) throws UtilsException {
		return format(Byte.valueOf(object), Byte.valueOf(defaultValue));
	}

	public String format(short object) throws UtilsException {
		return format(Short.valueOf(object));
	}

	public String format(short object, short defaultValue)
			throws UtilsException {
		return format(Short.valueOf(object), Short.valueOf(defaultValue));
	}

	public String format(int object) throws UtilsException {
		return format(Integer.valueOf(object));
	}

	public String format(int object, int defaultValue) throws UtilsException {
		return format(Integer.valueOf(object), Integer.valueOf(defaultValue));
	}

	public String format(long object) throws UtilsException {
		return format(Long.valueOf(object));
	}

	public String format(long object, long defaultValue) throws UtilsException {
		return format(Long.valueOf(object), Long.valueOf(defaultValue));
	}

	public String format(double object) throws UtilsException {
		return format(Double.valueOf(object));
	}

	public String format(double object, double defaultValue)
			throws UtilsException {
		return format(Double.valueOf(object), Double.valueOf(defaultValue));
	}

	public String format(float object) throws UtilsException {
		return format(Float.valueOf(object));
	}

	public String format(float object, float defaultValue)
			throws UtilsException {
		return format(Float.valueOf(object), Float.valueOf(defaultValue));
	}

	public String format(String pattern, byte object) throws UtilsException {
		return format(pattern, Byte.valueOf(object));
	}

	public String format(String pattern, byte object, byte defaultValue)
			throws UtilsException {
		return format(pattern, Byte.valueOf(object), Byte.valueOf(defaultValue));
	}

	public String format(String pattern, short object) throws UtilsException {
		return format(pattern, Short.valueOf(object));
	}

	public String format(String pattern, short object, short defaultValue)
			throws UtilsException {
		return format(pattern, Short.valueOf(object), Short
				.valueOf(defaultValue));
	}

	public String format(String pattern, int object) throws UtilsException {
		return format(pattern, Integer.valueOf(object));
	}

	public String format(String pattern, int object, int defaultValue)
			throws UtilsException {
		return format(pattern, Integer.valueOf(object), Integer
				.valueOf(defaultValue));
	}

	public String format(String pattern, long object) throws UtilsException {
		return format(pattern, Long.valueOf(object));
	}

	public String format(String pattern, long object, long defaultValue)
			throws UtilsException {
		return format(pattern, Long.valueOf(object), Long.valueOf(defaultValue));
	}

	public String format(String pattern, double object) throws UtilsException {
		return format(pattern, Double.valueOf(object));
	}

	public String format(String pattern, double object, double defaultValue)
			throws UtilsException {
		return format(pattern, Double.valueOf(object), Double
				.valueOf(defaultValue));
	}

	public String format(String pattern, float object) throws UtilsException {
		return format(pattern, Float.valueOf(object));
	}

	public String format(String pattern, float object, float defaultValue)
			throws UtilsException {
		return format(pattern, Float.valueOf(object), Float
				.valueOf(defaultValue));
	}

	public static void main(String[] args) {
		FormatterFactory formatter = new FormatterFactory(Locale.getDefault());
		try {
			formatter.initialize();
			formatter.register(Timestamp.class, new FormatterImpl(Locale
					.getDefault(), "%td-%<tm-%<tY %<tH:%<tM:%<tS"));

			System.out.println(formatter.format("Hello world"));
			System.out.println(formatter.format(null));

			System.out.println(formatter.format(Calendar.getInstance()
					.getTime()));
			System.out.println(formatter.format(Calendar.getInstance()));
			System.out.println(formatter.format(new BigDecimal(11223344)));
			System.out.println(formatter.format(new BigInteger("112234")));
			System.out.println(formatter.format(new Byte("10")));
			System.out.println(formatter.format((byte) 10));
			System.out.println(formatter.format(new Double(10.10)));
			System.out.println(formatter.format(new Double(10.11)));
			System.out.println(formatter.format((double) 10.11));
			System.out.println(formatter.format(new Float(10.10)));
			System.out.println(formatter.format(new Float(10.11)));
			System.out.println(formatter.format((float) 10.11));
			System.out.println(formatter.format(new Integer(100)));
			System.out.println(formatter.format(new Integer(1001)));
			System.out.println(formatter.format((int) 1001));
			System.out.println(formatter.format(new Long(100)));
			System.out.println(formatter.format(new Long(1001)));
			System.out.println(formatter.format((long) 1001));
			System.out.println(formatter.format(new Short("10")));
			System.out.println(formatter.format(new Short("11")));
			System.out.println(formatter.format((short) 11));
			System.out.println(formatter.format(new Boolean(true)));
			System.out.println(formatter.format(new Boolean(false)));
			System.out.println(formatter.format(new Character('a')));
			System.out.println(formatter.format(new Character('b')));
			System.out.println(formatter.format(Integer.class));
			System.out.println(formatter.format(new java.sql.Date(Calendar
					.getInstance().getTimeInMillis())));
			System.out.println(formatter.format(new Time(Calendar.getInstance()
					.getTimeInMillis())));
			System.out.println(formatter.format(new Timestamp(Calendar
					.getInstance().getTimeInMillis())));
			System.out.println(formatter.format(999999.999));

		} catch (UtilsException e) {
			e.printStackTrace();
		}
	}

}
