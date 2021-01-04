package com.daurenzg.satori.webapp.manager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.daurenzg.commons.beans.satori.item.BItemInfo;
import com.daurenzg.commons.beans.satori.item.IBItem;
import com.daurenzg.satori.webapp.AbstractManagerAction;
import com.daurenzg.satori.webapp.SatoriConstants;
import com.teremok.commons.beans.IComponentFactory;
import com.teremok.struts.helper.StrutsHelperConstants;

public class EditPushUpForwardAction extends AbstractManagerAction {

	@Override
	protected ActionForward executeManagerAction(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			HttpSession session = request.getSession(true);
			EditPushUpForwardForm articleForm = (EditPushUpForwardForm) form;
			IComponentFactory manager = (IComponentFactory) session
					.getServletContext().getAttribute(
							SatoriConstants.MANAGER_ATTR);
			IBItem item = manager.getItem();
			BItemInfo itemInfo = item.getNotification(
					articleForm.getNotificationId(), articleForm.getLangId());

			session.setAttribute(SatoriConstants.PUSH_UP_ITEM, itemInfo);
			return mapping.findForward(StrutsHelperConstants.SUCCESS_FORWARD);
		} catch (Exception e) {
			return mapping.findForward(StrutsHelperConstants.ERROR_FORWARD);
		}
	}
}
