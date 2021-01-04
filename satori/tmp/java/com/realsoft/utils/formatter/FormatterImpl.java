/*
 * Created on 10.04.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: FormatterImpl.java,v 1.1 2014/07/01 11:58:22 dauren_work Exp $
 */
package com.realsoft.utils.formatter;

import java.util.Formatter;
import java.util.Locale;

import com.realsoft.utils.UtilsException;

public class FormatterImpl implements IFormatter {

	private String pattern;

	private Locale locale = Locale.getDefault();

	public FormatterImpl(String pattern) {
		super();
		this.pattern = pattern;
	}

	public FormatterImpl(Locale locale, String pattern) {
		super();
		this.locale = locale;
		this.pattern = pattern;
	}

	private String makeFormat(String pattern, Object object) {
		Formatter format = new Formatter().format(locale, pattern, object);
		String result = format.toString();
		format.close();
		return result;
	}

	public String format(String pattern, Object object) throws UtilsException {
		if (object == null)
			return "";
		return makeFormat(pattern, object);
	}

	public String format(String pattern, Object[] objects)
			throws UtilsException {
		if (objects == null)
			return "";
		return makeFormat(pattern, objects);
	}

	public String format(String pattern, Object object, Object defaultValue)
			throws UtilsException {
		if (object == null) {
			return makeFormat(pattern, defaultValue);
		}
		return makeFormat(pattern, object);
	}

	public void register(Class clazz, IFormatter formatter)
			throws UtilsException {
		throw new UtilsException("Illegal methos call");
	}

	public String format(Object object) throws UtilsException {
		return format(pattern, object);
	}

	public String format(Object object, Object defaultValue)
			throws UtilsException {
		return format(pattern, object, defaultValue);
	}

	public String format(byte object) throws UtilsException {
		return format(object);
	}

	public String format(byte object, byte defaultValue) throws UtilsException {
		return format(object, defaultValue);
	}

	public String format(short object) throws UtilsException {
		return format(object);
	}

	public String format(short object, short defaultValue)
			throws UtilsException {
		return format(object, defaultValue);
	}

	public String format(int object) throws UtilsException {
		return format(object);
	}

	public String format(int object, int defaultValue) throws UtilsException {
		return format(object, defaultValue);
	}

	public String format(long object) throws UtilsException {
		return format(object);
	}

	public String format(long object, long defaultValue) throws UtilsException {
		return format(object, defaultValue);
	}

	public String format(double object) throws UtilsException {
		return format(object);
	}

	public String format(double object, double defaultValue)
			throws UtilsException {
		return format(object, defaultValue);
	}

	public String format(float object) throws UtilsException {
		return format(object);
	}

	public String format(float object, float defaultValue)
			throws UtilsException {
		return format(object, defaultValue);
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

}
