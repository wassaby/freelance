/**
 * IBPayment.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 16, 2004 (12:19:44 EST) WSDL2Java emitter.
 */

package com.realsoft.commons.beans.payment;

import java.util.Calendar;
import java.util.List;

import com.realsoft.commons.beans.abonent.BAbonentException;
import com.realsoft.commons.beans.abonent.IBAbonent;
import com.realsoft.commons.beans.account.IBAccount;
import com.realsoft.commons.beans.autopayment.IBAutopayment;
import com.realsoft.commons.beans.registration.BRegistrationException;
import com.realsoft.commons.beans.registration.IBRegistration;
import com.realsoft.commons.beans.urlresolver.IBURLResolver;
import com.realsoft.utils.UtilsException;

/**
 * Компонент позволяющий абоненту осуществить оплату. Используется в проекте
 * commons-services. Используется в компонентах: {@link IBAbonent},
 * {@link IBAccount}, {@link IBAutopayment}, {@link IBPayment},
 * {@link IBURLResolver}.
 * 
 * @author temirbulatov
 * 
 */
public interface IBPayment {

	/**
	 * Производит оплату абонента за телефон.
	 * 
	 * @param abonentId
	 *            идентификатор абонента
	 * @param amount
	 *            сумма денег
	 * @param currDate
	 *            дата оплаты
	 * @param currency
	 *            вид валюты
	 * @param documentNo
	 *            идентификатор документа
	 * @param netSource
	 *            ссылка на источник осуществления оплаты
	 * @param subDivision
	 *            подразделение источника
	 * @param payType
	 *            тип оплаты
	 * @param terminal
	 *            устройство оплаты
	 * @param transNo
	 *            идентификатор документа
	 * @param phone
	 *            десятизначный номер телефона(с кодом города)
	 * @return возвращает объект класса {@link BPaymentResponce}
	 * @throws UtilsException
	 * @throws {@link BPaymentException}
	 *             при ошибках в компоненте {@link IBPayment}
	 * @throws {@link BRegistrationException}
	 *             при ошибках в компоненте {@link IBRegistration}
	 * @throws {@link BAbonentException}
	 *             при ошибках в компоненте {@link IBAbonent}
	 */
	public BPaymentResponce payment(long abonentId, double amount,
			Calendar currDate, String currency, String documentNo,
			String netSource, String subDivision, String payType,
			String terminal, String transNo, String phone)
			throws BPaymentException, BRegistrationException, BAbonentException;

	/**
	 * 
	 * @param guid
	 *            Идентификатор абонента (пример: 17-13020-госслужащий)
	 * @param amount
	 *            сумма денег
	 * @param currDate
	 *            дата оплаты
	 * @param currency
	 *            вид валюты
	 * @param documentNo
	 *            идентификатор документа
	 * @param netSource
	 *            ссылка на источник осуществления оплаты
	 * @param subDivision
	 *            подразделение источника
	 * @param payType
	 *            тип оплаты
	 * @param terminal
	 *            устройство оплаты
	 * @param transNo
	 *            идентификатор документа
	 * @return возвращает объект класса {@link BPaymentResponce}
	 * @throws UtilsException
	 * @throws {@link BPaymentException}
	 *             при ошибках в компоненте {@link IBPayment}
	 * @throws {@link BRegistrationException}
	 *             при ошибках в компоненте {@link IBRegistration}
	 * @throws {@link BAbonentException}
	 *             при ошибках в компоненте {@link IBAbonent}
	 */
	public BPaymentResponce payment(String guid, double amount,
			Calendar currDate, String currency, String documentNo,
			String netSource, String subDivision, String payType,
			String terminal, String transNo) throws BPaymentException,
			BRegistrationException, BAbonentException;

	/**
	 * Производит оплату абонента за телефон.
	 * 
	 * @param amount
	 *            сумма денег
	 * @param currDate
	 *            дата оплаты
	 * @param currency
	 *            вид валюты
	 * @param documentNo
	 *            идентификатор документа
	 * @param netSource
	 *            ссылка на источник осуществления оплаты
	 * @param subDivision
	 *            подразделение источника
	 * @param payType
	 *            тип оплаты
	 * @param terminal
	 *            устройство оплаты
	 * @param transNo
	 *            идентификатор документа
	 * @param phone
	 *            десятизначный номер телефона(с кодом города)
	 * @return возвращает объект класса {@link BPaymentResponce}
	 * @throws BPaymentException
	 *             при ошибках в компоненте {@link IBPayment}
	 * @throws BRegistrationException
	 *             при ошибках в компоненте {@link IBRegistration}
	 * @throws BAbonentException
	 *             при ошибках в компоненте {@link IBAbonent}
	 */
	public BPaymentResponce payment(double amount, Calendar currDate,
			String currency, String documentNo, String netSource,
			String subDivision, String payType, String terminal,
			String transNo, String phone) throws BPaymentException,
			BRegistrationException, BAbonentException;

	/**
	 * Производит оплату за набор телефонов. Набор телефонов определяется
	 * списком регистраций. Метод используется в сервисе автосписания. Перед
	 * вызовом метеда нужно получить набор регистраций в компоненте registration
	 * 
	 * @param registrations
	 *            набор регистраций.
	 * @param currDate
	 *            текущая дата оплаты
	 * @param currency
	 *            валюта платежа. Оплаты принимаются только в тенге. Значение
	 *            параметра должно быть 'KZT'
	 * @param documentNo
	 *            номер документа при пакетной оплате.
	 * @param netSource
	 *            идентификатор источника платежа.
	 * @param subDivision
	 *            наименование подразделения через которое производтся платеж.
	 * @param payType
	 *            тип платежа, значение параметра может быть произвольным.
	 * @param terminal
	 *            рабочее место(устройство) с которого производился платеж.
	 * @param transNo
	 *            уникальеый номер транзакции.
	 * @return возращает набор объектов содержащих информацию о произведенных
	 *         оплатах.
	 * @throws BPaymentException
	 * @throws BRegistrationException
	 * @throws BAbonentException
	 */
	public List payment(List registrations, Calendar currDate, String currency,
			String documentNo, String netSource, String subDivision,
			String payType, String terminal, String transNo)
			throws BPaymentException, BRegistrationException, BAbonentException;

	/**
	 * Позволяет осуществить, в системе 135, оплату обонента за телефон.
	 * 
	 * @param abonentId
	 *            идентификатор абонента
	 * @param orderDate
	 *            дата оплаты
	 * @param money
	 *            сумма
	 * @param documentNo
	 *            идентификатор документа
	 * @param netSource
	 *            ссылка на источник осуществления оплаты
	 * @param payType
	 *            тип оплаты
	 * @param terminal
	 *            устройство оплаты
	 * @param transNo
	 *            идентификатор документа
	 * @param device
	 *            номер телефона оплаты(с кодом города)
	 * @return возвращает объект класса {@link BPaymentResponce}
	 * @throws {@link BPaymentException}
	 *             при ошибках в компоненте {@link IBPayment}
	 * @throws {@link BRegistrationException}
	 *             при ошибках в компоненте {@link IBRegistration}
	 * @throws {@link BAbonentException}
	 *             при ошибках в компоненте {@link IBAbonent}
	 */
	public BPaymentResponce payment135(long abonentId, Calendar orderDate,
			double money, String documentNo, String netSource, String payType,
			String terminal, String transNo, String device)
			throws BPaymentException, BRegistrationException, BAbonentException;

	public void errorPayment(String device, Calendar payDate,
			Calendar systemDate, String currency, String netSource,
			String subDivision, String terminal, String transNo, double amount,
			String status, String documentNo) throws BPaymentException;
}