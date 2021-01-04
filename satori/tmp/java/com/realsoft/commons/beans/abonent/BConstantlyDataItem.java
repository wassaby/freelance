package com.realsoft.commons.beans.abonent;

import java.io.Serializable;

public class BConstantlyDataItem implements Serializable {

	private static final long serialVersionUID = 1L;

	private String abName;// Наименование абонента

	private String cpName;// ФИО

	private String cpPhone;// Телефон

	private String cpEmail;// Email

	private String fax;// Факс

	private long townId;// Для сравнения с -101. Если не равен -101, то берем

	// townName.

	private long streetId;

	private long house;

	private long subHouse;

	private long flat;

	private long subFlat;

	private String townName;

	private String streetName;

	private String houseName;

	private String subHouseName;

	private String flatName;

	private String subFlatName;

	public String getAbName() {
		return abName;
	}

	public void setAbName(String abName) {
		this.abName = abName;
	}

	public String getCpEmail() {
		return cpEmail;
	}

	public void setCpEmail(String cpEmail) {
		this.cpEmail = cpEmail;
	}

	public String getCpName() {
		return cpName;
	}

	public void setCpName(String cpName) {
		this.cpName = cpName;
	}

	public String getCpPhone() {
		return cpPhone;
	}

	public void setCpPhone(String cpPhone) {
		this.cpPhone = cpPhone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * 
	 * @param abName
	 *            Наименование абонента
	 * @param cpName
	 *            ФИО
	 * @param cpPhone
	 *            Телефон
	 * @param cpEmail
	 *            Email
	 * @param fax
	 *            Факс
	 * @param townId
	 *            Идентификатор города. Для сравнения с -101. Если не равен
	 *            -101, то берем townName.
	 * @param streetId
	 *            Идентификатор улицы. Для сравнения с -101. Если не равен -101,
	 *            то берем streetName.
	 * @param house
	 *            Идентификатор номера дома. Для сравнения с -101. Если не равен
	 *            -101, то берем houseName.
	 * @param subHouse
	 *            Идентификатор дробной части номера дома. Для сравнения с -101.
	 *            Если не равен -101, то берем subHouseName.
	 * @param flat
	 *            Идентификатор номера квартиры. Для сравнения с -101. Если не
	 *            равен -101, то берем flatName.
	 * @param subFlat
	 *            Идентификатор дробной части номера квартиры. Для сравнения с
	 *            -101. Если не равен -101, то берем subFlatName.
	 * @param townName
	 *            Название города
	 * @param streetName
	 *            Название улицы
	 * @param houseName
	 *            Номер дома
	 * @param subHouseName
	 *            Дробная часть номера дома
	 * @param flatName
	 *            Номер квартиры
	 * @param subFlatName
	 *            Дробная часть номера квартиры
	 */
	public BConstantlyDataItem(String abName, String cpName, String cpPhone,
			String cpEmail, String fax, long townId, long streetId, long house,
			long subHouse, long flat, long subFlat, String townName,
			String streetName, String houseName, String subHouseName,
			String flatName, String subFlatName) {
		super();
		this.abName = abName;
		this.cpName = cpName;
		this.cpPhone = cpPhone;
		this.cpEmail = cpEmail;
		this.fax = fax;

		this.townId = townId;
		this.streetId = streetId;
		this.house = house;
		this.subHouse = subHouse;
		this.flat = flat;
		this.subFlat = subFlat;

		this.townName = townName;
		this.streetName = streetName;
		this.houseName = houseName;
		this.subHouseName = subHouseName;
		this.flatName = flatName;
		this.subFlatName = subFlatName;
	}

	public long getFlat() {
		return flat;
	}

	public void setFlat(long flat) {
		this.flat = flat;
	}

	public String getFlatName() {
		return flatName;
	}

	public void setFlatName(String flatName) {
		this.flatName = flatName;
	}

	public long getHouse() {
		return house;
	}

	public void setHouse(long house) {
		this.house = house;
	}

	public String getHouseName() {
		return houseName;
	}

	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}

	public long getStreetId() {
		return streetId;
	}

	public void setStreetId(long streetId) {
		this.streetId = streetId;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public long getSubFlat() {
		return subFlat;
	}

	public void setSubFlat(long subFlat) {
		this.subFlat = subFlat;
	}

	public String getSubFlatName() {
		return subFlatName;
	}

	public void setSubFlatName(String subFlatName) {
		this.subFlatName = subFlatName;
	}

	public long getSubHouse() {
		return subHouse;
	}

	public void setSubHouse(long subHouse) {
		this.subHouse = subHouse;
	}

	public String getSubHouseName() {
		return subHouseName;
	}

	public void setSubHouseName(String subHouseName) {
		this.subHouseName = subHouseName;
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

}
