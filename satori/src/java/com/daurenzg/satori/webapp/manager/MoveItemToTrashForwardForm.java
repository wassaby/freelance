package com.daurenzg.satori.webapp.manager;

import org.apache.struts.action.ActionForm;

/**
 * @author Dauren
 *
 */
public class MoveItemToTrashForwardForm extends ActionForm{
	
	private long itemId;
	private long langId;
	
	public long getItemId() {
		return itemId;
	}
	public void setItemId(long itemId) {
		this.itemId = itemId;
	}
	public long getLangId() {
		return langId;
	}
	public void setLangId(long langId) {
		this.langId = langId;
	}

}