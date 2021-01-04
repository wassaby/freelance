/**
 * CAccountForPeriodException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 16, 2004 (12:19:44 EST) WSDL2Java emitter.
 */

package com.realsoft.commons.utils14;

import java.rmi.RemoteException;
import java.text.MessageFormat;

/**
 * Класс генерирующий исключение при ошибках, возникающих в компоненте.
 * 
 * @author temirbulatov
 * 
 */

public class RealsoftException extends RemoteException implements
		java.io.Serializable {

	private static final long serialVersionUID = 0L;

	protected java.lang.String errorCode;

	protected String bundleName;

	protected java.lang.String messageKey;

	protected Object[] parameters;

	protected java.lang.String messageText;

	public RealsoftException() {
	}

	/**
	 * @param message
	 *            текст сообщения. Можно использовать как образец сообщения.
	 */
	public RealsoftException(String message) {
		super(message);
		this.messageText = message;
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
	public RealsoftException(String errorCode, String bundleName,
			String messageKey, Object[] parameters, String messageText) {
		super(messageText);
		this.errorCode = errorCode;
		this.bundleName = bundleName;
		this.messageKey = messageKey;
		this.parameters = parameters;
		this.messageText = messageText;
	}

	public RealsoftException(String errorCode, String bundleName,
			String messageKey, Object[] parameters, String messageText,
			Throwable cause) {
		super(messageText, cause);
		this.errorCode = errorCode;
		this.bundleName = bundleName;
		this.messageKey = messageKey;
		this.parameters = parameters;
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

	public String getBundleName() {
		return bundleName;
	}

	public void setBundleName(String bundleName) {
		this.bundleName = bundleName;
	}

	public Object[] getParameters() {
		return parameters;
	}

	public void setParameters(Object[] parameters) {
		this.parameters = parameters;
	}

	public String toString() {
		// StringBuffer buffer = new StringBuffer();
		// buffer.append(getClass().getName());
		// buffer.append("[ errorCode ").append(errorCode).append("; ");
		// buffer.append("bundleName = ").append(bundleName).append("; ");
		// buffer.append("messageKey = ").append(messageKey).append("; ");
		// if (parameters != null) {
		// buffer.append("parameters { ");
		// for (int i = 0; i < parameters.length; i++) {
		// if (i > 0) {
		// buffer.append(", ");
		// }
		// buffer.append(parameters[i]);
		// }
		// buffer.append(" }; ");
		// }
		// buffer.append("messageText =
		// ").append(MessageFormat.format(messageText, arguments)).append("; ");
		return MessageFormat.format(messageText, parameters);
		// return buffer.toString();
	}

}