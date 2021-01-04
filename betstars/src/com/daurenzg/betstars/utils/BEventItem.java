package com.daurenzg.betstars.utils;

import java.io.Serializable;

public class BEventItem implements Serializable {

	private static final long serialVersionUID = -3412644310620472470L;
	private long id;
	private String name;
	private int status;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

	public BEventItem(long id, String name, int status) {
		super();
		this.id = id;
		this.name = name;
		this.status = status;
	}

}
