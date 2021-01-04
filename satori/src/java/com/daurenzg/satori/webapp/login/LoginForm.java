package com.daurenzg.satori.webapp.login;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

public class LoginForm extends ActionForm{
	private String login;
	private String password;
	
	public LoginForm(){}
	
	public void setPassword(String password){
		this.password=password;
	}
	
	public String getPassword(){
		return password;
	}
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@Override
	public ActionErrors validate(ActionMapping mapping,
            HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        
        if ((login == null || login.length() == 0)||(password == null || password.length() == 0)) {
            errors.add("login.error.empty-fields", new ActionMessage("Do not leave empty inputs"));
        }
        
        return errors;
    }

}
