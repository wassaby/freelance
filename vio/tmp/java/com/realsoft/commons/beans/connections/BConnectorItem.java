package com.realsoft.commons.beans.connections;

import java.io.Serializable;

/**
 * Инфомация о соединении
 * 
 * @author temirbulatov
 * 
 */
public class BConnectorItem implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long deviceId = null;

	private String device = null;

	private String name = null;

	private String note = null;

	/**
	 * @param id
	 *            Идентификатор подключения
	 * @param device
	 *            Подключение
	 * @param name
	 *            Тарифный план
	 * @param note
	 *            Примечание
	 */
	public BConnectorItem(Long id, String device, String name, String note) {
		super();
		this.deviceId = id;
		this.device = device;
		this.name = name;
		this.note = note;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long id) {
		this.deviceId = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
