/*
 * Created on 06.03.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: ICControlBox.java,v 1.1 2014/07/01 11:58:22 dauren_work Exp $
 */
package com.realsoft.commons.beans.dealer.control;

import java.io.Serializable;
import java.util.List;

public interface ICControlBox extends Serializable {
	List<ICControl> getControlCollection();

	ICControl getControl(String name) throws CControlException;

	void addControl(ICControl control);
}
