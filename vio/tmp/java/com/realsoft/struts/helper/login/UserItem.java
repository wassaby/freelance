/*
 * Created on 28.03.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: UserItem.java,v 1.2 2016/04/15 10:37:25 dauren_home Exp $
 */
package com.realsoft.struts.helper.login;

public class UserItem {

	private String userName;

	private String host;

	private boolean isActive;

	private long sessionOpenTime;

	private long sessionCloseTime;

	public UserItem() {
		super();
	}

	public UserItem(String userName, String host, boolean isActive) {
		super();
		this.userName = userName;
		this.host = host;
		this.isActive = isActive;
		this.sessionOpenTime = System.currentTimeMillis();
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
		if (isActive) {
			sessionOpenTime = System.currentTimeMillis();
			sessionCloseTime = 0;
		} else
			sessionCloseTime = System.currentTimeMillis();
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public long getSessionCloseTime() {
		return sessionCloseTime;
	}

	public long getSessionOpenTime() {
		return sessionOpenTime;
	}

}
