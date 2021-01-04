package com.rs.vio.webapp.admin.dict;

import org.apache.struts.action.ActionForm;

public class EditInstanceForm extends ActionForm{
	
	private String name;
	private long instanceId;
	
	public long getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(long instanceId) {
		this.instanceId = instanceId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
