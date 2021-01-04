/*
 * Created on 28.06.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: IBItem.java,v 1.1 2014/07/01 11:58:19 dauren_work Exp $
 */
package com.realsoft.commons.beans.control;

import java.io.Serializable;

public interface IBItem extends Serializable {
	void setId(Object id);

	Object getId();

	Object getObject();

	void setObject(Object object);

}
