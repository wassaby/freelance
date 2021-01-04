/*
 * Created on 11.02.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: IBAbonent.java,v 1.1 2014/07/01 11:58:21 dauren_work Exp $
 */
package com.realsoft.commons.beans.abonent;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.realsoft.commons.beans.apps.IBDealer;
import com.realsoft.commons.beans.autopayment.IBAutopayment;
import com.realsoft.commons.beans.connections.BConnectorException;
import com.realsoft.commons.beans.login.BLoginException;
import com.realsoft.commons.beans.mail.BMailException;
import com.realsoft.commons.beans.payment.IBPayment;
import com.realsoft.commons.beans.registration.BRegistrationException;
import com.realsoft.commons.beans.registration.BRegistrationInfo13;
import com.realsoft.commons.beans.registration.IBRegistration;
import com.realsoft.commons.beans.report.abonent.IBReport;
import com.realsoft.commons.beans.request.BComboBoxItem;
import com.realsoft.commons.beans.request.BRequestException;
import com.realsoft.commons.beans.request.BTariffTypeItem;
import com.realsoft.commons.beans.util.BUtilException;

/**
 * Используется в компонентах: {@link IBDealer}, {@link IBPayment},
 * {@link IBRegistration}, {@link IBReport}, {@link IBAutopayment}.
 * Используется в проектах: billing-operator, billing-reporter,
 * billing-webdealer.
 * 
 * @author temirbulatov
 */
public interface IBAbonent {

	String ABONENT_ID = "abonent_id";

	String ABONENT_NAME = "abonent_name";

	/**
	 * Получает баланс абонента в системе bittl13 на данный момент времени.
	 * Система bittl13 - это система установленная в ОДТ. Номер телефона
	 * используется в маршрутизаторах для определения конкретного ОДТ. лицевой
	 * счет используется в конкретных ОДТ для нахождения информации о конкретном
	 * абоненте. Используется банкоматных системах.
	 * <code>abonentBalance13("3132222000",12)</code>
	 * 
	 * @param phone
	 *            10-и значный номер телефона
	 * @param abonentId
	 *            лицевой счет абонента
	 * @return текущий баланс абонента в системе bittl13
	 * @throws BAbonentException
	 */
	double abonentBalance13(String phone, long abonentId)
			throws BAbonentException;

	/**
	 * Получает баланс абонента в системе bittl13 на первое число месяца дата
	 * которого указана. Т.е. если, например, указать дату 14 февраля 2006 года,
	 * то будет определен баланс абонента на 1 февраля 2006 года. Используется в
	 * банкоматных системах.
	 * 
	 * @param abonentId
	 *            лицевой счет абонента
	 * @return баланс абонента
	 * @throws BAbonentException
	 */
	double getAbonentBalance13(long abonentId) throws BAbonentException;

	/**
	 * Получает информацию об абоненте в системе bittl13 по коду города и номеру
	 * устройства.
	 * 
	 * @param townCode
	 *            Код города
	 * @param device
	 *            Номер устройства
	 * @param dbLinkName
	 *            Наименование строки связи с филиалом
	 * @return
	 * @throws BAbonentException
	 */
	List<BAbonentInfo13> getAbonentInfo(String townCode, String device,
			String dbLinkName) throws BAbonentException;

	/**
	 * Получает информацию о балансе абонента в системе bittl135, т.е. в системе
	 * которая установлена в ДКП.
	 * 
	 * @param abonentId
	 *            лицевой счет абонента
	 * @return информаия о балансе абонента в системе bittl135
	 * @throws BAbonentException
	 */
	BAbonentBalanceInfo abonentBalance135(long abonentId)
			throws BAbonentException;

	/**
	 * Возвращает список всех открытых устройств данного абонента, у которых
	 * connectTypeId равен константе KIOSK_CONNECT_TYPE_PHONE
	 * 
	 * @param abonentId
	 *            Лицевой счет абонента
	 * @return список всех открытых устройств данного абонента
	 * @throws BAbonentException
	 */

	List<BAbonentDeviceItem> getAbonentDevices(long abonentId)
			throws BAbonentException;

	/**
	 * Находит все устройства абонента.
	 * 
	 * @param abonentId
	 *            Лицевой счет абонента
	 * @param dbLinkName
	 *            Строка связи с филиалом
	 * @return
	 * @throws BAbonentException
	 * @throws SQLException
	 */
	List<BAbonentDeviceItem> getAbonentDevices(long abonentId, String dbLinkName)
			throws BAbonentException, SQLException;

	/**
	 * Возвращает список всех открытых устройств данного абонента, у которых
	 * connectTypeId равен константе KIOSK_CONNECT_TYPE_PHONE
	 * 
	 * @param abonentGuid
	 *            Guid абонента
	 * @return список всех открытых устройств данного абонента
	 * @throws BAbonentException
	 */

	List<BAbonentDeviceItem> getAbonentDevices(String abonentGuid)
			throws BAbonentException;

	/**
	 * 
	 * @param abonentId
	 *            ID абонента
	 * @return
	 * @throws BAbonentException
	 */
	List<BAbonentDebitItem> getAbonentDebitList(long abonentId)
			throws BAbonentException;

	/**
	 * Возвращает информацию о задолженности абонента
	 * 
	 * @param abonentId
	 *            Лицевой счет
	 * @param dbLink
	 *            Строка связи с базой данных филиала
	 * @return
	 * @throws BAbonentException
	 */
	public List<BAbonentDebitItem> getAbonentDebitList(long abonentId,
			String dbLink) throws BAbonentException;

	/**
	 * Получает информацию в системе bittl13 об абоненте по его лицевому счету.
	 * Идентификатор города берется из файла code-url-mapping.xml. По
	 * идентификатору города определяется конкретное ОДТ. По номеру телефона и
	 * лицевому счету абонента определяется вся информация абонента.
	 * 
	 * @param townId
	 *            идентификатор города
	 * @param phone
	 *            10-и значный номер телефона
	 * @param abonentId
	 *            лицевой счет абонента
	 * @return информация об абоненте
	 * @throws BAbonentException
	 * @throws BRegistrationException
	 * @throws BRequestException
	 * @throws BMailException
	 */

	BAbonentInfo13 getAbonentInfo13ById(long townId, String phone,
			long abonentId) throws BAbonentException, BRegistrationException,
			BRequestException, BMailException;

	/**
	 * Получает информацию в системе bittl13 об абоненте по его лицевому счету.
	 * Метод используется сервисами для портала.
	 * 
	 * @param abonentId
	 *            лицевой счет абонента.
	 * @return
	 * @throws BAbonentException
	 * @throws BRegistrationException
	 * @throws BRequestException
	 * @throws BMailException
	 */

	BAbonentInfo13 getAbonentInfo13ById(long abonentId)
			throws BAbonentException, BRegistrationException,
			BRequestException, BMailException;

	String getAbonentName13ById(String phone, long abonentId)
			throws BAbonentException, BRegistrationException,
			BRequestException, BMailException;

	/**
	 * Получает информацию в системе bittl13 об абоненте по существующему наряду
	 * на устройство.
	 * 
	 * @param orderId
	 *            ID наряда на устройство
	 * @return
	 * @throws BAbonentException
	 * @throws BRegistrationException
	 * @throws BRequestException
	 * @throws BMailException
	 */
	BAbonentInfo13 getAbonentInfo13ByExistingOrder(long orderId)
			throws BAbonentException, BRegistrationException,
			BRequestException, BMailException;

	/**
	 * Возвращает адрес для доставки счета (digital-kiosk)
	 * 
	 * @param abonentGiud
	 *            Giud абонента
	 * @return адрес для доставки счета
	 * @throws BAbonentException
	 */

	String getDeliveryAddress(long abonentId) throws BAbonentException;

	/**
	 * Получает информацию в системе bittl13 об абоненте по его имени.
	 * Идентификатор города берется из файла code-url-mapping.xml. По
	 * идентификатору города определяется конкретное ОДТ. По номеру телефона и
	 * имени абонента определяется вся информация абонента.
	 * 
	 * @param townId
	 *            идентификатор города
	 * @param phone
	 *            10-и значный номер телефона
	 * @param name
	 *            имя абонента
	 * @return информация об абоненте
	 * @throws BAbonentException
	 * @throws BRegistrationException
	 * @throws BRequestException
	 * @throws BMailException
	 */
	BAbonentInfo13 getAbonentInfo13ByName(long townId, String phone, String name)
			throws BAbonentException, BRegistrationException,
			BRequestException, BMailException;

	/**
	 * Возвращает список абонентов, с которыми можно работать данному оператору.
	 * 
	 * @param abonentId
	 *            лицевой счет абонента
	 * @return список заявок абонента
	 * @throws BAbonentException
	 */
	List<BAbonentRequestsItem> getAbonentRequestsList(long abonentId)
			throws BAbonentException;

	/**
	 * Для получения контактной информации абонента. Используется в приложении
	 * "Веб-статистика" на странице "Регистрация заявки"
	 * 
	 * @param AbonentId
	 *            лицевой счет абонента
	 * @return констактная информация абонента
	 * @throws BAbonentException
	 * 
	 */
	List<BConstantlyDataItem> getAbonentConstantlyData(long AbonentId)
			throws BAbonentException;

	/**
	 * Получает идентификатор абонента по логину одного из его аккаунтов.
	 * Используется в bittl135.
	 * 
	 * @param loginName
	 *            логин абонента
	 * @return лицевой счет абонента
	 * @throws BAbonentException
	 */
	long getAbonentIdByLogin(String loginName) throws BAbonentException;

	/**
	 * Получает список сервисов для абонента.
	 * 
	 * @param abonentId
	 *            лицевой счет абонента
	 * @return список сервисов
	 * @throws BAbonentException
	 */
	Map<Integer, String> getAbonentServices(long abonentId)
			throws BAbonentException;

	/**
	 * Возвращает список всех абонентов доступных конкретному оператору.
	 * Используется в системе bittl135
	 * 
	 * @param userName
	 *            имя оператора
	 * @return список абонентов
	 * @throws BAbonentException
	 */
	List<BAbonentItem> getAllAbonents(String userName) throws BAbonentException;

	/**
	 * Возвращает список всех улиц для данного города
	 * 
	 * @param townId
	 *            Id города
	 * @return список всех улиц города
	 * @throws BAbonentException
	 */
	List<BComboBoxItem> getAllStreetNames(long townId) throws BAbonentException;

	/**
	 * Возвращает список всех подключений одного абонента. Используется в
	 * системе bittl135
	 * 
	 * @param abonentId
	 *            лицевой счет обонента
	 * @return список соединений
	 * @throws BAbonentException
	 */
	List<BAbonentItem> getAllAbonentConnections(long abonentId)
			throws BAbonentException;

	/**
	 * Получает адрес абонента в системе bittl135 по лицевому счету абонента.
	 * Используется в системе bittl135
	 * 
	 * @param abonentId
	 *            лицевой счет бонента
	 * @return адрес абонента
	 * @throws BAbonentException
	 */
	String getAbonentAdress135(long abonentId) throws BAbonentException;

	/**
	 * Используется в billing-operator'e. Для получения "отчета по подключению".
	 * Поле примечание клиента в дополнительной информации.
	 * 
	 * @param abonentId
	 *            лицевой счет
	 * @param connectName
	 *            имя соединения
	 * @return возращает дополнительную информацию об абоненте
	 * @throws BAbonentException
	 */
	String getAbonentNote(long abonentId, String connectName)
			throws BAbonentException;

	/**
	 * Меняет пароль оператора в системе bittl13. Параметр <code>phone</code>
	 * используется в маршрутизаторе, а остальные параметры используются в
	 * конкретном ОДТ. Используется в проектах:
	 * 
	 * @param phone
	 *            10-и значный номер телефона
	 * @param userId
	 *            идентификатор оператора
	 * @param password
	 *            пароль оператора
	 * @param question
	 *            контрольный вопрос
	 * @param answer
	 *            ответ на контрольный вопрос
	 * @param note
	 *            дополнительная информация
	 * @throws BAbonentException
	 */
	void changePwd13(String phone, long userId, String password,
			String question, String answer, String note)
			throws BAbonentException;

	/**
	 * Меняет пароль оператора в системе bittl13. Этот метод используется
	 * методом changePwd13(String phone, long userId, String password, String
	 * question, String answer, String note)
	 * 
	 * @param phone
	 *            10-и значный номер телефона
	 * @param login
	 *            логин оператора
	 * @param password
	 *            пароль оператора
	 * @param question
	 *            контрольный вопрос
	 * @param answer
	 *            ответ на контрольный вопрос
	 * @param note
	 *            дополнительная информация
	 * @throws BAbonentException
	 */
	void changePwd13(String phone, String login, String password,
			String question, String answer, String note)
			throws BAbonentException;

	/**
	 * Изменяет адрес для доставки счета
	 * 
	 * @param abonentId
	 *            id абонента, которому надо сменить адрес
	 * @param newAddress
	 *            новый адрес
	 * @param operatorId
	 *            id оператора
	 * @throws BAbonentException
	 * @throws BUtilException
	 */
	void chandeDeliveryAddress(long abonentId, String newAddress,
			long operatorId) throws BAbonentException, BUtilException;

	/**
	 * Изменяет пароль абонента в системе bittl13
	 * 
	 * @param abonentId
	 *            Лицевой счет абонента
	 * @param dateFrom
	 *            Дата с которой будет действовать новый пароль
	 * @param dateTo
	 *            Дата по которую будет действовать новый пароль
	 * @param password
	 *            Новый пароль абонента
	 * @param operatorId
	 *            Идентификатор операторы производящего изменение пароля
	 * @throws BAbonentException
	 * @throws BUtilException
	 */
	void changePwd13(long abonentId, Calendar dateFrom, Calendar dateTo,
			String password, long operatorId) throws BAbonentException,
			BUtilException;

	/**
	 * Получает лицевой счет абонента по его имени. Используется в системе
	 * bittl135
	 * 
	 * @param userName
	 *            имя абонента
	 * @return лицевой счет абонента
	 * @throws BAbonentException
	 */
	long getAbonentId(String userName) throws BAbonentException;

	/**
	 * Регистрирует заявки. Используется в проекте billing-reporter.
	 * 
	 * @param abonentId
	 *            лицевой счет абонента
	 * @param serviceId
	 *            идентификатор сервиса
	 * @param rmNote
	 *            описание проблемы
	 * @param cname
	 *            дополнительное имя
	 * @param cphone
	 *            дополнительный телефон
	 * @param cmobile
	 *            дополнитьлельный мобильный телефон
	 * @param cemail
	 *            дополнительный майл
	 * @param cicq
	 *            дополнительное icq
	 * @throws BAbonentException
	 *             Исп. в billing-reporter'e для регистрации заявки.
	 */

	void registerHelpDeskRequest(long abonentId, long serviceId, String rmNote,
			String cname, String cphone, String cmobile, String cemail,
			String cicq) throws BAbonentException;

	/**
	 * Получает имя абонента по его лицевому счету
	 * 
	 * @param abonentId
	 *            лицевой счет абонента
	 * @return имя абонента
	 * @throws BAbonentException
	 */
	String getLoginAbonentId(long abonentId) throws BAbonentException;

	/**
	 * Находит лицевой счет по аккаунту (db.account_usr.id).
	 * 
	 * @param accountId
	 *            идентификатор аккаунта
	 * @return лицевой счет
	 * @throws BAbonentException
	 */
	Map<String, Object> getAbonentId(long accountId) throws BAbonentException;

	/**
	 * Получает адрес абонента по его лицевому счету
	 * 
	 * @param abonentId
	 *            лицевой счет абонента
	 * @return адрес абонента
	 * @throws BConnectorException
	 */
	String getAddress(long abonentId) throws BAbonentException;

	/**
	 * Получает информацию об абоненте в системе bittl135 по идентификатору
	 * аккаунта абонента.
	 * 
	 * @param accountId
	 *            идентификатор аккаунта абонента
	 * @return информация об абоненте в системе bittl135
	 * @throws BAbonentException
	 */
	BAbonentInfo135 getAbonentInfo135(long accountId) throws BAbonentException;

	/**
	 * Получает информацию об абоненте в системе bittl135 по имени аккаунта
	 * абонента.
	 * 
	 * @param accountName
	 *            имя аккаунта абонента
	 * @return информация об абоненте в системе bittl135
	 * @throws BAbonentException
	 */
	BAbonentInfo135 getAbonentInfo135(String accountName)
			throws BAbonentException;

	/**
	 * Получает пароль оператора
	 * 
	 * @param operator
	 *            Логин оператора
	 * @return Пароль оператора
	 * @throws BLoginException
	 * @throws BAbonentException
	 */
	String getOperatorPassword(String operator) throws BAbonentException;

	/**
	 * Получает пароль абонента
	 * 
	 * @param abonentId
	 *            Лицевой счет абонента
	 * @return возращает пароль абонента
	 * @throws BAbonentException
	 * @throws BUtilException
	 */
	String getAbonentPassword(long abonentId) throws BAbonentException,
			BUtilException;

	/**
	 * Производит изменеие информации об абоненте.
	 * 
	 * @param portal
	 *            Объект с информацией об абоненте {@link BAbonentPortalInfo}
	 * @throws BAbonentException
	 */
	void setInfo(BAbonentPortalInfo portal) throws BAbonentException;

	/**
	 * Производит начисление абоненту за распечатку, полученную в информационном
	 * киоске.
	 * 
	 * @param abonentId
	 *            ID абонента
	 * @param device
	 *            Устройство абонента
	 * @param deviceGroupId
	 *            Группа устройств
	 * @param townId
	 *            Город
	 * @param pagesCount
	 *            Количество страниц, нарезанных в процессе печати
	 * @throws BAbonentException
	 */

	double chargeForPrint(long abonentId, String device, long deviceGroupId,
			long townId, long pagesCount) throws BAbonentException;

	/**
	 * Этот метод производит регистрацию абонента.
	 * 
	 * @param guid
	 *            GUID абонента
	 * @param codeWord
	 *            кодовое слово абонента
	 * @return возвращает объект класса {@link BRegistrationInfo13}
	 * @throws {@link BRegistrationException}
	 *             при ошибках возникающих в компоненте {@link IBRegistration}
	 */
	public BAbonentPortalInfo systemRegistration(java.lang.String guid,
			java.lang.String codeWord) throws BRegistrationException;

	/**
	 * Предназначен для получения списка услуг, которые зарегистрированы на
	 * устройство.
	 * 
	 * @param deviceId
	 *            ID устройства
	 * @param townId
	 *            ID города, к которому относится устройство. Необходим для
	 *            маршрутизации в нужное ОДТ
	 * @param servicePacketConstName
	 *            Наименование константы из db.all_const, в которой хранится ID
	 *            пакета услуг ("Абонентская плата", "Услуги цифровых станций" и
	 *            т.д)
	 * @return
	 * @throws BAbonentException
	 */

	List<BTariffTypeItem> getServiceList(long deviceId, long townId,
			String servicePacketConstName) throws BAbonentException;

	/**
	 * Предназначен для получения списка услуг, которые зарегистрированы на
	 * устройство.
	 * 
	 * @param abonentGuid
	 *            GUID абонента, необходим для маршрутизации
	 * @param deviceId
	 *            ID устройства
	 * @param townId
	 *            ID города, к которому относится устройство. Необходим для
	 *            маршрутизации в нужное ОДТ
	 * @param servicePacketConstName
	 *            Наименование константы из db.all_const, в которой хранится ID
	 *            пакета услуг ("Абонентская плата", "Услуги цифровых станций" и
	 *            т.д)
	 * @return
	 * @throws BAbonentException
	 */

	List<BTariffTypeItem> getServiceList(String abonentGuid, long deviceId,
			long townId, String servicePacketConstName)
			throws BAbonentException;

	/**
	 * Позволяет получить id абонента, по устройству. Используется в проекте
	 * client-debit
	 * 
	 * @param device
	 *            6-ти значный номер телефона
	 * @param deviceGroupId
	 *            ID группы устройств, к которой относится данное устройство
	 * @param townId
	 *            ID города, в котором находится данное устройство
	 * @return Лицевой счет, на который зарегистрировано данное устройство
	 * @throws BAbonentException
	 */
	long getAbonentId13(String device, long deviceGroupId, long townId)
			throws BAbonentException;

}