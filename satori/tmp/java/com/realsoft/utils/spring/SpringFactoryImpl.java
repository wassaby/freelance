/*
 * Created on 13.04.2005
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: SpringFactoryImpl.java,v 1.1 2014/07/01 11:58:22 dauren_work Exp $
 */
package com.realsoft.utils.spring;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.log4j.Logger;

import com.realsoft.commons.utils14.RealsoftConstants;
import com.realsoft.commons.utils14.RealsoftException;

/**
 * @author dimad
 */
public class SpringFactoryImpl implements ISpringFactory {

	private static Logger log = Logger.getLogger(SpringFactoryImpl.class);

	private BeanComponentManager manager = null;

	private static ISpringFactory instance = null;

	public static ISpringFactory getInstance(String beanResourceName)
			throws RealsoftException, URISyntaxException {
		if (instance == null) {
			instance = new SpringFactoryImpl(beanResourceName);
		}
		log.info("Component manager instance ready");
		return instance;
	}

	public static ISpringFactory getInstance(URI beanResourceName)
			throws RealsoftException {
		if (instance == null) {
			instance = new SpringFactoryImpl(beanResourceName);
		}
		return instance;
	}

	public static ISpringFactory getInstance() throws RealsoftException {
		if (instance == null)
			throw new RealsoftException(RealsoftConstants.ERR_SYSTEM,
					"ApplicationProperties",
					"amdocs-commons.AmdocsFactoryImpl.getInstance.error", null,
					"Instance had not been already initialized");
		return instance;
	}

	public SpringFactoryImpl(String beanResourceName) throws RealsoftException,
			URISyntaxException {
		this(new URI(beanResourceName));
	}

	public SpringFactoryImpl(URI beanResourceName) throws RealsoftException {
		if (instance == null) {
			manager = new BeanComponentManager(beanResourceName);
		} else {
			throw new RealsoftException(RealsoftConstants.ERR_SYSTEM,
					"ApplicationProperties",
					"amdocs-commons.AmdocsFactoryImpl.error", null,
					"Instance has been already initialized");
		}
	}

	public Object getBean(String beanName) {
		return manager.getBean(beanName);
	}

	public boolean containsBean(String beanName) {
		return manager.containsBean(beanName);
	}

	public void initialize() {
		manager.initialize();
	}

	public void dispose() {
		manager.dispose();
	}

	public void destroy() {
		manager.destroy();
	}

}