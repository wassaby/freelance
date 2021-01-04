/*
 * Created on 27.06.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BRadioImpl.java,v 1.2 2016/04/15 10:37:08 dauren_home Exp $
 */
package com.realsoft.commons.beans.control;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

public class BRadioImpl extends AbstractControl implements IBRadio,
		IBListControl {

	private static Logger log = Logger.getLogger(BRadioImpl.class);

	private static final long serialVersionUID = 1L;

	private IBItem value = null;

	private List<BRadioItem> itemList = null;

	public BRadioImpl(String name) {
		super(name);
	}

	public BRadioImpl(String name, IBRequest request) {
		super(name, request);
	}

	public BRadioImpl(String name, IBRequest request, List<IBControl> dependOn) {
		super(name, request, dependOn);
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = (IBItem) value;
	}

	public void setItemList(List itemList) {
		this.itemList = itemList;
	}

	public List getItemList() {
		return itemList;
	}

	public void setCurrentValue() throws CommonsControlException {
		for (BRadioItem radioItem : itemList) {
			if (radioItem.equals(value))
				return;
		}
		Iterator<BRadioItem> iterator = itemList.iterator();
		if (iterator.hasNext())
			value = iterator.next();
		else
			value = null;
	}

	public void addRadioItem(BRadioItem item) throws CommonsControlException {
		itemList.add(item);
	}

	public List<BRadioItem> getRadioItems() throws CommonsControlException {
		return itemList;
	}

	public void setRadioItems(List<BRadioItem> radioItems)
			throws CommonsControlException {
		this.itemList = radioItems;
	}

	public void setModel(Object model) throws CommonsControlException {
		if (model instanceof BRadioImpl) {
			copyFrom((BRadioImpl) model);
		}
	}

	public Object getModel() throws CommonsControlException {
		return this;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("\nRADIO[").append(name).append(",").append(value)
				.append("]\n\t").append("{");
		for (BRadioItem item : itemList) {
			buffer.append(item.toString()).append("\n");
		}
		buffer.append("}");
		return buffer.toString();
	}

	public void copyFrom(BRadioImpl impl) throws CommonsControlException {
		super.copyFrom(impl);
		setItemList(impl.getItemList());
		setRadioItems(impl.getRadioItems());
	}

}
