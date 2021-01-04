package com.rs.vio.webapp;

public class RequestObject {
	
	private String reportType;
	private String month;
	private long page;
	private long reportStatusId;
	
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

	public RequestObject(String reportType, String month, long page) {
		super();
		this.reportType = reportType;
		this.month = month;
		this.page = page;
	}

	public RequestObject(long reportStatusId, long page) {
		super();
		this.reportStatusId = reportStatusId;
		this.page = page;
	}
	
	public RequestObject() {
		super();
	}

	public RequestObject(String month) {
		super();
		this.month = month;
	}

	public long getReportStatusId() {
		return reportStatusId;
	}

	public void setReportStatusId(long reportStatusId) {
		this.reportStatusId = reportStatusId;
	}

}
