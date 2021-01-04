/*
 * Created on 15.03.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: IBTable.java,v 1.1 2014/07/01 11:58:19 dauren_work Exp $
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