/*
 * Created on 03.08.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BSecurityImpl.java,v 1.2 2016/04/15 10:37:39 dauren_home Exp $
 */
package com.realsoft.commons.beans.security.db;

import java.util.ArrayList;
import java.util.List;

import com.realsoft.commons.beans.security.IBSecurity;

public class BSecurityImpl implements IBSecurity {

	private List<BSecurityItem> list = new ArrayList<BSecurityItem>();

	private String user = null;

	public BSecurityImpl() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.security.IBSecurity#checkPermission(java.lang.String)
	 */
	public byte checkPermission(String tableName) {
		for (BSecurityItem securityItem : list) {
			if (securityItem.getTableName().equals(tableName)) {
				return securityItem.getAccess();
			}
		}
		return IBSecurity.UNACCESIBLE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.security.IBSecurity#loadSecurity(java.lang.String)
	 */
	public void loadSecurity(String user) {
		this.user = user;
		list.add(new BSecurityItem("MaterialUom", IBSecurity.EDITABLE));
	}

}
