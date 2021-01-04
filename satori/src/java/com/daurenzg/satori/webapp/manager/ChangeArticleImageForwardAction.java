package com.daurenzg.satori.webapp.manager;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.daurenzg.satori.webapp.AbstractManagerAction;
import com.teremok.struts.helper.StrutsHelperConstants;

public class ChangeArticleImageForwardAction extends AbstractManagerAction {

	@Override
	protected ActionForward executeManagerAction(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
			return mapping.findForward(StrutsHelperConstants.SUCCESS_FORWARD);
	}
}
