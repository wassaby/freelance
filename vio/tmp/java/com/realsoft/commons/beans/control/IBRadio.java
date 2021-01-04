/*
 * Created on 27.06.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: IBRadio.java,v 1.2 2016/04/15 10:37:07 dauren_home Exp $
 */
package com.realsoft.commons.beans.control;

import java.util.List;

public interface IBRadio extends IBControl {

	List<BRadioItem> getRadioItems() throws CommonsControlException;

	void setRadioItems(List<BRadioItem> radioItems)
			throws CommonsControlException;

	void addRadioItem(BRadioItem item) throws CommonsControlException;
}
