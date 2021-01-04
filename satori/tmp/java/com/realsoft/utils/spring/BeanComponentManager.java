/*
 * Created on 14.06.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BeanComponentManager.java,v 1.1 2014/07/01 11:58:22 dauren_work Exp $
 */
package com.realsoft.utils.spring;

import java.io.File;
import java.net.URI;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

public class BeanComponentManager {

	private static Logger log = Logger.getLogger(BeanComponentManager.class);

	private Resource resource = null;

	private XmlBeanFactory factory = null;

	private boolean isStarted = false;

	public BeanComponentManager(String beanResourceName) {
		super();
		if (beanResourceName == null)
			throw new IllegalArgumentException(
					"beanResourceName parameter can not be null");
		resource = new FileSystemResource(new File(beanResourceName));
		initialize();
	}

	public BeanComponentManager(URI beanResourceName) {
		super();
		if (beanResourceName == null)
			throw new IllegalArgumentException(
					"beanResourceName parameter can not be null");
		resource = new FileSystemResource(new File(beanResourceName));
		initialize();
	}

	public void initialize() {
		factory = new XmlBeanFactory(resource);
		isStarted = true;
	}

	public Object getBean(String beanName) {
		return factory.getBean(beanName);
	}

	public boolean containsBean(String beanName) {
		return factory.containsBean(beanName);
	}

	public void dispose() {
		isStarted = false;
	}

	public void destroy() {
		log.debug("Starting destroy BeanFactory ...");
		for (String beanName : factory.getBeanDefinitionNames()) {
			log.debug("Destroying bean " + beanName);
			try{
				factory.destroyScopedBean(beanName);
				log.debug("Destroying bean " + beanName+" done");
			}catch (Exception e){
				log.error("Could not destroy bean, with name \""+beanName+"\"", e);
			}
		}
		try{
			log.debug("Destroying singeltons...");
			factory.destroySingletons();
			log.debug("Destroying singeltons done");
		}catch (Exception e){
			log.error("Could not destroySingletons", e);
		}
		dispose();
		log.debug("Destroy BeanFactory DONE");
	}

}