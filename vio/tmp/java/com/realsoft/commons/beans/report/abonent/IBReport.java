/**
 * IBReport.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 16, 2004 (12:19:44 EST) WSDL2Java emitter.
 */

package com.realsoft.commons.beans.report.abonent;

import java.util.Date;
import java.util.List;

import com.realsoft.commons.beans.abonent.BAbonentBalanceInfo;
import com.realsoft.commons.beans.abonent.BAbonentException;
import com.realsoft.commons.beans.abonent.IBAbonent;
import com.realsoft.commons.beans.account.IBAccount;
import com.realsoft.commons.beans.mail.IBMail;
import com.realsoft.commons.beans.urlresolver.IBURLResolver;
import com.realsoft.utils.UtilsException;

/**
 * Компонент для генерации отчетов. Используется в проектах: billing-reporter,
 * commons-service. Используется в компонентах: {@link IBAbonent},
 * {@link IBMail}, {@link IBURLResolver}.
 * 
 * @author temirbulatov
 * 
 */
public interface IBReport {

	/**
	 * Возвращает полный отчет в диапазоне дат по определенному номеру абонента.
	 * Используется в веб-сервисах и банкоматах.
	 * 
	 * @param dateFrom
	 *            дата, с которой производилась выборка данных в отчет
	 * @param dateTo
	 *            дата, по которую производилась выборка данных в отчет
	 * @param netSource
	 *            ссылка на источник запроса отчета
	 * @param phone
	 *            десятизначный номер телефона абонента(с кодом города)
	 * @return возвращает список объектов класса {@link BTotalItem}
	 * @throws UtilsException
	 * @throws {@link BReportException}
	 *             при ошибках в компоненте {@link IBReport}
	 */
	public List<BTotalItem> getTotal(Date dateFrom, Date dateTo,
			String netSource, String phone) throws BReportException,
			UtilsException;

	/**
	 * Возвращает полный отчет в диапазоне дат
	 * 
	 * @param dateFrom
	 *            дата, с которой производилась выборка данных в отчет
	 * @param dateTo
	 *            дата, по которую производилась выборка данных в отчет
	 * @param netSource
	 *            ссылка на источник запроса отчета
	 * @return возвращает список объектов класса {@link BTotalItem}
	 * @throws UtilsException
	 * @throws {@link BReportException}
	 *             при ошибках в компоненте {@link IBReport}
	 */
	public List<BTotalItem> getTotal(Date dateFrom, Date dateTo,
			String netSource) throws BReportException, UtilsException;

	/**
	 * Возвращает данные об оплате в диапазоне дат по определенному номеру
	 * абонента. Используется в веб-сервисах и банкоматах.
	 * 
	 * @param dateFrom
	 *            дата, с которой производилась выборка данных в отчет
	 * @param dateTo
	 *            дата, по которую производилась выборка данных в отчет
	 * @param netSource
	 *            ссылка на источник запроса отчета
	 * @param phone
	 *            десятизначный номер телефона абонента(с кодом города)
	 * @return возвращает список объектов класса {@link BPaymentItem}
	 * @throws {@link BReportException}
	 *             при ошибках в компоненте {@link IBReport}
	 */
	public List<BPaymentItem> getPayment(Date dateFrom, Date dateTo,
			String netSource, String phone) throws BReportException;

	/**
	 * Возвращает данные об оплате. Используется в веб-сервисах и банкоматах.
	 * 
	 * @param dateFrom
	 *            дата, с которой производилась выборка данных в отчет
	 * @param dateTo
	 *            дата, по которую производилась выборка данных в отчет
	 * @param netSource
	 *            ссылка на источник запроса отчета
	 * @return возвращает список объектов класса {@link BPaymentItem}
	 * @throws {@link BReportException}
	 *             при ошибках в компоненте {@link IBReport}
	 */
	public List<BPaymentItem> getPayment(Date dateFrom, Date dateTo,
			String netSource) throws BReportException;

	/**
	 * Возвращает некоторые данные об оплате. Используется в сервисах.
	 * 
	 * @param phone
	 *            десятизначный номер телефона(с кодом города)
	 * @param abonentId
	 *            идентификатор абонента
	 * @param billTypeId
	 *            идентификатор типа биллинговой связи
	 * @param reportDateId
	 *            идентификатор отчета за определенную дату
	 * @return возвращает список объектов типа {@link BDetailChargeInfo}
	 * @throws {@link BReportException}
	 *             при ошибках в компоненте {@link IBReport}
	 */
	List<BDetailChargeInfo> getDetailCharge(String phone, long abonentId,
			long billTypeId, long reportDateId) throws BReportException;

	/**
	 * Предназначен для получения начислений абонента. Не учитывается
	 * billTypeId. Метод используется в проекте "информационный киоск".
	 * 
	 * @param phone
	 *            десятизначный номер телефона(с кодом города)
	 * @param abonentId
	 *            идентификатор абонента
	 * @param reportDateId
	 *            идентификатор отчета за определенную дату
	 * @return список объектов типа {@link BDetailChargeInfo}
	 * @throws BReportException
	 *             {@link BReportException}
	 */
	List<BDetailChargeInfo> getDetailChargeNoBillType(String phone,
			long abonentId, long reportDateId) throws BReportException;

	/**
	 * Возвращает некоторые данные об оплате. Используется в проекте
	 * digital-kiosk.
	 * 
	 * @param guid
	 *            guid идентификатор абонента (пример: 17-13020-доп.информация)
	 * @param billTypeId
	 *            идентификатор типа биллинговой связи
	 * @param reportDateId
	 *            идентификатор отчета за определенную дату
	 * @return возвращает список объектов типа {@link BDetailChargeInfo}
	 * @throws {@link BReportException}
	 *             при ошибках в компоненте {@link IBReport}
	 */
	List<BDetailChargeInfo> getDetailCharge(String guid, long billTypeId,
			long reportDateId) throws BReportException;

	/**
	 * 
	 * @param guid
	 *            идентификатор абонента (пример: 17-13020-доп.информация)
	 * @param billTypeId
	 *            идентификатор типа биллинговой связи
	 * @param reportDateId
	 *            идентификатор отчета за определенную дату
	 * @return возвращает список объектов типа {@link BDetailPaymentInfo}
	 * @throws BReportException
	 *             при ошибках в компоненте {@link IBReport}
	 */
	List<BDetailPaymentInfo> getDetailPayment(String guid, long billTypeId,
			long reportDateId) throws BReportException;

	/**
	 * Возвращает дополнительные данные об оплате. Используется в проекте
	 * digital-kiosk.
	 * 
	 * @param phone
	 *            десятизначный номер телефона(с кодом города)
	 * @param abonentId
	 *            идентификатор абонента
	 * @param billTypeId
	 * @param reportDateId
	 *            идентификатор отчета за определенную дату
	 * @return возвращает список компонентов класса {@link BDetailChargeInfo}
	 * @throws {@link BReportException}
	 *             при ошибках в компоненте {@link IBReport}
	 */
	List<BDetailPaymentInfo> getDetailPayment(String phone, long abonentId,
			long billTypeId, long reportDateId) throws BReportException;

	/**
	 * Дает информацию о статусе всех счетов определенного абонента.
	 * Используется в проекте digital-kiosk.
	 * 
	 * @param phone
	 *            десятизначный номер телефона(с кодом города)
	 * @param abonentId
	 *            идентификатор абонента
	 * @return возвращает список объектов класса {@link BAbonentStatusInfo}
	 * @throws {@link BReportException}
	 *             при ошибках в компоненте {@link IBReport}
	 */
	List<BAbonentStatusInfo> getAccountStatus(String phone, long abonentId)
			throws BReportException;

	/**
	 * Дает информацию о статусе всех счетов определенного абонента.
	 * Используется в проекте digital-kiosk.
	 * 
	 * @param guid
	 *            идентификатор абонента (пример: 17-13020-доп.информация)
	 * @return возвращает список объектов класса {@link BAbonentStatusInfo}
	 * @throws {@link BReportException}
	 *             при ошибках в компоненте {@link IBReport}
	 */
	List<BAbonentStatusInfo> getAccountStatus(String guid)
			throws BReportException;

	/**
	 * Возвращает дополнительную информацию по определенному счету за некоторый
	 * пероиод времени. Используется в проекте digital-kiosk.
	 * 
	 * @param accountId
	 *            идентификатор счета
	 * @param abonentId
	 *            идентификатор абонента
	 * @param reportDate
	 *            дата отчета
	 * @return возвращает список объектов класса {@link BAbonentForPeriodItem}
	 * @throws BReportException
	 * @throws {@link BAccountException}
	 *             при ошибках в компоненте {@link IBAccount}
	 */
	List getAccountForPeriodList(long accountId, long abonentId, long reportDate)
			throws BReportException;

	/**
	 * Возвращает информацию о балансе определенного абонента. Возвращает
	 * результат в системе 135.
	 * 
	 * @param abonentId
	 *            идентификатор абонента
	 * @return возвращает объект класса {@link BAbonentBalanceInfo}
	 * @throws {@link BAbonentException}
	 *             при ошибках в компоненте {@link IBAbonent}
	 */
	BAbonentBalanceInfo getAbonentBalance135(long abonentId)
			throws BAbonentException;

	/**
	 * Возвращает информацию о балансе определенного абонента. Возвращает
	 * результат в системе 13.
	 * 
	 * @param phone
	 *            десятизначный номер телефона абонента
	 * @param abonentId
	 *            идентификатор абонента
	 * @return возвращает объект класса {@link BAbonentBalanceInfo}
	 * @throws {@link BAbonentException}
	 *             при ошибках в компоненте {@link IBAbonent}
	 */
	double getAbonentBalance13(String phone, long abonentId)
			throws BReportException;

	/**
	 * 
	 * @param guids
	 *            GUIDS абонента
	 * @return
	 * @throws BReportException
	 */
	double getAbonentBalanceGuid13(String guids) throws BReportException;
}