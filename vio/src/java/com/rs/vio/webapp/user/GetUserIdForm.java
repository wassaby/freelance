package com.rs.vio.webapp.user;


import org.apache.struts.action.ActionForm;

public class GetUserIdForm extends ActionForm{
	
	private static final long serialVersionUID = -2578471504066228212L;
	
	private String ipAddress;

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	
}
