/*
 * Created on 11.02.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BComboBoxItem.java,v 1.2 2016/04/15 10:37:26 dauren_home Exp $
 */
package com.realsoft.commons.beans.request;

import java.io.Serializable;

public class BComboBoxItem implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Java-bean для общей страницы создания нарядов. Отображается в
	 * combo-box'e.
	 */

	private long id;

	private String name;

	/**
	 * 
	 * @param id
	 *            Клуч
	 * @param name
	 *            Значение
	 */

	public BComboBoxItem(long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
