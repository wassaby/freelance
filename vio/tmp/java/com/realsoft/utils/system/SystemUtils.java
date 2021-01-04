/*
 * Created on 09.02.2005
 *
 * Realsoft Ltd.
 * Dmitry Dudorga
 * $Id: SystemUtils.java,v 1.2 2016/04/15 10:37:47 dauren_home Exp $
 */
package com.realsoft.utils.system;

import com.realsoft.utils.UtilsException;

/**
 * @author dimad
 */
public class SystemUtils {

	private SystemUtils() {
		super();
	}

	public static String getPropertyValue(String property)
			throws UtilsException {
		String value = System.getProperty(property);
		if ((value == null) || (value.equals(""))) {
			throw new UtilsException("System parameter '" + property
					+ "' was not set");
		}
		return value;
	}

	public static String getPropertyValue(String property, String defaultValue) {
		return System.getProperty(property, defaultValue);
	}

	public static void setPropertyValue(String property, String value) {
		System.setProperty(property, value);
	}

}
