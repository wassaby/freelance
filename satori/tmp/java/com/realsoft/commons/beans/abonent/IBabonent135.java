/*
 * Created on 22.11.2007 18:44:56
 *
 * Realsoft Ltd.
 * <Last Name First Name>
 * $Id: IBabonent135.java,v 1.1 2014/07/01 11:58:21 dauren_work Exp $
 */
package com.realsoft.commons.beans.abonent;

import java.util.List;

public interface IBabonent135 {

	/**
	 * Получает информацию об абоненте в системе bittl135 по коду города и
	 * номеру устройства.
	 * 
	 * @param townCode
	 *            Код города
	 * @param device
	 *            Номер устройства
	 * @return
	 * @throws BAbonentException
	 */
	List<BAbonentInfo135> getAbonentInfo(String townCode, String device)
			throws BAbonentException;

	/**
	 * Возвращает список действующих устройств абонента 135.
	 * 
	 * @param abonentId
	 *            Лицевой счет абонента в БИТТЛ135
	 * @return
	 * @throws BAbonentException
	 */
	List<BAbonentDeviceItem> getAbonentDevices(long abonentId)
			throws BAbonentException;

	/**
	 * Возвращает текущее начисление
	 * 
	 * @param abonentId
	 *            Лицевой счет абонента 135
	 * @return
	 * @throws BAbonentException
	 */
	double getAbonentCharge(long abonentId) throws BAbonentException;

	/**
	 * Определяет находится ли абонент в сети
	 * 
	 * @param abonentId
	 *            Лицевой счет абонента 135
	 * @param deviceId
	 *            ID устройства
	 * 
	 * @return
	 * @throws BAbonentException
	 */

	boolean isAbonentActive(long abonentId, long deviceId)
			throws BAbonentException;

}
