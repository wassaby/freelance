/**
 * CAccountForPeriodException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 16, 2004 (12:19:44 EST) WSDL2Java emitter.
 */

package com.realsoft.commons.beans.httpclient;

import com.realsoft.commons.beans.CommonsBeansException;

public class BHttpClientException extends CommonsBeansException implements
		java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public BHttpClientException() {
	}

	public BHttpClientException(String errorCode, String messageKey,
			String messageText) {
		super(errorCode, messageKey, messageText);
		this.errorCode = errorCode;
		this.messageKey = messageKey;
		this.messageText = messageText;
	}

	/**
	 * @param message
	 * @param cause
	 */
	public BHttpClientException(String errorCode, String messageKey,
			String messageText, Throwable cause) {
		super(errorCode, messageKey, messageText, cause);
		this.errorCode = errorCode;
		this.messageKey = messageKey;
		this.messageText = messageText;
	}

}
