/*
 * Created on 16.08.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: IBAction.java,v 1.1 2014/07/01 11:58:19 dauren_work Exp $
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
