package com.rs.vio.webapp.user;

import org.apache.struts.action.ActionForm;

public class GetReportsByCategoryForm extends ActionForm{
	
	private static final long serialVersionUID = -2578471504066228212L;
	
	private String reportType;
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

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
