package com.realsoft.struts.helper.success;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.realsoft.struts.helper.StrutsHelperConstants;

public class SuccessAction extends Action {

	public SuccessAction() {
		super();
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setAttribute(StrutsHelperConstants.SUCCESS_MSG_ATTR,
				"create-account.success.message");
		return mapping.findForward(StrutsHelperConstants.SUCCESS_FORWARD);
	}

}
