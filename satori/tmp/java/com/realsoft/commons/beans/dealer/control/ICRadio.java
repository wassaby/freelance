/*
 * Created on 15.03.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: ICRadio.java,v 1.1 2014/07/01 11:58:21 dauren_work Exp $
 */
package com.realsoft.commons.beans.dealer.control;

public interface ICRadio extends ICControl {
	Object getValue();

	void setValue(Object value);
}
