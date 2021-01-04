/*
 * Created on 06.03.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: CTable.java,v 1.2 2016/04/15 10:37:30 dauren_home Exp $
 */
package com.realsoft.commons.beans.dealer.control;

import java.util.ArrayList;
import java.util.List;

public class CTable implements ICTable {

	private String name = null;

	private List<CRowItem> rowItems = new ArrayList<CRowItem>();

	public CTable() {
		super();
	}

	public CTable(String name) {
		super();
		this.name = name;
	}

	public CTable(String name, List<CRowItem> rowItems) {
		super();
		this.name = name;
		this.rowItems = rowItems;
	}

	public String getName() {
		return name;
	}

	public void setName(String controlName) {
		this.name = controlName;
	}

	public List<CRowItem> getRowItems() {
		return rowItems;
	}

	public void setRowItems(List<CRowItem> rowItems) {
		this.rowItems = rowItems;
	}

	public void addRowItem(CRowItem rowItem) {
		rowItems.add(rowItem);
	}

}
