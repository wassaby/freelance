/*
 * Created on 16.08.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: IBAction.java,v 1.2 2016/04/15 10:37:06 dauren_home Exp $
 */
package com.realsoft.commons.beans.control;

import java.io.Serializable;

import com.realsoft.commons.beans.CommonsBeansException;

public interface IBAction extends Serializable {

	void setName(String name);

	String getName();

	Serializable run(Serializable parameter) throws CommonsBeansException;

	String getType();

	void setType(String type);

	void initialize();

	void destroy();

}
