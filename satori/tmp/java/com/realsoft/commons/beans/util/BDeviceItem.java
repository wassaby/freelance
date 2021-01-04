package com.realsoft.commons.beans.util;

import java.io.Serializable;

public class BDeviceItem implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;

	private String name = null;

	private long townId;

	private long deviceGroupId;

	private long connectTypeId;

	public BDeviceItem(long id, String name, long townId, long deviceGroupId,
			long connectTypeId) {
		super();
		this.id = id;
		this.name = name;
		this.townId = townId;
		this.deviceGroupId = deviceGroupId;
		this.connectTypeId = connectTypeId;
	}

	public long getConnectTypeId() {
		return connectTypeId;
	}

	public void setConnectTypeId(long connectTypeId) {
		this.connectTypeId = connectTypeId;
	}

	public long getDeviceGroupId() {
		return deviceGroupId;
	}

	public void setDeviceGroupId(long deviceGroupId) {
		this.deviceGroupId = deviceGroupId;
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

	public long getTownId() {
		return townId;
	}

	public void setTownId(long townId) {
		this.townId = townId;
	}

}
