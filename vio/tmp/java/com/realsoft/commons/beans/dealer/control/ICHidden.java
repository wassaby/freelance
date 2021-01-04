/*
 * Created on 15.03.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: ICHidden.java,v 1.2 2016/04/15 10:37:31 dauren_home Exp $
 */
package com.realsoft.commons.beans.dealer.control;

public interface ICHidden extends ICControl {
	void setValue(Object value);

	Object getValue();
}
