/*
 * Created on 05.12.2005
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: StringUtils.java,v 1.2 2016/04/15 10:37:35 dauren_home Exp $
 */
package com.realsoft.utils.string;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class StringUtils extends com.realsoft.commons.utils14.StringUtils {

	private static String ORA_ERROR = "ORA-";

	private static Logger log = Logger.getLogger(StringUtils.class);

	private StringUtils() {
		super();
	}

	/**
	 * Uncapitalize first letters length LPDBUser -> lpdBUser
	 * 
	 * @param source
	 * @param length
	 * @return
	 */
	public static String uncapitalizeFirst(String source, int length) {
		String firstThree = source.substring(0, length);
		String last = source.substring(length);
		return firstThree.toLowerCase() + last;
	}

	/**
	 * Uncapitalize first three letter
	 * 
	 * @param source
	 * @return
	 */
	public static String uncapitalizeFirstTwo(String source) {
		return uncapitalizeFirst(source, 2);
	}

	public static String[] getOraErrorCode(String message) {
		StringUtils.log.debug("message = " + message);
		List<String> errorCodes = new ArrayList<String>();
		StringTokenizer tokenizer = new StringTokenizer(message, ORA_ERROR);
		while (tokenizer.hasMoreElements()) {
			String token = tokenizer.nextToken();
			StringUtils.log.debug("token = " + token);
			String[] messages = token.split(":");
			for (int i = 0; i < messages.length; i++) {
				if (NumberUtils.isNumber(messages[i])) {
					StringUtils.log.debug("message = " + messages[i]);
					errorCodes.add(messages[i]);
				}
			}
		}
		String[] oraErrorCodes = new String[errorCodes.size()];
		oraErrorCodes = errorCodes.toArray(oraErrorCodes);
		return oraErrorCodes;
	}

	public static String[] getOraErrorTrace(String message) {
		String[] errorCodes = getOraErrorCode(message);
		for (int i = 0; i < errorCodes.length; i++) {
			errorCodes[i] = "." + errorCodes[i];
		}
		return errorCodes;
	}

	public static String[] getOraErrorCodeDotend(String message) {
		String[] messages = getOraErrorTrace(message);
		if (messages.length > 0)
			return getOraErrorTrace(message);
		return new String[] { message };
	}

	/**
	 * Возвращает результат конкатинации inString самой с собой count раз
	 * 
	 */

	public static String stringOfChar(String inString, int count) {
		StringBuffer result = new StringBuffer();
		for (int i = 1; i <= count; i++)
			result.append(inString);
		return result.toString();
	}

	public static void main(String[] args) {
		DOMConfigurator.configure("C:/test/Log4j/Log4j.xml");

		// StringUtils.log
		// .info(StringUtils
		// .getOraErrorCode("java.sql.SQLException: ORA-20005: Невозможно
		// создание аккаунта.dimadORA-20017: Существует аккаунт с логином
		// dimadORA-06512: at \"UN.PKG_ACCOUNT_USR\", line 171ORA-06512: at line
		// 1"));
		// StringUtils.log
		// .info(StringUtils
		// .getOraErrorCode("20001: Невозможно создание подключения. Аккаунт
		// активизирован или не создано устройство 06512: at
		// 'UN.PKG_ACCOUNT_USR', line 129 06512: at line 1 - 123454 "));
		StringUtils.log.info(stringOfChar("&nbsp;", 0));
	}

}
