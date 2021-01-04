package com.daurenzg.satori.webapp.manager;

import org.apache.struts.action.ActionForm;

public class EditDayForwardForm extends ActionForm{
	
	private long langId;
	private long itemId;
	private long dayId;

	public long getLangId() {
		return langId;
	}

	public void setLangId(long langId) {
		this.langId = langId;
	}

	public long getDayId() {
		return dayId;
	}

	public void setDayId(long dayId) {
		this.dayId = dayId;
	}

	public long getItemId() {
		return itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

}