/*
 * Created on 27.06.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: IBButton.java,v 1.1 2014/07/01 11:58:19 dauren_work Exp $
 */
package com.realsoft.commons.beans.control;

import com.realsoft.commons.beans.CommonsBeansException;

public interface IBButton extends IBLabel {
	void setImage(String image) throws CommonsBeansException;

	String getImage() throws CommonsControlException;
}
