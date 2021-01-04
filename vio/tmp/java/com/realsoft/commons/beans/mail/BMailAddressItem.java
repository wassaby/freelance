/*
 * Created on 22.02.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BMailAddressItem.java,v 1.2 2016/04/15 10:37:40 dauren_home Exp $
 */
package com.realsoft.commons.beans.mail;

import java.io.Serializable;

public class BMailAddressItem implements Serializable {

	private static final long serialVersionUID = 1L;

	private long emailId;

	private String emailName;

	public BMailAddressItem() {
		super();
	}

	public BMailAddressItem(long emailId, String emailName) {
		super();
		this.emailId = emailId;
		this.emailName = emailName;
	}

	public long getEmailId() {
		return emailId;
	}

	public void setEmailId(long emailId) {
		this.emailId = emailId;
	}

	public String getEmailName() {
		return emailName;
	}

	public void setEmailName(String emailName) {
		this.emailName = emailName;
	}

	public String toString() {
		StringBuffer result = new StringBuffer().append("emailId = ").append(
				emailId).append("; emailName = ").append(emailName);
		return result.toString();
	}
}
