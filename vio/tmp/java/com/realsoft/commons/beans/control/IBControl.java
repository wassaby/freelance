/*
 * Created on 06.03.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: IBControl.java,v 1.2 2016/04/15 10:37:08 dauren_home Exp $
 */
package com.realsoft.commons.beans.control;

import java.util.List;

import com.realsoft.commons.beans.CommonsBeansException;

public interface IBControl extends IBModel {

	String getName();

	void setName(String name);

	List<IBControl> getDependOn() throws CommonsControlException;

	void setDependOn(List<IBControl> dependOn) throws CommonsControlException;

	IBRequest getRequest() throws CommonsControlException;

	void setRequest(IBRequest request) throws CommonsControlException;

	Object getValue() throws CommonsControlException;

	void setValue(Object value) throws CommonsControlException;

	boolean isDurty() throws CommonsControlException;

	void setDurty(boolean isDurty) throws CommonsControlException;

	boolean isEnabled() throws CommonsControlException;

	void setEnabled(boolean isEnabled) throws CommonsControlException;

	void setForeground(Integer color) throws CommonsControlException;

	Integer getForeground() throws CommonsControlException;

	void setBackground(Integer color) throws CommonsControlException;

	Integer getBackground() throws CommonsControlException;

	void setInitialBackground(Integer color) throws CommonsControlException;

	Integer getInitialBackground() throws CommonsControlException;

	void setInitialForeground(Integer color) throws CommonsControlException;

	Integer getInitialForeground() throws CommonsControlException;

	void setBackgroundImage(String image) throws CommonsBeansException;

	String getBackgroundImage() throws CommonsControlException;

	void clean() throws CommonsControlException;

	/**
	 * Копирует содель данных из другого экземпляра модели данных
	 * 
	 * @param control
	 *            экземпляр модели данных
	 * @throws CommonsControlException
	 */
	void copyFrom(IBControl control) throws CommonsControlException;

}
