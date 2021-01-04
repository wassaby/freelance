package com.rs.vio.webapp.webform;

import org.apache.struts.action.ActionForm;

import com.teremok.commons.beans.CommonsBeansConstants;

public class ViewReportForm extends ActionForm{
	
	private static final long serialVersionUID = -2578471504066228212L;

	private long reportId = CommonsBeansConstants.ID_NOT_DEFINED;
	
	public ViewReportForm() {
		super();
	}

	public long getReportId() {
		return reportId;
	}

	public void setReportId(long reportId) {
		this.reportId = reportId;
	}

}
