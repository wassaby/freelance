package com.rs.vio.webapp.admin.dict;

import org.apache.struts.action.ActionForm;

public class DeleteStatusForm extends ActionForm{
	
	private long statusId;

	public long getStatusId() {
		return statusId;
	}

	public void setStatusId(long statusId) {
		this.statusId = statusId;
	}

}
