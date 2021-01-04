package com.realsoft.commons.beans.request;

import java.util.Date;

public class BOrderDeviceItem135 {
	private long abonentId;
	private long orderId;
	private String deviceGroupName;
	private long actionId;
	private Date openDate;
	private long status;
	private String error;

	public BOrderDeviceItem135(long abonentId, long orderId,
			String deviceGroupName, long actionId, Date openDate, long status,
			String error) {
		super();
		this.abonentId = abonentId;
		this.orderId = orderId;
		this.deviceGroupName = deviceGroupName;
		this.actionId = actionId;
		this.openDate = openDate;
		this.status = status;
		this.error = error;
	}

	public long getAbonentId() {
		return abonentId;
	}

	public void setAbonentId(long abonentId) {
		this.abonentId = abonentId;
	}

	public long getActionId() {
		return actionId;
	}

	public void setActionId(long actionId) {
		this.actionId = actionId;
	}

	public String getDeviceGroupName() {
		return deviceGroupName;
	}

	public void setDeviceGroupName(String deviceGroupName) {
		this.deviceGroupName = deviceGroupName;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Date getOpenDate() {
		return openDate;
	}

	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public long getStatus() {
		return status;
	}

	public void setStatus(long status) {
		this.status = status;
	}

}