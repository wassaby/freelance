/*
 * Created on 11.02.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BAbonentInfo13.java,v 1.2 2016/04/15 10:37:36 dauren_home Exp $
 */
package com.realsoft.commons.beans.abonent;

import java.io.Serializable;
import java.util.List;

import com.realsoft.commons.beans.mail.BMailAddressItem;

/**
 * Содержит онформацию об абоненте в системе bittl13. Класс используется как для
 * хранения данных об абоненте, так и для хранения данных об операторе.
 * 
 * @author temirbulatov
 * 
 */
public class BAbonentInfo13 implements Serializable {

	private static final long serialVersionUID = 1L;

	private long abonentId;

	private String abonentName;

	private String addressName;

	private String abonentTypeName;

	private String rnn;

	private String password;

	private String passport;

	private long abonentTypeId;

	private long addressId;

	private long abonentGroupId;

	private long townId;

	private String townName;

	private List<BAbonentDeviceItem> devices;

	private BMailAddressItem email;

	private List<BMailAddressItem> emailAddresses;

	public BAbonentInfo13() {
		super();
	}

	/**
	 * 
	 * @param abonentId
	 *            лицевой счет абонента
	 * @param abonentName
	 *            имя абонента
	 * @param addressName
	 *            адрес абонента
	 * @param abonentTypeName
	 *            тип абонента
	 * @param rnn
	 *            РНН
	 * @param password
	 *            пароль
	 * @param abonentTypeId
	 *            идентификатор типа абонента
	 * @param addressId
	 *            идентификатор адреса
	 * @param abonentGroupId
	 *            идентификатор группы абонента
	 * @param townId
	 *            идентификатор города абонента. Берется из файла
	 *            code-url-mapping.xml
	 * @param townName
	 *            название города
	 */
	public BAbonentInfo13(long abonentId, String abonentName,
			String addressName, String abonentTypeName, String rnn,
			String password, String passport, long abonentTypeId,
			long addressId, long abonentGroupId, long townId, String townName,
			List<BAbonentDeviceItem> devices, BMailAddressItem email) {
		super();
		this.abonentId = abonentId;
		this.abonentName = abonentName;
		this.addressName = addressName;
		this.abonentTypeName = abonentTypeName;
		this.rnn = rnn;
		this.password = password;
		this.passport = passport;
		this.abonentTypeId = abonentTypeId;
		this.addressId = addressId;
		this.abonentGroupId = abonentGroupId;
		this.townId = townId;
		this.townName = townName;
		this.devices = devices;
		this.email = email;
	}

	public long getAbonentGroupId() {
		return abonentGroupId;
	}

	public void setAbonentGroupId(long abonentGroupId) {
		this.abonentGroupId = abonentGroupId;
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

	public long getAddressId() {
		return addressId;
	}

	public void setAddressId(long addressId) {
		this.addressId = addressId;
	}

	public String getAddressName() {
		return addressName;
	}

	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRnn() {
		return rnn;
	}

	public void setRnn(String rnn) {
		this.rnn = rnn;
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

	public List<BAbonentDeviceItem> getDevices() {
		return devices;
	}

	public void setDevices(List<BAbonentDeviceItem> devices) {
		this.devices = devices;
	}

	public BMailAddressItem getEmail() {
		return email;
	}

	public void setEmail(BMailAddressItem email) {
		this.email = email;
	}

	public String getPassport() {
		return passport;
	}

	public void setPassport(String passport) {
		this.passport = passport;
	}

	public List<BMailAddressItem> getEmailAddresses() {
		return emailAddresses;
	}

	public void setEmailAddresses(List<BMailAddressItem> emailAddresses) {
		this.emailAddresses = emailAddresses;
	}

	public String toString() {
		StringBuffer result = new StringBuffer().append("abonentId = ").append(
				abonentId).append("; abonentName = ").append(abonentName)
				.append("; addressName = ").append(addressName).append(
						"; abonentTypeName = ").append(abonentTypeName).append(
						"; rnn = ").append(rnn).append("; password = ").append(
						password).append("; passport = ").append(passport)
				.append("; abonentTypeId = ").append(abonentTypeId).append(
						"; addressId = ").append(addressId).append(
						"; abonentGroupId = ").append(abonentGroupId).append(
						"; townId = ").append(townId).append("; townName = ")
				.append(townName).append("; devices = \n");
		int cnt = 0;
		if (devices != null)
			for (BAbonentDeviceItem item : devices) {
				result.append(++cnt).append(") ").append(item).append("\n");
			}
		result.append("email = ").append(email);
		result.append("emails = \n");
		cnt = 0;
		if (emailAddresses != null)
			for (BMailAddressItem item : emailAddresses) {
				result.append(++cnt).append(") ").append(item).append("\n");
			}
		return result.toString();
	}

}
