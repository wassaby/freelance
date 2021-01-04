/*
 * Created on 27.06.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BLabelImpl.java,v 1.2 2016/04/15 10:37:06 dauren_home Exp $
 */
package com.realsoft.commons.beans.control;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BLabelImpl extends AbstractControl implements IBLabel,
		IBListControl {

	private static final long serialVersionUID = 3755278252660616841L;

	private List<BLabelItem> itemList = new ArrayList<BLabelItem>();

	private Class type = null;

	private String image = null;

	public BLabelImpl(String name, Class type) {
		super(name);
		this.type = type;
	}

	public BLabelImpl(String name, Object value, Class type)
			throws CommonsControlException {
		super(name);
		this.type = type;
		setValue(value);
	}

	public BLabelImpl(String name, IBRequest request, Class type) {
		super(name, request);
		this.type = type;
	}

	public BLabelImpl(String name, IBRequest request, List<IBControl> dependOn,
			Class type) {
		super(name, request, dependOn);
		this.type = type;
	}

	public void setItemList(List itemList) {
		this.itemList = itemList;
	}

	public List getItemList() {
		return itemList;
	}

	public void setCurrentValue() throws CommonsControlException {
		Iterator<BLabelItem> iterator = itemList.iterator();
		if (iterator.hasNext())
			value = iterator.next();
		else
			value = null;
	}

	public void setModel(Object model) throws CommonsControlException {
		if (model instanceof BLabelImpl) {
			copyFrom((BLabelImpl) model);
		}
	}

	public Object getModel() throws CommonsControlException {
		return this;
	}

	public Class getType() {
		return type;
	}

	public void setType(Class type) {
		this.type = type;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("\nLABEL[").append(name).append(",").append(value)
				.append(";").append(image).append("]\n\t").append("{");
		for (BLabelItem item : itemList) {
			buffer.append(item.toString()).append("\n");
		}
		buffer.append("}");
		return buffer.toString();
	}

	public void copyFrom(BLabelImpl labelImpl) throws CommonsControlException {
		super.copyFrom(labelImpl);
		setItemList(labelImpl.getItemList());
		setImage(labelImpl.image);
	}

}
