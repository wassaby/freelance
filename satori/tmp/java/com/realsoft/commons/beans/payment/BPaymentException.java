/**
 * CAccountForPeriodException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 16, 2004 (12:19:44 EST) WSDL2Java emitter.
 */

package com.realsoft.commons.beans.payment;

import com.realsoft.commons.beans.CommonsBeansException;

/**
 * Класс генерирующий исключение при ошибках, возникающих в компоненте
 * {@link IBPayment}. Этот класс расширяет класс исключений
 * {@link CommonsBeansException}.
 * 
 * @author temirbulatov
 * 
 */
public class BPaymentException extends CommonsBeansException implements
		java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public BPaymentException() {
	}

	/**
	 * Конструктор класса исключений {@link BPaymentException}
	 * 
	 * @param errorCode
	 *            строковая константа, используемая в народном банке
	 * @param messageKey
	 *            ключ сообщения
	 * @param messageText
	 *            текст сообщения
	 */
	public BPaymentException(String errorCode, String messageKey,
			String messageText) {
		super(errorCode, messageKey, messageText);
	}

	/**
	 * @param errorCode
	 * @param messageKey
	 * @param messageText
	 * @param cause
	 */
	public BPaymentException(String errorCode, String messageKey,
			String messageText, Throwable cause) {
		super(errorCode, messageKey, messageText, cause);
	}

}
