package com.realsoft.commons.beans.poihelper;

import java.io.Serializable;

public class BAdvancedDataItem implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;

	private String value;

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

	public BAdvancedDataItem(String name, String value) {
		super();
		// TODO Auto-generated constructor stub
		this.name = name;
		this.value = value;
	}

}
