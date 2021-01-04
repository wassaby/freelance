package com.daurenzg.satori.webapp.admin;

import org.apache.struts.action.ActionForm;

public class NewLangForm extends ActionForm{
	
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

}
