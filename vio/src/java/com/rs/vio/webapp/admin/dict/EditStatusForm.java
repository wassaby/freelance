package com.rs.vio.webapp.admin.dict;

import org.apache.struts.action.ActionForm;

public class EditStatusForm extends ActionForm{
	
	private String name;
	private long id;
	
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
