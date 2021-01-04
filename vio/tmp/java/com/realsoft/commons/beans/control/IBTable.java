/*
 * Created on 15.03.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: IBTable.java,v 1.2 2016/04/15 10:37:06 dauren_home Exp $
 */
package com.realsoft.commons.beans.control;

import java.util.List;

public interface IBTable extends IBControl {

	List<BRowItem> getRowItems() throws CommonsControlException;

	void setRowItems(List<BRowItem> rowItems) throws CommonsControlException;

	void addRowItem(BRowItem rowItem) throws CommonsControlException;

	void setRowItem(BRowItem rowItem) throws CommonsControlException;

	void setColumnNames(List<String> columnNames)
			throws CommonsControlException;

	List<String> getColumnNames() throws CommonsControlException;

	Class getClazz() throws CommonsControlException;

	void setClazz(Class clazz) throws CommonsControlException;

}