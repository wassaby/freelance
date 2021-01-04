package com.realsoft.commons.beans.account;

public class BServiceInfo {

	private String serviceCode;
	private double volume;

	public BServiceInfo() {
	}

	public BServiceInfo(String serviceCode, double volume) {
		super();
		this.serviceCode = serviceCode;
		this.volume = volume;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

}
