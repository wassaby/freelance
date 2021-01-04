package com.rs.vio.webapp.test;

import org.apache.struts.action.ActionForm;

/**
 * @author Dauren
 *
 */
public class TestRotateForm extends ActionForm{

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
