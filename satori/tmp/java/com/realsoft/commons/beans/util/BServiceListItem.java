/*
 * Created on 28.04.2005
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BServiceListItem.java,v 1.1 2014/07/01 11:58:19 dauren_work Exp $
 */
package com.realsoft.commons.beans.util;

import java.io.Serializable;

/**
 * @author dimad
 */
public class BServiceListItem implements Serializable {

	private static final long serialVersionUID = 1L;

	private long serviceId;

	private String name;

	public BServiceListItem() {
	}

	/**
	 * @param serviceId
	 *            Идентификатор услуги
	 * @param name
	 *            Наименование услуги
	 */
	public BServiceListItem(long serviceId, String name) {
		super();
		this.serviceId = serviceId;
		this.name = name;
	}

	/**
	 * @return Returns the serviceId.
	 */
	public long getServiceId() {
		return serviceId;
	}

	/**
	 * @param serviceId
	 *            The serviceId to set.
	 */
	public void setServiceId(long id) {
		this.serviceId = id;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
}
