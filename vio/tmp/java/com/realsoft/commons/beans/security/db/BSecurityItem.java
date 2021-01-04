/*
 * Created on 03.08.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BSecurityItem.java,v 1.2 2016/04/15 10:37:39 dauren_home Exp $
 */
package com.realsoft.commons.beans.security.db;

import java.io.Serializable;

import com.realsoft.commons.beans.security.IBSecurity;

public class BSecurityItem implements Serializable {

	private static final long serialVersionUID = 1L;

	private String tableName;

	private byte access;

	/**
	 * 
	 * @param tableName
	 *            Название таблицы
	 * @param access
	 *            Режим использования таблицы. Устанавливается одна из констант
	 *            в {@link IBSecurity}
	 */
	public BSecurityItem(String tableName, byte access) {
		super();
		this.tableName = tableName;
		this.access = access;
	}

	public byte getAccess() {
		return access;
	}

	public void setAccess(byte access) {
		this.access = access;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

}
