/*
 * Created on 15.03.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: CRadio.java,v 1.1 2014/07/01 11:58:22 dauren_work Exp $
 */
package com.realsoft.commons.beans.dealer.control;

public class CRadio implements ICRadio {

	private String name;

	private Object value;

	public CRadio() {
		super();
	}

	public CRadio(String name) {
		super();
		this.name = name;
	}

	public CRadio(String name, Object value) {
		super();
		this.name = name;
		this.value = value;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
