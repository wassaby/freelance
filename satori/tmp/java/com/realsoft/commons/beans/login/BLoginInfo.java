package com.realsoft.commons.beans.login;

import java.io.Serializable;

/**
 * Содержит информацию о подключении пользователя
 * 
 * @author temirbulatov
 * 
 */
public class BLoginInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String kasperskyKey = null;

	private String login = null;

	private String password;

	private long accountId;

	private long linkTypeId;

	private boolean canChangeCreditLimit;

	private boolean canChangeTariffPlan;

	public BLoginInfo() {
		super();
	}

	/**
	 * 
	 * @param login
	 *            Логин пользователя
	 * @param password
	 *            Пароль пользователя
	 */
	public BLoginInfo(String login, String password) {
		super();
		this.login = login;
		this.password = password;
	}

	/**
	 * Возвращает аккаунт абонента
	 */

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public long getLinkTypeId() {
		return linkTypeId;
	}

	public void setLinkTypeId(long linkTypeId) {
		this.linkTypeId = linkTypeId;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getKasperskyKey() {
		return kasperskyKey;
	}

	public void setKasperskyKey(String kasperskyKey) {
		this.kasperskyKey = kasperskyKey;
	}

	public boolean isCanChangeCreditLimit() {
		return canChangeCreditLimit;
	}

	public void setCanChangeCreditLimit(boolean canChangeCreditLimit) {
		this.canChangeCreditLimit = canChangeCreditLimit;
	}

	public boolean isCanChangeTariffPlan() {
		return canChangeTariffPlan;
	}

	public void setCanChangeTariffPlan(boolean canTariffPlans) {
		this.canChangeTariffPlan = canTariffPlans;
	}
}