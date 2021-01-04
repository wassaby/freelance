/*
 * Created on 06.03.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: ICControlBox.java,v 1.2 2016/04/15 10:37:31 dauren_home Exp $
 */
package com.realsoft.commons.beans.dealer.control;

import java.io.Serializable;
import java.util.List;

public interface ICControlBox extends Serializable {
	List<ICControl> getControlCollection();

	ICControl getControl(String name) throws CControlException;

	void addControl(ICControl control);
}
