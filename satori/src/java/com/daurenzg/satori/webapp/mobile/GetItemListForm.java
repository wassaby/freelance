package com.daurenzg.satori.webapp.mobile;

import org.apache.struts.action.ActionForm;

public class GetItemListForm extends ActionForm{
	
	private long userId;
	private String langCode;
	
	public String getLangCode() {
		return langCode;
	}

	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
	
}
