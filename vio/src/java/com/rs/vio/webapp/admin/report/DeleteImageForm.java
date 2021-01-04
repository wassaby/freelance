package com.rs.vio.webapp.admin.report;

import org.apache.struts.action.ActionForm;

public class DeleteImageForm extends ActionForm{
	
	private long reportId;
	private long fileId;
	
	public long getFileId() {
		return fileId;
	}
	public void setFileId(long fileId) {
		this.fileId = fileId;
	}
	public long getReportId() {
		return reportId;
	}
	public void setReportId(long reportId) {
		this.reportId = reportId;
	}
}
