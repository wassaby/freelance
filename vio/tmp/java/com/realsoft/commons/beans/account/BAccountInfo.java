package com.realsoft.commons.beans.account;

import java.io.Serializable;
import java.util.Date;

public class BAccountInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;

	private long radiusId = -1;

	private String radiusLogin;

	private String login;

	private String accountType;

	private long accountTypeId;

	private Date registrationDate;

	private Date fromDate;

	private String password;

	private String repeatePassword;

	private String question;

	private String answer;

	private long webInterfaceTypeId;

	private long providerId;

	private String tarifPlan;

	private boolean macAddressUndefined = true;

	private String codeWord;

	private String macAddress;

	private long linkTypeId;

	private String productCode;

	public BAccountInfo() {
		super();
	}

	/**
	 * @param id
	 *            Идентификатор аккаунта (view_account_list.id)
	 * @param login
	 *            логин аккаунта (view_account_list.login)
	 * @param accountType
	 *            тип аккаунта (view_account_list.account_type)
	 * @param registrationDate
	 *            дата регистрации (view_account_list.reg_date)
	 */
	public BAccountInfo(long id, String login, String accountType,
			Date registrationDate) {
		super();
		this.id = id;
		this.login = login;
		this.accountType = accountType;
		this.registrationDate = registrationDate;
	}

	/**
	 * 
	 * 
	 * @param login
	 *            логин аккаунта (db.account_usr.login)
	 * @param question
	 *            контрольный вопрос (db.account_usr.question)
	 * @param answer
	 *            ответ на контрольный вопрос (db.account_usr.answer)
	 * @param providerId
	 *            идентификатор провайдера (tr.packet_type.provider_id)
	 * @param tarifPlan
	 *            тарифный план (tr.packet_type.name)
	 * @param webInterfaceTypeId
	 *            идентификатор типа веб-приложения
	 *            (db.abonent.web_interface_type_id)
	 */
	public BAccountInfo(String login, String question, String answer,
			long providerId, String tarifPlan, long webInterfaceTypeId) {
		super();
		this.login = login;
		this.question = question;
		this.answer = answer;
		this.providerId = providerId;
		this.tarifPlan = tarifPlan;
		this.webInterfaceTypeId = webInterfaceTypeId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getRepeatePassword() {
		return repeatePassword;
	}

	public void setRepeatePassword(String repeatePassword) {
		this.repeatePassword = repeatePassword;
	}

	public long getWebInterfaceTypeId() {
		return webInterfaceTypeId;
	}

	public void setWebInterfaceTypeId(long webInterfaceTypeId) {
		this.webInterfaceTypeId = webInterfaceTypeId;
	}

	public long getProviderId() {
		return providerId;
	}

	public void setProviderId(long providerId) {
		this.providerId = providerId;
	}

	public long getRadiusId() {
		return radiusId;
	}

	public void setRadiusId(long radiusId) {
		this.radiusId = radiusId;
	}

	public String getTarifPlan() {
		return tarifPlan;
	}

	public void setTarifPlan(String tarifPlan) {
		this.tarifPlan = tarifPlan;
	}

	public String getRadiusLogin() {
		return radiusLogin;
	}

	public void setRadiusLogin(String radiusLogin) {
		this.radiusLogin = radiusLogin;
	}

	public boolean isMacAddressUndefined() {
		return macAddressUndefined;
	}

	public void setMacAddressUndefined(boolean macAddressUndefined) {
		this.macAddressUndefined = macAddressUndefined;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public long getAccountTypeId() {
		return accountTypeId;
	}

	public void setAccountTypeId(long accountTypeId) {
		this.accountTypeId = accountTypeId;
	}

	public String getCodeWord() {
		return codeWord;
	}

	public void setCodeWord(String codeWord) {
		this.codeWord = codeWord;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public long getLinkTypeId() {
		return linkTypeId;
	}

	public void setLinkTypeId(long linkTypeId) {
		this.linkTypeId = linkTypeId;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

}
