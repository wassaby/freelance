/*
 * Created on 23.12.2005
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: IBInventory.java,v 1.2 2016/04/15 10:37:42 dauren_home Exp $
 */
package com.realsoft.commons.beans.inventory;

import com.realsoft.commons.beans.apps.BDealerImpl;
import com.realsoft.commons.beans.urlresolver.IBURLResolver;

/**
 * Используется в компонентах: {@link IBURLResolver}, {@link BDealerImpl}.
 * Используется в проектах: dealer, rmi-services, commons-services
 * 
 * @author temirbulatov
 * 
 */
public interface IBInventory {
	/**
	 * Получает сообщение о возможности или невозможности подключения абонента к
	 * услугам мегалайн.
	 * 
	 * @param phone
	 *            десятизначный номер телефона
	 * @return текстовое сообщение
	 * @throws BInventoryException
	 */
	String checkDeviceAvailable(String phone) throws BInventoryException;

	/**
	 * Получает сообщение о возможности или невозможности подключения абонента к
	 * услугам мегалайна 135
	 * 
	 * @param phone
	 *            номер телефона
	 * @param dbLink
	 * @return
	 * @throws BInventoryException
	 */
	String checkDeviceAvailable(String phone, String dbLink)
			throws BInventoryException;
}
