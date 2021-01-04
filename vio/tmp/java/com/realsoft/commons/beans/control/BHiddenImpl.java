/*
 * Created on 27.06.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BHiddenImpl.java,v 1.2 2016/04/15 10:37:07 dauren_home Exp $
 */
package com.realsoft.commons.beans.control;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class BHiddenImpl extends AbstractControl implements IBHidden,
		IBListControl {

	private static final long serialVersionUID = 7177741900690461038L;

	private List<BHiddenItem> itemList = new LinkedList<BHiddenItem>();

	public BHiddenImpl(String name) {
		super(name);
	}

	public BHiddenImpl(String name, Object value)
			throws CommonsControlException {
		super(name);
		setValue(value);
	}

	public BHiddenImpl(String name, IBRequest request) {
		super(name, request);
	}

	public BHiddenImpl(String name, IBRequest request, List<IBControl> dependOn) {
		super(name, request, dependOn);
	}

	public BHiddenImpl(String name, List<IBControl> dependOn, IBRequest request,
			Object value) throws CommonsControlException {
		super(name, request, dependOn);
		setValue(value);
	}

	public void setItemList(List itemList) {
		this.itemList = itemList;
	}

	public List getItemList() {
		return itemList;
	}

	public void setCurrentValue() throws CommonsControlException {
		Iterator<BHiddenItem> iterator = itemList.iterator();
		if (iterator.hasNext())
			value = iterator.next();
		else
			value = null;
	}

	public void setModel(Object model) throws CommonsControlException {
		if (model instanceof BHiddenImpl) {
			copyFrom((BHiddenImpl) model);
		}
	}

	public Object getModel() throws CommonsControlException {
		return this;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("\nHIDDEN[").append(name).append(",").append(value)
				.append("]\n\t").append("{");
		for (BHiddenItem item : itemList) {
			buffer.append(item.toString()).append("\n");
		}
		buffer.append("}");
		return buffer.toString();
	}

	public void copyFrom(BComboBoxImpl impl) throws CommonsControlException {
		super.copyFrom(impl);
		setItemList(impl.getItemList());
	}

}
