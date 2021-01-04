/*
 * Created on 01.03.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: UniqueUserListener.java,v 1.2 2016/04/15 10:37:26 dauren_home Exp $
 */
package com.realsoft.struts.helper;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

import com.realsoft.struts.helper.login.UniqueAccountChecker;

public class UniqueUserListener implements HttpSessionListener {

	private static Logger log = Logger.getLogger(UniqueUserListener.class);

	public UniqueUserListener() {
		super();
	}

	public void sessionCreated(HttpSessionEvent event) {
		log.debug("Session created");
	}

	public void sessionDestroyed(HttpSessionEvent event) {
		log.debug("destroing session source object = " + event.getSource());
		HttpSession session = event.getSession();
		UniqueAccountChecker accountChecker = (UniqueAccountChecker) session
				.getServletContext().getAttribute(
						StrutsHelperConstants.USERNAME_LIST_ATTR);
		if (accountChecker != null) {
			String currentUsername = (String) session
					.getAttribute(StrutsHelperConstants.USERNAME_CURRENT_ATTR);
			log.debug("currentUsername = " + currentUsername);
			accountChecker.setAccountPassive(currentUsername);
		}
		log.debug("Session destroyed");
	}

}
