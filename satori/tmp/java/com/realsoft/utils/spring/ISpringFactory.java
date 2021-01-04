/*
 * Created on 14.04.2005
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: ISpringFactory.java,v 1.1 2014/07/01 11:58:22 dauren_work Exp $
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