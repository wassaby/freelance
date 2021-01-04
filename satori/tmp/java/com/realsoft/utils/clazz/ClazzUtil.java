/*
 * Created on 07.08.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: ClazzUtil.java,v 1.1 2014/07/01 11:58:26 dauren_work Exp $
 */
package com.realsoft.utils.clazz;

import org.apache.log4j.Logger;

public class ClazzUtil {

	private static Logger log = Logger.getLogger(ClazzUtil.class);

	private ClazzUtil() {
	}

	public static String getClassName(Class cglibClass) {
		return cglibClass.getName().substring(0,
				cglibClass.getName().indexOf("$$"));
	}

	public static Class getClass(Class cglibClass)
			throws ClassNotFoundException {
		if (cglibClass.getName().indexOf("$$") != -1)
			return Class.forName(getClassName(cglibClass));
		return cglibClass;
	}

}
