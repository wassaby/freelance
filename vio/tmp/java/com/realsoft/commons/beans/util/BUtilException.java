/**
 * CAccountForPeriodException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 16, 2004 (12:19:44 EST) WSDL2Java emitter.
 */

package com.realsoft.commons.beans.util;

import com.realsoft.commons.beans.CommonsBeansException;

public class BUtilException extends CommonsBeansException implements
		java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public BUtilException() {
	}

	public BUtilException(String errorCode, String messageKey,
			String messageText) {
		super(errorCode, messageKey, messageText);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public BUtilException(String errorCode, String messageKey,
			String messageText, Throwable cause) {
		super(errorCode, messageKey, messageText, cause);
	}

}
