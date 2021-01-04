/*
 * Created on 15.03.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: ICTable.java,v 1.2 2016/04/15 10:37:31 dauren_home Exp $
 */
package com.realsoft.commons.beans.dealer.control;

import java.util.List;

public interface ICTable extends ICControl {

	List<CRowItem> getRowItems();

	void setRowItems(List<CRowItem> rowItems);

	void addRowItem(CRowItem rowItem);

}