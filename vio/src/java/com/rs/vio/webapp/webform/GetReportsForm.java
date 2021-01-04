package com.rs.vio.webapp.webform;

import org.apache.struts.action.ActionForm;

public class GetReportsForm extends ActionForm{
	
	private static final long serialVersionUID = 1L;
	
	private long startPos;
	private long count;
	private String reportType;
	
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
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
	
}
