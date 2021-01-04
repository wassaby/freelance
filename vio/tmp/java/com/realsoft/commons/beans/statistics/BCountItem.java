/*
 * Created on 02.02.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BCountItem.java,v 1.2 2016/04/15 10:37:46 dauren_home Exp $
 */
package com.realsoft.commons.beans.statistics;

import java.io.Serializable;

public class BCountItem implements Serializable {

	private static final long serialVersionUID = 1L;

	private long count;

	private String name;

	private String town;

	public BCountItem(long count, String name, String town) {
		super();
		this.count = count;
		this.name = name;
		this.town = town;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
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
