/*
 * Created on 06.03.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: ICControl.java,v 1.2 2016/04/15 10:37:30 dauren_home Exp $
 */
package com.realsoft.commons.beans.dealer.control;

import java.io.Serializable;

public interface ICControl extends Serializable {
	String getName();

	void setName(String name);
}
