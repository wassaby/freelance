/**
 * BRegistrationInfo13.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 16, 2004 (12:19:44 EST) WSDL2Java emitter.
 */

package com.realsoft.commons.beans.registration;

import java.util.List;

public class BRegistrationInfo135 implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private List balance;

	private long idWeb;

	private long idRadius;

	private long abonentId;

	private String login = null;

	private long linkType;

	private long providerId;

	private long webInterfaceTypeId;

	private String abonentName;

	private String town;

	private String tariffPlan;

	public BRegistrationInfo135() {
		super();
	}

	/**
	 * @param balance
	 * @param idWeb
	 * @param idRadius
	 * @param abonentId
	 * @param login
	 * @param linkType
	 * @param providerId
	 * @param webInterfaceTypeId
	 * @param abonentName
	 * @param town
	 * @param tariffPlan
	 */
	public BRegistrationInfo135(List balance, long idWeb, long idRadius,
			long abonentId, String login, long linkType, long providerId,
			long webInterfaceTypeId, String abonentName, String town,
			String tariffPlan) {
		super();

		this.balance = balance;
		this.idWeb = idWeb;
		this.idRadius = idRadius;
		this.abonentId = abonentId;
		this.login = login;
		this.linkType = linkType;
		this.providerId = providerId;
		this.webInterfaceTypeId = webInterfaceTypeId;
		this.abonentName = abonentName;
		this.town = town;
		this.tariffPlan = tariffPlan;
	}

	public List getBalance() {
		return balance;
	}

	public void setBalance(List balance) {
		this.balance = balance;
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

	public long getIdRadius() {
		return idRadius;
	}

	public void setIdRadius(long idRadius) {
		this.idRadius = idRadius;
	}

	public long getIdWeb() {
		return idWeb;
	}

	public void setIdWeb(long idWeb) {
		this.idWeb = idWeb;
	}

	public long getLinkType() {
		return linkType;
	}

	public void setLinkType(long linkType) {
		this.linkType = linkType;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public long getProviderId() {
		return providerId;
	}

	public void setProviderId(long providerId) {
		this.providerId = providerId;
	}

	public String getTariffPlan() {
		return tariffPlan;
	}

	public void setTariffPlan(String tariffPlan) {
		this.tariffPlan = tariffPlan;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public long getWebInterfaceTypeId() {
		return webInterfaceTypeId;
	}

	public void setWebInterfaceTypeId(long webInterfaceTypeId) {
		this.webInterfaceTypeId = webInterfaceTypeId;
	}

}
