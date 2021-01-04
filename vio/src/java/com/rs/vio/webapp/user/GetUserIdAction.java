package com.rs.vio.webapp.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.rs.commons.vio.user.BUserItem;
import com.rs.commons.vio.user.IBUser;
import com.rs.vio.webapp.VioConstants;
import com.teremok.commons.beans.CommonsBeansException;
import com.teremok.commons.beans.IComponentFactory;
import com.teremok.struts.helper.StrutsHelperConstants;

public class GetUserIdAction extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		try {
			IComponentFactory manager = (IComponentFactory) getServlet()
					.getServletContext()
					.getAttribute(VioConstants.MANAGER_ATTR);
			
			IBUser user = manager.getUser();
			//BUserItem userItem = user.getNewUserId(userForm.getIpAddress());
			BUserItem userItem = user.getNewUserId();
			request.setAttribute(VioConstants.NEW_USER_ATTRS, userItem);

			return mapping.findForward(StrutsHelperConstants.SUCCESS_FORWARD);

		} catch (CommonsBeansException e) {
			request.setAttribute(VioConstants.LOGIN_ERROR_CODE_ATTR,
					e.getErrorCode());
			request.setAttribute(VioConstants.LOGIN_ERROR_MESSAGE_ATTR,
					e.getMessage());
			return mapping.findForward(StrutsHelperConstants.SUCCESS_FORWARD);
		}
	}
}
