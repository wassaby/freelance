/*
 * Created on 03.08.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: IBSecurity.java,v 1.2 2016/04/15 10:37:34 dauren_home Exp $
 */
package com.realsoft.commons.beans.security;

public interface IBSecurity {

	public byte UNACCESIBLE = 0;

	public byte READABLE = 1;

	public byte EDITABLE = 2;

	public String SEQURITY_DB = "Db";

	public String SEQURITY_TASK = "Task";

	/**
	 * Определяет наличие доступа к таблице
	 * 
	 * @param tableName
	 *            Название таблицы
	 * @return
	 */
	public byte checkPermission(String tableName);

	/**
	 * Устанавливает возможность редактирования таблицы для пользователя, т.е.
	 * устанавливает режим EDITABLE, READABLE или UNACCESIBLE
	 * 
	 * @param user
	 *            Имя пользователя
	 */
	public void loadSecurity(String user);

}
