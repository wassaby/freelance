/*
 * Created on 16.02.2005
 *
 * Realsoft Ltd.
 * Dmitry Dudorga.
 * $Id: ServletConfigurator.java,v 1.1 2014/07/01 11:58:27 dauren_work Exp $
 */
package com.realsoft.utils.servlet;

import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.ServletConfig;

import org.apache.log4j.Logger;

public class ServletConfigurator {

	private static final Logger log = Logger
			.getLogger(ServletConfigurator.class);

	private Properties conf = new Properties();

	public ServletConfigurator(ServletConfig ctx) {
		super();
		Enumeration names = ctx.getInitParameterNames();
		log.debug("parameters are = ");
		while (names.hasMoreElements()) {
			String name = (String) names.nextElement();
			log.debug("parameter " + name + " = " + ctx.getInitParameter(name));
			conf.put(name, ctx.getInitParameter(name));
		}
	}

	public String getValue(String name) {
		return conf.getProperty(name);
	}

	public String getValue(String name, String defaultValue) {
		return conf.getProperty(name, defaultValue);
	}
}
