package com.daurenzg.satori.webapp.admin;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.daurenzg.commons.beans.satori.dict.IBDict;
import com.daurenzg.satori.webapp.AbstractAdminAction;
import com.daurenzg.satori.webapp.SatoriConstants;
import com.teremok.commons.beans.IComponentFactory;
import com.teremok.struts.helper.StrutsHelperConstants;

public class ChangePswdAction extends AbstractAdminAction {

	@Override
	protected ActionForward executeAdminAction(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ChangePswdForm pswdForm = (ChangePswdForm) form;
		try {
			HttpSession session = request.getSession(true);
			IComponentFactory manager = (IComponentFactory) session
					.getServletContext().getAttribute(
							SatoriConstants.MANAGER_ATTR);
			IBDict dict = manager.getDict();
			dict.changePassword(pswdForm.getLogin(), pswdForm.getPassword(), pswdForm.getUserId());

			return mapping.findForward(StrutsHelperConstants.SUCCESS_FORWARD);
		} catch (Exception e) {
			return mapping.findForward(StrutsHelperConstants.ERROR_FORWARD);
		}
	}

}
