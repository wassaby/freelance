/*
 * Created on 14.02.2005
 *
 * Realsoft Ltd.
 * Dmitry Dudorga.
 * $Id: BundleManager.java,v 1.1 2014/07/01 11:58:27 dauren_work Exp $
 */
package com.realsoft.utils.resources;

import java.io.File;
import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

/**
 * @author dimad
 */
public class BundleManager {

	private static Logger log = Logger.getLogger(BundleManager.class);

	private static final String MESSAGE_FILE = "messages";

	private Locale locale;

	private ResourceBundle rb;

	public BundleManager(String messageFile, String locale) {
		this(messageFile, new Locale(locale));
	}

	public BundleManager(String messageFile, Locale locale) {
		this.locale = locale;
		try {
			rb = ResourceBundle.getBundle(messageFile, locale);
			showAllMessages();
		} catch (Exception e) {
			log.error("Could not build resouse bundle", e);
		}
	}

	public BundleManager(String resourcePath, String messageFile, String locale) {
		this(resourcePath, messageFile, new Locale(locale));
	}

	public BundleManager(String resourcePath, String messageFile, Locale locale) {
		this.locale = locale;
		try {
			rb = ResourceBundle.getBundle(resourcePath + File.separator
					+ messageFile, locale);
			showAllMessages();
		} catch (Exception e) {
			log.error("Could not build resouse bundle", e);
		}
	}

	public BundleManager(Locale locale) {
		this.locale = locale;
		try {
			rb = ResourceBundle.getBundle(MESSAGE_FILE, locale);
			showAllMessages();
		} catch (Exception e) {
			log.error("Could not build resouse bundle", e);
		}
	}

	public String getMessage(String key) {
		String value;
		try {
			value = rb.getString(key);
		} catch (Exception e) {
			log.error("Could not get message for key = " + key, e);
			value = key;
		}
		return value;
	}

	public String getMessage(String key, Object[] values) {
		String value;
		try {
			value = rb.getString(key);
			value = MessageFormat.format(value, values);
		} catch (Exception e) {
			log.error("Could not get message for key = " + key, e);
			value = key;
		}
		return value;
	}

	private void showAllMessages() {
		Enumeration<String> keys = rb.getKeys();
		for (; keys.hasMoreElements();) {
			try {
				String key = keys.nextElement();
				log.debug(key + " = " + rb.getString(key));
			} catch (Exception e) {
				log.error("Could not build resouse bundle", e);
			}
		}
	}

}
