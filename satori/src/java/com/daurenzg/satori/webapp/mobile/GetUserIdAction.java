package com.daurenzg.satori.webapp.mobile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.daurenzg.commons.beans.satori.mobile.BMobileItem;
import com.daurenzg.commons.beans.satori.mobile.IBMobile;
import com.daurenzg.satori.webapp.AbstractMobileAction;
import com.daurenzg.satori.webapp.SatoriConstants;
import com.teremok.commons.beans.CommonsBeansException;
import com.teremok.commons.beans.IComponentFactory;
import com.teremok.struts.helper.StrutsHelperConstants;

public class GetUserIdAction extends AbstractMobileAction{

	public ActionForward executeAction(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		try {
			IComponentFactory manager = (IComponentFactory) getServlet()
					.getServletContext()
					.getAttribute(SatoriConstants.MANAGER_ATTR);
			
			IBMobile user = manager.getMobile();
			BMobileItem mobItem = user.getNewUserId(SatoriConstants.USER_TYPE_MOBILE);
			request.setAttribute(SatoriConstants.NEW_USER_ATTRS, mobItem);

			return mapping.findForward(StrutsHelperConstants.SUCCESS_FORWARD);

		} catch (CommonsBeansException e) {
			request.setAttribute(SatoriConstants.LOGIN_ERROR_CODE_ATTR,
					e.getErrorCode());
			request.setAttribute(SatoriConstants.LOGIN_ERROR_MESSAGE_ATTR,
					e.getMessage());
			return mapping.findForward(StrutsHelperConstants.SUCCESS_FORWARD);
		}
	}
}
