/*
 * Created on 06.03.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: ICControl.java,v 1.1 2014/07/01 11:58:21 dauren_work Exp $
 */
package com.realsoft.commons.beans.dealer.control;

import java.io.Serializable;

public interface ICControl extends Serializable {
	String getName();

	void setName(String name);
}
