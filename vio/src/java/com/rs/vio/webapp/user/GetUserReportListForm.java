package com.rs.vio.webapp.user;

import org.apache.struts.action.ActionForm;

public class GetUserReportListForm extends ActionForm{
	
	private static final long serialVersionUID = -2578471504066228212L;
	
	private long userId;
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

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
