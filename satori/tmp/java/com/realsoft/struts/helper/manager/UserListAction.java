/*
 * Created on 28.03.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: UserListAction.java,v 1.1 2014/07/01 11:58:27 dauren_work Exp $
 */
package com.realsoft.struts.helper.manager;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.realsoft.struts.helper.AbstractDealerAction;
import com.realsoft.struts.helper.StrutsHelperConstants;
import com.realsoft.struts.helper.login.UniqueAccountChecker;
import com.realsoft.struts.helper.login.UserItem;

public class UserListAction extends AbstractDealerAction {

	public UserListAction() {
		super();
	}

	@Override
	protected ActionForward executeAction(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UniqueAccountChecker accountChecker = (UniqueAccountChecker) getServlet()
				.getServletContext().getAttribute(
						StrutsHelperConstants.USERNAME_LIST_ATTR);

		List<UserItem> userItemList = accountChecker.getUserItemState();

		request
				.setAttribute(StrutsHelperConstants.USER_LIST_ATTR,
						userItemList);

		return mapping.findForward(StrutsHelperConstants.SUCCESS_FORWARD);
	}

}
