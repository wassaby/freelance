/*
 * Created on 28.06.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BHiddenItem.java,v 1.2 2016/04/15 10:37:08 dauren_home Exp $
 */
package com.realsoft.commons.beans.control;

import org.apache.commons.lang.builder.EqualsBuilder;

public class BHiddenItem implements IBItem {

	private static final long serialVersionUID = 152575072262114226L;

	private Object id = null;

	private Object object = null;

	public BHiddenItem() {
		super();
	}

	public BHiddenItem(Object id) {
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
		if (!(other instanceof BHiddenItem))
			return false;
		BHiddenItem castOther = (BHiddenItem) other;
		return new EqualsBuilder().append(this.getId(), castOther.getId())
				.isEquals();
	}

	public String toString() {
		return id.toString();
	}

}
