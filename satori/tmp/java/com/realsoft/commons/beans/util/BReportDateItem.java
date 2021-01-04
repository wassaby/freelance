package com.realsoft.commons.beans.util;

import java.io.Serializable;

public class BReportDateItem implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;

	private String name;

	/**
	 * 
	 * @param id
	 *            Идентификатор отчетного периода
	 * @param name
	 *            Наименование отчетного периода
	 */
	public BReportDateItem(long id, String name) {
		this.id = id;
		this.name = name;
	}

	public long getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}
}
