package com.daurenzg.satori.webapp.manager;

import org.apache.struts.action.ActionForm;

public class EditItemForwardForm extends ActionForm{
	
	private long itemId;
	private long langId;

	public long getLangId() {
		return langId;
	}

	public void setLangId(long langId) {
		this.langId = langId;
	}

	public long getItemId() {
		return itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

}