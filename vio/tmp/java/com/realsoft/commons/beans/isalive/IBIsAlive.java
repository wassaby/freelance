/*
 * Created on 05.12.2005
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: IBIsAlive.java,v 1.2 2016/04/15 10:37:42 dauren_home Exp $
 */
package com.realsoft.commons.beans.isalive;

import com.realsoft.commons.beans.urlresolver.IBURLResolver;

/**
 * Используется в компонентах: {@link IBURLResolver} Используется в проектах:
 * commons-services
 * 
 * @author temirbulatov
 * 
 */
public interface IBIsAlive {
	/**
	 * Получает информацию о состоянии связи со всеми ОДТ.
	 * 
	 * @return список данных о состоянии связи со всеми ОДТ. Т.е. определяет
	 *         работоспособность базы данных всех ОДТ, работоспособность
	 *         веб-серверов всех ОДТ и работоспособность маршрутизатор(а/ов).
	 */
	BIsAliveInfo[] isAlive();

	/**
	 * Получает информацию о состоянии связи с конкретным ОДТ по номеру
	 * телефона. Т.е. определяет работоспособность базы данных ОДТ,
	 * работоспособность веб-сервера ОДТ и работоспособность маршрутизатора.
	 * 
	 * @param phone
	 *            десятизначный номер телефона.
	 * @return информация о состоянии связи с ОДТ.
	 */
	BIsAliveInfo isAliveByPhone(String phone);

	/**
	 * Получает информацию о состоянии связи с конкретным ОДТ по коду города.
	 * Т.е. определяет работоспособность базы данных ОДТ, работоспособность
	 * веб-сервера ОДТ и работоспособность маршрутизатора.
	 * 
	 * @param code
	 *            десятизначный номер телефона.
	 * @return информация о состоянии связи с ОДТ.
	 */
	BIsAliveInfo isAliveByCode(String code);

}