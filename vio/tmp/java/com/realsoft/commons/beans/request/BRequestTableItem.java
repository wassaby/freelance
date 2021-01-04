/*
 * Created on 19.02.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BRequestTableItem.java,v 1.2 2016/04/15 10:37:26 dauren_home Exp $
 */
package com.realsoft.commons.beans.request;

import java.io.Serializable;
import java.util.Date;

public class BRequestTableItem implements Serializable {

	private static final long serialVersionUID = 1L;

	private long requestId;

	private long abonentId;

	private String abonentName;

	private String townName;

	private long townId;

	private String address;

	private String device;

	private String connectType;

	private Date openDate;

	private Date closeDate;

	private String note;

	private long deviceGroupId;

	public long getDeviceGroupId() {
		return deviceGroupId;
	}

	public void setDeviceGroupId(long deviceGroupId) {
		this.deviceGroupId = deviceGroupId;
	}

	public BRequestTableItem() {
		super();
	}

	/**
	 * 
	 * @param requestId
	 *            Идентификатор наряда на устройство
	 * @param abonentId
	 *            Лицевой счет абонента
	 * @param abonentName
	 *            Имя абонента
	 * @param townName
	 *            Наименование города
	 * @param address
	 *            Адрес абонента
	 * @param device
	 *            Новое устройство
	 * @param connectType
	 *            Наименование типа подключения
	 * @param openDate
	 *            Дата открытия наряда на устройство
	 * @param closeDate
	 *            Дата закрытия наряда на устройство
	 * @param note
	 *            Примечание наряда на устройство
	 */
	public BRequestTableItem(long requestId, long abonentId,
			String abonentName, String townName, String address, String device,
			String connectType, Date openDate, Date closeDate, String note,
			long deviceGroupId, long townId) {
		super();
		this.requestId = requestId;
		this.abonentId = abonentId;
		this.abonentName = abonentName;
		this.townName = townName;
		this.address = address;
		this.device = device;
		this.connectType = connectType;
		this.openDate = openDate;
		this.closeDate = closeDate;
		this.note = note;
		this.deviceGroupId = deviceGroupId;
		this.townId = townId;
	}

	public long getAbonentId() {
		return abonentId;
	}

	public void setAbonentId(long abonentId) {
		this.abonentId = abonentId;
	}

	public String getAbonentName() {
		return abonentName;
	}

	public void setAbonentName(String abonentName) {
		this.abonentName = abonentName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}

	public String getConnectType() {
		return connectType;
	}

	public void setConnectType(String connectType) {
		this.connectType = connectType;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getOpenDate() {
		return openDate;
	}

	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}

	public long getRequestId() {
		return requestId;
	}

	public void setRequestId(long requestId) {
		this.requestId = requestId;
	}

	public String getTownName() {
		return townName;
	}

	public void setTownName(String townName) {
		this.townName = townName;
	}

	public long getTownId() {
		return townId;
	}

	public void setTownId(long townId) {
		this.townId = townId;
	}

}
