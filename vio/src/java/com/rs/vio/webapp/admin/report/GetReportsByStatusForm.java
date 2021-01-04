package com.rs.vio.webapp.admin.report;

import org.apache.struts.action.ActionForm;

public class GetReportsByStatusForm extends ActionForm{
	
	private long statusId;
	private long startPos;
	private long count;
	
	public long getStartPos() {
		return startPos;
	}

	public void setStartPos(long startPos) {
		this.startPos = startPos;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public long getStatusId() {
		return statusId;
	}

	public void setStatusId(long statusId) {
		this.statusId = statusId;
	}
	
}
