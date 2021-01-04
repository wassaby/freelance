/*
 * Created on 11.02.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: IBUtil.java,v 1.1 2014/07/01 11:58:19 dauren_work Exp $
 */
package com.realsoft.commons.beans.util;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.realsoft.commons.beans.abonent.BAreaInfo;
import com.realsoft.commons.beans.abonent.IBAbonent;
import com.realsoft.commons.beans.connections.BConnectorException;
import com.realsoft.commons.beans.request.BRequestException;

public interface IBUtil {
	/**
	 * Получает идентификатор группы устройств.
	 * 
	 * @param deviceGroupName
	 *            Название группы устройств
	 * @return Идетификатор группы устройств
	 * @throws BUtilException
	 */
	long getDeviceGroupId(String deviceGroupName) throws BUtilException;

	/**
	 * Получает список отчетных периодов
	 * 
	 * @return {@link BReportDateItem}
	 * @throws BUtilException
	 */
	List getReportDates() throws BUtilException;

	/**
	 * Получает идентификатор отчетного периода
	 * 
	 * @return Идентификатор отчетного периода
	 * @throws BUtilException
	 */
	Long getCurrentReportDate() throws BUtilException;

	/**
	 * Получает информацию о группе устройств по идентификатору группы
	 * устройств.
	 * 
	 * @param townId
	 *            Идентификатор города
	 * @param deviceGroupId
	 *            Идентификатор групы устройств
	 * @return {@link BDeviceGroupItem}
	 * @throws BUtilException
	 */
	BDeviceGroupItem getDeviceGroupById(long townId, long deviceGroupId)
			throws BUtilException;

	/**
	 * Получает список групп устройств по лицевому счету абонента.
	 * 
	 * @param townId
	 *            Идентификатор города
	 * @param abonentId
	 *            Лицевой счет абонента
	 * @return {@link BDeviceGroupItem}
	 * @throws BUtilException
	 */
	List getDeviceGroupList(long townId, long abonentId) throws BUtilException;

	/**
	 * Получает список данных об НДС группы абонентов.
	 * 
	 * @param abonentGroupId
	 *            Идентификатор группы абонентов
	 * @return {@link BNDSItem}
	 * @throws BUtilException
	 */
	List getNDSList(long abonentGroupId) throws BUtilException;

	/**
	 * Получает данные об НДС по идентификатору НДС.
	 * 
	 * @param ndsId
	 *            Идентификатор НДС
	 * @return {@link BNDSItem}
	 * @throws BUtilException
	 */
	BNDSItem getNDSItem(long ndsId) throws BUtilException;

	/**
	 * Получает ключевое слово по идентификатору доступа.
	 * 
	 * @param orderId
	 *            Идентификатор доступа
	 * @return Ключевое слово
	 * @throws BUtilException
	 */
	String getKeyWord(long orderId) throws BUtilException;

	/**
	 * 
	 * @param townId
	 *            Идентификатор города
	 * @param provideId
	 *            Идентификатор провайдера
	 * @return {@link BAreaInfo}
	 * @throws BUtilException
	 */
	BAreaInfo getAreaByTown(long townId, long provideId) throws BUtilException;

	/**
	 * Получает системную дату из базы данных
	 * 
	 * @return Системная дата
	 * @throws BUtilException
	 */
	Date getDBSysDate() throws BUtilException;

	/**
	 * Получает список услуг доступных данному аккаунту.
	 * 
	 * @param accountId
	 *            Идентификатор аккаунта
	 * @return {@link BServiceListItem}
	 * @throws BUtilException
	 */
	List getServiceList(long accountId) throws BUtilException;

	/**
	 * Получает последнюю дату открытия контракта.
	 * 
	 * @param abonentId
	 *            Лицевой счет абонента
	 * @param providerId
	 *            Идентификатор провайдера
	 * @return Последняя дата открытия контракта
	 * @throws BConnectorException
	 */
	Date getRegistrationDate(long abonentId, long providerId)
			throws BConnectorException;

	/**
	 * Получает ключ касперского.
	 * 
	 * @param radiusUserId
	 *            Идентификатор пользователя радиуса.
	 * @return Ключ
	 * @throws BUtilException
	 */
	String getKasperskyKey(long radiusUserId) throws BUtilException;

	/**
	 * Получает Jmap-ключ.
	 * 
	 * @param radiusUserId
	 *            Идентификатор пользователя радиуса.
	 * @return Ключ
	 * @throws BUtilException
	 */
	String getJmapKey(long radiusUserId) throws BUtilException;

	/**
	 * Получает бесплатный серийный номер Dr.Web (антивирус + антиспам) на 90
	 * дней.
	 * 
	 * @param radiusUserId
	 *            Идентификатор пользователя радиуса.
	 * @return Ключ
	 * @throws BUtilException
	 */
	String getDrWebKey(long radiusUserId) throws BUtilException;

	/**
	 * Позволяет изменить свойство устройства или абонента. Используется в
	 * {@link IBAbonent}
	 * 
	 * @param deviceId
	 *            Идентификатор устройства или абонента
	 * @param propertyId
	 *            Идентификатор свойства, получить которое можно из метода long
	 *            getDeviceGroupId(String deviceGroupName) компоненты IBUtil
	 * @param dateFrom
	 *            Дата с которой изменение свойства вступит в силу, если null,
	 *            то берется текущая дата на сервере Oracle
	 * @param dateTo
	 *            Дата по которую свойство актуально
	 * @param propertyValue
	 *            Значение свойства
	 * @param abnDevice
	 *            Может принимать значения 0 или 1. 0 - влияет на изменение
	 *            свойств усройства, 1 - влияет на изменение свойств абонента.
	 * @param valueId
	 *            Если принимает значение 0, то не учитывается
	 * @param operatorId
	 *            Идентификатор оператора производящего изменение пароля
	 * @throws BUtilException
	 */
	public void changeDeviceProperty(long deviceId, long propertyId,
			Calendar dateFrom, Calendar dateTo, String propertyValue,
			int abnDevice, Long valueId, long operatorId) throws BUtilException;

	/**
	 * Возвращает значение константы из db.all_const
	 * 
	 * @param constName
	 *            Наименование константы
	 * @return Значение константы
	 * @throws BUtilException
	 */
	public Object getConstValue(String constName) throws BUtilException;

	/**
	 * Возвращает значение константы из db.all_const
	 * 
	 * @param code
	 *            Код города, для маршрутизации в нужный ОДТ
	 * @param constName
	 *            Наименование константы
	 * @return Значение константы
	 * @throws BUtilException
	 */
	public Object getConstValue(String code, String constName)
			throws BUtilException;

	/**
	 * Возвращает значение константы из db.all_const
	 * 
	 * @param townId
	 *            ID города, для маршрутизации удаленного вызова.
	 * @param constName
	 *            Наименование константы
	 * @return Значение константы
	 * @throws BUtilException
	 */
	public Object getConstValue(Long townId, String constName)
			throws BUtilException;

	/**
	 * Определяет короткий номер телефона. В каждом филиале короткий номер
	 * (номер без кода города) может быть разной длинны. Используется в
	 * digital-kiosk при создании наряда на Megaline, там нужен номер устройства
	 * без кода города.
	 * 
	 * @param longDevice
	 *            Номер телефона с кодом города.
	 * @return Номер телефона без кодоа города.
	 * @throws BRequestException
	 *             Выбрасывается, если нет такого кода города.
	 */

	String getShortDevice(String longDevice) throws BUtilException;

	/**
	 * Достает из БИТТЛ'а идентификатор региона. Его мы в дальнейшем используем
	 * для обеспечения уникальности epay-транзакций. Полученный regionId
	 * составляет последние 3 цифры идентификатора платежа.
	 * 
	 * @return Идентификатор региона
	 * @throws BUtilException
	 */

	String getRegionId() throws BUtilException;

	/**
	 * Определяет установлена ли на устройстве хотя бы одна из улсуг,
	 * относящихся к "пакетам услуг".
	 * 
	 * @param device
	 *            Наименование устройства (с кодом города или без).
	 * @param servicePacketTypeIdsConstName
	 *            Наименование константы "пакеты услуг", значением которой
	 *            являются перечисленные ч/з запятую ID пакетов услуг.
	 * @param deviceGroupId
	 *            ID группы устройств, к которой относится проверяемое
	 *            устройство.
	 * @param deviceTownId
	 *            ID города, в котором установлено проверяемое устройство.
	 * @return
	 * @throws BUtilException
	 */

	boolean isAvailableServices(String device,
			String servicePacketTypeIdsConstName, long deviceGroupId,
			long deviceTownId) throws BUtilException;

}