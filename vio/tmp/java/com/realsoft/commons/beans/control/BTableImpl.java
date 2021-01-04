/*
 * Created on 06.03.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BTableImpl.java,v 1.2 2016/04/15 10:37:06 dauren_home Exp $
 */
package com.realsoft.commons.beans.control;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

public class BTableImpl extends AbstractControl implements IBTable,
		IBListControl {

	private static Logger log = Logger.getLogger(BTableImpl.class);

	private static final long serialVersionUID = -5601395353466903511L;

	private IBItem value = null;

	private List<BRowItem> rowItems = new LinkedList<BRowItem>();

	private List<String> columnNames = null;

	private Class clazz;

	public BTableImpl(String name) {
		super(name);
	}

	public BTableImpl(String name, List<String> columnNames) {
		super(name);
		this.columnNames = columnNames;
	}

	public BTableImpl(String name, List<String> columnNames, IBRequest request) {
		super(name, request);
		this.columnNames = columnNames;
	}

	public BTableImpl(String name, List<String> columnNames, IBRequest request,
			List<IBControl> dependOn) {
		super(name, request, dependOn);
		this.columnNames = columnNames;
	}

	public List<BRowItem> getRowItems() {
		return rowItems;
	}

	public void setRowItems(List<BRowItem> rowItems) {
		this.rowItems = rowItems;
	}

	public void addRowItem(BRowItem rowItem) {
		rowItems.add(rowItem);
	}

	public void setRowItem(BRowItem rowItem) {
		for (BRowItem item : rowItems) {
			if (item.getRowNum().equals(rowItem.getRowNum())) {
				log.debug("row item to replace = " + item);
				item = rowItem;
				return;
			}
		}
		log.debug("row item to add = " + rowItem);
		addRowItem(rowItem);
	}

	public void setColumnNames(List<String> columnNames) {
		this.columnNames = columnNames;
	}

	public List<String> getColumnNames() {
		return columnNames;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = (IBItem) value;
	}

	public void setItemList(List itemList) {
		this.rowItems = itemList;
	}

	public List getItemList() {
		return rowItems;
	}

	public void setCurrentValue() throws CommonsControlException {
		for (BRowItem rowItem : rowItems) {
			if (rowItem.equals(value))
				return;
		}
		Iterator<BRowItem> iterator = rowItems.iterator();
		if (iterator.hasNext())
			value = iterator.next();
		else
			value = null;
	}

	public Class getClazz() {
		return clazz;
	}

	public void setClazz(Class clazz) {
		this.clazz = clazz;
	}

	public void setModel(Object model) throws CommonsControlException {
		if (model instanceof IBTable) {
			IBTable tableModel = (IBTable) model;
			rowItems = new LinkedList<BRowItem>();
			for (BRowItem item : tableModel.getRowItems()) {
				rowItems.add(item);
			}
		}
	}

	public Object getModel() throws CommonsControlException {
		return this;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("\nTABLE[").append(name).append(",").append(value)
				.append(",").append(backgroundImage).append("]\n\t")
				.append("{");
		for (BRowItem item : rowItems) {
			buffer.append(item.toString()).append("\n");
		}
		buffer.append("}");
		return buffer.toString();
	}

	public void copyFrom(BTableImpl impl) throws CommonsControlException {
		super.copyFrom(impl);
		setItemList(impl.getItemList());
	}

}
