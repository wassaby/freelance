/*
 * Created on 27.06.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BTextBoxImpl.java,v 1.1 2014/07/01 11:58:19 dauren_work Exp $
 */
package com.realsoft.commons.beans.control;

import java.util.Iterator;
import java.util.List;

public class BTextBoxImpl extends AbstractControl implements IBTextBox,
		IBListControl {

	private static final long serialVersionUID = 1053175433166068827L;

	private List<BTextItem> itemList = null;

	public BTextBoxImpl(String name) {
		super(name);
	}

	public BTextBoxImpl(String name, Object value) {
		super(name);
		this.value = value;
	}

	public BTextBoxImpl(String name, IBRequest request) {
		super(name, request);
	}

	public BTextBoxImpl(String name, IBRequest request, List<IBControl> dependOn) {
		super(name, request, dependOn);
	}

	public void setItemList(List itemList) {
		this.itemList = itemList;
	}

	public List getItemList() {
		return itemList;
	}

	public void setCurrentValue() throws CommonsControlException {
		Iterator<BTextItem> iterator = itemList.iterator();
		if (iterator.hasNext())
			value = iterator.next();
		else
			value = null;
	}

	public void setModel(Object model) throws CommonsControlException {
		if (model instanceof BTextBoxImpl) {
			copyFrom((BTextBoxImpl) model);
		}
	}

	public Object getModel() throws CommonsControlException {
		return this;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("\nTEXT_BOX[").append(name).append(",").append(value)
				.append("]\n\t").append("{");
		for (BTextItem item : itemList) {
			buffer.append(item.toString()).append("\n");
		}
		buffer.append("}");
		return buffer.toString();
	}

	public void copyFrom(BTextBoxImpl impl) throws CommonsControlException {
		super.copyFrom(impl);
		setItemList(impl.getItemList());
	}

}
