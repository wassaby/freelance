/*
 * Created on 06.03.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: CComboboxItem.java,v 1.2 2016/04/15 10:37:31 dauren_home Exp $
 */
package com.realsoft.commons.beans.dealer.control;

import java.io.Serializable;

public class CComboboxItem implements Serializable {

	private Object id;

	private String name;

	public CComboboxItem(Object id, String name) {
		super();
		this.id = id;
		this.name = name;
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

}
