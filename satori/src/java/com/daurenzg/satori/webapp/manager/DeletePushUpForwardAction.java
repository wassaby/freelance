package com.daurenzg.satori.webapp.manager;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.daurenzg.satori.webapp.AbstractManagerAction;
import com.teremok.struts.helper.StrutsHelperConstants;

public class DeletePushUpForwardAction extends AbstractManagerAction {

	@Override
	protected ActionForward executeManagerAction(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession(true);
		
		return mapping.findForward(StrutsHelperConstants.SUCCESS_FORWARD);
	}

}
