package com.daurenzg.satori.webapp.manager;

import org.apache.struts.action.ActionForm;

public class NewPushUpAddForm extends ActionForm{
	
	private long itemId;
	private long dayId;
	private long langId;
	private String content;
	private String time;
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
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
	public long getDayId() {
		return dayId;
	}
	public void setDayId(long dayId) {
		this.dayId = dayId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
