/*
 * Created on 01.12.2005
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: IBTariff.java,v 1.1 2014/07/01 11:58:27 dauren_work Exp $
 */
package com.realsoft.commons.beans.trplan;

import java.util.Calendar;
import java.util.List;

import com.realsoft.commons.beans.persist.BPersistException;

public interface IBTariff {
	/**
	 * Получает список тарифных планов.
	 * 
	 * @param accountId
	 *            Идентификатор аккаунта
	 * @return {@link BTariffItem}
	 * @throws BPersistException
	 */
	List getTariffList(long accountId) throws BTariffException;

	/**
	 * В системе bittl13. (Таблица не имеет коментариев db.service_packet_type)
	 * 
	 * @param providerId
	 * @param abonentGroupId
	 * @param areaId
	 * @param areaIdS
	 * @param areaIdR
	 * @param areaIdT
	 * @param deviceGroupId
	 * @param connectTypeId
	 * @return {@link BTariffItem}
	 * @throws BPersistException
	 */
	List getTariffList(long providerId, long abonentGroupId, long areaId,
			long areaIdS, long areaIdR, long areaIdT, long deviceGroupId,
			long connectTypeId) throws BTariffException;

	/**
	 * 
	 * @param accountId
	 *            Идентификатор аккаунта
	 * @return {@link BTariffItem}
	 * @throws BPersistException
	 */
	BTariffItem getCurrentTariff(long accountId) throws BTariffException;

	/**
	 * 
	 * @param tariffId
	 *            Идентификатор тарифного плана
	 * @return {@link BTariffItem}
	 * @throws BPersistException
	 */
	BTariffItem getTariffById(long tariffId) throws BTariffException;

	/**
	 * 
	 * @param accountId
	 *            Идентификатор аккаунта
	 * @param tariffId
	 *            Идентификатор тарифного плана
	 * @param date
	 * @throws BPersistException
	 */
	void setCurrentTariff(long accountId, long tariffId, Calendar date)
			throws BTariffException;

	/**
	 * 
	 * @param accountId
	 *            Идентификатор аккаунта
	 * @return {@link BTariffItem}
	 * @throws BPersistException
	 */
	BTariffItem getCurrentNewTariff(long accountId) throws BTariffException;

	/**
	 * Возвращает тарифный план
	 * 
	 * @param abonentId
	 *            Лицевой счет абонента 135
	 * @param device
	 *            Устройство
	 * @return
	 * @throws BTariffException
	 */
	BTariffItem getTariff(long abonentId, String device)
			throws BTariffException;

}
