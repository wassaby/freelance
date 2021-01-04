/*
 * Created on 15.03.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: IBComboBox.java,v 1.1 2014/07/01 11:58:19 dauren_work Exp $
 */
package com.realsoft.commons.beans.control;

import java.util.List;

public interface IBComboBox extends IBControl {

	List<BComboBoxItem> getComboboxItems() throws CommonsControlException;

	void setComboboxItems(List<BComboBoxItem> comboboxItems)
			throws CommonsControlException;

	void addComboboxItem(BComboBoxItem item) throws CommonsControlException;

}