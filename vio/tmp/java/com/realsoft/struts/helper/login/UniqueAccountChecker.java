/*
 * Created on 01.03.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: UniqueAccountChecker.java,v 1.2 2016/04/15 10:37:25 dauren_home Exp $
 */
package com.realsoft.struts.helper.login;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.realsoft.struts.helper.StrutsHelperException;

public class UniqueAccountChecker implements Runnable {

	private static Logger log = Logger.getLogger(UniqueAccountChecker.class);

	private List<UserItem> userNameList = new ArrayList<UserItem>();

	private long sessionTimeOut;

	private long unactiveDelay;

	private Thread thread = null;

	private boolean started = true;

	public UniqueAccountChecker(long sessionTimeOut, long unactiveDelay) {
		this.sessionTimeOut = sessionTimeOut;
		this.unactiveDelay = unactiveDelay;
		thread = new Thread(this);
		thread.start();
	}

	public boolean isAccountActive(String account, String host) {
		log.debug("checking is account is active =" + account);
		synchronized (userNameList) {
			for (Iterator<UserItem> iterator = userNameList.iterator(); iterator
					.hasNext();) {
				UserItem listItem = iterator.next();
				if (listItem.getUserName().equals(account)
						&& listItem.isActive()
						&& !listItem.getHost().equals(host)) {
					log.debug("account = " + account + "; host = " + host
							+ "; active");
					return true;
				}
			}
		}
		return false;
	}

	public boolean isHostPassive(String host) throws StrutsHelperException {
		log.debug("checking is host is passive = " + host);
		synchronized (userNameList) {
			for (Iterator<UserItem> iterator = userNameList.iterator(); iterator
					.hasNext();) {
				UserItem listItem = iterator.next();
				if (listItem.getHost().equals(host)) {
					log.debug(listItem.getHost() + " = " + host);
					if (listItem.isActive())
						return false;
					else
						return true;
				}
			}
		}
		throw new StrutsHelperException("No such host " + host);
	}

	// public boolean isHostPassive(String hostName) {
	// log.debug("checking is host is pasive ="+hostName);
	// synchronized (userNameList) {
	// for (Iterator<UserItem> iterator = userNameList.iterator(); iterator
	// .hasNext();) {
	// UserItem listItem = iterator.next();
	// log.debug("host = " + listItem.getHost());
	// if (listItem.getHost().equals(hostName) && !listItem.isActive()) {
	// log.debug(listItem.getHost() + " = " + hostName);
	// return true;
	// }
	// }
	// }
	// return false;
	// }
	//
	public void setAccountActive(String userName, String remoteHost) {
		log.debug("activating user =" + userName);
		synchronized (userNameList) {
			for (Iterator<UserItem> iterator = userNameList.iterator(); iterator
					.hasNext();) {
				UserItem listItem = iterator.next();
				if (listItem.getUserName().equals(userName)) {
					// log.debug(listItem.getUserName() + " = " + userName);
					listItem.setActive(true);
					return;
				}
			}
			userNameList.add(new UserItem(userName, remoteHost, true));
		}
	}

	public void setAccountPassive(String userName) {
		log.debug("passivating user = " + userName);
		synchronized (userNameList) {
			for (Iterator<UserItem> iterator = userNameList.iterator(); iterator
					.hasNext();) {
				UserItem listItem = iterator.next();
				if (listItem.getUserName().equals(userName)
						&& listItem.isActive()) {
					// log.debug("user = " + listItem.getUserName() + "
					// active");
					// log.debug(listItem.getUserName() + " = " + userName);
					listItem.setActive(false);
					return;
				}
			}
		}
	}

	public void dropAccount(String userName) {
		log.debug("passivating user =" + userName);
		synchronized (userNameList) {
			for (Iterator<UserItem> iterator = userNameList.iterator(); iterator
					.hasNext();) {
				UserItem listItem = iterator.next();
				if (listItem.getUserName().equals(userName)
						&& listItem.isActive()) {
					userNameList.remove(listItem);
					return;
				}
			}
		}
	}

	public List<UserItem> getUserItemState() {
		return userNameList;
	}

	public void stop() {
		started = false;
	}

	public void run() {
		while (started) {
			try {
				synchronized (userNameList) {
					// log.debug("userNameList.size = " + userNameList.size());
					for (Iterator<UserItem> iterator = userNameList.iterator(); iterator
							.hasNext();) {
						UserItem listItem = iterator.next();
						// log.debug("UserName = "
						// + listItem.getUserName()
						// + ": active = "
						// + listItem.isActive()
						// + ": time = "
						// + (System.currentTimeMillis() - listItem
						// .getSessionOpenTime()) / 1000);
						if (System.currentTimeMillis()
								- listItem.getSessionOpenTime() > sessionTimeOut) {
							listItem.setActive(false);
						}
						if ((!listItem.isActive() && System.currentTimeMillis()
								- listItem.getSessionCloseTime() > unactiveDelay)) {
							userNameList.remove(listItem);
							break;
						}
					}
				}
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				log.error("Could not thead", e);
			}
		}
	}

}
