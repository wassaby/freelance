/*
 * Created on 28.06.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BTextItem.java,v 1.1 2014/07/01 11:58:19 dauren_work Exp $
 */
package com.realsoft.commons.beans.control;

import org.apache.commons.lang.builder.EqualsBuilder;

public class BTextItem implements IBItem {

	private static final long serialVersionUID = 6320367416961877711L;

	private Object id = null;

	private Object object = null;

	public BTextItem() {
		super();
	}

	public BTextItem(Object id) {
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
		if (!(other instanceof BTextItem))
			return false;
		BTextItem castOther = (BTextItem) other;
		return new EqualsBuilder().append(this.getId(), castOther.getId())
				.isEquals();
	}

	public String toString() {
		return id.toString();
	}

}
