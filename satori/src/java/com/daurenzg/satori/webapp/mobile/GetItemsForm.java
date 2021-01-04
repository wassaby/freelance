package com.daurenzg.satori.webapp.mobile;

import org.apache.struts.action.ActionForm;

public class GetItemsForm extends ActionForm{
	
	private long userId;
	private String langCode;
	private String itemIdList;
	
	public String getItemIdList() {
		return itemIdList;
	}

	public void setItemIdList(String itemIdList) {
		this.itemIdList = itemIdList;
	}

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
