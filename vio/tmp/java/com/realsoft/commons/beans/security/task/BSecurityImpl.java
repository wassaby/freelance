/*
 * Created on 03.08.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BSecurityImpl.java,v 1.2 2016/04/15 10:37:50 dauren_home Exp $
 */
package com.realsoft.commons.beans.security.task;

import java.util.LinkedHashMap;
import java.util.Map;

public class BSecurityImpl implements IBSecurity {

	private Map<Object, BSecurityItem> securityMap = new LinkedHashMap<Object, BSecurityItem>();

	private String user = null;

	public BSecurityImpl() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.security.IBSecurity#checkPermission(java.lang.String)
	 */
	public byte checkPermission(Object userName, String taskName,
			String function) throws BSecurityException {
		for (Object user : securityMap.keySet()) {
			BSecurityItem securityItem = securityMap.get(user);
			if (user.equals(userName)
					&& securityItem.getTaskName().equals(taskName)) {
				return securityItem.getFunction(function).getAccess();
			}
		}
		return IBSecurity.UNACCESIBLE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.security.IBSecurity#loadSecurity(java.lang.String)
	 */
	public Map<Object, BSecurityItem> loadSecurity(Object user) {
		Map<Object, BSecurityItem> securityItems = new LinkedHashMap<Object, BSecurityItem>();
		BSecurityItem item = new BSecurityItem("task1", IBSecurity.ACCESIBLE);
		item.addFunctionSecurity("function1", IBSecurity.EXECUTABLE);
		securityItems.put(user, item);
		securityMap.putAll(securityItems);
		return securityItems;
	}

}
