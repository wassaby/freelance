/*
 * Created on 10.10.2006
 *
 * Realsoft Ltd.
 * Roman Rychkov.
 * $Id: BTariffTypeItem.java,v 1.1 2014/07/01 11:58:21 dauren_work Exp $
 */
package com.realsoft.commons.beans.request;

import java.io.Serializable;

public class BTariffTypeItem implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;

	private String name;

	private long servicePacketTypeId;

	private long servieCountGroupId;

	public long getServieCountGroupId() {
		return servieCountGroupId;
	}

	public void setServieCountGroupId(long servieCountGroupId) {
		this.servieCountGroupId = servieCountGroupId;
	}

	public BTariffTypeItem(long id, String name, long servicePacketTypeId,
			long servieCountGroupId) {
		super();
		this.id = id;
		this.name = name;
		this.servicePacketTypeId = servicePacketTypeId;
		this.servieCountGroupId = servieCountGroupId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getServicePacketTypeId() {
		return servicePacketTypeId;
	}

	public void setServicePacketTypeId(long servicePacketTypeId) {
		this.servicePacketTypeId = servicePacketTypeId;
	}

}
