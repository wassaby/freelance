package com.rs.vio.webapp.admin.report;

import org.apache.struts.action.ActionForm;

/**
 * @author Dauren
 *
 */
public class AdminListForm extends ActionForm{
	
	private static final long serialVersionUID = -2578471504066228212L;

	private long page;
	private long reportStatusId;
	
	public long getReportStatusId() {
		return reportStatusId;
	}

	public void setReportStatusId(long reportStatusId) {
		this.reportStatusId = reportStatusId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public long getPage() {
		return page;
	}

	public void setPage(long page) {
		this.page = page;
	}
	
}
