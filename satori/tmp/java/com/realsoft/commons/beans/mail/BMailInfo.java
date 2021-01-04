/*
 * Created on 28.12.2005
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BMailInfo.java,v 1.1 2014/07/01 11:58:19 dauren_work Exp $
 */
package com.realsoft.commons.beans.mail;

import java.io.Serializable;
import java.util.List;

public class BMailInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private BMailAddressItem address;

	private List mailInfoList;

	public BMailInfo() {
		super();
	}

	public BMailInfo(BMailAddressItem address) {
		super();
		this.address = address;
	}

	public BMailInfo(BMailAddressItem address, List mailInfoList) {
		super();
		this.address = address;
		this.mailInfoList = mailInfoList;
	}

	public BMailAddressItem getAddress() {
		return address;
	}

	public void setAddress(BMailAddressItem address) {
		this.address = address;
	}

	public List getMailInfoList() {
		return mailInfoList;
	}

	public void setMailInfoList(List mailInfoList) {
		this.mailInfoList = mailInfoList;
	}

}
