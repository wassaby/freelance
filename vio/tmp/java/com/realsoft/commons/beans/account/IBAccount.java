/*
 * Created on 27.02.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: IBAccount.java,v 1.2 2016/04/15 10:37:31 dauren_home Exp $
 */
package com.realsoft.commons.beans.account;

import java.util.List;

import com.realsoft.commons.beans.abonent.IBAbonent;
import com.realsoft.commons.beans.autopayment.IBAutopayment;
import com.realsoft.commons.beans.login.BLoginException;
import com.realsoft.commons.beans.mail.IBMail;
import com.realsoft.commons.beans.payment.IBPayment;
import com.realsoft.commons.beans.registration.IBRegistration;

/**
 * Используется в компонентах: {@link IBAutopayment}, {@link IBMail},
 * {@link IBPayment}, {@link IBRegistration}, {@link IBAbonent}. Используется
 * в проектах: commons-components, commons-services, billing-reporter,
 * billing-webdealer.
 * 
 * @author dimad
 */
public interface IBAccount {

	/**
	 * Определяет список аккаунтов абонента
	 * 
	 * @param linkType
	 *            идентификатор типа соединения
	 * @return список аккаунтов абонента
	 * @throws BAccountException
	 */
	List<BAccountInfo> getAccountList(long linkType) throws BAccountException;

	/**
	 * Создает аккаунт радиуса и аккаунт веб-приложения. При это в таблице
	 * db.account_usr добавляются две записи. Возвращает ID созданного
	 * WEB-аккаунта.
	 * 
	 * @param pinCode
	 *            пин код
	 * @param loginRad
	 *            логин радиуса
	 * @param passwordRad
	 *            пароль радиуса
	 * @param loginWeb
	 *            логин веб-приложения
	 * @param passwordWeb
	 *            пароль веб-приложения
	 * @param question
	 *            контрольный вопрос
	 * @param answer
	 *            ответ на контрольный вопрос
	 * @param note
	 *            дополнительная информация
	 * @throws BAccountException
	 */
	long createAccount(String pinCode, String loginRad, String passwordRad,
			String loginWeb, String passwordWeb, String question,
			String answer, String note) throws BAccountException;

	/**
	 * Загружает параметры аккаунта
	 * 
	 * @param accountId
	 *            идентификатор аккаунта
	 * @return параметры аккаунта
	 * @throws BAccountException
	 */
	BAccountInfo loadAccountParameters(long accountId) throws BAccountException;

	/**
	 * Изменяет пароль и контрольный вопрос аккаунта
	 * 
	 * @param login
	 *            логин
	 * @param accountId
	 *            идентификатор аккаунта
	 * @param password
	 *            пароль аккаунта
	 * @param question
	 *            контрольный вопрос аккаунта
	 * @param answer
	 *            ответ на контрольный вопрос аккаунта
	 * @param note
	 *            дополнительная информация
	 * @throws BAccountException
	 */
	void editAccount(String login, long accountId, String password,
			String question, String answer, String note)
			throws BAccountException;

	/**
	 * Изменяет пароль аккаунта
	 * 
	 * @param accountId
	 *            идентификатор аккаунта
	 * @param password
	 *            пароль аккаунта
	 * @param schema
	 *            схема
	 * @throws BAccountException
	 */
	void editAccount(long accountId, String password, String schema)
			throws BAccountException;

	/**
	 * Получает число МАС-адресов, с которых было произведено подключение за
	 * последние 5 дней к веб-приложению.
	 * 
	 * @param accountId
	 *            идентификатор аккаунта
	 * @return число МАС-адресов
	 * @throws BAccountException
	 */
	long getLastMacAddresses(long accountId) throws BAccountException;

	/**
	 * Получает идентификатор аккаунта в веб-приложении
	 * 
	 * @param userName
	 *            веб-логин аккаунта
	 * @return идентификатор аккаунта
	 * @throws BLoginException
	 */
	long getAccountId(String userName) throws BLoginException;

	/**
	 * Получает логин аккаунта по его идентификатору
	 * 
	 * @param accountId
	 *            идентификатор аккаунта
	 * @return веб-логин аккаунта
	 * @throws BLoginException
	 */
	String getLoginAccountId(long accountId) throws BLoginException;

	/**
	 * Получает MAC-адрес абонента.
	 * 
	 * @param linkTypeId
	 *            LinkTypeId абонента.
	 * @return BAccountInfo-структуру, соержащую в поле login MAC-адрес
	 *         абонента.
	 * @throws BAccountException
	 */

	BAccountInfo getMacAddress(long linkTypeId) throws BAccountException;

	/**
	 * Возвращает информацию об аккаунте (Radius или Web).
	 * 
	 * @param accountId
	 *            ID аккаунта
	 * @return
	 * @throws BAccountException
	 */
	BAccountInfo getAccountInfo(long accountId) throws BAccountException;

	/**
	 * Устанавливает Mac-адрес абонента.
	 * 
	 * @param accountId
	 *            ID аккаунта абонента. Если мак-адрес еще не заведен, то null.
	 * @param linkTypeId
	 *            ID связки "абонент-устройство".
	 * @param MacAddress
	 *            Мак-адрес абонента.
	 * @param closeMakAddress
	 *            Если true, то закрыть mac-адрес, если false то сменить
	 *            mac-адрес.
	 * @throws BAccountException
	 */

	void setMacAddress(Long accountId, long linkTypeId, String macAddress,
			boolean closeMakAddress) throws BAccountException;

	BAccountInfo createAccount(BAccountInfo accountInfo)
			throws BAccountException;

	double checkIPTvService(BAccountInfo accountInfo, BServiceInfo serviceInfo)
			throws BAccountException;

	void setIPTvService(BAccountInfo accountInfo, BServiceInfo serviceInfo)
			throws BAccountException;

}