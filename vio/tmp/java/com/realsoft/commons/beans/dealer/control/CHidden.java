/*
 * Created on 15.03.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: CHidden.java,v 1.2 2016/04/15 10:37:31 dauren_home Exp $
 */
package com.realsoft.commons.beans.dealer.control;

public class CHidden implements ICHidden {

	private String name;

	private Object value;

	public CHidden() {
		super();
	}

	public CHidden(String name) {
		super();
		this.name = name;
	}

	public CHidden(String name, Object value) {
		super();
		this.name = name;
		this.value = value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Object getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
