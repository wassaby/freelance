package com.daurenzg.satori.webapp.manager;

import org.apache.struts.action.ActionForm;

public class GetItemsListForm extends ActionForm{
	private String item;

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}
	
}
