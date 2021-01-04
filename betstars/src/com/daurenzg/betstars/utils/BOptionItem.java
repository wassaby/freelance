package com.daurenzg.betstars.utils;

import java.io.Serializable;

public class BOptionItem implements Serializable{

	private static final long serialVersionUID = -2450199615674972823L;
	private String key = null;
	private String title = null;
	private double rate;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public BOptionItem(String key, String title, double rate) {
		super();
		this.key = key;
		this.title = title;
		this.rate = rate;
	}
}
