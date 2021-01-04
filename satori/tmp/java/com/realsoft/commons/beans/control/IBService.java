/*
 * Created on 03.08.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: IBService.java,v 1.1 2014/07/01 11:58:19 dauren_work Exp $
 */
package com.realsoft.commons.beans.control;

import com.realsoft.commons.beans.CommonsBeansException;

public interface IBService {

	IBModelPanel buildModelPanel(IBModelPanel modelPanel)
			throws CommonsBeansException;

	void delete(Class clazz, Object object) throws CommonsBeansException;

}
