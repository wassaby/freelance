/*
 * Created on 15.03.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: ICCombobox.java,v 1.2 2016/04/15 10:37:31 dauren_home Exp $
 */
package com.realsoft.commons.beans.dealer.control;

import java.util.List;

public interface ICCombobox extends ICControl {

	List<CComboboxItem> getComboboxItems();

	void setComboboxItems(List<CComboboxItem> comboboxItems);

	void addComboboxItem(Object id, String name);

}