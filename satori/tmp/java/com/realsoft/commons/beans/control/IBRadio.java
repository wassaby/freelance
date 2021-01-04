/*
 * Created on 27.06.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: IBRadio.java,v 1.1 2014/07/01 11:58:19 dauren_work Exp $
 */
package com.realsoft.commons.beans.control;

import java.util.List;

public interface IBRadio extends IBControl {

	List<BRadioItem> getRadioItems() throws CommonsControlException;

	void setRadioItems(List<BRadioItem> radioItems)
			throws CommonsControlException;

	void addRadioItem(BRadioItem item) throws CommonsControlException;
}
