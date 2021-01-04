/*
 * Created on 28.09.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: IBRow.java,v 1.2 2016/04/15 10:37:29 dauren_home Exp $
 */
package com.realsoft.commons.beans.persist;

import java.io.Serializable;

public interface IBRow {
	Serializable getId();

	void setId(Serializable id);

	Object getObject();

	void setObject(Object object);
}
