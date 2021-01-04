/*
 * Created on 04.05.2005
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: ChangeLanguageForm.java,v 1.1 2014/07/01 11:58:28 dauren_work Exp $
 */
package com.realsoft.struts.helper.language;

import org.apache.struts.action.ActionForm;

/**
 * @author dimad
 */
public class ChangeLanguageForm extends ActionForm {

	private String language;

	private String country;

	private String page;

	public ChangeLanguageForm() {
		super();
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

}