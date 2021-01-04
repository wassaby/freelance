/*
 * Created on 15.03.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: IBComboBox.java,v 1.2 2016/04/15 10:37:07 dauren_home Exp $
 */
package com.realsoft.commons.beans.control;

import java.util.List;

public interface IBComboBox extends IBControl {

	List<BComboBoxItem> getComboboxItems() throws CommonsControlException;

	void setComboboxItems(List<BComboBoxItem> comboboxItems)
			throws CommonsControlException;

	void addComboboxItem(BComboBoxItem item) throws CommonsControlException;

}