/*
 * Created on 28.03.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: UserForm.java,v 1.2 2016/04/15 10:37:40 dauren_home Exp $
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
