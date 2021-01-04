package com.realsoft.commons.beans.statistics;

import java.io.Serializable;
import java.util.Date;

/**
 * Информация об оказанной услуге
 * 
 * @author temirbulatov
 * 
 */
public class BStatisticItem implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date serviceDate;

	private long serviceId;

	private String serviceName;

	private double serviceCount;

	private String unitName;

	private double amount;

	private String connectName = null;

	private String connectionType = null;

	private String deviceGroup = null;

	private String address = null;

	/**
	 * 
	 * @param serviceDate
	 *            дата оказания услуги
	 * @param serviceId
	 *            идентификатор сервиса
	 * @param serviceName
	 *            наименование сервиса
	 * @param serviceCount
	 *            объем в единицах тарплана
	 * @param unitName
	 *            наименование модуля
	 * @param amount
	 *            начислено
	 */
	public BStatisticItem(Date serviceDate, long serviceId, String serviceName,
			double serviceCount, String unitName, double amount) {
		super();
		this.serviceDate = serviceDate;
		this.serviceId = serviceId;
		this.serviceName = serviceName;
		this.serviceCount = serviceCount;
		this.unitName = unitName;
		this.amount = amount;
	}

	public BStatisticItem(String connectName, String connectionType,
			String deviceGroup, String address) {
		super();
		this.connectName = connectName;
		this.connectionType = connectionType;
		this.deviceGroup = deviceGroup;
		this.address = address;
	}

	public double getServiceCount() {
		return serviceCount;
	}

	public void setServiceCount(double serviceCount) {
		this.serviceCount = serviceCount;
	}

	public Date getServiceDate() {
		return serviceDate;
	}

	public void setServiceDate(Date serviceDate) {
		this.serviceDate = serviceDate;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getConnectionType() {
		return connectionType;
	}

	public void setConnectionType(String connectionType) {
		this.connectionType = connectionType;
	}

	public String getConnectName() {
		return connectName;
	}

	public void setConnectName(String connectName) {
		this.connectName = connectName;
	}

	public String getDeviceGroup() {
		return deviceGroup;
	}

	public void setDeviceGroup(String deviceGroup) {
		this.deviceGroup = deviceGroup;
	}

	public long getServiceId() {
		return serviceId;
	}

	public void setServiceId(long serviceId) {
		this.serviceId = serviceId;
	}

}
