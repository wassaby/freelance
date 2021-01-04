/*
 * Created on 28.03.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: UserForm.java,v 1.1 2014/07/01 11:58:27 dauren_work Exp $
 */
package com.realsoft.struts.helper.manager;

import org.apache.struts.action.ActionForm;

public class UserForm extends ActionForm {

	private String userName;

	public UserForm() {
		super();
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
