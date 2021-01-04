package com.realsoft.struts.helper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class AbstractAuthenticationDispatchAction extends DispatchAction {

	private static Logger log = Logger
			.getLogger(AbstractAuthenticationDispatchAction.class);

	public AbstractAuthenticationDispatchAction() {
		super();
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (!isAutorized(mapping, form, request)) {
			log.debug("Not authorized request");
			return mapping.findForward(StrutsHelperConstants.LOGIN_FORWARD);
		}
		return super.execute(mapping, form, request, response);
	}

	protected boolean isAutorized(ActionMapping mapping, ActionForm form,
			HttpServletRequest request) {
		Object loginInfo = request.getSession(true).getAttribute(
				StrutsHelperConstants.LOGIN_INFO_ATTR);
		return loginInfo != null;
	}

}
