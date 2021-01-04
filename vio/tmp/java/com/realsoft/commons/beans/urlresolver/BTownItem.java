/*
 * Created on 09.02.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BTownItem.java,v 1.2 2016/04/15 10:37:49 dauren_home Exp $
 */
package com.realsoft.commons.beans.urlresolver;

import java.io.Serializable;

public class BTownItem implements Serializable {

	private static final long serialVersionUID = 1L;

	private String townName;

	private long townId;

	public BTownItem(long id, String townName) {
		super();
		this.townName = townName;
		this.townId = id;
	}

	public long getTownId() {
		return townId;
	}

	public void setTownId(long id) {
		this.townId = id;
	}

	public String getTownName() {
		return townName;
	}

	public void setTownName(String townName) {
		this.townName = townName;
	}

}
