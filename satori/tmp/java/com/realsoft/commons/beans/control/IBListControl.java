/*
 * Created on 28.06.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: IBListControl.java,v 1.1 2014/07/01 11:58:19 dauren_work Exp $
 */
package com.realsoft.commons.beans.control;

import java.util.List;

public interface IBListControl extends IBControl {
	void setItemList(List itemList);

	List getItemList();

	void setCurrentValue() throws CommonsControlException;
}
