/*
 * Created on 19.04.2005
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: LoginForm.java,v 1.1 2014/07/01 11:58:26 dauren_work Exp $
 */
package com.realsoft.struts.helper.login;

import org.apache.struts.action.ActionForm;

/**
 * @author dimad
 */
public class LoginForm extends ActionForm {

	private String user;

	private String password;

	public LoginForm() {
	}

	/**
	 * @return Returns the password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            The password to set.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return Returns the user.
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @param user
	 *            The user to set.
	 */
	public void setUser(String user) {
		this.user = user;
	}

}
