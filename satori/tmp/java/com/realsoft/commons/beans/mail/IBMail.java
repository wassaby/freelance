/*
 * Created on 16.11.2005
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: IBMail.java,v 1.1 2014/07/01 11:58:19 dauren_work Exp $
 */
package com.realsoft.commons.beans.mail;

import java.util.List;

import com.realsoft.commons.beans.report.abonent.BReportException;

/**
 * Выполняет операции с почторыми адресами абонентов систем bittl135 и bittl13.
 * Позволяет формировать некоторые почтовыет сообщения, а также позволяет
 * оправлять почтовые сообщения. В компоненте должны быть настроены значения
 * следующих параметров<br/> <code>smtpServer</code>  
 * <code>smtpUser</code>  
 * <code>smtpPassword</code>  
 * <code>mailSourceAddress</code>  
 * <code>mailSourceName</code>
 * 
 * @author dimad
 */
public interface IBMail {
	/**
	 * Формирует отчет о состоянии счета аккаунта и отправляет его на почтовый
	 * адрес абонента.
	 * 
	 * @param phone
	 *            десятизначный номер телефона
	 * @param subjectMessage
	 *            область аккаунта
	 * @param textMessage
	 *            примечание
	 * @param abonentId
	 *            лицевой счет абонента
	 * @return отчет о состоянии счета аккаунта
	 * @throws BMailException
	 * @throws BReportException
	 */
	public BMailInfo sendAccountingStatusMail(String phone,
			String subjectMessage, String textMessage, long abonentId)
			throws BMailException, BReportException;

	/**
	 * Формирует отчет о детальной информации по начислениям абонента и
	 * отправляет его на почтовый адрес абонента. В нее входит информация о
	 * начальном сальдо, начислении, отчислении.
	 * 
	 * @param phone
	 *            десятичный номер телефона
	 * @param subjectMessage
	 *            область аккаунта
	 * @param titleMessage
	 *            примечание
	 * @param abonentId
	 *            лицевой счет абонента
	 * @param billTyte
	 * @param reportDate
	 *            дата формирования отчета
	 * @return отчет о детальной информации по начислениям абонента
	 * @throws BMailException
	 * @throws BReportException
	 */
	public BMailInfo sendDetailChargeMail(String phone, String subjectMessage,
			String titleMessage, long abonentId, long billTyte, long reportDate)
			throws BMailException, BReportException;

	/**
	 * Формирует отчет об оплатах абонента и отправляет его на почтовый адрес
	 * абонента.
	 * 
	 * @param phone
	 *            десятизначный номер телефона
	 * @param subjectMessage
	 *            область аккаунта
	 * @param titleMessage
	 *            примечание
	 * @param abonentId
	 *            лицевой счет аккаунта
	 * @param billTyte
	 * @param reportDate
	 *            дата формирования отчета
	 * @return
	 * @throws BMailException
	 * @throws BReportException
	 */
	public BMailInfo sendDetailPaymentMail(String phone, String subjectMessage,
			String titleMessage, long abonentId, long billTyte, long reportDate)
			throws BMailException, BReportException;

	/**
	 * Отправляет текстовое сообщение по почте в системе bittl13.
	 * 
	 * @param phone
	 *            десятизначный номер телефона
	 * @param abonentId
	 *            лицевой счет абонента
	 * @param subjectMessage
	 *            область аккаунта
	 * @param textMessage
	 *            примечание
	 * @return полный отчет для системы bittl13
	 * @throws BMailException
	 */
	public BMailInfo sendMail13(String phone, long abonentId,
			String subjectMessage, String textMessage) throws BMailException;

	/**
	 * Отправляет текстовое сообщение по почте в системе bittl135, используя
	 * идентификатор аккаунта
	 * 
	 * @param accountId
	 *            идентификатор аккаунта
	 * @param subjectMessage
	 *            область аккаунта
	 * @param textMessage
	 *            примечание
	 * @throws BMailException
	 */
	public void sendMail135(long accountId, String subjectMessage,
			String textMessage) throws BMailException;

	/**
	 * Отправляет текстовое сообщение по почте в системе bittl135, используя
	 * логин аккаунта
	 * 
	 * @param login
	 *            логин аккаунта
	 * @param subjectMessage
	 *            область аккаунта
	 * @param textMessage
	 *            примечание
	 * @throws BMailException
	 */
	public void sendMail135(String login, String subjectMessage,
			String textMessage) throws BMailException;

	/**
	 * Отправляет текстовое сообщение по почте в системе bittl135 всем аккаунтам
	 * данного абонента.
	 * 
	 * @param login
	 *            логин абонента
	 * @param subjectMessage
	 *            область абонента
	 * @param textMessage
	 *            примечание
	 * @throws BMailException
	 */
	public void sendAllMail135(String login, String subjectMessage,
			String textMessage) throws BMailException;

	/**
	 * Посылает текстовое сообщение по указанному e-mail
	 * 
	 * @param email
	 *            указанная почта
	 * @param subjectMessage
	 *            тема сообщения
	 * @param textMessage
	 *            тело сообщения
	 * @throws BMailException
	 */
	public void sendMail(String email, String subjectMessage, String textMessage)
			throws BMailException;

	/**
	 * Получает почтовые дреса всех аккаунтов абонента в системе 135.
	 * 
	 * @param login
	 *            логин абонента
	 * @return адреса аккаунтов
	 * @throws BMailException
	 */
	public List<BMailAddressItem> getAllMailAddress135(String login)
			throws BMailException;

	/**
	 * Получает почтовый адрес абонента по его лицевому счету в системе 135.
	 * 
	 * @param abonentId
	 *            иденитификатор абонента
	 * @return адрес аккаунта
	 * @throws BMailException
	 */
	public BMailAddressItem getMailAddress135(long abonentId)
			throws BMailException;

	/**
	 * Получает почтовый адрес абонента по его аккаунту в системе 135.
	 * 
	 * @param login
	 *            логин абонента
	 * @return адрес аккаунта
	 * @throws BMailException
	 */
	public BMailAddressItem getMailAddress135(String login)
			throws BMailException;

	/**
	 * Получает почтовый адрес абонета в системе bittl13
	 * 
	 * @param abonentId
	 * @return
	 * @throws BMailException
	 */
	public BMailAddressItem getMailAddress13(long abonentId)
			throws BMailException;

	/**
	 * Получает все почтовые адреса абонета в системе bittl13
	 * 
	 * @param abonentId
	 * @return
	 * @throws BMailException
	 */
	public List<BMailAddressItem> getAllMailAddress13(long abonentId)
			throws BMailException;

	/**
	 * Устанавливает почтовый адрес абонента в системе bittl135 по
	 * идентификатору абонента
	 * 
	 * @param abonentId
	 *            лицевой счет абонента
	 * @param login
	 *            логин абюонента
	 * @param email
	 *            почта абонента
	 * @param note
	 *            примечание
	 * @throws BMailException
	 */
	public void setEmail135(long abonentId, String login, String email,
			String note) throws BMailException;

	/**
	 * Устанавливает почтовый адрес абонента в системе bittl135 по
	 * идентификатору WEB-аккаунта абонента
	 * 
	 * @param webAccountId
	 *            Идентификатор WEB-аккаунта абонента
	 * @param email
	 *            почта абонента
	 * @throws BMailException
	 */
	public void setEmail135(long webAccountId, String email)
			throws BMailException;

	/**
	 * Устанавливает почтовый адрес абонента в системе bittl135 по имени
	 * аккаунта
	 * 
	 * @param login
	 *            логин абонента
	 * @param email
	 *            почтый адрес абонента
	 * @param note
	 *            примечание
	 * @throws BMailException
	 */
	public void setEmail135(String login, String email, String note)
			throws BMailException;
}
