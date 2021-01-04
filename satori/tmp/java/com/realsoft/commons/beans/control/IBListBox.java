/*
 * Created on 15.03.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: IBListBox.java,v 1.1 2014/07/01 11:58:19 dauren_work Exp $
 */
package com.realsoft.commons.beans.control;

import java.util.List;

public interface IBListBox extends IBControl {

	List<BListBoxItem> getListboxItems() throws CommonsControlException;

	void setListboxItems(List<BListBoxItem> listboxItems)
			throws CommonsControlException;

	void addListboxItem(BListBoxItem item) throws CommonsControlException;

}