/*
 * Created on 28.06.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BRadioItem.java,v 1.2 2016/04/15 10:37:08 dauren_home Exp $
 */
package com.realsoft.commons.beans.control;

import org.apache.commons.lang.builder.EqualsBuilder;

public class BRadioItem implements IBItem {

	private static final long serialVersionUID = -8975650548970263902L;

	private Object id = null;

	private String note = null;

	private Object object = null;

	public BRadioItem(Object id, String note) {
		super();
		this.id = id;
		this.note = note;
	}

	public void setId(Object id) {
		this.id = id;
	}

	public Object getId() {
		return id;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
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
		if (!(other instanceof BRadioItem))
			return false;
		BRadioItem castOther = (BRadioItem) other;
		return new EqualsBuilder().append(this.getId(), castOther.getId())
				.isEquals();
	}

	public String toString() {
		return id.toString() + " = " + note;
	}

}
