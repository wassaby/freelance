/*
 * Created on 15.03.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: CLabel.java,v 1.1 2014/07/01 11:58:21 dauren_work Exp $
 */
package com.realsoft.commons.beans.dealer.control;

public class CLabel implements ICLabel {

	private String name;

	private String value;

	public CLabel() {
		super();
	}

	public CLabel(String name) {
		super();
		this.name = name;
	}

	public CLabel(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
