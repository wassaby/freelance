/*
 * Created on 15.03.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: CLabel.java,v 1.2 2016/04/15 10:37:30 dauren_home Exp $
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
