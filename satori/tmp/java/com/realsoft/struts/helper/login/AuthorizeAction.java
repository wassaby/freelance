/*
 * Created on 01.03.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: AuthorizeAction.java,v 1.1 2014/07/01 11:58:26 dauren_work Exp $
 */
package com.realsoft.struts.helper.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.realsoft.struts.helper.AbstractDealerAction;
import com.realsoft.struts.helper.StrutsHelperConstants;
import com.realsoft.struts.helper.StrutsHelperException;
import com.realsoft.struts.helper.StrutsUtils;

public class AuthorizeAction extends AbstractDealerAction {

	private static Logger log = Logger.getLogger(AuthorizeAction.class);

	public AuthorizeAction() {
		super();
	}

	protected ActionForward executeAction(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UniqueAccountChecker accountChecker = (UniqueAccountChecker) getServlet()
				.getServletContext().getAttribute(
						StrutsHelperConstants.USERNAME_LIST_ATTR);
		try {
			if (accountChecker != null
					&& accountChecker.isHostPassive(request.getRemoteHost())) {
				StrutsUtils.processErrors(request,
						StrutsHelperConstants.UNUNIQUE_SESSION_CLOSED,
						StrutsHelperConstants.UNUNIQUE_SESSION_CLOSED);
			}
		} catch (StrutsHelperException e) {
			log.debug(e);
		}
		return mapping.findForward(StrutsHelperConstants.SUCCESS_FORWARD);
	}

	protected boolean isAutorized(ActionMapping mapping, ActionForm form,
			HttpServletRequest request) {
		return true;
	}
}
