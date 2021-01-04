/*
 * Created on 11.02.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BConnectTypeItem.java,v 1.2 2016/04/15 10:37:26 dauren_home Exp $
 */
package com.realsoft.commons.beans.request;

import java.io.Serializable;

public class BConnectTypeItem implements Serializable {

	private static final long serialVersionUID = 1L;

	private long connectTypeId;

	private String name;

	private long iasConnectType;

	private String isActive;

	private long centerPacketType;

	private long centerConnectType;

	/**
	 * 
	 * @param connectTypeId
	 *            Идентификатор типа подключения
	 * @param name
	 *            Наименование типа подключения
	 * @param iasConnectType
	 *            id типа подключения ИАСППР
	 * @param isActive
	 *            Признак "Действующий/Недействующий"
	 * @param centerPacketType
	 *            ID тарифного плана из центрального биллинга
	 * @param centerConnectType
	 *            ID типа подключения из центрального биллинга
	 */
	public BConnectTypeItem(long connectTypeId, String name,
			long iasConnectType, String isActive, long centerPacketType,
			long centerConnectType) {
		super();
		this.connectTypeId = connectTypeId;
		this.name = name;
		this.iasConnectType = iasConnectType;
		this.isActive = isActive;
		this.centerPacketType = centerPacketType;
		this.centerConnectType = centerConnectType;
	}

	public long getCenterConnectType() {
		return centerConnectType;
	}

	public void setCenterConnectType(long centerConnectType) {
		this.centerConnectType = centerConnectType;
	}

	public long getCenterPacketType() {
		return centerPacketType;
	}

	public void setCenterPacketType(long centerPacketType) {
		this.centerPacketType = centerPacketType;
	}

	public long getConnectTypeId() {
		return connectTypeId;
	}

	public void setConnectTypeId(long connectTypeId) {
		this.connectTypeId = connectTypeId;
	}

	public long getIasConnectType() {
		return iasConnectType;
	}

	public void setIasConnectType(long iasConnectType) {
		this.iasConnectType = iasConnectType;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
