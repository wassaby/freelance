/*
 * Created on 06.03.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: CComboboxImpl.java,v 1.2 2016/04/15 10:37:30 dauren_home Exp $
 */
package com.realsoft.commons.beans.dealer.control;

import java.util.ArrayList;
import java.util.List;

public class CComboboxImpl implements ICCombobox {

	private String name;

	private List<CComboboxItem> comboboxItems = new ArrayList<CComboboxItem>();

	public CComboboxImpl(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String controlName) {
		this.name = controlName;
	}

	public List<CComboboxItem> getComboboxItems() {
		return comboboxItems;
	}

	public void setComboboxItems(List<CComboboxItem> comboboxItems) {
		this.comboboxItems = comboboxItems;
	}

	public void addComboboxItem(Object id, String name) {
		CComboboxItem comboboxItem = new CComboboxItem(id, name);
		comboboxItems.add(comboboxItem);
	}

}
