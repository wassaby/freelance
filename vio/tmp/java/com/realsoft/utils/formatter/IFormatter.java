/*
 * Created on 10.04.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: IFormatter.java,v 1.2 2016/04/15 10:37:31 dauren_home Exp $
 */
package com.realsoft.utils.formatter;

import com.realsoft.utils.UtilsException;

public interface IFormatter {

	public void register(Class clazz, IFormatter formatter)
			throws UtilsException;

	public String format(Object object) throws UtilsException;

	public String format(Object object, Object deafultValue)
			throws UtilsException;

	public String format(byte object) throws UtilsException;

	public String format(byte object, byte deafultValue) throws UtilsException;

	public String format(short object) throws UtilsException;

	public String format(short object, short deafultValue)
			throws UtilsException;

	public String format(int object) throws UtilsException;

	public String format(int object, int deafultValue) throws UtilsException;

	public String format(long object) throws UtilsException;

	public String format(long object, long deafultValue) throws UtilsException;

	public String format(double object) throws UtilsException;

	public String format(double object, double deafultValue)
			throws UtilsException;

	public String format(float object) throws UtilsException;

	public String format(float object, float deafultValue)
			throws UtilsException;

	public String format(String pattern, Object object) throws UtilsException;

	public String format(String pattern, Object[] objects)
			throws UtilsException;

	public String format(String pattern, Object object, Object deafultValue)
			throws UtilsException;

	public String format(String pattern, byte object) throws UtilsException;

	public String format(String pattern, byte object, byte deafultValue)
			throws UtilsException;

	public String format(String pattern, short object) throws UtilsException;

	public String format(String pattern, short object, short deafultValue)
			throws UtilsException;

	public String format(String pattern, int object) throws UtilsException;

	public String format(String pattern, int object, int deafultValue)
			throws UtilsException;

	public String format(String pattern, long object) throws UtilsException;

	public String format(String pattern, long object, long deafultValue)
			throws UtilsException;

	public String format(String pattern, double object) throws UtilsException;

	public String format(String pattern, double object, double deafultValue)
			throws UtilsException;

	public String format(String pattern, float object) throws UtilsException;

	public String format(String pattern, float object, float deafultValue)
			throws UtilsException;

}
