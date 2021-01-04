/**
 * CAccountForPeriodException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 16, 2004 (12:19:44 EST) WSDL2Java emitter.
 */

package com.realsoft.commons.beans;

import java.rmi.RemoteException;

/**
 * Класс генерирующий исключение при ошибках, возникающих в компоненте.
 * 
 * @author temirbulatov
 * 
 */

public class CommonsBeansException extends RemoteException implements
		java.io.Serializable {

	protected java.lang.String errorCode;

	protected java.lang.String messageKey;

	protected java.lang.String messageText;

	public CommonsBeansException() {
	}

	/**
	 * @param message
	 *            текст сообщения. Можно использовать как образец сообщения.
	 */
	public CommonsBeansException(String message) {
		super(message);
	}

	/**
	 * @param errorCode
	 *            строковая константа используемая народным банком
	 * @param messageKey
	 *            ключ сообщения. Его можно использовать в качестве ключа к
	 *            resourceBundle
	 * @param messageText
	 *            текст сообщения. Можно использовать как образец сообщения.
	 */
	public CommonsBeansException(String errorCode, String messageKey,
			String messageText) {
		super(messageText);
		this.errorCode = errorCode;
		this.messageKey = messageKey;
		this.messageText = messageText;
	}

	public CommonsBeansException(String errorCode, String messageKey,
			String messageText, Throwable cause) {
		super(messageText, cause);
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
		return getClass().getName() + "[ errorCode " + errorCode
				+ "; messageKey = " + messageKey + "; messageText = "
				+ messageText + " ]";
	}

}