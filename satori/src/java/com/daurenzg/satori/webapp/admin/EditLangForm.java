package com.daurenzg.satori.webapp.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

public class EditLangForm extends ActionForm{
	
	private long id;
	private String name;
	private String code;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	@Override
	public ActionErrors validate(ActionMapping mapping,
            HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        HttpSession session = request.getSession(true);
        
        if ((name == null || name.length() == 0) || code == null || code.length() == 0) {
            errors.add("error.empty-fields", new ActionMessage("Fill inputs"));
        }
        
        return errors;
    }
	
}
