/*
 * Created on 20.04.2005
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: LoginForm.java,v 1.5 2016/04/15 10:37:41 dauren_home Exp $
 */
package com.rs.vio.webapp.login;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 * @author dimad
 */
public class LoginForm extends ActionForm {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2578471504066228212L;

    private String login;

    private String password;

    private boolean loginByAdmin = false;
    
    public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public boolean isLoginByAdmin() {
		return loginByAdmin;
	}

	public void setLoginByAdmin(boolean loginByAdmin) {
		this.loginByAdmin = loginByAdmin;
	}

	@Override
	public ActionErrors validate(ActionMapping mapping,
            HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();

        if ((login == null || login.length() == 0)||(password == null || password.length() == 0)) {
            errors.add("login.error.empty-fields", new ActionMessage("login.error.empty-fields.message"));
        }

        return errors;
    }

}
