/*
 * Created on 28.06.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BCheckItem.java,v 1.1 2014/07/01 11:58:19 dauren_work Exp $
 */
package com.realsoft.commons.beans.control;

import org.apache.commons.lang.builder.EqualsBuilder;

public class BCheckItem implements IBItem {

	private static final long serialVersionUID = 3469899930065641516L;

	private Object id = null;

	private Object object = null;

	public BCheckItem() {
		super();
	}

	public BCheckItem(Object id) {
		super();
		this.id = id;
	}

	public void setId(Object id) {
		this.id = id;
	}

	public Object getId() {
		return id;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if (!(other instanceof BCheckItem))
			return false;
		BCheckItem castOther = (BCheckItem) other;
		return new EqualsBuilder().append(this.getId(), castOther.getId())
				.isEquals();
	}

	public String toString() {
		return id.toString();
	}

}
