/*
 * Created on 06.03.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BListBoxImpl.java,v 1.1 2014/07/01 11:58:19 dauren_work Exp $
 */
package com.realsoft.commons.beans.control;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BListBoxImpl extends AbstractControl implements IBListBox,
		IBListControl {

	private static final long serialVersionUID = 4200381523783728123L;

	private IBItem value = null;

	private List<BListBoxItem> listboxItems = new ArrayList<BListBoxItem>();

	public BListBoxImpl(String name) {
		super(name);
	}

	public BListBoxImpl(String name, IBRequest request) {
		super(name, request);
	}

	public BListBoxImpl(String name, IBRequest request, List<IBControl> dependOn) {
		super(name, request, dependOn);
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = (IBItem) value;
	}

	public List<BListBoxItem> getListboxItems() {
		return listboxItems;
	}

	public void setListboxItems(List<BListBoxItem> listboxItems) {
		this.listboxItems = listboxItems;
	}

	public void addListboxItem(BListBoxItem item) {
		listboxItems.add(item);
	}

	public void setItemList(List itemList) {
		this.listboxItems = itemList;
	}

	public List getItemList() {
		return listboxItems;
	}

	public void setCurrentValue() throws CommonsControlException {
		for (BListBoxItem listBoxItem : listboxItems) {
			if (listBoxItem.equals(value))
				return;
		}
		Iterator<BListBoxItem> iterator = listboxItems.iterator();
		if (iterator.hasNext())
			value = iterator.next();
		else
			value = null;
	}

	public List<BListBoxItem> getComboboxItems() throws CommonsControlException {
		return listboxItems;
	}

	public void setModel(Object model) throws CommonsControlException {
		if (model instanceof BListBoxImpl) {
			copyFrom((BListBoxImpl) model);
		}
	}

	public Object getModel() throws CommonsControlException {
		return this;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("\nLIST_BOX[").append(name).append(",").append(value)
				.append("]\n\t").append("{");
		for (BListBoxItem item : listboxItems) {
			buffer.append(item.toString()).append("\n");
		}
		buffer.append("}");
		return buffer.toString();
	}

	public void copyFrom(BListBoxImpl impl) throws CommonsControlException {
		super.copyFrom(impl);
		setItemList(impl.getItemList());
		setListboxItems(impl.getListboxItems());
	}

}
