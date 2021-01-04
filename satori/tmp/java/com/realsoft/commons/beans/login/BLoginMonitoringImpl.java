package com.realsoft.commons.beans.login;

import com.realsoft.commons.beans.CommonsBeansConstants;
import com.realsoft.commons.beans.control.IBModelPanel;

public class BLoginMonitoringImpl implements IBLogin {

	public BLoginInfo login(String userName, String password, long toDateSum)
			throws BLoginException {
		throw new BLoginException(CommonsBeansConstants.ERR_SYSTEM,
				"current.nethod.not.available",
				"Not available in this implementation");
	}

	public BLoginInfo login(String userName, String password, String serviceName)
			throws BLoginException {
		throw new BLoginException(CommonsBeansConstants.ERR_SYSTEM,
				"current.nethod.not.available",
				"Not available in this implementation");
	}

	public IBModelPanel loginMonitoringClient(IBModelPanel rootPanel)
			throws BLoginException {
		return null;
	}

}
