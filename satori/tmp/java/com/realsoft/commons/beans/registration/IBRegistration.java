/**
 * IBRegistration.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 16, 2004 (12:19:44 EST) WSDL2Java emitter.
 */

package com.realsoft.commons.beans.registration;

import java.util.List;

import com.realsoft.commons.beans.abonent.BAbonentException;
import com.realsoft.commons.beans.abonent.IBAbonent;
import com.realsoft.commons.beans.account.IBAccount;
import com.realsoft.commons.beans.autopayment.IBAutopayment;
import com.realsoft.commons.beans.payment.IBPayment;
import com.realsoft.commons.beans.urlresolver.IBURLResolver;
import com.realsoft.commons.beans.util.BUtilException;

/**
 * Компонент для регистрации абонентов. Используется в проектах:
 * billing-operator, commons-services, dealer, rmi-services. Используется в
 * компонентах: {@link IBAbonent}, {@link IBAccount}, {@link IBAutopayment},
 * {@link IBPayment}, {@link IBURLResolver}.
 * 
 * @author temirbulatov
 * 
 */
public interface IBRegistration {

	/**
	 * Производит регистрацию абонента.
	 * 
	 * @param phone
	 *            десятизначный номер телефона(с кодом города)
	 * @param password
	 *            необходимый пароль
	 * @return возвращает объект класса {@link BRegistrationInfo13}
	 * @throws BUtilException
	 * @throws BAbonentException
	 * @throws {@link BRegistrationException}
	 *             при ошибках возникающих в компоненте {@link IBRegistration}
	 * @throws {@link BAbonentException}
	 *             при ошибках возникающих в компоненте {@link IBAbonent}
	 */
	public BRegistrationInfo13 register13(java.lang.String phone,
			java.lang.String password) throws BRegistrationException,
			BUtilException, BAbonentException;

	/**
	 * Производит регистрацию оператора.
	 * 
	 * @param userName
	 *            имя оператора
	 * @param password
	 *            пароль оператора
	 * @return возвращает объект класса {@link BRegistrationInfo13}
	 * @throws {@link BRegistrationException}
	 *             при ошибках возникающих в компоненте {@link IBRegistration}
	 */
	public BRegistrationInfo13 registerOperator(java.lang.String userName,
			java.lang.String password) throws BRegistrationException;

	/**
	 * Осуществляет авторизацию оператора.
	 * 
	 * @param userName
	 *            имя оператора
	 * @param password
	 *            пароль оператора
	 * @return возвращает объект класса {@link BRegistrationInfo13}
	 * @throws {@link BRegistrationException}
	 *             при ошибках возникающих в компоненте {@link IBRegistration}
	 */
	public BRegistrationInfo13 loginOperator(java.lang.String userName,
			java.lang.String password) throws BRegistrationException;

	/**
	 * Осуществляет регистрацию оператора
	 * 
	 * @param townId
	 *            идентификационный ключ города
	 * @param userName
	 *            имя оператора
	 * @param password
	 *            пароль оператора
	 * @return возвращает объект класса {@link BRegistrationInfo13}
	 * @throws {@link BRegistrationException}
	 *             при ошибках возникающих в компоненте {@link IBRegistration}
	 */
	public BRegistrationInfo13 registerOperator(long townId,
			java.lang.String userName, java.lang.String password)
			throws BRegistrationException;

	/**
	 * Осуществляет закрытие сессии оператора.
	 * 
	 * @param abonentId
	 *            идентификатор оператора
	 * @throws {@link BRegistrationException}
	 *             при ошибках возникающих в компоненте {@link IBRegistration}
	 * @throws {@link BAccountException}
	 *             при ошибках возникающих в компоненте {@link IBAccount}
	 */
	public void unRegisterOperator(long abonentId)
			throws BRegistrationException;

	/**
	 * Этот метод осуществляет закрытие сессии оператора.
	 * 
	 * @param townId
	 *            идентификатор города
	 * @param abonentId
	 *            идентификатор оператора
	 * @throws {@link BRegistrationException}
	 *             при ошибках возникающих в компоненте {@link IBRegistration}
	 */
	public void unRegisterOperator(long townId, long abonentId)
			throws BRegistrationException;

	/**
	 * Этот метод производит регистрацию абонента.
	 * 
	 * @param phone
	 *            десятизначный номер телефона
	 * @return возвращает объект класса {@link BRegistrationInfo13}
	 * @throws {@link BRegistrationException}
	 *             при ошибках возникающих в компоненте {@link IBRegistration}
	 */
	public BRegistrationInfo13 register13(java.lang.String phone)
			throws BRegistrationException;

	// /**
	// * Этот метод производит регистрацию абонента.
	// *
	// * @param guid
	// * GUID абонента
	// * @param codeWord
	// * кодовое слово абонента
	// * @return возвращает объект класса {@link BRegistrationInfo13}
	// * @throws {@link BRegistrationException}
	// * при ошибках возникающих в компоненте {@link IBRegistration}
	// */
	// public BAbonentPortalInfo register13Guid(java.lang.String
	// guid,java.lang.String codeWord)
	// throws BRegistrationException;

	/**
	 * Этот метод производит регистрацию нескольких абонентов.
	 * 
	 * @param phones
	 *            номера абонентов
	 * @return массив объектов классов {@link BRegistrationInfo13}
	 * @throws {@link BRegistrationException}
	 *             при ошибках возникающих в компоненте {@link IBRegistration}
	 */
	public List register13(List phones) throws BRegistrationException;
}
