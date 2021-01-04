package com.rs.vio.webapp.admin.report;

import org.apache.struts.action.ActionForm;

public class RejectReportForm extends ActionForm{
	
	private long reportId;
	
	public long getReportId() {
		return reportId;
	}
	public void setReportId(long reportId) {
		this.reportId = reportId;
	}
}
