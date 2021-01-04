/*
 * Created on 06.03.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BComboBoxItem.java,v 1.2 2016/04/15 10:37:07 dauren_home Exp $
 */
package com.realsoft.commons.beans.control;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;

public class BComboBoxItem implements IBItem, Serializable {

	private static final long serialVersionUID = -6761514839379533090L;

	private Object id;

	private String name;

	private Object object;

	public BComboBoxItem(Object id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public BComboBoxItem(Object id, String name, Object object) {
		super();
		this.id = id;
		this.name = name;
		this.object = object;
	}

	public BComboBoxItem(IBItem item) {
		super();
		this.id = item.getId();
		this.name = item.toString();
	}

	public Object getId() {
		return id;
	}

	public void setId(Object id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
		if (!(other instanceof BComboBoxItem))
			return false;
		BComboBoxItem castOther = (BComboBoxItem) other;
		return new EqualsBuilder().append(this.getId(), castOther.getId())
				.append(this.getName(), castOther.getName()).isEquals();
	}

	public String toString() {
		return name;
	}

}
