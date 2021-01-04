/*
 * Created on Sep 27, 2007 2:19:48 PM
 *
 * Realsoft Ltd.
 * < --!-- JMan: Esanov Emil --!-- >
 * $Id: StringUtils.java,v 1.1 2014/07/01 11:58:26 dauren_work Exp $
 */
package com.realsoft.commons.utils14;

public class StringUtils {

	public static synchronized final String normalizeStringToNull(String str) {
		return str == null ? "" : str.length() == 0 ? null : str;
	}

	public static synchronized final String normalizeStringToZeroLength(
			String str) {
		return str == null ? "" : str;
	}

	public static synchronized final String normalizeStringToTrimNull(String str) {
		return str == null ? null : ((str = str.trim()).length() == 0 ? null
				: str);
	}

	public static synchronized final String normalizeStringToTrimNullOrSpace(
			String str) {
		return str == null ? null : ((str = str.trim()).length() == 0 ? null
				: str.replaceAll("\\s+", " "));
	}

	public static synchronized final String normalizeStringToTrimZeroLength(
			String str) {
		return str == null ? "" : ((str = str.trim()).length() == 0 ? "" : str);
	}

	public static synchronized final String normalizeStringToTrimZeroLengthOrSpace(
			String str) {
		return str == null ? "" : ((str = str.trim()).length() == 0 ? "" : str
				.replaceAll("\\s+", " "));
	}

	public static synchronized final String arrayToString(Object[] objects) {
		StringBuffer buffer = new StringBuffer();
		if (objects != null) {
			for (int i = 0; i < objects.length; i++) {
				if (objects[i] != null) {
					buffer.append(objects[i].toString());
				}
			}
			return buffer.toString();
		}
		return "";
	}

}
