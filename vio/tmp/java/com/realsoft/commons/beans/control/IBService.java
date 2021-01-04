/*
 * Created on 03.08.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: IBService.java,v 1.2 2016/04/15 10:37:07 dauren_home Exp $
 */
package com.realsoft.commons.beans.control;

import com.realsoft.commons.beans.CommonsBeansException;

public interface IBService {

	IBModelPanel buildModelPanel(IBModelPanel modelPanel)
			throws CommonsBeansException;

	void delete(Class clazz, Object object) throws CommonsBeansException;

}
