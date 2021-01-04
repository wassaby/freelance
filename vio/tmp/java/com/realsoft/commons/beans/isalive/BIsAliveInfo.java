/*
 * Created on 05.12.2005
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BIsAliveInfo.java,v 1.2 2016/04/15 10:37:42 dauren_home Exp $
 */
package com.realsoft.commons.beans.isalive;

import java.io.Serializable;

/**
 * Данные о состоянии связи с конкретным ОДТ.
 * 
 * @author temirbulatov
 * 
 */
public class BIsAliveInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean isAlive;

	private String message;

	private String note;

	private String url;

	public BIsAliveInfo() {
		super();
	}

	/**
	 * 
	 * @param isAlive
	 *            признак наличия связи с базой данных ОДТ
	 * @param message
	 *            сообщение о состоянии связи с ОДТ
	 * @param url
	 *            URL веб-сервера ОДТ
	 */
	public BIsAliveInfo(boolean isAlive, String message, String url) {
		super();
		this.isAlive = isAlive;
		this.message = message;
		this.url = url;
	}

	/**
	 * 
	 * @param isAlive
	 *            признак наличия связи с базой данных ОДТ
	 * @param message
	 *            сообщение о состоянии связи с ОДТ
	 * @param note
	 *            примечание
	 * @param url
	 *            URL веб-сервера ОДТ
	 */
	public BIsAliveInfo(boolean isAlive, String message, String note, String url) {
		super();
		this.isAlive = isAlive;
		this.message = message;
		this.note = note;
		this.url = url;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String toString() {
		return message;
	}

}
