/**
 * IBAutopayment.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 16, 2004 (12:19:44 EST) WSDL2Java emitter.
 */

package com.realsoft.commons.beans.autopayment;

import java.util.List;

import com.realsoft.commons.beans.abonent.BAbonentException;
import com.realsoft.commons.beans.abonent.IBAbonent;
import com.realsoft.commons.beans.registration.BRegistrationException;
import com.realsoft.commons.beans.registration.BRegistrationInfo13;
import com.realsoft.commons.beans.registration.IBRegistration;

/**
 * Компонент для автосписания, т.е. для того, чтобы банк сам осуществлял оплату
 * некоторых счетов абонента за счет абонента
 * 
 * @author temirbulatov
 * 
 */
public interface IBAutopayment extends java.rmi.Remote {

	/**
	 * Осуществляет оплату группы абонентов по десятизначному номеру телефона.
	 * Десятизначный номер телефона состоит из кода города и номера абонента.
	 * 
	 * @param phones
	 *            список телефонов
	 * @return возвращает массив объектов класса {@link BRegistrationInfo13}
	 * @throws {@link BAutopaymentException}
	 *             при ошибках возникающих в компоненте {@link IBAutopayment}
	 * @throws {@link BAbonentException}
	 *             при ошибках возникающих в компоненте {@link IBAbonent}
	 */
	public List<BRegistrationInfo13> registration(List<String> phones)
			throws BAutopaymentException, BAbonentException;

	public List<BRegistrationInfo13> registrationGuid(List<String> guids)
			throws BAutopaymentException, BAbonentException;

	/**
	 * Осуществляет оплату группы абонентов используя данные регистрации
	 * абонентов.
	 * 
	 * @param netSource
	 *            ссылка на источник оплаты абонента
	 * @param subDivision
	 *            подразделение источника
	 * @param payType
	 *            тип оплаты
	 * @param registrationInfo
	 *            массив объектов класса {@link BRegistrationInfo13}
	 * @return возвращает массив объектов класса {@link BAutopaymentItem}
	 * @throws {@link BAutopaymentException}
	 *             при ошибках возникающих в компоненте {@link IBAutopayment}
	 * @throws {@link BRegistrationException}
	 *             при ошибках возникающих в компоненте {@link IBRegistration}
	 * @throws {@link BAbonentException}
	 *             при ошибках возникающих в компоненте {@link IBAbonent}
	 */
	public List<BAutopaymentItem> autopayment(String netSource,
			String subDivision, String payType,
			List<BRegistrationInfo13> registrationInfo)
			throws BAutopaymentException, BRegistrationException,
			BAbonentException;
}
