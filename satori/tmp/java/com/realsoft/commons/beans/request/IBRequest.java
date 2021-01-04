/*
 * Created on 11.02.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: IBRequest.java,v 1.1 2014/07/01 11:58:21 dauren_work Exp $
 */
package com.realsoft.commons.beans.request;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import com.realsoft.commons.beans.abonent.BAbonentDeviceItem;
import com.realsoft.commons.beans.apps.BDealerImpl;
import com.realsoft.commons.beans.epay.BEpayException;
import com.realsoft.commons.beans.urlresolver.BURLResolverException;
import com.realsoft.commons.beans.util.BUtilException;

/**
 * Производит операции с заявками. Используется в проектах: dealer,
 * rmi-services. Используется в компонентах: {@link BDealerImpl}
 * 
 * @author temirbulatov
 */
public interface IBRequest {
	/**
	 * Получает список нарядов устройств в системе bittl135
	 * 
	 * @param device
	 *            Номер устройства
	 * @param townCode
	 *            Код города
	 * @param countDate
	 *            период, за сколько дней получать список нарядов
	 * @return Список наименований устройств
	 * @throws BRequestException
	 */

	List<BOrderDeviceItem135> getOrderDeviceList135(String device,
			String towncode, Date countDay) throws BRequestException;

	/**
	 * Получает данные(подразделение, результат и дата выполнения) о движении
	 * наряда в системе bittl13
	 * 
	 * @param orderId
	 *            Идентификатор наряда на устройство
	 * @param dblink
	 *            dblink
	 * @return Список наименований устройств
	 * @throws BRequestException
	 */

	List<BRequestOrderStageItem> getWorkPlace(long orderid, String dblink)
			throws BRequestException;

	/**
	 * Получает список нарядов устройств в системе bittl13
	 * 
	 * @param townCode
	 *            Идентификатор города
	 * @param dblink
	 *            dblink
	 * @param countDate
	 *            период, за сколько дней получать список нарядов
	 * @param abonentId
	 *            Лицевой счет абонента БИТТЛ13
	 * @return Список наименований устройств
	 * @throws BRequestException
	 */
	List<BOrderDeviceItem> getOrdersDeviceList13(String townCode,
			String dblink, Date countDay, long abonentId)
			throws BRequestException;

	/**
	 * Получает список наименований устройств в системе bittl13
	 * 
	 * @param townId
	 *            Идентификатор города
	 * @param abonentId
	 *            Лицевой счет абонента
	 * @param deviceGroupId
	 *            Идентификатор группы устройств
	 * @return Список наименований устройств
	 * @throws BRequestException
	 */
	List<BAbonentDeviceItem> getDeviceList(long townId, long abonentId,
			long deviceGroupId) throws BRequestException;

	// db.pkg_sanatel.get_device_info
	/**
	 * Возвращает информацию об устройстве абонента.
	 * 
	 * @param deviceGroupId
	 *            ID группы устройств
	 * @param device
	 *            Наименование устройства
	 * @param townCode
	 *            Код города
	 * @param dbLink
	 *            Строка связи с БД филиала
	 * @return
	 * @throws BRequestException
	 */

	BAbonentDeviceItem getDeviceInfo(long deviceGroupId, String device,
			String townCode, String dbLink) throws BRequestException;

	/**
	 * В системе bittl13 получает типы возможных подключений для данной группы
	 * устройств.
	 * 
	 * @param townId
	 *            Идентификатор города
	 * @param connectType
	 *            Наименование константы, идентифицирующей идентификатор или
	 *            набор идентификаторов таблицы connect_type
	 * @param deviceGroupId
	 *            Идентификатор группы устройств
	 * @return {@link BConnectTypeItem}
	 * @throws BRequestException
	 */
	List<BConnectTypeItem> getConnectTypes(long townId, String connectType,
			long deviceGroupId) throws BRequestException;

	/**
	 * 
	 * @param townId
	 * @param deviceGroupId
	 * @return
	 * @throws BRequestException
	 */
	List<BConnectTypeItem> getConnectTypes(long townId, long deviceGroupId)
			throws BRequestException;

	/**
	 * В системе bittl13 получает типы возможных подключений для данной группы
	 * устройств.
	 * 
	 * @param abonentGiud
	 *            Giud абонента
	 * @param connectType
	 *            Наименование константы, идентифицирующей идентификатор или
	 *            набор идентификаторов таблицы connect_type
	 * @param deviceGroupId
	 *            Идентификатор группы устройств
	 * @return {@link BConnectTypeItem}
	 * @throws BRequestException
	 */
	List<BConnectTypeItem> getConnectTypes(String abonentGiud,
			String connectType, String deviceGroupId) throws BRequestException;

	/**
	 * Создает наряд. Производит запись в таблицу order_device в системе bittl13
	 * 
	 * @param townId
	 *            Идентификатор города. Необходим для совместимости с проектом
	 *            web-dealer
	 * @param device
	 *            Устройство на которое нужно создать наряд
	 * @param abonentId
	 *            Лицевой счет абонента
	 * @param abonentGroupId
	 *            Идентификатор группы абонетов
	 * @param deviceGroupId
	 *            Идентификатор группы устройств
	 * @param abonentTownId
	 *            Идентификатор города абонента
	 * @param connectTypeId
	 *            Идентификатор типа подключения
	 * @param addressId
	 *            Идентификатор адреса
	 * @param userId
	 *            Идентификатор оператора создающего наряд
	 * @param tariffTypeId
	 *            Идентификатор типа тарифа
	 * @param tariff
	 *            Тариф
	 * @param note
	 *            Примечание
	 * @param ndsTypeId
	 *            Идентификатор типа НДС
	 * @param discountTypeId
	 *            Идентификатор типа дисконта
	 * @param servicePacketTypeId
	 *            Идентификатор типа пакета
	 * @param serviceCountGroupId
	 *            Идентификатор группы сервисов
	 * @param discount
	 *            Дисконт
	 * @param nds
	 *            НДС
	 * @param isMegalineCall
	 *            флаг того, что вызов производится для создания наряда на
	 *            установку Мегалайн
	 * @return возращает {@link BOrderDeviceItem}
	 * @throws BRequestException
	 * @throws BUtilException
	 */
	BOrderDeviceItem createOrderDevice(Long townId, String device,
			Long abonentId, Long abonentGroupId, Long deviceGroupId,
			Long abonentTownId, Long connectTypeId, Long addressId,
			Long userId, Long tariffTypeId, Double tariff, String note,
			Long ndsTypeId, Long discountTypeId, Long servicePacketTypeId,
			Long serviceCountGroupId, Double discount, Double nds,
			boolean isMegalineCall) throws BRequestException, BUtilException;

	/**
	 * Возвращает список всех тарифных планов
	 * 
	 * @param spTypeId
	 *            Значение поля name из db.all_const
	 * @return список всех тарифных планов
	 * @throws BRequestException
	 */
	List<BTariffTypeItem> getTariffTypes(String spTypeId)
			throws BRequestException;

	/**
	 * Возвращает список всех тарифных планов в конкретном ОДТ
	 * 
	 * @param spTypeId
	 *            Значение поля name из db.all_const
	 * @param abonentGiuds
	 *            Giud абонента. Данный параметр необходим для нахождения
	 *            нужного web-сервиса в нужном ОДТ.
	 * @return список всех тарифных планов
	 * @throws BRequestException
	 */
	List<BTariffTypeItem> getTariffTypes(String spTypeId, String abonentGiuds)
			throws BRequestException;

	/**
	 * Создает наряд. 1. По наименованию группы устройств выясняет идентификатор
	 * группы устройств <br/> 2. Вызывает метод createOrderDevice. Создает
	 * запись в таблице order_device <br/> 3. Вызывает метод
	 * getRequestDirection. Выясняет направление запроса <br/> 4. Вызывает метод
	 * sendOrder. Оправляет запрос по направлению выясненному ранее
	 * 
	 * @param townId
	 *            Идентификатор города, нужен для совместимости с проектом
	 *            web-dealer
	 * @param device
	 *            Устройство на которое нужно создать наряд
	 * @param abonentId
	 *            Лицевой счет абонента
	 * @param abonentGroupId
	 *            Идентификатор группы абонетов
	 * @param deviceGroupId
	 *            Идентификатор группы устройств
	 * @param abonentTownId
	 *            Идентификатор города абонента
	 * @param connectTypeId
	 *            Идентификатор типа подключения
	 * @param addressId
	 *            Идентификатор адреса
	 * @param userId
	 *            Идентификатор оператора создающего наряд
	 * @param tariffTypeId
	 *            Идентификатор типа тарифа
	 * @param tariff
	 *            Тариф
	 * @param note
	 *            Примечание
	 * @param ndsTypeId
	 *            Идентификатор типа НДС
	 * @param discountTypeId
	 *            Идентификатор типа дисконта
	 * @param servicePacketTypeId
	 *            Идентификатор типа пакета
	 * @param serviceCountGroupId
	 *            Идентификатор группы сервисов
	 * @param discount
	 *            Дисконт
	 * @param nds
	 *            НДС
	 * @param devideGroupName
	 *            Наименовение группы устройств
	 * @param serviceCenterType
	 * @param snote
	 *            Примечание
	 * @param isMegalineCall
	 *            Флаг того, что вызов производится для создания наряда на
	 *            установку Мегалайн
	 * @return
	 * @throws BRequestException
	 * @throws BUtilException
	 * @throws BEpayException
	 */
	BOrderDeviceItem makeOrderDevice(Long townId, String device,
			Long abonentId, Long abonentGroupId, Long abonentTownId,
			Long connectTypeId, Long addressId, Long userId, Long tariffTypeId,
			Double tariff, String note, Long ndsTypeId, Long discountTypeId,
			Long servicePacketTypeId, Long serviceCountGroupId,
			Double discount, Double nds, String devideGroupName,
			String serviceCenterType, String snote, boolean isMegalineCall)
			throws BRequestException, BUtilException, BURLResolverException;

	BOrderDeviceItem makeOrderDevice(Long townId, String device,
			Long abonentId, Long connectTypeId, Long tariffTypeId,
			Double tariff, String note, Long ndsTypeId, Long discountTypeId,
			Long servicePacketTypeId, Long serviceCountGroupId,
			String deviceGroup, Double discount, Double nds, String snote,
			boolean isMegalineCall) throws BRequestException, BUtilException,
			BURLResolverException;

	/**
	 * Выполняет наряд (наряд должен быть закрыт)
	 * 
	 * @param conn
	 *            Connection-сессия, в рамках которой происходит создание и
	 *            выполнение наряда
	 * @param orderId
	 *            id наряда
	 * @return
	 * @throws BRequestException
	 */
	long doOrderDevice(Connection conn, long orderId) throws BRequestException;

	/**
	 * Возвращает значение поля const_value из db.all_const по уникальному полю
	 * db.all_const.name
	 * 
	 * @param constId
	 *            ID констнаты
	 * @return значение поля const_value из db.all_const
	 * @throws BRequestException
	 */

	String getConstValue(String constId) throws BRequestException;

	/**
	 * Создает наряд на усугу, путем вызова db.create_order_service
	 * 
	 * @param abonentId
	 *            Id абонента
	 * @param deviceGroupId
	 *            находим по устройству (db.abonent.device_group_id)
	 * @param connectTypeId
	 *            находим по устройству (db.abonent.connect_type_id)
	 * @param device
	 *            устройство (db.abonent.device)
	 * @param townId
	 *            находим по устройству (db.abonent.town_id)
	 * @param linkTypeId
	 *            находим по устройству (db.abonent.link_type_id)
	 * @param servicePacketTypeId
	 * @param tariffTypeId
	 *            вызываем IBRequest.getTariffTypes(КОНСТАНТА_ИЗ_DB_ALL_CONST) и
	 *            из полученного списка достаем
	 *            BTariffTypeItem.servicePacketTypeId
	 * @param serviceCountGroupId
	 * @param operatorId
	 *            id оператора, который создает наряд
	 * @param serviceCenterType
	 * @param payServicePacketTypeId
	 *            ID пакета услуг (по нему как-то пользователю начислять за
	 *            услугу установки будут)
	 * @param note
	 *            примечание.
	 * @return
	 * @throws BRequestException
	 */

	double makeOrderService(long abonentId, long deviceGroupId,
			long connectTypeId, String device, long townId, long linkTypeId,
			long servicePacketTypeId, long tariffTypeId,
			long serviceCountGroupId, long operatorId,
			String serviceCenterType, long payServicePacketTypeId, String note)
			throws BRequestException, BUtilException;

	/**
	 * Создает наряд на усугу
	 * 
	 * @param abonentGuid
	 *            Guid абонента
	 * @param deviceGroupId
	 *            находим по устройству (db.abonent.device_group_id)
	 * @param connectTypeId
	 *            находим по устройству (db.abonent.connect_type_id)
	 * @param device
	 *            устройство (db.abonent.device)
	 * @param townId
	 *            находим по устройству (db.abonent.town_id)
	 * @param linkTypeId
	 *            находим по устройству (db.abonent.link_type_id)
	 * @param servicePacketTypeId
	 * @param tariffTypeId
	 *            вызываем IBRequest.getTariffTypes(КОНСТАНТА_ИЗ_DB_ALL_CONST) и
	 *            из полученного списка достаем
	 *            BTariffTypeItem.servicePacketTypeId
	 * @param serviceCountGroupId
	 * @param operatorId
	 *            id оператора, который создает наряд
	 * @param serviceCenterType
	 * @param payServicePacketTypeId
	 *            ID пакета услуг (по нему как-то пользователю начислять за
	 *            услугу установки будут)
	 * @param note
	 *            примечание.
	 * @return
	 * @throws BRequestException
	 */

	double makeOrderService(String abonentGuid, long deviceGroupId,
			long connectTypeId, String device, long townId, long linkTypeId,
			long servicePacketTypeId, long tariffTypeId,
			long serviceCountGroupId, String note) throws BRequestException,
			BUtilException;

	/**
	 * Создает заявку для закрытия существующего наряда.
	 * 
	 * @param townId
	 *            Идентификатор города, нужен для совместимости с проектом
	 *            web-dealer
	 * @param orderId
	 *            Идентификатор наряда, который нужно закрыть
	 * @param closeDate
	 *            Дата закрытия наряда
	 * @param userId
	 *            Идентификатор абонента производящего закрытие наряда
	 * @param note
	 *            Примечание
	 * @throws BRequestException
	 * @throws BUtilException
	 */
	public void closeOrderDevice(long townId, long orderId, Date closeDate,
			long userId, String note) throws BRequestException, BUtilException;

	/**
	 * Возращает полный список нарядов доступных данному оператору.
	 * 
	 * @param userId
	 *            Идентицфикатор оператора
	 * @return
	 * @throws BRequestException
	 */
	List<BRequestTableItem> getRequestTableInfo(long userId)
			throws BRequestException;

	/**
	 * Возвращает список нарядов на устройство, зарегистрированных на абонента.
	 * 
	 * @param abonentId
	 *            Лицевой счет абонента.
	 * @return
	 * @throws BRequestException
	 */
	List<BOrderDeviceItem> getOrderDeviceList(long abonentId)
			throws BRequestException;

	/**
	 * Возвращает список нарядов на устройство, зарегистрированных на абонента.
	 * 
	 * @param abonentGuid
	 *            Guid абонента.
	 * @return
	 * @throws BRequestException
	 */
	List<BOrderDeviceItem> getOrderDeviceList(String abonentGuid)
			throws BRequestException;

	/**
	 * Возвращает список нарядов на услугу.
	 * 
	 * @param abonentId
	 *            ID абонента.
	 * @param device
	 *            номер устройства без кода города
	 * @return
	 * @throws BRequestException
	 */
	List<BOrderServiceItem> getOrderServiceList(long abonentId, String device)
			throws BRequestException;

	/**
	 * Возвращает список нарядов на услугу.
	 * 
	 * @param abonentGuid
	 *            GUID абонента, необходим для маршрутизации
	 * @param device
	 *            номер устройства без кода города *
	 * @return
	 * @throws BRequestException
	 */

	List<BOrderServiceItem> getOrderServiceList(String abonentGuid,
			String device) throws BRequestException;

	/**
	 * Возращает список нарядов ограниченный по идентификатору наряда, по
	 * абоненту, по дате открытия и закрытия нарядов, по флагу показывать ли
	 * закрытые наряды
	 * 
	 * @param userId
	 *            Идентицфикатор оператора
	 * @param requestId
	 *            Идентификатор наряда, может быть равен null
	 * @param abonentId
	 *            Лицевой счет абонента, может быть равен null
	 * @param beginOpenDate
	 *            Начальная дата открытия, может быть равен null
	 * @param endOpenDate
	 *            Крнечная дата открытия, может быть равен null
	 * @param beginCloseDate
	 *            Начальня дата закрытия, может быть равен null
	 * @param endCloseDate
	 *            Конечная дата закрытия, может быть равен null
	 * @param abonentName
	 *            Имя абонента, может быть равен null
	 * @param device
	 *            Устройство, может быть равно null
	 * @param showClosedRequests
	 *            Флаг того, показывать ли закрытые наряды
	 * @return список нарядов данного оператора
	 * @throws BRequestException
	 */
	List<BRequestTableItem> getRequestTableInfo(long userId, Long requestId,
			Long abonentId, Date beginOpenDate, Date endOpenDate,
			Date beginCloseDate, Date endCloseDate, String abonentName,
			String device, boolean showClosedRequests) throws BRequestException;

	/**
	 * Возращает набор пунктов назначения, через которые проходит наряд.
	 * 
	 * @param orderId
	 *            Идентификатор наряда
	 * @return набор пунктов назначения, через которые проходит наряд.
	 * @throws BRequestException
	 * @throws BUtilException
	 */
	List<BRequestOrderStageItem> getRequestOrderStagesList(long orderId)
			throws BRequestException, BUtilException;

	/**
	 * информация по конкретному наряду.
	 * 
	 * @param orderId
	 *            идентификатор наряда.
	 * @return
	 * @throws BRequestException
	 */
	BRequestTableItem getRequestInfo(long orderId) throws BRequestException;

	/**
	 * Возращает список направлений наряда
	 * 
	 * @return
	 * @throws BRequestException
	 */
	List<BRequestDirectionItem> getRequestDirectionList()
			throws BRequestException;

	/**
	 * Определяет направление наряда. Пытается определить направление наряда по
	 * группе устройств, указанной в наряде. Если по указанной в наряде группе
	 * устройств направление определить не удается, то попытка определить
	 * направление повторяется, но уже с группой устройств "Основной телефон".
	 * 
	 * @param townIdOrder
	 * @param townIdRoute
	 * @param serviceCenterTypeId
	 * @param device
	 * @param deviceGroupId
	 * @return
	 * @throws BRequestException
	 */
	BRequestDirectionItem getRequestDirection(Long townIdOrder,
			Long townIdRoute, Long serviceCenterTypeId, String device,
			Long deviceGroupId) throws BRequestException, BUtilException;

	/**
	 * Находит пункт назначения наряда в 4 шага: шаг 1 - поиск пункта назначения
	 * (далее ПН) по группе устройств, указанной в наряде. Если ПН найден, то
	 * возвращается найденный ПН; шаг 2 - поиск ПН по группе устройств
	 * "Телефон". ID группы устройств "Телефон" хранится в константе
	 * "PRIMARY_DEVICE_GROUP_ID". если при попытке взять значение константы
	 * "PRIMARY_DEVICE_GROUP_ID" возникает исключение, то считаем что ID группы
	 * устройств "Телефон" = 1. Если ПН найден, то возвращается найденный ПН;
	 * шаг 3 - поиск ПН, который не обслуживает ни одну номерную ёмкость. Если
	 * ПН найден, то возвращается найденный ПН шаг 4 - поиск случайного ПН, в
	 * заданной службе (serviceCenterTypeId).
	 * 
	 * @param townId
	 *            ID города, указанного в наряде.
	 * @param serviceCenterTypeId
	 *            ID службы, в рамках которой осуществляется поиск ПН.
	 * @param device
	 *            Наименование устройства, указанного в наряде.
	 * @param deviceGroupId
	 *            ID группы устройств, указанной в наряде.
	 * @return
	 * @link BRequestDirectionItem
	 * @throws BRequestException
	 *             Возникает, если все 4 шага поиска ПН завершились неудачей.
	 */
	BRequestDirectionItem getWorkplaceId(Long townId, Long serviceCenterTypeId,
			String device, Long deviceGroupId) throws BRequestException;

	/**
	 * Находит пункт назначения наряда в 4 шага: шаг 1 - поиск пункта назначения
	 * (далее ПН) по группе устройств, указанной в наряде. Если ПН найден, то
	 * возвращается найденный ПН; шаг 2 - поиск ПН по группе устройств
	 * "Телефон". ID группы устройств "Телефон" хранится в константе
	 * "PRIMARY_DEVICE_GROUP_ID". если при попытке взять значение константы
	 * "PRIMARY_DEVICE_GROUP_ID" возникает исключение, то считаем что ID группы
	 * устройств "Телефон" = 1. Если ПН найден, то возвращается найденный ПН;
	 * шаг 3 - поиск ПН, который не обслуживает ни одну номерную ёмкость. Если
	 * ПН найден, то возвращается найденный ПН шаг 4 - поиск случайного ПН, в
	 * заданной службе (serviceCenterTypeId).
	 * 
	 * @param townIdForRoute
	 *            ID города, по которому осуществляется маршрутизация удаленного
	 *            вызова.
	 * @param townId
	 *            ID города, указанного в наряде.
	 * @param serviceCenterTypeId
	 *            ID службы, в рамках которой осуществляется поиск ПН.
	 * @param device
	 *            Наименование устройства, указанного в наряде.
	 * @param deviceGroupId
	 *            ID группы устройств, указанной в наряде.
	 * @return
	 * @link BRequestDirectionItem
	 * @throws BRequestException
	 *             Возникает, если все 4 шага поиска ПН завершились неудачей.
	 */
	BRequestDirectionItem getWorkplaceId(Long townIdForRoute, Long townId,
			Long serviceCenterTypeId, String device, Long deviceGroupId)
			throws BRequestException;

	/**
	 * Возвращает ID первого попавшегося рабочего места в службе
	 * 
	 * @param serviceCenterTypeId
	 *            ID службы
	 * @return
	 * @throws BRequestException
	 */
	BRequestDirectionItem getRandomWorkPlace(Long serviceCenterTypeId)
			throws BRequestException;

	/**
	 * Возвращает ID рабочего места в службе, за которым не закреплено ни одной
	 * нумерной емкости
	 * 
	 * @param serviceCenterTypeId
	 *            ID службы
	 * @return
	 * @throws BRequestException
	 */
	BRequestDirectionItem getEmptyWorkPlace(Long serviceCenterTypeId)
			throws BRequestException;

	/**
	 * Отправляет наряд по направлению.
	 * 
	 * @param townId
	 *            Идентификатор города, нужен для совместимости с проектом
	 *            web-dealer
	 * @param orderId
	 *            Идентификатор наряда
	 * @param packId
	 *            Идентификатор пакета
	 * @param directId
	 *            Идентификатор направления
	 * @param directDetail
	 * @param snote
	 *            Примечание
	 * @param suserId
	 *            Идентификатор оператора
	 * @param sourceGroupId
	 *            Идентификатор группы
	 * @param debit
	 * @param isOld
	 * @param oldDirectId
	 * @param sysdateTax
	 * @return
	 * @throws BRequestException
	 */
	long sendOrder(long townId, long orderId, long packId, long directId,
			long directDetail, String snote, Long suserId, Long sourceGroupId,
			double debit, boolean isOld, long oldDirectId, double sysdateTax)
			throws BRequestException;

	/**
	 * Позволяет изменить зарегистрированный почтовый адрес абонента в системе
	 * bittl13
	 * 
	 * @param email
	 *            Новый почтовый адрес
	 * @param abonentId
	 *            Лицевой счет абонента
	 * @param abonentGroupId
	 *            Идентификатор группы абонента
	 * @param deviceGroupId
	 *            Идентификатор группы устройств
	 * @param connectTypeId
	 *            Идентификатор типа соединения
	 * @param addressId
	 *            Идентификатор адреса
	 * @param userId
	 *            Идентификатор оператора производящего изменение почтового
	 *            адреса
	 * @param note
	 *            Примечание абонента Возвращает нам id зарегистрированного
	 *            e-mail.
	 * @throws BRequestException
	 * @throws BUtilException
	 */
	long updateEmail(String email, Long abonentId, Long abonentGroupId,
			Long deviceGroupId, Long townId, Long connectTypeId,
			Long addressId, Long userId, String note) throws BRequestException,
			BUtilException;

	/**
	 * Удаляет e-mail абонента
	 * 
	 * @param mailId
	 *            id е-майла
	 * @param mailMame
	 *            name майла
	 * @param abonentId
	 *            id абонента
	 * @param abonentGroupId
	 *            abonentGroupId абонента
	 * @param deviceGroupId
	 *            deviceGroupId майла
	 * @param townId
	 *            townId майла
	 * @param connectTypeId
	 *            connectTypeId майла
	 * @param addressId
	 *            addressId майла
	 * @param userId
	 *            userId оператора
	 * @throws BRequestException
	 */
	void deleteEmail(long mailId, String mailMame, long abonentId,
			long abonentGroupId, long deviceGroupId, long townId,
			long connectTypeId, long addressId, long userId)
			throws BRequestException, BUtilException;

	/**
	 * Создает наряд на разблокировку телефона.
	 * 
	 * @param abonentId
	 *            ID-абонента
	 * @param deviceGroupId
	 *            группа устройств девайса, который необходимо разблокировать
	 * @param townId
	 *            ID города, в котором находится устройство
	 * @param device
	 *            номер устройства абонента
	 * @param oldConnectTypeId
	 *            старый тип подключения
	 * @param newConnectTypeId
	 *            новый тип подключения
	 * @param operatorId
	 *            ID оператора, который создал наряд
	 * @throws BRequestException
	 */

	long createOrderChangeData(long abonentId, long deviceGroupId, long townId,
			String device, long oldConnectTypeId, long newConnectTypeId,
			Long operatorId) throws BRequestException;

	/**
	 * Создает наряд на разблокировку телефона.
	 * 
	 * @param abonentGuid
	 *            Guid-абонента
	 * @param deviceGroupId
	 *            группа устройств девайса, который необходимо разблокировать
	 * @param townId
	 *            ID города, в котором находится устройство
	 * @param device
	 *            номер устройства абонента
	 * @param oldConnectTypeId
	 *            старый тип подключения
	 * @param newConnectTypeId
	 *            новый тип подключения
	 * @param operatorId
	 *            ID оператора, который создал наряд
	 * @throws BRequestException
	 */
	long createOrderChangeData(String abonentGuid, long deviceGroupId,
			long townId, String device, long oldConnectTypeId,
			long newConnectTypeId, Long operatorId) throws BRequestException;

	/**
	 * Предназначен для нахождения payServicePacketTypeId по actionId. Этот
	 * метод был написан специально для проекта "информационный киоск". Алгоритм
	 * нахождения payServicePacketTypeId был предложен Васильевой и в случае
	 * BRequestException все вопросы к ней.
	 * 
	 * @param actionId
	 *            actionId для ДВО (прописан в db.all_const )
	 * @return Возвращает payServicePacketTypeId, который мы потом используем
	 *         при создании наряда на ДВО (метод makeOrderService()).
	 * @throws BRequestException
	 */

	int getPayServicePacketTypeId(int actionId) throws BRequestException;

	/**
	 * Возвращает ID группы устройств "Megaline" из таблицы соответствий.
	 * 
	 * @param dbLinkName
	 *            Наименование строки связи с БД филиала.
	 * @return
	 * @throws BRequestException
	 */
	long getMegalineDeviceGroupId(String dbLinkName) throws BRequestException;

}