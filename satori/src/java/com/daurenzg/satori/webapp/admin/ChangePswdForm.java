package com.daurenzg.satori.webapp.admin;

import org.apache.struts.action.ActionForm;

public class ChangePswdForm extends ActionForm{
	private String login;
	private String password;
	private long userId;
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
