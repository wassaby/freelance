/*
 * Created on 03.08.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BSecurityItem.java,v 1.1 2014/07/01 11:58:21 dauren_work Exp $
 */
package com.realsoft.commons.beans.security.resource;

import java.io.Serializable;

public class BSecurityItem implements Serializable {

	private static final long serialVersionUID = 3214751743429205262L;

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
