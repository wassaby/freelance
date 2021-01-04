package com.rs.vio.webapp.webform;

import org.apache.struts.action.ActionForm;

public class GetReportsTypeForm extends ActionForm{
	
	private String reportType;
	private String month;
	private long page;
	
	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public long getPage() {
		return page;
	}

	public void setPage(long page) {
		this.page = page;
	}

}
