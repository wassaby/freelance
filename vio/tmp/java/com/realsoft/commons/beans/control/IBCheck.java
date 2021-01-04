/*
 * Created on 27.06.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: IBCheck.java,v 1.2 2016/04/15 10:37:07 dauren_home Exp $
 */
package com.realsoft.commons.beans.control;

public interface IBCheck extends IBControl {

	void setSelected(boolean isSelected) throws CommonsControlException;

	boolean isSelected() throws CommonsControlException;

}
