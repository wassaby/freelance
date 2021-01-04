/*
 * Created on 02.02.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BTrafficItem.java,v 1.2 2016/04/15 10:37:46 dauren_home Exp $
 */
package com.realsoft.commons.beans.statistics;

import java.io.Serializable;

public class BTrafficItem implements Serializable {

	private static final long serialVersionUID = 1L;

	private long acctinputoctets;

	private long acctoutputoctets;

	private String town;

	private String name;

	/**
	 * @param acctinputoctets
	 * @param acctoutputoctets
	 * @param town
	 * @param name
	 */
	public BTrafficItem(long acctinputoctets, long acctoutputoctets,
			String town, String name) {
		super();

		this.acctinputoctets = acctinputoctets;
		this.acctoutputoctets = acctoutputoctets;
		this.town = town;
		this.name = name;
	}

	public long getAcctinputoctets() {
		return acctinputoctets;
	}

	public void setAcctinputoctets(long acctinputoctets) {
		this.acctinputoctets = acctinputoctets;
	}

	public long getAcctoutputoctets() {
		return acctoutputoctets;
	}

	public void setAcctoutputoctets(long acctoutputoctets) {
		this.acctoutputoctets = acctoutputoctets;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

}
