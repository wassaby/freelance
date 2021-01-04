package com.daurenzg.satori.webapp.manager;

import org.apache.struts.action.ActionForm;

public class EditPushUpForwardForm extends ActionForm{
	
	private long langId;
	private long notificationId;

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