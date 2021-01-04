/*
 * Created on 15.03.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: CObject.java,v 1.2 2016/04/15 10:37:30 dauren_home Exp $
 */
package com.realsoft.commons.beans.dealer.control;

public class CObject implements ICObject {

	private String name;

	private Object object;

	public CObject() {
		super();
	}

	public CObject(String name) {
		super();
		this.name = name;
	}

	public CObject(String name, Object object) {
		super();
		this.name = name;
		this.object = object;
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

}
