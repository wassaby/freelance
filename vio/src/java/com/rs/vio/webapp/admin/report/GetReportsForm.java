package com.rs.vio.webapp.admin.report;

import org.apache.struts.action.ActionForm;

public class GetReportsForm extends ActionForm{
	
	private static final long serialVersionUID = 1L;
	
	private long page;

	public long getPage() {
		return page;
	}

	public void setPage(long page) {
		this.page = page;
	}
	
}
