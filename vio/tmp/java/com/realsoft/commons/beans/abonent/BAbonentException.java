/**
 * CAccountForPeriodException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 16, 2004 (12:19:44 EST) WSDL2Java emitter.
 */

package com.realsoft.commons.beans.abonent;

import com.realsoft.commons.beans.CommonsBeansException;

/**
 * Класс генерирующий исключение при ошибках, возникающих в компоненте
 * {@link IBAbonent}. Этот класс расширяет класс исключений
 * {@link CommonsBeansException}.
 * 
 * @author temirbulatov
 */
public class BAbonentException extends CommonsBeansException implements
		java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private java.lang.String errorCode;

	private java.lang.String messageKey;

	private java.lang.String messageText;

	public BAbonentException() {
	}

	public BAbonentException(String errorCode, String messageKey,
			String messageText) {
		super(errorCode, messageKey, messageText);
		this.errorCode = errorCode;
		this.messageKey = messageKey;
		this.messageText = messageText;
	}

	/**
	 * @param errorCode
	 *            Код ошибки
	 * @param messageKey
	 *            Ключ сообщения об ошибке
	 * @param messageText
	 *            Образец сообщения об ошибке
	 * @param cause
	 */
	public BAbonentException(String errorCode, String messageKey,
			String messageText, Throwable cause) {
		super(errorCode, messageKey, messageText, cause);
		this.errorCode = errorCode;
		this.messageKey = messageKey;
		this.messageText = messageText;
	}

	public java.lang.String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(java.lang.String errorCode) {
		this.errorCode = errorCode;
	}

	public java.lang.String getMessageKey() {
		return messageKey;
	}

	public void setMessageKey(java.lang.String messageKey) {
		this.messageKey = messageKey;
	}

	public java.lang.String getMessageText() {
		return messageText;
	}

	public void setMessageText(java.lang.String messageText) {
		this.messageText = messageText;
	}

	public String toString() {
		return messageText;
	}
}