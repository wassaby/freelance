package com.daurenzg.betstars.wao;

import java.io.Serializable;

public class AuthItem implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2186412308572179236L;
	private String accessToken;
	private String tokenType;
	private long expiresIn;
	private int responceCode;
	private String responseMessage;
	private ProfileItem profItem;
	
	public ProfileItem getProfItem() {
		return profItem;
	}
	public void setProfItem(ProfileItem profItem) {
		this.profItem = profItem;
	}
	public String getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	public int getResponceCode() {
		return responceCode;
	}
	public void setResponceCode(int responceCode) {
		this.responceCode = responceCode;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getTokenType() {
		return tokenType;
	}
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
	public long getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(long expiresIn) {
		this.expiresIn = expiresIn;
	}
	
}
