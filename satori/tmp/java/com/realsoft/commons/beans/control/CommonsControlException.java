/**
 * CAccountForPeriodException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 16, 2004 (12:19:44 EST) WSDL2Java emitter.
 */

package com.realsoft.commons.beans.control;

import com.realsoft.commons.beans.CommonsBeansException;

/**
 * Класс генерирующий исключение при ошибках, возникающих в компоненте.
 * 
 * @author dimad
 * 
 */

public class CommonsControlException extends CommonsBeansException implements
		java.io.Serializable {

	private static final long serialVersionUID = -111054378210695267L;

	public CommonsControlException() {
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
	public CommonsControlException(String errorCode, String messageKey,
			String messageText) {
		super(errorCode, messageKey, messageText);
	}

	public CommonsControlException(String errorCode, String messageKey,
			String messageText, Throwable cause) {
		super(errorCode, messageKey, messageText, cause);
	}

}