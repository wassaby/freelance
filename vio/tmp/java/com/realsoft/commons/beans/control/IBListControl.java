/*
 * Created on 28.06.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: IBListControl.java,v 1.2 2016/04/15 10:37:06 dauren_home Exp $
 */
package com.realsoft.commons.beans.control;

import java.util.List;

public interface IBListControl extends IBControl {
	void setItemList(List itemList);

	List getItemList();

	void setCurrentValue() throws CommonsControlException;
}
