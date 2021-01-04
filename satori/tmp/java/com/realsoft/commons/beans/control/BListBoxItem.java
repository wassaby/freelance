/*
 * Created on 06.03.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BListBoxItem.java,v 1.1 2014/07/01 11:58:19 dauren_work Exp $
 */
package com.realsoft.commons.beans.control;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;

public class BListBoxItem implements IBItem, Serializable {

	private static final long serialVersionUID = -6761514839379533090L;

	private int num;

	private Object id;

	private String name;

	private Object object = null;

	public BListBoxItem(int num, Object id, String name) {
		super();
		this.num = num;
		this.id = id;
		this.name = name;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
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
		if (!(other instanceof BListBoxItem))
			return false;
		BListBoxItem castOther = (BListBoxItem) other;
		return new EqualsBuilder().append(this.getId(), castOther.getId())
				.append(this.getName(), castOther.getName()).isEquals();
	}

	public String toString() {
		return num + "[" + id.toString() + " = " + name + "]";
	}

}
