/*
 * Created on 27.06.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BCheckImpl.java,v 1.1 2014/07/01 11:58:19 dauren_work Exp $
 */
package com.realsoft.commons.beans.control;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

public class BCheckImpl extends AbstractControl implements IBCheck,
		IBListControl {

	private static Logger log = Logger.getLogger(BCheckImpl.class);

	private static final long serialVersionUID = 1L;

	private IBItem value = null;

	private List<BCheckItem> itemList = null;

	public BCheckImpl(String name) {
		super(name);
	}

	public BCheckImpl(String name, IBRequest request) {
		super(name, request);
	}

	public BCheckImpl(String name, IBRequest request, List<IBControl> dependOn) {
		super(name, request, dependOn);
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = (IBItem) value;
	}

	public void setSelected(boolean isSelected) throws CommonsControlException {
		int i = 0;
		if (itemList == null) {
			setValue(new BCheckItem(isSelected));
		} else {
			Iterator<BCheckItem> iterator = itemList.iterator();
			if (!isSelected) {
				setValue(iterator.next());
			} else {
				iterator.next();
				setValue(iterator.next());
			}
		}
	}

	public boolean isSelected() throws CommonsControlException {
		int i = 0;
		if (itemList != null) {
			for (IBItem item : itemList) {
				if (item.equals(getValue())) {
					boolean result = true;
					if (i == 0)
						result = false;
					else if (i == 1)
						result = true;
					return result;
				}
				i++;
			}
		}
		return false;
		// throw new SpringBeansException(SpringBeansConstants.ERR_SYSTEM,
		// "spring-beans.check-box.is-selected.no-such-value.error",
		// "No such value error " + value);
	}

	public void setItemList(List itemList) {
		this.itemList = itemList;
	}

	public List getItemList() {
		return itemList;
	}

	public void setCurrentValue() throws CommonsControlException {
		for (BCheckItem checkBoxItem : itemList) {
			if (checkBoxItem.equals(value))
				return;
		}
		Iterator<BCheckItem> iterator = itemList.iterator();
		if (iterator.hasNext())
			value = iterator.next();
		else
			value = null;
	}

	public String toString() {
		return "\nCHECK[" + name + ";" + value + "]";
	}

	public void setModel(Object model) throws CommonsControlException {
		if (model instanceof BCheckImpl) {
			copyFrom((BCheckImpl) model);
		}
	}

	public Object getModel() throws CommonsControlException {
		return this;
	}

	public void copyFrom(BCheckImpl impl) throws CommonsControlException {
		super.copyFrom(impl);
		setItemList(impl.getItemList());
	}

}
