/*
 * Created on 10.10.2006
 *
 * Realsoft Ltd.
 * Roman Rychkov.
 * $Id: BAbonentDeviceItem.java,v 1.2 2016/04/15 10:37:36 dauren_home Exp $
 */
package com.realsoft.commons.beans.abonent;

import java.io.Serializable;
import java.util.Date;

public class BAbonentDeviceItem implements Serializable {

	public BAbonentDeviceItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	private static final long serialVersionUID = 1L;

	private long id;

	private String device;

	private long deviceGroupId;

	private String deviceGroupName;

	private long connectTypeId;

	private String connectTypeName;

	private boolean disconnected;

	private String damageTypeName;

	private Date damageRegistrationDate;

	private long townId;

	private long linkTypeId;

	private long addressId;

	/**
	 * 
	 * @param id
	 *            Идентификатор устройства
	 * @param device
	 *            Наименование устройства
	 * @param deviceGroupId
	 *            Идентификатор группы устройств, к которой относится данное
	 *            устройство
	 * @param connectTypeId
	 *            Идентификатор типа подключения, к которому относится данное
	 *            устройство
	 * @param townId
	 *            Идентификатор города, к которому относится данное устройство
	 * @param linkTypeId
	 *            Идентификатор адреса устройства
	 * @param addressId
	 */

	public BAbonentDeviceItem(long id, String device, long deviceGroupId,
			long connectTypeId, long townId, long linkTypeId, long addressId) {
		super();
		this.id = id;
		this.device = device;
		this.deviceGroupId = deviceGroupId;
		this.connectTypeId = connectTypeId;
		this.townId = townId;
		this.linkTypeId = linkTypeId;
		this.addressId = addressId;
	}

	public long getConnectTypeId() {
		return connectTypeId;
	}

	public void setConnectTypeId(long connectTypeId) {
		this.connectTypeId = connectTypeId;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
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

	public long getLinkTypeId() {
		return linkTypeId;
	}

	public void setLinkTypeId(long linkTypeId) {
		this.linkTypeId = linkTypeId;
	}

	public long getTownId() {
		return townId;
	}

	public void setTownId(long townId) {
		this.townId = townId;
	}

	public long getAddressId() {
		return addressId;
	}

	public void setAddressId(long addressId) {
		this.addressId = addressId;
	}

	public String getConnectTypeName() {
		return connectTypeName;
	}

	public void setConnectTypeName(String connectTypeName) {
		this.connectTypeName = connectTypeName;
	}

	public Date getDamageRegistrationDate() {
		return damageRegistrationDate;
	}

	public void setDamageRegistrationDate(Date damageRegistrationDate) {
		this.damageRegistrationDate = damageRegistrationDate;
	}

	public String getDamageTypeName() {
		return damageTypeName;
	}

	public void setDamageTypeName(String damageTypeName) {
		this.damageTypeName = damageTypeName;
	}

	public boolean isDisconnected() {
		return disconnected;
	}

	public void setDisconnected(boolean disconnected) {
		this.disconnected = disconnected;
	}

	public String getDeviceGroupName() {
		return deviceGroupName;
	}

	public void setDeviceGroupName(String deviceGroupName) {
		this.deviceGroupName = deviceGroupName;
	}

	public String toString() {
		StringBuffer result = new StringBuffer().append("id = ").append(id)
				.append("; device = ").append(device).append(
						"; deviceGroupId = ").append(deviceGroupId).append(
						"; deviceGroupName = ").append(deviceGroupName).append(
						"; connectTypeId = ").append(connectTypeId).append(
						"; connectTypeName = ").append(connectTypeName).append(
						"; disconnected = ").append(disconnected).append(
						"; damageTypeName = ").append(damageTypeName).append(
						"; damageRegistrationDate = ").append(
						damageRegistrationDate).append("; townId = ").append(
						townId).append("; linkTypeId = ").append(linkTypeId)
				.append("; addressId = ").append(addressId);
		return result.toString();
	}

}
