package com.rs.vio.webapp.admin.dict;

import org.apache.struts.action.ActionForm;

public class DeleteInstanceForm extends ActionForm{
	
	private long instanceId;

	public long getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(long instanceId) {
		this.instanceId = instanceId;
	}

}
