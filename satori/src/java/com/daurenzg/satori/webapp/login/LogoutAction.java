package com.daurenzg.satori.webapp.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.teremok.struts.helper.AbstractDealerAction;
import com.teremok.struts.helper.StrutsHelperConstants;

public class LogoutAction extends AbstractDealerAction {

	public LogoutAction() {
		super();
	}

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		request.getSession(true).invalidate();
		return mapping.findForward(StrutsHelperConstants.SUCCESS_FORWARD);
	}

	@Override
	protected boolean isAutorized(ActionMapping mapping, ActionForm form,
			HttpServletRequest request) {
		return true;
	}

}

