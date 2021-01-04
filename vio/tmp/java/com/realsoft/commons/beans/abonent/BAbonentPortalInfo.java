/*
 * Created on 11.02.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BAbonentPortalInfo.java,v 1.2 2016/04/15 10:37:36 dauren_home Exp $
 */
package com.realsoft.commons.beans.abonent;

import java.io.Serializable;

/**
 * Содержит онформацию об абоненте в системе bittl13. Класс используется как для
 * хранения данных об абоненте, так и для хранения данных об операторе.
 * 
 * @author temirbulatov
 * 
 */
public class BAbonentPortalInfo implements Serializable {

	// private static final long serialVersionUID = 1L;

	private java.lang.String account;

	private java.lang.String address;

	private java.lang.String branch;

	private java.lang.String category;

	private java.lang.String email;

	private java.lang.String emailType = "text";

	private java.lang.String fullName;

	private java.lang.String guids;

	private java.lang.String identityCardNo;

	private java.lang.String phones;

	private java.lang.String rnn;

	private int mailAccount;

	public BAbonentPortalInfo() {
		super();
	}

	public BAbonentPortalInfo(String guids, String fullName, String account,
			String phones, String category, String identityCardNo,
			String address, String email, String emailType, String branch,
			String rnn) {
		super();
		this.guids = guids;
		this.fullName = fullName;
		this.account = account;
		this.phones = phones;
		this.category = category;
		this.identityCardNo = identityCardNo;
		this.address = address;
		this.email = email;
		this.emailType = emailType;
		this.branch = branch;
		this.rnn = rnn;

	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmailType() {
		return emailType;
	}

	public void setEmailType(String emailType) {
		this.emailType = emailType;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getGuids() {
		return guids;
	}

	public void setGuids(String guids) {
		this.guids = guids;
	}

	public String getIdentityCardNo() {
		return identityCardNo;
	}

	public void setIdentityCardNo(String identityCardNo) {
		this.identityCardNo = identityCardNo;
	}

	public int getMailAccount() {
		return mailAccount;
	}

	public void setMailAccount(int mailAccount) {
		this.mailAccount = mailAccount;
	}

	public String getPhones() {
		return phones;
	}

	public void setPhones(String phones) {
		this.phones = phones;
	}

	public String getRnn() {
		return rnn;
	}

	public void setRnn(String rnn) {
		this.rnn = rnn;
	}
}
