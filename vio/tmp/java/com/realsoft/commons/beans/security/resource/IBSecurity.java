/*
 * Created on 03.08.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: IBSecurity.java,v 1.2 2016/04/15 10:37:27 dauren_home Exp $
 */
package com.realsoft.commons.beans.security.resource;

import java.rmi.RemoteException;
import java.util.Map;

import com.realsoft.commons.beans.control.CommonsControlException;

public interface IBSecurity {

	public byte UNACCESIBLE = 0;

	public byte READABLE = 1;

	public byte EDITABLE = 2;

	public byte EXECUTABLE = 3;

	/**
	 * Определяет наличие доступа к таблице
	 * 
	 * @param tableName
	 *            Название таблицы
	 * @return
	 * @throws CommonsControlException
	 * @throws RemoteException
	 */
	public byte checkPermission(Object userName, String tableName)
			throws CommonsControlException;

	/**
	 * Устанавливает возможность редактирования таблицы для пользователя, т.е.
	 * устанавливает режим EDITABLE, READABLE или UNACCESIBLE
	 * 
	 * @param user
	 *            Имя пользователя
	 * @throws CommonsControlException
	 * @throws RemoteException
	 */
	public Map<Object, BSecurityItem> loadSecurity(Object userName)
			throws CommonsControlException;

}
