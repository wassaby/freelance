package com.realsoft.commons.beans.security.task;

import java.util.Map;

public interface IBSecurity {

	public byte UNACCESIBLE = 0;

	public byte ACCESIBLE = 1;

	public byte EXECUTABLE = 2;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.security.IBSecurity#checkPermission(java.lang.String)
	 */
	byte checkPermission(Object userName, String taskName, String function)
			throws BSecurityException;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.security.IBSecurity#loadSecurity(java.lang.String)
	 */
	Map<Object, BSecurityItem> loadSecurity(Object user)
			throws BSecurityException;

}