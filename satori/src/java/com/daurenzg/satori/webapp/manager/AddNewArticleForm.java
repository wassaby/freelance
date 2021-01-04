package com.daurenzg.satori.webapp.manager;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class AddNewArticleForm extends ActionForm{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6876408641120858371L;
	private long notificationId;
	private long langId;
	private FormFile file;
	private String text;
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public long getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(long notificationId) {
		this.notificationId = notificationId;
	}

	public FormFile getFile() {
		return file;
	}

	public void setFile(FormFile file) {
		this.file = file;
	}

	public long getLangId() {
		return langId;
	}

	public void setLangId(long langId) {
		this.langId = langId;
	}

}
