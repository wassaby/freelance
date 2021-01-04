package com.rs.vio.webapp.admin.report;

import org.apache.struts.action.ActionForm;

public class DownloadXLSForm extends ActionForm{
	
	private long reportStatusId;

	public long getReportStatusId() {
		return reportStatusId;
	}

	public void setReportStatusId(long reportStatusId) {
		this.reportStatusId = reportStatusId;
	}

}
