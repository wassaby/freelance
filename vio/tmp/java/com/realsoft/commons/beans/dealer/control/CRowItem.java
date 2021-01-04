/*
 * Created on 06.03.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: CRowItem.java,v 1.2 2016/04/15 10:37:30 dauren_home Exp $
 */
package com.realsoft.commons.beans.dealer.control;

import java.io.Serializable;

public class CRowItem implements Serializable {

	private long id;

	private Object values = null;

	private int currentValue = 0;

	public CRowItem() {
		super();
	}

	public CRowItem(Object values) {
		super();
		this.values = values;
	}

	public CRowItem(long id, Object values) {
		super();
		this.id = id;
		this.values = values;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Object getValues() {
		return values;
	}

	public void setValues(Object values) {
		this.values = values;
	}

}
