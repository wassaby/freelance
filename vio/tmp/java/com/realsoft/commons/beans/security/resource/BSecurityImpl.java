/*
 * Created on 03.08.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BSecurityImpl.java,v 1.2 2016/04/15 10:37:27 dauren_home Exp $
 */
package com.realsoft.commons.beans.security.resource;

import java.util.LinkedHashMap;
import java.util.Map;

public class BSecurityImpl implements IBSecurity {

	private Map<Object, BSecurityItem> securityMap = new LinkedHashMap<Object, BSecurityItem>();

	public BSecurityImpl() {
		super();
	}

	public byte checkPermission(Object user, String tableName) {
		for (Object userName : securityMap.keySet()) {
			if (user.equals(userName)
					&& securityMap.get(userName).getTableName().equals(
							tableName)) {
				return securityMap.get(userName).getAccess();
			}
		}
		return IBSecurity.UNACCESIBLE;
	}

	public Map<Object, BSecurityItem> loadSecurity(Object user) {
		Map<Object, BSecurityItem> securityItems = new LinkedHashMap<Object, BSecurityItem>();
		securityItems.put(user, new BSecurityItem("operator-info-table",
				IBSecurity.READABLE));
		securityItems.put(user, new BSecurityItem("phone-book-table",
				IBSecurity.EDITABLE));
		securityMap.putAll(securityItems);
		return securityItems;
	}

}
