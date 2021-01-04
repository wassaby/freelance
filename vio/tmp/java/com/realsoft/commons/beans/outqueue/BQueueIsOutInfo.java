/*
 * Created on 05.12.2005
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BQueueIsOutInfo.java,v 1.2 2016/04/15 10:37:34 dauren_home Exp $
 */
package com.realsoft.commons.beans.outqueue;

import java.io.Serializable;
import java.util.Date;

public class BQueueIsOutInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private long queueConut = 0;

	private String name;

	private Date date;

	private String url;

	private String message;

	private String note;

	public BQueueIsOutInfo() {
		super();
	}

	public BQueueIsOutInfo(long queueConut, Date date, String message) {
		super();
		this.queueConut = queueConut;
		this.date = date;
		this.message = message;
	}

	public BQueueIsOutInfo(long queueConut, Date date, String url,
			String message) {
		super();
		this.queueConut = queueConut;
		this.date = date;
		this.url = url;
		this.message = message;
	}

	public BQueueIsOutInfo(long queueConut, Date date, String url,
			String message, String note) {
		super();
		this.queueConut = queueConut;
		this.date = date;
		this.url = url;
		this.message = message;
		this.note = note;
	}

	public BQueueIsOutInfo(long queueConut, String name, Date date, String url,
			String message) {
		super();
		this.queueConut = queueConut;
		this.name = name;
		this.date = date;
		this.url = url;
		this.message = message;
	}

	public BQueueIsOutInfo(long queueConut, String name, Date date, String url,
			String message, String note) {
		super();
		this.queueConut = queueConut;
		this.name = name;
		this.date = date;
		this.url = url;
		this.message = message;
		this.note = note;
	}

	public long getQueueConut() {
		return queueConut;
	}

	public void setQueueConut(long queueConut) {
		this.queueConut = queueConut;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
