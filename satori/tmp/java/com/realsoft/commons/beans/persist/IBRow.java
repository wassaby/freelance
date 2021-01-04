/*
 * Created on 28.09.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: IBRow.java,v 1.1 2014/07/01 11:58:21 dauren_work Exp $
 */
package com.realsoft.commons.beans.persist;

import java.io.Serializable;

public interface IBRow {
	Serializable getId();

	void setId(Serializable id);

	Object getObject();

	void setObject(Object object);
}
