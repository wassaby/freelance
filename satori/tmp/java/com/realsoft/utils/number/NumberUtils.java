/*
 * Created on 31.01.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: NumberUtils.java,v 1.1 2014/07/01 11:58:24 dauren_work Exp $
 */
package com.realsoft.utils.number;

import org.apache.log4j.Logger;

public class NumberUtils {

	private static Logger log = Logger.getLogger(NumberUtils.class);

	private NumberUtils() {
		super();
	}

	public static boolean hasFractionalPart(double value) {
		return (value - java.lang.Math.floor(value) > 0);
	}

	public static boolean isNumber(Object object) {
		if (object == null)
			return false;
		if (object.getClass() == double.class || object.getClass() == int.class
				|| object.getClass() == long.class
				|| object.getClass() == byte.class
				|| object.getClass() == float.class
				|| object instanceof Integer || object instanceof Double
				|| object instanceof Long || object instanceof Byte)
			return true;
		return org.apache.commons.lang.math.NumberUtils.isNumber(object
				.toString());
	}

	public static boolean isNumber(Class clazz) {
		return (clazz == double.class || clazz == int.class
				|| clazz == long.class || clazz == byte.class
				|| clazz == float.class || clazz == Integer.class
				|| clazz == Double.class || clazz == Long.class || clazz == Byte.class);
	}

	public static Integer getInteger(int value) {
		return Integer.valueOf(value);
	}

	public static Integer getInteger(String value) {
		if (value != null && value.length() != 0)
			return Integer.valueOf(value);
		return null;
	}

	public static Long getLong(long value) {
		return Long.valueOf(value);
	}

	public static Long getLong(String value) {
		if (value != null && value.length() != 0)
			return Long.valueOf(value);
		return null;
	}

	public static Double getDouble(double value) {
		return Double.valueOf(value);
	}

	public static Double getDouble(String value) {
		if (value != null && value.length() != 0)
			return Double.valueOf(value);
		return null;
	}

	public static Byte getByte(byte value) {
		return Byte.valueOf(value);
	}

	public static Byte getByte(String value) {
		if (value != null && value.length() != 0)
			return Byte.valueOf(value);
		return null;
	}

	public static Float getFloat(float value) {
		return Float.valueOf(value);
	}

	public static Float getFloat(String value) {
		if (value != null && value.length() != 0)
			return Float.valueOf(value);
		return null;
	}

}
