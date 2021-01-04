package com.daurenzg.satori.webapp.manager;

import org.apache.struts.action.ActionForm;

/**
 * @author Dauren
 *
 */
public class AddItemNewLanguageForm extends ActionForm{
	
	private long itemId;
	private long langId;
	private long parentId;
	private long statusId;
	private String itemName;
	private long typeId;
	
	public long getTypeId() {
		return typeId;
	}
	public void setTypeId(long typeId) {
		this.typeId = typeId;
	}
	public long getStatusId() {
		return statusId;
	}
	public void setStatusId(long statusId) {
		this.statusId = statusId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
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
	public long getParentId() {
		return parentId;
	}
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
	
}