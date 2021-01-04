/*
 * Created on 28.06.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BCheckBoxItem.java,v 1.1 2014/07/01 11:58:19 dauren_work Exp $
 */
package com.realsoft.commons.beans.control;

import org.apache.commons.lang.builder.EqualsBuilder;

public class BCheckBoxItem implements IBItem {

	private static final long serialVersionUID = 1449981841046207529L;

	private Object id = null;

	private String note = null;

	private Object object = null;

	public BCheckBoxItem(Object id, String note) {
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
		if (!(other instanceof BCheckBoxItem))
			return false;
		BCheckBoxItem castOther = (BCheckBoxItem) other;
		return new EqualsBuilder().append(this.getId(), castOther.getId())
				.isEquals();
	}

	public String toString() {
		return id + " = " + note;
	}

}
