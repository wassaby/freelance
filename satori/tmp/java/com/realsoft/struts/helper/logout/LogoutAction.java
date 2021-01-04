/*
 * Created on 20.04.2005
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: LogoutAction.java,v 1.1 2014/07/01 11:58:28 dauren_work Exp $
 */
package com.realsoft.struts.helper.logout;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.realsoft.struts.helper.AbstractDealerAction;
import com.realsoft.struts.helper.StrutsHelperConstants;

/**
 * @author dimad
 */
public class LogoutAction extends AbstractDealerAction {

	public LogoutAction() {
		super();
	}

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		request.getSession(true).invalidate();
		return mapping.findForward(StrutsHelperConstants.SUCCESS_FORWARD);
	}

	protected boolean isAutorized(ActionMapping mapping, ActionForm form,
			HttpServletRequest request) {
		return true;
	}

}
