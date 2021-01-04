/*
 * Created on 15.03.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: ICCombobox.java,v 1.1 2014/07/01 11:58:21 dauren_work Exp $
 */
package com.realsoft.commons.beans.dealer.control;

import java.util.List;

public interface ICCombobox extends ICControl {

	List<CComboboxItem> getComboboxItems();

	void setComboboxItems(List<CComboboxItem> comboboxItems);

	void addComboboxItem(Object id, String name);

}