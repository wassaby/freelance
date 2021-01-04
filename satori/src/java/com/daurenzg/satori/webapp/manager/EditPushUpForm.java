package com.daurenzg.satori.webapp.manager;

import org.apache.struts.action.ActionForm;

/**
 * @author Dauren
 *
 */
public class EditPushUpForm extends ActionForm{
	
	private long langId;
	private long notificationId;
	private String text;
	private String time;
	
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public long getLangId() {
		return langId;
	}

	public void setLangId(long langId) {
		this.langId = langId;
	}

	public long getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(long notificationId) {
		this.notificationId = notificationId;
	}


}