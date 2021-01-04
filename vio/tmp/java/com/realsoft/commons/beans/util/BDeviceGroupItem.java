/*
 * Created on 27.02.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BDeviceGroupItem.java,v 1.2 2016/04/15 10:37:26 dauren_home Exp $
 */
package com.realsoft.commons.beans.util;

import java.io.Serializable;

public class BDeviceGroupItem implements Serializable {

	private static final long serialVersionUID = 1L;

	private long deviceGroupId;

	private String deviceGroupName;

	private long provideId;

	public BDeviceGroupItem() {
		super();
	}

	/**
	 * 
	 * @param deviceGroupId
	 *            Идентификатор группы устройств
	 * @param deviceGroupName
	 *            Название группы устройств
	 * @param provideId
	 *            Идентификатор провайдера
	 */
	public BDeviceGroupItem(long deviceGroupId, String deviceGroupName,
			long provideId) {
		super();
		this.deviceGroupId = deviceGroupId;
		this.deviceGroupName = deviceGroupName;
		this.provideId = provideId;
	}

	public long getDeviceGroupId() {
		return deviceGroupId;
	}

	public void setDeviceGroupId(long deviceGroupId) {
		this.deviceGroupId = deviceGroupId;
	}

	public String getDeviceGroupName() {
		return deviceGroupName;
	}

	public void setDeviceGroupName(String deviceGroupName) {
		this.deviceGroupName = deviceGroupName;
	}

	public long getProvideId() {
		return provideId;
	}

	public void setProvideId(long provideId) {
		this.provideId = provideId;
	}

}
