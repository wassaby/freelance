package com.realsoft.commons.beans.limit;

import java.util.Date;

/**
 * Компонент для определения и установления минимального аванса. Используется в
 * проекте billing-webdealer/
 * 
 * @author temirbulatov
 * 
 */
public interface IBCreditLimit {
	/**
	 * Получает информацию минимальном авансе аккаунта
	 * 
	 * @param accountId
	 *            Идентификатор аккаунта
	 * @return информация о кредитном лимите аккаунта
	 * @throws BCreditLimitException
	 */
	BCreditLimitItem getCreditLimit(long accountId)
			throws BCreditLimitException;

	/**
	 * Устанавливает новый минимальный аванс.
	 * 
	 * @param contractId
	 *            Идентификатор контракта
	 * @param accountId
	 *            Идентификатор аккаунта
	 * @param providerId
	 *            Идентификатор провайдера
	 * @param contract
	 *            Контракт
	 * @param openDate
	 *            Дата открытия
	 * @param closeDate
	 *            Дата закрытия
	 * @param newLimit
	 *            Новый минимальный аванс
	 * @throws BCreditLimitException
	 */
	public void putNewLimit(long contractId, long accountId, long providerId,
			String contract, Date openDate, Date closeDate, double newLimit)
			throws BCreditLimitException;

	/**
	 * Возвращает кредитный лимит абонента
	 * 
	 * @param abonentId
	 *            Лицевой счет абонента 135
	 * @return
	 * @throws BCreditLimitException
	 */

	BCreditLimitItem getCreditLimitByAbonentId(long abonentId)
			throws BCreditLimitException;

}
