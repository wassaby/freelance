/*
 * Created on 28.03.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: DropUserAction.java,v 1.2 2016/04/15 10:37:40 dauren_home Exp $
 */
package com.realsoft.struts.helper.manager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.realsoft.struts.helper.AbstractDealerAction;
import com.realsoft.struts.helper.StrutsHelperConstants;
import com.realsoft.struts.helper.login.UniqueAccountChecker;

public class DropUserAction extends AbstractDealerAction {

	public DropUserAction() {
		super();
	}

	@Override
	protected ActionForward executeAction(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserForm userForm = (UserForm) form;

		UniqueAccountChecker accountChecker = (UniqueAccountChecker) getServlet()
				.getServletContext().getAttribute(
						StrutsHelperConstants.USERNAME_LIST_ATTR);

		accountChecker.dropAccount(userForm.getUserName());

		return mapping.findForward(StrutsHelperConstants.SUCCESS_FORWARD);
	}

}
