/*
 * Created on 06.03.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BComboBoxImpl.java,v 1.1 2014/07/01 11:58:19 dauren_work Exp $
 */
package com.realsoft.commons.beans.control;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BComboBoxImpl extends AbstractControl implements IBComboBox,
		IBListControl {

	private static final long serialVersionUID = 4200381523783728123L;

	protected Object value = null;

	private List<BComboBoxItem> comboboxItems = new ArrayList<BComboBoxItem>();

	public BComboBoxImpl(String name) {
		super(name);
	}

	public BComboBoxImpl(String name, IBRequest request) {
		super(name, request);
	}

	public BComboBoxImpl(String name, IBRequest request, List<IBControl> dependOn) {
		super(name, request, dependOn);
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public List<BComboBoxItem> getComboboxItems() {
		return comboboxItems;
	}

	public void setComboboxItems(List<BComboBoxItem> comboboxItems) {
		this.comboboxItems = comboboxItems;
	}

	public void addComboboxItem(BComboBoxItem item) {
		comboboxItems.add(item);
	}

	public void setItemList(List itemList) {
		this.comboboxItems = itemList;
	}

	public List getItemList() {
		return comboboxItems;
	}

	public void setCurrentValue() throws CommonsControlException {
		for (BComboBoxItem comboBoxItem : comboboxItems) {
			if (comboBoxItem.getId().equals(value))
				return;
		}
		Iterator<BComboBoxItem> iterator = comboboxItems.iterator();
		if (iterator.hasNext())
			value = iterator.next().getId();
		else
			value = null;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("\nCOMBOBOX[").append(name).append(",").append(value)
				.append("]\n\t").append("{");
		int i = 0;
		for (BComboBoxItem item : comboboxItems) {
			buffer.append((i++ > 0) ? "," : "").append(item.toString()).append(
					"\n");
		}
		buffer.append("}");
		return buffer.toString();
	}

	public void setModel(Object model) throws CommonsControlException {
		if (model instanceof BComboBoxImpl) {
			copyFrom((BComboBoxImpl) model);
		}
	}

	public Object getModel() throws CommonsControlException {
		return this;
	}

	public void copyFrom(BComboBoxImpl impl) throws CommonsControlException {
		super.copyFrom(impl);
		setItemList(impl.getItemList());
		setComboboxItems(impl.getComboboxItems());
	}

}
