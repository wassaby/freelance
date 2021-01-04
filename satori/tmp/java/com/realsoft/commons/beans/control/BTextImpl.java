/*
 * Created on 27.06.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BTextImpl.java,v 1.1 2014/07/01 11:58:19 dauren_work Exp $
 */
package com.realsoft.commons.beans.control;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BTextImpl extends AbstractControl implements IBText, IBListControl {

	private static final long serialVersionUID = -3956185371741034056L;

	private List<BTextItem> itemList = new ArrayList<BTextItem>();

	public BTextImpl(String name) {
		super(name);
	}

	public BTextImpl(String name, Object value) {
		super(name);
		this.value = value;
	}

	public BTextImpl(String name, IBRequest request) {
		super(name, request);
	}

	public BTextImpl(String name, IBRequest request, List<IBControl> dependOn) {
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
		if (model instanceof BTextImpl) {
			copyFrom((BTextImpl) model);
		}
	}

	public Object getModel() throws CommonsControlException {
		return this;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("\nTEXT[").append(name).append(",").append(value);
		if (value != null)
			buffer.append(" - ").append(value.getClass());
		buffer.append("]\n\t").append("{");
		for (BTextItem item : itemList) {
			buffer.append(item.toString()).append("\n");
		}
		buffer.append("}");
		return buffer.toString();
	}

	public void copyFrom(BTextImpl impl) throws CommonsControlException {
		super.copyFrom(impl);
		setItemList(impl.getItemList());
	}

}
