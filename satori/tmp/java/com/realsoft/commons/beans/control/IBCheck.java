/*
 * Created on 27.06.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: IBCheck.java,v 1.1 2014/07/01 11:58:19 dauren_work Exp $
 */
package com.realsoft.commons.beans.control;

public interface IBCheck extends IBControl {

	void setSelected(boolean isSelected) throws CommonsControlException;

	boolean isSelected() throws CommonsControlException;

}
