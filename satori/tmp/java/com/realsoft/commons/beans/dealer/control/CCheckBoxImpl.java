/*
 * Created on 15.03.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: CCheckBoxImpl.java,v 1.1 2014/07/01 11:58:21 dauren_work Exp $
 */
package com.realsoft.commons.beans.dealer.control;

public class CCheckBoxImpl implements ICCheckBox {

	private String name;

	private boolean value;

	public CCheckBoxImpl() {
		super();
	}

	public CCheckBoxImpl(String name) {
		super();
		this.name = name;
	}

	public CCheckBoxImpl(String name, boolean value) {
		super();
		this.name = name;
		this.value = value;
	}

	public boolean getValue() {
		return value;
	}

	public void setValue(boolean value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
