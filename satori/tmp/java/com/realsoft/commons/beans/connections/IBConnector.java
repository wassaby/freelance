package com.realsoft.commons.beans.connections;

import java.util.List;

/**
 * Используется в проекте billing-reporter
 * 
 * @author temirbulatov
 * 
 */
public interface IBConnector {

	/**
	 * Получает массив данных состоящий из: 1) величина исходящего сальдо 2)
	 * величина начисления 3) величина оплаты
	 * 
	 * @param abonentId
	 *            лицевой счет абонента
	 * @param providerId
	 *            идентификатор абонента
	 * @return Double[]{исходящее сальдо, начислено, оплачено}
	 * @throws BConnectorException
	 */
	Double[] getBillState(long abonentId, long providerId)
			throws BConnectorException;

	/**
	 * Получает значение исходящего сальдо предыдущей оплаты.
	 * 
	 * @param abonentId
	 *            лицевой счет абонента
	 * @param providerId
	 *            идентификатор провайдера
	 * @return величина исходящего сальдо
	 * @throws BConnectorException
	 */
	Double getPreviousPayment(long abonentId, long providerId)
			throws BConnectorException;

	/**
	 * Получает тип валюты
	 * 
	 * @return тип валюты
	 * @throws BConnectorException
	 */
	String getCurrency() throws BConnectorException;

	/**
	 * Получает список соединений конкретного аккаунта
	 * 
	 * @param abonentId
	 *            лицевой счет абонента
	 * @param providerId
	 *            идентификатор провайдера
	 * @param accountId
	 *            идентификатор аккаунта
	 * @return список соединений
	 * @throws BConnectorException
	 */
	List<BConnectorItem> getConnections(long abonentId, long providerId,
			long accountId) throws BConnectorException;

}
