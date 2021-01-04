/*
 * Created on 15.04.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BAbonentInfo135.java,v 1.2 2016/04/15 10:37:36 dauren_home Exp $
 */
package com.realsoft.commons.beans.abonent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Содержит информацию об абоненте или об операторе в системе bittl135.
 * 
 * @author temirbulatov
 * 
 */
public class BAbonentInfo135 implements Serializable {

	private static final long serialVersionUID = 1L;

	private long abonentId;

	private Long alienAbonentId;

	private String abonentName;

	private long townId;

	private String townName;

	private long abonentTypeId;

	private String abonentTypeName;

	private boolean isBlocked;

	private List<BAbonentDeviceItem> deviceList = new ArrayList<BAbonentDeviceItem>();

	public BAbonentInfo135() {
		super();
	}

	/**
	 * 
	 * @param abonentId
	 *            лицевой счет абонента
	 * @param abonentName
	 *            имя абонента
	 * @param townId
	 *            идентификатор города
	 * @param townName
	 *            название города
	 */
	public BAbonentInfo135(long abonentId, String abonentName, long townId,
			String townName, Long alienAbonentId) {
		super();
		this.abonentId = abonentId;
		this.abonentName = abonentName;
		this.townId = townId;
		this.townName = townName;
		this.alienAbonentId = alienAbonentId;
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

	public long getTownId() {
		return townId;
	}

	public void setTownId(long townId) {
		this.townId = townId;
	}

	public String getTownName() {
		return townName;
	}

	public void setTownName(String townName) {
		this.townName = townName;
	}

	public Long getAlienAbonentId() {
		return alienAbonentId;
	}

	public void setAlienAbonentId(Long alienAbonentId) {
		this.alienAbonentId = alienAbonentId;
	}

	public List<BAbonentDeviceItem> getDeviceList() {
		return deviceList;
	}

	public void setDeviceList(List<BAbonentDeviceItem> deviceList) {
		this.deviceList = deviceList;
	}

	public long getAbonentTypeId() {
		return abonentTypeId;
	}

	public void setAbonentTypeId(long abonentTypeId) {
		this.abonentTypeId = abonentTypeId;
	}

	public String getAbonentTypeName() {
		return abonentTypeName;
	}

	public void setAbonentTypeName(String abonentTypeName) {
		this.abonentTypeName = abonentTypeName;
	}

	public boolean isBlocked() {
		return isBlocked;
	}

	public void setBlocked(boolean isBlocked) {
		this.isBlocked = isBlocked;
	}

}
