/*
 * Created on 25.11.2005
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: IBLogin.java,v 1.2 2016/04/15 10:37:43 dauren_home Exp $
 */
package com.realsoft.commons.beans.login;

import com.realsoft.commons.beans.control.IBModelPanel;

/**
 * Компонент для авторизации пользователя. Используется в проектах:
 * billing-reporter, billing-webdealer, commons-services.
 * 
 * @author temirbulatov
 * 
 */
public interface IBLogin {

	public String MODE_MD5 = "MD5";

	public String MODE_135 = "135";

	/**
	 * 
	 * @param userName
	 *            Логин пользователя
	 * @param password
	 *            Пароль пользователя
	 * @param toDateSum
	 * @return Информация о подключении пользователя
	 * @throws BLoginException
	 */
	BLoginInfo login(String userName, String password, long toDateSum)
			throws BLoginException;

	/**
	 * Получает информацию о подключении пользователя
	 * 
	 * @param userName
	 *            Логин пользователя
	 * @param password
	 *            Пароль пользователя
	 * @param serviceName
	 *            Наименование сервиса, к которому определяется доступ
	 * @return Информация о подключении пользователя
	 * @throws BLoginException
	 */
	BLoginInfo login(String userName, String password, String serviceName)
			throws BLoginException;

	/**
	 * Производит регистрацию клиента, желающего отслеживать состояния
	 * устройств.
	 * 
	 * @param rootPanel
	 *            Модель данных "дерево", которую нужно наполнить.
	 * @return Наполненная модель данных.
	 * @throws BLoginException
	 */

	IBModelPanel loginMonitoringClient(IBModelPanel rootPanel)
			throws BLoginException;

}