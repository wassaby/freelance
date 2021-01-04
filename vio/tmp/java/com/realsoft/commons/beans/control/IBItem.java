/*
 * Created on 28.06.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: IBItem.java,v 1.2 2016/04/15 10:37:08 dauren_home Exp $
 */
package com.realsoft.commons.beans.control;

import java.io.Serializable;

public interface IBItem extends Serializable {
	void setId(Object id);

	Object getId();

	Object getObject();

	void setObject(Object object);

}
