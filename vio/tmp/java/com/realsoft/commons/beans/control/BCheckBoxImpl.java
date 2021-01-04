/*
 * Created on 27.06.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BCheckBoxImpl.java,v 1.2 2016/04/15 10:37:06 dauren_home Exp $
 */
package com.realsoft.commons.beans.control;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * Реализация модели данных для графической компоненты группа checkbox.
 * Документацию по использованию графических компонент вы можете найти в
 * {@link AbstractControl}
 * 
 * @author dimad
 */
public class BCheckBoxImpl extends AbstractControl implements IBCheckBox,
		IBListControl {

	/**
	 * Логгер используемый компонентой
	 */
	private static Logger log = Logger.getLogger(BCheckBoxImpl.class);

	private static final long serialVersionUID = 1L;

	/**
	 * Набор выделенных значений в группе checkbox-ов
	 */
	private List<BCheckBoxItem> value = new LinkedList<BCheckBoxItem>();

	/**
	 * Возможные значения, принимаемые компонентой
	 */
	private List<BCheckBoxItem> itemList = null;

	/**
	 * 
	 * @param name
	 */
	public BCheckBoxImpl(String name) {
		super(name);
	}

	/**
	 * 
	 * @param name
	 * @param request
	 */
	public BCheckBoxImpl(String name, IBRequest request) {
		super(name, request);
	}

	/**
	 * 
	 * @param name
	 * @param request
	 * @param dependOn
	 */
	public BCheckBoxImpl(String name, IBRequest request,
			List<IBControl> dependOn) {
		super(name, request, dependOn);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.control.AbstractControl#getValue()
	 */
	public Object getValue() {
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.control.AbstractControl#setValue(java.lang.Object)
	 */
	public void setValue(Object value) {
		this.value.add((BCheckBoxItem) value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.control.IBCheckBox#removeValue(com.realsoft.commons.beans.control.BCheckBoxItem)
	 */
	public void removeValue(BCheckBoxItem item) {
		value.remove(item);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.control.IBCheckBox#isValueExist(com.realsoft.commons.beans.control.BCheckBoxItem)
	 */
	public boolean isValueExist(BCheckBoxItem item) {
		return value.contains(item);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.control.IBListControl#setItemList(java.util.List)
	 */
	public void setItemList(List itemList) {
		this.itemList = itemList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.control.IBListControl#getItemList()
	 */
	public List getItemList() {
		return itemList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.control.IBListControl#setCurrentValue()
	 */
	public void setCurrentValue() throws CommonsControlException {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.control.IBCheckBox#addCheckBoxItem(com.realsoft.commons.beans.control.BCheckBoxItem)
	 */
	public void addCheckBoxItem(BCheckBoxItem item)
			throws CommonsControlException {
		itemList.add(item);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.control.IBCheckBox#getCheckBoxItems()
	 */
	public List<BCheckBoxItem> getCheckBoxItems()
			throws CommonsControlException {
		return itemList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.control.IBCheckBox#setCheckBoxItems(java.util.List)
	 */
	public void setCheckBoxItems(List<BCheckBoxItem> radioItems)
			throws CommonsControlException {
		itemList = radioItems;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("\nCHECK_BOX[").append(name).append(",").append(value)
				.append("]\n\t").append("{");
		for (BCheckBoxItem item : value) {
			buffer.append(item.toString()).append("\n");
		}
		buffer.append("}");
		return buffer.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.control.IBModel#setModel(java.lang.Object)
	 */
	public void setModel(Object model) throws CommonsControlException {
		if (model instanceof BCheckBoxImpl) {
			copyFrom((BCheckBoxImpl) model);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.control.IBModel#getModel()
	 */
	public Object getModel() throws CommonsControlException {
		return this;
	}

	public void copyFrom(BCheckBoxImpl impl) throws CommonsControlException {
		super.copyFrom(impl);
	}

}
