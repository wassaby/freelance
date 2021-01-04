/*
 * Created on 14.04.2005
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: ISpringFactory.java,v 1.2 2016/04/15 10:37:33 dauren_home Exp $
 */
package com.realsoft.utils.spring;

/**
 * @author dimad
 */
public interface ISpringFactory {

	String SERVICE_MODE = "serviceMode";

	String BILLING_MODE = "billing";

	String ROUTE_MODE = "router";

	String UNKNOW_MODE = "";

	void initialize();

	Object getBean(String beanName);

	boolean containsBean(String beanName);

	void dispose();

	void destroy();

}