/*
 * Created on 15.03.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: ICTable.java,v 1.1 2014/07/01 11:58:22 dauren_work Exp $
 */
package com.realsoft.commons.beans.dealer.control;

import java.util.List;

public interface ICTable extends ICControl {

	List<CRowItem> getRowItems();

	void setRowItems(List<CRowItem> rowItems);

	void addRowItem(CRowItem rowItem);

}