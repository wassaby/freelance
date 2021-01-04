package com.rs.vio.webapp.admin.report;

import org.apache.struts.action.ActionForm;

public class GetReportsByMonthForm extends ActionForm{
	
	private String month;
	private long page;
	
	public long getPage() {
		return page;
	}

	public void setPage(long page) {
		this.page = page;
	}
	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

}
