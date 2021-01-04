/*
 * Created on 06.03.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: CText.java,v 1.2 2016/04/15 10:37:30 dauren_home Exp $
 */
package com.realsoft.commons.beans.dealer.control;

public class CText implements ICControl {

	private String name = null;

	private String value = null;

	public CText() {
		super();
	}

	public CText(String name) {
		super();
		this.name = name;
	}

	public CText(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
